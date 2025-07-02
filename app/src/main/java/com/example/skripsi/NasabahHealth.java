package com.example.skripsi;

import java.util.ArrayList;

public class NasabahHealth extends Nasabah{
    String bod;
    String pekerjaan;
    String periodePertanggungan;
    String plan;
    String namaAhliWaris;
    String hubunganDenganAhliWaris;
    ArrayList<String> riwayatPenyakit;

    public NasabahHealth(String nik, String name, String email, String gender, String phoneNumber, String address, String password, String jenisAsuransi, String company,
                         String bod, String pekerjaan, String periodePertanggungan, String plan, String namaAhliWaris, String hubunganDenganAhliWaris, ArrayList<String> riwayatPenyakit) {
        super(nik, name, email, gender, phoneNumber, address, password, jenisAsuransi ,company);
        this.bod = bod;
        this.pekerjaan = pekerjaan;
        this.periodePertanggungan = periodePertanggungan;
        this.plan = plan;
        this.namaAhliWaris = namaAhliWaris;
        this.hubunganDenganAhliWaris = hubunganDenganAhliWaris;
        this.riwayatPenyakit = riwayatPenyakit;
    }

    public String getBod() {
        return bod;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public String getPlan() {
        return plan;
    }

    public String getPeriodePertanggungan() {
        return periodePertanggungan;
    }

    public String getNamaAhliWaris() {
        return namaAhliWaris;
    }

    public String getHubunganDenganAhliWaris() {
        return hubunganDenganAhliWaris;
    }

    public ArrayList<String> getRiwayatPenyakit() {
        return riwayatPenyakit;
    }

    public void setRiwayatPenyakit(ArrayList<String> riwayatPenyakit) {
        this.riwayatPenyakit = riwayatPenyakit;
    }
}
