package com.example.skripsi;

public class NasabahTravel extends Nasabah{
    public NasabahTravel(String nik, String name, String email, String gender, String phoneNumber, String address,
                         String password, String jenisAsuransi, int company, String time, String date, int limit, String jenisPolis, String namaKeluarga, String planAsuransi,
                         String masaPerjalanan, String tipePolis, String namaAhliWaris, String hubunganDenganAhliWaris,
                         String negaraTujuan, String tujuanPerjalanan) {
        super(nik, name, email, gender, phoneNumber, address, password, jenisAsuransi, company, time, date, limit);
        this.jenisPolis = jenisPolis;
        this.namaKeluarga = namaKeluarga;
        this.planAsuransi = planAsuransi;
        this.masaPerjalanan = masaPerjalanan;
        this.tipePolis = tipePolis;
        this.namaAhliWaris = namaAhliWaris;
        this.hubunganDenganAhliWaris = hubunganDenganAhliWaris;
        this.negaraTujuan = negaraTujuan;
        this.tujuanPerjalanan = tujuanPerjalanan;
    }

    String jenisPolis;
    String namaKeluarga;
    String planAsuransi;
    String masaPerjalanan;
    String tipePolis;
    String namaAhliWaris;
    String hubunganDenganAhliWaris;
    String negaraTujuan;
    String tujuanPerjalanan;


    public String getTipePolis() {
        return tipePolis;
    }

    public void setTipePolis(String tipePolis) {
        this.tipePolis = tipePolis;
    }

    public String getJenisPolis() {
        return jenisPolis;
    }

    public void setJenisPolis(String jenisPolis) {
        this.jenisPolis = jenisPolis;
    }

    public String getNamaKeluarga() {
        return namaKeluarga;
    }

    public void setNamaKeluarga(String namaKeluarga) {
        this.namaKeluarga = namaKeluarga;
    }

    public String getPlanAsuransi() {
        return planAsuransi;
    }

    public void setPlanAsuransi(String planAsuransi) {
        this.planAsuransi = planAsuransi;
    }

    public String getMasaPerjalanan() {
        return masaPerjalanan;
    }

    public void setMasaPerjalanan(String masaPerjalanan) {
        this.masaPerjalanan = masaPerjalanan;
    }

    public String getNamaAhliWaris() {
        return namaAhliWaris;
    }

    public void setNamaAhliWaris(String namaAhliWaris) {
        this.namaAhliWaris = namaAhliWaris;
    }

    public String getHubunganDenganAhliWaris() {
        return hubunganDenganAhliWaris;
    }

    public void setHubunganDenganAhliWaris(String hubunganDenganAhliWaris) {
        this.hubunganDenganAhliWaris = hubunganDenganAhliWaris;
    }

    public String getNegaraTujuan() {
        return negaraTujuan;
    }

    public void setNegaraTujuan(String negaraTujuan) {
        this.negaraTujuan = negaraTujuan;
    }

    public String getTujuanPerjalanan() {
        return tujuanPerjalanan;
    }

    public void setTujuanPerjalanan(String tujuanPerjalanan) {
        this.tujuanPerjalanan = tujuanPerjalanan;
    }
}
