package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;

public class Converte {
    public static ContentValues generoToContentValues(Genero genero) {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelGenero.CAMPO_DESCRICAO, genero.getDescricao());

        return valores;
    }

    public static Genero contentValuesToGenero(ContentValues valores) {
        Genero genero = new Genero();

        genero.setId(valores.getAsLong(BdTabelGenero._ID));
        genero.setDescricao(valores.getAsString(BdTabelGenero.CAMPO_DESCRICAO));

        return genero;
    }

    public static ContentValues distritoToContentValues(Distrito distrito) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableDistrito.CAMPO_DESCRICAO, distrito.getDescricao());

        return valores;
    }
    public static Distrito contentValuesToDistrito(ContentValues valores) {
        Distrito distrito = new Distrito();

        distrito.setId(valores.getAsLong(BdTableDistrito._ID));
        distrito.setDescricao(valores.getAsString(BdTableDistrito.CAMPO_DESCRICAO));

        return distrito;
    }

    public static ContentValues estadoToContentValues(Estado estado) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableEstado.CAMPO_DESCRICAO, estado.getDescricao());

        return valores;
    }
    public static Estado contentValuesToEstado(ContentValues valores) {
        Estado estado = new Estado();

        estado.setId(valores.getAsLong(BdTableEstado._ID));
        estado.setDescricao(valores.getAsString(BdTableEstado.CAMPO_DESCRICAO));

        return estado;
    }

    public static ContentValues pacienteToContentValues(Paciente paciente) {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelPaciente.CAMPO_NOME, paciente.getNomePaciente());
        valores.put(BdTabelPaciente.CAMPO_ID_ANO_NASCIMENTO, paciente.getIdAno());
        valores.put(BdTabelPaciente.CAMPO_ID_GENERO, paciente.getIdGenero());
        valores.put(BdTabelPaciente.CAMPO_ID_DISTRITO, paciente.getIdDistrito());
        valores.put(BdTabelPaciente.CAMPO_ID_ESTADO, paciente.getIdDistrito());

        return valores;
    }

    public static Paciente contentValuesToPaciente(ContentValues valores) {
        Paciente paciente = new Paciente();

        paciente.setId(valores.getAsLong(BdTabelPaciente._ID));
        paciente.setNomePaciente(valores.getAsString(BdTabelPaciente.CAMPO_NOME));
        paciente.setIdAno(valores.getAsLong(BdTabelPaciente.CAMPO_ID_ANO_NASCIMENTO));
        paciente.setAno(valores.getAsString(BdTabelPaciente.CAMPO_ANO_NASCIMENTO));
        paciente.setIdGenero(valores.getAsLong(BdTabelPaciente.CAMPO_ID_GENERO));
        paciente.setGenero(valores.getAsString(BdTabelPaciente.CAMPO_GENERO));
        paciente.setIdDistrito(valores.getAsLong(BdTabelPaciente.CAMPO_ID_DISTRITO));
        paciente.setDistrito(valores.getAsString(BdTabelPaciente.CAMPO_DISTRITO));
        paciente.setIdEstado(valores.getAsLong(BdTabelPaciente.CAMPO_ID_ESTADO));
        paciente.setEstado(valores.getAsString(BdTabelPaciente.CAMPO_ESTADO));

        return paciente;
    }

    public static Paciente cursorToPaciente(Cursor cursor) {
        Paciente paciente = new Paciente();

        paciente.setId(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente._ID)));
        paciente.setNomePaciente(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_NOME)));
        paciente.setIdAno(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ID_ANO_NASCIMENTO)));
        paciente.setAno(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ANO_NASCIMENTO)));
        paciente.setIdGenero(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ID_GENERO)));
        paciente.setGenero(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_GENERO)));
        paciente.setIdDistrito(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ID_DISTRITO)));
        paciente.setDistrito(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_DISTRITO)));
        paciente.setIdEstado(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ID_ESTADO)));
        paciente.setEstado(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ESTADO)));

        return paciente;
    }
}
