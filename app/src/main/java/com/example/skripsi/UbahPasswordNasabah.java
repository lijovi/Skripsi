package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    Button btnUbah, btnBack;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseHealth = database.getReference("clientHealth");
    DatabaseReference databaseTravel = database.getReference("clientTravel");
    String NIK;
    EditText editText1, editText2;
    TextView jumlahKarakter1, jumlahKarakter2, number1, number2, kapital1, kapital2, symbol1, symbol2;
    int cek1 = 0;
    int cek2 = 0;
    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

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
        btnBack = findViewById(R.id.btnBack);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        jumlahKarakter1 = findViewById(R.id.jumlahkarakter1);
        jumlahKarakter2 = findViewById(R.id.jumlahkarakter2);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        kapital1 = findViewById(R.id.kapital1);
        kapital2 = findViewById(R.id.kapital2);
        symbol1 = findViewById(R.id.symbol1);
        symbol2 = findViewById(R.id.symbol2);

        passwordBaru.setPlaceholderText("Masukkan Password Baru");
        konfirmasiPassword.setPlaceholderText("Masukkan Password Lagi");

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!(text.length() >=8)){
                    jumlahKarakter1.setVisibility(View.VISIBLE);
                    cek1 = 0;
                } else {
                    passwordBaru.setError(null);
                    jumlahKarakter1.setVisibility(View.GONE);
                    cek1 = 1;
                }

                if (!text.matches(".*[0-9].*")){
                    number1.setVisibility(View.VISIBLE);
                    cek1 = 0;
                } else {
                    passwordBaru.setError(null);
                    number1.setVisibility(View.GONE);
                    cek1 = 1;
                }

                if (!text.matches(".*[A-Z].*")){
                    kapital1.setVisibility(View.VISIBLE);
                    cek1 = 0;
                } else {
                    passwordBaru.setError(null);
                    kapital1.setVisibility(View.GONE);
                    cek1 = 1;
                }

                if (!text.matches("^(?=.*[_.!*()$@]).*$")){
                    symbol1.setVisibility(View.VISIBLE);
                    cek1 = 0;
                } else {
                    passwordBaru.setError(null);
                    symbol1.setVisibility(View.GONE);
                    cek1 = 1;
                }
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!(text.length()>=8)){
                    jumlahKarakter2.setVisibility(View.VISIBLE);
                    cek2 = 0;
                } else {
                    konfirmasiPassword.setError(null);
                    jumlahKarakter2.setVisibility(View.GONE);
                    cek2 = 1;
                }

                if (!text.matches(".*[0-9].*")){
                    number2.setVisibility(View.VISIBLE);
                    cek2 = 0;
                } else {
                    konfirmasiPassword.setError(null);
                    number2.setVisibility(View.GONE);
                    cek2 = 1;
                }

                if (!text.matches(".*[A-Z].*")){
                    kapital2.setVisibility(View.VISIBLE);
                    cek2 = 0;
                } else {
                    konfirmasiPassword.setError(null);
                    kapital2.setVisibility(View.GONE);
                    cek2 = 1;
                }

                if (!text.matches("^(?=.*[_.!*()$@]).*$")){
                    symbol2.setVisibility(View.VISIBLE);
                    cek2 = 0;
                } else {
                    konfirmasiPassword.setError(null);
                    symbol2.setVisibility(View.GONE);
                    cek2 = 1;
                }
            }
        });

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean validatePassword(){
        if (cek1 == 1){
            return true;
        } else {
            return false;
        }
    }

    private Boolean validatePasswordKonfirmasi(){
        if (cek2 == 1){
            return true;
        } else {
            return false;
        }
    }
}