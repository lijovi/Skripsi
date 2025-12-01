package com.example.skripsi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransaksiTravel extends Transaksi{
    String nomorPolisTravel;
    FirebaseDatabase database;
    DatabaseReference referenceTransaksi;

    public TransaksiTravel(String NIK, int besarPremi, int company, String date, String jatuhTempo, String nomorPolisTravel) {
        super(NIK, besarPremi, company, date, jatuhTempo);
        this.nomorPolisTravel = nomorPolisTravel;
    }

    public String getNomorPolisTravel() {
        return nomorPolisTravel;
    }

    public void setNomorPolisTravel(String nomorPolisTravel) {
        this.nomorPolisTravel = nomorPolisTravel;
    }

    public void newTransaction(TransaksiTravel transaksi, String nik){
        database = FirebaseDatabase.getInstance();
        referenceTransaksi = database.getReference("transaksiTravel").child(nik);
        referenceTransaksi.setValue(transaksi);
    }
}

