package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class PopUpRiwayatPenyakit extends AppCompatActivity {

    CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18;
    ArrayList<String> list = new ArrayList<>();
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_pop_up_riwayat_penyakit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);
        c7 = findViewById(R.id.c7);
        c8 = findViewById(R.id.c8);
        c9 = findViewById(R.id.c9);
        c10 = findViewById(R.id.c10);
        c11 = findViewById(R.id.c11);
        c12 = findViewById(R.id.c12);
        c13 = findViewById(R.id.c13);
        c14 = findViewById(R.id.c14);
        c15 = findViewById(R.id.c15);
        c16 = findViewById(R.id.c16);
        c17 = findViewById(R.id.c17);
        c18 = findViewById(R.id.c18);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrasiHealth.class);
                intent.putExtra("riwayat", list);
                startActivity(intent);
            }
        });
    }

    public void checked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if (view.getId() == R.id.c1){
            if (checked){
                list.add(c1.getText().toString());
            }
        } else if (view.getId() == R.id.c2) {
            if (checked){
                list.add(c2.getText().toString());
            }

        } else if (view.getId() == R.id.c3) {
            if (checked){
                list.add(c3.getText().toString());
            }

        } else if (view.getId() == R.id.c4) {
            if (checked){
                list.add(c4.getText().toString());
            }

        } else if (view.getId() == R.id.c5) {
            if (checked){
                list.add(c5.getText().toString());
            }

        } else if (view.getId() == R.id.c6) {
            if (checked){
                list.add(c6.getText().toString());
            }

        } else if (view.getId() == R.id.c7) {
            if (checked){
                list.add(c7.getText().toString());
            }

        } else if (view.getId() == R.id.c8) {
            if (checked){
                list.add(c8.getText().toString());
            }

        } else if (view.getId() == R.id.c9) {
            if (checked){
                list.add(c9.getText().toString());
            }

        } else if (view.getId() == R.id.c10) {
            if (checked){
                list.add(c10.getText().toString());
            }

        } else if (view.getId() == R.id.c11) {
            if (checked){
                list.add(c11.getText().toString());
            }

        } else if (view.getId() == R.id.c12) {
            if (checked){
                list.add(c12.getText().toString());
            }

        } else if (view.getId() == R.id.c13) {
            if (checked){
                list.add(c13.getText().toString());
            }

        } else if (view.getId() == R.id.c14) {
            if (checked){
                list.add(c14.getText().toString());
            }

        } else if (view.getId() == R.id.c15) {
            if (checked){
                list.add(c15.getText().toString());
            }

        } else if (view.getId() == R.id.c16) {
            if (checked){
                list.add(c16.getText().toString());
            }

        } else if (view.getId() == R.id.c17) {
            if (checked){
                list.add(c17.getText().toString());
            }

        } else if (view.getId() == R.id.c18) {
            if (checked){
                list.add(c18.getText().toString());
            }

        }

    }
}