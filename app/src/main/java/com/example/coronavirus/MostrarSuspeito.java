package com.example.coronavirus;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MostrarSuspeito extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String ID_SUSPEITO = "ID_SUSPEITO";
    public static final int ID_CURSOR_LOADER_SUSPEITO = 0;
    private AdaptadorSuspeito adaptadorSuspeito;
    private RecyclerView recyclerViewSuspeito;

    private Suspeito suspeito = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_suspeito);
        Intent intentMostrarSuspeito = getIntent();

        recyclerViewSuspeito = (RecyclerView) findViewById(R.id.RecyclerViewSuspeitos);
        adaptadorSuspeito = new AdaptadorSuspeito(this);
        recyclerViewSuspeito.setAdapter(adaptadorSuspeito);
        recyclerViewSuspeito.setLayoutManager(new LinearLayoutManager(this));

        adaptadorSuspeito.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SUSPEITO, null, this);
    }

    public void inserirSuspeito (View view){
        Intent intentInserirSuspeito = new Intent(this, DisplayInserirSuspeito.class);
        startActivity(intentInserirSuspeito);
    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_SUSPEITO,null,this);
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
        return  new androidx.loader.content.CursorLoader(this, PacienteContentProvider.ENDERECO_SUSPEITOS, BdTableSuspeitos.TODOS_CAMPOS_SUSPEITO, null, null, BdTableSuspeitos.NOME_SUSPEITO);
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
        adaptadorSuspeito.setCursor(data);
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
        adaptadorSuspeito.setCursor(null);
    }


    public void eliminarSuspeito(View view) {
        try {
            Uri enderecoLivro = Uri.withAppendedPath(PacienteContentProvider.ENDERECO_SUSPEITOS, String.valueOf(suspeito.getId()));

            SQLiteDatabase db = openOrCreateDatabase("suspeito.db", Context.MODE_PRIVATE, null);
            db.delete("suspeito", "id=?", new String[] {String.valueOf(enderecoLivro)});
            db.close();
            Toast.makeText(getApplicationContext(), "Suspeito eliminado com sucesso", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MostrarSuspeito.class));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao eliminar", Toast.LENGTH_SHORT).show();
        }
    }

}