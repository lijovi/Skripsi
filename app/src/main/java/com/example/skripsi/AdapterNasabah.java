package com.example.skripsi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNasabah extends RecyclerView.Adapter<AdapterNasabah.ViewHolder> {
    ArrayList<TransaksiHealth> transaksiHealth;
    ArrayList<TransaksiTravel> transaksiTravel;

    @NonNull
    @Override
    public AdapterNasabah.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNasabah.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textnotif, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textnotif = itemView.findViewById(R.id.textNotifikasi);
            time = itemView.findViewById(R.id.time);

        }
    }
}
