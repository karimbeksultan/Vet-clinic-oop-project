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

    public void setName(String name) {this.name = name;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setEmail(String email) {this.email = email;}
    public void setAddress(String address) {this.address = address;}
    public void setGender(Gender gender) {this.gender = gender;}

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