package com.example.skripsi;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class RegistrasiTravel extends AppCompatActivity {

    EditText nik, nama, email, noTelp, alamat;
    RadioGroup jenisKelamin;
    RadioButton selectedGender;
    Button btnDaftar, btnMasaPerjalanan;
    TextView masaPerjalanan;

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

        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        noTelp = findViewById(R.id.noTelp);
        alamat = findViewById(R.id.alamat);
        jenisKelamin = findViewById(R.id.jenisKelamin);
        masaPerjalanan = findViewById(R.id.masaPerjalanan);
        btnDaftar = findViewById(R.id.btnDaftar);
        btnMasaPerjalanan = findViewById(R.id.btnMasaPerjalanan);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        btnMasaPerjalanan.setOnClickListener(view-> {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

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


    }

    private void insertData() {
        int selectedId = jenisKelamin.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);
    }
}