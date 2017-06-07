package com.olicor.miclima;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ClimaActual climaActual;
    private TextView temperatura;

    @BindView(R.id.txtTiempo) TextView hora;
    @BindView(R.id.txtHumedad) TextView humedad;
    @BindView(R.id.txtProbLluvia) TextView lluvia;
    @BindView(R.id.txtResumen) TextView pronostico;

    public static final String ETIQUETA = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        temperatura = (TextView) findViewById(R.id.txtTemperatura);


        String url, llaveApi;
        llaveApi = "01070baac6beabec5a38f3808294ca3c";
        double longitud, latitud;
        latitud = 19.02;
        longitud = -97.94;
        url = "https://api.darksky.net/forecast/" + llaveApi + "/" + latitud + "," + longitud +
                "?lang=es&units=si";

        if(redDisponible()) {
            OkHttpClient cliente = new OkHttpClient();
            Request peticion = new Request.Builder().url(url).build();
            Call llamada = cliente.newCall(peticion);
            llamada.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response respuesta) throws IOException {
                    try {
                        String datos = respuesta.body().string();
                        if (respuesta.isSuccessful()) {
                            climaActual = getDetallesActuales(datos);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    actualizarDatos();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }

                    } catch (Exception e) {
                        Log.e(ETIQUETA, "Excepción atrapada", e);
                    }
                }
            });

        }
        else{
            Toast.makeText(this, R.string.red_inaccesible, Toast.LENGTH_SHORT).show();
        }

    }

    private void actualizarDatos() {
        temperatura.setText(String.valueOf(Math.round(climaActual.getTemperatura())));
        humedad.setText(String.valueOf(climaActual.getHumedad()));
        lluvia.setText(String.valueOf(climaActual.getPrecipitacion()));
        pronostico.setText(climaActual.getResumen());
        hora.setText("A las " + climaActual.getTiempoComprensible() + " estarás a");
    }

    private ClimaActual getDetallesActuales(String datos) throws JSONException {
        JSONObject clima = new JSONObject(datos);
        String zonaHoraria = clima.getString("timezone");
        JSONObject actualmente = clima.getJSONObject("currently");
        ClimaActual climaActual = new ClimaActual();
        climaActual.setHumedad(actualmente.getDouble("humidity"));
        climaActual.setTemperatura(actualmente.getDouble("temperature"));
        climaActual.setPrecipitacion(actualmente.getDouble("precipProbability"));
        climaActual.setResumen(actualmente.getString("summary"));
        climaActual.setIcono(actualmente.getString("icon"));
        climaActual.setTiempo(actualmente.getLong("time"));
        climaActual.setZonaHoraria(zonaHoraria);
        Log.i(ETIQUETA, climaActual.getTiempoComprensible());
        return climaActual;
    }

    private boolean redDisponible() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialogo = new AlertDialogFragment();
        dialogo.show(getFragmentManager(), "dialogo_error");
    }
}
