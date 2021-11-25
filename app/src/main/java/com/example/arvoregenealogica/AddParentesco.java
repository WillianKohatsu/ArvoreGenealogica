package com.example.arvoregenealogica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvoregenealogica.db.DatabaseHelper;
import com.example.arvoregenealogica.db.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class AddParentesco extends AppCompatActivity {

    private Button buttonAdd;
    private String tipoPar;
    private DatabaseHelper db = new DatabaseHelper(this);
    public Integer id, idPar;
    String[] parentesco = {"Pai", "Mãe", "Conjuge"};
    private List<Pessoa> parentes = db.getAllPessoas();
    public ArrayList<String> nomesParentes = preencherDados(parentes);

    AutoCompleteTextView autoCompleteTextView, autoCompleteTextView2;
    ArrayAdapter<String> adapterItem, adapterName;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentesco);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        iniciarComponentes();

        adapterName = new ArrayAdapter<String>(this, R.layout.list_item,nomesParentes);
        autoCompleteTextView2.setAdapter(adapterName);

        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item,parentesco);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tipo = adapterView.getItemAtPosition(i).toString();
                switch (tipo){
                    case "Pai" : parentescoID(1);break;
                    case "Mãe" : parentescoID(2);break;
                    case "Conjuge" : parentescoID(3);break;
                }
            }
        });

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tipo1 = adapterView.getItemAtPosition(i).toString();
                getParentId(i);

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addParentesco(tipoPar);
            }
        });
    }

    private ArrayList<String> preencherDados(List<Pessoa> parentes) {
        ArrayList<String> dados = new ArrayList<String>();
        for (Pessoa pessoa:
                parentes) {
            dados.add(pessoa.getNome());
        }
        return dados;}

    private void parentescoID(int idParente){
        switch (idParente){
            case 1 : tipoPar = "pai"; break;
            case 2 : tipoPar = "mae"; break;
            case 3 : tipoPar = "conjuge"; break;
        }
    }

    private void getParentId(int id){
        idPar = id;
    }

    private void addParentesco(String tipo){
        switch (tipo){
            case "pai" : db.insertParentescoPai(id, idPar);break;
            case "mae" : db.insertParentescoMae(id, idPar);break;
            case "conjuge" : db.insertParentescoConjuge(id, idPar);break;
        }
    }


    private void iniciarComponentes(){
        buttonAdd = findViewById(R.id.btnAddPar);
        autoCompleteTextView2 = findViewById(R.id.auto2);
        autoCompleteTextView = findViewById(R.id.auto);
    }
}
