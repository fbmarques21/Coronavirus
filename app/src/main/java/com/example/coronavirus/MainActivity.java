package com.example.coronavirus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{
    private Fragment fragmentActual = null;
    private int menuActual = R.menu.menu_lista_paciente;
    private Menu menu;
    private Paciente paciente = null;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setFragmentActual(Fragment fragmentActual) {
        this.fragmentActual = fragmentActual;
    }

    public void setMenuActual(int menuActual) {
        if (menuActual != this.menuActual) {
            this.menuActual = menuActual;
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void pacienteAlterado(Paciente paciente) {
        this.paciente = paciente;

        boolean mostraEditarEliminar = (paciente != null);

        menu.findItem(R.id.action_alterar_paciente).setVisible(mostraEditarEliminar);
        menu.findItem(R.id.action_eliminar_paciente).setVisible(mostraEditarEliminar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuActual, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (menuActual == R.menu.menu_lista_paciente) {
            if (processaOpcoesMenuListaPaciente(id)) return true;
        } else if (menuActual == R.menu.menu_inserir_paciente) {
            if (processaOpcoesMenuInserirPaciente(id)) return true;
        } else if (menuActual == R.menu.menu_alterar_paciente) {
            if (processaOpcoesMenuAlterarPaciente(id)) return true;
        } else if (menuActual == R.menu.menu_eliminar_paciente) {
            if (processaOpcoesMenuEliminarPaciente(id)) return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean processaOpcoesMenuEliminarPaciente(int id) {
        EliminaPacienteFragmnet eliminarPacienteFragment = (EliminaPacienteFragmnet) fragmentActual;

        if (id == R.id.action_cancelar) {
            eliminarPacienteFragment.cancelar();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuAlterarPaciente(int id) {
        AlteraPacienteFragment alterarPacienteFragment = (AlteraPacienteFragment) fragmentActual;

        if (id == R.id.action_guardar) {
            alterarPacienteFragment.guardar();
            return true;
        } else if (id == R.id.action_cancelar) {
            alterarPacienteFragment.cancelar();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuInserirPaciente(int id) {
        AdicionarPacienteFragment adicionarPacienteFragment = (AdicionarPacienteFragment) fragmentActual;

        if (id == R.id.action_guardar) {
            adicionarPacienteFragment.guardar();
            return true;
        } else if (id == R.id.action_cancelar) {
            adicionarPacienteFragment.cancelar();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuListaPaciente(int id) {
        ListaPacienteFragment listaPacienteFragment = (ListaPacienteFragment) fragmentActual;

        if (id == R.id.action_inserir_livro) {
            listaPacienteFragment.novoLivro();
            return true;
        } else if (id == R.id.action_alterar_livro) {
            listaPacienteFragment.alteraLivro();
            return true;
        } else if (id == R.id.action_eliminar_livro) {
            listaPacienteFragment.eliminaLivro();
            return true;
        }
    }

}
