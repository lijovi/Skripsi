package com.example.skripsi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class AdapterNotifikasi extends RecyclerView.Adapter<AdapterNotifikasi.ViewHolder> {

    private List<NotifikasiModel> list;

    public AdapterNotifikasi(List<NotifikasiModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notifikasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotifikasiModel model = list.get(position);
        String text;
        if (Objects.equals(model.getJenis(), "Buat Password")){
            text = holder.itemView.getContext().getString(R.string.buatPassword);
            holder.tvDeskripsi.setText(text);
        } else if (Objects.equals(model.getJenis(), "Pembayaran")) {
            if (Objects.equals(model.getAsuransi(), "Health")){
                if (Objects.equals(model.getDeskripsi(), "Diterima")){
                    text = holder.itemView.getContext().getString(R.string.pembayaranHealth);
                } else {
                    text = holder.itemView.getContext().getString(R.string.pembayaranHealthDitolak);
                }
                holder.tvDeskripsi.setText(text);
            } else if (Objects.equals(model.getAsuransi(), "Travel")) {
                if (Objects.equals(model.getDeskripsi(), "Diterima")){
                    text = holder.itemView.getContext().getString(R.string.pembayaranTravel);
                } else {
                    text = holder.itemView.getContext().getString(R.string.pembayaranTravelDitolak);
                }
                holder.tvDeskripsi.setText(text);
            }
        } else if (Objects.equals(model.getJenis(), "Pendaftaran")) {
            if (Objects.equals(model.getAsuransi(), "Health")){
                text = holder.itemView.getContext().getString(R.string.pendaftaranHealth);
                holder.tvDeskripsi.setText(text);
            } else if (Objects.equals(model.getAsuransi(), "Travel")) {
                text = holder.itemView.getContext().getString(R.string.pendaftaranTravel);
                holder.tvDeskripsi.setText(text);
            }
        } else if (Objects.equals(model.getJenis(), "Ubah Password")) {
            text = holder.itemView.getContext().getString(R.string.ubahPassword);
            holder.tvDeskripsi.setText(text);
        }
//        holder.tvDeskripsi.setText(model.getDeskripsi());
        holder.tvWaktu.setText(model.getWaktu());
        holder.tvTanggal.setText(model.getTanggal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeskripsi, tvWaktu, tvTanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
