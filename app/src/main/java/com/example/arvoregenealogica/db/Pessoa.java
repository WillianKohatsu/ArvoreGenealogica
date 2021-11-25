package com.example.arvoregenealogica.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Parentesco mae;
    private Parentesco pai;
    private Parentesco conjuge;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + "("
                    + ID_PESSOA + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NOME + " TEXT,"
                    + TITULO + " TEXT,"
                    + IMAGEM + " TEXT,"
                    + GENERO + " TEXT,"
                    + DT_NASC + " TEXT"
                    + ")";

    public Pessoa() {
    }

    public Pessoa(int id, String nome, String titulo, String imagem, String genero, String dtNasc) {
        this.id = id;
        this.nome = nome;
        this.titulo = titulo;
        this.imagem = imagem;
        this.genero = genero;
        this.dtNasc = dtNasc;
    }

    public Pessoa(String nome, String titulo, String imagem, String genero, String dtNasc) {
        this.nome = nome;
        this.imagem = imagem;
        this.genero = genero;
        this.dtNasc = dtNasc;
    }

    public void popularParentescos(List<Parentesco> parentescos){
        for (Parentesco parentesco:
                parentescos) {
            switch (parentesco.getIdTipoParentesco()){
                case TipoParentesco.mae:
                    setMae(parentesco);
                    break;
                case TipoParentesco.pai:
                    setPai(parentesco);
                    break;
                case TipoParentesco.conjuge:
                    setConjuge(parentesco);
                    break;
            }
        }
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

    public Parentesco getMae() {
        return mae;
    }

    public void setMae(Parentesco mae) {
        this.mae = mae;
    }

    public Parentesco getPai() {
        return pai;
    }

    public void setPai(Parentesco pai) {
        this.pai = pai;
    }

    public Parentesco getConjuge() {
        return conjuge;
    }

    public void setConjuge(Parentesco conjuge) {
        this.conjuge = conjuge;
    }

}
