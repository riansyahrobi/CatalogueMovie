package com.gmail.robidahariansyah8.cataloguemovie;

import org.json.JSONObject;

public class MovieItems {
    private String judul;
    private String deskripsi;
    private String tglRilis;
    private String gambar;
    private String rating;
    private String vote;

    public MovieItems() {
    }

    MovieItems(JSONObject object) {
        try {
            String gambar = object.getString("poster_path");
            String judul = object.getString("title");
            String tglRilis = object.getString("release_date");
            String rating = object.getString("vote_average");
            String vote = object.getString("vote_count");
            String deskripsi = object.getString("overview");

            this.gambar = gambar;
            this.judul = judul;
            this.tglRilis = tglRilis;
            this.rating = rating;
            this.vote = vote;
            this.deskripsi = deskripsi;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTglRilis() {
        return tglRilis;
    }

    public void setTglRilis(String tglRilis) {
        this.tglRilis = tglRilis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

}
