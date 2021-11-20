package com.example.arvoregenealogica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class Parente extends AppCompatActivity {

    private EditText nomeParente, parentesco, generoParente, dataNascimento;
    private Button btnAdicionar;
    String[] genero = {"Masculino", "Feminino", "Outros"};
    String[] mensagem = {"Preencha todos os campos", "Parente adicionado com sucesso"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parente);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item,genero);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });

        btnAdicionar.setOnClickListener(view -> {
            String nome = nomeParente.getText().toString();
            String grau = parentesco.getText().toString();
            String genero = generoParente.getText().toString();
            String data = dataNascimento.getText().toString();

            if(nome.isEmpty() || genero.isEmpty() || grau.isEmpty() || data.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagem[0], Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else{
                cadastrarParente(view);
            }
        });
    }

    private void cadastrarParente(View v){

        Snackbar snackbar = Snackbar.make(v, mensagem[1], Snackbar.LENGTH_SHORT);
        snackbar.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                telaArvore();
            }
        },3000);

    }

    private void telaArvore(){
        Intent intent = new Intent(Parente.this, Arvore.class);
        startActivity(intent);
        finish();
    }

    private void iniciarComponentes(){
        nomeParente = findViewById(R.id.editNome);
        parentesco = findViewById(R.id.editParentesco);
        generoParente = findViewById(R.id.editGenero);
        dataNascimento = findViewById(R.id.editData);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        autoCompleteTextView = findViewById(R.id.auto);
    }
}