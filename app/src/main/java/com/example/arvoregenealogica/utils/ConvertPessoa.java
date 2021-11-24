package com.example.arvoregenealogica.utils;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.arvoregenealogica.db.DatabaseHelper;
import com.example.arvoregenealogica.db.Parentesco;
import com.example.arvoregenealogica.db.Pessoa;
import com.example.arvoregenealogica.db.TipoParentesco;
import com.example.arvoregenealogica.model.FamilyMember;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONStringer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConvertPessoa {
    private DatabaseHelper db;

    public ConvertPessoa(Context context){
        db = new DatabaseHelper(context);
    }

    private List<FamilyMember> listarFamilyMembers(){
        List<FamilyMember> members = new ArrayList<>();
        List<Pessoa> pessoas = db.getAllPessoas();
        for (Pessoa pessoa:pessoas) {
            pessoa.popularParentescos(db.getAllParentescosByIdPessoa(pessoa.getId()));
            FamilyMember member = new FamilyMember(){{
                setMemberId(String.valueOf(pessoa.getId()));
                setMemberName(pessoa.getNome());
                setCall(pessoa.getTitulo());
                setFatherId(pessoa.getPai()!=null?String.valueOf(pessoa.getPai().getIdParente()):null);
                setMotherId(pessoa.getMae()!=null?String.valueOf(pessoa.getMae().getIdParente()):null);
                setSpouseId(pessoa.getConjuge()!=null?String.valueOf(pessoa.getConjuge().getIdParente()):null);
            }};
            members.add(member);
        }
        return members;
    }

    public String listarJsonFamilyMembers(){
        List<FamilyMember> members = listarFamilyMembers();
        Type familyMemberType = new TypeToken<FamilyMember>(){}.getType();

        Gson gson = new Gson();

        JsonArray array = new JsonArray();

        for (FamilyMember member:
             members) {
            JsonElement element = new Gson().toJsonTree(member,familyMemberType);
            array.add(element);
        }
        String strArray = array.toString();


        return strArray;
    }

}
