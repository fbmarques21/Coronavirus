package com.example.coronavirus;

public class Suspeito {
    private long id = -1;
    private String nomeSuspeito;
    private String Ano;
    private String genero;
    private long idDistrito;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeSuspeito() {
        return nomeSuspeito;
    }

    public void setNomeSuspeito(String nomeSuspeito) {
        this.nomeSuspeito = nomeSuspeito;
    }

    public String getAno() {
        return Ano;
    }

    public void setAno(String ano) {
        Ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(long idDistrito) {
        this.idDistrito = idDistrito;
    }
}
