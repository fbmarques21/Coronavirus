package com.example.coronavirus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inserirPaciente(View view){

        Intent intentInserirPaciente = new Intent(this, AdicionarPacienteFragment.class);
        startActivity(intentInserirPaciente);
    }
    public void verDados (View view){

        Intent intentDados = new Intent(this, ListaPacienteFragment.class);
        startActivity(intentDados);
    }
    public void inserirSuspeitos(View view){

        Intent intentInserirSuspeitos = new Intent(this, DisplayInserirSuspeito.class);
        startActivity(intentInserirSuspeitos);
    }

}
