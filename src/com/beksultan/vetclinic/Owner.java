package com.beksultan.vetclinic;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private String name;
    private String phone;
    private String email;
    private String address;
    private Gender gender;
    private List<Pet> pets = new ArrayList<>();

    public Owner(String name, String phone, String email, String address, Gender gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        System.out.println("Питомец " + pet.getName() + " добавлен владельцу " + name);
    }

    public List<Pet> getPets() { return pets; }

    @Override
    public String toString() {
        return name + " (" + phone + ", " + email + ", " + address + ")";
    }
}
