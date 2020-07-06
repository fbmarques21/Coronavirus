package com.example.coronavirus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdPacienteTest {
    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Before
    public void apagaBaseDados() {
        getTargetContext().deleteDatabase(BdPacienteOpenHelper.NOME_BASE_DADOS);
    }

    public String getTableAsString(SQLiteDatabase db, String tableName) {
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        return tableString;
    }

    @Test
    public void consegueAbrirBaseDados() {
        // Context of the app under test.
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdPaciente = openHelper.getReadableDatabase();
        assertTrue(bdPaciente.isOpen());
        getTableAsString(bdPaciente, "Distrito");
        bdPaciente.close();
    }

    private long insereDistrito (BdTableDistrito tabelaDistrito, Distrito distrito){
        long id = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));
        assertNotEquals(-1, id);
        return id;
    }

    private long insereDistrito(BdTableDistrito tabelaDistrito, String nome_distrito, int nr_infetados, int nr_recuperados, int nr_mortos){
        Distrito distrito = new Distrito();
        distrito.setNome_distrito(nome_distrito);
        distrito.setNr_infetados(nr_infetados);
        distrito.setNr_recuperados(nr_recuperados);
        distrito.setNr_mortos(nr_mortos);
        return insereDistrito(tabelaDistrito, distrito);
    }

    @Test
    public void consegueInserirDistrito(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);
        insereDistrito(tabelaDistrito,"Guarda",0,0,0);

        bd.close();
    }

    @Test
    public void consegueLerDistrito(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);

        Cursor cursor = tabelaDistrito.query(BdTableDistrito.TODOS_CAMPOS_DISTRITO, null, null, null, null, null);
        int numeroDistrito = cursor.getCount();
        cursor.close();

        insereDistrito(tabelaDistrito, "Guarda",0,0,0);

        cursor = tabelaDistrito.query(BdTableDistrito.TODOS_CAMPOS_DISTRITO, null, null, null, null ,null);
        assertEquals(numeroDistrito + 1, cursor.getCount());
        cursor.close();

        bd.close();
    }

    @Test
    public void consegueEditarDistrito(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);

        Distrito distrito = new Distrito();
        distrito.setNome_distrito("Guarda");
        distrito.setNr_mortos(0);
        distrito.setNr_recuperados(0);
        distrito.setNr_infetados(0);

        long id = insereDistrito(tabelaDistrito, distrito);

        distrito.setNome_distrito("Guarda");
        distrito.setNr_mortos(0);
        distrito.setNr_recuperados(0);
        distrito.setNr_infetados(1);

        int registosAlteradosPais = tabelaDistrito.update(Converte.distritoToContentValues(distrito), BdTableDistrito._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAlteradosPais);

        bd.close();
    }

    @Test
    public void conseguesApagarDistrito(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);
        long id = insereDistrito(tabelaDistrito, "Guarda",0,0,0);

        int registoApagadoDistrito = tabelaDistrito.delete(BdTableDistrito._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registoApagadoDistrito);

        bd.close();
    }

    private long inserePaciente(BdTabelPaciente tabelaPaciente, Paciente paciente) {
        long id = tabelaPaciente.insert(Converte.pacienteToContentValues(paciente));
        assertNotEquals(-1, id);
        return id;
    }

    private long inserePaciente(BdTabelPaciente tabelaPaciente, String Nome, String Ano_Nascimento, long Distrito, String Genero, String Estado) {
        Paciente paciente = new Paciente();
        paciente.setNomePaciente(Nome);
        paciente.setAno(Ano_Nascimento);
        paciente.setIdDistrito(Distrito);
        paciente.setGenero(Genero);
        paciente.setEstado(Estado);
        return inserePaciente(tabelaPaciente, paciente);
    }

    @Test
    public void concegueInserirPaciente() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        BdTabelPaciente tabelPaciente = new BdTabelPaciente(db);
        getTableAsString(db, "Concelhos");
        Cursor cursor = tabelPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();
        Cursor cursor1 = tabelPaciente.query(new String[]{"_id"}, "nome_paciente=?", new String[]{"Guarda"}, null, null, null);
        long id_concelho = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_concelho = cursor1.getInt(cursor1.getColumnIndex("_id"));
        inserePaciente(tabelPaciente, "Francisco", "2000", id_concelho, "Masculino", "Recuperado");
        cursor = tabelPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();
        db.close();
    }

    @Test
    public void consegueAlterarPaciente() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdPaciente = openHelper.getWritableDatabase();

        BdTabelPaciente tabelPaciente = new BdTabelPaciente(bdPaciente);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(bdPaciente);

        Paciente paciente = new Paciente();
        paciente.setNomePaciente("Francisco");
        long id_distrito = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito = ?", new String[]{"Guarda"}, null, null, null).getColumnIndex("_id");
        paciente.setIdDistrito(id_distrito);
        paciente.setGenero("Masculino");
        paciente.setEstado("Recuperado");
        long id = inserePaciente(tabelPaciente, paciente);

        paciente.setNomePaciente("Francisco Marques");
        id_distrito = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito = ?", new String[]{"Guarda"}, null, null, null).getColumnIndex("_id");
        paciente.setIdDistrito(id_distrito);
        paciente.setGenero("Masculino");
        paciente.setEstado("Recuperado");
        int registosAlterados = tabelPaciente.update(Converte.pacienteToContentValues(paciente), BdTabelPaciente._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAlterados);
        bdPaciente.close();
    }

    @Test
    public void consegueApagarPaciente() {
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(db);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);

        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito=?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        long id = inserePaciente(tabelaPaciente, "Gonçalo", "2000", id_distrito, "Masculino", "Recuperado");
        int registosApagados = tabelaPaciente.delete(BdTabelPaciente._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosApagados);
        db.close();
    }

    @Test
    public void consegueLerPaciente() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTabelPaciente tabelPaciente = new BdTabelPaciente(db);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);

        Cursor cursor = tabelPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();
        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_concelho_=?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        inserePaciente(tabelPaciente, "Gonçalo", "2000", id_distrito, "Masculino", "Recuperado");
        cursor = tabelPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();
        db.close();
    }


}
