package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class HistoryAsuransi extends AppCompatActivity {

    Button btnHome, btnProfile;
    RecyclerView recyclerView;
    ArrayList<NotifikasiCompany> listHistory;
    ArrayList<Nasabah> listNasabah;
    AdapterHistory adapter;
    DatabaseReference reference, referenceNasabah;
    String NIK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_asuransi);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHome = findViewById(R.id.btnHome);
        btnProfile = findViewById(R.id.btnProfile);
        int id = CompanySession.getInstance().getId();

        reference = FirebaseDatabase.getInstance().getReference("history");
        referenceNasabah = FirebaseDatabase.getInstance().getReference("userData");

        recyclerView = findViewById(R.id.rvView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listNasabah = new ArrayList<>();
        listHistory = new ArrayList<>();
        adapter = new AdapterHistory(listHistory, listNasabah);
        recyclerView.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount(); // total direct children
                Log.d("FIREBASE_DEBUG", "Total items fetched: " + count);
                listNasabah.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (Objects.equals(id, dataSnapshot.child("company").getValue(int.class))) {
                        String jenisAsuransi = dataSnapshot.child("jenisAsuransi").getValue(String.class);
                        String time = dataSnapshot.child("time").getValue(String.class);
                        String nik = dataSnapshot.child("nik").getValue(String.class);
                        String nama = dataSnapshot.child("name").getValue(String.class);
                        int company = dataSnapshot.child("company").getValue(int.class);
                        String status = dataSnapshot.child("status").getValue(String.class);

                        Nasabah nasabah = new Nasabah();
                        nasabah.setJenisAsuransi(jenisAsuransi);
                        nasabah.setTime(time);
                        nasabah.setNik(nik);
                        nasabah.setCompany(company);
                        nasabah.setName(nama);

                        listNasabah.add(nasabah);

                        NotifikasiCompany notifikasiCompany = new NotifikasiCompany();
                        notifikasiCompany.setStatus(status);
                        listHistory.add(notifikasiCompany);

                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
    }
}