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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.SimpleFormatter;

public class RegistrasiTravel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nik, nama, email, noTelp, alamat, lamaPerjalanan, namaKeluarga, namaAhliWaris, hubunganDenganAhliWaris, negaraTujuan, tujuanPerjalanan;
    RadioGroup jenisKelamin, jenisPolis, tipePolis;
    RadioButton selectedGender, selectedJenis, selectedPolis;
    Button btnDaftar, btnMasaPerjalanan;
    TextView masaPerjalanan;
    LinearLayout namaKeluargaAll;
    int selectedID;
    FirebaseDatabase database;
    DatabaseReference reference;
    String JenisPolis, pilihanPlan, perusahaan;
    Spinner plan;
//    LocalTime currentTime;
//    String date;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_registrasi_travel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("clientSementara");
        calendar = Calendar.getInstance();

        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        noTelp = findViewById(R.id.noTelp);
        alamat = findViewById(R.id.alamat);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        masaPerjalanan = findViewById(R.id.masaPerjalanan);
        btnDaftar = findViewById(R.id.btnDaftar);
        btnMasaPerjalanan = findViewById(R.id.btnMasaPerjalanan);
        namaKeluargaAll = findViewById(R.id.namaKeluargaAll);
        lamaPerjalanan = findViewById(R.id.lamaPerjalanan);
        tipePolis = findViewById(R.id.tipePolis);
        namaAhliWaris = findViewById(R.id.namaAhliWaris);
        hubunganDenganAhliWaris = findViewById(R.id.hubunganDenganAhliWaris);
        negaraTujuan = findViewById(R.id.negaraTujuan);
        tujuanPerjalanan = findViewById(R.id.tujuanPerjalanan);
        jenisPolis = findViewById(R.id.jenisPolis);
        namaKeluarga = findViewById(R.id.namaKeluarga);
        plan = findViewById(R.id.plan);
//
        perusahaan = getIntent().getStringExtra("tipePerusahaan");
//
        selectedID = jenisPolis.getCheckedRadioButtonId();
        selectedJenis = findViewById(selectedID);
//        JenisPolis = selectedJenis.getText().toString();
//
//
//        if (selectedJenis.equals(R.id.family)){
//            namaKeluargaAll.setVisibility(View.VISIBLE);
//        } else {
//            namaKeluargaAll.setVisibility(View.GONE);
//        }
//
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                Intent masuk = new Intent(getApplicationContext(), Login.class);
                startActivity(masuk);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planTravel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plan.setAdapter(adapter);
        plan.setOnItemSelectedListener(this);


//
        btnMasaPerjalanan.setOnClickListener(view-> {
//                    final Calendar calendar = Calendar.getInstance();
//                    int year = calendar.get(Calendar.YEAR);
//                    int month = calendar.get(Calendar.MONTH);
//                    int day = calendar.get(Calendar.DAY_OF_MONTH);

            MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
            )).build();

            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                @Override
                public void onPositiveButtonClick(Pair<Long, Long> selection) {
                    String date1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection.first));
                    String date2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection.second));
                    masaPerjalanan.setText(date1 + " - " + date2);
                }
            });

            materialDatePicker.show(getSupportFragmentManager(), "tag");
                }
        );

//        currentTime = LocalTime.now();
//        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    }

    private void insertData() {
        int selectedId = jenisKelamin.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
        int selectedIDPolis = tipePolis.getCheckedRadioButtonId();
        selectedPolis = findViewById(selectedIDPolis);
        String NIK = nik.getText().toString();
        String Nama = nama.getText().toString();
        String Email = email.getText().toString();
        String JenisKelamin = selectedGender.getText().toString();
        String NoTelp = noTelp.getText().toString();
        String Alamat = alamat.getText().toString();
        String NamaKeluarga = namaKeluarga.getText().toString();
        String MasaPerjalanan = masaPerjalanan.getText().toString();
        String LamaPerjalanan = lamaPerjalanan.getText().toString();
        String TipePolis = selectedPolis.getText().toString();
        String NamaAhliWaris = namaAhliWaris.getText().toString();
        String HubunganDenganAhliWaris = hubunganDenganAhliWaris.getText().toString();
        String NegaraTujuan = negaraTujuan.getText().toString();
        String TujuanPerjalanan = tujuanPerjalanan.getText().toString();
        String PlanAsuransi = pilihanPlan.toString();
        String Perusahaan = perusahaan;
//        String Time = currentTime.toString();
//        String Date = date;
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));

        String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String currenttime = hour + ":" + minute + ":" + second;
        String currentdate = day + "-" + month + "-" + year;

        if (Objects.equals(JenisPolis, "Family")){
            NasabahTravel nasabah = new NasabahTravel(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Travel",
                    Perusahaan, currenttime, currentdate, JenisPolis, NamaKeluarga, PlanAsuransi, MasaPerjalanan, LamaPerjalanan, TipePolis,
                    NamaAhliWaris, HubunganDenganAhliWaris, NegaraTujuan, TujuanPerjalanan);
            reference.child(NIK).setValue(nasabah);
        } else {
            NasabahTravel nasabah = new NasabahTravel(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Travel",
                    Perusahaan, currenttime, currentdate, JenisPolis, null, PlanAsuransi, MasaPerjalanan, LamaPerjalanan, TipePolis,
                    NamaAhliWaris, HubunganDenganAhliWaris, NegaraTujuan, TujuanPerjalanan);
            reference.child(NIK).setValue(nasabah);
        }
//        dataref.push().setValue(nasabah);

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
