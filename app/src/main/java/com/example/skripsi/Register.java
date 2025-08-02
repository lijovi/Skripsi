package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    RadioGroup tipeAsuransi;
//    String selectedTipe;
    RadioButton tipe;
    Button btnLanjut;
//    String pilihan;
    int pilihan;
    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//        Spinner Pilih Perusahaan
        spinner = findViewById(R.id.company);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.perusahaan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //        Pilih Tipe Asuransi
        tipeAsuransi = findViewById(R.id.tipeAsuransi);

        btnLanjut = findViewById(R.id.btnLanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = tipeAsuransi.getCheckedRadioButtonId();
                tipe = findViewById(id);
                String Tipe = tipe.getText().toString();
                if (id == R.id.travel){
                    Intent intent1 = new Intent(getApplicationContext(), RegistrasiTravel.class);
                    intent1.putExtra("tipePerusahaan", pilihan);
                    startActivity(intent1);
                } else if (id == R.id.health) {
                    Intent intent2 = new Intent(getApplicationContext(), RegistrasiHealth.class);
                    intent2.putExtra("tipePerusahaan", pilihan);
                    startActivity(intent2);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String choice = parent.getItemAtPosition(position).toString();
//        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_SHORT).show();
//        pilihan = choice;
//        String choice = parent.getItemAtPosition(position).toString();
//        pilihan = choice;
        pilihan = position+1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}