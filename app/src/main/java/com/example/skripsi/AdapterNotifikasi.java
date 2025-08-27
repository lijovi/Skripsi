package com.example.skripsi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.tvDeskripsi.setText(model.getDeskripsi());
        holder.tvWaktu.setText(model.getWaktu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeskripsi, tvWaktu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
        }
    }
}
