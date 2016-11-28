package com.example.santiagolopezgarcia.subastapp.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.santiagolopezgarcia.subastapp.R;
import com.example.santiagolopezgarcia.subastapp.modelonegocio.Categoria;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/11/16.
 */

public class CategoriaAdapter extends ArrayAdapter<Categoria> {

    private boolean esNegrilla;
    private Context context;
    private String descripcionValorPordefecto;
    private List<Categoria> listaCategoria;
    private Categoria categoria;
    private boolean ajustarTamaño = true;

    public CategoriaAdapter(Context context, List<Categoria> listaCategoria) {
        super(context, 0, listaCategoria);
        this.context = context;
        this.descripcionValorPordefecto = "";
        this.listaCategoria = listaCategoria;
    }

    public CategoriaAdapter(Context context, List<Categoria> listaCategoria, String descripcionValorPordefecto) {
        super(context, 0, listaCategoria);
        this.context = context;
        this.descripcionValorPordefecto = descripcionValorPordefecto;
        adicionarValorPorDefecto(listaCategoria);
        this.listaCategoria = listaCategoria;
    }

    private void adicionarValorPorDefecto(List<Categoria> listaCategoria) {
        categoria = new Categoria();
        categoria.setNombre(descripcionValorPordefecto);
        listaCategoria.add(0, categoria);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }

    @NonNull
    private View getViewItem(int position, View convertView, ViewGroup parent) {
        CategoriaViewHolder categoriaViewHolder;
        final Categoria categoria = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
            categoriaViewHolder = new CategoriaViewHolder();
            categoriaViewHolder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
            categoriaViewHolder.tvNombre.setTypeface(null, (esNegrilla) ? Typeface.BOLD : Typeface.NORMAL);
            convertView.setTag(categoriaViewHolder);
        } else {
            categoriaViewHolder = (CategoriaViewHolder) convertView.getTag();
        }
        categoriaViewHolder.tvNombre.setSingleLine(ajustarTamaño);
        categoriaViewHolder.tvNombre.setText(categoria.getNombre());
        if (!this.descripcionValorPordefecto.isEmpty()) {
            if (position == 0) {
                if (ajustarTamaño) {
                    categoriaViewHolder.tvNombre.setText(descripcionValorPordefecto);
                } else {
                    categoriaViewHolder.tvNombre.setText("Ninguna");
                }
                categoriaViewHolder.tvNombre.setTextColor(context.getResources().getColor(R.color.grisclaro));
            } else {
                categoriaViewHolder.tvNombre.setTextColor(context.getResources().getColor(R.color.negro));
            }
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = getViewItem(position, convertView, parent);
        return convertView;
    }


    static class CategoriaViewHolder {
        TextView tvNombre;
    }
}