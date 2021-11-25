package com.example.arvoregenealogica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arvoregenealogica.db.DatabaseHelper;
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

public class FormCadastro extends AppCompatActivity {

    private DatabaseHelper dbh;
    private EditText editNome, editEmail, editSenha;
    private Button btnCadastrar;
    String[] mensagem = {"Preencha todos os campos", "Cadastro realizado com sucesso"};
    String usuarioId, titulo = "Eu";
    String dtNasc, gen;
    String imagem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        btnCadastrar.setOnClickListener(view -> {
            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagem[0], Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else{
                cadastrarUsuario(view);
            }
        });
    }

    private void cadastrarUsuario(View v){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                salvarDadosUsuario();
                Snackbar snackbar = Snackbar.make(v, mensagem[1], Snackbar.LENGTH_SHORT);
                snackbar.show();
                Intent intent = new Intent(FormCadastro.this, Login.class);
                startActivity(intent);
                finish();
            }else{
                String erro;
                try {
                    throw task.getException();
                }catch (FirebaseAuthWeakPasswordException e) {
                    erro = "Digite uma senha com no mímino 6 caracteres";
                }catch (FirebaseAuthUserCollisionException e) {
                    erro = "O email já está cadastrado";
                }catch (FirebaseAuthInvalidCredentialsException e){
                    erro = "Email inválido";
                }catch (Exception e){
                    erro = "Erro ao cadastrar usuário";
                }

                Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }

        });
    }

    private void salvarDadosUsuario(){
        String nome = editNome.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> usuarios = new HashMap<>();
        usuarios.put("nome", nome);

        //cadastro sqplite
        dbh = new DatabaseHelper(this);
        dbh.insertPessoa(nome, titulo, imagem, gen, dtNasc);

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
                });

    }

    private void iniciarComponentes(){
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
    }
}