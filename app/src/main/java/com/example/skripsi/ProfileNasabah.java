package com.example.skripsi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ProfileNasabah extends AppCompatActivity {

    TextView nama, email, noTelp, keluar, syaratDanKetentuan, FAQ, ubahBahasa, ubahPassword;
    Button btnHome, btnInfo, btnNotifikasi;
    ImageView profile;
//    FirebaseAuth mAuth;
    DatabaseReference referenceHealth = FirebaseDatabase.getInstance().getReference("clientHealth");
    DatabaseReference referenceTravel = FirebaseDatabase.getInstance().getReference("clientTravel");
    FirebaseStorage storage;
    StorageReference storageRef;
    int PICK_IMAGE_REQUEST = 100;
    String NIK;
    ActivityResultLauncher<Intent> gallery;

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
        syaratDanKetentuan = findViewById(R.id.syaratDanKetentuan);

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
                // Inflate your popup layout
                LayoutInflater inflater = LayoutInflater.from(ProfileNasabah.this);
                View popupView = inflater.inflate(R.layout.pop_up_ubah_bahasa, null);

                // Build AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileNasabah.this);
                builder.setView(popupView);
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                // Bind UI elements from the popup
                ImageView btnClose = popupView.findViewById(R.id.btnClose);
                LinearLayout btnEnglish = popupView.findViewById(R.id.btnEnglish);
                LinearLayout btnIndo = popupView.findViewById(R.id.btnIndo);

                btnClose.setOnClickListener(close -> dialog.dismiss());

                btnEnglish.setOnClickListener(lang -> {
                    LocaleHelper.setLocale(ProfileNasabah.this, "en");
                    recreate(); // recreate activity to apply language
                    dialog.dismiss();
                });

                btnIndo.setOnClickListener(lang -> {
                    LocaleHelper.setLocale(ProfileNasabah.this, "id");
                    recreate(); // recreate activity to apply language
                    dialog.dismiss();
                });

                dialog.show();
            }
        });

        referenceHealth.child(NIK).child("profile").get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()){
                String imageuri = snapshot.getValue(String.class);

                Glide.with(this).load(imageuri).circleCrop().into(profile);
            }
        });

        referenceTravel.child(NIK).child("profile").get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()){
                String imageuri = snapshot.getValue(String.class);

                Glide.with(this).load(imageuri).circleCrop().into(profile);
            }
        });

        gallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null){
                Uri selectedImage = result.getData().getData();
                uploadToCloudinary(selectedImage);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallery.launch(intent);
            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FAQ.class);
                startActivity(intent);
            }
        });

        syaratDanKetentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SyaratDanKetentuan.class);
                startActivity(intent);
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

                Glide.with(ProfileNasabah.this).load(imageuri).circleCrop().into(profile);

                referenceHealth.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            if (Objects.equals(NIK, snapshot.child(NIK).child("nik"))){
                                FirebaseDatabase.getInstance().getReference("clientHealth").child(NIK).child("profile").setValue(imageuri);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                referenceTravel.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            if (Objects.equals(NIK, snapshot.child(NIK).child("nik"))){
                                FirebaseDatabase.getInstance().getReference("clientTravel").child(NIK).child("profile").setValue(imageuri);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//            Uri imageUri = data.getData();
//            profile.setImageURI(imageUri);
//
//            StorageReference fileRef = storageRef.child(NIK + ".jpg");
//
//            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
//                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                    String downloadurl = uri.toString();
//
////                    reference.child(String.valueOf(NIK)).setValue(downloadurl).addOnSuccessListener(aVoid -> {
////                        Toast.makeText(this, "Profile picture has been changed", Toast.LENGTH_SHORT).show();
////                    });
//                });
//            });
//        }
//
//    }
}