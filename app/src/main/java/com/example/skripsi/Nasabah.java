package com.example.skripsi;

import java.io.Serializable;

public class Nasabah implements Serializable {
    String nik;
    String name;
    String email;
    String gender;
    String phoneNumber;
    String address;
    String password;
    String jenisAsuransi;
    String company;

    public Nasabah(String nik, String name, String email, String gender, String phoneNumber, String address, String password, String jenisAsuransi, String company) {
        this.nik = nik;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.jenisAsuransi = jenisAsuransi;
        this.company = company;
    }

    public String getJenisAsuransi() {
        return jenisAsuransi;
    }

    public void setJenisAsuransi(String jenisAsuransi) {
        this.jenisAsuransi = jenisAsuransi;
    }

    public String getNik() {
        return nik;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getCompany() {
        return company;
    }
}
