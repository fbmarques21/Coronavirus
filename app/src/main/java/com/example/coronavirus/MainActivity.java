package com.example.coronavirus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inserirPaciente(View view){
        Intent intentInserirPaciente = new Intent(this, DisplayInserirPaciente.class);
        startActivity(intentInserirPaciente);
    }
    public void consultarDados (View view){

        Intent intentDados = new Intent(this, ListaPaciente.class);
        startActivity(intentDados);
    }
    public void inserirSuspeito(View view){

        Intent intentInserirSuspeitos = new Intent(this, DisplayInserirSuspeito.class);
        startActivity(intentInserirSuspeitos);
    }
}
