package com.example.coronavirus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AdicionarPacienteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER = 0;
    private EditText editTextNome;
    private EditText editTextAno;
    private Spinner spinnerGenero;
    private Spinner spinnerDistrito;
    private Spinner spinnerEstado;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adicionar_paciente, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_inserir_paciente);

        editTextNome = (EditText) view.findViewById(R.id.editTextNome);
        editTextAno = (EditText) view.findViewById(R.id.editTextAno);
        spinnerGenero = (Spinner) view.findViewById(R.id.spinnerGenero);
        spinnerDistrito = (Spinner) view.findViewById(R.id.spinnerDistrito);
        spinnerEstado = (Spinner) view.findViewById(R.id.spinnerEstado);

        mostraDadosSpinnerGenero(null);
        mostraDadosSpinnerDistrito(null);
        mostraDadosSpinnerEstado(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER, null, this);
    }

    public void cancelar() {
        NavController navController = NavHostFragment.findNavController(AdicionarPacienteFragment.this);
        navController.navigate(R.id.action_NovoPaciente_to_ListaPaciente);
    }

    public void guardar() {
        String nome = editTextNome.getText().toString();

        if (nome.length() == 0) {
            editTextNome.setError("Preencha o nome");
            editTextNome.requestFocus();
            return;
        }

        String ano = editTextAno.getText().toString();

        if (ano.length() == 0) {
            editTextAno.setError("Preencha o ano");
            editTextAno.requestFocus();
            return;
        }

        long idGenero = spinnerGenero.getSelectedItemId();
        long idDistrito = spinnerDistrito.getSelectedItemId();
        long idEstado = spinnerEstado.getSelectedItemId();

        Paciente paciente = new Paciente();
        paciente.setNomePaciente(nome);
        paciente.setAno(ano);
        paciente.setIdGenero(idGenero);
        paciente.setIdDistrito(idDistrito);
        paciente.setIdEstado(idEstado);

        try {
            getActivity().getContentResolver().insert(PacienteContentProvider.ENDERECO_PACIENTES, Converte.pacienteToContentValues(paciente));
            Toast.makeText(getContext(), "Paciente adicionado com sucesso", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(AdicionarPacienteFragment.this);
            navController.navigate(R.id.action_NovoPaciente_to_ListaPaciente);
        } catch (Exception e) {
            Snackbar.make(editTextNome, "Erro: Não foi possível criar o paciente", Snackbar.LENGTH_INDEFINITE).show();
        }
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
        return new androidx.loader.content.CursorLoader(getContext(), PacienteContentProvider.ENDERECO_DISTRITO, BdTableDistrito.TODOS_CAMPOS, null, null, BdTableDistrito.CAMPO_DESCRICAO);
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
        mostraDadosSpinnerGenero(null);
        mostraDadosSpinnerDistrito(null);
        mostraDadosSpinnerEstado(null);
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
        mostraDadosSpinnerGenero(null);
        mostraDadosSpinnerDistrito(null);
        mostraDadosSpinnerEstado(null);
    }

    private void mostraDadosSpinnerGenero(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTabelGenero.CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );

        spinnerGenero.setAdapter(adapter);
    }

    private void mostraDadosSpinnerDistrito(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTableDistrito.CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );

        spinnerDistrito.setAdapter(adapter);
    }

    private void mostraDadosSpinnerEstado(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTableEstado.CAMPO_DESCRICAO},
                new int[]{android.R.id.text1}
        );

        spinnerEstado.setAdapter(adapter);
    }
}