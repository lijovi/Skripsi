package com.example.skripsi;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Objects;

public class RiwayatPembayaran extends AppCompatActivity {

    ArrayList<DataPembayaran> listPembayaran;
    AdapterRiwayatPembayaran adapter;
    RecyclerView recyclerView;
    DatabaseReference reference, refHealth, refTravel;
    FirebaseDatabase database;
    String nama, besarPremi, tanggal;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_riwayat_pembayaran);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.rvView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listPembayaran = new ArrayList<>();
        adapter = new AdapterRiwayatPembayaran(listPembayaran);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("pembayaran");
        refHealth = database.getReference("transaksiHealth");
        refTravel = database.getReference("transaksiTravel");
        back = findViewById(R.id.back);

        String NIK = ClientSession.getInstance().getNik();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.child(NIK).getChildren()){
                    final String nama = data.child("nama").getValue(String.class);
                    final String besarPremi = data.child("besarPremi").getValue(String.class);
                    final String tanggal = data.child("date").getValue(String.class);
                    final String noPremi = data.child("nomorPremi").getValue(String.class);

                    DataPembayaran dataPembayaran = new DataPembayaran();
                    dataPembayaran.setNama(nama);
                    dataPembayaran.setBesarPremi(besarPremi);
                    dataPembayaran.setDate(tanggal);

                    refHealth.child(NIK).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (Objects.equals(noPremi, snapshot.child("nomorPolisKesehatan").getValue(String.class))){
                                if (snapshot.hasChild("check")){
                                    if (Objects.equals(snapshot.child("check").getValue(String.class), "Approve")){
                                        dataPembayaran.setTime("Success");
                                    } else {
                                        dataPembayaran.setTime("Pending");
                                    }
                                }
                            }

                            refTravel.child(NIK).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (Objects.equals(noPremi, snapshot.child("nomorPolisTravel").getValue(String.class))){
                                        if (snapshot.hasChild("check")){
                                            if (Objects.equals(snapshot.child("check").getValue(String.class), "Approve")){
                                                dataPembayaran.setTime("Success");
                                            } else {
                                                dataPembayaran.setTime("Pending");
                                            }
                                        }
                                    }
                                    listPembayaran.add(dataPembayaran);
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}