package com.example.itunesapp.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.itunesapp.R;
import com.example.itunesapp.dto.Cancion;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class DetalleCancionActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

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


    private void initActividad ()
    {
        Picasso.get().load(cancion.getArtworkUrl100()).into(imagen_disco);
        this.artista_cancion.setText(cancion.getArtistName());
        this.titulo_cancion.setText(cancion.getTrackName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancion_layout);


        this.cancion = getIntent().getParcelableExtra("cancion");
        Log.d("MIAPP", "cancion recibida " + cancion.toString());

        this.imagen_disco = (ImageView)findViewById(R.id.imagen_disco);
        this.titulo_cancion = (TextView) findViewById(R.id.titulo_cancion);

        this.artista_cancion = (TextView) findViewById(R.id.artista_cancion);
        this.imagen_play_pause = (ImageView)findViewById(R.id.play_pause);
        this.seekBar = (SeekBar)findViewById(R.id.seekbar);



        this.sonando = false;
        this.pausado = false;

        initActividad();
        reproducir(null);



    }

    public void reproducir(View view) {

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

                mediaPlayer = MediaPlayer.create(this, Uri.parse(cancion.getPreviewUrl()));
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
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("MIAPP", "La canción ha terminado de sonar");
        mediaPlayer.stop();
        seekBar.setProgress(0);
        imagen_play_pause.setImageResource(R.drawable.ic_pause_96dp);
        timer.cancel();
    }
}
