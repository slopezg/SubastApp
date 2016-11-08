package com.example.santiagolopezgarcia.subastapp.modelonegocio;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class Mensaje implements Serializable {

    private String codigo;
    private Usuario emisor;
    private Usuario receptor;
    private String contenido;
    private String estado;
    private Date fecha;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String esatdo) {
        this.estado = esatdo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
