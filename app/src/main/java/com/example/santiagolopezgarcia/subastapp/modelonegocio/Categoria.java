package com.example.santiagolopezgarcia.subastapp.modelonegocio;

import java.io.Serializable;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class Categoria implements Serializable {

    private String codigo;
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
