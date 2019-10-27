package com.example.itunesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itunesapp.R;
import com.example.itunesapp.dto.Cancion;

import java.util.List;

public class ListaCancionesAdapter extends RecyclerView.Adapter<CancionViewHolder> {


    private List<Cancion> lista_canciones;


    public ListaCancionesAdapter() {}

    public ListaCancionesAdapter(List<Cancion> lista_canciones) {
        this.lista_canciones = lista_canciones;
    }

    public List<Cancion> getLista_canciones() {
        return lista_canciones;
    }

    public void setLista_canciones(List<Cancion> lista_canciones) {
        this.lista_canciones = lista_canciones;
    }

    @NonNull
    @Override
    public CancionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CancionViewHolder cancionViewHolder = null;

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fila_cancion, parent, false);
            cancionViewHolder = new CancionViewHolder(itemView);

        return cancionViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CancionViewHolder cancion_view_holder, int position) {

        Cancion cancion = this.lista_canciones.get(position);
        cancion_view_holder.cargarCancionEnHolder(cancion);

    }

    @Override
    public int getItemCount() {
        int numero_canciones;

            numero_canciones = this.lista_canciones.size();

        return numero_canciones;
    }
}
