package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FAQ extends AppCompatActivity {

    Button plus1, plus2, plus3, plus4, plus5, plus6, plus7;
    TextView q1, q2, q3, q4, q5, q6, q7;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_faq);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        plus1 = findViewById(R.id.plus1);
        plus2 = findViewById(R.id.plus2);
        plus3 = findViewById(R.id.plus3);
        plus4 = findViewById(R.id.plus4);
        plus5 = findViewById(R.id.plus5);
        plus6 = findViewById(R.id.plus6);
        plus7 = findViewById(R.id.plus7);

        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);
        q6 = findViewById(R.id.q6);
        q7 = findViewById(R.id.q7);

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileNasabah.class);
                startActivity(intent);
            }
        });

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q1.getVisibility() == View.GONE){
                    plus1.setBackground(getDrawable(R.drawable.minus_hitam));
                    q1.setVisibility(View.VISIBLE);
                } else {
                    plus1.setBackground(getDrawable(R.drawable.plus_hitam));
                    q1.setVisibility(View.GONE);
                }
            }
        });

        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q2.getVisibility() == View.GONE){
                    plus2.setBackground(getDrawable(R.drawable.minus_hitam));
                    q2.setVisibility(View.VISIBLE);
                } else {
                    plus2.setBackground(getDrawable(R.drawable.plus_hitam));
                    q2.setVisibility(View.GONE);
                }
            }
        });

        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q3.getVisibility() == View.GONE){
                    plus3.setBackground(getDrawable(R.drawable.minus_hitam));
                    q3.setVisibility(View.VISIBLE);
                } else {
                    plus3.setBackground(getDrawable(R.drawable.plus_hitam));
                    q3.setVisibility(View.GONE);
                }
            }
        });

        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q4.getVisibility() == View.GONE){
                    plus4.setBackground(getDrawable(R.drawable.minus_hitam));
                    q4.setVisibility(View.VISIBLE);
                } else {
                    plus4.setBackground(getDrawable(R.drawable.plus_hitam));
                    q4.setVisibility(View.GONE);
                }
            }
        });

        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q5.getVisibility() == View.GONE){
                    plus5.setBackground(getDrawable(R.drawable.minus_hitam));
                    q5.setVisibility(View.VISIBLE);
                } else {
                    plus5.setBackground(getDrawable(R.drawable.plus_hitam));
                    q5.setVisibility(View.GONE);
                }
            }
        });

        plus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q6.getVisibility() == View.GONE){
                    plus6.setBackground(getDrawable(R.drawable.minus_hitam));
                    q6.setVisibility(View.VISIBLE);
                } else {
                    plus6.setBackground(getDrawable(R.drawable.plus_hitam));
                    q6.setVisibility(View.GONE);
                }
            }
        });

        plus7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q7.getVisibility() == View.GONE){
                    plus7.setBackground(getDrawable(R.drawable.minus_hitam));
                    q7.setVisibility(View.VISIBLE);
                } else {
                    plus7.setBackground(getDrawable(R.drawable.plus_hitam));
                    q7.setVisibility(View.GONE);
                }
            }
        });



    }
}