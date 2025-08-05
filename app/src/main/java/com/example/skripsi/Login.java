package com.example.skripsi;

import android.content.Context;
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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextInputLayout nik,nama;
    Button btnMasuk;
    TextView daftar, masukAsuransi;
    int flag1, flag2;
    int check1, check2;
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

        nik.setPlaceholderText("Input NIK");
        nama.setPlaceholderText("Input Nama");


        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateNIK() | !validateNama()){

                } else {
                    cek();
                    if (flag1 == 1 && flag2 == 1){
                        nik.setError("User does not exist");
                    }
                }
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
        String Nik = nik.getEditText().getText().toString();

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
        String Nama = nama.getEditText().getText().toString();

        if (Nama.isEmpty()){
            nama.setError("NIK Cannot Be Empty");
            return false;
        } else {
            nama.setError(null);
            return true;
        }
    }

    private void cek(){
        String NIK = nik.getEditText().getText().toString().trim();
        String Nama = nama.getEditText().getText().toString().trim();

        DatabaseReference databaseHealth = FirebaseDatabase.getInstance().getReference("clientHealth");
        Query checkDataHealth = databaseHealth.orderByChild("nik").equalTo(NIK);

        DatabaseReference databaseTravel = FirebaseDatabase.getInstance().getReference("clientTravel");
        Query checkDataTravel = databaseTravel.orderByChild("nik").equalTo(NIK);

        checkDataHealth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    nik.setError(null);
                    String NameFromDB = snapshot.child(NIK).child("name").getValue(String.class);
                    String Email = snapshot.child(NIK).child("email").getValue(String.class);
                    String Name = snapshot.child(NIK).child("name").getValue(String.class);
                    String NoTelp = snapshot.child(NIK).child("phoneNumber").getValue(String.class);
                    String Gender = snapshot.child(NIK).child("gender").getValue(String.class);
                    String Password = snapshot.child(NIK).child("password").getValue(String.class);
                    int Company = snapshot.child(NIK).child("company").getValue(int.class);
                    int Limit = snapshot.child(NIK).child("limit").getValue(int.class);
                    if (Objects.equals(NameFromDB, Nama)){
                        nik.setError(null);
                        Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);

                        ClientSession.getInstance().setNama(Name);
                        ClientSession.getInstance().setEmail(Email);
                        ClientSession.getInstance().setNoTelp(NoTelp);
                        ClientSession.getInstance().setNik(NIK);
                        ClientSession.getInstance().setGender(Gender);
                        ClientSession.getInstance().setCompany(Company);
                        ClientSession.getInstance().setLimitHealth(Limit);
                        if (!Objects.equals(Password, "0")){
                            ClientSession.getInstance().setPassword(Password);
                        }


                        startActivity(intent);
                    } else {
                        nama.setError("Incorrect Nama");
                        nama.requestFocus();
                    }
                } else {
//                    nik.setError("User does not exist");
                    flag1=1;
                    nik.requestFocus();
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
                    nik.setError(null);
                    String NameFromDB = snapshot.child(NIK).child("name").getValue(String.class);
                    String Email = snapshot.child(NIK).child("email").getValue(String.class);
                    String Name = snapshot.child(NIK).child("name").getValue(String.class);
                    String NoTelp = snapshot.child(NIK).child("phoneNumber").getValue(String.class);
                    String Gender = snapshot.child(NIK).child("gender").getValue(String.class);
                    String Password = snapshot.child(NIK).child("password").getValue(String.class);
                    int Company = snapshot.child(NIK).child("company").getValue(int.class);
                    int Limit = snapshot.child(NIK).child("limit").getValue(int.class);
                    if (Objects.equals(NameFromDB, Nama)){
                        nik.setError(null);
                        Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);

                        ClientSession.getInstance().setNama(Name);
                        ClientSession.getInstance().setEmail(Email);
                        ClientSession.getInstance().setNoTelp(NoTelp);
                        ClientSession.getInstance().setNik(NIK);
                        ClientSession.getInstance().setGender(Gender);
                        ClientSession.getInstance().setCompany(Company);
                        ClientSession.getInstance().setLimitTravel(Limit);
                        if (!Objects.equals(Password, "0")){
                            ClientSession.getInstance().setPassword(Password);
                        }

                        startActivity(intent);
                    } else {
                        nama.setError("Incorrect Nama");
                        nama.requestFocus();
                    }
                } else {
//                    nik.setError("User does not exist");
                    flag2=1;
                    nik.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}