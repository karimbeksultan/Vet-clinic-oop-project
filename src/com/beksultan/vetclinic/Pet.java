package com.beksultan.vetclinic;

public class Pet {
    private String name;
    private String species;
    private int age;
    private Gender gender;
    private boolean vaccinated;

    public Pet(String name, String species, int age, Gender gender, boolean vaccinated) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.vaccinated = vaccinated;
    }
    public String getName() {return name;}
    public String getSpecies() {return species;}
    public int getAge() {return age;}
    public Gender getGender() {return gender;}
    public boolean isVaccinated() {return vaccinated;}
    public void setName(String name) {this.name = name;}
    public void setSpecies(String species) {this.species = species;}
    public void setAge(int age) {this.age = age;}
    public void setGender(Gender gender) {this.gender = gender;}
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