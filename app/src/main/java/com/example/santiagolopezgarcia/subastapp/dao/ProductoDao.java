package com.example.santiagolopezgarcia.subastapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.santiagolopezgarcia.subastapp.datos.DaoBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Producto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class ProductoDao extends DaoBase implements ProductoRepositorio {

    static final String NombreTabla = "tbl_producto";

    public ProductoDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Producto cargar(String codigo) throws ParseException {
        Producto producto = new Producto();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaProducto.CODIGO
                + " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            producto = convertirCursorAObjeto(cursor);
        }
        cursor.close();
        return producto;
    }

    @Override
    public List<Producto> cargar() {
        List<Producto> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " ORDER BY " + ColumnaProducto.CODIGO);
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
    public boolean guardar(Producto producto) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(producto);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(Producto producto) {
        try {
            ContentValues datos = convertirObjetoAContentValues(producto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaProducto.CODIGO + " = ? "
                , new String[]{producto.getCodigo()}) > 0;
    }

    @Override
    public boolean actualizar(Producto producto) {
        ContentValues content = null;
        try {
            content = convertirObjetoAContentValues(producto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(content, ColumnaProducto.CODIGO + " = '"
                + producto.getCodigo() + "'");
    }

    @Override
    public boolean guardar(List<Producto> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Producto producto : lista) {
            listaContentValues.add(convertirObjetoAContentValues(producto));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private Producto convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Producto producto = new Producto();
        producto.setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaProducto.CODIGO)));
        producto.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaProducto.NOMBRE)));
        producto.getCategoria().setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaProducto.CATEGORIA)));
        producto.getUsuario().setIdentificacion(cursor.getString(cursor.getColumnIndex(ColumnaProducto.USUARIO)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaProducto.IMAGEN))) {
            producto.setImagen(cursor.getString(cursor.getColumnIndex(ColumnaProducto.IMAGEN)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaProducto.DESCRIPCION))) {
            producto.setDescripcion(cursor.getString(cursor.getColumnIndex(ColumnaProducto.DESCRIPCION)));
        }
        return producto;
    }

    private ContentValues convertirObjetoAContentValues(Producto producto) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!producto.getCodigo().isEmpty()) {
            contentValues.put(ColumnaProducto.CODIGO, producto.getCodigo());
        }
        if (!producto.getNombre().isEmpty()) {
            contentValues.put(ColumnaProducto.NOMBRE, producto.getNombre());
        }
        if (!producto.getCategoria().getCodigo().isEmpty()) {
            contentValues.put(ColumnaProducto.CATEGORIA, producto.getCategoria().getCodigo());
        }
        if (!producto.getUsuario().getIdentificacion().isEmpty()) {
            contentValues.put(ColumnaProducto.USUARIO, producto.getUsuario().getIdentificacion());
        }
        if (producto.getImagen() != null) {
            contentValues.put(ColumnaProducto.IMAGEN, producto.getImagen());
        }
        if (producto.getDescripcion() != null) {
            contentValues.put(ColumnaProducto.DESCRIPCION, producto.getDescripcion());
        }
        return contentValues;
    }

    public static class ColumnaProducto {

        public static final String CODIGO = "codigo";
        public static final String NOMBRE = "nombre";
        public static final String CATEGORIA = "categoria";
        public static final String USUARIO = "usuario";
        public static final String IMAGEN = "imagen";
        public static final String DESCRIPCION = "descripcion";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaProducto.CODIGO + " " + STRING_TYPE + " not null," +
                    ColumnaProducto.NOMBRE + " " + STRING_TYPE + " null," +
                    ColumnaProducto.CATEGORIA + " " + STRING_TYPE + " not null," +
                    ColumnaProducto.USUARIO + " " + STRING_TYPE + " not null," +
                    ColumnaProducto.IMAGEN + " " + STRING_TYPE + " null," +
                    ColumnaProducto.DESCRIPCION + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaProducto.CODIGO + ")" +
                    " FOREIGN KEY (" + ColumnaProducto.CATEGORIA + ") REFERENCES " +
                    CategoriaDao.NombreTabla + "( " + CategoriaDao.ColumnaCategoria.CODIGO + ")," +
                    " FOREIGN KEY (" + ColumnaProducto.USUARIO + ") REFERENCES " +
                    UsuarioDao.NombreTabla + "( " + UsuarioDao.ColumnaUsuario.IDENTIFICACION +") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}