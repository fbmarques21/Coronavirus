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

    @Test
    public void consegueAbrirBaseDados(){
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bd = openHelper.getReadableDatabase();
        assertTrue(bd.isOpen());
        bd.close();
    }

    private long insereDistrito(BdTableDistrito tabelaDistrito, Distrito distrito) {
        long id = tabelaDistrito.insert(Converte.distritoToContentValues(distrito));
        assertNotEquals(-1, id);

        return id;
    }

    private long insereDistrito(BdTableDistrito tabelaDistrito, String nomeDistrito) {
        Distrito distrito = new Distrito();
        distrito.setNome_distrito(nomeDistrito);

        return insereDistrito(tabelaDistrito, distrito);
    }

    @Test
    public void consegueInserirCategorias() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdPaciente = openHelper.getWritableDatabase();

        BdTableDistrito tabelaDistrito = new BdTableDistrito(bdPaciente);

        insereDistrito(tabelaDistrito, "Guarda");

        bdPaciente.close();
    }
}
