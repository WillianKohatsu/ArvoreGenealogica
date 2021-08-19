package com.example.arvoregenealogica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private TextView txt_tela_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        iniciarComponentes();

        txt_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, FormCadastro.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarComponentes(){
        txt_tela_cadastro = findViewById(R.id.txtTelaCadastro);
    }

}