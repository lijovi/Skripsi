package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UbahPasswordNasabah extends AppCompatActivity {

    TextInputLayout passwordBaru, konfirmasiPassword;
    Button btnUbah;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseHealth = database.getReference("client");
    DatabaseReference databaseTravel = database.getReference("client");
    String NIK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ubah_password_nasabah);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NIK = ClientSession.getInstance().getNik();

        Query checkDataHealth = databaseHealth.orderByChild("nik").equalTo(NIK);

        Query checkDataTravel = databaseTravel.orderByChild("nik").equalTo(NIK);

        passwordBaru = findViewById(R.id.passwordBaru);
        konfirmasiPassword = findViewById(R.id.konfirmasiPassword);
        btnUbah = findViewById(R.id.btnUbah);

        passwordBaru.setPlaceholderText("Masukkan Password Baru");
        konfirmasiPassword.setPlaceholderText("Masukkan Password Lagi");

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekPassword = passwordBaru.getEditText().getText().toString();
                String cekPasswordKonfirmasi = konfirmasiPassword.getEditText().getText().toString();
                if (!validatePassword() || !validatePasswordKonfirmasi()){

                } else {
                    if (Objects.equals(cekPassword, cekPasswordKonfirmasi)){
                        checkDataHealth.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
//                                    String Password = snapshot.child(NIK).child("password").getValue(String.class);
                                    databaseHealth.child(NIK).child("password").setValue(cekPassword);
                                    Toast.makeText(getApplicationContext(), "Password Berhasil Diubah!", Toast.LENGTH_SHORT).show();
                                    ClientSession.getInstance().setPassword(cekPassword);
                                    Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        checkDataTravel.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
//                                    String Password = snapshot.child(NIK).child("password").getValue(String.class);
                                    databaseTravel.child(NIK).child("password").setValue(cekPassword);
                                    Toast.makeText(getApplicationContext(), "Password Berhasil Diubah!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);
                                    ClientSession.getInstance().setPassword(cekPassword);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        konfirmasiPassword.setError("Password berbeda");
                    }
                }
            }
        });

    }

    private Boolean validatePassword(){
        String cekPassword = passwordBaru.getEditText().getText().toString();
        int cek = 0;

        if (cekPassword.isEmpty()){
            passwordBaru.setError("Password Cannot Be Empty");
            return false;
        } else {

            if (cekPassword.length()>=8){
                cek = cek + 1;
            } else {
                passwordBaru.setError("Must be more than 8 characters");
                return false;
            }

            if (cekPassword.matches(".*[0-9].*")){
                cek = cek + 1;
            } else {
                passwordBaru.setError("Must contain number");
                return false;
            }

            if (cekPassword.matches(".*[A-Z].*")){
                cek = cek + 1;
            } else {
                passwordBaru.setError("Must contain capital letter");
                return false;
            }

            if (cekPassword.matches("^(?=.*[_.!*()$@]).*$")){
                cek = cek + 1;
            } else {
                passwordBaru.setError("Must contain symbol");
                return false;
            }

            passwordBaru.setError(null);
            return true;

//            if (cek == 4){
//
//            } else {
//                return false;
//            }

        }
    }

    private Boolean validatePasswordKonfirmasi(){
        String cekPasswordKonfirmasi = konfirmasiPassword.getEditText().getText().toString();
        int cek = 0;

        if (cekPasswordKonfirmasi.isEmpty()){
            konfirmasiPassword.setError("Password Cannot Be Empty");
            return false;
        } else {
            if (cekPasswordKonfirmasi.length()>=8){
                cek = cek + 1;
            } else {
                konfirmasiPassword.setError("Must be more than 8 characters");
                return false;
            }

            if (cekPasswordKonfirmasi.matches(".*[0-9].*")){
                cek = cek + 1;
            } else {
                konfirmasiPassword.setError("Must contain number");
                return false;
            }

            if (cekPasswordKonfirmasi.matches(".*[A-Z].*")){
                cek = cek + 1;
            } else {
                konfirmasiPassword.setError("Must contain capital letter");
                return false;
            }

            if (cekPasswordKonfirmasi.matches("^(?=.*[_.!*()$@]).*$")){
                cek = cek + 1;
            } else {
                konfirmasiPassword.setError("Must contain symbol");
                return false;
            }

            konfirmasiPassword.setError(null);
            return true;

//            if (cek == 4){
//
//            } else {
//                return false;
//            }
        }
    }
}