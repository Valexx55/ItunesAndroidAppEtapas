package com.example.itunesapp.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.itunesapp.actividades.ItunesActivity;
import com.example.itunesapp.dto.ResultadoCanciones;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class QueryItunes extends AsyncTask<String, Void, ResultadoCanciones> {


    private Context context;//la activity desde la que nos invocan
    private final static String URI_ITUNES = "https://itunes.apple.com/search/?media=music&term=";//la dirección para búsquedas

    /**
     * Constructor. Truquito para guardar una referencia a la Activity desde que se le invoca.
     *
     * @param c la pantalla desde la que me llaman, para que una vez acabe este proceso, pueda volver a la activity que me llamó
     */
    public QueryItunes(Context c)
    {
        this.context = c;
    }

    /**
     * Método que formatea la URL para codificar espacios y caracteres especiale de forma adecuada
     * @param busqueda la cadena de búsqueda
     * @return la URL covenientemente formateada
     */
    private  URL preperarURL (String busqueda)
    {
        URL url = null;
        String queryString = null;

        try
            {
            queryString = URLEncoder.encode(busqueda, "UTF-8");
            url = new URL(URI_ITUNES+queryString);
            Log.d("MIAPP", "url = "+url);
            }
            catch (Exception e)
            {
                Log.e("MIAPP", "Problemas al componer la URL", e);
            }


        return  url;
    }


    /**
     * Método que procura la conexión al servidor externo
     * @param canciones los términos de búsqueda preparado para que sean varios, en nuestro caso, sólo uno, el pimero
     * @return la información recuperada por la búsqueda. Null si hubo algún fallo en la comunicación
     */
    @Override
    protected ResultadoCanciones doInBackground(String... canciones) {
        ResultadoCanciones rc = null;

        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStreamReader is = null;

        try {
            url = preperarURL(canciones[0]);//en canciones[0] está el término de búsqueda. Con él, preparo la URL
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");//por defecto sería GET

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {//leo la respuesta y si fue bien

                Log.d("MIAPP", "La info que viaja en el cuerpo es de tipo "+ httpURLConnection.getContentType());
                is = new InputStreamReader(httpURLConnection.getInputStream());//leo el cuerpo
                Gson gson = new Gson();
                rc = gson.fromJson(is, ResultadoCanciones.class);//lo paso de texto JSON a Objeto JAVA

            }


        } catch (Exception e) {
            Log.e("MIAPP", "Errro al comunicarme con el servdor itunes", e);
        } finally
        {
            try
            {
                is.close();//el cuerpo es como un fichero. Debo cerrarlo
            } catch (IOException e)
            {
                Log.e("MIAPP", "Error al liberar recursos", e);
            }
            httpURLConnection.disconnect();//debo tambień librerar la conexión, haya ido bien o mal, por eso en el bloque finally
        }

        return rc;
    }

    /**
     * Este método es invocado una vez finaliza la comunicación remota hecha en doInBackGround
     * @param resultadoCanciones el valor del objeto que devuelve el método anterior
     */
    @Override
    protected void onPostExecute(ResultadoCanciones resultadoCanciones) {

        super.onPostExecute(resultadoCanciones);

        if (resultadoCanciones!=null) {
            //simplemente hago un log informativo con el resultado de las canciones recuperdas, en caso de que no se vacío
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(resultadoCanciones);
            Log.d("MIAPP", "JSON CANCIONES = " + json);
        }
        //aviso a la pantalla que los datos están listos
        ItunesActivity ia = (ItunesActivity)this.context;
        ia.actualizarLista(resultadoCanciones);



    }
}