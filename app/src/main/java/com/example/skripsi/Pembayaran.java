package com.example.skripsi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Pembayaran extends AppCompatActivity {

    TextView virtualAccount, jumlah, jatuhTempo;
    String nik, nomor, temp;
    int company;
    FirebaseDatabase database;
    DatabaseReference checkVirtual, checkPremi, besarPremi;
    Button back;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pembayaran);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        virtualAccount = findViewById(R.id.virtualAccount);
        jumlah = findViewById(R.id.jumlah);
        jatuhTempo = findViewById(R.id.jatuhTempo);
        back = findViewById(R.id.back);

        company = ClientSession.getInstance().getCompany();
        nik = ClientSession.getInstance().getNik();

        database = FirebaseDatabase.getInstance();
        checkVirtual = database.getReference("company");
//        besarPremi = database.getReference();
        Log.d("INTENT", "COMPANY: " + company);
        Query checkDataVirtual = checkVirtual.orderByChild("companyId").equalTo(company);
        checkDataVirtual.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String virtual = snapshot.child(String.valueOf(company)).child("companyVirtualAccount").getValue(String.class);
                    virtualAccount.setText(virtual);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        checkPremi = database.getReference("transaksi");
        Query checkDataPremi = checkPremi.orderByChild("nik").equalTo(nik);
        checkDataPremi.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String premi = snapshot.child(nik).child("besarPremi").getValue(String.class);
                    String JatuhTempo = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                    jumlah.setText(premi);
                    jatuhTempo.setText(JatuhTempo + " !");
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