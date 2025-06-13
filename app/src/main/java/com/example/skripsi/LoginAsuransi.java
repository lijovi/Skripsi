package com.example.skripsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginAsuransi extends AppCompatActivity {

    FirebaseDatabase database;
    TextInputLayout username, password;
    Button btnMasuk;
    TextView masukNasabah;
    DatabaseReference reference;
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
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("company");
        Asuransi asuransi = new Asuransi(1, "aca", "aca@gmail.com", "aca1234", "ACA", "1234567890");
        Asuransi asuransi2 = new Asuransi(2, "etiqa", "etiqa@gmail.com", "etiqa5678", "ETIQA", "081234567890");
        String newusername1 = "aca";
        String newusername2 = "etiqa";
        reference.child(newusername1).setValue(asuransi);
        reference.child(newusername2).setValue(asuransi2);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnMasuk = findViewById(R.id.btnMasuk);
        masukNasabah = findViewById(R.id.masukNasabah);
//        asuransi = (Asuransi) getIntent().getSerializableExtra("company")

//        username.setHint("Input Username");
        username.setPlaceholderText("Input Username");
        password.setPlaceholderText("Input Password");

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUsername() | !validatePassword()){

                } else {
                    cek();
                }
            }
        });

        masukNasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAsuransi.this, Login.class);
                startActivity(intent);
            }
        });
    }

    //    CEK USERNAME
    private Boolean validateUsername(){
        String Username = username.getEditText().getText().toString();

        if (Username.isEmpty()){
            username.setError("Username Cannot Be Empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    //    CEK PASSWORD
    private Boolean validatePassword(){
        String Password = password.getEditText().getText().toString();

        if (Password.isEmpty()){
            password.setError("Password Cannot Be Empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private void cek(){
        String Username = username.getEditText().getText().toString().trim();
        String Password = password.getEditText().getText().toString().trim();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("company");
        Query checkData = database.orderByChild("companyUsername").equalTo(Username);

        checkData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    username.setError(null);
                    String PasswordFromDB = snapshot.child(Username).child("companyPassword").getValue(String.class);
                    String Email = snapshot.child(Username).child("companyEmail").getValue(String.class);
                    String Name = snapshot.child(Username).child("companyName").getValue(String.class);
                    String VirtualAccount = snapshot.child(Username).child("companyVirtualAccount").getValue(String.class);
                    if (Objects.equals(PasswordFromDB, Password)){
                        username.setError(null);
                        Intent intent = new Intent(getApplicationContext(), HomePageAsuransi.class);
                        CompanySession.getInstance().setNama(Name);
                        CompanySession.getInstance().setEmail(Email);
                        CompanySession.getInstance().setUsername(Username);
                        CompanySession.getInstance().setVirtualAccount(VirtualAccount);
                        startActivity(intent);
                    } else {
                        password.setError("Incorrect Password");
                        password.requestFocus();
                    }
                } else {
                    username.setError("User does not exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}