package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;

public class Converte {
    public static ContentValues pacienteToContentValues(Paciente paciente) {
        ContentValues valores = new ContentValues();
        valores.put(BdTabelPaciente.NOME_PACIENTE, paciente.getNomePaciente());
        valores.put(BdTabelPaciente.ANO_NASCIMENTO_PACIENTE, paciente.getAno());
        valores.put(BdTabelPaciente.GENERO_PACIENTE, paciente.getGenero());
        valores.put(BdTabelPaciente.CAMPO_ID_DISTRITO, paciente.getIdDistrito());
        valores.put(BdTabelPaciente.ESTADO_PACIENTE, paciente.getEstado());
        return valores;
    }

    public static Paciente contentValuesToPaciente(ContentValues valores) {
        Paciente paciente = new Paciente();
        paciente.setId(valores.getAsLong(BdTabelPaciente._ID));
        paciente.setNomePaciente(valores.getAsString(BdTabelPaciente.NOME_PACIENTE));
        paciente.setAno(valores.getAsString(BdTabelPaciente.ANO_NASCIMENTO_PACIENTE));
        paciente.setGenero(valores.getAsString(BdTabelPaciente.GENERO_PACIENTE));
        paciente.setIdDistrito(valores.getAsLong(BdTabelPaciente.CAMPO_ID_DISTRITO));
        paciente.setEstado(valores.getAsString(BdTabelPaciente.ESTADO_PACIENTE));
        return paciente;
    }

    public static Paciente cursorToPaciente(Cursor cursor) {
        Paciente paciente = new Paciente();
        paciente.setId(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente._ID)));
        paciente.setNomePaciente(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.NOME_PACIENTE)));
        paciente.setAno(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.ANO_NASCIMENTO_PACIENTE)));
        paciente.setGenero(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.GENERO_PACIENTE)));
        paciente.setIdDistrito(cursor.getLong(cursor.getColumnIndex(BdTabelPaciente.CAMPO_ID_DISTRITO)));
        paciente.setEstado(cursor.getString(cursor.getColumnIndex(BdTabelPaciente.ESTADO_PACIENTE)));
        return paciente;
    }

    public static ContentValues suspeitoToContentValues(Suspeito suspeito) {
        ContentValues valores = new ContentValues();
        valores.put(BdTableSuspeitos.NOME_SUSPEITO, suspeito.getNomeSuspeito());
        valores.put(BdTableSuspeitos.ANO_NASCIMENTO_SUSPEITO, suspeito.getAno());
        valores.put(BdTableSuspeitos.GENERO_SUSPEITO, suspeito.getGenero());
        valores.put(BdTableSuspeitos.CAMPO_ID_DISTRITO, suspeito.getIdDistrito());
        return valores;
    }

    public static Suspeito contentValuesToSuspeito(ContentValues valores) {
        Suspeito suspeito = new Suspeito();
        suspeito.setId(valores.getAsLong(BdTableSuspeitos._ID));
        suspeito.setNomeSuspeito(valores.getAsString(BdTableSuspeitos.NOME_SUSPEITO));
        suspeito.setAno(valores.getAsString(BdTableSuspeitos.ANO_NASCIMENTO_SUSPEITO));
        suspeito.setGenero(valores.getAsString(BdTableSuspeitos.GENERO_SUSPEITO));
        suspeito.setIdDistrito(valores.getAsLong(BdTableSuspeitos.CAMPO_ID_DISTRITO));
        return suspeito;
    }

    public static Suspeito cursorToSuspeito(Cursor cursor) {
        Suspeito suspeito = new Suspeito();
        suspeito.setId(cursor.getLong(cursor.getColumnIndex(BdTableSuspeitos._ID)));
        suspeito.setNomeSuspeito(cursor.getString(cursor.getColumnIndex(BdTableSuspeitos.NOME_SUSPEITO)));
        suspeito.setAno(cursor.getString(cursor.getColumnIndex(BdTableSuspeitos.ANO_NASCIMENTO_SUSPEITO)));
        suspeito.setGenero(cursor.getString(cursor.getColumnIndex(BdTableSuspeitos.GENERO_SUSPEITO)));
        suspeito.setIdDistrito(cursor.getLong(cursor.getColumnIndex(BdTableSuspeitos.CAMPO_ID_DISTRITO)));
        return suspeito;
    }

    public static ContentValues distritoToContentValues(Distrito distrito){
        ContentValues valores = new ContentValues();
        valores.put(BdTableDistrito.NOME_DISTRITO, distrito.getNome_distrito());
        valores.put(BdTableDistrito.NR_INFETADOS_DISTRITO, distrito.getNr_infetados());
        valores.put(BdTableDistrito.NR_RECUPERADOS_DISTRITO, distrito.getNr_recuperados());
        valores.put(BdTableDistrito.NR_MORTOS_DISTRITO, distrito.getNr_mortos());
        valores.put(BdTableDistrito.NR_HABITANTES_DISTRITO,distrito.getNr_habitantes());
        return  valores ;
    }
    public static Distrito contentValuesToDistrito(ContentValues valores){
        Distrito distrito = new Distrito();
        distrito.setId(valores.getAsLong(BdTableDistrito._ID));
        distrito.setNr_infetados(valores.getAsInteger(BdTableDistrito.NR_INFETADOS_DISTRITO));
        distrito.setNr_recuperados(valores.getAsInteger(BdTableDistrito.NR_RECUPERADOS_DISTRITO));
        distrito.setNr_mortos(valores.getAsInteger(BdTableDistrito.NR_MORTOS_DISTRITO));
        distrito.setNr_habitantes(valores.getAsInteger(BdTableDistrito.NR_HABITANTES_DISTRITO));
        return distrito;
    }
    public static Distrito cursorToDistrito(Cursor cursor){
        Distrito distrito = new Distrito();
        distrito.setId(cursor.getLong(cursor.getColumnIndex(BdTableDistrito._ID)));
        distrito.setNr_infetados(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.NR_INFETADOS_DISTRITO)));
        distrito.setNr_recuperados(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.NR_RECUPERADOS_DISTRITO)));
        distrito.setNr_mortos(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.NR_MORTOS_DISTRITO)));
        distrito.setNr_habitantes(cursor.getInt(cursor.getColumnIndex(BdTableDistrito.NR_HABITANTES_DISTRITO)));
        return distrito;
    }

}
