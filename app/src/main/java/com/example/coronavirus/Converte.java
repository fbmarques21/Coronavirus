package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;

public class Converte {
    public static ContentValues pacienteToContentValues(Paciente paciente) {
        ContentValues valores = new ContentValues();
        valores.put(BdTabelPaciente.NOME_PACIENTE, paciente.getNomePaciente());
        valores.put(BdTabelPaciente.CAMPO_ANO_NASCIMENTO, paciente.getAno());
        valores.put(BdTabelPaciente.CAMPO_GENERO, paciente.getGenero());
        valores.put(BdTabelPaciente.CAMPO_ID_DISTRITO, paciente.getIdDistrito());
        valores.put(BdTabelPaciente.CAMPO_ESTADO, paciente.getEstado());
        return valores;
    }

    public static Paciente contentValuesToPaciente(ContentValues valores) {
        Paciente paciente = new Paciente();
        paciente.setId(valores.getAsLong(BdTabelPaciente._ID));
        paciente.setNomePaciente(valores.getAsString(BdTabelPaciente.NOME_PACIENTE));
        paciente.setAno(valores.getAsString(BdTabelPaciente.CAMPO_ANO_NASCIMENTO));
        paciente.setGenero(valores.getAsString(BdTabelPaciente.CAMPO_GENERO));
        paciente.setIdDistrito(valores.getAsLong(BdTabelPaciente.CAMPO_ID_DISTRITO));
        paciente.setDistrito(valores.getAsString(BdTabelPaciente.CAMPO_DISTRITO));
        paciente.setEstado(valores.getAsString(BdTabelPaciente.CAMPO_ESTADO));
        return paciente;
    }

    public static Paciente cursorToPaciente(Cursor cursor) {
        Paciente paciente = new Paciente();
        paciente.setId(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente._ID)));
        paciente.setNomePaciente(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.NOME_PACIENTE)));
        paciente.setAno(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ANO_NASCIMENTO)));
        paciente.setGenero(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_GENERO)));
        paciente.setIdDistrito(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ID_DISTRITO)));
        paciente.setDistrito(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_DISTRITO)));
        paciente.setEstado(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ESTADO)));
        return paciente;
    }

    public static ContentValues distritoToContentValues(Distrito distrito){
        ContentValues valores = new ContentValues();
        valores.put(BdTableDistrito.NOME_DISTRITO, distrito.getNome_distrito());
        valores.put(BdTableDistrito.CAMPO_NR_INFETADOS_DISTRITO, distrito.getNr_infetados());
        valores.put(BdTableDistrito.CAMPO_NR_RECUPERADOS_DISTRITO, distrito.getNr_recuperados());
        valores.put(BdTableDistrito.CAMPO_NR_MORTOS_DISTRITO, distrito.getNr_mortos());
        return  valores ;
    }
    public static Distrito contentValuesToDistrito(ContentValues valores){
        Distrito distrito = new Distrito();
        distrito.setId(valores.getAsLong(BdTableDistrito._ID));
        distrito.setNr_infetados(valores.getAsInteger(BdTableDistrito.CAMPO_NR_INFETADOS_DISTRITO));
        distrito.setNr_recuperados(valores.getAsInteger(BdTableDistrito.CAMPO_NR_RECUPERADOS_DISTRITO));
        distrito.setNr_mortos(valores.getAsInteger(BdTableDistrito.CAMPO_NR_MORTOS_DISTRITO));
        return distrito;
    }
    public static Distrito cursorToDistrito(Cursor cursor){
        Distrito distrito = new Distrito();
        distrito.setId(cursor.getLong(cursor.getColumnIndex(BdTableDistrito._ID)));
        distrito.setNr_infetados(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.CAMPO_NR_INFETADOS_DISTRITO)));
        distrito.setNr_recuperados(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.CAMPO_NR_RECUPERADOS_DISTRITO)));
        distrito.setNr_mortos(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.CAMPO_NR_MORTOS_DISTRITO)));
        return distrito;
    }
}
