package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class HomePageAsuransi extends AppCompatActivity {

    Button btnProfile;
    FirebaseDatabase database;
    DatabaseReference reference, notif;
    ArrayList<Nasabah> listNasabah;
    Adapter adapter;
    RecyclerView recyclerView;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_home_page_asuransi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String company = CompanySession.getInstance().getNama();
        int id = CompanySession.getInstance().getId();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("clientSementara");
        btnProfile = findViewById(R.id.btnProfile);

        recyclerView = findViewById(R.id.rvView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listNasabah = new ArrayList<>();
        adapter = new Adapter(listNasabah);
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount(); // total direct children
                Log.d("FIREBASE_DEBUG", "Total items fetched: " + count);
                listNasabah.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (Objects.equals(id, dataSnapshot.child("company").getValue(int.class))) {
                        String nama = dataSnapshot.child("name").getValue(String.class);
                        String jenisAsuransi = dataSnapshot.child("jenisAsuransi").getValue(String.class);
                        String time = dataSnapshot.child("time").getValue(String.class);
                        String nik = dataSnapshot.child("nik").getValue(String.class);
                        int company = dataSnapshot.child("company").getValue(int.class);

                        Nasabah nasabah = new Nasabah();
                        nasabah.setName(nama);
                        nasabah.setJenisAsuransi(jenisAsuransi);
                        nasabah.setTime(time);
                        nasabah.setNik(nik);
                        nasabah.setCompany(company);
                        listNasabah.add(nasabah);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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