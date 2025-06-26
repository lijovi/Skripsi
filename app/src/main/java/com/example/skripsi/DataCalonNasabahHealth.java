package com.example.skripsi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DataCalonNasabahHealth extends AppCompatActivity {

    TextView nik, nama, email, jenisKelamin, tanggalLahir, noTelepon, alamat,
            pekerjaan, periodePertanggungan, planAsuransi, riwayatPenyakit;

    @SuppressLint("MissingInflatedId")
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
        email = findViewById(R.id.email);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        tanggalLahir = findViewById(R.id.tanggalLahir);
        noTelepon = findViewById(R.id.noTelepon);
        alamat = findViewById(R.id.alamat);
        pekerjaan = findViewById(R.id.pekerjaan);
        periodePertanggungan = findViewById(R.id.periodePertanggungan);
        planAsuransi = findViewById(R.id.planAsuransi);
        riwayatPenyakit = findViewById(R.id.riwayatPenyakit);

        String NIK = ClientSession.getInstance().getNik();
        String Nama = ClientSession.getInstance().getNama();
        String Email = ClientSession.getInstance().getEmail();
        String JenisKelamin = ClientSession.getInstance().getGender();
        String TanggalLahir = ClientSession.getInstance().getBod();
        String NoTelp = ClientSession.getInstance().getNoTelp();
        String Alamat = ClientSession.getInstance().getAddress();
        String Pekerjaan = ClientSession.getInstance().getPekerjaan();
        String periodePertanggungan = ClientSession.getInstance().getPeriodePertanggunganHealth();
        String planAsuransi = ClientSession.getInstance().getPlanHealth();

    }
}