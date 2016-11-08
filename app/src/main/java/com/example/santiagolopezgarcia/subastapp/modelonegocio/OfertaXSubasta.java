package com.example.santiagolopezgarcia.subastapp.modelonegocio;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public class OfertaXSubasta {

    private String codigo;
    private Usuario usuario;
    private Subasta subasta;
    private double valor;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Subasta getSubasta() {
        return subasta;
    }

    public void setSubasta(Subasta subasta) {
        this.subasta = subasta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
