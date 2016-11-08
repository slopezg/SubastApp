package com.example.santiagolopezgarcia.subastapp.datos;

import com.example.santiagolopezgarcia.subastapp.dao.CategoriaDao;
import com.example.santiagolopezgarcia.subastapp.dao.MensajeDao;
import com.example.santiagolopezgarcia.subastapp.dao.OfertaXSubastaDao;
import com.example.santiagolopezgarcia.subastapp.dao.ProductoDao;
import com.example.santiagolopezgarcia.subastapp.dao.SubastaDao;
import com.example.santiagolopezgarcia.subastapp.dao.UsuarioDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class AdministradorBaseDatos implements AdministradorBaseDatosInterface {

    private boolean ejecutoActualizacion;

    @Override
    public List<String> GetQuerysCreate() {
        List<String> lista = new ArrayList<>();
        if (!ejecutoActualizacion) {
            lista.add(UsuarioDao.CREAR_SCRIPT);
            lista.add(CategoriaDao.CREAR_SCRIPT);
            lista.add(ProductoDao.CREAR_SCRIPT);
            lista.add(SubastaDao.CREAR_SCRIPT);
            lista.add(OfertaXSubastaDao.CREAR_SCRIPT);
            lista.add(MensajeDao.CREAR_SCRIPT);
        }
        return lista;
    }

    @Override
    public List<String> GetQuerysUpgrade() {
        ejecutoActualizacion = true;
        List<String> lista = new ArrayList<>();
        return lista;
    }
}