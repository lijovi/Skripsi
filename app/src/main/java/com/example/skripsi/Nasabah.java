package com.example.skripsi;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Objects;

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
    int limit;
    String namaAhliWaris;
    String hubunganDenganAhliWaris;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseHealth = database.getReference("clientHealth");
    DatabaseReference databaseTravel = database.getReference("clientTravel");


    public Nasabah(String nik, String name, String email, String gender, String phoneNumber, String address, String password, String jenisAsuransi, int company, String time, String date, int limit, String namaAhliWaris, String hubunganDenganAhliWaris) {
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
        this.limit = limit;
        this.namaAhliWaris = namaAhliWaris;
        this.hubunganDenganAhliWaris = hubunganDenganAhliWaris;
    }

    public Nasabah() {

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public void CreatePassword(String nik, String password){
        databaseHealth.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    databaseHealth.child(nik).child("password").setValue(password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseTravel.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    databaseTravel.child(nik).child("password").setValue(password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ChangePassword(String nik, String password){
        databaseHealth.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    databaseHealth.child(nik).child("password").setValue(password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseTravel.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    databaseTravel.child(nik).child("password").setValue(password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ChangeProfilePicture(String imageuri, String nik){
        databaseHealth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (Objects.equals(nik, snapshot.child(nik).child("nik"))){
                        FirebaseDatabase.getInstance().getReference("clientHealth").child(nik).child("profile").setValue(imageuri);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (Objects.equals(nik, snapshot.child(nik).child("nik"))){
                        FirebaseDatabase.getInstance().getReference("clientTravel").child(nik).child("profile").setValue(imageuri);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Pembayaran(String currentTime, String imageuri, String nik, String jenis){
        if (Objects.equals(jenis, "travel")){
            FirebaseDatabase.getInstance().getReference("transaksiTravel").child(nik).child("linkBukti").setValue(imageuri);
            FirebaseDatabase.getInstance().getReference("transaksiTravel").child(nik).child("check").setValue("Not Approve");
            FirebaseDatabase.getInstance().getReference("transaksiTravel").child(nik).child("time").setValue(currentTime);
        } else if (Objects.equals(jenis, "health")) {
            FirebaseDatabase.getInstance().getReference("transaksiHealth").child(nik).child("linkBukti").setValue(imageuri);
            FirebaseDatabase.getInstance().getReference("transaksiHealth").child(nik).child("check").setValue("Not Approve");
            FirebaseDatabase.getInstance().getReference("transaksiHealth").child(nik).child("time").setValue(currentTime);

        }

    }
}
