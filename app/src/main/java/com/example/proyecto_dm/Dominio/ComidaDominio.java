package com.example.proyecto_dm.Dominio;

public class ComidaDominio {
    private String titulo;
    private String foto;
    private String descripcion;
    private double cuota;
    private int numeroenCarro;

    public ComidaDominio(String titulo, String foto, String descripcion, double cuota) {
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
        this.cuota = cuota;
    }

    public ComidaDominio(String titulo, String foto, String descripcion, double cuota, int numeroenCarro) {
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
        this.cuota = cuota;
        this.numeroenCarro = numeroenCarro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public int getNumeroenCarro() {
        return numeroenCarro;
    }

    public void setNumeroenCarro(int numeroenCarro) {
        this.numeroenCarro = numeroenCarro;
    }
}
