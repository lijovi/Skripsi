package com.example.skripsi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailKlaim extends AppCompatActivity {

    TextView txtJenisAsuransi, txtKlaim, txtStatus, txtTanggal, txtNomorPolis, txtNilaiKlaim, txtKeterangan;
    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_klaim);

        txtJenisAsuransi = findViewById(R.id.txtJenisAsuransi);
        txtKlaim = findViewById(R.id.txtKlaim);
        txtStatus = findViewById(R.id.txtStatus);
        txtTanggal = findViewById(R.id.txtTanggal);
        txtNomorPolis = findViewById(R.id.txtNomorPolis);
        txtNilaiKlaim = findViewById(R.id.txtNilaiKlaim);
        txtKeterangan = findViewById(R.id.txtKeterangan);
        btnKembali = findViewById(R.id.btnKembali);

        // Ambil data dari intent
        String jenisAsuransi = getIntent().getStringExtra("jenisAsuransi");
        String klaim = getIntent().getStringExtra("klaim");
        String status = getIntent().getStringExtra("status");
        String tanggal = getIntent().getStringExtra("tanggal");
        String nomorPolis = getIntent().getStringExtra("nomorPolis");
        String nilaiKlaim = getIntent().getStringExtra("nilaiKlaim");
        String keterangan = getIntent().getStringExtra("keterangan");

        txtJenisAsuransi.setText("Asuransi: " + jenisAsuransi);
        txtKlaim.setText("Klaim: " + klaim);
        txtStatus.setText("Status: " + status);
        txtTanggal.setText("Tanggal Pengajuan: " + tanggal);
        txtNomorPolis.setText("Nomor Polis: " + nomorPolis);
        txtNilaiKlaim.setText("Nilai Klaim: " + nilaiKlaim);
        txtKeterangan.setText("Keterangan: " + keterangan);

        btnKembali.setOnClickListener(v -> finish());
    }

    private String getSafe(DataSnapshot snapshot, String key) {
        String value = snapshot.child(key).getValue(String.class);
        return value != null ? value : "-";
    }
}
