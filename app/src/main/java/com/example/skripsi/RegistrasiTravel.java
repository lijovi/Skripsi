package com.example.skripsi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
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
    String JenisPolis, pilihanPlan;
    int perusahaan;
    Spinner plan;
    Calendar calendar;
    int limit;
    TextView textnik, textnama, textemail, textkelamin, textno, textalamat, textjenis, textkeluarga, textmasa, texttipe, textahli, texthubungan, textnegara, texttujuan;
    int cek;
    CheckBox check;
    Button information, close, back;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView superior, deluxe, executive, vip;

    TextView judul, no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14, no15, no16, no17, no18;


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

        textnik = findViewById(R.id.textnik);
        textnama = findViewById(R.id.textnama);
        textemail = findViewById(R.id.textemail);
        textkelamin = findViewById(R.id.textkelamin);
        textno = findViewById(R.id.textno);
        textalamat = findViewById(R.id.textalamat);
        textjenis = findViewById(R.id.textjenis);
        textkeluarga = findViewById(R.id.textkeluarga);
        textmasa = findViewById(R.id.textmasa);
        texttipe = findViewById(R.id.texttipe);
        textahli = findViewById(R.id.textahli);
        texthubungan = findViewById(R.id.texthubungan);
        textnegara = findViewById(R.id.textnegara);
        texttujuan = findViewById(R.id.texttujuan);
        check = findViewById(R.id.check);
        information = findViewById(R.id.information);

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

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormInfo();
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
                cek = 0;

                if (TextUtils.isEmpty(nik.getText().toString())){
                    textnik.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textnik.setError(null);
                }

                if (TextUtils.isEmpty(nama.getText().toString())) {
                    textnama.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textnama.setError(null);
                }

                if (TextUtils.isEmpty(email.getText().toString())){
                    textemail.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textemail.setError(null);
                }

                if (jenisKelamin.getCheckedRadioButtonId() == -1) {
                    textkelamin.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textkelamin.setError(null);
                }

                if (TextUtils.isEmpty(noTelp.getText().toString())) {
                    textno.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textno.setError(null);
                }

                if (TextUtils.isEmpty(alamat.getText().toString())) {
                    textalamat.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textalamat.setError(null);
                }

                if (jenisPolis.getCheckedRadioButtonId() == -1){
                    textjenis.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textjenis.setError(null);
                    selectedID = jenisPolis.getCheckedRadioButtonId();
                    selectedJenis = findViewById(selectedID);
                    String selected = selectedJenis.getText().toString();
                    if (Objects.equals(selected, "Family")){
                        if (TextUtils.isEmpty(namaKeluarga.getText().toString())){
                            textkeluarga.setError(getString(R.string.wajib));
                            cek+=1;
                        } else {
                            textkeluarga.setError(getString(R.string.wajib));
                        }
                    }
                }

                if (tipePolis.getCheckedRadioButtonId() == -1){
                    texttipe.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    texttipe.setError(null);
                }

                if (TextUtils.isEmpty(masaPerjalanan.getText().toString())){
                    textmasa.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textmasa.setError(null);
                }

                if (TextUtils.isEmpty(namaAhliWaris.getText().toString())){
                    textahli.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textahli.setError(null);
                }

                if (TextUtils.isEmpty(hubunganDenganAhliWaris.getText().toString())){
                    texthubungan.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    texthubungan.setError(null);
                }

                if (TextUtils.isEmpty(negaraTujuan.getText().toString())){
                    textnegara.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    textnegara.setError(null);
                }

                if (TextUtils.isEmpty(tujuanPerjalanan.getText().toString())){
                    texttujuan.setError(getString(R.string.wajib));
                    cek+=1;
                } else {
                    texttujuan.setError(null);
                }

                if (!check.isChecked()){
                    cek+=1;
                    check.setError(getString(R.string.check));
                } else {
                    check.setError(null);
                }


                if (cek == 0){
                    insertData();
                    Intent masuk = new Intent(getApplicationContext(), Login.class);
                    startActivity(masuk);
                } else {
                    cek = 0;
                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planTravel, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plan.setAdapter(adapter);
        plan.setOnItemSelectedListener(this);


        btnMasaPerjalanan.setOnClickListener(view-> {

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

    private void DialogFormInfo() {
        dialog = new AlertDialog.Builder(RegistrasiTravel.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.information_travel, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        superior = dialogView.findViewById(R.id.superior);
        deluxe = dialogView.findViewById(R.id.deluxe);
        executive = dialogView.findViewById(R.id.executive);
        vip = dialogView.findViewById(R.id.vip);

//        DatabaseReference Info = FirebaseDatabase.getInstance().getReference("plan").child("travel");
//        Info.child("1").setValue("Superior");
//        Info.child("2").setValue("Deluxe");
//        Info.child("3").setValue("Executive");
//        Info.child("4").setValue("VIP");

        DatabaseReference InfoData = FirebaseDatabase.getInstance().getReference("plan").child("travel");
        InfoData.get().addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               DataSnapshot snapshot = task.getResult();

               superior.setText(snapshot.child("1").getValue(String.class));
               deluxe.setText(snapshot.child("2").getValue(String.class));
               executive.setText(snapshot.child("3").getValue(String.class));
               vip.setText(snapshot.child("4").getValue(String.class));
           }
        });

        superior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormTravel("Superior");
            }
        });

        deluxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormTravel("Deluxe");
            }
        });

        executive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormTravel("Executive");
            }
        });

        vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFormTravel("VIP");
            }
        });

        close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void DialogFormTravel(String plan) {
        dialog = new AlertDialog.Builder(RegistrasiTravel.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.travel, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        DatabaseReference data = FirebaseDatabase.getInstance().getReference("benefit").child("travel").child(plan);

        judul = dialogView.findViewById(R.id.judul);
        no1 = dialogView.findViewById(R.id.no1);
        no2 = dialogView.findViewById(R.id.no2);
        no3 = dialogView.findViewById(R.id.no3);
        no4 = dialogView.findViewById(R.id.no4);
        no5 = dialogView.findViewById(R.id.no5);
        no6 = dialogView.findViewById(R.id.no6);
        no7 = dialogView.findViewById(R.id.no7);
        no8 = dialogView.findViewById(R.id.no8);
        no9 = dialogView.findViewById(R.id.no9);
        no10 = dialogView.findViewById(R.id.no10);
        no11 = dialogView.findViewById(R.id.no11);
        no12 = dialogView.findViewById(R.id.no12);
        no13 = dialogView.findViewById(R.id.no13);
        no14 = dialogView.findViewById(R.id.no14);
        no15 = dialogView.findViewById(R.id.no15);
        no16 = dialogView.findViewById(R.id.no16);
        no17 = dialogView.findViewById(R.id.no17);
        no18 = dialogView.findViewById(R.id.no18);

        judul.setText(plan + "\n" + "(US Dollar)");
        data.get().addOnCompleteListener(task -> {
            DataSnapshot snapshot = task.getResult();
            no1.setText(snapshot.child("no1").getValue(String.class));
            no2.setText(snapshot.child("no2").getValue(String.class));
            no3.setText(snapshot.child("no3").getValue(String.class));
            no4.setText(snapshot.child("no4").getValue(String.class));
            no5.setText(snapshot.child("no5").getValue(String.class));
            no6.setText(snapshot.child("no6").getValue(String.class));
            no7.setText(snapshot.child("no7").getValue(String.class));
            no8.setText(snapshot.child("no8").getValue(String.class));
            no9.setText(snapshot.child("no9").getValue(String.class));
            no10.setText(snapshot.child("no10").getValue(String.class));
            no11.setText(snapshot.child("no11").getValue(String.class));
            no12.setText(snapshot.child("no12").getValue(String.class));
            no13.setText(snapshot.child("no13").getValue(String.class));
            no14.setText(snapshot.child("no14").getValue(String.class));
            no15.setText(snapshot.child("no15").getValue(String.class));
            no16.setText(snapshot.child("no16").getValue(String.class));
            no17.setText(snapshot.child("no17").getValue(String.class));
            no18.setText(snapshot.child("no18").getValue(String.class));

        });


        back = dialogView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
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

        String TipePolis = selectedPolis.getText().toString();
        String NamaAhliWaris = namaAhliWaris.getText().toString();
        String HubunganDenganAhliWaris = hubunganDenganAhliWaris.getText().toString();
        String NegaraTujuan = negaraTujuan.getText().toString();
        String TujuanPerjalanan = tujuanPerjalanan.getText().toString();
        String PlanAsuransi = pilihanPlan.toString();
        int Perusahaan = perusahaan;
        int Limit = limit;

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
                    Perusahaan, currenttime, currentdate, Limit, NamaAhliWaris, HubunganDenganAhliWaris, JenisPolis, NamaKeluarga, PlanAsuransi, MasaPerjalanan, TipePolis, NegaraTujuan, TujuanPerjalanan);
            nasabah.RegistrasiTravel(nasabah);
        } else {
            NasabahTravel nasabah = new NasabahTravel(NIK, Nama, Email, JenisKelamin, NoTelp, Alamat,"0", "Travel",
                    Perusahaan, currenttime, currentdate, Limit, NamaAhliWaris, HubunganDenganAhliWaris, JenisPolis, null, PlanAsuransi, MasaPerjalanan, TipePolis, NegaraTujuan, TujuanPerjalanan);
            nasabah.RegistrasiTravel(nasabah);
        }

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
