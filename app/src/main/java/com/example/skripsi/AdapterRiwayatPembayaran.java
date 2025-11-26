package com.example.skripsi;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AdapterRiwayatPembayaran extends RecyclerView.Adapter<AdapterRiwayatPembayaran.ViewHolder> {

    ArrayList<DataPembayaran> listPembayaran;

    public AdapterRiwayatPembayaran(ArrayList<DataPembayaran> listPembayaran){
        this.listPembayaran = listPembayaran;
    }

    @NonNull
    @Override
    public AdapterRiwayatPembayaran.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_riwayat_pembayaran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRiwayatPembayaran.ViewHolder holder, int position) {
        DataPembayaran pembayaran = listPembayaran.get(position);
        long besarPremi = Long.parseLong(pembayaran.getBesarPremi());
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String nominalRupiah = formatRupiah.format(besarPremi);
        nominalRupiah = nominalRupiah.replace("Rp", "Rp ");
        String text = holder.itemView.getContext().getString(R.string.text);
        holder.notif.setText(pembayaran.getNama() + " " + text + " " + nominalRupiah);
        holder.date.setText(pembayaran.getDate());
        holder.check.setText(pembayaran.getStatus());
        if (Objects.equals(pembayaran.getStatus(), "Success")){
            holder.check.setTextColor(Color.parseColor("#1E942C"));
        } else if (Objects.equals(pembayaran.getStatus(), "Pending")){
            holder.check.setTextColor(Color.parseColor("#AD9900"));
        } else {
            holder.check.setTextColor(Color.parseColor("#FF0000"));
            String gagal = holder.itemView.getContext().getString(R.string.gagal);
            holder.notif.setText(pembayaran.getNama() + " " + gagal + " " + pembayaran.getNomorPolis());
        }
    }

    @Override
    public int getItemCount() {
        return listPembayaran.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notif, date, check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notif = itemView.findViewById(R.id.textNotifikasi);
            date = itemView.findViewById(R.id.time);
            check = itemView.findViewById(R.id.check);
        }
    }
}
