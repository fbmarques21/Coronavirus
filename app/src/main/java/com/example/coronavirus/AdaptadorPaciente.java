package com.example.coronavirus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class AdaptadorPaciente extends RecyclerView.Adapter<AdaptadorPaciente.ViewHolderPaciente> {
    private final Context context;
    private Cursor cursor = null;

    public AdaptadorPaciente(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if (cursor != this.cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolderPaciente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPaciente = LayoutInflater.from(context).inflate(R.layout.item_paciente, parent, false);
        return new ViewHolderPaciente(itemPaciente);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPaciente holder, int position) {
        cursor.moveToPosition(position);
        Paciente paciente = Converte.cursorToPaciente(cursor);
        holder.setPaciente(paciente);
    }

    @Override
    public int getItemCount() {
        if(cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    private ViewHolderPaciente viewHolderPacienteSelecionado = null;

    public Paciente getPacienteSelecionado() {
        if (viewHolderPacienteSelecionado == null) return null;
        return viewHolderPacienteSelecionado.paciente;
    }

    public class ViewHolderPaciente extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Paciente paciente = null;

        private final TextView textViewNome;
        private final TextView textViewAno;
        private final TextView textViewGenero;
        private final TextView textViewDistrito;
        private final TextView textViewEstado;

        public ViewHolderPaciente(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView)itemView.findViewById(R.id.textViewNomePaciente);
            textViewAno = (TextView)itemView.findViewById(R.id.textViewAnoNascimentoPaciente);
            textViewGenero = (TextView)itemView.findViewById(R.id.textViewGeneroPaciente);
            textViewDistrito = (TextView)itemView.findViewById(R.id.textViewDistritoPaciente);
            textViewEstado = (TextView)itemView.findViewById(R.id.textViewEstadoPaciente);
            itemView.setOnClickListener(this);
        }

        public void setPaciente(Paciente paciente) {
            this.paciente = paciente;
            textViewNome.setText(paciente.getNome_paciente());
            textViewAno.setText(String.valueOf(paciente.getAno_nascimento_paciente()));
            textViewGenero.setText(paciente.getGenero_paciente());
            textViewDistrito.setText(String.valueOf(paciente.getId_distrito()));
            textViewEstado.setText(paciente.getEstado_paciente());
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (viewHolderPacienteSelecionado == this) {
                return;
            }

            if (viewHolderPacienteSelecionado != null) {
                viewHolderPacienteSelecionado.desSeleciona();
            }

            viewHolderPacienteSelecionado = this;
            seleciona();
            MostrarPaciente mostrarPaciente = (MostrarPaciente) AdaptadorPaciente.this.context;
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorAccent);
        }

        private void desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
    }
}
