package com.example.santiagolopezgarcia.subastapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.santiagolopezgarcia.subastapp.datos.DaoBase;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Usuario;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class UsuarioDao extends DaoBase implements UsuarioRepositorio {

    static final String NombreTabla = "tbl_usuario";

    public UsuarioDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Usuario cargar(String identificacion) throws ParseException {
        Usuario usuario = new Usuario();
        Cursor cursorUsuarios = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " WHERE " + ColumnaUsuario.IDENTIFICACION
                + " = '" + identificacion + "'");
        if (cursorUsuarios.moveToFirst()) {
            usuario = convertirCursorAObjeto(cursorUsuarios);
        }
        cursorUsuarios.close();
        return usuario;
    }

    @Override
    public List<Usuario> cargar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursorUsuarios = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla +
                " ORDER BY " + ColumnaUsuario.IDENTIFICACION);
        if (cursorUsuarios.moveToFirst()) {
            while (!cursorUsuarios.isAfterLast()) {
                try {
                    listaUsuarios.add(convertirCursorAObjeto(cursorUsuarios));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cursorUsuarios.moveToNext();
            }
        }
        cursorUsuarios.close();
        return listaUsuarios;
    }


    @Override
    public boolean guardar(Usuario usuario) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(usuario);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(Usuario usuario) {
        try {
            ContentValues datos = convertirObjetoAContentValues(usuario);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaUsuario.IDENTIFICACION + " = ? "
                , new String[]{usuario.getIdentificacion()}) > 0;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        ContentValues contentUsuarios = null;
        try {
            contentUsuarios = convertirObjetoAContentValues(usuario);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(contentUsuarios, ColumnaUsuario.IDENTIFICACION + " = '"
                + usuario.getIdentificacion() + "'");
    }

    @Override
    public boolean guardar(List<Usuario> listaUsuarios) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(listaUsuarios.size());
        for (Usuario usuario : listaUsuarios) {
            listaContentValues.add(convertirObjetoAContentValues(usuario));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private Usuario convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Usuario usuario = new Usuario();
        usuario.setIdentificacion(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.IDENTIFICACION)));
        usuario.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.NOMBRE)));
        usuario.setApellidos(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.APELLIDOS)));
        usuario.setContrasena(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CONTRASENA)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaUsuario.CELULAR))) {
            usuario.setCelular(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CELULAR)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaUsuario.CORREO))) {
            usuario.setCorreo(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CORREO)));
        }
        return usuario;
    }

    private ContentValues convertirObjetoAContentValues(Usuario usuario) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!usuario.getIdentificacion().isEmpty()) {
            contentValues.put(ColumnaUsuario.IDENTIFICACION, usuario.getIdentificacion());
        }
        if (!usuario.getNombre().isEmpty()) {
            contentValues.put(ColumnaUsuario.NOMBRE, usuario.getNombre());
        }
        if (!usuario.getApellidos().isEmpty()) {
            contentValues.put(ColumnaUsuario.APELLIDOS, usuario.getApellidos());
        }
        if (!usuario.getContrasena().isEmpty()) {
            contentValues.put(ColumnaUsuario.CONTRASENA, usuario.getContrasena());
        }
        if (!usuario.getCelular().isEmpty()) {
            contentValues.put(ColumnaUsuario.CELULAR, usuario.getCelular());
        }
        if (usuario.getCorreo() != null) {
            contentValues.put(ColumnaUsuario.CORREO, usuario.getCorreo());
        }
        return contentValues;
    }

    public static class ColumnaUsuario {

        public static final String IDENTIFICACION = "identificacion";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDOS = "apellidos";
        public static final String CONTRASENA = "contrasena";
        public static final String CELULAR = "celular";
        public static final String CORREO = "correo";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaUsuario.IDENTIFICACION + " " + STRING_TYPE + " not null," +
                    ColumnaUsuario.NOMBRE + " " + STRING_TYPE + " null," +
                    ColumnaUsuario.APELLIDOS + " " + STRING_TYPE + " not null," +
                    ColumnaUsuario.CONTRASENA + " " + STRING_TYPE + " not null," +
                    ColumnaUsuario.CELULAR + " " + STRING_TYPE + " null," +
                    ColumnaUsuario.CORREO + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaUsuario.IDENTIFICACION + "))";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
