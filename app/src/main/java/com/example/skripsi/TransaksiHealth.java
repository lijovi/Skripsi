package com.example.skripsi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TransaksiHealth extends Transaksi{
    String nomorPolisKesehatan;
    FirebaseDatabase database;
    DatabaseReference referenceTransaksi;

    public TransaksiHealth(String NIK, int besarPremi, int company, String date, String jatuhTempo, String nomorPolisKesehatan) {
        super(NIK, besarPremi, company, date, jatuhTempo);
        this.nomorPolisKesehatan = nomorPolisKesehatan;
    }

    public String getNomorPolisKesehatan() {
        return nomorPolisKesehatan;
    }

    public void setNomorPolisKesehatan(String nomorPolisKesehatan) {
        this.nomorPolisKesehatan = nomorPolisKesehatan;
    }

    public void newTransaction(TransaksiHealth transaksi, String nik){
        database = FirebaseDatabase.getInstance();
        referenceTransaksi = database.getReference("transaksiHealth").child(nik);
        referenceTransaksi.setValue(transaksi);
    }
}
