package com.example.coronavirus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

public class ListaPaciente extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int ID_CURSOR_LOADER_PACIENTE = 0;
    private AdaptadorPaciente adaptadorPaciente;
    private RecyclerView recyclerViewPacientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_ver_estatisticas);
        Intent intentDados = getIntent();

        recyclerViewPacientes = (RecyclerView) findViewById(R.id.RecyclerViewPacientes);
        adaptadorPaciente = new AdaptadorPaciente(this);
        recyclerViewPacientes.setAdapter(adaptadorPaciente);
        recyclerViewPacientes.setLayoutManager(new LinearLayoutManager(this));

        adaptadorPaciente.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_PACIENTE, null, this);
    }

    public void NovoPaciente(View view){
        Intent intentcriar = new Intent(this, DisplayInserirPaciente.class);
        startActivity(intentcriar);
    }

    @Override
    protected void onResume(){
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_PACIENTE,null, this);
        super.onResume();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, PacienteContentProvider.ENDERECO_PACIENTES, BdTabelPaciente.TODOS_CAMPOS_PACIENTE, null, null, BdTabelPaciente.NOME_PACIENTE);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorPaciente.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorPaciente.setCursor(null);
    }
}
