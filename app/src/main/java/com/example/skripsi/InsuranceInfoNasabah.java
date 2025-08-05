package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
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
import java.util.Currency;
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
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHome = findViewById(R.id.btnHome);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        btnProfile = findViewById(R.id.btnProfile);
        limitHealth = findViewById(R.id.limitHealth);
        limitTravel = findViewById(R.id.limitTravel);
        content = findViewById(R.id.insuranceContent);
        nomorPolisTravel = findViewById(R.id.nomorPolisTravel);
        namaTravel = findViewById(R.id.namaTravel);

        LimitHealth = ClientSession.getInstance().getLimitHealth();
        LimitTravel = ClientSession.getInstance().getLimitTravel();
        NamaTravel = ClientSession.getInstance().getNama();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("transaksi");

        String NIK = ClientSession.getInstance().getNik();
        Query check = reference.orderByChild("nik").equalTo(NIK);
        Log.d("INTENT", "NIK: " + NIK);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisTravel = snapshot.child(NIK).child("nomorPolisTravel").getValue(String.class);
                    Log.d("INTENT", "Received NIK: " + NomorPolisTravel);
                    nomorPolisTravel.setText(NomorPolisTravel);
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
        namaTravel.setText(NamaTravel);

        DialogForm();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);
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
                Intent intent = new Intent(getApplicationContext(), ProfileNasabah.class);
                startActivity(intent);
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
}