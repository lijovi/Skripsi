package com.example.skripsi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    LinearLayout namaKeluargaAll, lamaPerjalananAll;
    int selectedID;
    FirebaseDatabase database;
    DatabaseReference reference;
    String JenisPolis, pilihanPlan;
    int perusahaan;
    Spinner plan;
//    LocalTime currentTime;
//    String date;
    Calendar calendar;
    int limit;

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
//        lamaPerjalanan = findViewById(R.id.lamaPerjalanan);
        tipePolis = findViewById(R.id.tipePolis);
        namaAhliWaris = findViewById(R.id.namaAhliWaris);
        hubunganDenganAhliWaris = findViewById(R.id.hubunganDenganAhliWaris);
        negaraTujuan = findViewById(R.id.negaraTujuan);
        tujuanPerjalanan = findViewById(R.id.tujuanPerjalanan);
        jenisPolis = findViewById(R.id.jenisPolis);
        namaKeluarga = findViewById(R.id.namaKeluarga);
        plan = findViewById(R.id.plan);
//        lamaPerjalananAll = findViewById(R.id.lamaPerjalananAll);

        perusahaan = getIntent().getIntExtra("tipePerusahaan",0);

        selectedID = jenisPolis.getCheckedRadioButtonId();
        selectedJenis = findViewById(selectedID);

        tipePolis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedPolis = findViewById(checkedId);
                String temp = selectedPolis.getText().toString();
//                if (Objects.equals(temp, "Iya")){
//                    lamaPerjalananAll.setVisibility(View.GONE);
//                } else {
//                    lamaPerjalananAll.setVisibility(View.VISIBLE);
//                }
            }
        });

        jenisPolis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedJenis = findViewById(checkedId);
                String temp = selectedJenis.getText().toString();
                if (Objects.equals(temp, "Individual")){
                    namaKeluargaAll.setVisibility(View.GONE);
                } else {
                    namaKeluargaAll.setVisibility(View.VISIBLE);
                }
            }
        });

        nik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length()!=12 || text.matches(".*[A-Z].*") || text.matches(".*[a-z].*")){
                    nik.setError("NIK must contains 12 numbers");
                } else {
                    nik.setError(null);
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (!text.contains("@")){
                    email.setError("Please write your email correctly");
                } else {
                    email.setError(null);
                }
            }
        });

        noTelp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length()<10 || text.length()>13 || text.matches(".*[A-Z].*") || text.matches(".*[a-z].*")){
                    noTelp.setError("Please input your phone number correctly");
                } else {
                    noTelp.setError(null);
                }
            }
        });

        nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.matches(".*[0-9].*")){
                    nama.setError("Please input your name correctly");
                } else {
                    nama.setError(null);
                }
            }
        });



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

//        String temp = selectedPolis.getText().toString();

//        currentTime = LocalTime.now();
//        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    }

    private void insertData() {
        int selectedId = jenisKelamin.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
        int selectedIDPolis = tipePolis.getCheckedRadioButtonId();
        selectedPolis = findViewById(selectedIDPolis);
        selectedID = jenisPolis.getCheckedRadioButtonId();
        selectedJenis = findViewById(selectedID);
        String JenisPolis = selectedJenis.getText().toString();
        String NIK = nik.getText().toString();
        String Nama = nama.getText().toString();
        String Email = email.getText().toString();
        String JenisKelamin = selectedGender.getText().toString();
        String NoTelp = noTelp.getText().toString();
        String Alamat = alamat.getText().toString();
        String NamaKeluarga = namaKeluarga.getText().toString();
        String MasaPerjalanan = masaPerjalanan.getText().toString();
//        String LamaPerjalanan = lamaPerjalanan.getText().toString();
        String TipePolis = selectedPolis.getText().toString();
        String NamaAhliWaris = namaAhliWaris.getText().toString();
        String HubunganDenganAhliWaris = hubunganDenganAhliWaris.getText().toString();
        String NegaraTujuan = negaraTujuan.getText().toString();
        String TujuanPerjalanan = tujuanPerjalanan.getText().toString();
        String PlanAsuransi = pilihanPlan.toString();
        int Perusahaan = perusahaan;
        int Limit = limit;
//        String Time = currentTime.toString();
//        String Date = date;
        String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
        String second = String.format("%02d", calendar.get(Calendar.SECOND));

        String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String currenttime = hour + " : " + minute + " : " + second;
        String currentdate = day + " - " + month + " - " + year;

        if (Objects.equals(JenisPolis, "Family")){
            NasabahTravel nasabah = new NasabahTravel(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Travel",
                    Perusahaan, currenttime, currentdate, Limit, JenisPolis, NamaKeluarga, PlanAsuransi, MasaPerjalanan, TipePolis,
                    NamaAhliWaris, HubunganDenganAhliWaris, NegaraTujuan, TujuanPerjalanan);
            reference.child(NIK).setValue(nasabah);
        } else {
            NasabahTravel nasabah = new NasabahTravel(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Travel",
                    Perusahaan, currenttime, currentdate, Limit, JenisPolis, null, PlanAsuransi, MasaPerjalanan, TipePolis,
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
        if (Objects.equals(pilihanPlan, "VIP")){
            limit = 100000000;
        } else if (Objects.equals(pilihanPlan, "Executive")) {
            limit = 70000000;
        } else if (Objects.equals(pilihanPlan, "Deluxe")){
            limit = 50000000;
        } else if (Objects.equals(pilihanPlan, "Superior")) {
            limit = 30000000;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
