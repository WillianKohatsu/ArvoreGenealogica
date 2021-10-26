package com.example.arvoregenealogica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parente extends AppCompatActivity {

    private EditText nomeParente, sobreomeParente, localNascimento, dataNascimento;
    private Button btnAdicionar;
    String[] mensagem = {"Preencha todos os campos", "Parente adicionado com sucesso"};
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parente);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        btnAdicionar.setOnClickListener(view -> {
            String nome = nomeParente.getText().toString();
            String sobrenome = sobreomeParente.getText().toString();
            String local = localNascimento.getText().toString();
            String data = dataNascimento.getText().toString();

            if(nome.isEmpty() || sobrenome.isEmpty() || local.isEmpty() || data.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagem[0], Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else{
                cadastrarParente(view);
            }
        });
    }

    private void cadastrarParente(View v){
        /* String nome = nomeParente.getText().toString();
        String sobrenome = sobreomeParente.getText().toString();
        String local = localNascimento.getText().toString();
        String data = dataNascimento.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> usuarios = new HashMap<>();
        usuarios.put("nome", nome);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao salvar dados"+e.toString());
                    }
                });  */
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
        sobreomeParente = findViewById(R.id.editSobrenome);
        localNascimento = findViewById(R.id.editLocal);
        dataNascimento = findViewById(R.id.editData);
        btnAdicionar = findViewById(R.id.btnAdicionar);
    }
}