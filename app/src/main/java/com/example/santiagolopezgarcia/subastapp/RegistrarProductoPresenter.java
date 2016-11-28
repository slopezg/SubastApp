package com.example.santiagolopezgarcia.subastapp;

import android.content.Context;

import com.example.santiagolopezgarcia.subastapp.dao.CategoriaDao;
import com.example.santiagolopezgarcia.subastapp.dao.CategoriaRepositorio;
import com.example.santiagolopezgarcia.subastapp.dao.ProductoDao;
import com.example.santiagolopezgarcia.subastapp.dao.ProductoRepositorio;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Producto;
import com.example.santiagolopezgarcia.subastapp.view.IRegistrarProductoView;

import java.text.ParseException;


/**
 * Created by santiagolopezgarcia on 28/11/16.
 */

public class RegistrarProductoPresenter {

    private IRegistrarProductoView iRegistrarProductoView;
    private Context context;
    private CategoriaRepositorio categoriaRepositorio;
    private ProductoRepositorio productoRepositorio;

    public RegistrarProductoPresenter(Context context) {
        this.context = context;
        categoriaRepositorio = new CategoriaDao(context);
        productoRepositorio = new ProductoDao(context);
        if(context instanceof IRegistrarProductoView)
            iRegistrarProductoView  = (IRegistrarProductoView) context;
    }

    public void iniciar() {
        iRegistrarProductoView.mostrarCategorias(categoriaRepositorio.cargar());
    }

    public boolean guardar(Producto producto) {
        try {
            return productoRepositorio.guardar(producto);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
