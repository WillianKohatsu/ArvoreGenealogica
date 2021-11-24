package com.example.arvoregenealogica.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.arvoregenealogica.db.Parentesco;
import com.example.arvoregenealogica.db.Pessoa;
import com.example.arvoregenealogica.db.TipoParentesco;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "arvore_genealogica";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Pessoa.CREATE_TABLE);
        db.execSQL(Parentesco.CREATE_TABLE);
        db.execSQL(TipoParentesco.CREATE_TABLE);
        db.execSQL(TipoParentesco.INSERT_VALORES);

        //insertPessoa("nome do usuario","Eu","","",""); //Chamar dado do firebase. Não usar junto de insertValoresTeste() @TODO
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Pessoa.NOME_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + Parentesco.NOME_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + TipoParentesco.NOME_TABELA);

        onCreate(db);
    }

    public void recreate() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Pessoa.NOME_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + Parentesco.NOME_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + TipoParentesco.NOME_TABELA);

        onCreate(db);
    }

    public long insertPessoa(String nome, String titulo, String imagem, String genero, String dtNasc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Pessoa.NOME, nome);
        values.put(Pessoa.TITULO, titulo);
        values.put(Pessoa.IMAGEM, imagem);
        values.put(Pessoa.GENERO, genero);
        values.put(Pessoa.DT_NASC, dtNasc);

        long id = db.insert(Pessoa.NOME_TABELA, null, values);

        db.close();

        return id;
    }

    private long insertParentesco(int idPessoa, int idParente, int idTipoParentesco) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Parentesco.ID_PESSOA, idPessoa);
        values.put(Parentesco.ID_PARENTE, idParente);
        values.put(Parentesco.ID_TIPO_PARENTESCO, idTipoParentesco);

        long id = db.insert(Parentesco.NOME_TABELA, null, values);

        db.close();

        return id;
    }

    public long insertTipoParentesco(String nomeTipoParentesco) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TipoParentesco.NOME_TIPO_PARENTESCO, nomeTipoParentesco);

        long id = db.insert(TipoParentesco.NOME_TIPO_PARENTESCO, null, values);

        db.close();

        return id;
    }

    public long insertParentescoMae(int idPessoa, int idParente) {
        return insertParentesco(idPessoa, idParente, TipoParentesco.mae);
    }

    public long insertParentescoPai(int idPessoa, int idParente) {
        return insertParentesco(idPessoa, idParente, TipoParentesco.pai);
    }

    public void insertParentescoConjuge(int idPessoa, int idParente) {
        insertParentesco(idPessoa, idParente, TipoParentesco.conjuge);
        insertParentesco(idParente, idPessoa, TipoParentesco.conjuge);
    }

    public Pessoa getPessoa(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Pessoa.NOME_TABELA,
                new String[]{Pessoa.ID_PESSOA, Pessoa.NOME, Pessoa.TITULO, Pessoa.IMAGEM, Pessoa.GENERO, Pessoa.DT_NASC},
                Pessoa.ID_PESSOA + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Pessoa pessoa = new Pessoa(
                cursor.getInt(cursor.getColumnIndex(Pessoa.ID_PESSOA)),
                cursor.getString(cursor.getColumnIndex(Pessoa.NOME)),
                cursor.getString(cursor.getColumnIndex(Pessoa.TITULO)),
                cursor.getString(cursor.getColumnIndex(Pessoa.IMAGEM)),
                cursor.getString(cursor.getColumnIndex(Pessoa.GENERO)),
                cursor.getString(cursor.getColumnIndex(Pessoa.DT_NASC))
        );


        // close the db connection
        cursor.close();

        return pessoa;
    }

    public Parentesco getParentesco(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Parentesco.NOME_TABELA,
                new String[]{Parentesco.ID_PARENTESCO, Parentesco.ID_PESSOA, Parentesco.ID_PARENTE, Parentesco.ID_TIPO_PARENTESCO},
                Parentesco.ID_PARENTESCO + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Parentesco parentesco = new Parentesco(
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTESCO)),
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PESSOA)),
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTE)),
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_TIPO_PARENTESCO))
        );


        // close the db connection
        cursor.close();

        return parentesco;
    }

    public Parentesco getParentesco(int idPessoa, int idTipoParentesco) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Parentesco.NOME_TABELA,
                new String[]{Parentesco.ID_PARENTESCO, Parentesco.ID_PESSOA, Parentesco.ID_PARENTE, Parentesco.ID_TIPO_PARENTESCO},
                Parentesco.ID_PESSOA + "=? and " + Parentesco.ID_TIPO_PARENTESCO + "=?",
                new String[]{String.valueOf(idPessoa),String.valueOf(idTipoParentesco)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Parentesco parentesco = new Parentesco(
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTESCO)),
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PESSOA)),
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTE)),
                cursor.getInt(cursor.getColumnIndex(Parentesco.ID_TIPO_PARENTESCO))
        );


        // close the db connection
        cursor.close();

        return parentesco;
    }

    public List<Pessoa> getAllPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Pessoa.NOME_TABELA + " ORDER BY " +
                Pessoa.NOME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(cursor.getInt(cursor.getColumnIndex(Pessoa.ID_PESSOA)));
                pessoa.setNome(cursor.getString(cursor.getColumnIndex(Pessoa.NOME)));
                pessoa.setTitulo(cursor.getString(cursor.getColumnIndex(Pessoa.TITULO)));
                pessoa.setImagem(cursor.getString(cursor.getColumnIndex(Pessoa.IMAGEM)));
                pessoa.setGenero(cursor.getString(cursor.getColumnIndex(Pessoa.GENERO)));
                pessoa.setDtNasc(cursor.getString(cursor.getColumnIndex(Pessoa.DT_NASC)));

                pessoas.add(pessoa);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        for (Pessoa pessoa:
             pessoas) {
            List<Parentesco> parentescos = getAllParentescosByIdPessoa(pessoa.getId());
            pessoa.popularParentescos(parentescos);
        }

        // return notes list
        return pessoas;
    }

    public List<Parentesco> getAllParentescosByIdPessoa(int idPessoa) {
        List<Parentesco> parentescos = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Parentesco.NOME_TABELA + " WHERE " + Parentesco.ID_PESSOA + " = " +
                idPessoa;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Parentesco parentesco = new Parentesco();
                parentesco.setId(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTESCO)));
                parentesco.setIdPessoa(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PESSOA)));
                parentesco.setIdParente(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTE)));
                parentesco.setIdTipoParentesco(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_TIPO_PARENTESCO)));

                parentescos.add(parentesco);
            } while (cursor.moveToNext());
        }

        db.close();

        return parentescos;
    }

    public List<Parentesco> getAllParentescosByIdParente(int idParente) {
        List<Parentesco> parentescos = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Parentesco.NOME_TABELA + " WHERE " + Parentesco.ID_PARENTE + " = " +
                idParente;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Parentesco parentesco = new Parentesco();
                parentesco.setId(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTESCO)));
                parentesco.setIdPessoa(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PESSOA)));
                parentesco.setIdParente(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_PARENTE)));
                parentesco.setIdTipoParentesco(cursor.getInt(cursor.getColumnIndex(Parentesco.ID_TIPO_PARENTESCO)));

                parentescos.add(parentesco);
            } while (cursor.moveToNext());
        }

        db.close();

        return parentescos;
    }

    public List<TipoParentesco> getAllTiposParentesco() {
        List<TipoParentesco> tiposParentesco = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TipoParentesco.NOME_TABELA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TipoParentesco tipoParentesco = new TipoParentesco();
                tipoParentesco.setId(cursor.getInt(cursor.getColumnIndex(TipoParentesco.ID_TIPO_PARENTESCO)));
                tipoParentesco.setNomeTipoParentesco(cursor.getString(cursor.getColumnIndex(TipoParentesco.NOME_TIPO_PARENTESCO)));

                tiposParentesco.add(tipoParentesco);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return tiposParentesco;
    }

    public int getPessoasCount() {
        String countQuery = "SELECT  * FROM " + Pessoa.NOME_TABELA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        return count;
    }

    public int updatePessoa(Pessoa pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Pessoa.NOME, pessoa.getNome());
        values.put(Pessoa.TITULO, pessoa.getTitulo());
        values.put(Pessoa.IMAGEM, pessoa.getImagem());
        values.put(Pessoa.GENERO, pessoa.getGenero());
        values.put(Pessoa.DT_NASC, pessoa.getDtNasc());

        return db.update(Pessoa.NOME_TABELA, values, Pessoa.ID_PESSOA + " = ?",
                new String[]{String.valueOf(pessoa.getId())});
    }

    public void deletePessoa(int idPessoa) {
        ArrayList<Parentesco> parentescos = new ArrayList<Parentesco>();
        parentescos.addAll(getAllParentescosByIdPessoa(idPessoa));
        parentescos.addAll(getAllParentescosByIdParente(idPessoa));

        for (Parentesco parentesco : parentescos) {
            deleteParentesco(parentesco.getId());
        }

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Pessoa.NOME_TABELA, Pessoa.ID_PESSOA + " = ?",
                new String[]{String.valueOf(idPessoa)});
        db.close();
    }

    public void deleteParentescoConjuge(int idParentesco) {
        Parentesco parentesco = this.getParentesco(idParentesco);
        Parentesco parentesco2 = this.getParentesco(parentesco.getIdParente(),TipoParentesco.conjuge);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Parentesco.NOME_TABELA, Parentesco.ID_PARENTESCO + " = ?",
                new String[]{String.valueOf(idParentesco)});
        db.delete(Parentesco.NOME_TABELA, Parentesco.ID_PARENTESCO + " = ?",
                new String[]{String.valueOf(parentesco2.getId())});
        db.close();
    }

    public void deleteParentescoMae(int idParentesco) {
        deleteParentesco(idParentesco);
    }

    public void deleteParentescoPai(int idParentesco) {
        deleteParentesco(idParentesco);
    }

    private void deleteParentesco(int idParentesco) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Parentesco.NOME_TABELA, Parentesco.ID_PARENTESCO + " = ?",
                new String[]{String.valueOf(idParentesco)});
        db.close();
    }

    public void insertValoresTeste(){
        int eu = (int) insertPessoa("Breno","Eu","","Masculino","");
        int mae = (int)insertPessoa("Marileide","Mãe","","Feminino","");
        int pai = (int)insertPessoa("Carlos","Pai","","Masculino","");
        int vovo = (int)insertPessoa("José","Avô","","Masculino","");
        int vo = (int)insertPessoa("Marília","Avó","","Feminino","");
        int esp = (int)insertPessoa("Giselle","Esposa","","Feminino","");
        int filha = (int)insertPessoa("Larissa","Filha","","Feminino","");
        int neto = (int)insertPessoa("Jorge","Neto","","Masculino","");

        insertParentescoPai(eu,pai);
        insertParentescoPai(mae,vovo);
        insertParentescoPai(filha,eu);

        insertParentescoMae(eu, mae);
        insertParentescoMae(mae, vo);
        insertParentescoMae(filha, esp);
        insertParentescoMae(neto, filha);

        insertParentescoConjuge(eu,esp);
        insertParentescoConjuge(mae,pai);
        insertParentescoConjuge(vovo,vo);
    }
}
