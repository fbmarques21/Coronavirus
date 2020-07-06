package com.example.coronavirus;

public class Distrito {
    private long id = -1;
    private String nome_distrito;
    private Integer nr_infetados;
    private Integer nr_recuperados;
    private Integer nr_mortos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_distrito() {
        return nome_distrito;
    }

    public void setNome_distrito(String nome_distrito) {
        this.nome_distrito = nome_distrito;
    }

    public Integer getNr_infetados() {
        return nr_infetados;
    }

    public void setNr_infetados(Integer nr_infetados) {
        this.nr_infetados = nr_infetados;
    }

    public Integer getNr_recuperados() {
        return nr_recuperados;
    }

    public void setNr_recuperados(Integer nr_recuperados) {
        this.nr_recuperados = nr_recuperados;
    }

    public Integer getNr_mortos() {
        return nr_mortos;
    }

    public void setNr_mortos(Integer nr_mortos) {
        this.nr_mortos = nr_mortos;
    }
}
