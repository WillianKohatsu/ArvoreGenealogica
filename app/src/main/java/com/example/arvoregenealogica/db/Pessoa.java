package com.example.arvoregenealogica.db;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pessoa {
    public static final String NOME_TABELA = "pessoa";

    public static final String ID_PESSOA = "id";
    public static final String NOME = "nome";
    public static final String TITULO = "titulo";
    public static final String IMAGEM = "imagem";
    public static final String GENERO = "genero";
    public static final String DT_NASC = "dt_nasc";

    private int id;

    private String nome;
    private String titulo;
    private String imagem;
    private String genero;
    private String dtNasc;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + "("
                    + ID_PESSOA + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NOME + " TEXT,"
                    + TITULO + " TEXT,"
                    + IMAGEM + " TEXT,"
                    + GENERO + " TEXT,"
                    + DT_NASC + " DATE"
                    + ")";

    public Pessoa() {
    }

    public Pessoa(int id, String nome, String titulo, String imagem, String genero, String dtNasc) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.genero = genero;
        this.dtNasc = dtNasc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getImagem() { return imagem; }

    public void setImagem(String imagem) { this.imagem = imagem; }

    public String getGenero() { return genero; }

    public void setGenero(String genero) { this.genero = genero; }

    public String getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(String dtNasc) {
        this.dtNasc = dtNasc;
    }

}
