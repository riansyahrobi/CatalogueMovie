package com.gmail.robidahariansyah8.cataloguemovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.robidahariansyah8.cataloguemovie.BuildConfig;
import com.gmail.robidahariansyah8.cataloguemovie.MovieItems;
import com.gmail.robidahariansyah8.cataloguemovie.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/*kesimpulan adapter digunakan untuk xml cetakan pencarian*/
public class AdapterMovie extends BaseAdapter {
    private ArrayList<MovieItems> movie = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public AdapterMovie(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items) {
        movie = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item) {
        movie.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        movie.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (movie == null) return 0;
        return movie.size();
    }

    @Override
    public MovieItems getItem(int i) {
        return movie.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.movie_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso
                .get()
                .load(BuildConfig.BASE_URL_w185 + movie.get(i).getGambar())
                .placeholder(context.getResources().getDrawable(R.drawable.gambar))
                .error(context.getResources().getDrawable(R.drawable.gambar))
                .into(holder.gambar);

        holder.judul.setText(movie.get(i).getJudul());

        String tgl = movie.get(i).getTglRilis();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(tgl);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat tgl_baru = new SimpleDateFormat("yyyy");
            String tgl_rilis = tgl_baru.format(date);
            holder.tglRilis.setText(tgl_rilis);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.deskripsi.setText(movie.get(i).getDeskripsi());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.textView_title_search) TextView judul;
        @BindView(R.id.textView_release_date_search) TextView tglRilis;
        @BindView(R.id.imageView_movie_search) ImageView gambar;
        @BindView(R.id.textView_overview_search) TextView deskripsi;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
