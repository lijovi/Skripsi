package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPembayaran extends RecyclerView.Adapter<AdapterPembayaran.ViewHolder> {

    ArrayList<DataPembayaran> listPembayaran;

    public AdapterPembayaran(ArrayList<DataPembayaran> listPembayaran) {
        this.listPembayaran = listPembayaran;
    }

    @NonNull
    @Override
    public AdapterPembayaran.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPembayaran.ViewHolder holder, int position) {
        DataPembayaran pembayaran = listPembayaran.get(position);
        holder.textnotif.setText(pembayaran.getNama() + "Telah Melakukan Pembayaran sebesar " + pembayaran.getBesarPremi());
        holder.time.setText(pembayaran.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, BuktiPembayaranAsuransi.class);
                intent.putExtra("nik", pembayaran.getNik());
                intent.putExtra("nama", pembayaran.getNama());
                intent.putExtra("besarPremi", pembayaran.getBesarPremi());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPembayaran.size();
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
