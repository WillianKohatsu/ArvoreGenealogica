package com.example.arvoregenealogica.db;

public class Parentesco {
    public static final String NOME_TABELA = "parentesco";

    public static final String ID_PARENTESCO = "id";
    public static final String ID_PESSOA = "id_pessoa";
    public static final String ID_PARENTE = "id_parente";
    public static final String ID_TIPO_PARENTESCO = "tipo_parentesco";

    private int id;
    private int idPessoa;
    private int idParente;
    private int idTipoParentesco;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + "("
                    + ID_PARENTESCO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ID_PESSOA + " INTEGER,"
                    + ID_PARENTE + " INTEGER,"
                    + ID_TIPO_PARENTESCO + " INTEGER"
                    + ")";

    public Parentesco() {
        id = 0;
        idPessoa = 0;
        idParente = 0;
        idTipoParentesco = 0;
    }

    public Parentesco(int id, int idPessoa, int idParente, int idTipoParentesco) {
        this.id = id;
        this.idPessoa = idPessoa;
        this.idParente = idParente;
        this.idTipoParentesco = idTipoParentesco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public int getIdParente() {
        return idParente;
    }

    public void setIdParente(int idParente) {
        this.idParente = idParente;
    }

    public int getIdTipoParentesco() {
        return idTipoParentesco;
    }

    public void setIdTipoParentesco(int idTipoParentesco) {
        this.idTipoParentesco = idTipoParentesco;
    }

}
