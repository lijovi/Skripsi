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
    int company;
    String time;
    String date;

    public Nasabah(String nik, String name, String email, String gender, String phoneNumber, String address, String password, String jenisAsuransi, int company, String time, String date) {
        this.nik = nik;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.jenisAsuransi = jenisAsuransi;
        this.company = company;
        this.time = time;
        this.date = date;
    }

    public Nasabah() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getCompany() {
        return company;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompany(int company) {
        this.company = company;
    }
}
