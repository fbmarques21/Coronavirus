package com.example.coronavirus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBPaciente {
    private SQLiteDatabase db;

    public DBPaciente(Context context){
        this.db = new SQLiteOpenHelper(context).getWritableDatabase();
    }

    public void insert(Paciente paciente) {
        ContentValues valores = new ContentValues();
        valores.put("nome", paciente.getNome());
        valores.put("ano", paciente.getAno());

        this.db.insert("paciente", null, valores);
    }

    public void update(Paciente paciente){
        ContentValues valores = new ContentValues();
        valores.put("nome", paciente.getNome());
        valores.put("ano", paciente.getAno());

        this.db.update("paciente", valores,"_id = ?", new String[]{""+paciente.get_id()});
    }

    public void delete(Paciente paciente){
        this.db.delete("paciente","_id = ?", new String[]{""+paciente.get_id()});
    }

    public ArrayList<Paciente> procurar(){
        ArrayList<Paciente> lista = new ArrayList<>();
        String[] colunas = new String[]{"_id","nome","ano"};
        Cursor cursor = this.db.query("paciente", colunas, null, null, null, null,"nome DESC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Paciente paciente = new Paciente();
                paciente.set_id(cursor.getInt(0));
                paciente.setNome(cursor.getString(1));
                paciente.setAno(cursor.getString(2));
                lista.add(paciente);
            }while (cursor.moveToNext());
        }

        return lista;
    }
}
