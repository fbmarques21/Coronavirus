package com.example.coronavirus;

public class Paciente {
    private long id = -1;
    private String nomePaciente;
    private long idAno = -1;
    private String Ano;
    private long idGenero = -1;
    private String genero;
    private long idDistrito = -1;
    private String Distrito;
    private long idEstado = -1;
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

    public long getIdAno() {
        return idAno;
    }

    public void setIdAno(long idAno) {
        this.idAno = idAno;
    }

    public String getAno() {
        return Ano;
    }

    public void setAno(String ano) {
        Ano = ano;
    }

    public long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(long idGenero) {
        this.idGenero = idGenero;
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

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
