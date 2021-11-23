package com.example.arvoregenealogica.db;

public class TipoParentesco {
    public static final String NOME_TABELA = "tipoParentesco";

    public static final String ID_TIPO_PARENTESCO = "id";
    public static final String NOME_TIPO_PARENTESCO = "nome_tipo_parentesco";
    public static final int mae = 1;
    public static final int pai = 2;
    public static final int conjuge = 3;

    private int id;
    private String nomeTipoParentesco;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + NOME_TABELA + "("
                    + ID_TIPO_PARENTESCO + " INTEGER PRIMARY KEY,"
                    + NOME_TIPO_PARENTESCO + " TEXT"
                    + ")";

    public static final String INSERT_VALORES =
            "INSERT INTO " +NOME_TABELA + "("+ ID_TIPO_PARENTESCO +","+ NOME_TIPO_PARENTESCO+ ") VALUES"
                    + "(1, 'Mae'),"
                    + "(2, 'Pai'),"
                    + "(3, 'Conjuge')";

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
