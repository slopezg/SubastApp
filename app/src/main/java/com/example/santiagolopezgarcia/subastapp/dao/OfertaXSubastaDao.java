package com.example.santiagolopezgarcia.subastapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.santiagolopezgarcia.subastapp.datos.DaoBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.OfertaXSubasta;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class OfertaXSubastaDao extends DaoBase implements OfertaXSubastaRepositorio {

    static final String NombreTabla = "tbl_ofertaxsubasta";

    public OfertaXSubastaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public OfertaXSubasta cargar(String codigo) throws ParseException {
        OfertaXSubasta producto = new OfertaXSubasta();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaOfertaXSubasta.CODIGO
                + " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            producto = convertirCursorAObjeto(cursor);
        }
        cursor.close();
        return producto;
    }

    @Override
    public List<OfertaXSubasta> cargar() {
        List<OfertaXSubasta> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " ORDER BY " + ColumnaOfertaXSubasta.CODIGO);
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
    public boolean guardar(OfertaXSubasta ofertaXSubasta) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(ofertaXSubasta);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(OfertaXSubasta ofertaXSubasta) {
        try {
            ContentValues datos = convertirObjetoAContentValues(ofertaXSubasta);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaOfertaXSubasta.CODIGO + " = ? "
                , new String[]{ofertaXSubasta.getCodigo()}) > 0;
    }

    @Override
    public boolean actualizar(OfertaXSubasta producto) {
        ContentValues content = null;
        try {
            content = convertirObjetoAContentValues(producto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(content, ColumnaOfertaXSubasta.CODIGO + " = '"
                + producto.getCodigo() + "'");
    }

    @Override
    public boolean guardar(List<OfertaXSubasta> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (OfertaXSubasta ofertaXSubasta : lista) {
            listaContentValues.add(convertirObjetoAContentValues(ofertaXSubasta));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private OfertaXSubasta convertirCursorAObjeto(Cursor cursor) throws ParseException {
        OfertaXSubasta ofertaXSubasta = new OfertaXSubasta();
        ofertaXSubasta.setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaOfertaXSubasta.CODIGO)));
        ofertaXSubasta.getUsuario().setIdentificacion(cursor.getString(cursor.getColumnIndex(ColumnaOfertaXSubasta.USUARIO)));
        ofertaXSubasta.getSubasta().setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaOfertaXSubasta.SUBASTA)));
        ofertaXSubasta.setValor(Double.parseDouble(cursor.getString(cursor.getColumnIndex(ColumnaOfertaXSubasta.VALOR))));

        return ofertaXSubasta;
    }

    private ContentValues convertirObjetoAContentValues(OfertaXSubasta ofertaXSubasta) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!ofertaXSubasta.getCodigo().isEmpty()) {
            contentValues.put(ColumnaOfertaXSubasta.CODIGO, ofertaXSubasta.getCodigo());
        }
        if (!ofertaXSubasta.getUsuario().getIdentificacion().isEmpty()) {
            contentValues.put(ColumnaOfertaXSubasta.USUARIO, ofertaXSubasta.getUsuario().getIdentificacion());
        }
        if (!ofertaXSubasta.getSubasta().getCodigo().isEmpty()) {
            contentValues.put(ColumnaOfertaXSubasta.SUBASTA, ofertaXSubasta.getSubasta().getCodigo());
        }
        contentValues.put(ColumnaOfertaXSubasta.VALOR, ofertaXSubasta.getValor());
        return contentValues;
    }

    public static class ColumnaOfertaXSubasta {

        public static final String CODIGO = "codigo";
        public static final String USUARIO = "usuario";
        public static final String SUBASTA = "subasta";
        public static final String VALOR = "valor";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaOfertaXSubasta.CODIGO + " " + STRING_TYPE + " not null," +
                    ColumnaOfertaXSubasta.USUARIO + " " + STRING_TYPE + " not null," +
                    ColumnaOfertaXSubasta.SUBASTA + " " + STRING_TYPE + " not null," +
                    ColumnaOfertaXSubasta.VALOR + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaOfertaXSubasta.CODIGO + ")" +
                    " FOREIGN KEY (" + ColumnaOfertaXSubasta.USUARIO + ") REFERENCES " +
                    UsuarioDao.NombreTabla + "( " + UsuarioDao.ColumnaUsuario.IDENTIFICACION + ")," +
                    " FOREIGN KEY (" + ColumnaOfertaXSubasta.SUBASTA + ") REFERENCES " +
                    SubastaDao.NombreTabla + "( " + SubastaDao.ColumnaSubasta.CODIGO +") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}