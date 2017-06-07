package com.olicor.miclima;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by subaru on 6/06/17.
 */

public class ClimaActual {
    private String icono, resumen, zonaHoraria;
    private long tiempo;
    private double temperatura, humedad, precipitacion;
    int iconoId;
    
    
    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }



    public String getTiempoComprensible(){
        SimpleDateFormat formato = new SimpleDateFormat("hh:mm a");
        formato.setTimeZone(TimeZone.getTimeZone(getZonaHoraria()));
        Date horaFecha = new Date(getTiempo() * 1000);
        String tiempo = formato.format(horaFecha);
        return tiempo;
    }

    public int getIconoId(){
        
        if (icono.equals("clear-day")) {
            iconoId = R.drawable.clear_day;
        }
        else if (icono.equals("clear-night")) {
            iconoId = R.drawable.clear_night;
        }
        else if (icono.equals("rain")) {
            iconoId = R.drawable.rain;
        }
        else if (icono.equals("snow")) {
            iconoId = R.drawable.snow;
        }
        else if (icono.equals("sleet")) {
            iconoId = R.drawable.sleet;
        }
        else if (icono.equals("wind")) {
            iconoId = R.drawable.wind;
        }
        else if (icono.equals("fog")) {
            iconoId = R.drawable.fog;
        }
        else if (icono.equals("cloudy")) {
            iconoId = R.drawable.cloudy;
        }
        else if (icono.equals("partly-cloudy-day")) {
            iconoId = R.drawable.partly_cloudy;
        }
        else if (icono.equals("partly-cloudy-night")) {
            iconoId = R.drawable.cloudy_night;
        }
        return iconoId;
    }


    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getPrecipitacion() {
        return precipitacion;
    }

    public void setPrecipitacion(double precipitacion) {
        this.precipitacion = precipitacion;
    }
}
