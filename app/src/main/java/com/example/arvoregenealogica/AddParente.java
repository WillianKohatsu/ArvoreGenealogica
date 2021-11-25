package com.example.arvoregenealogica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.arvoregenealogica.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Objects;

public class AddParente extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private DatabaseHelper db;
    private EditText nomeParente, parentesco;
    private Button btnAdicionar;
    String[] genero = {"Masculino", "Feminino", "Outros"};
    String[] mensagem = {"Preencha todos os campos", "Parente adicionado com sucesso"};

    String nome = nomeParente.getText().toString();
    String titulo = parentesco.getText().toString();
    String dtNasc, gen;
    String imagem = "";

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

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String generos = adapterView.getItemAtPosition(i).toString();
                gen = getGenero(generos);
            }
        });

        btnAdicionar.setOnClickListener(view -> {

            if(nome.isEmpty() || dtNasc.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, mensagem[0], Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else{
                cadastrarParente(view);
            }
        });
    }

    private void cadastrarParente(View v){
        db = new DatabaseHelper(this);
        db.insertPessoa(nome, titulo, imagem, gen, dtNasc);
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
        Intent intent = new Intent(AddParente.this, Arvore.class);
        startActivity(intent);
        finish();
    }

    private void iniciarComponentes(){
        nomeParente = findViewById(R.id.editNome);
        parentesco = findViewById(R.id.editParentesco);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        autoCompleteTextView = findViewById(R.id.auto);
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                dtNasc = date;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEV";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "ABR";
        if(month == 5)
            return "MAI";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AGO";
        if(month == 9)
            return "SET";
        if(month == 10)
            return "OUT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEZ";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public String getGenero( String g) {
        return g;
    }
}