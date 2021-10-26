package com.example.arvoregenealogica.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.arvoregenealogica.R;


public class MainActivity extends BaseActivity {

    private TextView tvNot;
    private TextView tvHave;
    private TextView tvNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, FamilyTreeActivity.class));

    }
}
