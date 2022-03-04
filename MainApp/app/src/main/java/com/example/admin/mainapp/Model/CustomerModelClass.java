package com.example.admin.mainapp.Model;

/**
 * Created by Admin on 19-01-2018.
 */

public class CustomerModelClass {
    private String name,email,phone,address,id;

    public CustomerModelClass()
    {

    }

    public CustomerModelClass(String name, String email, String phone, String address,String cust_id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.id = cust_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getId() { return id; }
}
