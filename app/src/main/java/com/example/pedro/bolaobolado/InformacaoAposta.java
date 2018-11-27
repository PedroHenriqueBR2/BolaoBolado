package com.example.pedro.bolaobolado;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformacaoAposta extends AppCompatActivity {

    // Variáveis globais
    SQLiteDatabase db;
    private TextView txtInfoId;
    private TextView txtInfoTimeA;
    private TextView txtInfoTimeB;
    private TextView txtInfoGolA;
    private TextView txtInfoGolB;
    private TextView txtInfoData;
    private TextView txtInfoPago;
    private TextView txtInfoValor;
    private TextView txtInfoResponsavel;
    private TextView txtInfoTelefone;
    private Button btnAtualizarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_aposta);

        overridePendingTransition(R.anim.main_to_add, R.anim.menu_sair);

        // Recuperando variaveis do template
        txtInfoId           = findViewById(R.id.txtInfoId);
        txtInfoTimeA        = findViewById(R.id.txtInfoTimeA);
        txtInfoTimeB        = findViewById(R.id.txtInfoTimeB);
        txtInfoGolA         = findViewById(R.id.txtInfoGolA);
        txtInfoGolB         = findViewById(R.id.txtInfoGolB);
        txtInfoData         = findViewById(R.id.txtInfoData);
        txtInfoPago         = findViewById(R.id.txtInfoPago);
        txtInfoValor        = findViewById(R.id.txtInfoValor);
        txtInfoResponsavel  = findViewById(R.id.txtInfoResponsavel);
        txtInfoTelefone     = findViewById(R.id.txtInfoTelefone);
        btnAtualizarDados   = findViewById(R.id.btnAtualizarDados);

        Intent it = getIntent();
        final String id = it.getStringExtra("id");

        btnAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent up = new Intent(getApplicationContext(), InformacaoUpdate.class);
                up.putExtra("id", id);
                startActivity(up);
                MainActivity mainActivity = new MainActivity();
                mainActivity.finish();
                finish();
            }
        });



        preencheInformacoes(id);
    }

    // Metodo para preencher os campos
    private void preencheInformacoes(String argId){
        // Abrir Banco de dados
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM dados WHERE id = '" + argId + "';", null);

        if (c.moveToFirst()){
            txtInfoId.setText(c.getString(0));
            txtInfoTimeA.setText(c.getString(1));
            txtInfoTimeB.setText(c.getString(2));
            txtInfoGolA.setText(c.getString(3));
            txtInfoGolB.setText(c.getString(4));
            txtInfoPago.setText(c.getString(5));
            txtInfoValor.setText(c.getString(6));
            txtInfoResponsavel.setText(c.getString(7));
            txtInfoTelefone.setText(c.getString(8));
            txtInfoData.setText(c.getString(9));

        } else {
            showMessage("Erro!", "Dado inválido");
        }

        if (db.isOpen()){
            db.close();
        }
    }

    //Método para mostrar o AlertDialog
    public void showMessage(String title, String message){
        AlertDialog.Builder builde = new AlertDialog.Builder(this);
        builde.setNeutralButton("OK", null);
        builde.setTitle(title);
        builde.setMessage(message);
        builde.show();
    }

    @Override
    public void finish() {
//        Intent it = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(it);
        super.finish();
    }
}
