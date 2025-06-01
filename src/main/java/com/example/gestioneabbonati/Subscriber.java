package com.example.gestioneabbonati;

public class Subscriber {
    private String code, magazineName, secondName, name, address, gender, city;

    public Subscriber(String code, String magazineName, String secondName, String name, String address, String gender, String city) {
        this.code = code;
        this.magazineName = magazineName;
        this.secondName = secondName;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return code + ";" + magazineName + ";" + secondName + ";" + name + ";" + address + ";" + gender + ";" + city + "\n";
    }
}
