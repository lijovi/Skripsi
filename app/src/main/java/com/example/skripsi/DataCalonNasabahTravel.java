package com.example.skripsi;

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

import java.util.Calendar;
import java.util.Objects;

public class DataCalonNasabahTravel extends AppCompatActivity {

    TextView nik, nama, email, jenisKelamin, alamat, jenisPolis, planAsuransi, masaPerjalanan, tipePolis,
            lamaPerjalanan, namaAhliWaris, hubunganDenganAhliWaris,  negaraTujuan, tujuanPerjalanan;

    Button btnTerima, btnTolak, btnOkPremi, btnOkPolis;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_calon_nasabah_travel);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NIK = getIntent().getStringExtra("nik");
        Company = getIntent().getIntExtra("company",0);

        if (NIK != null) {
            Log.d("INTENT", "Received NIK: " + Company);
        } else {
            Log.e("INTENT", "NIK is null");
        }

        if (NIK == null || NIK.isEmpty()) {
            Log.e("SESSION_ERROR", "NIK is null or empty!");

            return; // stop execution to prevent crash
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("clientSementara").child(NIK);
        referenceTransaksi = database.getReference("transaksiTravel").child(NIK);
        referenceNasabah = database.getReference("clientTravel").child(NIK);
        calendar = Calendar.getInstance();
        calendarJ = Calendar.getInstance();
        calendarJ.add(Calendar.DAY_OF_MONTH, 7);

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
        btnTerima = findViewById(R.id.btnTerima);
        btnTolak = findViewById(R.id.btnTolak);

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
        dialog = new AlertDialog.Builder(DataCalonNasabahTravel.this);
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
        dialog = new AlertDialog.Builder(DataCalonNasabahTravel.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.pop_up_input_premi, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
//
        besarPremi = dialogView.findViewById(R.id.besarPremi);
        btnOkPremi = dialogView.findViewById(R.id.btnOkPremi);
//

//
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
                        TransaksiTravel transaksi = new TransaksiTravel(NIK, BesarPremi, NomorPolis, Company, currentdate, jatuhTempo);
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