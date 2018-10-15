package com.gmail.robidahariansyah8.cataloguemovie;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskLoaderKu extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mMovie;
    private boolean hasResult = false;

    private String kumpulanJudul;

    public AsyncTaskLoaderKu(final Context context, String kumpulanJudul) {
        super(context);
        onContentChanged();
        this.kumpulanJudul = kumpulanJudul;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mMovie);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data) {
        mMovie = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources();
            mMovie = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient syncHttpClient = new SyncHttpClient();

        final ArrayList<MovieItems> movieItems = new ArrayList<>();
        String url = BuildConfig.BASE_URL_SEARCH + "?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=" + kumpulanJudul;
        syncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems1 = new MovieItems(movie);
                        movieItems.add(movieItems1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItems;
    }

    private void onReleaseResources() {

    }
}
