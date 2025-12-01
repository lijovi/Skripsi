package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Pembayaran extends AppCompatActivity {

    TextView virtualAccount, jumlah, jatuhTempo;
    String nik, Nomor, temp;
    int company;
    FirebaseDatabase database;
    DatabaseReference checkVirtual, checkPremi, besarPremi;
    Button back, btnBuktiFoto, btnUnggah;
    ImageView bukti;
    int PICK_IMAGE_REQUEST = 100;
    StorageReference storageRef;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    RadioGroup pembayaran;
    RadioButton pilih;
    ActivityResultLauncher<Intent> gallery;
    int check = 0;
    Calendar calendar;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pembayaran);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        virtualAccount = findViewById(R.id.virtualAccount);
        jumlah = findViewById(R.id.jumlah);
        jatuhTempo = findViewById(R.id.jatuhTempo);
        back = findViewById(R.id.back);
        btnBuktiFoto = findViewById(R.id.btnUploadBuktiFoto);
        bukti = findViewById(R.id.bukti);
        btnUnggah = findViewById(R.id.btnUnggah);
        calendar = Calendar.getInstance();

//        company = ClientSession.getInstance().getCompany();
        nik = ClientSession.getInstance().getNik();

        database = FirebaseDatabase.getInstance();

        DialogForm();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnBuktiFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.launch(intent);
            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null){
                Uri selectedImage = result.getData().getData();
                uploadToCloudinary(selectedImage);
            }
        });

        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);
                startActivity(intent);
            }
        });
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(Pembayaran.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.pop_up_pilih_pembayaran, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        Locale locale = new Locale("in", "ID");
        NumberFormat idrFormat = NumberFormat.getInstance(locale);

        pembayaran = dialogView.findViewById(R.id.pembayaran);

        pembayaran.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pilih = dialogView.findViewById(checkedId);
                if (checkedId == R.id.travel) {
                    checkPremi = database.getReference("transaksiTravel");
                    Query checkDataPremi = checkPremi.orderByChild("nik").equalTo(nik);
                    checkDataPremi.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                if (!snapshot.child(nik).hasChild("check")){
                                    String cek = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
                                    LocalDate date = LocalDate.parse(cek, format);
                                    LocalDate current = LocalDate.now();
                                    if (!current.isAfter(date)){
                                        int premi = snapshot.child(nik).child("besarPremi").getValue(int.class);
                                        String JatuhTempo = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                        Nomor = snapshot.child(nik).child("nomorPolisTravel").getValue(String.class);
                                        jumlah.setText("Rp " + idrFormat.format((double) premi));
                                        jatuhTempo.setText(JatuhTempo + " !");
                                        int nocompany = snapshot.child(nik).child("company").getValue(int.class);
                                        checkVirtual = database.getReference("company");
                                        Query checkDataVirtual = checkVirtual.orderByChild("companyId").equalTo(nocompany);
                                        Log.d("INTENT", "COMPANY: " + nocompany);
                                        checkDataVirtual.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){
                                                    String virtual = snapshot.child(String.valueOf(nocompany)).child("companyVirtualAccount").getValue(String.class);
                                                    virtualAccount.setText(virtual);
                                                    check = 1;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                } else if (Objects.equals(snapshot.child(nik).child("check").getValue(String.class), "Not Approve")) {
                                    String cek = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
                                    LocalDate date = LocalDate.parse(cek, format);
                                    LocalDate current = LocalDate.now();
                                    if (!current.isAfter(date)){
                                        int premi = snapshot.child(nik).child("besarPremi").getValue(int.class);
                                        String JatuhTempo = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                        Nomor = snapshot.child(nik).child("nomorPolisTravel").getValue(String.class);
                                        jumlah.setText("Rp " + idrFormat.format((double) premi));
                                        jatuhTempo.setText(JatuhTempo + " !");
                                        int nocompany = snapshot.child(nik).child("company").getValue(int.class);
                                        checkVirtual = database.getReference("company");
                                        Query checkDataVirtual = checkVirtual.orderByChild("companyId").equalTo(nocompany);
                                        Log.d("INTENT", "COMPANY: " + nocompany);
                                        checkDataVirtual.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){
                                                    String virtual = snapshot.child(String.valueOf(nocompany)).child("companyVirtualAccount").getValue(String.class);
                                                    virtualAccount.setText(virtual);
                                                    check = 1;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }
                            alertDialog.dismiss();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (checkedId == R.id.health) {
                    checkPremi = database.getReference("transaksiHealth");
                    Query checkDataPremi = checkPremi.orderByChild("nik").equalTo(nik);
                    checkDataPremi.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                if (!snapshot.child(nik).hasChild("check")){
                                    String cek = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
                                    LocalDate date = LocalDate.parse(cek, format);
                                    LocalDate current = LocalDate.now();
                                    if (!current.isAfter(date)){
                                        int premi = snapshot.child(nik).child("besarPremi").getValue(int.class);
                                        String JatuhTempo = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                        Nomor = snapshot.child(nik).child("nomorPolisKesehatan").getValue(String.class);
                                        jumlah.setText("Rp " + idrFormat.format((double) premi));
                                        jatuhTempo.setText(JatuhTempo + " !");
                                        int nocompany = snapshot.child(nik).child("company").getValue(int.class);
                                        Log.d("INTENT", "COMPANY: " + nocompany);
                                        checkVirtual = database.getReference("company");
                                        Query checkDataVirtual = checkVirtual.orderByChild("companyId").equalTo(nocompany);
                                        checkDataVirtual.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){
                                                    String virtual = snapshot.child(String.valueOf(nocompany)).child("companyVirtualAccount").getValue(String.class);
                                                    virtualAccount.setText(virtual);
                                                    check = 2;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                } else if (Objects.equals(snapshot.child(nik).child("check").getValue(String.class), "Not Approve")) {
                                    String cek = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd - MM - yyyy");
                                    LocalDate date = LocalDate.parse(cek, format);
                                    LocalDate current = LocalDate.now();
                                    if (!current.isAfter(date)){
                                        int premi = snapshot.child(nik).child("besarPremi").getValue(int.class);
                                        String JatuhTempo = snapshot.child(nik).child("jatuhTempo").getValue(String.class);
                                        Nomor = snapshot.child(nik).child("nomorPolisKesehatan").getValue(String.class);
                                        jumlah.setText("Rp " + idrFormat.format((double) premi));
                                        jatuhTempo.setText(JatuhTempo + " !");
                                        int nocompany = snapshot.child(nik).child("company").getValue(int.class);
                                        Log.d("INTENT", "COMPANY: " + nocompany);
                                        checkVirtual = database.getReference("company");
                                        Query checkDataVirtual = checkVirtual.orderByChild("companyId").equalTo(nocompany);
                                        checkDataVirtual.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()){
                                                    String virtual = snapshot.child(String.valueOf(nocompany)).child("companyVirtualAccount").getValue(String.class);
                                                    virtualAccount.setText(virtual);
                                                    check = 2;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }
                            alertDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void uploadToCloudinary(Uri selectedImage) {
        MediaManager.get().upload(selectedImage).unsigned("save_image").callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String imageuri = resultData.get("secure_url").toString();

                Glide.with(Pembayaran.this).load(imageuri).into(bukti);
                bukti.setVisibility(View.VISIBLE);

                String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
                String second = String.format("%02d",calendar.get(Calendar.SECOND));
                String currenttime = hour + " : " + minute + " : " + second;

                Nasabah nasabah = new Nasabah();

                if (check == 1){
                    nasabah.Pembayaran(currenttime, imageuri, nik, "travel");
                } else if (check == 2) {
                    nasabah.Pembayaran(currenttime, imageuri, nik, "health");
                }

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }
}