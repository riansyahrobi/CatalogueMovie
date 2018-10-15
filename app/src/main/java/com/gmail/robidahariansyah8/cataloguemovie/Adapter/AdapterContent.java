package com.gmail.robidahariansyah8.cataloguemovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.robidahariansyah8.cataloguemovie.BuildConfig;
import com.gmail.robidahariansyah8.cataloguemovie.MovieDetail;
import com.gmail.robidahariansyah8.cataloguemovie.MovieItems;
import com.gmail.robidahariansyah8.cataloguemovie.OnItemClickListener;
import com.gmail.robidahariansyah8.cataloguemovie.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterContent extends RecyclerView.Adapter<AdapterContent.ViewHolder> {
    private List<MovieItems> movieItemsList;
    private Context context;

    @BindString(R.string.detail)
    String detail;
    @BindString(R.string.share)
    String share;

    public AdapterContent(List<MovieItems> movieLists, Context context) {
        this.movieItemsList = movieLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_playing, parent, false);
        ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MovieItems movieItems = movieItemsList.get(position);
        holder.judul.setText(movieItems.getJudul());
        holder.deskripsi.setText(movieItems.getDeskripsi());

        String tglRilis = movieItems.getTglRilis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tgl = simpleDateFormat.parse(tglRilis);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat("E, MMM dd, yyyy");
            String tgl_rilis = newSimpleDateFormat.format(tgl);
            holder.tglRilis.setText(tgl_rilis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso
                .get()
                .load(BuildConfig.BASE_URL_w342 + movieItems.getGambar())
                .into(holder.gambar);

        holder.btnShare.setOnClickListener(new OnItemClickListener(position, new OnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, share + ": " + movieItems.getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnDetail.setOnClickListener(new OnItemClickListener(position, new OnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

            }
        }));

        holder.linearLayoutMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItems movieItems1 = movieItemsList.get(position);
                Intent intent = new Intent(context, MovieDetail.class);
                
                intent.putExtra(MovieDetail.EXTRA_JUDUL, movieItems1.getJudul());
                intent.putExtra(MovieDetail.EXTRA_DESKRIPSI, movieItems1.getDeskripsi());
                intent.putExtra(MovieDetail.EXTRA_RATING, movieItems1.getRating());
                intent.putExtra(MovieDetail.EXTRA_VOTE, movieItems1.getVote());
                intent.putExtra(MovieDetail.EXTRA_GAMBAR, movieItems1.getGambar());
                intent.putExtra(MovieDetail.EXTRA_TGL_RILIS, movieItems1.getTglRilis());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItemsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_title_fragment) TextView judul;
        @BindView(R.id.textView_overview_fragment) TextView deskripsi;
        @BindView(R.id.textView_release_date_fragment) TextView tglRilis;
        @BindView(R.id.imageView_movie_fragment) ImageView gambar;

        @BindView(R.id.button_detail) Button btnDetail;
        @BindView(R.id.button_share) Button btnShare;
        @BindView(R.id.linear_layout_movie) LinearLayout linearLayoutMovie;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
