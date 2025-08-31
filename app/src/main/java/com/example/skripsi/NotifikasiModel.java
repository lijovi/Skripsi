package com.example.skripsi;

public class NotifikasiModel {
    private String jenisNotifikasi;
    private String notificationDesc;
    private String notificationDate;

    public NotifikasiModel() {
        // Needed for Firebase
    }

    public NotifikasiModel(String jenisNotifikasi, String notificationDesc, String notificationDate) {
        this.jenisNotifikasi = jenisNotifikasi;
        this.notificationDesc = notificationDesc;
        this.notificationDate = notificationDate;
    }

    public String getJenisNotifikasi() {
        return jenisNotifikasi;
    }

    public void setJenisNotifikasi(String jenisNotifikasi) {
        this.jenisNotifikasi = jenisNotifikasi;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }
}

