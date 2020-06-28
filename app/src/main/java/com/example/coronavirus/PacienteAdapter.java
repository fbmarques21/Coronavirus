package com.example.coronavirus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PacienteAdapter extends ArrayAdapter<Paciente> {
    private Context context;
    private ArrayList<Paciente> lista;

    public PacienteAdapter(Context context, ArrayList<Paciente> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Paciente itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_display_item, null);
        final View layout = convertView;

        TextView textView = (TextView) convertView.findViewById(R.id.textViewNomeP);
        textView.setText("Nome:" + itemPosicao.getNome());

        TextView textViewAno = (TextView) convertView.findViewById(R.id.textViewAnoP);
        textViewAno.setText("Ano:" + itemPosicao.getAno());


        Button buttonEdit = (Button) convertView.findViewById(R.id.buttonEditar);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DisplayInserirPaciente.class);
                intent.putExtra("_id",itemPosicao.get_id());
                intent.putExtra("nome", itemPosicao.getNome());
                intent.putExtra("ano",itemPosicao.getAno());
                context.startActivity(intent);
            }
        });

        Button buttonApagar = (Button) convertView.findViewById(R.id.buttonApagar);
        buttonApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBPaciente(context).delete(itemPosicao);
                layout.setVisibility(View.GONE);
            }
        });

        return convertView;
    }
}
