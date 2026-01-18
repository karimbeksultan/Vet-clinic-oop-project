package model;


import exception.InvalidInputException;

import java.util.ArrayList;


public class Owner extends Person implements Servable {
    private String address;
    private ArrayList<Pet> pets = new ArrayList<>();

    public Owner(String name, String phone, String email, String address, Gender gender)
            throws InvalidInputException {
        super(name, phone, email, gender);
        setAddress(address);
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    public void setAddress(String address) throws InvalidInputException {
        if (!Validating.isValidStr(address)) throw new InvalidInputException("Address is invalid");
        this.address = address;
    }

    @Override
    public String getRole() { return "model.Owner"; }

    @Override
    public void serve() {
        System.out.println(name + " takes care of their pets!");
    }

    @Override
    public String toString() {
        return name + " (model.Owner)\nPhone: " + phone + "\nEmail: " + email + "\nAddress: " + address;
    }
}

