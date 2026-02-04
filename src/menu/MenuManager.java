package menu;

import model.*;
import exception.InvalidInputException;
import database.OwnerDAO;
import database.PetDAO;
import database.VeterinarianDAO;
import java.util.List;
import java.util.Scanner;

public class MenuManager implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final OwnerDAO ownerDAO;
    private final PetDAO petDAO;
    private final VeterinarianDAO veterinarianDAO;

    public MenuManager() {
        this.ownerDAO = new OwnerDAO();
        this.petDAO = new PetDAO();
        this.veterinarianDAO = new VeterinarianDAO();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== VET CLINIC MENU ===");
        System.out.println("1 - Show all pets");
        System.out.println("2 - Show all owners");
        System.out.println("3 - Show all veterinarians");
        System.out.println("4 - Add pet");
        System.out.println("5 - Add owner");
        System.out.println("6 - Add veterinarian");
        System.out.println("7 - Vaccinate pet");
        System.out.println("8 - Exit");
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
                case 1 -> showAllPets();
                case 2 -> showAllOwners();
                case 3 -> showAllVeterinarians();
                case 4 -> addPet();
                case 5 -> addOwner();
                case 6 -> addVeterinarian();
                case 7 -> vaccinatePet();
                case 8 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid choice: please choose a valid menu number.");
            }
        }
    }

    private void showAllPets() {
        try {
            List<Pet> pets = petDAO.getAllPets();
            if (pets.isEmpty()) {
                System.out.println("No pets available.");
                return;
            }
            System.out.println("\n=== ALL PETS ===");
            for (Pet pet : pets) {
                System.out.println(pet);
            }
        } catch (Exception e) {
            System.out.println("Error loading pets: " + e.getMessage());
        }
    }

    private void showAllOwners() {
        try {
            List<Owner> owners = ownerDAO.getAllOwners();
            if (owners.isEmpty()) {
                System.out.println("No owners available.");
                return;
            }
            System.out.println("\n=== ALL OWNERS ===");
            for (Owner owner : owners) {
                System.out.println(owner);
                System.out.println("---");
            }
        } catch (Exception e) {
            System.out.println("Error loading owners: " + e.getMessage());
        }
    }

    private void showAllVeterinarians() {
        try {
            List<Veterinarian> vets = veterinarianDAO.getAllVeterinarians();
            if (vets.isEmpty()) {
                System.out.println("No veterinarians available.");
                return;
            }
            System.out.println("\n=== ALL VETERINARIANS ===");
            for (Veterinarian vet : vets) {
                System.out.println(vet);
                System.out.println("---");
            }
        } catch (Exception e) {
            System.out.println("Error loading veterinarians: " + e.getMessage());
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

            Pet pet = new Pet(name, species, age, gender, false);
            boolean success = petDAO.insertPet(pet);

            if (success) {
                System.out.println("✓ Pet has been added successfully.");
            } else {
                System.out.println("✗ Failed to add pet.");
            }
        } catch (InvalidInputException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender: " + e.getMessage());
        } catch (Exception e) {
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

            Owner owner = new Owner(name, phone, email, address, gender);
            boolean success = ownerDAO.insertOwner(owner);

            if (success) {
                System.out.println("✓ Owner has been added successfully.");
            } else {
                System.out.println("✗ Failed to add owner.");
            }
        } catch (InvalidInputException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid gender: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addVeterinarian() {
        try {
            System.out.print("Enter veterinarian name: ");
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

            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine();
            if (!Validating.isValidStr(specialization)) {
                System.out.println("Invalid input: specialization cannot be empty.");
                return;
            }

            System.out.print("Enter experience (years): ");
            String expInput = scanner.nextLine();
            if (!Validating.isNumber(expInput)) {
                System.out.println("Invalid input: experience must be a number.");
                return;
            }
            int experience = Integer.parseInt(expInput);

            Veterinarian vet = new Veterinarian(name, phone, email, specialization, experience);
            boolean success = veterinarianDAO.insertVeterinarian(vet);

            if (success) {
                System.out.println("✓ Veterinarian has been added successfully.");
            } else {
                System.out.println("✗ Failed to add veterinarian.");
            }
        } catch (InvalidInputException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void vaccinatePet() {
        try {
            System.out.print("Enter pet name to vaccinate: ");
            String name = scanner.nextLine();

            if (!Validating.isValidStr(name)) {
                System.out.println("Invalid input: pet name must not be empty.");
                return;
            }

            List<Pet> pets = petDAO.getAllPets();
            boolean found = false;

            for (Pet pet : pets) {
                if (pet.getName().equalsIgnoreCase(name)) {
                    pet.vaccinate();
                    boolean success = petDAO.updatePet(pet);
                    if (success) {
                        System.out.println("✓ " + pet.getName() + " has been vaccinated and updated in database.");
                    } else {
                        System.out.println("✗ Vaccinated but failed to update in database.");
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("✗ Pet not found.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}