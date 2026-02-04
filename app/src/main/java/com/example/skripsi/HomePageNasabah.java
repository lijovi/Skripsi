package com.example.skripsi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HomePageNasabah extends AppCompatActivity {

    TextView nama, disini, company_name_health, contact_person_health, no_asuransi_health, company_name_travel, contact_person_travel, no_asuransi_travel,
    namaRS1, namaRS2, namaRS3, namaRS4, telp1RS1, telp2RS1, telp1RS2, telp2RS2, telp1RS3, telp2RS3, telp1RS4, telp2RS4;
    LinearLayout infoPassword, perusahaanHealth, perusahaanTravel, contactHealth, contactTravel, telpHealth, telpTravel;
    String Nama, Password;
    ImageView bayarPremi, riwayatPembayaran, daftarAsuransi;
    Button btnHome, btnInfo, btnNotifikasi, btnProfile;
    int companyHealth = 0;
    int companyTravel = 0;
    int check = 0;
    TextView sariAsih, pondokIndah, harapanKita, pluit;
    int tipe;

    @SuppressLint("MissingInflatedId")

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
        setContentView(R.layout.activity_home_page_nasabah);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String Company = String.valueOf(ClientSession.getInstance().getCompany());
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

        namaRS1 = findViewById(R.id.namaRS1);
        namaRS2 = findViewById(R.id.namaRS2);
        namaRS3 = findViewById(R.id.namaRS3);
        namaRS4 = findViewById(R.id.namaRS4);

        telp1RS1 = findViewById(R.id.telp1RS1);
        telp2RS1 = findViewById(R.id.telp2RS1);
        telp1RS2 = findViewById(R.id.telp1RS2);
        telp2RS2 = findViewById(R.id.telp2RS2);
        telp1RS3 = findViewById(R.id.telp1RS3);
        telp2RS3 = findViewById(R.id.telp2RS3);
        telp1RS4 = findViewById(R.id.telp1RS4);
        telp2RS4 = findViewById(R.id.telp2RS4);

        sariAsih = findViewById(R.id.sariAsih);
        pondokIndah = findViewById(R.id.pondokIndah);
        harapanKita = findViewById(R.id.harapanKita);
        pluit = findViewById(R.id.pluit);

//        RumahSakit rumahSakit1 = new RumahSakit(4, "RS Sari Asih Karawaci", "021-552 2794", "021-552 3239", "https://maps.app.goo.gl/WmwYuGLUFo5EsNpd7?g_st=ipc");
//        RumahSakit rumahSakit2 = new RumahSakit(4,"RS Pondok Indah Puri Indah", "021-2569 5222", "021-2569 5200", "https://maps.app.goo.gl/awiAQnrS61opZQ7x6?g_st=ipc");
//        RumahSakit rumahSakit3 = new RumahSakit(4,"RSAB Harapan Kita", "021-566 8284", "021-566 8284", "https://maps.app.goo.gl/TDUEP1eZ7nwHNNmj7?g_st=ipc");
//        RumahSakit rumahSakit4 = new RumahSakit(4,"RS Pluit", "021-668 5006", "021-668 507", "https://maps.app.goo.gl/e8h4vF5s4f7AJNr6A?g_st=ipc");
//
//        info.child("4").child("1").setValue(rumahSakit1);
//        info.child("4").child("2").setValue(rumahSakit2);
//        info.child("4").child("3").setValue(rumahSakit3);
//        info.child("4").child("4").setValue(rumahSakit4);

        Password = ClientSession.getInstance().getPassword();
        Log.d("PASSWORD", "PASSWORD: " + Password);
        if (Objects.equals(Password, "0")){
            infoPassword.setVisibility(View.VISIBLE);
        } else {
            infoPassword.setVisibility(View.GONE);
        }

        dataHealth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    companyHealth = snapshot.child("company").getValue(int.class);
                    DatabaseReference refHealth = FirebaseDatabase.getInstance().getReference("company").child(String.valueOf(companyHealth));
                    loadRumahSakit(companyHealth, companyTravel);
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

//        DatabaseReference refPembayaran = FirebaseDatabase.getInstance().getReference("pembayaran").child(NIK);
//        refPembayaran.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        String tanggal =  dataSnapshot.child("date").getValue(String.class);
//                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
//                        LocalDate start = LocalDate.parse(tanggal, format);
//                        LocalDate end = start.plusYears(1);
//                        LocalDate current = LocalDate.now();
//                        LocalDate jatuhTempo = end.minusMonths(1);
//
//                        if (!current.isBefore(jatuhTempo) && current.isBefore(end)){
//                            String noPolis = dataSnapshot.child("nomorPremi").getValue(String.class);
//
//                            DatabaseReference checkHealth = FirebaseDatabase.getInstance().getReference("transaksiHealth").child(NIK);
//                            checkHealth.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if (snapshot.exists()){
//                                        if (Objects.equals(snapshot.child("nomorPolisKesehatan"), noPolis)){
//                                            tipe = 0;
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//                            DatabaseReference checkTravel = FirebaseDatabase.getInstance().getReference("transaksiTravel").child(NIK);
//                            checkTravel.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if (snapshot.exists()){
//                                        if (Objects.equals(snapshot.child("nomorPolisTravel"), noPolis)){
//                                            tipe = 1;
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//                            if (tipe == 0){
//                                DatabaseReference refHealth = FirebaseDatabase.getInstance().getReference("clientHealth").child(NIK);
//                                DatabaseReference inputH = FirebaseDatabase.getInstance().getReference("clientSementaraHealth").child(NIK);
//                                inputH.setValue(refHealth);
//                            } else if (tipe == 1) {
//                                DatabaseReference refTravel = FirebaseDatabase.getInstance().getReference("clientTravel").child(NIK);
//                                refTravel.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if (snapshot.exists()){
//                                            if (Objects.equals(snapshot.child("tipePolis"), "Iya")){
//                                                DatabaseReference inputT = FirebaseDatabase.getInstance().getReference("clientSementaraTravel").child(NIK);
//                                                inputT.setValue(refTravel);
//                                            }
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
        dataTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    companyTravel = snapshot.child("company").getValue(int.class);
                    loadRumahSakit(companyHealth, companyTravel);
                    if (companyTravel != companyHealth){
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

        riwayatPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RiwayatPembayaran.class);
                startActivity(intent);
            }
        });
    }

    private void loadRumahSakit(int companyHealth, int companyTravel) {
        DatabaseReference info = FirebaseDatabase.getInstance().getReference("rumahSakit");
        if (companyHealth!=0){
            info.child(String.valueOf(companyHealth)).child("1").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS1.setText(snapshot.child("nama").getValue(String.class));
                telp1RS1.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS1.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                sariAsih.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                sariAsih.setMovementMethod(LinkMovementMethod.getInstance());

            });

            info.child(String.valueOf(companyHealth)).child("2").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS2.setText(snapshot.child("nama").getValue(String.class));
                telp1RS2.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS2.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                pondokIndah.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                pondokIndah.setMovementMethod(LinkMovementMethod.getInstance());

            });

            info.child(String.valueOf(companyHealth)).child("3").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS3.setText(snapshot.child("nama").getValue(String.class));
                telp1RS3.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS3.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                harapanKita.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                harapanKita.setMovementMethod(LinkMovementMethod.getInstance());

            });

            info.child(String.valueOf(companyHealth)).child("4").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS4.setText(snapshot.child("nama").getValue(String.class));
                telp1RS4.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS4.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                pluit.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                pluit.setMovementMethod(LinkMovementMethod.getInstance());

            });

        }else if (companyTravel != 0){
            info.child(String.valueOf(companyTravel)).child("1").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS2.setText(snapshot.child("nama").getValue(String.class));
                telp1RS2.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS2.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                pondokIndah.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                pondokIndah.setMovementMethod(LinkMovementMethod.getInstance());

            });

            info.child(String.valueOf(companyTravel)).child("2").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS2.setText(snapshot.child("nama").getValue(String.class));
                telp1RS2.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS2.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                pondokIndah.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                pondokIndah.setMovementMethod(LinkMovementMethod.getInstance());

            });

            info.child(String.valueOf(companyTravel)).child("3").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS3.setText(snapshot.child("nama").getValue(String.class));
                telp1RS3.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS3.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                harapanKita.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                harapanKita.setMovementMethod(LinkMovementMethod.getInstance());

            });

            info.child(String.valueOf(companyTravel)).child("4").get().addOnCompleteListener(task -> {
                DataSnapshot snapshot = task.getResult();

                namaRS4.setText(snapshot.child("nama").getValue(String.class));
                telp1RS4.setText("- " + snapshot.child("nomorTelp1").getValue(String.class));
                telp2RS4.setText("- " + snapshot.child("nomorTelp2").getValue(String.class));
                pluit.setText(Html.fromHtml("<a href=\"" + snapshot.child("linkMap").getValue(String.class) + "\">Klik Disini</a>", Html.FROM_HTML_MODE_LEGACY));
                pluit.setMovementMethod(LinkMovementMethod.getInstance());

            });

        }
    }
}