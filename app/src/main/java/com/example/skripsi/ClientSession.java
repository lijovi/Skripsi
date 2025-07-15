package com.example.skripsi;

public class ClientSession {
    private static ClientSession instance;

    public static ClientSession getInstance(){
        if (instance == null) instance = new ClientSession();
        return instance;
    }
    private String nama;
    private String email;
    private String noTelp;
    private String nik;
    private String gender;
    private String phoneNumber;
    private String address;
    private String password;
    private String limitHealth;
    private String limitTravel;
    private String planHealth;
    private String pekerjaan;
    private String periodePertanggunganHealth;
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPeriodePertanggunganHealth() {
        return periodePertanggunganHealth;
    }

    public void setPeriodePertanggunganHealth(String periodePertanggunganHealth) {
        this.periodePertanggunganHealth = periodePertanggunganHealth;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getPlanHealth() {
        return planHealth;
    }

    public void setPlanHealth(String planHealth) {
        this.planHealth = planHealth;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    private String bod;

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLimitHealth() {
        return limitHealth;
    }

    public void setLimitHealth(String limitHealth) {
        this.limitHealth = limitHealth;
    }

    public String getLimitTravel() {
        return limitTravel;
    }

    public void setLimitTravel(String limitTravel) {
        this.limitTravel = limitTravel;
    }
}
