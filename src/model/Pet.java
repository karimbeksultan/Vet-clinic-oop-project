package model;

import exception.InvalidInputException;

public class Pet implements Servable {
    private int id;
    private String name;
    private String species;
    private int age;
    private Gender gender;
    private boolean vaccinated;
    private int ownerId; // ссылка на владельца

    // Конструктор без ID (для создания нового)
    public Pet(String name, String species, int age, Gender gender, boolean vaccinated)
            throws InvalidInputException {
        this(0, name, species, age, gender, vaccinated);
    }

    // Конструктор с ID (для загрузки из базы)
    public Pet(int id, String name, String species, int age, Gender gender, boolean vaccinated)
            throws InvalidInputException {
        this.id = id;
        setName(name);
        setSpecies(species);
        setAge(age);
        this.gender = gender;
        this.vaccinated = vaccinated;
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) throws InvalidInputException {
        if (!Validating.isValidStr(name))
            throw new InvalidInputException("Pet name is invalid");
        this.name = name;
    }

    public String getSpecies() { return species; }
    public void setSpecies(String species) throws InvalidInputException {
        if (!Validating.isValidStr(species))
            throw new InvalidInputException("Species is invalid");
        this.species = species;
    }

    public int getAge() { return age; }
    public void setAge(int age) throws InvalidInputException {
        if (age < 0 || age > 100)
            throw new InvalidInputException("Age must be 0–100");
        this.age = age;
    }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public boolean isVaccinated() { return vaccinated; }
    public void setVaccinated(boolean vaccinated) { this.vaccinated = vaccinated; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public void vaccinate() {
        if (!vaccinated) {
            vaccinated = true;
            System.out.println(name + " has been vaccinated!");
        } else {
            System.out.println(name + " is already vaccinated!");
        }
    }

    @Override
    public void serve() {
        System.out.println(name + " is being cared for!");
    }

    @Override
    public String toString() {
        return "ID: " + id + " - " + name + " (" + species + ", " + age + " yo, " + gender +
                ", vaccinated=" + vaccinated + ", ownerId=" + ownerId + ")";
    }
}