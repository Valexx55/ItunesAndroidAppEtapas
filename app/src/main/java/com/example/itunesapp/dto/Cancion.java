package com.example.itunesapp.dto;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cancion implements Parcelable {

    private String artistName;
    private int trackId;
    private String trackName;
    private String previewUrl;//la dirección WEB (URI) del archivo musical mp4
    private String artworkUrl100;//la URI de la imagen de la portada del disco/canción


    //TODOS ESTOS MÉTODOS SON PARA HACER MI OBJETO CANCIÓN PARCELABE
    //ES DECIR, QUE PUEDA ESCRIBIRSE EN BUNDLES
    public static final Parcelable.Creator<Cancion> CREATOR = new Parcelable.Creator<Cancion>(){

        @Override
        public Cancion createFromParcel(Parcel parcel) {
            return new Cancion(parcel);
        }

        @Override
        public Cancion[] newArray(int size) {
            return new Cancion[size];
        }
    };




    public Cancion(String artistName, int trackId, String trackName, String previewUrl, String artworkUrl100) {
        this.artistName = artistName;
        this.trackId = trackId;
        this.trackName = trackName;
        this.previewUrl = previewUrl;
        this.artworkUrl100 = artworkUrl100;
    }

    //importantísimo para que esto funcione, es que se escriba en el parcel y se lea en el mismo orden
    //Creamos una canción desde un parcel (deserializamos en realidad)
    public Cancion (Parcel parcel)
    {
        this.artistName = parcel.readString();
        this.trackId = parcel.readInt();
        this.trackName = parcel.readString();
        this.previewUrl = parcel.readString();
        this.artworkUrl100 = parcel.readString();
    }

    //guardamos la canción en un parcel (serializamos en realidad)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.artistName);
        parcel.writeInt(this.trackId);
        parcel.writeString(this.trackName);
        parcel.writeString(this.previewUrl);
        parcel.writeString(this.artworkUrl100);

    }

    @Override
    public int describeContents() {
        return 0;
    }
    //HASTA AQUÍ EL PARCELABLE

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public Cancion ()
    {

    }

    public Cancion(String artistName, int trackId, String trackName) {
        this.artistName = artistName;
        this.trackId = trackId;
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "artistName='" + artistName + '\'' +
                ", trackId=" + trackId +
                ", trackName='" + trackName + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", artworkUrl100='" + artworkUrl100 + '\'' +
                '}';
    }
}