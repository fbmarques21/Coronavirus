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

    public static ContentValues pacienteToContentValues(Paciente paciente) {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelNomePaciente.CAMPO_NOME, paciente.getNomePaciente());
        valores.put(BdTabelNomePaciente.CAMPO_ID_ANO_NASCIMENTO, paciente.getIdAno());
        valores.put(BdTabelNomePaciente.CAMPO_ID_GENERO, paciente.getIdGenero());
        valores.put(BdTabelNomePaciente.CAMPO_ID_DISTRITO, paciente.getIdDistrito());
        valores.put(BdTabelNomePaciente.CAMPO_ID_ESTADO, paciente.getIdDistrito());

        return valores;
    }

    public static Paciente contentValuesToPaciente(ContentValues valores) {
        Paciente paciente = new Paciente();

        paciente.setId(valores.getAsLong(BdTabelNomePaciente._ID));
        paciente.setNomePaciente(valores.getAsString(BdTabelNomePaciente.CAMPO_NOME));
        paciente.setIdAno(valores.getAsLong(BdTabelNomePaciente.CAMPO_ID_ANO_NASCIMENTO));
        paciente.setAno(valores.getAsString(BdTabelNomePaciente.CAMPO_ANO_NASCIMENTO));
        paciente.setIdGenero(valores.getAsLong(BdTabelNomePaciente.CAMPO_ID_GENERO));
        paciente.setGenero(valores.getAsString(BdTabelNomePaciente.CAMPO_GENERO));
        paciente.setIdDistrito(valores.getAsLong(BdTabelNomePaciente.CAMPO_ID_DISTRITO));
        paciente.setDistrito(valores.getAsString(BdTabelNomePaciente.CAMPO_DISTRITO));
        paciente.setIdEstado(valores.getAsLong(BdTabelNomePaciente.CAMPO_ID_ESTADO));
        paciente.setEstado(valores.getAsString(BdTabelNomePaciente.CAMPO_ESTADO));

        return paciente;
    }

    public static Paciente cursorToPaciente(Cursor cursor) {
        Paciente paciente = new Paciente();

        paciente.setId(cursor.getLong(cursor.getColumnIndex(BdTabelNomePaciente._ID)));
        paciente.setNomePaciente(cursor.getString(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_NOME)));
        paciente.setIdAno(cursor.getLong(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_ID_ANO_NASCIMENTO)));
        paciente.setAno(cursor.getString(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_ANO_NASCIMENTO)));
        paciente.setIdGenero(cursor.getLong(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_ID_GENERO)));
        paciente.setGenero(cursor.getString(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_GENERO)));
        paciente.setIdDistrito(cursor.getLong(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_ID_DISTRITO)));
        paciente.setDistrito(cursor.getString(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_DISTRITO)));
        paciente.setIdEstado(cursor.getLong(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_ID_ESTADO)));
        paciente.setEstado(cursor.getString(cursor.getColumnIndex(BdTabelNomePaciente.CAMPO_ESTADO)));

        return paciente;
    }
}
