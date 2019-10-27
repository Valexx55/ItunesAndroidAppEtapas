package com.example.itunesapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itunesapp.R;
import com.example.itunesapp.dto.Cancion;

public class CancionViewHolder extends RecyclerView.ViewHolder {

    private TextView tv_artista;
    private TextView tv_cancion;
    private ImageView icono_detalle;

    public CancionViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tv_artista = itemView.findViewById(R.id.artista);
        this.tv_cancion = itemView.findViewById(R.id.cancion);
        this.icono_detalle = itemView.findViewById(R.id.icono_detalle);


    }



    public void cargarCancionEnHolder (Cancion cancion, int posicion)
    {
        this.tv_artista.setText(cancion.getArtistName());
        this.tv_cancion.setText(cancion.getTrackName());
        this.icono_detalle.setTag(posicion);
    }
}
