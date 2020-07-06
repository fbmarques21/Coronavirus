package com.example.coronavirus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class BdPacienteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    public static final String NOME_BASE_DADOS = "paciente.db";
    private static final int VERSAO_BASE_DADOS = 1;
    private static final boolean DESENVOLVIMENTO = true;
    private final Context context;

    public BdPacienteOpenHelper(@Nullable Context context){
        super(context,NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
        this.context = context;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        BdTabelPaciente tabelaNomePaciente = new BdTabelPaciente(db);
        tabelaNomePaciente.cria();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        tabelaDistrito.cria();

        BdTableSuspeitos tabelaSuspeitos = new BdTableSuspeitos(db);
        tabelaDistrito.cria();

        inserirDistrito(tabelaDistrito);

        if (DESENVOLVIMENTO) {
            seedData(db);
        }
    }

    private void seedData(SQLiteDatabase db) {
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(db);

        Distrito distrito = new Distrito();
        distrito.setNome_distrito("Guarda");
        distrito.setNr_infetados(1);
        distrito.setNr_mortos(1);
        distrito.setNr_recuperados(0);
        long idDisGua = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        Paciente paciente = new Paciente();
        paciente.setNomePaciente("Francisco Marques");
        paciente.setGenero("Masculino");
        paciente.setIdDistrito(idDisGua);
        paciente.setEstado("Recuperado");
        tabelaPaciente.insert(Converte.pacienteToContentValues(paciente));

        distrito = new Distrito();
        distrito.setNome_distrito("Lisboa");
        distrito.setNr_infetados(1);
        distrito.setNr_mortos(1);
        distrito.setNr_recuperados(0);
        long idLis = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        paciente = new Paciente();
        paciente.setNomePaciente("Miguel");
        paciente.setGenero("Masculino");
        paciente.setIdDistrito(idLis);
        paciente.setEstado("Morto");
        tabelaPaciente.insert(Converte.pacienteToContentValues(paciente));
    }

    private void inserirDistrito(BdTableDistrito tabelaDistrito) {
        Distrito distrito = new Distrito();

        distrito.setNome_distrito(context.getString(R.string.distrito_guarda));
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_lisboa));
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

