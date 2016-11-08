package com.example.santiagolopezgarcia.subastapp.dao;

import com.example.santiagolopezgarcia.subastapp.datos.RepositorioBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Mensaje;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 31/10/16.
 */

public interface MensajeRepositorio extends RepositorioBase<Mensaje>{
    Mensaje cargar(String codigo) throws ParseException;

    List<Mensaje> cargar();
}
