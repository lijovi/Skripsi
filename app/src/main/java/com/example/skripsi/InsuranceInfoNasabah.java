package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class InsuranceInfoNasabah extends AppCompatActivity {

    Button btnHome, btnNotifikasi, btnProfile, btnOk;
    TextView limitTravel, lupaPassword, nomorPolisTravel, namaTravel, jenisTravel, statusTravel, jangkaTravel;
    TextView limitHealth, nomorPolisHealth, namaHealth, jenisHealth, statusHealth, jangkaHealth;
    int LimitHealth, LimitTravel;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextInputLayout password;
    ScrollView content;
    String NomorPolisTravel, Nama;
    String NomorPolisHealth;
    FirebaseDatabase database;
    DatabaseReference referenceTravel, referenceHealth, referenceDataHealth, referenceDataTravel, referencePembayaran;
    String checkH, checkT;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insurance_info_nasabah);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) supportActionBar.hide();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        btnHome = findViewById(R.id.btnHome);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        btnProfile = findViewById(R.id.btnProfile);
        limitHealth = findViewById(R.id.limitHealth);
        limitTravel = findViewById(R.id.limitTravel);
        content = findViewById(R.id.insuranceContent);

        // inisialisasi data travel
        nomorPolisTravel = findViewById(R.id.nomorPolisTravel);
        namaTravel = findViewById(R.id.namaTravel);
        jenisTravel = findViewById(R.id.jenisTravel);
        statusTravel = findViewById(R.id.statusTravel);
        jangkaTravel = findViewById(R.id.jangkaTravel);

        // inisialisasi data health
        nomorPolisHealth = findViewById(R.id.nomorPolisHealth);
        namaHealth = findViewById(R.id.namaHealth);
        jenisHealth = findViewById(R.id.jenisHealth);
        statusHealth = findViewById(R.id.statusHealth);
        jangkaHealth = findViewById(R.id.jangkaHealth);

        // Set values
        LimitHealth = ClientSession.getInstance().getLimitHealth();
        LimitTravel = ClientSession.getInstance().getLimitTravel();
        Nama = ClientSession.getInstance().getNama();


        // DATABASE
        database = FirebaseDatabase.getInstance();
        referenceTravel = database.getReference("transaksiTravel");
        referenceHealth = database.getReference("transaksiHealth");
        referenceDataHealth = database.getReference("clientHealth");
        referenceDataTravel = database.getReference("clientTravel");
        referencePembayaran = database.getReference("pembayaran");

        String NIK = ClientSession.getInstance().getNik();
        Query checkTravel = referenceTravel.orderByChild("nik").equalTo(NIK);
        Log.d("INTENT", "NIK: " + NIK);
        checkTravel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisTravel = snapshot.child(NIK).child("nomorPolisTravel").getValue(String.class);
                    Log.d("INTENT", "Received NIK: " + NomorPolisTravel);
                    nomorPolisTravel.setText(NomorPolisTravel);
                    namaTravel.setText(Nama);
                    if (snapshot.child(NIK).hasChild("check")){
                        checkT = snapshot.child(NIK).child("check").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkDataTravel = referenceDataTravel.orderByChild("nik").equalTo(NIK);
        checkDataTravel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    jenisTravel.setText(snapshot.child(NIK).child("planAsuransi").getValue(String.class));
                    String cek = snapshot.child(NIK).child("tipePolis").getValue(String.class);
                    if (Objects.equals(cek, "Iya")){
                        jangkaTravel.setText(R.string.tahunan);
                        referencePembayaran.child(NIK).child(NomorPolisTravel).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String date = snapshot.child("date").getValue(String.class);
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
                                    LocalDate start = LocalDate.parse(date, format);
                                    LocalDate end = start.plusYears(1);
                                    LocalDate current = LocalDate.now();

                                    if (Objects.equals(checkT, "Approve")){
                                        if (!current.isBefore(start) && !current.isAfter(end)){
                                            statusTravel.setText(R.string.aktif);
                                        } else {
                                            statusTravel.setText(R.string.non_aktif);
                                        }
                                    } else {
                                        statusTravel.setText(R.string.non_aktif);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        String fulltext = snapshot.child(NIK).child("masaPerjalanan").getValue(String.class);
                        String[] part = fulltext.split("-");
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            Date date1 = dates.parse(part[0].trim());
                            Date date2 = dates.parse(part[1].trim());

                            Long difference = (Math.abs(date1.getTime() - date2.getTime()) / (24*60*60*1000))+1;
                            jangkaTravel.setText(difference.toString());

                            Date currentDate = dates.parse(dates.format(new Date()));
                            Log.d("MyApp", "Current Date: " + dates.format(currentDate));

                            if (Objects.equals(checkT, "Approve")){
                                if (!currentDate.before(date1) && !currentDate.after(date2)){
                                    statusTravel.setText(R.string.aktif);
                                } else {
                                    statusTravel.setText(R.string.non_aktif);
                                }
                            } else {
                                statusTravel.setText(R.string.non_aktif);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkHealth = referenceHealth.orderByChild("nik").equalTo(NIK);
        checkHealth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisHealth = snapshot.child(NIK).child("nomorPolisKesehatan").getValue(String.class);
                    nomorPolisHealth.setText(NomorPolisHealth);
                    namaHealth.setText(Nama);
//                    Log.d("INTENT", "NOMOR POLIS: " + NomorPolisHealth);
//                    Log.d("INTENT", "NAMA: " + Nama);
                    if (snapshot.child(NIK).hasChild("check")){
                        checkH = snapshot.child(NIK).child("check").getValue(String.class);
                    }
//                    Log.d("CHECK", checkH);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query checkDataHealth = referenceDataHealth.orderByChild("nik").equalTo(NIK);
        checkDataHealth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    jenisHealth.setText(snapshot.child(NIK).child("plan").getValue(String.class));
                    jangkaHealth.setText(R.string.tahunan);
//                    Log.d("INTENT", "JENIS HEALTH: " + jenisHealth);
//                    Log.d("INTENT", "JANGKA HEALTH: " + jangkaHealth);
//
                    if (Objects.equals(checkH, "Approve")){
                        String date = snapshot.child(NIK).child("periodePertanggungan").getValue(String.class);
//                        Log.d("INTENT", "DATE: " + date);
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate start = LocalDate.parse(date, format);
                        LocalDate end = start.plusYears(1);
                        LocalDate current = LocalDate.now();
                        if (!current.isBefore(start) && !current.isAfter(end)){
                            statusHealth.setText(R.string.aktif);
                        } else {
                            statusHealth.setText(R.string.non_aktif);
                        }
                    } else {
                        statusHealth.setText(R.string.non_aktif);
                    }
//                    String date = snapshot.child(NIK).child("date").getValue(String.class);
//                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
//                    LocalDate start = LocalDate.parse(date, format);
//                    LocalDate end = start.plusYears(1);
//                    LocalDate current = LocalDate.now();
//                    if (!current.isBefore(start) && !current.isAfter(end)){
//                        statusHealth.setText(R.string.aktif);
//                    } else {
//                        statusHealth.setText(R.string.non_aktif);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Locale locale = new Locale("in", "ID");

        NumberFormat idrFormat = NumberFormat.getInstance(locale);

        limitHealth.setText("Rp " + idrFormat.format((double) LimitHealth));
        limitTravel.setText("Rp " + idrFormat.format((double) LimitTravel));
//        limitHealth.setText(LimitHealth);
//        limitTravel.setText(LimitTravel);

        // Show popup on load
        DialogForm();

        // Button listeners
        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomePageNasabah.class));
        });

        btnNotifikasi.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), NotificationNasabah.class));
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProfileNasabah.class));
        });
    }


    private void DialogForm() {
        dialog = new AlertDialog.Builder(InsuranceInfoNasabah.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.input_password_nasabah, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        password = dialogView.findViewById(R.id.password);
        lupaPassword = dialogView.findViewById(R.id.lupaPassword);
        btnOk = dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = password.getEditText().getText().toString();
                String cekPassword = ClientSession.getInstance().getPassword();

                if (Objects.equals(Password, cekPassword)){
                    alertDialog.dismiss();
                    content.setVisibility(View.VISIBLE);

                } else {
                    password.setError("Wrong Password");
                }
            }
        });

        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UbahPasswordNasabah.class);
                startActivity(intent);
            }
        });
    }

}