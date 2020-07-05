package com.example.coronavirus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class EliminaPacienteFragmnet extends Fragment {

    private TextView textViewNome;
    private TextView textViewAno;
    private TextView textViewGenero;
    private TextView textViewDistrito;
    private TextView textViewEstado;
    private Paciente paciente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eliminar_paciente, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);

        activity.setMenuActual(R.layout.fragment_eliminar_paciente);

        textViewNome = (TextView) view.findViewById(R.id.textViewEnome);
        textViewAno = (TextView) view.findViewById(R.id.textViewEano);
        textViewGenero = (TextView) view.findViewById(R.id.textViewEgenero);
        textViewDistrito = (TextView) view.findViewById(R.id.textViewEdistrito);
        textViewEstado = (TextView) view.findViewById(R.id.textViewEestado);

        Button buttonEliminar = (Button) view.findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        Button buttonCancelar = (Button) view.findViewById(R.id.buttonCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        paciente = activity.getPaciente();
        textViewNome.setText(paciente.getNomePaciente());
        textViewAno.setText(paciente.getAno());
        textViewGenero.setText(paciente.getGenero());
        textViewDistrito.setText(paciente.getDistrito());
        textViewEstado.setText(paciente.getEstado());
    }

    public void cancelar() {
        NavController navController = NavHostFragment.findNavController(EliminaPacienteFragmnet.this);
        navController.navigate(R.id.action_EliminaPaciente_to_ListaPaciente);
    }

    public void eliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Eliminar Paciente");
        builder.setMessage("Tem a certeza que pretende eliminar o paciente '" + paciente.getNomePaciente() + "'");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton("Sim, eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmaEliminar();
            }
        });

        builder.setNegativeButton("Não, cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // cancelar
            }
        });

        builder.show();
    }

    private void confirmaEliminar() {
        try {
            Uri enderecoPaciente = Uri.withAppendedPath(PacienteContentProvider.ENDERECO_PACIENTES, String.valueOf(paciente.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoPaciente, null, null);

            if (apagados == 1) {
                Toast.makeText(getContext(), "Paciente eliminado com sucesso", Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EliminaPacienteFragmnet.this);
                navController.navigate(R.id.action_EliminaPaciente_to_ListaPaciente);
                return;
            }
        } catch (Exception e) {
        }

        Snackbar.make(textViewNome, "Erro: Não foi possível eliminar o paciente", Snackbar.LENGTH_INDEFINITE).show();
    }

}