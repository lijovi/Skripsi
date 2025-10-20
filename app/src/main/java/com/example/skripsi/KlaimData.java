package com.example.skripsi;

public class KlaimData {
    public String klaim, statusKlaimAsuransi, tanggalPengajuan;

    public KlaimData() {} // Wajib buat Firebase

    public KlaimData(String klaim, String statusKlaimAsuransi, String tanggalPengajuan) {
        this.klaim = klaim;
        this.statusKlaimAsuransi = statusKlaimAsuransi;
        this.tanggalPengajuan = tanggalPengajuan;
    }
}

