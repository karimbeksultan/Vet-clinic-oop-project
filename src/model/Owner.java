package model;

import exception.InvalidInputException;
import java.util.ArrayList;

public class Owner extends Person implements Servable {
    private int id;
    private String address;
    private ArrayList<Pet> pets = new ArrayList<>();

    // Конструктор без ID
    public Owner(String name, String phone, String email, String address, Gender gender)
            throws InvalidInputException {
        this(0, name, phone, email, address, gender);
    }

    // Конструктор с ID
    public Owner(int id, String name, String phone, String email, String address, Gender gender)
            throws InvalidInputException {
        super(name, phone, email, gender);
        this.id = id;
        setAddress(address);
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAddress() { return address; }

    public ArrayList<Pet> getPets() { return pets; }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void setAddress(String address) throws InvalidInputException {
        if (!Validating.isValidStr(address))
            throw new InvalidInputException("Address is invalid");
        this.address = address;
    }

    @Override
    public String getRole() {
        return "Owner";
    }

    @Override
    public void serve() {
        System.out.println(name + " takes care of their pets!");
    }

    @Override
    public String toString() {
        return "ID: " + id + " - " + name + " (Owner)\nPhone: " + phone +
                "\nEmail: " + email + "\nAddress: " + address + "\nGender: " + gender;
    }
}