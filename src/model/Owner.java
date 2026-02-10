package model;

import java.util.ArrayList;

public class Owner
{
    private int id;
    private String name;
    private String phone;
    private Gender gender;
    private String email;
    private String address;
    private ArrayList<Pet> ownpets;

    public Owner(int id, String name, String phone, String address, String email, Gender gender) {
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setAddress(address);
        setGender(gender);
        this.ownpets = new ArrayList<>();

    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}
    public Gender getGender() {return gender;}
    public ArrayList<Pet> getPets() {return ownpets;}

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        }
        else {
            throw new IllegalArgumentException("Id is already exists, please try again with another id!");
        }
    }

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
        if (Validating.isValidStr(email) && email.contains("@")) {
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

    public void addownpet(Pet pet) {ownpets.add(pet);}
    public void removeownpet(Pet pet) {ownpets.remove(pet);}

    public void petList() {
        System.out.println(name + "'s pets list:");
        for (Pet pet : ownpets) {
            System.out.println(pet);
        }
    }

    @Override
    public String toString() {
        return "(" + id + ") " + name + "\nPhone number: " + phone + "\nEmail: " + email + "\nAddress: " + address + "\nGender: " + gender;
    }
}