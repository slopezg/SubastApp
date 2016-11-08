package com.example.santiagolopezgarcia.subastapp.datos;

import android.content.Context;
import android.os.Environment;

import com.example.santiagolopezgarcia.subastapp.helpers.Constantes;

import java.io.File;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public abstract class DaoBase {

    protected Context context;
    protected OperadorDatos operadorDatos;
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String REAL_TYPE = "real";
    private static final String NOMBRE_BASE_DATOS = "Subastapp.db";
    private static final int VERSION_BASE_DATOS = 1;

    public DaoBase(Context context) {
        this.context = context;
        String rutaBaseDatos = Environment.getExternalStorageDirectory() + File.separator + Constantes.NOMBRE_CARPETA_SUBASTAPP + File.separator + NOMBRE_BASE_DATOS;
        operadorDatos = new OperadorDatos(context, rutaBaseDatos, VERSION_BASE_DATOS, new AdministradorBaseDatos());
    }

    public boolean tieneRegistros() {
        return this.operadorDatos.numeroRegistros() > 0;
    }
}