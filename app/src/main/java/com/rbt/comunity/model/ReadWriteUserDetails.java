package com.rbt.comunity.model;

public class ReadWriteUserDetails {
    public String userName, email;

    // Constructor
    public ReadWriteUserDetails(){};

    public ReadWriteUserDetails(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
