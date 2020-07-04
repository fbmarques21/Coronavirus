package com.example.coronavirus;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTabelNomePaciente implements BaseColumns {
    public static final String NOME_TABELA = "paciente";

    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_ID_ANO_NASCIMENTO = "id_ano";
    public static final String CAMPO_ANO_NASCIMENTO = "ano";
    public static final String CAMPO_ID_GENERO = "id_genero";
    public static final String CAMPO_GENERO = "genero";
    public static final String CAMPO_ID_DISTRITO = "id_distrito";
    public static final String CAMPO_DISTRITO = "distrito";
    public static final String CAMPO_ID_ESTADO = "id_estado";
    public static final String CAMPO_ESTADO = "estado";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_NOME_COMPLETO = NOME_TABELA + "." + CAMPO_NOME;
    public static final String CAMPO_ID_ANO_COMPLETO = NOME_TABELA + "." + CAMPO_ID_ANO_NASCIMENTO;
    public static final String CAMPO_ANO_NASCIMENTO_COMPLETO = BdTabelAnoNascimento.CAMPO_DESCRICAO_COMPLETO + " AS " + CAMPO_ANO_NASCIMENTO;
    public static final String CAMPO_ID_GENERO_COMPLETO = NOME_TABELA + "." + CAMPO_ID_GENERO;
    public static final String CAMPO_GENERO_COMPLETO = BdTabelGenero.CAMPO_DESCRICAO_COMPLETO + " AS " + CAMPO_GENERO;
    public static final String CAMPO_ID_DISTRITO_COMPLETO = NOME_TABELA + "." + CAMPO_ID_DISTRITO;
    public static final String CAMPO_DISTRITO_COMPLETO = BdTableDistrito.CAMPO_DESCRICAO_COMPLETO + " AS " + CAMPO_DISTRITO;
    public static final String CAMPO_ID_ESTADO_COMPLETO = NOME_TABELA + "." + CAMPO_ID_ESTADO;
    public static final String CAMPO_ESTADO_COMPLETO = BdTableEstado.CAMPO_DESCRICAO_COMPLETO + " AS " + CAMPO_ESTADO;

    public static final String[] TODOS_CAMPOS = {CAMPO_ID_COMPLETO, CAMPO_NOME_COMPLETO, CAMPO_ID_ANO_COMPLETO, CAMPO_ANO_NASCIMENTO_COMPLETO, CAMPO_ID_GENERO_COMPLETO,
                                                 CAMPO_GENERO_COMPLETO, CAMPO_ID_DISTRITO_COMPLETO, CAMPO_DISTRITO_COMPLETO, CAMPO_ID_ESTADO_COMPLETO, CAMPO_ESTADO_COMPLETO};

    private SQLiteDatabase db;

    public BdTabelNomePaciente(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL("CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_NOME + " TEXT NOT NULL," +
                CAMPO_ANO_NASCIMENTO + " TEXT NOT NULL," +
                CAMPO_GENERO + " TEXT NOT NULL," +
                CAMPO_DISTRITO + " TEXT NOT NULL," +
                CAMPO_ESTADO + " TEXT NOT NULL," +
                CAMPO_ID_DISTRITO + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + CAMPO_ID_DISTRITO + ") REFERENCES " +
                    BdTableDistrito.NOME_TABELA + "("+ BdTableDistrito._ID + ")" +
                ")");
    }

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param values this map contains the initial column values for the
     *            row. The keys should be the column names and the values the
     *            column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    /**
     * Query the given table, returning a {@link Cursor} over the result set.
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
        if (!Arrays.asList(columns).contains(CAMPO_ANO_NASCIMENTO_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }
        if (!Arrays.asList(columns).contains(CAMPO_GENERO_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }
        if (!Arrays.asList(columns).contains(CAMPO_DISTRITO_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }
        if (!Arrays.asList(columns).contains(CAMPO_ESTADO_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }


        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTabelAnoNascimento.NOME_TABELA;
        sql += " ON " + CAMPO_ID_ANO_NASCIMENTO + "=" + BdTabelAnoNascimento.CAMPO_ID_COMPLETO;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTabelGenero.NOME_TABELA;
        sql += " ON " + CAMPO_ID_GENERO + "=" + BdTabelGenero.CAMPO_ID_COMPLETO;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTabelAnoNascimento.NOME_TABELA;
        sql += " ON " + CAMPO_ID_DISTRITO + "=" + BdTableDistrito.CAMPO_ID_COMPLETO;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTableDistrito.NOME_TABELA;
        sql += " ON " + CAMPO_ID_ESTADO + "=" + BdTableEstado.CAMPO_ID_COMPLETO;

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
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
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
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
