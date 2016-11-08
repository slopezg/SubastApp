package com.example.santiagolopezgarcia.subastapp.dao;

import com.example.santiagolopezgarcia.subastapp.datos.RepositorioBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Producto;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public interface ProductoRepositorio extends RepositorioBase<Producto> {

    Producto cargar(String identificacion) throws ParseException;

    List<Producto> cargar();
}
