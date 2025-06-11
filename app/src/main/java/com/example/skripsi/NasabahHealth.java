package com.example.skripsi;

public class NasabahHealth extends Nasabah{
    String bod;
    String pekerjaan;
    String periodePertanggungan;
    String plan;
    String namaAhliWaris;
    String hubunganDenganAhliWaris;

    public NasabahHealth(String nik, String name, String email, String gender, String phoneNumber, String address, String password, int company,
                         String bod, String pekerjaan, String periodePertanggungan, String plan, String namaAhliWaris, String hubunganDenganAhliWaris) {
        super(nik, name, email, gender, phoneNumber, address, password, company);
        this.bod = bod;
        this.pekerjaan = pekerjaan;
        this.periodePertanggungan = periodePertanggungan;
        this.plan = plan;
        this.namaAhliWaris = namaAhliWaris;
        this.hubunganDenganAhliWaris = hubunganDenganAhliWaris;
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
}
