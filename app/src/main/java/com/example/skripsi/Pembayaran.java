package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.Locale;

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

//        company = ClientSession.getInstance().getCompany();
        nik = ClientSession.getInstance().getNik();

        database = FirebaseDatabase.getInstance();
//        besarPremi = database.getReference();
//        Log.d("INTENT", "COMPANY: " + company);

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
                startActivity(intent);
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
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            bukti.setImageURI(imageUri);
            bukti.setVisibility(View.VISIBLE);
            btnBuktiFoto.setVisibility(View.GONE);
            StorageReference fileRef = storageRef.child( Nomor+ ".jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadurl = uri.toString();

//                    reference.child(String.valueOf(NIK)).setValue(downloadurl).addOnSuccessListener(aVoid -> {
//                        Toast.makeText(this, "Profile picture has been changed", Toast.LENGTH_SHORT).show();
//                    });
                });
            });
        }
    }
}