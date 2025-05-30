package com.example.skripsi;

import java.io.Serializable;

public class Asuransi implements Serializable {
    int companyId;
    String companyUsername;
    String companyEmail;
    String companyPassword;
    String companyName;
    int companyVirtualAccount;

    public Asuransi(int companyId, String companyUsername, String companyEmail, String companyPassword, String companyName, int companyVirtualAccount) {
        this.companyId = companyId;
        this.companyUsername = companyUsername;
        this.companyEmail = companyEmail;
        this.companyPassword = companyPassword;
        this.companyName = companyName;
        this.companyVirtualAccount = companyVirtualAccount;
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

    public int getCompanyVirtualAccount() {
        return companyVirtualAccount;
    }
}
