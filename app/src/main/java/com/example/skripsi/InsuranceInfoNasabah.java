package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import java.util.Currency;
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
    DatabaseReference referenceTravel, referenceHealth, referenceDataHealth, referenceDataTravel;
    TableLayout tableMedicalHistory, tableKlaimAsuransiHealth, tableKlaimAsuransiTravel;

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

        // TABEL
        tableMedicalHistory = findViewById(R.id.tableMedicalHistory);
        tableKlaimAsuransiHealth = findViewById(R.id.tableKlaimAsuransiHealth);
        tableKlaimAsuransiTravel = findViewById(R.id.tableKlaimAsuransiTravel);

        String NIK = ClientSession.getInstance().getNik();
        loadMedicalHistory(NIK);
        Query checkTravel = referenceTravel.orderByChild("nik").equalTo(NIK);
        Log.d("INTENT", "NIK: " + NIK);
        checkTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisTravel = snapshot.child(NIK).child("nomorPolisTravel").getValue(String.class);
                    Log.d("INTENT", "Received NIK: " + NomorPolisTravel);
                    nomorPolisTravel.setText(NomorPolisTravel);
                    namaTravel.setText(Nama);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkDataTravel = referenceDataTravel.orderByChild("nik").equalTo(NIK);
        checkDataTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    jenisTravel.setText(snapshot.child(NIK).child("planAsuransi").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkHealth = referenceHealth.orderByChild("nik").equalTo(NIK);
        checkHealth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisHealth = snapshot.child(NIK).child("nomorPolisKesehatan").getValue(String.class);
                    nomorPolisHealth.setText(NomorPolisHealth);
                    namaHealth.setText(Nama);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkDataHealth = referenceDataHealth.orderByChild("nik").equalTo(NIK);
        checkDataHealth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    jenisHealth.setText(snapshot.child(NIK).child("plan").getValue(String.class));
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
    }

    private void addRowToTable(TableLayout table, String[] data, int position) {
        TableRow row = new TableRow(this);

        // Zebra striping
        if (position % 2 == 0) {
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.zebra_light));
        } else {
            row.setBackgroundColor(ContextCompat.getColor(this, R.color.zebra_dark));
        }

        // Create cells
        for (String value : data) {
            TextView tv = new TextView(this);
            tv.setText(value);
            tv.setPadding(16, 12, 16, 12);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(14);
            row.addView(tv);
        }

        table.addView(row);
    }

    // buat isi tabel
    private void loadMedicalHistory(String nik) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("riwayatMedis");
        Query query = ref.orderByChild("nik").equalTo(nik);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear old rows except header
                if (tableMedicalHistory.getChildCount() > 1) {
                    tableMedicalHistory.removeViews(1, tableMedicalHistory.getChildCount() - 1);
                }

                int position = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    String tgl = data.child("tglDiagnosa").getValue(String.class);
                    String kondisi = data.child("kondisi").getValue(String.class);
                    String klaim = data.child("besarklaim").getValue(String.class);
                    String status = data.child("statusKlaim").getValue(String.class);

                    addRowToTable(tableMedicalHistory,
                            new String[]{tgl, kondisi, klaim, status},
                            position);
                    position++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InsuranceInfoNasabah.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}