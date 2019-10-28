package com.example.itunesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itunesapp.R;
import com.example.itunesapp.actividades.DetalleCancionActivity;
import com.example.itunesapp.actividades.ListaCancionesActivity;
import com.example.itunesapp.dto.Cancion;

import java.util.ArrayList;
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


            cancionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int poscion = (int)view.getTag();

                    Log.d("MIAPP", "Ha tocado la fila " +poscion);

                    Intent intent = new Intent(view.getContext(), ListaCancionesActivity.class);
                    intent.putParcelableArrayListExtra("lista_canciones", (ArrayList<Cancion>)lista_canciones);//me pide el subtipoArray. lo casteo de List a ArrayList y ya
                    intent.putExtra("posicion", poscion);

                    view.getContext().startActivity(intent);


                }
            });



        return cancionViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CancionViewHolder cancion_view_holder, int position) {

        Cancion cancion = this.lista_canciones.get(position);
        cancion_view_holder.cargarCancionEnHolder(cancion, position);

        cancion_view_holder.itemView.setTag(position);


    }

    @Override
    public int getItemCount() {
        int numero_canciones;

            numero_canciones = this.lista_canciones.size();

        return numero_canciones;
    }
}
