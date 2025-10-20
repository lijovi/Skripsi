package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class InsuranceInfoNasabah extends AppCompatActivity {

    Button btnHome, btnNotifikasi, btnProfile, btnOk;
    TextView limitTravel, lupaPassword, nomorPolisTravel, namaTravel, jenisTravel, statusTravel, jangkaTravel;
    TextView limitHealth, nomorPolisHealth, namaHealth, jenisHealth, statusHealth, jangkaHealth;
    int LimitHealth, LimitTravel;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextInputLayout password;
    ScrollView content;
    String NomorPolisTravel, Nama;
    String NomorPolisHealth;
    FirebaseDatabase database;
    DatabaseReference referenceTravel, referenceHealth, referenceDataHealth, referenceDataTravel, referencePembayaran, riwayatMedisRef, klaimRef;
    String checkH, checkT;
    TableLayout tableMedicalHistory;
    TableLayout tableKlaimAsuransiHealth;
    TableLayout tableKlaimAsuransiTravel;
    private String NIK;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insurance_info_nasabah);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) supportActionBar.hide();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        btnHome = findViewById(R.id.btnHome);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        btnProfile = findViewById(R.id.btnProfile);
        limitHealth = findViewById(R.id.limitHealth);
        limitTravel = findViewById(R.id.limitTravel);
        content = findViewById(R.id.insuranceContent);

        // inisialisasi data travel
        nomorPolisTravel = findViewById(R.id.nomorPolisTravel);
        namaTravel = findViewById(R.id.namaTravel);
        jenisTravel = findViewById(R.id.jenisTravel);
        statusTravel = findViewById(R.id.statusTravel);
        jangkaTravel = findViewById(R.id.jangkaTravel);

        // inisialisasi data health
        nomorPolisHealth = findViewById(R.id.nomorPolisHealth);
        namaHealth = findViewById(R.id.namaHealth);
        jenisHealth = findViewById(R.id.jenisHealth);
        statusHealth = findViewById(R.id.statusHealth);
        jangkaHealth = findViewById(R.id.jangkaHealth);

        // Tabel riwayat medis
        tableMedicalHistory = findViewById(R.id.tableMedicalHistory);
        tableKlaimAsuransiHealth = findViewById(R.id.tableKlaimAsuransiHealth);
        tableKlaimAsuransiTravel = findViewById(R.id.tableKlaimAsuransiTravel);

        // Set values
        LimitHealth = ClientSession.getInstance().getLimitHealth();
        LimitTravel = ClientSession.getInstance().getLimitTravel();
        Nama = ClientSession.getInstance().getNama();

        // DATABASE
        database = FirebaseDatabase.getInstance();
        referenceTravel = database.getReference("transaksiTravel");
        referenceHealth = database.getReference("transaksiHealth");
        referenceDataHealth = database.getReference("clientHealth");
        referenceDataTravel = database.getReference("clientTravel");
        referencePembayaran = database.getReference("pembayaran");
        klaimRef = database.getReference("klaim");
        riwayatMedisRef = database.getReference("riwayatMedis");

        NIK = ClientSession.getInstance().getNik();

        Query checkTravel = referenceTravel.orderByChild("nik").equalTo(NIK);
        Log.d("INTENT", "NIK: " + NIK);
        checkTravel.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisTravel = snapshot.child(NIK).child("nomorPolisTravel").getValue(String.class);
                    Log.d("INTENT", "Received NIK: " + NomorPolisTravel);
                    nomorPolisTravel.setText(NomorPolisTravel);
                    namaTravel.setText(Nama);
                    if (snapshot.child(NIK).hasChild("check")){
                        checkT = snapshot.child(NIK).child("check").getValue(String.class);
                    } else {
                        statusTravel.setText(R.string.non_aktif);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkDataTravel = referenceDataTravel.orderByChild("nik").equalTo(NIK);
        checkDataTravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    jenisTravel.setText(snapshot.child(NIK).child("planAsuransi").getValue(String.class));
                    String cek = snapshot.child(NIK).child("tipePolis").getValue(String.class);
                    if (Objects.equals(cek, "Iya")){
                        jangkaTravel.setText(R.string.tahunan);
                        referencePembayaran.child(NIK).child(NomorPolisTravel).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    String date = snapshot.child("date").getValue(String.class);
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
                                    LocalDate start = LocalDate.parse(date, format);
                                    LocalDate end = start.plusYears(1);
                                    LocalDate current = LocalDate.now();

                                    if (Objects.equals(checkT, "Approve")){
                                        if (!current.isBefore(start) && !current.isAfter(end)){
                                            statusTravel.setText(R.string.aktif);
                                        } else {
                                            statusTravel.setText(R.string.non_aktif);
                                        }
                                    } else {
                                        statusTravel.setText(R.string.non_aktif);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        String fulltext = snapshot.child(NIK).child("masaPerjalanan").getValue(String.class);
                        String[] part = fulltext.split("-");
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            Date date1 = dates.parse(part[0].trim());
                            Date date2 = dates.parse(part[1].trim());

                            Long difference = (Math.abs(date1.getTime() - date2.getTime()) / (24*60*60*1000))+1;
                            jangkaTravel.setText(difference.toString());

                            Date currentDate = dates.parse(dates.format(new Date()));
                            Log.d("MyApp", "Current Date: " + dates.format(currentDate));

                            if (Objects.equals(checkT, "Approve")){
                                if (!currentDate.before(date1) && !currentDate.after(date2)){
                                    statusTravel.setText(R.string.aktif);
                                } else {
                                    statusTravel.setText(R.string.non_aktif);
                                }
                            } else {
                                statusTravel.setText(R.string.non_aktif);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query checkHealth = referenceHealth.orderByChild("nik").equalTo(NIK);
        checkHealth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NomorPolisHealth = snapshot.child(NIK).child("nomorPolisKesehatan").getValue(String.class);
                    nomorPolisHealth.setText(NomorPolisHealth);
                    namaHealth.setText(Nama);
//                    Log.d("INTENT", "NOMOR POLIS: " + NomorPolisHealth);
//                    Log.d("INTENT", "NAMA: " + Nama);
                    if (snapshot.child(NIK).hasChild("check")){
                        checkH = snapshot.child(NIK).child("check").getValue(String.class);
                    } else {
                        statusHealth.setText(R.string.non_aktif);
                    }
//                    Log.d("CHECK", checkH);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Query checkDataHealth = referenceDataHealth.orderByChild("nik").equalTo(NIK);
        checkDataHealth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    jenisHealth.setText(snapshot.child(NIK).child("plan").getValue(String.class));
                    jangkaHealth.setText(R.string.tahunan);
//                    Log.d("INTENT", "JENIS HEALTH: " + jenisHealth);
//                    Log.d("INTENT", "JANGKA HEALTH: " + jangkaHealth);
//
                    if (Objects.equals(checkH, "Approve")){
                        String date = snapshot.child(NIK).child("periodePertanggungan").getValue(String.class);
//                        Log.d("INTENT", "DATE: " + date);
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate start = LocalDate.parse(date, format);
                        LocalDate end = start.plusYears(1);
                        LocalDate current = LocalDate.now();
                        if (!current.isBefore(start) && !current.isAfter(end)){
                            statusHealth.setText(R.string.aktif);
                        } else {
                            statusHealth.setText(R.string.non_aktif);
                        }
                    } else {
                        statusHealth.setText(R.string.non_aktif);
                    }
//                    String date = snapshot.child(NIK).child("date").getValue(String.class);
//                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
//                    LocalDate start = LocalDate.parse(date, format);
//                    LocalDate end = start.plusYears(1);
//                    LocalDate current = LocalDate.now();
//                    if (!current.isBefore(start) && !current.isAfter(end)){
//                        statusHealth.setText(R.string.aktif);
//                    } else {
//                        statusHealth.setText(R.string.non_aktif);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        riwayatMedisRef.child(NIK).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String tglDiagnosa = snapshot.child("tanggalDiagnosa").getValue(String.class);
                    String kondisi = snapshot.child("kondisi").getValue(String.class);
                    String besarKlaim = snapshot.child("besarKlaim").getValue(String.class);
                    String statusKlaim = snapshot.child("statusKlaim").getValue(String.class);
                    addMedicalHistoryRow(tableMedicalHistory, tglDiagnosa, kondisi, besarKlaim, statusKlaim);
//                    tableMedicalHistory.removeViews(1, tableMedicalHistory.getChildCount() - 1);
//                    for (DataSnapshot child : snapshot.getChildren()) {
//                        String tglDiagnosa = child.child("tanggalDiagnosa").getValue(String.class);
//                        String kondisi = child.child("kondisi").getValue(String.class);
//                        String besarKlaim = child.child("besarKlaim").getValue(String.class);
//                        String statusKlaim = child.child("statusKlaim").getValue(String.class);
//
//                        addMedicalHistoryRow(tableMedicalHistory, tglDiagnosa, kondisi, besarKlaim, statusKlaim);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        DatabaseReference healthRef = klaimRef.child("Health").child(NIK);
        DatabaseReference travelRef = klaimRef.child("Travel").child(NIK);

        // ================= HEALTH =================
        healthRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // Klaim 1 Health
                    Map<String, Object> klaim1 = new HashMap<>();
                    klaim1.put("tanggalPengajuan", "2025-09-10");
                    klaim1.put("klaim", "Klaim Rawat Inap");
                    klaim1.put("statusKlaimAsuransi", "Disetujui");
                    Map<String, Object> detail1 = new HashMap<>();
                    detail1.put("nomorPolis", "H12345678");
                    detail1.put("nilaiKlaim", "Rp 5.000.000");
                    detail1.put("keterangan", "Rawat inap 3 hari di RS Bina Sehat");
                    klaim1.put("detail", detail1);
                    healthRef.child("Klaim1").setValue(klaim1);

                    // Klaim 2 Health
                    Map<String, Object> klaim2 = new HashMap<>();
                    klaim2.put("tanggalPengajuan", "2025-09-15");
                    klaim2.put("klaim", "Klaim Rawat Jalan");
                    klaim2.put("statusKlaimAsuransi", "Menunggu Verifikasi");
                    Map<String, Object> detail2 = new HashMap<>();
                    detail2.put("nomorPolis", "H87654321");
                    detail2.put("nilaiKlaim", "Rp 3.000.000");
                    detail2.put("keterangan", "Rawat jalan 2 hari di RS Bina Sehat");
                    klaim2.put("detail", detail2);
                    healthRef.child("Klaim2").setValue(klaim2);
                }

                // Tampilkan tabel Health
                healthRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int childCount = tableKlaimAsuransiHealth.getChildCount();
                        if (childCount > 1) {
                            tableKlaimAsuransiHealth.removeViews(1, childCount - 1);
                        }

                        for (DataSnapshot klaimSnapshot : snapshot.getChildren()) {
                            addClaimRow(tableKlaimAsuransiHealth, klaimSnapshot, "Health");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // ================= TRAVEL =================
        travelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // Klaim 1 Travel
                    Map<String, Object> klaim1 = new HashMap<>();
                    klaim1.put("tanggalPengajuan", "2025-08-05");
                    klaim1.put("klaim", "Klaim Keterlambatan Penerbangan");
                    klaim1.put("statusKlaimAsuransi", "Disetujui");
                    Map<String, Object> detail1 = new HashMap<>();
                    detail1.put("nomorPolis", "T98765432");
                    detail1.put("nilaiKlaim", "Rp 3.000.000");
                    detail1.put("keterangan", "Keterlambatan penerbangan 3 jam");
                    klaim1.put("detail", detail1);
                    travelRef.child("Klaim1").setValue(klaim1);

                    // Klaim 2 Travel
                    Map<String, Object> klaim2 = new HashMap<>();
                    klaim2.put("tanggalPengajuan", "2025-08-10");
                    klaim2.put("klaim", "Klaim Barang Hilang");
                    klaim2.put("statusKlaimAsuransi", "Menunggu Verifikasi");
                    Map<String, Object> detail2 = new HashMap<>();
                    detail2.put("nomorPolis", "T12345678");
                    detail2.put("nilaiKlaim", "Rp 2.500.000");
                    detail2.put("keterangan", "Bagasi hilang di bandara");
                    klaim2.put("detail", detail2);
                    travelRef.child("Klaim2").setValue(klaim2);
                }

                // Tampilkan tabel Travel
                travelRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int childCount = tableKlaimAsuransiTravel.getChildCount();
                        if (childCount > 1) {
                            tableKlaimAsuransiTravel.removeViews(1, childCount - 1);
                        }

                        for (DataSnapshot klaimSnapshot : snapshot.getChildren()) {
                            addClaimRow(tableKlaimAsuransiTravel, klaimSnapshot, "Travel");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        Locale locale = new Locale("in", "ID");

        NumberFormat idrFormat = NumberFormat.getInstance(locale);

        limitHealth.setText("Rp " + idrFormat.format((double) LimitHealth));
        limitTravel.setText("Rp " + idrFormat.format((double) LimitTravel));

        DialogForm();

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), HomePageNasabah.class));
        });

        btnNotifikasi.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), NotificationNasabah.class));
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProfileNasabah.class));
        });
    }


    private void DialogForm() {
        dialog = new AlertDialog.Builder(InsuranceInfoNasabah.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.input_password_nasabah, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        password = dialogView.findViewById(R.id.password);
        lupaPassword = dialogView.findViewById(R.id.lupaPassword);
        btnOk = dialogView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = password.getEditText().getText().toString();
                String cekPassword = ClientSession.getInstance().getPassword();

                if (Objects.equals(Password, cekPassword)){
                    alertDialog.dismiss();
                    content.setVisibility(View.VISIBLE);

                } else {
                    password.setError("Wrong Password");
                }
            }
        });

        
    }

    private void addMedicalHistoryRow(TableLayout table, String tglDiagnosa, String kondisi, String besarKlaim, String statusKlaim) {
        TableRow row = new TableRow(this);
    
        TextView tgl = new TextView(this);
        tgl.setText(tglDiagnosa);
        tgl.setPadding(6, 6, 6, 6);
        tgl.setTextColor(ContextCompat.getColor(this, R.color.black));
    
        TextView kond = new TextView(this);
        kond.setText(kondisi);
        kond.setPadding(6, 6, 6, 6);
        kond.setTextColor(ContextCompat.getColor(this, R.color.black));

        TextView klaim = new TextView(this);
        klaim.setText(besarKlaim);
        klaim.setPadding(6, 6, 6, 6);
        klaim.setTextColor(ContextCompat.getColor(this, R.color.black));

        TextView status = new TextView(this);
        status.setText(statusKlaim);
        status.setPadding(6, 6, 6, 6);
        status.setTextColor(ContextCompat.getColor(this, R.color.black));

        row.addView(tgl);
        row.addView(kond);
        row.addView(klaim);
        row.addView(status);
    
        table.addView(row);
    }

    private void addClaimRow(TableLayout tableLayout, DataSnapshot klaimSnapshot, String tipeAsuransi) {
        String klaim = klaimSnapshot.child("klaim").getValue(String.class);
        String status = klaimSnapshot.child("statusKlaimAsuransi").getValue(String.class);
        String tanggal = klaimSnapshot.child("tanggalPengajuan").getValue(String.class);
        String nomorPolis = klaimSnapshot.child("detail/nomorPolis").getValue(String.class);
        String nilaiKlaim = klaimSnapshot.child("detail/nilaiKlaim").getValue(String.class);
        String keterangan = klaimSnapshot.child("detail/keterangan").getValue(String.class);

        TableRow row = new TableRow(this);
        row.setPadding(8, 8, 8, 8);

        TextView txtTanggal = new TextView(this);
        txtTanggal.setText(tanggal);
        txtTanggal.setPadding(8, 8, 8, 8);
        txtTanggal.setTextColor(Color.BLACK);

        TextView txtKlaim = new TextView(this);
        txtKlaim.setText(klaim);
        txtKlaim.setPadding(8, 8, 8, 8);
        txtKlaim.setTextColor(Color.BLACK);

        TextView txtStatus = new TextView(this);
        txtStatus.setText(status);
        txtStatus.setPadding(8, 8, 8, 8);
        txtStatus.setTextColor(Color.BLACK);

        row.addView(txtKlaim);
        row.addView(txtStatus);
        row.addView(txtTanggal);


        row.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailKlaim.class);
            intent.putExtra("nik", NIK);
            intent.putExtra("jenisAsuransi", tipeAsuransi);
            intent.putExtra("klaim", klaim);
            intent.putExtra("status", status);
            intent.putExtra("tanggal", tanggal);
            intent.putExtra("nomorPolis", nomorPolis);
            intent.putExtra("nilaiKlaim", nilaiKlaim);
            intent.putExtra("keterangan", keterangan);
            startActivity(intent);
        });

        tableLayout.addView(row);
    }
}