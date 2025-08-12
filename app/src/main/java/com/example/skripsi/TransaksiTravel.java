package com.example.skripsi;

public class TransaksiTravel extends Transaksi{
    String nomorPolisTravel;

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
}

