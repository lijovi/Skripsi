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


}
