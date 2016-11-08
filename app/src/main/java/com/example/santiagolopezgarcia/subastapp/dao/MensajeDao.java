package com.example.santiagolopezgarcia.subastapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.santiagolopezgarcia.subastapp.datos.DaoBase;
import com.example.santiagolopezgarcia.subastapp.helpers.DateHelper;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Mensaje;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 31/10/16.
 */

public class MensajeDao extends DaoBase implements MensajeRepositorio {

    static final String NombreTabla = "tbl_mensaje";

    public MensajeDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Mensaje cargar(String codigo) throws ParseException {
        Mensaje mensaje = new Mensaje();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaMensaje.CODIGO
                + " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            mensaje = convertirCursorAObjeto(cursor);
        }
        cursor.close();
        return mensaje;
    }

    @Override
    public List<Mensaje> cargar() {
        List<Mensaje> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " ORDER BY " + ColumnaMensaje.CODIGO);
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
    public boolean guardar(Mensaje mensaje) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(mensaje);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(Mensaje mensaje) {
        try {
            ContentValues datos = convertirObjetoAContentValues(mensaje);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaMensaje.CODIGO + " = ? "
                , new String[]{mensaje.getCodigo()}) > 0;
    }

    @Override
    public boolean actualizar(Mensaje mensaje) {
        ContentValues content = null;
        try {
            content = convertirObjetoAContentValues(mensaje);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(content, ColumnaMensaje.CODIGO + " = '"
                + mensaje.getCodigo() + "'");
    }

    @Override
    public boolean guardar(List<Mensaje> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Mensaje mensaje : lista) {
            listaContentValues.add(convertirObjetoAContentValues(mensaje));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private Mensaje convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Mensaje mensaje = new Mensaje();
        mensaje.setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaMensaje.CODIGO)));
        mensaje.getEmisor().setIdentificacion(cursor.getString(cursor.getColumnIndex(ColumnaMensaje.EMISOR)));
        mensaje.getReceptor().setIdentificacion(cursor.getString(cursor.getColumnIndex(ColumnaMensaje.RECEPTOR)));
        mensaje.setContenido(cursor.getString(cursor.getColumnIndex(ColumnaMensaje.CONTENIDO)));
        mensaje.setEstado(cursor.getString(cursor.getColumnIndex(ColumnaMensaje.ESTADO)));
        mensaje.setFecha(DateHelper.convertirStringADate(cursor.getString(
                cursor.getColumnIndex(ColumnaMensaje.FECHA)), DateHelper.TipoFormato.yyyyMMddHHmmss));
        return mensaje;
    }

    private ContentValues convertirObjetoAContentValues(Mensaje mensaje) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!mensaje.getCodigo().isEmpty()) {
            contentValues.put(ColumnaMensaje.CODIGO, mensaje.getCodigo());
        }
        if (!mensaje.getEmisor().getIdentificacion().isEmpty()) {
            contentValues.put(ColumnaMensaje.EMISOR, mensaje.getEmisor().getIdentificacion());
        }
        if (!mensaje.getReceptor().getIdentificacion().isEmpty()) {
            contentValues.put(ColumnaMensaje.RECEPTOR, mensaje.getReceptor().getIdentificacion());
        }
        if (!mensaje.getContenido().isEmpty()) {
            contentValues.put(ColumnaMensaje.CONTENIDO, mensaje.getContenido());
        }
        if (!mensaje.getEstado().isEmpty()) {
            contentValues.put(ColumnaMensaje.ESTADO, mensaje.getEstado());
        }
        if (mensaje.getFecha() != null) {
            contentValues.put(ColumnaMensaje.FECHA, DateHelper.convertirDateAString(
                    mensaje.getFecha(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        }
        return contentValues;
    }

    public static class ColumnaMensaje {

        public static final String CODIGO = "codigo";
        public static final String EMISOR = "emisor";
        public static final String RECEPTOR = "receptor";
        public static final String CONTENIDO = "contenido";
        public static final String ESTADO = "estado";
        public static final String FECHA = "fecha";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaMensaje.CODIGO + " " + STRING_TYPE + " not null," +
                    ColumnaMensaje.EMISOR + " " + STRING_TYPE + " not null," +
                    ColumnaMensaje.RECEPTOR + " " + STRING_TYPE + " not null," +
                    ColumnaMensaje.CONTENIDO + " " + STRING_TYPE + " not null," +
                    ColumnaMensaje.ESTADO + " " + STRING_TYPE + " not null," +
                    ColumnaMensaje.FECHA + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaMensaje.CODIGO + ")" +
                    " FOREIGN KEY (" + ColumnaMensaje.EMISOR + ") REFERENCES " +
                    UsuarioDao.NombreTabla + "( " + UsuarioDao.ColumnaUsuario.IDENTIFICACION + ")," +
                    " FOREIGN KEY (" + ColumnaMensaje.RECEPTOR + ") REFERENCES " +
                    UsuarioDao.NombreTabla + "( " + UsuarioDao.ColumnaUsuario.IDENTIFICACION +") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
