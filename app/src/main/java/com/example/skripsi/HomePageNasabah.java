package com.example.skripsi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomePageNasabah extends AppCompatActivity {

    TextView nama, disini, company_name_health, contact_person_health, no_asuransi_health, company_name_travel, contact_person_travel, no_asuransi_travel;
    LinearLayout infoPassword, perusahaanHealth, perusahaanTravel, contactHealth, contactTravel, telpHealth, telpTravel;
    String Nama, Password;
    ImageView bayarPremi, riwayatPembayaran, daftarAsuransi;
    Button btnHome, btnInfo, btnNotifikasi, btnProfile;
    int companyHealth, companyTravel;
    int check = 0;

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

//        String Company = String.valueOf(ClientSession.getInstance().getCompany());
        String NIK = ClientSession.getInstance().getNik();

        DatabaseReference dataHealth = FirebaseDatabase.getInstance().getReference("clientHealth").child(NIK);
//        Query checkHealth = dataHealth.orderByChild("nik").equalTo(NIK);
        DatabaseReference dataTravel = FirebaseDatabase.getInstance().getReference("clientTravel").child(NIK);
//        Query checkTravel = dataTravel.orderByChild("nik").equalTo(NIK);

        nama = findViewById(R.id.nama);
        bayarPremi = findViewById(R.id.bayarPremi);
        riwayatPembayaran = findViewById(R.id.riwayatPembayaran);
        daftarAsuransi = findViewById(R.id.daftarAsuransi);

        company_name_health = findViewById(R.id.company_name_health);
        contact_person_health = findViewById(R.id.contact_person_health);
        no_asuransi_health = findViewById(R.id.no_asuransi_health);

        company_name_travel = findViewById(R.id.company_name_travel);
        contact_person_travel = findViewById(R.id.contact_person_travel);
        no_asuransi_travel = findViewById(R.id.no_asuransi_travel);

        perusahaanHealth = findViewById(R.id.perusahaanHealth);
        perusahaanTravel = findViewById(R.id.perusahaanTravel);
        contactHealth = findViewById(R.id.contactHealth);
        contactTravel = findViewById(R.id.contactTravel);
        telpHealth = findViewById(R.id.telpHealth);
        telpTravel = findViewById(R.id.telpTravel);

        btnHome = findViewById(R.id.btnHome);
        btnInfo = findViewById(R.id.btnInfo);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        btnProfile = findViewById(R.id.btnProfile);

        infoPassword = findViewById(R.id.infoPassword);
        disini = findViewById(R.id.disini);

        Nama = ClientSession.getInstance().getNama();
        nama.setText(Nama + " !");

        Password = ClientSession.getInstance().getPassword();
        Log.d("PASSWORD", "PASSWORD: " + Password);
        if (Objects.equals(Password, "0")){
            infoPassword.setVisibility(View.VISIBLE);
        } else {
            infoPassword.setVisibility(View.GONE);
        }

        dataHealth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    companyHealth = snapshot.child("company").getValue(int.class);
                    DatabaseReference refHealth = FirebaseDatabase.getInstance().getReference("company").child(String.valueOf(companyHealth));

                    refHealth.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                company_name_health.setText(snapshot.child("companyName").getValue(String.class));
                                contact_person_health.setText(snapshot.child("companyContactPerson").getValue(String.class));
                                no_asuransi_health.setText(snapshot.child("companyPhoneNumber").getValue(String.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    perusahaanHealth.setVisibility(View.GONE);
                    contactHealth.setVisibility(View.GONE);
                    telpHealth.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dataTravel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    companyTravel = snapshot.child("company").getValue(int.class);
                    DatabaseReference refTravel = FirebaseDatabase.getInstance().getReference("company").child(String.valueOf(companyTravel));
                    Log.d("INTENT", "nama: " + companyTravel);
                    refTravel.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Log.d("INTENT", "nama: " + snapshot.child("companyName").getValue(String.class));
                                company_name_travel.setText(snapshot.child("companyName").getValue(String.class));
                                contact_person_travel.setText(snapshot.child("companyContactPerson").getValue(String.class));
                                no_asuransi_travel.setText(snapshot.child("companyPhoneNumber").getValue(String.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    perusahaanTravel.setVisibility(View.GONE);
                    contactTravel.setVisibility(View.GONE);
                    telpTravel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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