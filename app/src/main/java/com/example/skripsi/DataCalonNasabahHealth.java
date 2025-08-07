package com.example.skripsi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class DataCalonNasabahHealth extends AppCompatActivity {

    TextView nik, nama, email, jenisKelamin, tanggalLahir, noTelepon, alamat,
            pekerjaan, periodePertanggungan, planAsuransi, riwayatPenyakit;

    Button btnTerima, btnTolak, btnOkPolis, btnOkPremi;

    FirebaseDatabase database;
    DatabaseReference reference, referenceTransaksi, referenceNasabah;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextInputLayout nomorPolis, besarPremi;
    String NIK, NomorPolis;
    int BesarPremi;
    int Company;
    Calendar calendar, calendarJ;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_calon_nasabah_health);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NIK = getIntent().getStringExtra("nik");
        Company = getIntent().getIntExtra("company",0);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("clientSementara").child(NIK);
        referenceTransaksi = database.getReference("transaksiHealth").child(NIK);
        referenceNasabah = database.getReference("clientHealth").child(NIK);
        calendar = Calendar.getInstance();
        calendarJ = Calendar.getInstance();
        calendarJ.add(Calendar.DAY_OF_MONTH, 7);

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

        btnTerima = findViewById(R.id.btnTerima);
        btnTolak = findViewById(R.id.btnTolak);

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
                for (String item : list){
                    sb.append("- ").append(item).append("\n");
                }
                riwayatPenyakit.setText(sb.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormPolis();
            }
        });

        btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                startActivity(intent);
            }
        });
    }

    private void DialogFormPolis() {
        dialog = new AlertDialog.Builder(DataCalonNasabahHealth.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.pop_up_input_polis, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        nomorPolis = dialogView.findViewById(R.id.nomorPolis);
        btnOkPolis = dialogView.findViewById(R.id.btnOkPolis);
        btnOkPolis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                NomorPolis = nomorPolis.getEditText().getText().toString();
                DialogFormPremi();
            }
        });
    }

    private void DialogFormPremi() {
        dialog = new AlertDialog.Builder(DataCalonNasabahHealth.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.pop_up_input_premi, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
//
        besarPremi = dialogView.findViewById(R.id.besarPremi);
        btnOkPremi = dialogView.findViewById(R.id.btnOkPremi);
        btnOkPremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BesarPremi = Integer.parseInt(besarPremi.getEditText().getText().toString());
                String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
                String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                String currentdate = day + " - " + month + " - " + year;

                String day1 = String.format("%02d" ,calendarJ.get(Calendar.DAY_OF_MONTH));
                String month1 = String.format("%02d",calendarJ.get(Calendar.MONTH)+1);
                String year1 = String.valueOf(calendarJ.get(Calendar.YEAR));
                String jatuhTempo = day1 + " - " + month1 + " - " + year1;

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        referenceNasabah.setValue(snapshot.getValue());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                referenceTransaksi.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TransaksiHealth transaksi = new TransaksiHealth(NIK, BesarPremi, NomorPolis, Company, currentdate, jatuhTempo);
                        referenceTransaksi.setValue(transaksi);
                        Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                        startActivity(intent);
                        reference.removeValue();
                        alertDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}