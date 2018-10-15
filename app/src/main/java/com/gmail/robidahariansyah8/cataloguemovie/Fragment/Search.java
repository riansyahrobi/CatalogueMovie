package com.gmail.robidahariansyah8.cataloguemovie.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.gmail.robidahariansyah8.cataloguemovie.Adapter.AdapterMovie;
import com.gmail.robidahariansyah8.cataloguemovie.AsyncTaskLoaderKu;
import com.gmail.robidahariansyah8.cataloguemovie.MovieDetail;
import com.gmail.robidahariansyah8.cataloguemovie.MovieItems;
import com.gmail.robidahariansyah8.cataloguemovie.R;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    AdapterMovie adapterMovie;
    static final String EXTRAS_MOVIE = "extras_movie";

    @BindView(R.id.movie_list)
    ListView listView;
    @BindView(R.id.editText_find_movie)
    EditText judul;
    @BindView(R.id.button_find_movie)
    Button btnCari;

    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        adapterMovie = new AdapterMovie(Objects.requireNonNull(getActivity()));
        adapterMovie.notifyDataSetChanged();

        listView.setAdapter(adapterMovie);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItems movieItems = (MovieItems) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), MovieDetail.class);

                intent.putExtra(MovieDetail.EXTRA_JUDUL, movieItems.getJudul());
                intent.putExtra(MovieDetail.EXTRA_DESKRIPSI, movieItems.getDeskripsi());
                intent.putExtra(MovieDetail.EXTRA_TGL_RILIS, movieItems.getTglRilis());
                intent.putExtra(MovieDetail.EXTRA_GAMBAR, movieItems.getGambar());
                intent.putExtra(MovieDetail.EXTRA_RATING, movieItems.getRating());
                intent.putExtra(MovieDetail.EXTRA_VOTE, movieItems.getVote());

                startActivity(intent);
            }
        });

        Objects.requireNonNull(btnCari).setOnClickListener(movieListener);

        String judul_movie = judul.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, judul_movie);

        getLoaderManager().initLoader(0, bundle, Search.this);

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String judulMovie = "";
        if (args != null) {
            judulMovie = args.getString(EXTRAS_MOVIE);
        }
        return new AsyncTaskLoaderKu(getActivity(), judulMovie);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapterMovie.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<MovieItems>> loader) {
        adapterMovie.setData(null);
    }

    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movie_title = judul.getText().toString();
            if (TextUtils.isEmpty(movie_title)) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movie_title);
            getLoaderManager().restartLoader(0, bundle, Search.this);
        }
    };
}
