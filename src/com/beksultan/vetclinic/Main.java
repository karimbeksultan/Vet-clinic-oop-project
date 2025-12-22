package com.beksultan.vetclinic;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Everyone!\nThis is my vetclinic application for my oop course");
        Pet honeybadger = new Pet("VipKazakh", "Honeybadger", 4, Gender.FEMALE, false);
        Pet pug = new Pet("Megatron", "pug", 7, Gender.MALE, true);
        Owner beksultan = new Owner("Beksultan", "+77777777777", "254524@astanait.edu.kz", "Tole BI 46 st", Gender.MALE);
        Veterenarian aidana = new Veterenarian("Aidana", "+77766666777", " Every animal", 10);

        System.out.println("Testing Pet Class:");
        System.out.println(honeybadger.getName() + " is " + honeybadger.getAge() + " years old");
        System.out.println("Pet's name " + honeybadger.getName());
        if (honeybadger.isVaccinated()){
            System.out.println(honeybadger.getName() + " is vaccinated");
        }
        else{
            System.out.println(honeybadger.getName() + " isn't vaccinated");
        }
        System.out.println(honeybadger.getName() + "'s Gender is " + honeybadger.getGender());
        honeybadger.setName("VipKazakh");
        honeybadger.setAge(6);
        honeybadger.vaccinate();
        System.out.println(honeybadger.getName() + " is " + honeybadger.getAge() + " years old");
        honeybadger.birthday();
        System.out.println(honeybadger.getName() + " is " + honeybadger.getAge() + " years old");
        System.out.println("Pet's name " + honeybadger.getName());
        honeybadger.vaccinate();
        System.out.println(honeybadger);

        System.out.println("\nTesting Owner Class:");
        beksultan.addpet(honeybadger);
        beksultan.petList();
        beksultan.addpet(pug);
        beksultan.petList();
        System.out.println("Owner's name is " + beksultan.getName());
        System.out.println(beksultan.getName() + "'s home adress is " + beksultan.getAddress());
        System.out.println(beksultan.getName() + "'s email is " + beksultan.getEmail());
        System.out.println(beksultan.getName() + "'s phone number is " + beksultan.getPhone());
        System.out.println(beksultan);

        System.out.println("\nTesting Veterenarian Class:");
        System.out.println("Veterenarian name is " + aidana.getName());
        System.out.println(aidana.getName() + "'s Phone Number is " + aidana.getPhone());
        System.out.println(aidana.getName() + " is on work: " + aidana.isOnWork());
        aidana.startWork();
        System.out.println(aidana.getName() + " is on work: " + aidana.isOnWork());
        aidana.addRating(5);
        aidana.addRating(4);
        aidana.addRating(6);
        System.out.println(aidana.getName() + "'s rating is " +aidana.getRating());
        System.out.println(aidana);
    }
}