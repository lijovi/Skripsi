package com.example.skripsi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
    }

    @Override
    public int getItemCount() {
        return listPembayaran.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notif, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notif = itemView.findViewById(R.id.textNotifikasi);
            date = itemView.findViewById(R.id.time);
        }
    }
}
