package com.beksultan.vetclinic;

public class Veterinarian {
    private String name;
    private String phone;
    private String speciality;
    private int experienceYears;
    private boolean onWork;
    private double rating;
    private int ratingCount;

    public Veterinarian(String name, String phone, String speciality, int experienceYears) {
        this.name = name;
        this.phone = phone;
        this.speciality = speciality;
        this.experienceYears = experienceYears;
    }

    public void startWork() {
        onWork = true;
        System.out.println(name + " начала работу.");
    }

    public void endWork() {
        onWork = false;
        System.out.println(name + " закончила работу.");
    }

    public void addRating(int value) {
        rating = (rating * ratingCount + value) / (++ratingCount);
        System.out.println("Добавлен рейтинг " + value + ". Средний рейтинг: " + rating);
    }

    @Override
    public String toString() {
        return name + " (" + speciality + ", опыт: " + experienceYears + " лет, рейтинг: " + rating + ")";
    }
}
