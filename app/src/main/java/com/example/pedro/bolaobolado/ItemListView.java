package com.example.pedro.bolaobolado;

public class ItemListView {

    private String idDados;
    private String titulo;
    private String data;

    public ItemListView(String idDados, String titulo, String data){
        this.idDados = idDados;
        this.titulo = titulo;
        this.data = data;
    }

    public String getIdDados(){
        return idDados;
    }

    public void setIdDados(String idDados){
        this.titulo = idDados;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.titulo = data;
    }

}
