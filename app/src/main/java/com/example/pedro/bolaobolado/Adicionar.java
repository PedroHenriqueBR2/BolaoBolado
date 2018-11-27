package com.example.pedro.bolaobolado;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Adicionar extends AppCompatActivity {
    // Variáveis globais
    SQLiteDatabase db;
    private EditText txtTimeA;
    private EditText txtTimeB;
    private EditText txtGolA;
    private EditText txtGolB;
    private EditText txtPago;
    private EditText txtResponsavel;
    private EditText txtDataJogo;
    private EditText txtValorPago;
    private EditText txtTelefoneResponsavel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        overridePendingTransition(R.anim.main_to_add, R.anim.menu_sair);

        // Recuperando variáveis do template
        txtTimeA               = findViewById(R.id.txtTimeA);
        txtTimeB               = findViewById(R.id.txtTimeB);
        txtGolA                = findViewById(R.id.txtGolA);
        txtGolB                = findViewById(R.id.txtGolB);
        txtPago                = findViewById(R.id.txtPago);
        txtResponsavel         = findViewById(R.id.txtResponsavel);
        txtDataJogo            = findViewById(R.id.txtDataJogo);
        txtValorPago           = findViewById(R.id.txtValorPago);
        txtTelefoneResponsavel = findViewById(R.id.txtTelefoneResponsavel);

        txtTimeA.hasFocus();

        //Criar Banco de dados
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        try {
            db.execSQL("CREATE TABLE `dados` (`id`    INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`timea` TEXT NOT NULL, " +
                    "`timeb` TEXT NOT NULL, " +
                    "`gola`  TEXT, " +
                    "`golb`  TEXT, " +
                    "`pago`  TEXT, " +
                    "`valorpago`  TEXT, " +
                    "`responsavel`   TEXT, " +
                    "`telefoneresponsavel`   TEXT, " +
                    "`data`  TEXT);");
        } catch (Exception e){
            System.out.println("Erro ao criar DB" + e.toString());
        }

        if (db.isOpen()){
            db.close();
        }

        txtTimeA.requestFocus();

    }


    //Adicionar
    public void adcionar(View view){
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        if (txtTimeA.getText().toString().trim().length() == 0 || txtTimeB.getText().toString().trim().length() == 0){
            showMessage("Atenção!", "Por favor, digite o nome das duas seleções.");
        } else {
            try {
                db.execSQL("INSERT INTO dados(timea, timeb, gola, golb, pago, valorpago, responsavel, telefoneresponsavel, data)" +
                        " VALUES('"+txtTimeA.getText()+"','"+txtTimeB.getText()+"','"+txtGolA.getText()+"','"+txtGolB.getText()+"','"+ txtPago.getText()+"','"+txtValorPago.getText()+"','"+txtResponsavel.getText()+"','"+txtTelefoneResponsavel.getText()+"','"+txtDataJogo.getText()+"')  ");
                Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_LONG).show();
                limparTudo();
            } catch (Exception e){
                showMessage("Erro", e.toString());
            }
        }
        if (db.isOpen()){
            db.close();
        }
        txtTimeA.requestFocus();

    }

    //Método para mostrar o AlertDialog
    public void showMessage(String title, String message){
        AlertDialog.Builder builde = new AlertDialog.Builder(this);
        builde.setNeutralButton("OK", null);
        builde.setTitle(title);
        builde.setMessage(message);
        builde.show();
    }

    //Método para limpae todos os campos
    public void limparTudo(){
        txtTimeA.setText("");
        txtTimeB.setText("");
        txtGolA.setText("");
        txtGolB.setText("");
        txtPago.setText("");
        txtResponsavel.setText("");
        txtDataJogo.setText("");
        txtValorPago.setText("");
        txtTelefoneResponsavel.setText("");
    }

    // reencerever método finish
    @Override
    public void finish() {
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        super.finish();
    }
}
