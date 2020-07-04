package com.example.coronavirus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
    @Test
    public void consegueAbrirBaseDados() {
        // Context of the app under test.
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdPaciente = openHelper.getReadableDatabase();
        assertTrue(bdPaciente.isOpen());
        bdPaciente.close();
    }

    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    private long insereGenero(BdTabelGenero tabelaGenero, Genero genero) {
        long id = tabelaGenero.insert(Converte.generoToContentValues(genero));
        assertNotEquals(-1, id);

        return id;
    }

    private long insereGenero(BdTabelGenero tabelaGenero, String descricao) {
        Genero genero = new Genero();
        genero.setDescricao(descricao);

        return insereGenero(tabelaGenero, genero);
    }

    @Test
    public void consegueInserirGenero() {
        Context appContext = getTargetContext();

        BdPacienteOpenHelper openHelper = new BdPacienteOpenHelper(appContext);
        SQLiteDatabase bdPaciente = openHelper.getWritableDatabase();

        BdTabelGenero tabelaGenero = new BdTabelGenero(bdPaciente);

        insereGenero(tabelaGenero, "Masculino");

        bdPaciente.close();
    }
}
