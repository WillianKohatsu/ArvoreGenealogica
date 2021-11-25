package com.example.arvoregenealogica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arvoregenealogica.db.DatabaseHelper;
import com.example.arvoregenealogica.db.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class ListaParente extends AppCompatActivity {

    public ListView listViewDados;
    public Button botao;
    public ArrayList<Integer> arrayIds;
    public Integer idSelecionado;
    private DatabaseHelper db = new DatabaseHelper(this);
    private List<Pessoa> parentes = db.getAllPessoas();
    public  ArrayList<String> nomesParentes = preencherDados(parentes);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        iniciarComponentes();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        listViewDados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                confirmaExcluir();
                return true;
            }
        });

        listViewDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idSelecionado = arrayIds.get(i);
                abrirTelaPerfil();
            }
        });

        listarDados();
    }

    @Override
    protected void onResume(){
        super.onResume();
        listarDados();
    }

    public void listarDados(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomesParentes);
        listViewDados.setAdapter(arrayAdapter);
    }

    private ArrayList<String> preencherDados(List<Pessoa> parentes) {
        ArrayList<String> dados = new ArrayList<String>();
        for (Pessoa pessoa:
                parentes) {
            dados.add(pessoa.getNome());
        }
        return dados;}

    public void abrirTelaCadastro(){
        Intent intent = new Intent(this,AddParente.class);
        startActivity(intent);
    }

    public void confirmaExcluir() {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Você realmente deseja excluir esse registro?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                excluir();
                listarDados();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        msgBox.show();
    }

    public void excluir(){
        //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show();
        try{
            db.deletePessoa(idSelecionado);
            listarDados();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void abrirTelaPerfil(){
        Intent intent = new Intent(this, Perfil.class);
        intent.putExtra("id",idSelecionado);
        startActivity(intent);
    }

    private void iniciarComponentes(){
        botao = (Button) findViewById(R.id.buttonAlterar);
        listViewDados = (ListView) findViewById(R.id.listViewDados);
    }
}
