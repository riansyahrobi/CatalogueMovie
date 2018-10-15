package com.gmail.robidahariansyah8.cataloguemovie.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.robidahariansyah8.cataloguemovie.Adapter.AdapterContent;
import com.gmail.robidahariansyah8.cataloguemovie.BuildConfig;
import com.gmail.robidahariansyah8.cataloguemovie.MovieItems;
import com.gmail.robidahariansyah8.cataloguemovie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlaying extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MovieItems> movieItemsList;

    private static final String URL = BuildConfig.BASE_URL + "/now_playing?api_key=" + BuildConfig.API_KEY + "&language=en-US";;

    public NowPlaying() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieItemsList = new ArrayList<>();
        loadData();
        return view;
    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading . . .");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MovieItems movieItems = new MovieItems();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        movieItems.setJudul(jsonObject1.getString("title"));
                        movieItems.setDeskripsi(jsonObject1.getString("overview"));
                        movieItems.setTglRilis(jsonObject1.getString("release_date"));
                        movieItems.setGambar(jsonObject1.getString("poster_path"));
                        movieItems.setRating(jsonObject1.getString("vote_average"));
                        movieItems.setVote(jsonObject1.getString("vote_count"));
                        movieItemsList.add(movieItems);
                    }
                    adapter = new AdapterContent(movieItemsList, getActivity());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        requestQueue.add(stringRequest);
    }
}
