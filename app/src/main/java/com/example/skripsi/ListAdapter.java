package com.example.skripsi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    Context context;
    ArrayList<Nasabah> nasabahList;
    String jenis;

    public ListAdapter(Context context){
        this.context = context;
        this.nasabahList = new ArrayList<>();
    }

    public void setListAdapter(ArrayList<Nasabah> nasabahList){
        this.nasabahList = nasabahList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Nasabah nasabah = nasabahList.get(position);
        String name = nasabah.getName();
        String jenisAsuransi = nasabah.getJenisAsuransi();
        jenis = jenisAsuransi;
        holder.content.setText(name + " telah melakukan pendaftaran " + jenisAsuransi);
    }

    @Override
    public int getItemCount() {
        return nasabahList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (jenis == "Health"){

            } else if (jenis == "Travel") {

            }
        }
    }
}
