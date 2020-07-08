package com.example.coronavirus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdPacienteOpenHelper extends SQLiteOpenHelper {
    public static final String NOME_BASE_DADOS = "paciente.db";
    private static final int VERSAO_BASE_DADOS = 2;
    private static final boolean DESENVOLVIMENTO = false;
    private final Context context;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *  @param context to use for locating paths to the the database
     *
     */
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
        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(db);
        tabelaPaciente.criar();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        tabelaDistrito.cria();

        BdTableSuspeitos tabelaSuspeitos = new BdTableSuspeitos(db);
        tabelaSuspeitos.cria();

        inserirDistrito(tabelaDistrito);

        if (DESENVOLVIMENTO) {
            seedData(db);
        }
    }

    private void seedData(SQLiteDatabase db) {
        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(db);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);

        Distrito distrito = new Distrito();
        distrito.setNome_distrito("Guarda");
        distrito.setNr_infetados(1);
        distrito.setNr_mortos(1);
        distrito.setNr_recuperados(0);
        distrito.setNr_habitantes(42531);
        long idGuarda = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        Paciente paciente = new Paciente();
        paciente.setNome_paciente("Francisco Marques");
        paciente.setAno_nascimento_paciente("2000");
        paciente.setGenero_paciente("Masculino");
        paciente.setId_distrito(idGuarda);
        paciente.setEstado_paciente("Recuperado");
        tabelaPaciente.insert(Converte.pacienteToContentValues(paciente));

        distrito = new Distrito();
        distrito.setNome_distrito("Lisboa");
        distrito.setNr_infetados(1);
        distrito.setNr_mortos(1);
        distrito.setNr_recuperados(0);
        distrito.setNr_habitantes(2265832);
        long idLisboa = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        paciente = new Paciente();
        paciente.setNome_paciente("Miguel");
        paciente.setAno_nascimento_paciente("1999");
        paciente.setGenero_paciente("Masculino");
        paciente.setId_distrito(idLisboa);
        paciente.setEstado_paciente("Morto");
        tabelaPaciente.insert(Converte.pacienteToContentValues(paciente));
    }

    private void inserirDistrito(BdTableDistrito tabelaDistrito) {
        Distrito distrito = new Distrito();

        distrito.setNome_distrito(context.getString(R.string.distrito_aveiro));
        distrito.setNr_habitantes(714200);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_beja));
        distrito.setNr_habitantes(152758);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_braga));
        distrito.setNr_habitantes(956185);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_braganca));
        distrito.setNr_habitantes(136252);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_castelo_branco));
        distrito.setNr_habitantes(196264);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_coimbra));
        distrito.setNr_habitantes(429987);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_evora));
        distrito.setNr_habitantes(168034);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_faro));
        distrito.setNr_habitantes(434023);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_guarda));
        distrito.setNr_habitantes(168898);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_leiria));
        distrito.setNr_habitantes(470895);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_lisboa));
        distrito.setNr_habitantes(2265832);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_portalegre));
        distrito.setNr_habitantes(118506);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_porto));
        distrito.setNr_habitantes(1778146);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_santarem));
        distrito.setNr_habitantes(465701);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_setubal));
        distrito.setNr_habitantes(911794);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_viana_do_castelo));
        distrito.setNr_habitantes(240133);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));

        distrito.setNome_distrito(context.getString(R.string.distrito_vila_real));
        distrito.setNr_habitantes(213775);
        tabelaDistrito.insert(Converte.distritoToContentValues(distrito));


        distrito.setNome_distrito(context.getString(R.string.distrito_viseu));
        distrito.setNr_habitantes(391215);
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

