package com.example.coronavirus;

public class Paciente {
    private long id = -1;
    private String nomePaciente;
    private String Ano;
    private String genero;
    private long idDistrito;
    private String estado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
