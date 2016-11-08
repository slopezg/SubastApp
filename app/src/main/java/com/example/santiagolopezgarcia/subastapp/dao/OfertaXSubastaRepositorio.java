package com.example.santiagolopezgarcia.subastapp.dao;

import com.example.santiagolopezgarcia.subastapp.datos.RepositorioBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.OfertaXSubasta;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public interface OfertaXSubastaRepositorio extends RepositorioBase<OfertaXSubasta> {

    OfertaXSubasta cargar(String codigo) throws ParseException;

    List<OfertaXSubasta> cargar();
}
