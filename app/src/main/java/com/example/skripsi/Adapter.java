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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Nasabah> listNasabah;

    public Adapter(ArrayList<Nasabah> listNasabah){
        this.listNasabah = listNasabah;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nasabah nasabah = listNasabah.get(position);
        holder.textnotif.setText(nasabah.getName() + " telah melakukan registrasi asuransi " + nasabah.getJenisAsuransi());
        holder.time.setText(nasabah.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(nasabah.getJenisAsuransi(), "Travel")){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DataCalonNasabahTravel.class);
                    intent.putExtra("nik", nasabah.getNik());
                    intent.putExtra("company", nasabah.getCompany());
                    Log.d("INTENT", "Received NIK: " + nasabah.getNik());
                    Log.d("INTENT", "Received NIK: " + nasabah.getJenisAsuransi());
//                    ClientSession.getInstance().setNik(nasabah.getNik());
                    context.startActivity(intent);
                } else if (Objects.equals(nasabah.getJenisAsuransi(), "Health")){
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DataCalonNasabahHealth.class);
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
        return listNasabah.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textnotif, time;

        public ViewHolder(View itemView){
            super(itemView);
            textnotif = itemView.findViewById(R.id.textNotifikasi);
            time = itemView.findViewById(R.id.time);
        }
    }

}
