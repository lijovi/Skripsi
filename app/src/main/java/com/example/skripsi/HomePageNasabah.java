package com.example.skripsi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePageNasabah extends AppCompatActivity {

    TextView nama;
    String Nama;
    ImageView bayarPremi, riwayatPembayaran, daftarAsuransi;

    @SuppressLint("MissingInflatedId")
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

        Nama = ClientSession.getInstance().getNama();
        nama.setText(Nama + " !");

        bayarPremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }
}