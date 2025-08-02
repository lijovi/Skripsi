package com.example.skripsi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class ProfileAsuransi extends AppCompatActivity {

    Button btnHome, btnBack;
    TextView namaPerusahaan, username, email, virtualAccount, ubahPassword, ubahBahasa, FAQ, syaratDanKetentuan, keluar;
    FirebaseAuth mAuth;
    String Username, Nama, Email, VirtualAccount, ProfilePicture;
    int Id;
    ImageButton profile;
//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    String uid = auth.getCurrentUser().getUid().toString();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("company");
//    Asuransi asuransi;
    FirebaseStorage storage;
    StorageReference storageRef;
    int PICK_IMAGE_REQUEST = 100;

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
        profile = findViewById(R.id.profile);

        ubahPassword = findViewById(R.id.ubahPassword);
        ubahBahasa = findViewById(R.id.ubahBahasa);
        FAQ = findViewById(R.id.FAQ);
        syaratDanKetentuan = findViewById(R.id.syaratDanKetentuan);
        keluar = findViewById(R.id.keluar);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("profile_insurance");

//        Ambil Data
        Username = CompanySession.getInstance().getUsername();
        Nama = CompanySession.getInstance().getNama();
        Email = CompanySession.getInstance().getEmail();
        VirtualAccount = CompanySession.getInstance().getVirtualAccount();
        Id = CompanySession.getInstance().getId();

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

        ubahBahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mAuth = FirebaseAuth.getInstance();

        ubahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ResetPassword();
//                sendEmail(Email, "myapp://open/UbahPasswordAsuransi");
                Intent intent = new Intent(getApplicationContext(), UbahPasswordAsuransi.class);
                startActivity(intent);
            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
            }
        });

        syaratDanKetentuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ubahBahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate popup layout
                View popupView = getLayoutInflater().inflate(R.layout.pop_up_ubah_bahasa, null);

                // Create dialog
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ProfileAsuransi.this);
                builder.setView(popupView);
                final androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                // Bind UI components
                ImageView btnClose = popupView.findViewById(R.id.btnClose);
                LinearLayout btnEnglish = popupView.findViewById(R.id.btnEnglish);
                LinearLayout btnIndo = popupView.findViewById(R.id.btnIndo);

                btnClose.setOnClickListener(close -> dialog.dismiss());

                btnEnglish.setOnClickListener(lang -> {
                    LocaleHelper.setLocale(ProfileAsuransi.this, "en");
                    recreate();
                    dialog.dismiss();
                });

                btnIndo.setOnClickListener(lang -> {
                    LocaleHelper.setLocale(ProfileAsuransi.this, "id");
                    recreate();
                    dialog.dismiss();
                });

                dialog.show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
            }
        });

//    private void ResetPassword() {
//        mAuth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Intent intent = new Intent(ProfileAsuransi.this, LoginAsuransi.class);
//                startActivity(intent);
//            }
//        });
//    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profile.setImageURI(imageUri);

            StorageReference fileRef = storageRef.child(Id + ".jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadurl = uri.toString();

                    reference.child(String.valueOf(Id)).setValue(downloadurl).addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Profile picture has been changed", Toast.LENGTH_SHORT).show();
                    });
                });
            });
        }
    }

//    private void sendEmail (String send_email, String send_link){
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:" + send_email));
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Ubah Password");
//        intent.putExtra(Intent.EXTRA_TEXT, send_link);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(Intent.createChooser(intent, ""));
//            Toast.makeText(this, "Email Sent!", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Email tidak terkirim!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void showDialog() {
//        Dialog dialog = new Dialog(this);
////        dialog.setContentView();
//    }

}

