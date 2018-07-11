package com.example.pedro.bolaobolado;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Variáveis globais
    SQLiteDatabase db;
    private ListView lista;
    private Button btnAdd;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperando variáveis do template
        lista  = (ListView) findViewById(R.id.lista);
        btnAdd = (Button) findViewById(R.id.btnAdd);

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

        //Populando ListView
        final ArrayList<ItemListView> minhaLista = adicionaLista();


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(), InformacaoAposta.class);

                // Variaveis a ser enviada
                it.putExtra("id",minhaLista.get(position).getIdDados());

                startActivity(it);
            }
        });

        //Método para um longo click em uma opçao da lista
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setTitle("Atenção!");
                alerta.setIcon(R.drawable.avisodeletar);
                alerta.setMessage("Você deseja deletar o item selecionado?");
                alerta.setCancelable(true);

                //Caso a resposta seja não
                alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });

                //Caso a resposta seja sim
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletar(minhaLista.get(position).getIdDados());

                        /*Intent it = new Intent(getApplicationContext(), Editar.class);
                        it.putExtra("titulo", minhaLista.get(position).getTitulo());
                        it.putExtra("cor", cor);
                        startActivity(it);*/
                    }
                });

                AlertDialog alertDialog = alerta.create();
                alertDialog.show();

                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Adicionar.class);
                startActivity(it);
                finish();
            }
        });

        preencherLista();

    }

    //Deletar
    public void deletar(String argId){
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM dados WHERE id = '" + argId + "';", null);

        if (c.moveToFirst()){
            db.execSQL("DELETE FROM dados WHERE id = '"+ argId +"';");
            showMessage("Concluído!", "Dados deletados!");
        } else {
            showMessage("Erro!", "Dado inválido");
        }

        if (db.isOpen()){
            db.close();
        }

        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);

        finish();
    }

    // Preencher lista
    private void preencherLista(){
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        itens = new ArrayList<ItemListView>();


        Cursor c=db.rawQuery("SELECT * FROM dados ORDER BY id DESC;", null);
        if (c.getCount()==0){
            System.out.println("Nada Encontrado!");
        } else {
            StringBuffer buffer = new StringBuffer();

            while (c.moveToNext()) {
                buffer.append("Título " + c.getString(0) + "\n");

                String id = Integer.toString(c.getInt(0));
                String time = c.getString(1) + " X " +c.getString(2);
                String data = c.getString(9);

                ItemListView item = new ItemListView(id, time, data);
                itens.add(item);
            }

        }


        /*
        ItemListView item1 = new ItemListView("Brasil X Argentina", "17/06/2018");

        itens.add(item1);
        */

        adapterListView = new AdapterListView(this, itens);
        lista.setAdapter(adapterListView);

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

    //Método para setar o ListView personalizado
    private ArrayList<ItemListView> adicionaLista(){
        db=openOrCreateDatabase("bolaobolado", Context.MODE_PRIVATE, null);
        ArrayList<ItemListView> minhalista = new ArrayList<ItemListView>();
        ArrayList<String> dados = new ArrayList<>();
        ItemListView e;

        Cursor c=db.rawQuery("SELECT * FROM dados ORDER BY id DESC;", null);
        while (c.moveToNext()) {
            dados.add(c.getString(0).toString());
        }

        for (String d: dados){
            e = new ItemListView(d, "", "");
            minhalista.add(e);
        }

        if (db.isOpen()){
            db.close();
        }

        return minhalista;
    }
}
