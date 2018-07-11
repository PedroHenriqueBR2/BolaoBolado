package com.example.pedro.bolaobolado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<ItemListView> itens;

    public AdapterListView(Context context, ArrayList<ItemListView> itens){
        this.itens = itens;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemListView item = itens.get(position);
        convertView = mInflater.inflate(R.layout.linha, null);

        ((TextView) convertView.findViewById(R.id.txtId)).setText(item.getIdDados());
        ((TextView) convertView.findViewById(R.id.titulo)).setText(item.getTitulo());
        ((TextView) convertView.findViewById(R.id.txtData)).setText(item.getData());

        return convertView;

    }
}
