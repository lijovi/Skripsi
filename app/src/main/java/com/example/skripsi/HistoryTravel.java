package com.example.skripsi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistoryTravel extends AppCompatActivity {

    Button btnBack;
    TextView nik, nama, email, jenisKelamin, alamat, jenisPolis, planAsuransi, masaPerjalanan, tipePolis,
            lamaPerjalanan, namaAhliWaris, hubunganDenganAhliWaris, negaraTujuan, tujuanPerjalanan;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_history_travel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btnBack);
        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        alamat = findViewById(R.id.alamat);
        jenisPolis = findViewById(R.id.jenisPolis);
        planAsuransi = findViewById(R.id.planAsuransi);
        masaPerjalanan = findViewById(R.id.masaPerjalanan);
        tipePolis = findViewById(R.id.tipePolis);
        lamaPerjalanan = findViewById(R.id.lamaPerjalanan);
        namaAhliWaris = findViewById(R.id.namaAhliWaris);
        hubunganDenganAhliWaris = findViewById(R.id.hubunganDenganAhliWaris);
        negaraTujuan = findViewById(R.id.negaraTujuan);
        tujuanPerjalanan = findViewById(R.id.tujuanPerjalanan);

        String NIK = getIntent().getStringExtra("nik");
        int Company = getIntent().getIntExtra("company",0);

        reference = FirebaseDatabase.getInstance().getReference("userData").child(NIK);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Nik = snapshot.child("nik").getValue(String.class);
                String Nama = snapshot.child("name").getValue(String.class);
                String Email = snapshot.child("email").getValue(String.class);
                String JenisKelamin = snapshot.child("gender").getValue(String.class);
                String Alamat = snapshot.child("address").getValue(String.class);
                String JenisPolis = snapshot.child("jenisPolis").getValue(String.class);
                String PlanAsuransi = snapshot.child("planAsuransi").getValue(String.class);
                String MasaPerjalanan = snapshot.child("masaPerjalanan").getValue(String.class);
                String TipePolis = snapshot.child("tipePolis").getValue(String.class);
                String LamaPerjalanan = snapshot.child("lamaPerjalanan").getValue(String.class);
                String NamaAhliWaris = snapshot.child("namaAhliWaris").getValue(String.class);
                String HubunganDenganAhliWaris = snapshot.child("hubunganDenganAhliWaris").getValue(String.class);
                String NegaraTujuan = snapshot.child("negaraTujuan").getValue(String.class);
                String TujuanPerjalanan = snapshot.child("tujuanPerjalanan").getValue(String.class);
                nik.setText(Nik);
                nama.setText(Nama);
                email.setText(Email);
                jenisKelamin.setText(JenisKelamin);
                alamat.setText(Alamat);
                jenisPolis.setText(JenisPolis);
                planAsuransi.setText(PlanAsuransi);
                masaPerjalanan.setText(MasaPerjalanan);
                tipePolis.setText(TipePolis);
                lamaPerjalanan.setText(LamaPerjalanan);
                namaAhliWaris.setText(NamaAhliWaris);
                hubunganDenganAhliWaris.setText(HubunganDenganAhliWaris);
                negaraTujuan.setText(NegaraTujuan);
                tujuanPerjalanan.setText(TujuanPerjalanan);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}