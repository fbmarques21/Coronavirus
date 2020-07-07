package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTabelPaciente implements BaseColumns {
    public static final String NOME_TABELA = "paciente";
    public static final String NOME_PACIENTE = "nome";
    public static final String ANO_NASCIMENTO_PACIENTE = "ano";
    public static final String GENERO_PACIENTE = "genero";
    public static final String DISTRITO_PACIENTE = "distrito";
    public static final String CAMPO_ID_DISTRITO = "id_distrito";
    public static final String ESTADO_PACIENTE = "estado";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String NOME_PACIENTE_COMPLETO = NOME_TABELA + "." + NOME_PACIENTE;
    public static final String ANO_NASCIMENTO_PACIENTE_COMPLETO = NOME_TABELA + "." + ANO_NASCIMENTO_PACIENTE;
    public static final String GENERO_PACIENTE_COMPLETO = NOME_TABELA + "." + GENERO_PACIENTE;
    public static final String CAMPO_ID_DISTRITO_COMPLETO = BdTableDistrito.CAMPO_ID_COMPLETO + "." + CAMPO_ID_DISTRITO;
    public static final String CAMPO_DISTRITO_COMPLETO = BdTableDistrito.NOME_DISTRITO + "." + DISTRITO_PACIENTE;
    public static final String ESTADO_PACIENTE_COMPLETO = NOME_TABELA + "." + ESTADO_PACIENTE;

    public static final String[] TODOS_CAMPOS_PACIENTE = {CAMPO_ID_COMPLETO, NOME_PACIENTE_COMPLETO, ANO_NASCIMENTO_PACIENTE_COMPLETO, GENERO_PACIENTE_COMPLETO, CAMPO_ID_DISTRITO_COMPLETO,
                                               CAMPO_DISTRITO_COMPLETO, ESTADO_PACIENTE_COMPLETO};

    private SQLiteDatabase db;

    public BdTabelPaciente(SQLiteDatabase db) {
        this.db = db;
    }
    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOME_PACIENTE + " TEXT NOT NULL," +
                ANO_NASCIMENTO_PACIENTE + " TEXT NOT NULL," +
                GENERO_PACIENTE + " TEXT NOT NULL," +
                DISTRITO_PACIENTE + " TEXT NOT NULL," +
                ESTADO_PACIENTE + " TEXT NOT NULL," +
                CAMPO_ID_DISTRITO + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + CAMPO_ID_DISTRITO + ") REFERENCES " +
                    BdTableDistrito.NOME_TABELA + "("+ BdTableDistrito._ID + ")" +
                ")");
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    /**
     * Query the given table, returning a {@link Cursor} over the result set.
     *
     *
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {

        if (!Arrays.asList(columns).contains(CAMPO_DISTRITO_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTableDistrito.NOME_TABELA;
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
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }


    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
