package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTabelPaciente implements BaseColumns {
    public static final String NOME_TABELA_PACIENTE = "paciente";
    public static final String NOME_PACIENTE = "nome";
    public static final String ANO_NASCIMENTO_PACIENTE = "ano";
    public static final String GENERO_PACIENTE = "genero";
    public static final String DISTRITO_PACIENTE = "distrito";
    public static final String CAMPO_ID_DISTRITO = "id_distrito";
    public static final String ESTADO_PACIENTE = "estado";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA_PACIENTE + "." + _ID;
    public static final String NOME_PACIENTE_COMPLETO = NOME_TABELA_PACIENTE + "." + NOME_PACIENTE;
    public static final String ANO_NASCIMENTO_PACIENTE_COMPLETO = NOME_TABELA_PACIENTE + "." + ANO_NASCIMENTO_PACIENTE;
    public static final String GENERO_PACIENTE_COMPLETO = NOME_TABELA_PACIENTE + "." + GENERO_PACIENTE;
    public static final String CAMPO_ID_DISTRITO_COMPLETO = NOME_TABELA_PACIENTE + "." + CAMPO_ID_DISTRITO;
    public static final String CAMPO_DISTRITO_COMPLETO = BdTableDistrito.NOME_TABELA_DISTRITO + "." + BdTableDistrito.NOME_DISTRITO;
    public static final String ESTADO_PACIENTE_COMPLETO = NOME_TABELA_PACIENTE + "." + ESTADO_PACIENTE;

    public static final String[] TODOS_CAMPOS_PACIENTE = {CAMPO_ID_COMPLETO, NOME_PACIENTE_COMPLETO, ANO_NASCIMENTO_PACIENTE_COMPLETO, GENERO_PACIENTE_COMPLETO, CAMPO_ID_DISTRITO_COMPLETO,
                                               CAMPO_DISTRITO_COMPLETO, ESTADO_PACIENTE_COMPLETO};

    private SQLiteDatabase db;

    public BdTabelPaciente(SQLiteDatabase db) {
        this.db = db;
    }

    public void criar(){
        db.execSQL("CREATE TABLE " + NOME_TABELA_PACIENTE + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_PACIENTE + " TEXT NOT NULL," +
                ANO_NASCIMENTO_PACIENTE + " TEXT NOT NULL," +
                GENERO_PACIENTE + " TEXT NOT NULL," +
                ESTADO_PACIENTE +  " TEXT NOT NULL," +
                CAMPO_ID_DISTRITO + " INTEGER NOT NULL," + //CAMPO_ID_DISTRITO
                "FOREIGN KEY (" + CAMPO_ID_DISTRITO + ") REFERENCES " +
                BdTableDistrito.NOME_TABELA_DISTRITO + "(" + BdTableDistrito._ID +")"+
                ")");
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA_PACIENTE, null, values);
    }


    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {

        if (!Arrays.asList(columns).contains(CAMPO_DISTRITO_COMPLETO)) {
            return db.query(NOME_TABELA_PACIENTE, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA_PACIENTE + " INNER JOIN " + BdTableDistrito.NOME_TABELA_DISTRITO;
        sql += " ON " + CAMPO_ID_DISTRITO_COMPLETO + "=" + BdTableDistrito.CAMPO_ID_COMPLETO;

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
    /**
     * Convenience method for updating rows in the database.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA_PACIENTE, values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA_PACIENTE, whereClause, whereArgs);
    }
}
