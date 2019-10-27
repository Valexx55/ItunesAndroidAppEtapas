package com.example.itunesapp.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.itunesapp.R;
import com.example.itunesapp.adapter.ListaCancionesAdapter;
import com.example.itunesapp.dto.Cancion;
import com.example.itunesapp.dto.ResultadoCanciones;
import com.example.itunesapp.remote.QueryItunes;
import com.example.itunesapp.util.InternetUtil;

import java.util.ArrayList;
import java.util.List;

public class ItunesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {



    private List<Cancion> lista_canciones;//la lista de canciones con la informarción en objetos
    private RecyclerView recyclerView;//la lista dibujada
    private ListaCancionesAdapter adapter;//el adapter que necesita el recycler para ir dibujando la lista
    private SearchView lupa;//objeto visual para búsquedas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = findViewById(R.id.recyclerview);
        this.lupa = findViewById(R.id.lupa);//obtengo referencia a la vista de la caja de búsqueda

        this.lupa.setOnQueryTextListener(this);//y programo el listener sobre los eventos de introducción de texto
        this.lupa.setOnCloseListener(this);//y cuando pulsa la equis (X). En este caso, gestiono esos eventos desde esta clase (this)
        //para lo cual, hice que esta clase implementase esas dos interfaces implements SearchView.OnQueryTextListener, SearchView.OnCloseListener
        //recibiendo así sendos callbacks a onQueryTextChange -introduce nuevas letras en la caja o le da a la equis por primera vez-, onClose -le da a la equis- y onQueryTextSubmit -le da a buscar-

        ocultarBarra ();//oculto la barra porque sólo debe mostrarse mientras se busca, como espera a los resultados de la conexión a internet
    }

    public void buscarCanciones (String termino_busqueda)
    {
        if (InternetUtil.hayInternet(this)) {//compruebo que haya conexión a internet en el dispositivo. Sino, poca leche que buscar

            mostrarBarra();//en caso afirmativo dibujo la barra de búsqueda

            QueryItunes queryItunes = new QueryItunes(this);//preparo el objeto de búsqueda. Este paso lo hago para que el objeto de bśuqueda tenga una referencia a la pantalla que lo ha invocado (this). Así, cuando acaba de buscar, puede invocar a la pantalla con los resultados de la conexión
            queryItunes.execute(termino_busqueda);//y lanzo su ejecución

        } else //aviso al usuario de que no se puede buscar porque no tiene conexión a internet
        {
            Toast aviso = Toast.makeText(this, "NO HAY INTERNET", Toast.LENGTH_SHORT);
            aviso.show();
        }
    }

    /**
     * método que oculta la barra de progreso
     */
    private void ocultarBarra ()
    {
        Log.d("MIAPP", "Ocultando");
        ProgressBar pb = findViewById(R.id.barra_progreso);
        pb.setVisibility(View.INVISIBLE);
    }

    /**
     * método que muestra la barra de progreso mientras se realiza la búsqueda en la otra clase
     */
    private void mostrarBarra ()
    {
        Log.d("MIAPP", "Mostrando");
        ProgressBar pb = findViewById(R.id.barra_progreso);
        pb.setVisibility(View.VISIBLE);
    }

    /**
     * Método que será invocado por la clase de búsqueda al finalizar su tarea de buscar.
     * Con la información recibida, trata de representar la lista visualmente
     *
     * @param rc Es el objeto con la información recuperada rc de ResultadoCaniones
     *
     */
    public void actualizarLista (ResultadoCanciones rc)
    {

        if (rc!=null && rc.getResultCount()>0)//puede ser que sea null si hubo algún fallo o que no haya habido resultados en la búsqueda
        {
            Log.d("MIAPP", "Ha habido resultados. Mostrando ...");//en caso que sí haya que mostrar
            this.adapter = new ListaCancionesAdapter();//preparo el adapter
            this.lista_canciones = rc.getResults();//le paso los datos
            this.adapter.setLista_canciones(lista_canciones);//que son las listas de canciones
            this.recyclerView.setAdapter(adapter);//le asocio el adapter al recycler, para que vaya inflando los items (filas)
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));//seteo el estilo al recyclerpara respresentar la lista verticalmente

        }
        else {//si no ha habido datos, le informo al usuario y no hago nada más. Ni recycler ni niño muerto

            Toast aviso = Toast.makeText(this, "Su búsqueda no produjo resultados", Toast.LENGTH_SHORT);
            aviso.show();
            vaciarLista();//caso especial, cuando rebusca y la primera vez tuvo resltados y la segunda no, hay que limpiar la lista

        }
        ocultarBarra();//en cualquier caso, oculto la barra de progreso, pues la tarea de buscar finalizó


    }

    @Override
    public boolean onQueryTextSubmit(String busqueda) { //ha tocado el botón de buscar, recibo directamente en el String lo que ha introducido
        Log.d("MIAPP", "onQueryTextSubmit " + busqueda);
        buscarCanciones(busqueda);//con ese dato, mando a buscar
        return false;//ni me va ni me viene este boolean, sabe Dios pa qué vale esto. habría que ver la documentación del API (curiosos, busquen)
    }

    private void vaciarLista()
    {
        if (adapter!=null && adapter.getLista_canciones()!=null) {
            adapter.getLista_canciones().clear();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Este método se invoca cada vez que el usuario introduce una letra en la caja de búsqueda
     * También se invoca cuando toca la equis por primera vez (funcionamiento descubierto por prueba y error)
     *
     * @param busqueda la nueva palabra construida tras el último caracter introducido
     * @return
     */
    @Override
    public boolean onQueryTextChange(String busqueda) {
        Log.d("MIAPP", "onQueryTextChange " + busqueda);
        if (busqueda==null||busqueda.isEmpty())//si la caja de búsqueda tiene algo, es que está buscando algo, pero no ha terminado de darle a la lupa
        {
            vaciarLista ();//borro la lista por si hubiera información de una búsqueda anterior
        }

        return false;
    }

    @Override
    public boolean onClose() {
        Log.d("MIAPP", "onClose ");
        vaciarLista ();//el usuario toca la equis, limpia la caja de búsqueda. Yo, limpio la lista.
        return false;
    }


    public void verdetalle(View view) {
        Log.d("MIAPP", "verdetalle ");

        int posicion = (int)view.getTag();
        Cancion cancion = lista_canciones.get(posicion);

        Intent intent_detalle = new Intent(this, DetalleCancionActivity.class);
        intent_detalle.putExtra("cancion", cancion);
        startActivity(intent_detalle);

    }
}
