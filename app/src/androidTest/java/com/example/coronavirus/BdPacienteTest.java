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

    public Context getTargetContext() {
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
        SQLiteDatabase bdCoronavirus = openHelper.getReadableDatabase();
        assertTrue(bdCoronavirus.isOpen());
        getTableAsString(bdCoronavirus, "Distrito");
        bdCoronavirus.close();
    }

    private long insereDistrito (BdTableDistrito tabelaDistrito, Distrito distrito){
        long id = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));
        assertNotEquals(-1, id);
        return id;
    }


    private long insereDistrito(BdTableDistrito tabelaDistrito, String nome_distrito, int nr_habitante, int nr_infetados, int nr_recuperados, int nr_mortos){
        Distrito distrito = new Distrito();
        distrito.setNome_distrito(nome_distrito);
        distrito.setNr_habitantes(nr_habitante);
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
        insereDistrito(tabelaDistrito,"Guarda",42531,0,0,0);

        bd.close();
    }

    @Test
    public void consegueLerDistrito(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);

        Cursor cursor = tabelaDistrito.query(BdTableDistrito.TODOS_CAMPOS_DISTRITO, null, null, null, null, null);
        int numeroDistritos = cursor.getCount();
        cursor.close();

        insereDistrito(tabelaDistrito, "Guarda", 42531,0,0,0);

        cursor = tabelaDistrito.query(BdTableDistrito.TODOS_CAMPOS_DISTRITO, null, null, null, null ,null);
        assertEquals(numeroDistritos + 1, cursor.getCount());
        cursor.close();

        bd.close();
    }

    @Test
    public void consegueEditarDistritos(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);

        Distrito distrito = new Distrito();
        distrito.setNome_distrito("Guarda");
        distrito.setNr_habitantes(42531);
        distrito.setNr_mortos(0);
        distrito.setNr_recuperados(0);
        distrito.setNr_infetados(0);

        long id = insereDistrito(tabelaDistrito, distrito);

        distrito.setNome_distrito("Guarda");
        distrito.setNr_habitantes(42531);
        distrito.setNr_mortos(0);
        distrito.setNr_recuperados(0);
        distrito.setNr_infetados(1);

        int registosAlteradosDistritos = tabelaDistrito.update(Converte.distritoToContentValues(distrito), BdTableDistrito._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAlteradosDistritos);

        bd.close();
    }

    @Test
    public void conseguesApagarDistritos(){
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bd);
        long id = insereDistrito(tabelaDistrito, "Guarda", 42531,0,0,0);

        int registoApagadoDistrito = tabelaDistrito.delete(BdTableDistrito._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registoApagadoDistrito);

        bd.close();
    }

    private long inserePaciente(BdTabelPaciente tabelaPaciente, Paciente paciente) {
        long id = tabelaPaciente.insert(Converte.pacienteToContentValues(paciente));
        assertNotEquals(-1, id);
        return id;
    }

    private long inserePaciente(BdTabelPaciente tabelaPaciente, String nome, String ano_nascimento, long distrito, String genero, String estado) {
        Paciente paciente = new Paciente();
        paciente.setNome_paciente(nome);
        paciente.setAno_nascimento_paciente(ano_nascimento);
        paciente.setId_distrito(distrito);
        paciente.setGenero_paciente(genero);
        paciente.setEstado_paciente(estado);
        return inserePaciente(tabelaPaciente, paciente);
    }

    @Test
    public void concegueInserirPaciente() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(db);
        getTableAsString(db, "Distrito");
        Cursor cursor = tabelaPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();
        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito =?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        inserePaciente(tabelaPaciente, "Francisco", "2000", id_distrito, "Masculino", "Recuperado");
        cursor = tabelaPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();
        db.close();
    }

    @Test
    public void consegueAlterarPaciente() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdPaciente = openHelper.getWritableDatabase();

        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(bdPaciente);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(bdPaciente);

        Paciente paciente = new Paciente();
        paciente.setNome_paciente("Francisco");
        paciente.setAno_nascimento_paciente("2000");
        long id_distrito = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito = ?", new String[]{"Guarda"}, null, null, null).getColumnIndex("_id");
        paciente.setId_distrito(id_distrito);
        paciente.setGenero_paciente("Masculino");
        paciente.setEstado_paciente("Recuperado");
        long id = inserePaciente(tabelaPaciente, paciente);

        paciente.setNome_paciente("Ana");
        paciente.setAno_nascimento_paciente("1999");
        id_distrito = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito = ?", new String[]{"Lisboa"}, null, null, null).getColumnIndex("_id");
        paciente.setId_distrito(id_distrito);
        paciente.setGenero_paciente("Feminino");
        paciente.setEstado_paciente("Recuperado");
        int registosAlterados = tabelaPaciente.update(Converte.pacienteToContentValues(paciente), BdTabelPaciente._ID + "=?", new String[]{String.valueOf(id)});
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

        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito =?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        long id = inserePaciente(tabelaPaciente, "Francisco", "2000", id_distrito, "Masculino", "Recuperado");
        int registosApagados = tabelaPaciente.delete(BdTabelPaciente._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosApagados);
        db.close();
    }

    @Test
    public void consegueLerPaciente() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTabelPaciente tabelaPaciente = new BdTabelPaciente(db);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);

        Cursor cursor = tabelaPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();
        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito =?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        inserePaciente(tabelaPaciente, "Francisco", "2000", id_distrito, "Masculino", "Recuperado");
        cursor = tabelaPaciente.query(BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();
        db.close();
    }

    private long insereSuspeito(BdTableSuspeitos tabelaSuspeito, Suspeito suspeito) {
        long id = tabelaSuspeito.insert(Converte.suspeitoToContentValues(suspeito));
        assertNotEquals(-1, id);
        return id;
    }

    private long insereSuspeito(BdTableSuspeitos tabelaSuspeito, String nome, String ano_nascimento, long distrito, String genero) {
        Suspeito suspeito = new Suspeito();
        suspeito.setNomeSuspeito(nome);
        suspeito.setAno(ano_nascimento);
        suspeito.setIdDistrito(distrito);
        suspeito.setGenero(genero);
        return insereSuspeito(tabelaSuspeito, suspeito);
    }

    @Test
    public void concegueInserirSuspeito() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);
        BdTableSuspeitos tabelaSuspeito = new BdTableSuspeitos(db);
        getTableAsString(db, "Distrito");
        Cursor cursor = tabelaSuspeito.query(BdTableSuspeitos.TODOS_CAMPOS_SUSPEITO, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();
        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito =?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        insereSuspeito(tabelaSuspeito, "Miguel", "2000", id_distrito, "Masculino");
        cursor = tabelaSuspeito.query(BdTableSuspeitos.TODOS_CAMPOS_SUSPEITO, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();
        db.close();
    }

    @Test
    public void consegueAlterarSuspeito() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdSuspeito = openHelper.getWritableDatabase();

        BdTableSuspeitos tabelaSuspeito = new BdTableSuspeitos(bdSuspeito);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(bdSuspeito);

        Suspeito suspeito = new Suspeito();
        suspeito.setNomeSuspeito("Miguel");
        suspeito.setAno("2000");
        long id_distrito = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito = ?", new String[]{"Guarda"}, null, null, null).getColumnIndex("_id");
        suspeito.setIdDistrito(id_distrito);
        suspeito.setGenero("Masculino");
        long id = insereSuspeito(tabelaSuspeito, suspeito);

        suspeito.setNomeSuspeito("Rute");
        suspeito.setAno("1999");
        id_distrito = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito = ?", new String[]{"Lisboa"}, null, null, null).getColumnIndex("_id");
        suspeito.setIdDistrito(id_distrito);
        suspeito.setGenero("Feminino");
        int registosAlterados = tabelaSuspeito.update(Converte.suspeitoToContentValues(suspeito), BdTableSuspeitos._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAlterados);
        bdSuspeito.close();
    }

    @Test
    public void consegueApagarSuspeito() {
        Context appContext = getTargetContext();
        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();

        BdTableSuspeitos tabelaSuspeito = new BdTableSuspeitos(db);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);

        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito =?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        long id = insereSuspeito(tabelaSuspeito, "Miguel", "2000", id_distrito, "Masculino");
        int registosApagados = tabelaSuspeito.delete(BdTableSuspeitos._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosApagados);
        db.close();
    }

    @Test
    public void consegueLerSuspeito() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        BdTableSuspeitos tabelaSuspeito = new BdTableSuspeitos(db);
        BdTableDistrito tabelaDistrito = new BdTableDistrito(db);

        Cursor cursor = tabelaSuspeito.query(BdTableSuspeitos.TODOS_CAMPOS_SUSPEITO, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();
        Cursor cursor1 = tabelaDistrito.query(new String[]{"_id"}, "nome_distrito =?", new String[]{"Guarda"}, null, null, null);
        long id_distrito = -1;
        if(cursor1 != null && cursor1.moveToFirst())
            id_distrito = cursor1.getInt(cursor1.getColumnIndex("_id"));
        insereSuspeito(tabelaSuspeito, "Miguel", "2000", id_distrito, "Masculino");
        cursor = tabelaSuspeito.query(BdTableSuspeitos.TODOS_CAMPOS_SUSPEITO, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();
        db.close();
    }


}
