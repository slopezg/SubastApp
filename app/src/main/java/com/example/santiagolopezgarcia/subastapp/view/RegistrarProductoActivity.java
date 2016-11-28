package com.example.santiagolopezgarcia.subastapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.santiagolopezgarcia.subastapp.R;
import com.example.santiagolopezgarcia.subastapp.RegistrarProductoPresenter;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Categoria;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Producto;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by santiagolopezgarcia on 31/10/16.
 */

public class RegistrarProductoActivity extends AppCompatActivity implements IRegistrarProductoView{

    @InjectView(R.id.etCodigoProducto)
    EditText etCodigo;
    @InjectView(R.id.etNombreProducto)
    EditText etNombre;
    @InjectView(R.id.etDescripcion)
    EditText etDescripcion;
    @InjectView(R.id.ivImagen)
    ImageView ivImagen;
    @InjectView(R.id.spCategoria)
    Spinner spCategoria;
    RegistrarProductoPresenter presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);
        presentador.iniciar();
    }

    @OnClick(R.id.btnGuardar)
    public void guardar(){
        Producto producto = new Producto();
        producto.setCodigo(etCodigo.getText().toString());
        producto.setNombre(etNombre.getText().toString());
        producto.setCategoria((Categoria) spCategoria.getSelectedItem());
        producto.setDescripcion(etDescripcion.getText().toString());
        presentador.guardar(producto);
    }

    @Override
    public void mostrarCategorias(List<Categoria> categorias) {
        spCategoria.setAdapter(new CategoriaAdapter(this, categorias, getString(R.string.seleccione_categoria)));
    }
}
