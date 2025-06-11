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
    int company;

    public Nasabah(String nik, String name, String email, String gender, String phoneNumber, String address, String password, int company) {
        this.nik = nik;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.company = company;
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

    public int getCompany() {
        return company;
    }
}
