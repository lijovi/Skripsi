package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import java.util.Locale;
import java.util.Objects;

public class InsuranceInfoNasabah extends AppCompatActivity {

    Button btnHome, btnNotifikasi, btnProfile, btnOk;
    TextView limitHealth, limitTravel, lupaPassword, nomorPolisTravel, namaTravel;
    int LimitHealth, LimitTravel;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextInputLayout password;
    ScrollView content;
    String NomorPolisTravel, NamaTravel;
    FirebaseDatabase database;
    DatabaseReference reference;
    TableLayout tableMedicalHistory;

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
        nomorPolisTravel = findViewById(R.id.nomorPolisTravel);
        namaTravel = findViewById(R.id.namaTravel);
        tableMedicalHistory = findViewById(R.id.tableMedicalHistory);


        LimitHealth = ClientSession.getInstance().getLimitHealth();
        LimitTravel = ClientSession.getInstance().getLimitTravel();
        NamaTravel = ClientSession.getInstance().getNama();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("transaksiTravel");

        String NIK = ClientSession.getInstance().getNik();
        Query checkTravel = reference.orderByChild("nik").equalTo(NIK);
        Log.d("INTENT", "NIK: " + NIK);

        checkTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot data : snapshot.getChildren()) {
                        NomorPolisTravel = data.child("nomorPolisTravel").getValue(String.class);
                        Log.d("INTENT", "Received NIK: " + NomorPolisTravel);
                        nomorPolisTravel.setText(NomorPolisTravel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });

        Locale locale = new Locale("in", "ID");
        NumberFormat idrFormat = NumberFormat.getInstance(locale);
        limitHealth.setText("Rp " + idrFormat.format((double) LimitHealth));
        limitTravel.setText("Rp " + idrFormat.format((double) LimitTravel));
        namaTravel.setText(NamaTravel);


        loadMedicalHistory(NIK);

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

    private void loadMedicalHistory(String nik) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("riwayatMedis");
        Query query = ref.orderByChild("nik").equalTo(nik);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (tableMedicalHistory.getChildCount() > 1) {
                    tableMedicalHistory.removeViews(1, tableMedicalHistory.getChildCount() - 1);
                }

                if (snapshot.exists()) {
                    int index = 0; // zebra striping

                    for (DataSnapshot data : snapshot.getChildren()) {
                        TableRow row = new TableRow(InsuranceInfoNasabah.this);

                        if (index % 2 == 0) {
                            row.setBackgroundColor(Color.parseColor("#F5F5F5"));
                        } else {
                            row.setBackgroundColor(Color.WHITE);
                        }
                        index++;

                        TextView tgl = new TextView(InsuranceInfoNasabah.this);
                        tgl.setText(data.child("tglDiagnosa").getValue(String.class));
                        tgl.setPadding(6,6,6,6);

                        TextView kondisi = new TextView(InsuranceInfoNasabah.this);
                        kondisi.setText(data.child("kondisi").getValue(String.class));
                        kondisi.setPadding(6,6,6,6);

                        TextView klaim = new TextView(InsuranceInfoNasabah.this);
                        klaim.setText(data.child("besarklaim").getValue(String.class));
                        klaim.setPadding(6,6,6,6);

                        TextView status = new TextView(InsuranceInfoNasabah.this);
                        status.setText(data.child("statusKlaim").getValue(String.class));
                        status.setPadding(6,6,6,6);

                        row.addView(tgl);
                        row.addView(kondisi);
                        row.addView(klaim);
                        row.addView(status);

                        tableMedicalHistory.addView(row);
                    }
                } else {
                    Toast.makeText(InsuranceInfoNasabah.this, "Riwayat medis kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error load medis: " + error.getMessage());
            }
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
        btnOk.setOnClickListener(v -> {
            String Password = password.getEditText().getText().toString();
            String cekPassword = ClientSession.getInstance().getPassword();

            if (Objects.equals(Password, cekPassword)){
                alertDialog.dismiss();
                content.setVisibility(View.VISIBLE);
            } else {
                password.setError("Wrong Password");
            }
        });
    }
}
