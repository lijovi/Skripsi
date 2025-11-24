package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

public class BuktiPembayaranAsuransi extends AppCompatActivity {

    TextView nik, nama, besarPremi;
    String NIK, Nama, BesarPremi, NomorPolis ;
    StorageReference storage;
    String imageurl;
    ImageView buktiPembayaran;
    Button btnTerima, btnTolak, back;
    DatabaseReference referenceNotifikasi = FirebaseDatabase.getInstance().getReference("notifikasiNasabah");
    DatabaseReference referencePembayaran = FirebaseDatabase.getInstance().getReference("pembayaran");
    DatabaseReference referenceTransaksiHealth = FirebaseDatabase.getInstance().getReference("transaksiHealth");
    DatabaseReference referenceTransaksiTravel = FirebaseDatabase.getInstance().getReference("transaksiTravel");
    Calendar calendar;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bukti_pembayaran_asuransi);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        besarPremi = findViewById(R.id.besarPremi);
        buktiPembayaran = findViewById(R.id.buktiPembayaran);
        btnTerima = findViewById(R.id.btnTerima);
        btnTolak = findViewById(R.id.btnTolak);
        back = findViewById(R.id.back);

        NIK = getIntent().getStringExtra("nik");
        Nama = getIntent().getStringExtra("nama");
        BesarPremi = getIntent().getStringExtra("besarPremi");
        NomorPolis = getIntent().getStringExtra("nomorPolis");
        calendar = Calendar.getInstance();

        nik.setText(NIK);
        nama.setText(Nama);
        besarPremi.setText(BesarPremi);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        referenceTransaksiTravel.child(NIK).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()){
                String imageuri = snapshot.child("linkBukti").getValue(String.class);
                Glide.with(this).load(imageuri).into(buktiPembayaran);
            }
        });

        referenceTransaksiHealth.child(NIK).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()){
                String imageuri = snapshot.child("linkBukti").getValue(String.class);
                Glide.with(this).load(imageuri).into(buktiPembayaran);
            }
        });

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
                String second = String.format("%02d",calendar.get(Calendar.SECOND));
                String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
                String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                String currenttime = hour + " : " + minute + " : " + second;
                String currentdate = day + " - " + month + " - " + year;

                Log.d("NIK", NIK);
                Log.d("Nomor", NomorPolis);

                BuktiBayar pembayaran = new BuktiBayar(NIK, Nama, BesarPremi, NomorPolis, currenttime, currentdate);
                referencePembayaran.child(NIK).child(NomorPolis).setValue(pembayaran);


                referenceTransaksiHealth.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (Objects.equals(NomorPolis, snapshot.child(NIK).child("nomorPolisKesehatan").getValue(String.class))){
                            referenceTransaksiHealth.child(NIK).child("check").setValue("Approve");
                            String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
                            String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                            String year = String.valueOf(calendar.get(Calendar.YEAR));

                            String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                            String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
                            String second = String.format("%02d",calendar.get(Calendar.SECOND));

                            String currentdate = day + " - " + month + " - " + year;
                            String currenttime = hour + " : " + minute + " : " + second;
                            NotifikasiModel notifikasiModel = new NotifikasiModel("Diterima", currenttime, currentdate, "Pembayaran", "Health");
                            notifikasiModel.NewNotification(notifikasiModel, NIK);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                referenceTransaksiTravel.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (Objects.equals(NomorPolis, snapshot.child(NIK).child("nomorPolisTravel").getValue(String.class))){
                            referenceTransaksiTravel.child(NIK).child("check").setValue("Approve");
                            String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
                            String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                            String year = String.valueOf(calendar.get(Calendar.YEAR));

                            String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                            String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
                            String second = String.format("%02d",calendar.get(Calendar.SECOND));

                            String currentdate = day + " - " + month + " - " + year;
                            String currenttime = hour + " : " + minute + " : " + second;
                            NotifikasiModel notifikasiModel = new NotifikasiModel("Diterima", currenttime, currentdate, "Pembayaran", "Travel");
                            notifikasiModel.NewNotification(notifikasiModel, NIK);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(getApplicationContext(), HomePageAsuransiPembayaran.class);
                startActivity(intent);
            }
        });
    }
}