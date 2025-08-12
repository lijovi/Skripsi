package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {

    ArrayList<NotifikasiCompany> listHistory;
    ArrayList<Nasabah> listNasabah;

    public AdapterHistory(ArrayList<NotifikasiCompany> listHistory, ArrayList<Nasabah> listNasabah) {
        this.listHistory = listHistory;
        this.listNasabah = listNasabah;
    }

    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.ViewHolder holder, int position) {
        Nasabah nasabah = listNasabah.get(position);
        NotifikasiCompany history = listHistory.get(position);
        holder.notif.setText(nasabah.getName() + " telah melakukan registrasi asuransi " + nasabah.getJenisAsuransi());
        holder.status.setText(history.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(nasabah.getJenisAsuransi(), "Travel")){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, HistoryTravel.class);
                    intent.putExtra("nik", nasabah.getNik());
                    intent.putExtra("company", nasabah.getCompany());
                    Log.d("INTENT", "Received NIK: " + nasabah.getNik());
                    Log.d("INTENT", "Received NIK: " + nasabah.getJenisAsuransi());
//                    ClientSession.getInstance().setNik(nasabah.getNik());
                    context.startActivity(intent);
                } else if (Objects.equals(nasabah.getJenisAsuransi(), "Health")){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, HistoryHealth.class);
                    intent.putExtra("nik", nasabah.getNik());
                    intent.putExtra("company", nasabah.getCompany());
                    Log.d("INTENT", "Received NIK: " + nasabah.getNik());
                    Log.d("INTENT", "Received NIK: " + nasabah.getJenisAsuransi());
//                    ClientSession.getInstance().setNik(nasabah.getNik());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notif, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notif = itemView.findViewById(R.id.notif);
            status = itemView.findViewById(R.id.status);

        }
    }
}
