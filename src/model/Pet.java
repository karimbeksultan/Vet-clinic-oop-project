package model;

import exception.InvalidInputException;

public class Pet implements Servable {
    private String name;
    private String species;
    private int age;
    private Gender gender;
    private boolean vaccinated;

    public Pet(String name, String species, int age, Gender gender, boolean vaccinated)
            throws InvalidInputException {
        setName(name);
        setSpecies(species);
        setAge(age);
        this.gender = gender;
        this.vaccinated = vaccinated;
    }

    // Getter методы
    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setName(String name) throws InvalidInputException {
        if (!Validating.isValidStr(name))
            throw new InvalidInputException("Pet name is invalid");
        this.name = name;
    }

    public void setSpecies(String species) throws InvalidInputException {
        if (!Validating.isValidStr(species))
            throw new InvalidInputException("Species is invalid");
        this.species = species;
    }

    public void setAge(int age) throws InvalidInputException {
        if (age < 0 || age > 100)
            throw new InvalidInputException("Age must be 0–100");
        this.age = age;
    }

    // Метод должен быть public!
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
        return name + " (" + species + ", " + age + " yo, " + gender +
                ", vaccinated=" + vaccinated + ")";
    }
}