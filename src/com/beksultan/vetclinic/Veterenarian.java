package com.beksultan.vetclinic;

public class Veterenarian extends Person{
    private String name;
    private String phone;
    private String AnimalSpeciality;
    private int Experience;
    private boolean onWork;
    private int rating;
    private int ratingcount;

    public Veterenarian(String name, String phone, String email, String animalSpeciality, int experienceYears) {
        super(name, phone, email, Gender.FEMALE);
        this.name = name;
        this.phone = phone;
        this.AnimalSpeciality = animalSpeciality;
        this.Experience = experienceYears;
        this.onWork = false;

        setRating(0, 0);
    }




    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getAnimalSpeciality() {return AnimalSpeciality;}
    public int getExperienceYears() {return Experience;}
    public boolean isOnWork() {return onWork;}
    public double getRating() {
        if(ratingcount == 0){
            return 0;
        }
        else{
            return (double) rating / ratingcount;
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
        if (Experience >= 0) {
            this.Experience = experienceyears;
        }
        else {
            throw new IllegalArgumentException("Experience years is invalid!");
        }
    }
    public void setAnimalSpeciality(String animalspeciality){
        if (Validating.isValidStr(animalspeciality)) {
            this.AnimalSpeciality = animalspeciality;
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
            System.out.println("Invalid rating");
        }

    }

    @Override
    public String work() {
            return name + " Is treating animals: " + AnimalSpeciality;
}

    @Override
    public String toString() {
        return name + "(" + AnimalSpeciality + " veterinarian with " + Experience + " years of experience)\nRating: " + getRating() + "\nCurrently at work: " + onWork;
         }
}
