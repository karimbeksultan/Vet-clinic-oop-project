package com.beksultan.vetclinic;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Pet honeybadger = new Pet("VipKazakh", "Honeybadger", 4, Gender.FEMALE, false);
        Pet pug = new Pet("Megatron", "Pug", 7, Gender.MALE, true);

        Owner beksultan = new Owner("Beksultan", "+77777777777", "254524@astanait.edu.kz",
                "Tole BI 46 st", Gender.MALE);

        Veterenarian aidana = new Veterenarian("Aidana", "+77766666777", "aidana@vet.com",
                "Every animal", 10);

        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(honeybadger);
        pets.add(pug);

        boolean running = true;

        while (running) {
            System.out.println("\n=== VET CLINIC MENU ===");
            System.out.println("1 — Show all pets");
            System.out.println("2 — Show owner info");
            System.out.println("3 — Show veterinarian info");
            System.out.println("4 — Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("\n--- PETS INFORMATION ---");
                    for (Pet pet : pets) {
                        System.out.println(pet);
                    }
                    break;

                case 2:
                    System.out.println("\n--- OWNER INFORMATION ---");
                    System.out.println("Name: " + beksultan.getName());
                    System.out.println("Phone: " + beksultan.getPhone());
                    System.out.println("Email: " + beksultan.getEmail());
                    System.out.println("Address: " + beksultan.getAddress());
                    if (!pets.isEmpty()) {
                        System.out.println("Pets:");
                        beksultan.petList();
                    } else {
                        System.out.println("No pets to show.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- VETERINARIAN INFORMATION ---");
                    System.out.println("Name: " + aidana.getName());
                    System.out.println("Phone: " + aidana.getPhone());
                    System.out.println("Specialty: " + aidana.getAnimalSpeciality());
                    System.out.println("Years of Experience: " + aidana.getExperienceYears());
                    System.out.println("On Work Now: " + aidana.isOnWork());
                    System.out.println("Rating: " + aidana.getRating());
                    break;

                case 4:
                    System.out.println("Exiting application...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Try again.");
            }
        }

        scanner.close();
    }
}

