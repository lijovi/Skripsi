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
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Objects;

public class BuktiPembayaranHistory extends AppCompatActivity {
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
        setContentView(R.layout.activity_bukti_pembayaran_history);
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
    }
}