package com.example.arvoregenealogica;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvoregenealogica.db.DatabaseHelper;
import com.example.arvoregenealogica.db.Pessoa;
import com.example.arvoregenealogica.utils.ToastMaster;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddParentesco extends AppCompatActivity {

    private Button buttonAdd;
    private String tipoPar;
    private DatabaseHelper db = new DatabaseHelper(this);
    public Integer id, idPar;
    private ArrayList<String> parentesco = new ArrayList<>();//{"Pai", "Mãe", "Conjuge"};
    private ArrayList<Pessoa> parentes = new ArrayList<>();
    private List<Pessoa> parentes2 = new ArrayList<>();
    public ArrayList<String> nomesParentes = new ArrayList<>();
    private Pessoa pessoa = new Pessoa();

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
                if(idPar != null && tipoPar != null){
                    addParentesco(tipoPar);
                    buttonAdd.setEnabled(false);
                    Snackbar snackbar = Snackbar.make(view, "Parentesco criado com sucesso", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },3000);
                }
                else{
                    Snackbar snackbar = Snackbar.make(view, "É necessário preencher os campos", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    private ArrayList<String> preencherDados(List<Pessoa> parentes) {
        ArrayList<String> dados = new ArrayList<String>();
        for (Pessoa pessoa:
                parentes) {
            if(pessoa.getId()!=id)
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
        idPar = parentes.get(id).getId();
    }

    private void addParentesco(String tipo){
        switch (tipo){
            case "pai" : db.insertParentescoPai(id, idPar); break;
            case "mae" : db.insertParentescoMae(id, idPar);break;
            case "conjuge" : db.insertParentescoConjuge(id, idPar); break;
        }
    }


    private void iniciarComponentes(){
        buttonAdd = findViewById(R.id.btnAddPar);
        autoCompleteTextView2 = findViewById(R.id.auto2);
        autoCompleteTextView = findViewById(R.id.auto);
        parentes2 = db.getAllPessoas();
        String test = "";
        pessoa = db.getPessoa(id);
        pessoa.popularParentescos(db.getAllParentescosByIdPessoa(id));
        if(pessoa.getPai() == null) parentesco.add("Pai");
        if(pessoa.getMae() == null) parentesco.add("Mãe");
        if(pessoa.getConjuge() == null) parentesco.add("Conjuge");
        try {
            for (Pessoa p :
                    parentes2) {
                int idPai = (pessoa.getPai() == null?0:pessoa.getPai().getIdParente()),
                        idMae = (pessoa.getMae()==null?0:pessoa.getMae().getIdParente()),
                        idConjuge = (pessoa.getConjuge()==null?0:pessoa.getConjuge().getIdParente()),
                        idPessoa = p.getId();


                if (idPessoa != idPai &&
                    idPessoa != idMae &&
                    idPessoa!= idConjuge)
                        parentes.add(p);
            }
        }
        catch(Exception e){
            String msg = e.getMessage();
        }
        nomesParentes = preencherDados(parentes);
    }
}
