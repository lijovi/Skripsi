package com.example.skripsi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class HomePageNasabah extends AppCompatActivity {

    TextView nama, disini;
    LinearLayout infoPassword;
    String Nama, Password;
    ImageView bayarPremi, riwayatPembayaran, daftarAsuransi;
    Button btnHome, btnInfo, btnNotifikasi, btnProfile;

    @SuppressLint("MissingInflatedId")

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page_nasabah);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nama = findViewById(R.id.nama);
        bayarPremi = findViewById(R.id.bayarPremi);
        riwayatPembayaran = findViewById(R.id.riwayatPembayaran);
        daftarAsuransi = findViewById(R.id.daftarAsuransi);

        btnHome = findViewById(R.id.btnHome);
        btnInfo = findViewById(R.id.btnInfo);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        btnProfile = findViewById(R.id.btnProfile);

        infoPassword = findViewById(R.id.infoPassword);
        disini = findViewById(R.id.disini);

        Nama = ClientSession.getInstance().getNama();
        nama.setText(Nama + " !");

        Password = ClientSession.getInstance().getPassword();


        if (Objects.equals(Password, "0")){
            infoPassword.setVisibility(View.VISIBLE);
        } else {
            infoPassword.setVisibility(View.GONE);
        }


        disini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuatPasswordNasabah.class);
                startActivity(intent);
            }
        });

        bayarPremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientSession.getInstance().setCompany(ClientSession.getInstance().getCompany());
                Intent intent = new Intent(getApplicationContext(), Pembayaran.class);
                startActivity(intent);
            }
        });

        daftarAsuransi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsuranceInfoNasabah.class);
                startActivity(intent);
            }
        });

        btnNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationNasabah.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageNasabah.this, ProfileNasabah.class);
                startActivity(intent);
            }
        });
    }
}