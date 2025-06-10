package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    EditText nik, nama;
    Button btnMasuk;
    TextView daftar, masukAsuransi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        btnMasuk = findViewById(R.id.btnMasuk);
        daftar = findViewById(R.id.daftar);
        masukAsuransi = findViewById(R.id.masukAsuransi);


        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (nik.getText().toString().equals())
            }
        });
        masukAsuransi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, LoginAsuransi.class);
                startActivity(intent);
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }



//    CEK NIK
    private Boolean validateNIK(){
        String Nik = nik.getText().toString();

        if (Nik.isEmpty()){
            nik.setError("NIK Cannot Be Empty");
            return false;
        } else {
            nik.setError(null);
            return true;
        }
    }

//    CEK NAMA
    private Boolean validateNama(){
        String Nama = nama.getText().toString();

        if (Nama.isEmpty()){
            nama.setError("NIK Cannot Be Empty");
            return false;
        } else {
            nama.setError(null);
            return true;
        }
    }

    private void cek(){
        String NIK = nik.getText().toString().trim();
        String Nama = nama.getText().toString().trim();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("nasabah");
        Query checkData = database.orderByChild("nik").equalTo(NIK);

        checkData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    nik.setError(null);
                    String NamaFromDB = snapshot.child(NIK).child("nama").getValue(String.class);

                    if (!Objects.equals(NamaFromDB, Nama)){
                        nik.setError(null);
//                        Intent intent = new Intent();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}