package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginAsuransi extends AppCompatActivity {

    DatabaseReference database;
    EditText username, password;
    Button btnMasuk;
    TextView masukNasabah;
    Asuransi asuransi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_login_asuransi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance().getReference().child("company");

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnMasuk = findViewById(R.id.btnMasuk);
        masukNasabah = findViewById(R.id.masukNasabah);
//        asuransi = (Asuransi) getIntent().getSerializableExtra("company")


        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_username = username.getText().toString();
                String login_password = password.getText().toString().trim();

                if (login_username.isEmpty()){
                    username.setError("Username Cannot Be Empty");
                } else if (login_password.isEmpty()) {
                    password.setError("Password Cannot Be Empty");
                }

                Intent menu = new Intent(v.getContext(), HomePageAsuransi.class);
                v.getContext().startActivity(menu);
            }
        });

        masukNasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}