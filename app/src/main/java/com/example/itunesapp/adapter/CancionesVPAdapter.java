package com.example.itunesapp.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.itunesapp.actividades.CancionFragment;
import com.example.itunesapp.dto.Cancion;

import java.util.List;

public class CancionesVPAdapter extends FragmentStatePagerAdapter {


    private List<Cancion> cancionList;

    public CancionesVPAdapter (FragmentManager fragmentManager, List<Cancion> lista_canciones)
    {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.cancionList = lista_canciones;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Cancion cancion = null;
        Bundle saquito = null;


            Log.d("MIAPP", "getItem " + position);
            fragment = new CancionFragment();
            cancion = cancionList.get(position);

            saquito = new Bundle();
            saquito.putParcelable("cancion", cancion);

            fragment.setArguments(saquito);

        return  fragment;
    }

    @Override
    public int getCount() {
        int tamanio = -1;

            tamanio = cancionList.size();

        return tamanio;
    }
}
