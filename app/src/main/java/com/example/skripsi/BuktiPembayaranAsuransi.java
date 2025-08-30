package com.example.skripsi;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class BuktiPembayaranAsuransi extends AppCompatActivity {

    TextView nik, nama, besarPremi;
    String NIK, Nama, BesarPremi, NomorPremi;
    StorageReference storage;
    String imageurl;
    ImageView buktiPembayaran;


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

        NIK = getIntent().getStringExtra("nik");
        Nama = getIntent().getStringExtra("nama");
        BesarPremi = getIntent().getStringExtra("besarPremi");
        NomorPremi = getIntent().getStringExtra("nomorPremi");

        nik.setText(NIK);
        nama.setText(Nama);
        besarPremi.setText(BesarPremi);

        storage = FirebaseStorage.getInstance().getReference().child(NomorPremi + ".jpg");

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        storage.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

        }).addOnFailureListener(exception->{

        });

        long MEGABYTE = 1024*1024;
        storage.getBytes(MEGABYTE).addOnSuccessListener(bytes -> {

        }).addOnFailureListener(exception->{

        });

        storage.getDownloadUrl().addOnSuccessListener(uri -> {
            imageurl = uri.toString();
        }).addOnFailureListener(exception->{

        });

        Glide.with(this).load(imageurl).into(buktiPembayaran);


    }
}