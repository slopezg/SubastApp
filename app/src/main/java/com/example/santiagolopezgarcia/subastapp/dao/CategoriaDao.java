package com.example.santiagolopezgarcia.subastapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.santiagolopezgarcia.subastapp.datos.DaoBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Categoria;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class CategoriaDao extends DaoBase implements CategoriaRepositorio {

    static final String NombreTabla = "tbl_categoria";

    public CategoriaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Categoria cargar(String codigo) throws ParseException {
        Categoria categoria = new Categoria();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaCategoria.CODIGO
                + " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            categoria = convertirCursorAObjeto(cursor);
        }
        cursor.close();
        return categoria;
    }

    @Override
    public List<Categoria> cargar() {
        List<Categoria> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " ORDER BY " + ColumnaCategoria.CODIGO);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    lista.add(convertirCursorAObjeto(cursor));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return lista;
    }


    @Override
    public boolean guardar(Categoria categoria) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(categoria);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(Categoria categoria) {
        try {
            ContentValues datos = convertirObjetoAContentValues(categoria);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaCategoria.CODIGO + " = ? "
                , new String[]{categoria.getCodigo()}) > 0;
    }

    @Override
    public boolean actualizar(Categoria categoria) {
        ContentValues content = null;
        try {
            content = convertirObjetoAContentValues(categoria);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(content, ColumnaCategoria.CODIGO + " = '"
                + categoria.getCodigo() + "'");
    }

    @Override
    public boolean guardar(List<Categoria> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Categoria categoria : lista) {
            listaContentValues.add(convertirObjetoAContentValues(categoria));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private Categoria convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Categoria categoria = new Categoria();
        categoria.setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaCategoria.CODIGO)));
        categoria.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaCategoria.NOMBRE)));
        return categoria;
    }

    private ContentValues convertirObjetoAContentValues(Categoria categoria) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!categoria.getCodigo().isEmpty()) {
            contentValues.put(ColumnaCategoria.CODIGO, categoria.getCodigo());
        }
        if (!categoria.getNombre().isEmpty()) {
            contentValues.put(ColumnaCategoria.NOMBRE, categoria.getNombre());
        }
        return contentValues;
    }

    public static class ColumnaCategoria {

        public static final String CODIGO = "codigo";
        public static final String NOMBRE = "nombre";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaCategoria.CODIGO + " " + STRING_TYPE + " not null," +
                    ColumnaCategoria.NOMBRE + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaCategoria.CODIGO + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}