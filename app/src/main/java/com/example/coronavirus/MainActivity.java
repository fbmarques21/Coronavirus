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

    public void mostrarPaciente(View view){
        Intent intentMostrarPaciente = new Intent(this, MostrarPaciente.class);
        startActivity(intentMostrarPaciente);
    }
    public void verEstatisticas (View view){

        Intent intentEstatistica = new Intent(this, DisplayVerEstatistica.class);
        startActivity(intentEstatistica);
    }
    public void mostrarSuspeito(View view){

        Intent intentMostrarSuspeitos = new Intent(this, MostrarSuspeito.class);
        startActivity(intentMostrarSuspeitos);
    }
}
