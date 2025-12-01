package com.example.skripsi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Asuransi implements Serializable {
    int companyId;
    String companyUsername;
    String companyEmail;
    String companyPassword;
    String companyName;
    String companyVirtualAccount;
    String companyPhoneNumber;
    String companyContactPerson;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("company");

    public Asuransi(int companyId, String companyUsername, String companyEmail, String companyPassword, String companyName, String companyVirtualAccount, String companyPhoneNumber, String companyContactPerson) {
        this.companyId = companyId;
        this.companyUsername = companyUsername;
        this.companyEmail = companyEmail;
        this.companyPassword = companyPassword;
        this.companyName = companyName;
        this.companyVirtualAccount = companyVirtualAccount;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyContactPerson = companyContactPerson;
    }

    public Asuransi(){

    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCompanyUsername() {
        return companyUsername;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyVirtualAccount() {
        return companyVirtualAccount;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public String getCompanyContactPerson() {
        return companyContactPerson;
    }

    public void ChangePassword(int id, String password){
        reference.child(String.valueOf(id)).child("companyPassword").setValue(password);
    }

    public void ChangeProfilePicture(int id, String imageuri){
        FirebaseDatabase.getInstance().getReference("company").child(String.valueOf(id)).child("profile").setValue(imageuri);
    }
}
