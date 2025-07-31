package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileNasabah extends AppCompatActivity {

    TextView nama, email, noTelp, keluar, syaratDanKetentuan, FAQ, ubahBahasa, ubahPassword;
    Button btnHome, btnInfo, btnNotifikasi;
    ImageView profile;
//    FirebaseAuth mAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("company");
    FirebaseStorage storage;
    StorageReference storageRef;
    int PICK_IMAGE_REQUEST = 100;
    String NIK;

    // buat ubah bahasa locale
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_nasabah);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        noTelp = findViewById(R.id.noTelp);
        keluar = findViewById(R.id.keluar);
        ubahPassword = findViewById(R.id.ubahPassword);
        ubahBahasa = findViewById(R.id.ubahBahasa);
        profile = findViewById(R.id.profile);
        FAQ = findViewById(R.id.FAQ);

        btnHome = findViewById(R.id.btnHome);
        btnInfo = findViewById(R.id.btnInfo);
        btnNotifikasi = findViewById(R.id.btnNotifikasi);

        String Nama = ClientSession.getInstance().getNama();
        String Email = ClientSession.getInstance().getEmail();
        String NoTelp = ClientSession.getInstance().getNoTelp();
        NIK = ClientSession.getInstance().getNik();

        nama.setText(Nama);
        email.setText(Email);
        noTelp.setText(NoTelp);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePageNasabah.class);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsuranceInfoNasabah.class);
                startActivity(intent);
            }
        });

        btnNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationNasabah.class);
                startActivity(intent);
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        ubahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UbahPasswordNasabah.class);
                startActivity(intent);

//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.setData(Uri.parse("mailto:"));
//                email.setType("text/plain");
//
//                email.putExtra(Intent.EXTRA_EMAIL, Email);
//                email.putExtra(Intent.EXTRA_SUBJECT, "Change Password");
//                email.putExtra(Intent.EXTRA_TEXT, "");

//                mAuth.sendPasswordResetEmail(Email);
            }
        });

        ubahBahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentLang = LocaleHelper.getLanguage(ProfileNasabah.this);
                String newLang = currentLang.equals("en") ? "id" : "en";

                LocaleHelper.setLocale(ProfileNasabah.this, newLang);
                recreate();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.skripsi.FAQ.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profile.setImageURI(imageUri);

            StorageReference fileRef = storageRef.child(NIK + ".jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadurl = uri.toString();

                    reference.child(String.valueOf(NIK)).setValue(downloadurl).addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile picture has been changed", Toast.LENGTH_SHORT).show();
                    });
                });
            });
        }

    }
}