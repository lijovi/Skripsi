package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterPembayaranHistory extends RecyclerView.Adapter<AdapterPembayaranHistory.ViewHolder>{
    ArrayList<BuktiBayar> listPembayaran;

    public AdapterPembayaranHistory(ArrayList<BuktiBayar> listPembayaran) {
        this.listPembayaran = listPembayaran;
    }

    @NonNull
    @Override
    public AdapterPembayaranHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifikasi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPembayaranHistory.ViewHolder holder, int position) {
        BuktiBayar pembayaran = listPembayaran.get(position);

        long besarPremi = Long.parseLong(pembayaran.getBesarPremi());
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String nominalRupiah = formatRupiah.format(besarPremi);
        nominalRupiah = nominalRupiah.replace("Rp", "Rp ");
        String text = holder.itemView.getContext().getString(R.string.textPembayaran);

        holder.textnotif.setText(pembayaran.getNama() + " " + text + " " + nominalRupiah);
        holder.time.setText(pembayaran.getTime());
        holder.tanggal.setText(pembayaran.getTanggal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, BuktiPembayaranHistory.class);
                intent.putExtra("nik", pembayaran.getNik());
                intent.putExtra("nama", pembayaran.getNama());
                intent.putExtra("besarPremi", pembayaran.getBesarPremi());
                intent.putExtra("nomorPolis", pembayaran.getNomorPolis());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPembayaran.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textnotif, time, tanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textnotif = itemView.findViewById(R.id.tvDeskripsi);
            time = itemView.findViewById(R.id.tvWaktu);
            tanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
