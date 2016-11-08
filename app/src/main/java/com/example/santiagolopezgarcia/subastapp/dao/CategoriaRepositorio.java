package com.example.santiagolopezgarcia.subastapp.dao;

import com.example.santiagolopezgarcia.subastapp.datos.RepositorioBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Categoria;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public interface CategoriaRepositorio extends RepositorioBase<Categoria> {

    Categoria cargar(String codigo) throws ParseException;

    List<Categoria> cargar();
}
