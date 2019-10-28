package com.example.itunesapp.actividades;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.itunesapp.R;
import com.example.itunesapp.dto.Cancion;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;


public class CancionFragment extends Fragment implements MediaPlayer.OnCompletionListener, View.OnClickListener {

    private Cancion cancion;
    private ImageView imagen_disco;
    private TextView artista_cancion;
    private TextView titulo_cancion;
    private MediaPlayer mediaPlayer;
    private ImageView imagen_play_pause;
    private SeekBar seekBar;
    private boolean sonando;
    private boolean pausado;
    private Timer timer;

    //Llamo al constructor del padre. Necesario para iniciar el fragment
    public CancionFragment() {
        super();
    }

    //Sobreescribo este método para devolver la vista raíz del fragment
    //inflando para ello el layout que representa la vista de dicho fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Bundle saquito = getArguments();
        this.cancion = saquito.getParcelable("cancion");

        Log.d("MIAPP", "onCreateView id_cancion " + cancion.getTrackId());

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cancion, container, false);


        this.imagen_disco = (ImageView)rootView.findViewById(R.id.imagen_disco);
        this.titulo_cancion = (TextView) rootView.findViewById(R.id.titulo_cancion);

        this.artista_cancion = (TextView)rootView.findViewById(R.id.artista_cancion);
        this.imagen_play_pause = (ImageView)rootView.findViewById(R.id.play_pause);
        this.seekBar = (SeekBar)rootView.findViewById(R.id.seekbar);



        this.sonando = false;
        this.pausado = false;

        initActividad();
        //reproducir(null);

        this.imagen_play_pause.setOnClickListener(this);//debo progrmar el listener programáticamente


        return rootView;

    }


    private void initActividad ()
    {
        Picasso.get().load(cancion.getArtworkUrl100()).into(imagen_disco);
        this.artista_cancion.setText(cancion.getArtistName());
        this.titulo_cancion.setText(cancion.getTrackName());
    }

    @Override
    public void onClick(View view) {
        //view debe ser vista botón

        if (sonando)
        {
            mediaPlayer.pause();
            imagen_play_pause.setImageResource(R.drawable.ic_play_96dp);
            pausado = true;
            sonando=false;

        } else if (pausado)
        {

            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            mediaPlayer.start();
            imagen_play_pause.setImageResource(R.drawable.ic_pause_96dp);
            pausado=false;
            sonando=true;


        } else {//estaba en sTOP

            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(cancion.getPreviewUrl()));
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();
            imagen_play_pause.setImageResource(R.drawable.ic_pause_96dp);
            sonando=true;

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            },0,1000);

        }



    }


    @Override
    public void onPause() { //este método se invoca cuando el fragment deja de verse (le sustitute otro) entonces, aquí, paro el reproducotr
        super.onPause();
        Log.d("MIAPP", "OnPuase Fragemnt");
        if (mediaPlayer!=null) //fue inicializado
        {
            mediaPlayer.stop();
            seekBar.setProgress(0);
            imagen_play_pause.setImageResource(R.drawable.ic_play_96dp);
            timer.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MIAPP", "OnStop Fragmemnt");
        if (mediaPlayer!=null) //fue inicializado
        {
            mediaPlayer.stop();
        }

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("MIAPP", "La canción ha terminado de sonar");
        mediaPlayer.stop();
        seekBar.setProgress(0);
        imagen_play_pause.setImageResource(R.drawable.ic_play_96dp);
        timer.cancel();
    }


}
