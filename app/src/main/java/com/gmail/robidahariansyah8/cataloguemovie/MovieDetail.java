package com.gmail.robidahariansyah8.cataloguemovie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity {
    @BindView(R.id.textView_title) TextView txtJudul;
    @BindView(R.id.textView_overview) TextView txtDeskripsi;
    @BindView(R.id.textView_release_date) TextView txtTglRilis;
    @BindView(R.id.textView_rating) TextView txtRating;
    @BindView(R.id.imageView_movie) ImageView imgGambar;
    @BindView(R.id.textView_vote) TextView txtVote;

    public static String EXTRA_GAMBAR = "extra_gambar";
    public static String EXTRA_JUDUL = "extra_judul";
    public static String EXTRA_TGL_RILIS = "extra_tgl_rilis";
    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_VOTE = "extra_vote";
    public static String EXTRA_DESKRIPSI = "extra_deskripsi";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        String judul = getIntent().getStringExtra(EXTRA_JUDUL);
        String deskripsi = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        String tglRilis = getIntent().getStringExtra(EXTRA_TGL_RILIS);
        String gambar = getIntent().getStringExtra(EXTRA_GAMBAR);
        String rating = getIntent().getStringExtra(EXTRA_RATING);
        String vote = getIntent().getStringExtra(EXTRA_VOTE);

        Picasso
                .get()
                .load(BuildConfig.BASE_URL_ORIGINAL + gambar)
                .into(imgGambar);

        txtJudul.setText(judul);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(tglRilis);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat tgl_baru = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String tgl_rilis = tgl_baru.format(date);
            txtTglRilis.setText(tgl_rilis);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtRating.setText(rating + " Ratings (" + rating + "/10)");

        txtVote.setText(vote);

        txtDeskripsi.setText(deskripsi);
    }
}
