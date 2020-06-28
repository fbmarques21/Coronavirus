package com.example.coronavirus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class display_lista_paciente extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lista_paciente);

        this.listView = (ListView)findViewById(R.id.lista);

        ArrayList<Paciente> lista = new DBPaciente(this).procurar();
        PacienteAdapter pacienteAdapter = new PacienteAdapter(this, lista);
        this.listView.setAdapter(pacienteAdapter);
    }
}
