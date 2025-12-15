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

    public String getName() { return name; }
    public int getAge() { return age; }
    public boolean isVaccinated() { return vaccinated; }

    public void vaccinate() {
        if (vaccinated) {
            System.out.println(name + " уже привит.");
        } else {
            vaccinated = true;
            System.out.println(name + " успешно привит!");
        }
    }

    public void birthday() {
        age++;
        System.out.println("У " + name + " сегодня день рождения! Теперь ему " + age + " лет.");
    }

    @Override
    public String toString() {
        return name + " (" + species + ", " + age + " лет, " + gender + ", привит: " + vaccinated + ")";
    }
}
