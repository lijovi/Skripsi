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

import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Pattern;
import android.content.Context;

public class BuatPasswordNasabah extends AppCompatActivity {

    TextInputLayout passwordBaru, konfirmasiPassword;
    Button btnBuat, btnBack;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseHealth = database.getReference("clientHealth");
    DatabaseReference databaseTravel = database.getReference("clientTravel");
    DatabaseReference referenceNotifikasi = FirebaseDatabase.getInstance().getReference("notifikasiNasabah");
    String NIK;
    EditText editText1, editText2;
    TextView jumlahKarakter1, jumlahKarakter2, number1, number2, kapital1, kapital2, symbol1, symbol2;
    Calendar calendar;
    int cek1 = 0;
    int cek2 = 0;
    Nasabah nasabah = new Nasabah();

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
        setContentView(R.layout.activity_buat_password_nasabah);
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
        btnBuat = findViewById(R.id.btnBuat);
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

        calendar = Calendar.getInstance();

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

        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekPassword = passwordBaru.getEditText().getText().toString();
                String cekPasswordKonfirmasi = konfirmasiPassword.getEditText().getText().toString();
                if (!validatePassword() || !validatePasswordKonfirmasi()){

                } else {
                    if (Objects.equals(cekPassword, cekPasswordKonfirmasi)){
                        nasabah.CreatePassword(NIK, cekPassword);
                        Toast.makeText(getApplicationContext(), "Password Sudah Dibuat", Toast.LENGTH_SHORT).show();
                        ClientSession.getInstance().setPassword(cekPassword);
                        Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);
                        startActivity(intent);


                        referenceNotifikasi.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
                                String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                                String year = String.valueOf(calendar.get(Calendar.YEAR));

                                String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                                String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
                                String second = String.format("%02d",calendar.get(Calendar.SECOND));

                                String currentdate = day + " - " + month + " - " + year;
                                String currenttime = hour + " : " + minute + " : " + second;
                                NotifikasiModel notifikasiModel = new NotifikasiModel("Berhasil", currenttime, currentdate, "Buat Password", null);
                                notifikasiModel.NewNotification(notifikasiModel, NIK);
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