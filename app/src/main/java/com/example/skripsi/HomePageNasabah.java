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

import java.util.Objects;

public class HomePageNasabah extends AppCompatActivity {

    TextView nama, disini, company_name, contact_person, no_asuransi;
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

        String Company = String.valueOf(ClientSession.getInstance().getCompany());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("company").child(Company);

        nama = findViewById(R.id.nama);
        bayarPremi = findViewById(R.id.bayarPremi);
        riwayatPembayaran = findViewById(R.id.riwayatPembayaran);
        daftarAsuransi = findViewById(R.id.daftarAsuransi);

        company_name = findViewById(R.id.company_name);
        contact_person = findViewById(R.id.contact_person);
        no_asuransi = findViewById(R.id.no_asuransi);

        btnHome = findViewById(R.id.btnHome);
        btnInfo = findViewById(R.id.btnInfo);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        btnProfile = findViewById(R.id.btnProfile);

        infoPassword = findViewById(R.id.infoPassword);
        disini = findViewById(R.id.disini);

        Nama = ClientSession.getInstance().getNama();
        nama.setText(Nama + " !");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    company_name.setText(snapshot.child("companyName").getValue(String.class));
                    contact_person.setText(snapshot.child("companyContactPerson").getValue(String.class));
                    no_asuransi.setText(snapshot.child("companyPhoneNumber").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                ClientSession.getInstance().setNik(ClientSession.getInstance().getNik());
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