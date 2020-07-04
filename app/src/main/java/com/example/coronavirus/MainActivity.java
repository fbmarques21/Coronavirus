package com.example.coronavirus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    private Fragment fragmentActual = null;
    private int menuActual = R.menu.menu_lista_paciente;
    private Menu menu;
    private Paciente paciente = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void inserirSuspeito(View view) {
        Intent intentInsert = new Intent(this,DisplayInserirSuspeito.class);
        startActivity(intentInsert);
    }

    public void consultarDados(View view) {
        Intent intentDados = new Intent(this,display_lista_paciente.class);
        startActivity(intentDados);
    }

    public void pacienteAlterado(Paciente paciente) {

    }
}
