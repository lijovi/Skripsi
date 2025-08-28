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

import java.util.ArrayList;

public class HistoryHealth extends AppCompatActivity {

    Button btnBack;
    TextView nik, nama, email, jenisKelamin, tanggalLahir, noTelepon, alamat, pekerjaan, periodePertanggungan, planAsuransi, riwayatPenyakit;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_health);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
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
        tanggalLahir = findViewById(R.id.tanggalLahir);
        noTelepon = findViewById(R.id.noTelepon);
        alamat = findViewById(R.id.alamat);
        pekerjaan = findViewById(R.id.pekerjaan);
        periodePertanggungan = findViewById(R.id.periodePertanggungan);
        planAsuransi = findViewById(R.id.planAsuransi);
        riwayatPenyakit = findViewById(R.id.riwayatPenyakit);

        String NIK = getIntent().getStringExtra("nik");
        int Company = getIntent().getIntExtra("company",0);

        reference = FirebaseDatabase.getInstance().getReference("userData").child(NIK);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String NIK = snapshot.child("nik").getValue(String.class);
                String Nama = snapshot.child("name").getValue(String.class);
                String Email = snapshot.child("email").getValue(String.class);
                String JenisKelamin = snapshot.child("gender").getValue(String.class);
                String TanggalLahir = snapshot.child("bod").getValue(String.class);
                String NoTelp = snapshot.child("phoneNumber").getValue(String.class);
                String Alamat = snapshot.child("address").getValue(String.class);
                String Pekerjaan = snapshot.child("pekerjaan").getValue(String.class);
                String PeriodePertanggungan = snapshot.child("periodePertanggungan").getValue(String.class);
                String PlanAsuransi = snapshot.child("plan").getValue(String.class);
                ArrayList<String> list = (ArrayList<String>) snapshot.child("riwayatPenyakit").getValue();

                StringBuilder sb = new StringBuilder();
                nik.setText(NIK);
                nama.setText(Nama);
                email.setText(Email);
                jenisKelamin.setText(JenisKelamin);
                tanggalLahir.setText(TanggalLahir);
                noTelepon.setText(NoTelp);
                alamat.setText(Alamat);
                pekerjaan.setText(Pekerjaan);
                periodePertanggungan.setText(PeriodePertanggungan);
                planAsuransi.setText(PlanAsuransi);
                if (list == null){
                    riwayatPenyakit.setText(null);
                } else {
                    for (String item : list){
                        sb.append("- ").append(item).append("\n");
                    }
                    riwayatPenyakit.setText(sb.toString());
                }
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