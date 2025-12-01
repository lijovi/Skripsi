package com.example.skripsi;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotifikasiCompany {
    String NIK;
    String status;
    String date;
    String time;
    int company;
    String jenisAsuransi;
    String name;

    FirebaseDatabase database;
    DatabaseReference referenceHistory;

    public NotifikasiCompany(){

    }

    public NotifikasiCompany(String NIK, String nama, String status, String date, String time, int company, String jenisAsuransi) {
        this.NIK = NIK;
        this.status = status;
        this.date = date;
        this.time = time;
        this.company = company;
        this.jenisAsuransi = jenisAsuransi;
        this.name = nama;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getJenisAsuransi() {
        return jenisAsuransi;
    }

    public void setJenisAsuransi(String jenisAsuransi) {
        this.jenisAsuransi = jenisAsuransi;
    }

    public void Decline(NotifikasiCompany notif, int Company){
        referenceHistory = database.getReference("history");
        referenceHistory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referenceHistory.child(String.valueOf(Company)).child(String.valueOf(snapshot.child(String.valueOf(Company)).getChildrenCount()+1)).setValue(notif);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Accept(NotifikasiCompany notif, int Company){
        referenceHistory = database.getReference("history");
        referenceHistory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referenceHistory.child(String.valueOf(Company)).child(String.valueOf(snapshot.child(String.valueOf(Company)).getChildrenCount()+1)).setValue(notif);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
