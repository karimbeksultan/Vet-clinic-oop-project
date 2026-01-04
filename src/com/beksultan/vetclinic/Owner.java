package com.beksultan.vetclinic;

import java.util.ArrayList;

public class Owner {
    private String name;
    private String phone;
    private Gender gender;
    private String email;
    private String address;
    private ArrayList<Pet> pets;

    public Owner(String name, String phone, String email, String address,  Gender gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.pets = new ArrayList<>();

    }

    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}
    public Gender getGender() {return gender;}
    public ArrayList<Pet> getPets() {return pets;}

    public void setName(String name) {
        if (Validating.isValidStr(name)) {
            this.name = name;
        }
        else {
            throw new IllegalArgumentException("Name is invalid, please try again with another name!");
        }
    }
    public void setPhone(String phone) {
        if (Validating.isValidStr(phone) && Validating.isNumber(phone)) {
            this.phone = phone;
        }
        else {
            throw new IllegalArgumentException("Phone is invalid, please try again with another number!");
        }
    }
    public void setEmail(String email) {
        if (Validating.isValidStr(email) &&  email.contains("@")) {
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email is invalid, please try again with another email!");
        }
    }
    public void setAddress(String address) {
        if (Validating.isValidStr(address)) {
            this.address = address;
        }
    }
    public void setGender(Gender gender) {
        if (gender != null) {
            this.gender = gender;
        }
        else {
            throw new IllegalArgumentException("Gender is invalid, please try again with another gender!");
        }
    }

    public void addpet(Pet pet) {pets.add(pet);}
    public void removepet(Pet pet) {pets.remove(pet);}

    public void petList() {
        System.out.println(name + "'s pets list:");
        for (Pet pet : pets) {
            System.out.println(pet.getName() + " " + pet.getSpecies() + " " + pet.getAge());
        }
    }

    @Override
    public String toString() {
        return name + "\nPhone number: " + phone + "\nEmail: " + email + "\nAddress: " + address + "\nGender: " + gender;
    }
}