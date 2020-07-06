package com.example.coronavirus;

public class Distrito {
    private long id = -1;
    private String nome_distrito;
    private Integer nr_infetados = 0;
    private Integer nr_recuperados = 0;
    private Integer nr_mortos = 0;
    private Integer nr_habitantes = 0;

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

    public Integer getNr_habitantes() {
        return nr_habitantes;
    }

    public void setNr_habitantes(Integer nr_habitantes) {
        this.nr_habitantes = nr_habitantes;
    }
}
