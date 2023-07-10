package com.example.evaluacion3.modelo;

public class Automovil {

    private long codigo;
    private String modelo;
    private String patente;
    private String precio;

    public Automovil(){}
    public Automovil(long codigo, String modelo, String patente, String precio){
        this.codigo= codigo;
        this.modelo= modelo;
        this.patente=patente;
        this.precio=precio;

    }

    public long getCodigo()
    {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPatente() {
        return patente;
    }
    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
