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


}
