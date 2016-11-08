package com.example.santiagolopezgarcia.subastapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.santiagolopezgarcia.subastapp.datos.DaoBase;
import com.example.santiagolopezgarcia.subastapp.helpers.DateHelper;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Subasta;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class SubastaDao extends DaoBase implements SubastaRepositorio {

    static final String NombreTabla = "tbl_subasta";

    public SubastaDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Subasta cargar(String codigo) throws ParseException {
        Subasta subasta = new Subasta();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaSubasta.CODIGO
                + " = '" + codigo + "'");
        if (cursor.moveToFirst()) {
            subasta = convertirCursorAObjeto(cursor);
        }
        cursor.close();
        return subasta;
    }

    @Override
    public List<Subasta> cargar() {
        List<Subasta> lista = new ArrayList<>();
        Cursor cursor = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " ORDER BY " + ColumnaSubasta.CODIGO);
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
    public boolean guardar(Subasta subasta) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(subasta);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(Subasta subasta) {
        try {
            ContentValues datos = convertirObjetoAContentValues(subasta);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaSubasta.CODIGO + " = ? "
                , new String[]{subasta.getCodigo()}) > 0;
    }

    @Override
    public boolean actualizar(Subasta subasta) {
        ContentValues content = null;
        try {
            content = convertirObjetoAContentValues(subasta);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(content, ColumnaSubasta.CODIGO + " = '"
                + subasta.getCodigo() + "'");
    }

    @Override
    public boolean guardar(List<Subasta> lista) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(lista.size());
        for (Subasta subasta : lista) {
            listaContentValues.add(convertirObjetoAContentValues(subasta));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private Subasta convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Subasta subasta = new Subasta();
        subasta.setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaSubasta.CODIGO)));
        subasta.setFechaInicial(DateHelper.convertirStringADate(cursor.getString(
                cursor.getColumnIndex(ColumnaSubasta.FECHAINICIAL)), DateHelper.TipoFormato.yyyyMMddHHmmss));
        subasta.setFechaInicial(DateHelper.convertirStringADate(cursor.getString(
                cursor.getColumnIndex(ColumnaSubasta.FECHAFINAL)), DateHelper.TipoFormato.yyyyMMddHHmmss));
        subasta.setPrecioInicial(Double.parseDouble(cursor.getString(cursor.getColumnIndex(ColumnaSubasta.PRECIOINICIAL))));
        subasta.setPrecioFinal(Double.parseDouble(cursor.getString(cursor.getColumnIndex(ColumnaSubasta.PRECIOFINAL))));
        subasta.getProducto().setCodigo(cursor.getString(cursor.getColumnIndex(ColumnaSubasta.PRODUCTO)));
        subasta.setEstado(cursor.getString(cursor.getColumnIndex(ColumnaSubasta.ESTADO)));
        return subasta;
    }

    private ContentValues convertirObjetoAContentValues(Subasta subasta) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!subasta.getCodigo().isEmpty()) {
            contentValues.put(ColumnaSubasta.CODIGO, subasta.getCodigo());
        }
        if (subasta.getFechaInicial() != null) {
            contentValues.put(ColumnaSubasta.FECHAINICIAL, DateHelper.convertirDateAString(
                    subasta.getFechaInicial(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        }
        if (subasta.getFechaInicial() != null) {
            contentValues.put(ColumnaSubasta.FECHAFINAL, DateHelper.convertirDateAString(
                    subasta.getFechaFinal(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        }
        contentValues.put(ColumnaSubasta.PRECIOINICIAL, subasta.getPrecioInicial());
        contentValues.put(ColumnaSubasta.PRECIOFINAL, subasta.getPrecioFinal());
        contentValues.put(ColumnaSubasta.PRODUCTO, subasta.getProducto().getCodigo());
        contentValues.put(ColumnaSubasta.ESTADO, subasta.getEstado());
        return contentValues;
    }

    public static class ColumnaSubasta {

        public static final String CODIGO = "codigo";
        public static final String FECHAINICIAL = "fechainicial";
        public static final String FECHAFINAL = "fechafinal";
        public static final String PRECIOINICIAL = "precioinicial";
        public static final String PRECIOFINAL = "preciofinal";
        public static final String PRODUCTO = "producto";
        public static final String ESTADO = "estado";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaSubasta.CODIGO + " " + STRING_TYPE + " not null," +
                    ColumnaSubasta.FECHAINICIAL + " " + STRING_TYPE + " not null," +
                    ColumnaSubasta.FECHAFINAL + " " + STRING_TYPE + " not null," +
                    ColumnaSubasta.PRECIOINICIAL + " " + STRING_TYPE + " not null," +
                    ColumnaSubasta.PRECIOFINAL + " " + STRING_TYPE + " not null," +
                    ColumnaSubasta.PRODUCTO + " " + STRING_TYPE + " not null," +
                    ColumnaSubasta.ESTADO + " " + STRING_TYPE + " not null," +
                    " primary key (" + ColumnaSubasta.CODIGO + ")" +
                    " FOREIGN KEY (" + ColumnaSubasta.PRODUCTO + ") REFERENCES " +
                    ProductoDao.NombreTabla + "( " + ProductoDao.ColumnaProducto.CODIGO + ") ON DELETE CASCADE)";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}