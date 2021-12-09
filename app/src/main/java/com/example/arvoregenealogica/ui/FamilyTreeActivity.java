package com.example.arvoregenealogica.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.arvoregenealogica.Perfil;
import com.example.arvoregenealogica.db.DatabaseHelper;
import com.example.arvoregenealogica.interfaces.OnFamilySelectListener;
import com.example.arvoregenealogica.model.FamilyMember;
import com.example.arvoregenealogica.ui.view.FamilyTreeView;
import com.example.arvoregenealogica.ui.view.FamilyTreeView2;
import com.example.arvoregenealogica.utils.AssetsUtil;
import com.example.arvoregenealogica.utils.ConvertPessoa;
import com.example.arvoregenealogica.utils.ToastMaster;
import com.example.arvoregenealogica.R;
import com.example.arvoregenealogica.db.FamilyLiteOrm;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.List;


public class FamilyTreeActivity extends BaseActivity {

    public static final String HAVE_FOSTER_PARENT = "have_foster_parent";

    private Button btnEnlarge;
    private Button btnShrinkDown;
    private FamilyTreeView ftvTree;
    private FamilyTreeView2 ftvTree2;

    private FamilyLiteOrm mDatabase;

    private boolean haveFosterParent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree);

        /*
        String appName = getString(R.string.app_name);
        permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        refuseTips = new String[]{
                String.format("Abra a permissão de armazenamento em Configurações-Aplicativo", appName),
        };
        setPermissions();
         */
        initView();
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.closeDB();
        }
    }

    @Override
    public void onPermissionSuccess() {
        setData();
    }

    private void initView() {
        btnEnlarge = (Button) findViewById(R.id.btn_enlarge);
        btnShrinkDown = (Button) findViewById(R.id.btn_shrink_down);
        ftvTree = (FamilyTreeView) findViewById(R.id.ftv_tree);
        ftvTree2 = (FamilyTreeView2) findViewById(R.id.ftv_tree2);
    }

    private void setData() {
        try{
            haveFosterParent = getIntent().getBooleanExtra(HAVE_FOSTER_PARENT, false);
            if (haveFosterParent) {
                ftvTree.setVisibility(View.GONE);
                ftvTree2.setVisibility(View.VISIBLE);
                btnEnlarge.setVisibility(View.GONE);
                btnShrinkDown.setVisibility(View.GONE);
            } else {
                ftvTree.setVisibility(View.VISIBLE);
                ftvTree2.setVisibility(View.GONE);
            }

            mDatabase = new FamilyLiteOrm(this);

            //new DatabaseHelper(this).recreate();
            //new DatabaseHelper(this).insertValoresTeste();

            String novoJson = new ConvertPessoa(this).listarJsonFamilyMembers();
            List<FamilyMember> mList = JSONObject.parseArray(novoJson, FamilyMember.class);


            String MY_ID = "1";
            mDatabase.deleteTable();
            mDatabase.save(mList);

            btnEnlarge.setOnClickListener(click);
            btnShrinkDown.setOnClickListener(click);


            FamilyMember mFamilyMember = mDatabase.getFamilyTreeById(MY_ID);

            if (mFamilyMember != null) {
                if (haveFosterParent) {
                    ftvTree2.setFamilyMember(mFamilyMember);
                } else {
                    ftvTree.setFamilyMember(mFamilyMember);
                }
            }

            ftvTree.setOnFamilySelectListener(familySelect);
            ftvTree2.setOnFamilySelectListener(familySelect);
        }
        catch (Exception ex){
            String msg = ex.getMessage();
        }
    }


    private OnFamilySelectListener familySelect = new OnFamilySelectListener() {
        @Override
        public void onFamilySelect(FamilyMember family) {
            if (family.isSelect()) {
                abrirTelaPerfil(family.getMemberId());
                //ToastMaster.toast(family.getMemberName());//Substituir pela tela de exibição de dados do membro selecionado @TODO
            } else {
                String currentFamilyId = family.getMemberId();
                FamilyMember currentFamily = mDatabase.getFamilyTreeById(currentFamilyId);
                if (currentFamily != null) {
                    if (haveFosterParent) {
                        ftvTree2.setFamilyMember(currentFamily);
                    } else {
                        ftvTree.setFamilyMember(currentFamily);
                    }
                }
            }
        }
    };

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!haveFosterParent) {
                switch (v.getId()) {
                    case R.id.btn_enlarge:
                        ftvTree.doEnlarge();
                        break;
                    case R.id.btn_shrink_down:
                        ftvTree.doShrinkDown();
                        break;
                }
            }
        }
    };

    public void abrirTelaPerfil(String id){
        Intent intent = new Intent(this, Perfil.class);
        intent.putExtra("id",Integer.parseInt(id));
        startActivity(intent);
    }
}
