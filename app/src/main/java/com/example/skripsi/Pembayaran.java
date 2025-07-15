package com.example.skripsi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

public class Pembayaran extends AppCompatActivity {

    TextView virtualAccount, jumlah, jatuhTempo;
    String company, nomor, temp;
    FirebaseDatabase database;
    DatabaseReference check, reference, besarPremi;

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
        company = ClientSession.getInstance().getCompany();

        database = FirebaseDatabase.getInstance();
        check = database.getReference("company");
//        besarPremi = database.getReference();
        Log.d("INTENT", "COMPANY: " + company);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datasnapshot : snapshot.getChildren()){
                    String key = datasnapshot.getKey();
                    if (key != null && key.toLowerCase().contains(company.toLowerCase())){
                        reference = datasnapshot.getRef();

                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                nomor = snapshot.child("virtualAccount").getValue(String.class);
                                Log.d("INTENT", "VIRTUAL ACCOUNT: " + nomor);
                                virtualAccount.setText(nomor);
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