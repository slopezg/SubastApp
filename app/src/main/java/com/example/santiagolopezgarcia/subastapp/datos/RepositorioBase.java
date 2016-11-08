package com.example.santiagolopezgarcia.subastapp.datos;

import java.text.ParseException;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public interface RepositorioBase<T> {

    boolean tieneRegistros();

    boolean guardar(List<T> lista) throws ParseException;

    boolean guardar(T dato) throws ParseException;

    boolean actualizar(T dato);

    boolean eliminar(T dato);

}