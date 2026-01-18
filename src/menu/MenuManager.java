package menu;

import model.*;
import exception.InvalidInputException;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {
    private final ArrayList<Person> persons = new ArrayList<>();
    private final ArrayList<Pet> pets = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public MenuManager() {
        try {
            persons.add(new Owner("Beksultan", "+77777777777", "email@x.kz", "Astana", Gender.MALE));
            pets.add(new Pet("Honeybadger", "Badger", 4, Gender.FEMALE, false));
        } catch (InvalidInputException ignored) {}
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== VET CLINIC MENU ===");
        System.out.println("1 - Show pets");
        System.out.println("2 - Show owners");
        System.out.println("3 - Add pet");
        System.out.println("4 - Add owner");
        System.out.println("5 - Vaccinate pet");
        System.out.println("6 - Exit");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine();
            if (!Validating.isNumber(input)) {
                System.out.println("Invalid input: please enter a number.");
                continue;
            }

            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> showPets();
                case 2 -> showOwners();
                case 3 -> addPet();
                case 4 -> addOwner();
                case 5 -> vaccinatePet();
                case 6 -> running = false;
                default -> System.out.println("Invalid choice: please choose a valid menu number.");
            }
        }
    }

    private void showPets() {
        if (pets.isEmpty()) {
            System.out.println("No pets available.");
            return;
        }
        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }

    private void showOwners() {
        boolean found = false;
        for (Person p : persons) {
            if (p instanceof Owner) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No owners available.");
        }
    }

    private void addPet() {
        try {
            System.out.print("Enter pet name: ");
            String name = scanner.nextLine();
            if (!Validating.isValidStr(name)) {
                System.out.println("Invalid input: name cannot be empty.");
                return;
            }

            System.out.print("Enter species: ");
            String species = scanner.nextLine();
            if (!Validating.isValidStr(species)) {
                System.out.println("Invalid input: species cannot be empty.");
                return;
            }

            System.out.print("Enter age: ");
            String ageInput = scanner.nextLine();
            if (!Validating.isNumber(ageInput)) {
                System.out.println("Invalid input: age must be a number.");
                return;
            }
            int age = Integer.parseInt(ageInput);

            System.out.print("Enter gender (MALE/FEMALE): ");
            String genderStr = scanner.nextLine().toUpperCase();
            if (!genderStr.equals("MALE") && !genderStr.equals("FEMALE")) {
                System.out.println("Invalid input: gender must be MALE or FEMALE.");
                return;
            }
            Gender gender = Gender.valueOf(genderStr);

            pets.add(new Pet(name, species, age, gender, false));
            System.out.println("model.Pet has been added successfully.");
        } catch (InvalidInputException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addOwner() {
        try {
            System.out.print("Enter owner name: ");
            String name = scanner.nextLine();
            if (!Validating.isValidStr(name)) {
                System.out.println("Invalid input: name cannot be empty.");
                return;
            }

            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();
            if (!Validating.isValidStr(phone) || !Validating.isNumber(phone)) {
                System.out.println("Invalid input: phone must contain only numbers.");
                return;
            }

            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            if (!Validating.isValidStr(email) || !email.contains("@")) {
                System.out.println("Invalid input: email must contain '@'.");
                return;
            }

            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            if (!Validating.isValidStr(address)) {
                System.out.println("Invalid input: address cannot be empty.");
                return;
            }

            System.out.print("Enter gender (MALE/FEMALE): ");
            String genderStr = scanner.nextLine().toUpperCase();
            if (!genderStr.equals("MALE") && !genderStr.equals("FEMALE")) {
                System.out.println("Invalid input: gender must be MALE or FEMALE.");
                return;
            }
            Gender gender = Gender.valueOf(genderStr);

            persons.add(new Owner(name, phone, email, address, gender));
            System.out.println("model.Owner has been added successfully.");
        } catch (InvalidInputException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void vaccinatePet() {
        System.out.print("Enter pet name to vaccinate: ");
        String name = scanner.nextLine();
        if (!Validating.isValidStr(name)) {
            System.out.println("Invalid input: pet name must not be empty.");
            return;
        }

        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                pet.vaccinate();
                return;
            }
        }
        System.out.println("model.Pet not found.");
    }
}
