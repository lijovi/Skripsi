package com.example.skripsi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegistrasiHealth extends AppCompatActivity {

    EditText nik, nama, email, bodText, noTelp, alamat, pekerjaan, periodePertanggungan, namaAhliWaris, hubunganDenganAhliWaris;
    Button btnDaftar, bod;
    RadioGroup jenisKelamin;
    RadioButton selectedGender;
    DatabaseReference dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_registrasi_health);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        bodText = findViewById(R.id.bodtext);
        noTelp = findViewById(R.id.noTelp);
        alamat = findViewById(R.id.alamat);
        pekerjaan = findViewById(R.id.pekerjaan);
        periodePertanggungan = findViewById(R.id.periodePertanggungan);
        namaAhliWaris = findViewById(R.id.namaAhliWaris);
        hubunganDenganAhliWaris = findViewById(R.id.hubunganDenganAhliWaris);
        btnDaftar = findViewById(R.id.btnDaftar);
        bod = findViewById(R.id.bod);
        jenisKelamin = findViewById(R.id.jenisKelamin);

        bod.setOnClickListener(view-> {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrasiHealth.this,
                            (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                                String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                bodText.setText(selectedDate);
                            }, year, month, day);

                    datePickerDialog.show();
                }
        );

        dataref = FirebaseDatabase.getInstance().getReference().child("nasabah");

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
                Intent masuk = new Intent(v.getContext(), Login.class);
                v.getContext().startActivity(masuk);
            }
        });
    }
    private void insertdata(){
        int selectedId = jenisKelamin.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
        int NIK = nik.getText().length();
        String Nama = nama.getText().toString();
        String Email = email.getText().toString();
        String BodText = bodText.getText().toString();
        String NoTelp = noTelp.getText().toString();
        String Alamat = alamat.getText().toString();
        String Pekerjaan = pekerjaan.getText().toString();
        String PeriodePertanggungan = periodePertanggungan.getText().toString();
        String NamaAhliWaris = namaAhliWaris.getText().toString();
        String HubunganDenganAhliWaris = hubunganDenganAhliWaris.getText().toString();
        String JenisKelamin = selectedGender.getText().toString();


        NasabahHealth nasabah = new NasabahHealth(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0",
                BodText, Pekerjaan, PeriodePertanggungan, NamaAhliWaris, HubunganDenganAhliWaris);
        dataref.push().setValue(nasabah);
        Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
    }
}