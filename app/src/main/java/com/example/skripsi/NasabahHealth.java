package com.example.skripsi;

public class NasabahHealth extends Nasabah{
    String bod;
    String pekerjaan;
    String periodePertanggungan;
    String namaAhliWaris;
    String hubunganDenganAhliWaris;

    public NasabahHealth(int nik, String name, String email, String gender, String phoneNumber, String address, String password,
                         String bod, String pekerjaan, String periodePertanggungan, String namaAhliWaris, String hubunganDenganAhliWaris) {
        super(nik, name, email, gender, phoneNumber, address, password);
        this.bod = bod;
        this.pekerjaan = pekerjaan;
        this.periodePertanggungan = periodePertanggungan;
        this.namaAhliWaris = namaAhliWaris;
        this.hubunganDenganAhliWaris = hubunganDenganAhliWaris;
    }

    public String getBod() {
        return bod;
    }

    public String getPekerjaan() {
        return pekerjaan;
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
}
