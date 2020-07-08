package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTableSuspeitos implements BaseColumns {
    public static final String NOME_TABELA_SUSPEITO = "suspeito";
    public static final String NOME_SUSPEITO = "nome";
    public static final String ANO_NASCIMENTO_SUSPEITO = "ano";
    public static final String GENERO_SUSPEITO = "genero";
    public static final String DISTRITO_SUSPEITO = "distrito";
    public static final String CAMPO_ID_DISTRITO = "id_distrito";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA_SUSPEITO + "." + _ID;
    public static final String NOME_SUSPEITO_COMPLETO = NOME_TABELA_SUSPEITO + "." + NOME_SUSPEITO;
    public static final String ANO_NASCIMENTO_SUSPEITO_COMPLETO = NOME_TABELA_SUSPEITO + "." + ANO_NASCIMENTO_SUSPEITO;
    public static final String GENERO_SUSPEITO_COMPLETO = NOME_TABELA_SUSPEITO + "." + GENERO_SUSPEITO;
    public static final String CAMPO_ID_DISTRITO_COMPLETO = BdTableDistrito.CAMPO_ID_COMPLETO + " AS " + CAMPO_ID_DISTRITO;
    public static final String CAMPO_DISTRITO_COMPLETO = BdTableDistrito.NOME_DISTRITO + " AS " + DISTRITO_SUSPEITO;

    public static final String[] TODOS_CAMPOS_SUSPEITO = {CAMPO_ID_COMPLETO, NOME_SUSPEITO_COMPLETO, ANO_NASCIMENTO_SUSPEITO_COMPLETO, GENERO_SUSPEITO_COMPLETO,
            CAMPO_ID_DISTRITO_COMPLETO, CAMPO_DISTRITO_COMPLETO};

    private SQLiteDatabase db;

    public BdTableSuspeitos(SQLiteDatabase db) {
        this.db = db;
    }
    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA_SUSPEITO + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_SUSPEITO + " TEXT NOT NULL," +
                ANO_NASCIMENTO_SUSPEITO + " TEXT NOT NULL," +
                GENERO_SUSPEITO + " TEXT NOT NULL," +
                CAMPO_ID_DISTRITO + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + CAMPO_ID_DISTRITO + ") REFERENCES " +
                BdTableDistrito.NOME_TABELA_DISTRITO + "("+ BdTableDistrito._ID + ")" +
                ")");
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA_SUSPEITO, null, values);
    }


    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {

        if (!Arrays.asList(columns).contains(CAMPO_DISTRITO_COMPLETO)) {
            return db.query(NOME_TABELA_SUSPEITO, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA_SUSPEITO + " INNER JOIN " + BdTableDistrito.NOME_TABELA_DISTRITO;
        sql += " ON " + CAMPO_ID_DISTRITO + "=" + BdTableDistrito.CAMPO_ID_COMPLETO;

        if (selection != null) {
            sql += " WHERE " + selection;
        }

        if (groupBy != null) {
            sql += " GROUP BY " + groupBy;

            if (having != null) {
                sql += " HAVING " + having;
            }
        }

        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA_SUSPEITO, values, whereClause, whereArgs);
    }


    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA_SUSPEITO, whereClause, whereArgs);
    }
}
