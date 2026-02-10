package model;

import exception.InvalidInputException;

import java.math.BigDecimal;

public class Veterenarian implements Treatable {
    private int id;
    private String name;
    private String phone;
    private String animalSpeciality; // home pets, farm animals, exotic animals or all of them
    private int experienceYears;
    private boolean onWork;
    private int rating;
    private int ratingcount;
    private BigDecimal salary;

    public Veterenarian(int id, String name, String phone, String animalspeciality, int experienceyears, boolean onwork) {
        setId(id);
        setName(name);
        setPhone(phone);
        setAnimalSpeciality(animalspeciality);
        setExperienceYears(experienceyears);
        this.onWork = onwork;
        setRating(0, 0);
    }


    public int getId() {return id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getAnimalSpeciality() {return animalSpeciality;}
    public int getExperienceYears() {return experienceYears;}
    public boolean isOnWork() {return onWork;}
    public double getRating() {
        if(ratingcount == 0){
            return 0;
        }
        else{
            return (double) rating / ratingcount;
        }
    }
    public BigDecimal getSalary() {return salary;}

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
    public void setOnWork(boolean onWork){this.onWork = onWork;}
    public void setExperienceYears(int experienceyears) {
        if (experienceYears >= 0) {
            this.experienceYears = experienceyears;
        }
        else {
            throw new IllegalArgumentException("Experience years is invalid!");
        }
    }
    public void setAnimalSpeciality(String animalspeciality){
        if (Validating.isValidStr(animalspeciality)) {
            this.animalSpeciality = animalspeciality;
        }
        else {
            throw new IllegalArgumentException("Animal Speciality is invalid");
        }
    }
    public void setRating(int rating, int ratingcount){
        if (rating >= 0 && ratingcount >= 0) {
            this.rating = rating;
            this.ratingcount = ratingcount;
        }
    }


    public void startWork(){this.onWork = true;}
    public void endWork(){this.onWork = false;}
    public void addRating(int rate){
        if (rate >= 1 & rate <= 5){
            this.rating += rate;
            this.ratingcount += 1;
            System.out.println("Rating changed!");
        }
        else{
            throw new InvalidInputException("Rating is invalid! Number should be between 1 and 5!");
        }

    }

    public void setSalary(BigDecimal salary) {
        if (salary == null) {
            throw new IllegalArgumentException("Salary cannot be null");
        }
        if (salary.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Salary cannot be negative: " + salary);
        }
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + name + "(" + animalSpeciality + " veterinarian with " + experienceYears + " years of experience)\nRating: " + getRating() + "\nCurrently at work: " + onWork;
    }

    @Override
    public void treat(Pet pet, String treatment) {
        if (pet == null) throw new IllegalArgumentException("Pet cannot be null");
        if (treatment == null || treatment.isBlank())
            throw new IllegalArgumentException("Treatment cannot be empty");
        System.out.println("Dr. " + getName() + " treats " + pet.getName() + ": " + treatment);
    }

}