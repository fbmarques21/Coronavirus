package com.example.coronavirus;

public class Paciente {
    private long id = -1;
    private String nome_paciente;
    private String ano_nascimento_paciente;
    private String genero_paciente;
    private long id_distrito;
    private String estado_paciente;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_paciente() {
        return nome_paciente;
    }

    public void setNome_paciente(String nome_paciente) {
        this.nome_paciente = nome_paciente;
    }

    public String getAno_nascimento_paciente() {
        return ano_nascimento_paciente;
    }

    public void setAno_nascimento_paciente(String ano_nascimento_paciente) {
        this.ano_nascimento_paciente = ano_nascimento_paciente;
    }

    public String getGenero_paciente() {
        return genero_paciente;
    }

    public void setGenero_paciente(String genero_paciente) {
        this.genero_paciente = genero_paciente;
    }

    public long getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(long id_distrito) {
        this.id_distrito = id_distrito;
    }

    public String getEstado_paciente() {
        return estado_paciente;
    }

    public void setEstado_paciente(String estado_paciente) {
        this.estado_paciente = estado_paciente;
    }
}
