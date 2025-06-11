package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileAsuransi extends AppCompatActivity {

    Button btnHome, btnBack;
    TextView namaPerusahaan, username, email, virtualAccount, ubahPassword, ubahBahasa, FAQ, syaratDanKetentuan, keluar;

//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    String uid = auth.getCurrentUser().getUid().toString();
//    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("company");
//    Asuransi asuransi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_profile_asuransi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHome = findViewById(R.id.btnHome);
        namaPerusahaan = findViewById(R.id.namaPerusahaan);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        virtualAccount = findViewById(R.id.virtualAccount);
        btnBack = findViewById(R.id.btnBack);

        ubahPassword = findViewById(R.id.ubahPassword);
        ubahBahasa = findViewById(R.id.ubahBahasa);
        FAQ = findViewById(R.id.FAQ);
        syaratDanKetentuan = findViewById(R.id.syaratDanKetentuan);
        keluar = findViewById(R.id.keluar);

//        Ambil Data
        String Username = CompanySession.getInstance().getUsername();
        String Nama = CompanySession.getInstance().getNama();
        String Email = CompanySession.getInstance().getEmail();
        String VirtualAccount = CompanySession.getInstance().getVirtualAccount();

//        Set Data Ke Tampilan
        namaPerusahaan.setText(Nama);
        username.setText(Username);
        email.setText(Email);
        virtualAccount.setText(VirtualAccount);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                startActivity(intent);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAsuransi.this, LoginAsuransi.class);
                startActivity(intent);
            }
        });
    }

}