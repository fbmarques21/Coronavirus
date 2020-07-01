package com.example.coronavirus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class BdPacienteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    public static final String NOME_BASE_DADOS = "paciente.db";
    private static final int VERSAO_BASE_DADOS = 1;
    private static final boolean DESENVOLVIMENTO = true;

    public BdPacienteOpenHelper(@Nullable Context context){
        super(context,NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        BdTabelAnoNascimento tabelaAnoNascimento = new BdTabelAnoNascimento(db);
        tabelaAnoNascimento.cria();

        BdTabelNomePaciente tabelaNomePaciente = new BdTabelNomePaciente(db);
        tabelaNomePaciente.cria();

        BdTabelGenero tabelaGenero = new BdTabelGenero(db);
        tabelaGenero.cria();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        tabelaDistrito.cria();

        BdTableEstado tabelaEstado = new BdTableEstado(db);
        tabelaEstado.cria();

        if (DESENVOLVIMENTO) {
            seedData(db);
        }
    }

    private void seedData(SQLiteDatabase db) {
        BdTabelGenero tabelaGenero = new BdTabelGenero(db);

        Genero genero = new Genero();
        genero.setDescricao("Masculino");
        long idGenMasc = tabelaGenero.insert(Converte.generoToContentValues(genero));

        genero = new Genero();
        genero.setDescricao("Feminino");
        long idGenFem = tabelaGenero.insert(Converte.generoToContentValues(genero));

        BdTabelNomePaciente tabelaPaciente = new BdTabelNomePaciente(db);

        Livro livro = new Livro();
        livro.setTitulo("Lua vermelha");
        livro.setIdCategoria(idCatAcao);
        tabelaLivros.insert(Converte.livroToContentValues(livro));

        livro = new Livro();
        livro.setTitulo("O sobrevivente");
        livro.setIdCategoria(idCatAcao);
        tabelaLivros.insert(Converte.livroToContentValues(livro));

        livro = new Livro();
        livro.setTitulo("O intruso");
        livro.setIdCategoria(idCatTerror);
        tabelaLivros.insert(Converte.livroToContentValues(livro));

        livro = new Livro();
        livro.setTitulo("O mistério do quarto secreto");
        livro.setIdCategoria(idCatMisterio);
        tabelaLivros.insert(Converte.livroToContentValues(livro));
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

