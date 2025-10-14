package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomePageAsuransiPembayaran extends AppCompatActivity {

    Button btnProfile, btnHistory, btnHome;
    FirebaseDatabase database;
    DatabaseReference referenceHealth, referenceTravel, referenceNamaHealth, referenceNamaTravel;
    ArrayList<BuktiBayar> listPembayaran;
    AdapterPembayaran adapter;
    RecyclerView recyclerView;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView pendaftaran;
    LinearLayout pilihan;
    String nik, nama, nomorPolis, time, tanggal;
    int besarPremi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_home_page_asuransi_pembayaran);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        btnProfile = findViewById(R.id.btnProfile);
        btnHistory = findViewById(R.id.btnHistory);
        database = FirebaseDatabase.getInstance();
        referenceHealth = database.getReference("transaksiHealth");
        referenceTravel = database.getReference("transaksiTravel");
        referenceNamaHealth = database.getReference("clientHealth");
        referenceNamaTravel = database.getReference("clientTravel");

        btnHome = findViewById(R.id.btnHome);
        pilihan = findViewById(R.id.pilihan);
        pendaftaran = findViewById(R.id.pendaftaran);

        recyclerView = findViewById(R.id.rvView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listPembayaran = new ArrayList<>();
        adapter = new AdapterPembayaran(listPembayaran);
        recyclerView.setAdapter(adapter);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileAsuransi.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryAsuransi.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pilihan.getVisibility() == View.VISIBLE){
                    pilihan.setVisibility(View.GONE);
                } else if (pilihan.getVisibility() == View.GONE) {
                    pilihan.setVisibility(View.VISIBLE);
                }
            }
        });

        pendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                startActivity(intent);
            }
        });

        referenceHealth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (Objects.equals("Not Approve", dataSnapshot.child("check").getValue(String.class))){
                        nik = dataSnapshot.child("nik").getValue(String.class);
                        besarPremi = dataSnapshot.child("besarPremi").getValue(int.class);
                        nomorPolis = dataSnapshot.child("nomorPolisKesehatan").getValue(String.class);
                        time = dataSnapshot.child("time").getValue(String.class);
                        tanggal = dataSnapshot.child("date").getValue(String.class);

                        referenceNamaHealth.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                nama = snapshot.child("name").getValue(String.class);
                                BuktiBayar buktiBayar = new BuktiBayar();
                                buktiBayar.setNik(nik);
                                buktiBayar.setNama(nama);
                                buktiBayar.setBesarPremi(String.valueOf(besarPremi));
                                buktiBayar.setNomorPolis(nomorPolis);
                                buktiBayar.setTime(time);
                                buktiBayar.setTanggal(tanggal);
                                listPembayaran.add(buktiBayar);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referenceTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (Objects.equals("Not Approve", dataSnapshot.child("check").getValue(String.class))){
                        nik = dataSnapshot.child("nik").getValue(String.class);
                        besarPremi = dataSnapshot.child("besarPremi").getValue(int.class);
                        nomorPolis = dataSnapshot.child("nomorPolisTravel").getValue(String.class);
                        time = dataSnapshot.child("time").getValue(String.class);
                        tanggal = dataSnapshot.child("date").getValue(String.class);

                        referenceNamaTravel.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                nama = snapshot.child("name").getValue(String.class);
                                BuktiBayar buktiBayar = new BuktiBayar();
                                buktiBayar.setNik(nik);
                                buktiBayar.setNama(nama);
                                buktiBayar.setBesarPremi(String.valueOf(besarPremi));
                                buktiBayar.setNomorPolis(nomorPolis);
                                buktiBayar.setTime(time);
                                buktiBayar.setTanggal(tanggal);
                                listPembayaran.add(buktiBayar);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}