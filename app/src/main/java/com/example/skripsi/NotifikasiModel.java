package com.example.skripsi;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotifikasiModel {
    private String deskripsi;
    private String waktu;
    private String tanggal;
    private String jenis;
    private String asuransi;

    public NotifikasiModel() {
        // Diperlukan Firebase
    }

    public NotifikasiModel(String deskripsi, String waktu, String tanggal, String jenis, String asuransi) {
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.jenis = jenis;
        this.asuransi = asuransi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJenis() {
        return jenis;
    }

    public String getAsuransi() {
        return asuransi;
    }

    public void NewNotification(NotifikasiModel notifikasi, String nik){
        DatabaseReference referenceNotifikasi = FirebaseDatabase.getInstance().getReference("notifikasiNasabah");
        referenceNotifikasi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referenceNotifikasi.child(nik).child(String.valueOf(snapshot.child(nik).getChildrenCount()+1)).setValue(notifikasi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
