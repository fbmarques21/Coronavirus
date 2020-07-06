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
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DisplayInserirPaciente extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Spinner spinnerDistrito;
    public static final int ID_CURSOR_LOADER_DISTRITO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_inserir_paciente);

        Intent intentInserirPaciente = getIntent();

        spinnerDistrito = (Spinner) findViewById(R.id.spinnerDistrito);
        mostrarDadosSpinnerDistrito(null);
        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_DISTRITO, null, this);
    }

    private void mostrarDadosSpinnerDistrito(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                data,
                new String[]{BdTableDistrito.NOME_DISTRITO},
                new int[]{android.R.id.text1}
        );
        spinnerDistrito.setAdapter(adapter);
    }


    public void NovoPaciente(View view) {
        EditText EditTextNome = (EditText) findViewById(R.id.editTextAno);
        String Nome = EditTextNome.getText().toString();

        if (Nome.length() < 1) {
            EditTextNome.setError(getString(R.string.campo_obrigatorio));
            EditTextNome.requestFocus();
            return;
        }

        EditText EditTextAnoNascimento = (EditText) findViewById(R.id.editTextAno);
        String Ano = EditTextAnoNascimento.getText().toString();

        if (Ano.length() < 10) {
            EditTextAnoNascimento.setError(getString(R.string.campo_obrigatorio));
            EditTextAnoNascimento.requestFocus();
            return;
        }

        long idDistrito = spinnerDistrito.getSelectedItemId();

        Paciente paciente = new Paciente();
        paciente.setNomePaciente(Nome);
        paciente.setAno("2000");
        paciente.setGenero("Masculino");
        paciente.setDistrito("Guarda");
        paciente.setEstado("Recuperado");

        try{
            this.getContentResolver().insert(PacienteContentProvider.ENDERECO_DISTRITO, Converte.pacienteToContentValues(paciente));
            Toast.makeText(this, "Paciente Inserida", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Erro de Inserção", Toast.LENGTH_SHORT).show();
        }

        Intent intentPaciente = new Intent(this, ListaPaciente.class);
        startActivity(intentPaciente);
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
        return new androidx.loader.content.CursorLoader(this, PacienteContentProvider.ENDERECO_DISTRITO, BdTableDistrito.TODOS_CAMPOS_DISTRITO,null,null,null);
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
        mostrarDadosSpinnerDistrito(data);
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
        mostrarDadosSpinnerDistrito(null);
    }

}