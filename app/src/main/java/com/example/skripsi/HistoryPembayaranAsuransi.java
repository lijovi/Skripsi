package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

public class HistoryPembayaranAsuransi extends AppCompatActivity {

    TextView pendaftaran, pembayaran;
    Button btnHome, btnProfile;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    ArrayList<BuktiBayar> listPembayaran;
    AdapterPembayaranHistory adapter;
    DatabaseReference referenceHealth, referenceTravel, referenceNamaHealth, referenceNamaTravel;
//    String nik, nama, nomorPolis, time, tanggal;
//    int besarPremi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_history_pembayaran_asuransi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int id = CompanySession.getInstance().getId();

        pendaftaran = findViewById(R.id.pendaftaran);
        pembayaran = findViewById(R.id.pembayaran);
        btnHome = findViewById(R.id.btnHome);
        btnProfile = findViewById(R.id.btnProfile);
        recyclerView = findViewById(R.id.rvView);

        database = FirebaseDatabase.getInstance();
        referenceHealth = database.getReference("transaksiHealth");
        referenceTravel = database.getReference("transaksiTravel");
        referenceNamaHealth = database.getReference("clientHealth");
        referenceNamaTravel = database.getReference("clientTravel");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listPembayaran = new ArrayList<>();

        adapter = new AdapterPembayaranHistory(listPembayaran);
        recyclerView.setAdapter(adapter);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileAsuransi.class);
                startActivity(intent);
            }
        });

        pendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryAsuransi.class);
                startActivity(intent);
            }
        });

        referenceHealth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (Objects.equals("Approve", dataSnapshot.child("check").getValue(String.class)) && Objects.equals(id, dataSnapshot.child("company").getValue(int.class))){
                        final String nik = dataSnapshot.child("nik").getValue(String.class);
                        final int besarPremi = dataSnapshot.child("besarPremi").getValue(int.class);
                        final String nomorPolis = dataSnapshot.child("nomorPolisKesehatan").getValue(String.class);
                        final String time = dataSnapshot.child("time").getValue(String.class);
                        final String tanggal = dataSnapshot.child("date").getValue(String.class);

                        referenceNamaHealth.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String nama = snapshot.child("name").getValue(String.class);
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
                    if (Objects.equals("Approve", dataSnapshot.child("check").getValue(String.class)) && Objects.equals(id, dataSnapshot.child("company").getValue(int.class))){
                        final String nik = dataSnapshot.child("nik").getValue(String.class);
                        final int besarPremi = dataSnapshot.child("besarPremi").getValue(int.class);
                        final String nomorPolis = dataSnapshot.child("nomorPolisTravel").getValue(String.class);
                        final String time = dataSnapshot.child("time").getValue(String.class);
                        final String tanggal = dataSnapshot.child("date").getValue(String.class);

                        referenceNamaTravel.child(nik).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String nama = snapshot.child("name").getValue(String.class);
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