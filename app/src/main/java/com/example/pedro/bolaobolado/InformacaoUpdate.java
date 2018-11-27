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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InformacaoUpdate extends AppCompatActivity {

    // Variáveis globais
    SQLiteDatabase db;
    private EditText txtTimeAUpdate;
    private EditText txtTimeBUpdate;
    private EditText txtGolAUpdate;
    private EditText txtGolBUpdate;
    private EditText txtPagoUpdate;
    private EditText txtResponsavelUpdate;
    private EditText txtDataJogoUpdate;
    private EditText txtValorPagoUpdate;
    private EditText txtTelefoneResponsavelUpdate;
    private TextView txtIdUpdate;
    private Button btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_update);

        setContentView(R.layout.activity_informacao_update);

        // Recuperando variáveis do template
        btnAtualizar                 = (Button)findViewById(R.id.btnAtualizar);
        txtIdUpdate                  = (TextView) findViewById(R.id.txtIdUpdate);
        txtTimeAUpdate               = (EditText)findViewById(R.id.txtTimeAUpdate);
        txtTimeBUpdate               = (EditText)findViewById(R.id.txtTimeBUpdate);
        txtGolAUpdate                = (EditText)findViewById(R.id.txtGolAUpdate);
        txtGolBUpdate                = (EditText)findViewById(R.id.txtGolBUpdate);
        txtPagoUpdate                = (EditText)findViewById(R.id.txtPagoUpdate);
        txtResponsavelUpdate         = (EditText)findViewById(R.id.txtResponsavelUpdate);
        txtDataJogoUpdate            = (EditText)findViewById(R.id.txtDataJogoUpdate);
        txtValorPagoUpdate           = (EditText)findViewById(R.id.txtValorPagoUpdate);
        txtTelefoneResponsavelUpdate = (EditText)findViewById(R.id.txtTelefoneResponsavelUpdate);

        Intent recebe = getIntent();
        String id = recebe.getStringExtra("id");

        preencheInformacoes(id);

    }

    // Metodo para preencher os campos
    private void preencheInformacoes(String argId){
        // Abrir Banco de dados
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM dados WHERE id = '" + argId + "';", null);

        if (c.moveToFirst()){
            txtIdUpdate.setText(c.getString(0));
            txtTimeAUpdate.setText(c.getString(1));
            txtTimeBUpdate.setText(c.getString(2));
            txtGolAUpdate.setText(c.getString(3));
            txtGolBUpdate.setText(c.getString(4));
            txtPagoUpdate.setText(c.getString(5));
            txtValorPagoUpdate.setText(c.getString(6));
            txtResponsavelUpdate.setText(c.getString(7));
            txtTelefoneResponsavelUpdate.setText(c.getString(8));
            txtDataJogoUpdate.setText(c.getString(9));

        } else {
            showMessage("Erro!", "Dado inválido");
        }

        if (db.isOpen()){
            db.close();
        }
    }

    //Atualizar
    public void atualizar(View view){
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);

        if (txtTimeAUpdate.getText().toString().trim().length() == 0 || txtTimeAUpdate.getText().toString().trim().length() == 0){
            showMessage("Erro", "Digite o nome das duas seleções!");
        } else {
            Cursor c = db.rawQuery("SELECT * FROM dados WHERE id = '" + txtIdUpdate.getText() + "';", null);

            if (c.moveToFirst()){
                db.execSQL("UPDATE dados SET timea='"+txtTimeAUpdate.getText()+"', timeb='"+txtTimeBUpdate.getText()+"', gola='"+txtGolAUpdate.getText()+"', golb='"+txtGolBUpdate.getText()+"', pago='"+txtPagoUpdate.getText()+"'," +
                        "valorpago='"+txtValorPagoUpdate.getText()+"',responsavel='"+txtResponsavelUpdate.getText()+"', telefoneresponsavel='"+txtTelefoneResponsavelUpdate.getText()+"', data='"+txtDataJogoUpdate.getText()+"'  WHERE id = '"+ txtIdUpdate.getText() +"' ;");

                if (db.isOpen()){
                    db.close();
                }
                MostraMensagem();
                finish();
            } else {
                showMessage("Erro!", "Dado inválido");
            }
        }

        if (db.isOpen()){
            db.close();
        }
    }

    // Método para mostra o toast
    private void MostraMensagem(){
        Toast.makeText(getApplicationContext(), "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
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
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        super.finish();
    }
}
