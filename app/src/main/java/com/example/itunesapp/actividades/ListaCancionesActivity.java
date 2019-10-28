package com.example.itunesapp.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.example.itunesapp.R;
import com.example.itunesapp.adapter.CancionesVPAdapter;
import com.example.itunesapp.anim.AnimacionEntreFragments;
import com.example.itunesapp.dto.Cancion;

import java.util.List;

public class ListaCancionesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        List<Cancion> lista_canciones = getIntent().getParcelableArrayListExtra("lista_canciones");
        int posicion = getIntent().getIntExtra("posicion", 0);//posicion x defecto

        Log.d("MIAPP","onCreate ListaCancionesActivity pos inicial = " + posicion);
        // Obtengo la referencia al ViewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Y le asigno su adpter
        pagerAdapter = new CancionesVPAdapter(getSupportFragmentManager(), lista_canciones);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(posicion);

        //opcional, para efectos entre transiciones
        viewPager.setPageTransformer(true, new AnimacionEntreFragments());
    }
}
