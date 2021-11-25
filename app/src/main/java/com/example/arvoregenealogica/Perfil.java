package com.example.arvoregenealogica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arvoregenealogica.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class Perfil extends AppCompatActivity {

    private Button btnEdit, btnAddParentesco;
    private TextView perfilNome, perfilTitulo, perfilGenero, perfilDtNasc;
    private DatabaseHelper db = new DatabaseHelper(this);
    public Integer id;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Objects.requireNonNull(getSupportActionBar()).hide();

        iniciarComponentes();

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        carregarDadosPerfil();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Editar.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        btnAddParentesco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, AddParentesco.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
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
        try{
            db.deletePessoa(id);
            Intent intent = new Intent(Perfil.this, ListaParente.class);
            startActivity(intent);
            finish();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void carregarDadosPerfil(){
        perfilNome.setText(db.getPessoa(id).getNome());
        perfilTitulo.setText(db.getPessoa(id).getTitulo());
        perfilGenero.setText(db.getPessoa(id).getGenero());
        perfilDtNasc.setText(db.getPessoa(id).getDtNasc());
    }

    private void iniciarComponentes(){
        perfilNome = findViewById(R.id.perfilNome);
        perfilTitulo = findViewById(R.id.perfilTitulo);
        perfilGenero = findViewById(R.id.perfilGenero);
        perfilDtNasc = findViewById(R.id.perfilDtNasc);
        btnEdit = findViewById(R.id.btnEdit);
        btnAddParentesco = findViewById(R.id.btnAddParent);;
    }
}
