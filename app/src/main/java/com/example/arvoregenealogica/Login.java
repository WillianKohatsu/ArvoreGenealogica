package com.example.arvoregenealogica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private TextView txt_tela_cadastro;
    private EditText editEmail, editSenha;
    private Button btnLogin;
    private ProgressBar progressBar;
    String[] mensagem = {"Preencha todos os campos", "Login efetuado com sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        txt_tela_cadastro.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, FormCadastro.class);
            startActivity(intent); });

        btnLogin.setOnClickListener(view -> {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();

            if(email.isEmpty() || senha.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagem[1], Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else{
                autenticarUsuario(view);
            }
        });
    }

    private void autenticarUsuario(View view){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        telaPerfil();
                    }
                },3000);
            }else{
                String erro;
            try {
                throw task.getException();
            }catch (Exception e){
                erro = "Erro ao efetuar login";
            }
            Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
            snackbar.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(usuarioAtual != null){
            telaPerfil();
        }
    }

    private void telaPerfil(){
        Intent intent = new Intent(Login.this, TelaPerfil.class);
        startActivity(intent);
        finish();
    }

    private void iniciarComponentes(){
        txt_tela_cadastro = findViewById(R.id.txtTelaCadastro);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
    }

}