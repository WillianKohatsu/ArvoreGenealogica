package com.example.arvoregenealogica;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Perfil extends AppCompatActivity {
    public Integer id;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
    }
}
