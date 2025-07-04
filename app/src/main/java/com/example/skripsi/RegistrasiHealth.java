package com.example.skripsi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    String perusahaan;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
    ArrayList<String> list = new ArrayList<>();
//    ArrayList<String> List;
    Button btnSimpan;
//    LocalTime currentTime;
//    String date;
    Calendar calendar;


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
        calendar = Calendar.getInstance();

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

//        StringBuilder text = new StringBuilder();
//        int size = list.size();
//        for (int i = 0; i<size; i++){
//            riwayatPenyakit.setText(list.get(i));
//        }


//        perusahaan = Integer.parseInt(getIntent().getStringExtra("tipePerusahaan"));
        perusahaan = getIntent().getStringExtra("tipePerusahaan");

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
//                Intent intent = new Intent(getApplicationContext(), PopUpRiwayatPenyakit.class);
//                startActivity(intent);
                DialogForm();
            }
        });

        if (riwayatPenyakit == null){
            riwayatPenyakit.setText("");
        }
//        currentTime = LocalTime.now();
//        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(RegistrasiHealth.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_pop_up_riwayat_penyakit, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        c1 = dialogView.findViewById(R.id.c1);
        c2 = dialogView.findViewById(R.id.c2);
        c3 = dialogView.findViewById(R.id.c3);
        c4 = dialogView.findViewById(R.id.c4);
        c5 = dialogView.findViewById(R.id.c5);
        c6 = dialogView.findViewById(R.id.c6);
        c7 = dialogView.findViewById(R.id.c7);
        c8 = dialogView.findViewById(R.id.c8);
        c9 = dialogView.findViewById(R.id.c9);
        c10 = dialogView.findViewById(R.id.c10);
        c11 = dialogView.findViewById(R.id.c11);
        c12 = dialogView.findViewById(R.id.c12);
        c13 = dialogView.findViewById(R.id.c13);
        c14 = dialogView.findViewById(R.id.c14);
        c15 = dialogView.findViewById(R.id.c15);
        c16 = dialogView.findViewById(R.id.c16);
        c17 = dialogView.findViewById(R.id.c17);
        c18 = dialogView.findViewById(R.id.c18);

        c1.setChecked(list.contains(c1.getText().toString()));
        c2.setChecked(list.contains(c2.getText().toString()));
        c3.setChecked(list.contains(c3.getText().toString()));
        c4.setChecked(list.contains(c4.getText().toString()));
        c5.setChecked(list.contains(c5.getText().toString()));
        c6.setChecked(list.contains(c6.getText().toString()));
        c7.setChecked(list.contains(c7.getText().toString()));
        c8.setChecked(list.contains(c8.getText().toString()));
        c9.setChecked(list.contains(c9.getText().toString()));
        c10.setChecked(list.contains(c10.getText().toString()));
        c11.setChecked(list.contains(c11.getText().toString()));
        c12.setChecked(list.contains(c12.getText().toString()));
        c13.setChecked(list.contains(c13.getText().toString()));
        c14.setChecked(list.contains(c14.getText().toString()));
        c15.setChecked(list.contains(c15.getText().toString()));
        c16.setChecked(list.contains(c16.getText().toString()));
        c17.setChecked(list.contains(c17.getText().toString()));
        c18.setChecked(list.contains(c18.getText().toString()));

        btnSimpan = dialogView.findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RegistrasiHealth.class);
//                intent.putExtra("riwayat", list);
                riwayatPenyakit.setText(String.valueOf(list));
                alertDialog.dismiss();
            }
        });

//        List = (ArrayList<String>) getIntent().getSerializableExtra("riwayat");

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
        String Perusahaan = perusahaan;
        String PilihanPlan = pilihanPlan.toString();
        ArrayList<String> List = list;

        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        String day = String.format("%02d" ,calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String currenttime = hour + ":" + minute + ":" + second;
        String currentdate = day + "-" + month + "-" + year;

        NasabahHealth nasabah = new NasabahHealth(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Health",
                Perusahaan, currenttime, currentdate, BodText, Pekerjaan, PeriodePertanggungan, PilihanPlan, NamaAhliWaris, HubunganDenganAhliWaris, List);
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

    public void checked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if (view.getId() == R.id.c1){
            if (checked){
                list.add(c1.getText().toString());
            } else {
                list.remove(c1.getText().toString());
            }
        } else if (view.getId() == R.id.c2) {
            if (checked){
                list.add(c2.getText().toString());
            } else {
                list.remove(c2.getText().toString());
            }

        } else if (view.getId() == R.id.c3) {
            if (checked){
                list.add(c3.getText().toString());
            } else {
                list.remove(c3.getText().toString());
            }

        } else if (view.getId() == R.id.c4) {
            if (checked){
                list.add(c4.getText().toString());
            } else {
                list.remove(c4.getText().toString());
            }

        } else if (view.getId() == R.id.c5) {
            if (checked){
                list.add(c5.getText().toString());
            } else {
                list.remove(c5.getText().toString());
            }

        } else if (view.getId() == R.id.c6) {
            if (checked){
                list.add(c6.getText().toString());
            } else {
                list.remove(c6.getText().toString());
            }

        } else if (view.getId() == R.id.c7) {
            if (checked){
                list.add(c7.getText().toString());
            } else {
                list.remove(c7.getText().toString());
            }

        } else if (view.getId() == R.id.c8) {
            if (checked){
                list.add(c8.getText().toString());
            } else {
                list.remove(c8.getText().toString());
            }

        } else if (view.getId() == R.id.c9) {
            if (checked){
                list.add(c9.getText().toString());
            } else {
                list.remove(c9.getText().toString());
            }

        } else if (view.getId() == R.id.c10) {
            if (checked){
                list.add(c10.getText().toString());
            } else {
                list.remove(c10.getText().toString());
            }

        } else if (view.getId() == R.id.c11) {
            if (checked){
                list.add(c11.getText().toString());
            } else {
                list.remove(c11.getText().toString());
            }

        } else if (view.getId() == R.id.c12) {
            if (checked){
                list.add(c12.getText().toString());
            } else {
                list.remove(c12.getText().toString());
            }

        } else if (view.getId() == R.id.c13) {
            if (checked){
                list.add(c13.getText().toString());
            } else {
                list.remove(c13.getText().toString());
            }

        } else if (view.getId() == R.id.c14) {
            if (checked){
                list.add(c14.getText().toString());
            } else {
                list.remove(c14.getText().toString());
            }

        } else if (view.getId() == R.id.c15) {
            if (checked){
                list.add(c15.getText().toString());
            } else {
                list.remove(c15.getText().toString());
            }

        } else if (view.getId() == R.id.c16) {
            if (checked){
                list.add(c16.getText().toString());
            } else {
                list.remove(c16.getText().toString());
            }

        } else if (view.getId() == R.id.c17) {
            if (checked){
                list.add(c17.getText().toString());
            } else {
                list.remove(c17.getText().toString());
            }

        } else if (view.getId() == R.id.c18) {
            if (checked){
                list.add(c18.getText().toString());
            } else {
                list.remove(c18.getText().toString());
            }

        }

    }

}