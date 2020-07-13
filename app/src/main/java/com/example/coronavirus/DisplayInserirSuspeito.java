package com.example.coronavirus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayInserirSuspeito extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_DISTRITO = 0;
    private Spinner spinnerDistrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_inserir_suspeito);
        Intent intentInserirSuspeitos = getIntent();

        Spinner dropdownGenero;
        dropdownGenero = (Spinner) findViewById(R.id.spinnerGenero);
        final List<String> genero = new ArrayList<>();
        genero.add(getString(R.string.GeneroF));
        genero.add(getString(R.string.GeneroM));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genero);
        dropdownGenero.setAdapter(adapter);

        spinnerDistrito = (Spinner) findViewById(R.id.spinnerDistrito);
        mostrarDadosSpinnerDistritoSuspeito(null);
        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_DISTRITO, null, this);
    }

    private void mostrarDadosSpinnerDistritoSuspeito(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,data,new String[]{BdTableDistrito.NOME_DISTRITO},new int[]{android.R.id.text1});
        spinnerDistrito.setAdapter(adapter);
    }

    public void NovoSuspeito(View view) {
        EditText TextEditNome = (EditText) findViewById(R.id.editTextNomeSuspeito);
        EditText TextEditAno = (EditText) findViewById(R.id.editTextAnoSuspeito);

        String nome = TextEditNome.getText().toString();
        String ano = TextEditAno.getText().toString();

        String genero = ((Spinner) findViewById(R.id.spinnerGenero)).getSelectedItem().toString();


        if (nome.length() == 0) {
            TextEditNome.setError(getString(R.string.campo_obrigatorio));
            TextEditNome.requestFocus();
            return;
        }
        else if ((ano.length() != 4)) {
            TextEditAno.setError(getString(R.string.campo_quatro_numeros));
            TextEditAno.requestFocus();
        }
        else if ((ano.length() < 1900) || (ano.length()>2020)) {
            TextEditAno.setError(getString(R.string.campo_entre));
            TextEditAno.requestFocus();
        }
        else {
            long idDistrito = spinnerDistrito.getSelectedItemId();

            Suspeito suspeito = new Suspeito();
            suspeito.setNomeSuspeito(nome);
            suspeito.setAno(ano);
            suspeito.setGenero(genero);
            suspeito.setIdDistrito(idDistrito);
            try {
                this.getContentResolver().insert(PacienteContentProvider.ENDERECO_SUSPEITOS, Converte.suspeitoToContentValues(suspeito));
                Toast.makeText(this, "Suspeito inserido com sucesso", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Suspeito não inserido ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_DISTRITO, null, this);
        super.onResume();
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new androidx.loader.content.CursorLoader(this,PacienteContentProvider.ENDERECO_DISTRITO,BdTableDistrito.TODOS_CAMPOS_DISTRITO,null,null,null);
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *  @param loader The Loader that has finished.
     *
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostrarDadosSpinnerDistritoSuspeito(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostrarDadosSpinnerDistritoSuspeito(null);
    }
}
