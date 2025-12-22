package com.beksultan.vetclinic;

public class Veterenarian {
    private String name;
    private String phone;
    private String AnimalSpeciality; // home pets, farm animals, exotic animals or all of them
    private int Experience;
    private boolean onWork;
    private int rating;
    private int ratingcount;

    public Veterenarian(String name, String phone, String AnimalSpeciality, int ExperienceYears) {
        this.name = name;
        this.phone = phone;
        this.AnimalSpeciality = AnimalSpeciality;
        this.Experience = Experience;
        this.onWork = false;
        this.rating = 0;
        this.ratingcount = 0;
    }

    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getAnimalSpeciality() {return AnimalSpeciality;}
    public int getExperience() {return Experience;}
    public boolean isOnWork() {return onWork;}
    public double getRating() {
        if(ratingcount == 0){
            return 0;
        }
        else{
            return (double) rating / ratingcount;
        }
    }

    public void setname(String name){this.name = name;}
    public void setphone(String phone){this.phone = phone;}
    public void setOnWork(boolean onWork){this.onWork = onWork;}
    public void setExperience(int experience) {Experience = experience;}
    public void setAnimalSpeciality(String AnimalSpeciality){this.AnimalSpeciality = AnimalSpeciality;}
    public void setRating(int rating){this.rating = rating;}
    public void setRatingcount(int ratingcount){this.ratingcount = ratingcount;}

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
    public String toString() {
        return name + "(" + AnimalSpeciality + " veterinarian with " + Experience + " years of experience)\nRating: " + getRating() + "\nCurrently at work: " + onWork;
    }
}
