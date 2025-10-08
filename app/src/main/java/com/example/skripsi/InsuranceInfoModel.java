package com.example.skripsi;

public class InsuranceInfoModel {
    // --- Data Asuransi Umum ---
    private String nik;
    private String nama;
    private String jenisAsuransi;
    private String nomorPolis;
    private String status;

    // --- Data Riwayat Medis ---
    private String tanggalDiagnosa;
    private String kondisi;
    private String besarKlaim;
    private String statusKlaim;

    // --- Data Klaim ---
    private String klaim;
    private String statusKlaimAsuransi;
    private String tanggalPengajuan;

    public InsuranceInfoModel() {
        // Required empty constructor for Firebase
    }

    // kalo gapake/udah pake yang lu buat hapus aja
    public InsuranceInfoModel(String nik, String nama, String jenisAsuransi, String nomorPolis, String status) {
        this.nik = nik;
        this.nama = nama;
        this.jenisAsuransi = jenisAsuransi;
        this.nomorPolis = nomorPolis;
        this.status = status;
    }

    // riwayat medis
    public InsuranceInfoModel(String tanggalDiagnosa, String kondisi, String besarKlaim, String statusKlaim) {
        this.tanggalDiagnosa = tanggalDiagnosa;
        this.kondisi = kondisi;
        this.besarKlaim = besarKlaim;
        this.statusKlaim = statusKlaim;
    }

    // klaim
    public InsuranceInfoModel(String klaim, String statusKlaimAsuransi, String tanggalPengajuan) {
        this.klaim = klaim;
        this.statusKlaimAsuransi = statusKlaimAsuransi;
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJenisAsuransi() { return jenisAsuransi; }
    public void setJenisAsuransi(String jenisAsuransi) { this.jenisAsuransi = jenisAsuransi; }

    public String getNomorPolis() { return nomorPolis; }
    public void setNomorPolis(String nomorPolis) { this.nomorPolis = nomorPolis; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTanggalDiagnosa() { return tanggalDiagnosa; }
    public void setTanggalDiagnosa(String tanggalDiagnosa) { this.tanggalDiagnosa = tanggalDiagnosa; }

    public String getKondisi() { return kondisi; }
    public void setKondisi(String kondisi) { this.kondisi = kondisi; }

    public String getBesarKlaim() { return besarKlaim; }
    public void setBesarKlaim(String besarKlaim) { this.besarKlaim = besarKlaim; }

    public String getStatusKlaim() { return statusKlaim; }
    public void setStatusKlaim(String statusKlaim) { this.statusKlaim = statusKlaim; }

    public String getKlaim() { return klaim; }
    public void setKlaim(String klaim) { this.klaim = klaim; }

    public String getStatusKlaimAsuransi() { return statusKlaimAsuransi; }
    public void setStatusKlaimAsuransi(String statusKlaimAsuransi) { this.statusKlaimAsuransi = statusKlaimAsuransi; }

    public String getTanggalPengajuan() { return tanggalPengajuan; }
    public void setTanggalPengajuan(String tanggalPengajuan) { this.tanggalPengajuan = tanggalPengajuan; }
}
