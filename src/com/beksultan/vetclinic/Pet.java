package com.beksultan.vetclinic;

public class Pet {
    private String name;
    private String species;
    private int age;
    private Gender gender;
    private boolean vaccinated;

    public Pet(String name, String species, int age, Gender gender, boolean vaccinated) {
        setName(name);
        setSpecies(species);
        setAge(age);
        setGender(gender);
        this.vaccinated = vaccinated;
    }
    public String getName() {return name;}
    public String getSpecies() {return species;}
    public int getAge() {return age;}
    public Gender getGender() {return gender;}
    public boolean isVaccinated() {return vaccinated;}

    public void setName(String name) {
        if (Validating.isValidStr(name)) {
            this.name = name;
        }
        else {
            throw new IllegalArgumentException("Pet name is invalid, please try again with another name!");
        }
    }
    public void setSpecies(String species) {
        if (Validating.isValidStr(species)) {
            this.species = species;
        }
        else {
            throw new IllegalArgumentException("Species is invalid, please try again with another species!");
        }
    }
    public void setAge(int age) {
        if (age >= 0 && age <= 100) {
            this.age = age;
        }
        else {
            throw new IllegalArgumentException("Age is invalid, please try again with another age!");
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

    public void setVaccinated(boolean vaccinated) {this.vaccinated = vaccinated;}

    public void birthday(){
        this.age++;
        System.out.printf("%s turned %d!\n", name, age);
    }

    public void vaccinate(){
        if (!vaccinated){
            this.vaccinated = true;
            System.out.printf("%s has been vaccinated!\n", name);
        }
        else{
            System.out.printf("%s is already vaccinated!\n", name);
        }
    }

    @Override
    public String toString() {
        return name + "\nSpecies: " + species + "\n" + age + " years old\nGender: " + gender + "\nVaccinated: " + vaccinated;
    }
}