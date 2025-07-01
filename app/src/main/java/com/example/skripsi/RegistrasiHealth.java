package com.example.skripsi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrasiHealth extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nik, nama, email, bodText, noTelp, alamat, pekerjaan, periodePertanggungan, namaAhliWaris, hubunganDenganAhliWaris;
    Button btnDaftar, bod, plus;
    RadioGroup jenisKelamin;
    RadioButton selectedGender;
    DatabaseReference reference, referenceNotif;
    FirebaseDatabase database;
    Spinner plan;
    String pilihanPlan;
    TextView riwayatPenyakit;
    int perusahaan;

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

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("clientSementara");

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
        plan = findViewById(R.id.plan);
        plus = findViewById(R.id.plus);
        riwayatPenyakit = findViewById(R.id.riwayatPenyakit);

        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("riwayat");
//        StringBuilder text = new StringBuilder();
//        int size = list.size();
//        for (int i = 0; i<size; i++){
//            riwayatPenyakit.setText(list.get(i));
//        }

        riwayatPenyakit.setText(String.valueOf(list));


//        perusahaan = Integer.parseInt(getIntent().getStringExtra("tipePerusahaan"));
        perusahaan = getIntent().getIntExtra("tipePerusahaan", 0);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planHealth, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plan.setAdapter(adapter);
        plan.setOnItemSelectedListener(this);

//        dataref = FirebaseDatabase.getInstance().getReference().child("nasabah");

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertdata();
//                Intent masuk = new Intent(v.getContext(), Login.class);
//                v.getContext().startActivity(masuk);
                Intent masuk = new Intent(getApplicationContext(), Login.class);
                startActivity(masuk);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopUpRiwayatPenyakit.class);
                startActivity(intent);
            }
        });
    }
    private void insertdata(){
        int selectedId = jenisKelamin.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
        String NIK = nik.getText().toString();
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
        int Perusahaan = perusahaan;
        String PilihanPlan = pilihanPlan.toString();


        NasabahHealth nasabah = new NasabahHealth(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Health",
                Perusahaan, BodText, Pekerjaan, PeriodePertanggungan, PilihanPlan, NamaAhliWaris, HubunganDenganAhliWaris);
//        dataref.push().setValue(nasabah);
        reference.child(NIK).setValue(nasabah);
        Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        pilihanPlan = choice;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}