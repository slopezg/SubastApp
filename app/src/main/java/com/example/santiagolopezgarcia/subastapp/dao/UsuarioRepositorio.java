package com.example.santiagolopezgarcia.subastapp.dao;

import com.example.santiagolopezgarcia.subastapp.datos.RepositorioBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Usuario;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public interface UsuarioRepositorio extends RepositorioBase<Usuario> {
    Usuario cargar(String identificacion) throws ParseException;

    List<Usuario> cargar();
}
