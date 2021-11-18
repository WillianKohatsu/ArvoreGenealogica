package com.example.arvoregenealogica.db;

public class TipoParentesco {
    public static final String NOME_TABELA = "tipoParentesco";

    public static final String ID_TIPO_PARENTESCO = "id";
    public static final String NOME_TIPO_PARENTESCO = "nome_tipo_parentesco";

    private int id;
    private String nomeTipoParentesco;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + "("
                    + ID_TIPO_PARENTESCO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NOME_TIPO_PARENTESCO + " TEXT"
                    + ")";

    public TipoParentesco() {
    }

    public TipoParentesco(int id, String nomeTipoParentesco) {
        this.id = id;
        this.nomeTipoParentesco = nomeTipoParentesco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTipoParentesco() {
        return nomeTipoParentesco;
    }

    public void setNomeTipoParentesco(String nomeTipoParentesco) {
        this.nomeTipoParentesco = nomeTipoParentesco;
    }
}
