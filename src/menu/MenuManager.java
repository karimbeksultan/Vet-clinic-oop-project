package menu;

import model.*;
import exception.InvalidInputException;
import database.OwnerDAO;
import database.PetDAO;
import database.VeterinarianDAO;
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
        System.out.println("=== PETS ===");
        System.out.println("1  - Show all pets");
        System.out.println("2  - Add pet");
        System.out.println("3  - Update pet");
        System.out.println("4  - Delete pet");
        System.out.println("5  - Search pet by name");
        System.out.println("6  - Search pet by age");
        System.out.println("7  - Search pet by species");
        System.out.println("8  - Search pet by owner name");
        System.out.println("9  - Get pet by ID");

        System.out.println("\n=== OWNERS ===");
        System.out.println("10 - Show all owners");
        System.out.println("11 - Add owner");
        System.out.println("12 - Update owner");
        System.out.println("13 - Delete owner");
        System.out.println("14 - Search owner by name");
        System.out.println("15 - Search owner by phone");
        System.out.println("16 - Get owner by ID");

        System.out.println("\n=== VETERINARIANS ===");
        System.out.println("17 - Show all veterinarians");
        System.out.println("18 - Add veterinarian");
        System.out.println("19 - Update veterinarian");
        System.out.println("20 - Delete veterinarian");
        System.out.println("21 - Search veterinarian by name");
        System.out.println("22 - Search veterinarian by phone");
        System.out.println("23 - Get veterinarian by ID");

        System.out.println("\n=== OTHER ===");
        System.out.println("24 - Vaccinate pet");
        System.out.println("0  - Exit");
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
                // Pets
                case 1 -> petDAO.displayAll();
                case 2 -> addPet();
                case 3 -> updatePet();
                case 4 -> deletePet();
                case 5 -> searchPetByName();
                case 6 -> searchPetByAge();
                case 7 -> searchPetBySpecies();
                case 8 -> searchPetByOwnerName();
                case 9 -> getPetById();

                // Owners
                case 10 -> ownerDAO.displayAll();
                case 11 -> addOwner();
                case 12 -> updateOwner();
                case 13 -> deleteOwner();
                case 14 -> searchOwnerByName();
                case 15 -> searchOwnerByPhone();
                case 16 -> getOwnerById();

                // Veterinarians
                case 17 -> veterinarianDAO.displayAll();
                case 18 -> addVeterinarian();
                case 19 -> updateVeterinarian();
                case 20 -> deleteVeterinarian();
                case 21 -> searchVeterinarianByName();
                case 22 -> searchVeterinarianByPhone();
                case 23 -> getVeterinarianById();

                // Other
                case 24 -> vaccinatePet();
                case 0 -> {
                    System.out.println("Exiting... Thank you!");
                    running = false;
                }
                default -> System.out.println("Invalid choice: please choose a valid menu number.");
            }
        }
    }

    // ========== PET METHODS ==========
    private void addPet() {
        try {
            System.out.println("\n=== ADD NEW PET ===");

            System.out.print("Enter pet name: ");
            String name = scanner.nextLine();

            System.out.print("Enter species: ");
            String species = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter gender (MALE/FEMALE): ");
            Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Is vaccinated? (true/false): ");
            boolean vaccinated = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Enter owner ID: ");
            int ownerId = Integer.parseInt(scanner.nextLine());

            Pet pet = new Pet(name, species, age, gender, vaccinated);
            pet.setOwnerId(ownerId);

            boolean success = petDAO.insert(pet);
            System.out.println(success ? "✓ Pet added!" : "✗ Failed to add pet.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updatePet() {
        try {
            System.out.println("\n=== UPDATE PET ===");

            System.out.print("Enter pet ID to update: ");
            int petId = Integer.parseInt(scanner.nextLine());

            Pet currentPet = petDAO.getById(petId);
            if (currentPet == null) {
                System.out.println("✗ Pet not found!");
                return;
            }

            System.out.println("Current: " + currentPet);
            System.out.println("\nEnter new values (press Enter to keep current):");

            System.out.print("New name [" + currentPet.getName() + "]: ");
            String name = scanner.nextLine();
            if (name.isEmpty()) name = currentPet.getName();

            System.out.print("New species [" + currentPet.getSpecies() + "]: ");
            String species = scanner.nextLine();
            if (species.isEmpty()) species = currentPet.getSpecies();

            System.out.print("New age [" + currentPet.getAge() + "]: ");
            String ageInput = scanner.nextLine();
            int age = ageInput.isEmpty() ? currentPet.getAge() : Integer.parseInt(ageInput);

            System.out.print("New gender [" + currentPet.getGender() + "]: ");
            String genderInput = scanner.nextLine();
            Gender gender = genderInput.isEmpty() ? currentPet.getGender() : Gender.valueOf(genderInput.toUpperCase());

            System.out.print("Vaccinated? [" + currentPet.isVaccinated() + "]: ");
            String vaccInput = scanner.nextLine();
            boolean vaccinated = vaccInput.isEmpty() ? currentPet.isVaccinated() : Boolean.parseBoolean(vaccInput);

            Pet updatedPet = new Pet(petId, name, species, age, gender, vaccinated);
            updatedPet.setOwnerId(currentPet.getOwnerId());

            boolean success = petDAO.update(updatedPet);
            System.out.println(success ? "✓ Pet updated!" : "✗ Failed to update pet.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deletePet() {
        try {
            System.out.println("\n=== DELETE PET ===");

            System.out.print("Enter pet ID to delete: ");
            int petId = Integer.parseInt(scanner.nextLine());

            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = petDAO.delete(petId);
                System.out.println(success ? "✓ Pet deleted!" : "✗ Failed to delete pet.");
            } else {
                System.out.println("Delete cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchPetByName() {
        System.out.print("\nEnter pet name: ");
        String name = scanner.nextLine();
        petDAO.searchByName(name);
    }

    private void searchPetByAge() {
        try {
            System.out.print("\nEnter pet age: ");
            int age = Integer.parseInt(scanner.nextLine());
            petDAO.searchByAge(age);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchPetBySpecies() {
        System.out.print("\nEnter pet species: ");
        String species = scanner.nextLine();
        petDAO.searchBySpecies(species);
    }

    private void searchPetByOwnerName() {
        System.out.print("\nEnter owner name: ");
        String ownerName = scanner.nextLine();
        petDAO.searchByOwnerName(ownerName);
    }

    private void getPetById() {
        try {
            System.out.print("\nEnter pet ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Pet pet = petDAO.getById(id);
            if (pet == null) {
                System.out.println("No pet found with ID " + id);
            } else {
                System.out.println("Pet found: " + pet);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== OWNER METHODS ==========
    private void addOwner() {
        try {
            System.out.println("\n=== ADD NEW OWNER ===");

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter address: ");
            String address = scanner.nextLine();

            System.out.print("Enter gender (MALE/FEMALE): ");
            Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

            Owner owner = new Owner(name, phone, email, address, gender);
            boolean success = ownerDAO.insert(owner);
            System.out.println(success ? "✓ Owner added!" : "✗ Failed to add owner.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateOwner() {
        try {
            System.out.println("\n=== UPDATE OWNER ===");

            System.out.print("Enter owner ID to update: ");
            int ownerId = Integer.parseInt(scanner.nextLine());

            Owner current = ownerDAO.getById(ownerId);
            if (current == null) {
                System.out.println("✗ Owner not found!");
                return;
            }

            System.out.println("Current: " + current);
            System.out.println("\nEnter new values (press Enter to keep current):");

            System.out.print("New name [" + current.getName() + "]: ");
            String name = scanner.nextLine();
            if (name.isEmpty()) name = current.getName();

            System.out.print("New phone [" + current.getPhone() + "]: ");
            String phone = scanner.nextLine();
            if (phone.isEmpty()) phone = current.getPhone();

            System.out.print("New email [" + current.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (email.isEmpty()) email = current.getEmail();

            System.out.print("New address [" + current.getAddress() + "]: ");
            String address = scanner.nextLine();
            if (address.isEmpty()) address = current.getAddress();

            Owner updated = new Owner(ownerId, name, phone, email, address, current.getGender());
            boolean success = ownerDAO.update(updated);
            System.out.println(success ? "✓ Owner updated!" : "✗ Failed to update owner.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteOwner() {
        try {
            System.out.println("\n=== DELETE OWNER ===");

            System.out.print("Enter owner ID to delete: ");
            int ownerId = Integer.parseInt(scanner.nextLine());

            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = ownerDAO.delete(ownerId);
                System.out.println(success ? "✓ Owner deleted!" : "✗ Failed to delete owner.");
            } else {
                System.out.println("Delete cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchOwnerByName() {
        System.out.print("\nEnter owner name: ");
        String name = scanner.nextLine();
        ownerDAO.searchByName(name);
    }

    private void searchOwnerByPhone() {
        System.out.print("\nEnter owner phone: ");
        String phone = scanner.nextLine();
        ownerDAO.searchByPhone(phone);
    }

    private void getOwnerById() {
        try {
            System.out.print("\nEnter owner ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Owner owner = ownerDAO.getById(id);
            if (owner == null) {
                System.out.println("No owner found with ID " + id);
            } else {
                System.out.println("Owner found: " + owner);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== VETERINARIAN METHODS ==========
    private void addVeterinarian() {
        try {
            System.out.println("\n=== ADD NEW VETERINARIAN ===");

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter phone: ");
            String phone = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine();

            System.out.print("Enter experience (years): ");
            int experience = Integer.parseInt(scanner.nextLine());

            Veterinarian vet = new Veterinarian(name, phone, email, specialization, experience);
            boolean success = veterinarianDAO.insert(vet);
            System.out.println(success ? "✓ Veterinarian added!" : "✗ Failed to add veterinarian.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateVeterinarian() {
        try {
            System.out.println("\n=== UPDATE VETERINARIAN ===");

            System.out.print("Enter veterinarian ID to update: ");
            int vetId = Integer.parseInt(scanner.nextLine());

            Veterinarian current = veterinarianDAO.getById(vetId);
            if (current == null) {
                System.out.println("✗ Veterinarian not found!");
                return;
            }

            System.out.println("Current: " + current);
            System.out.println("\nEnter new values (press Enter to keep current):");

            System.out.print("New name [" + current.getName() + "]: ");
            String name = scanner.nextLine();
            if (name.isEmpty()) name = current.getName();

            System.out.print("New phone [" + current.getPhone() + "]: ");
            String phone = scanner.nextLine();
            if (phone.isEmpty()) phone = current.getPhone();

            System.out.print("New email [" + current.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (email.isEmpty()) email = current.getEmail();

            System.out.print("New specialization [" + current.getSpecialization() + "]: ");
            String specialization = scanner.nextLine();
            if (specialization.isEmpty()) specialization = current.getSpecialization();

            System.out.print("New experience [" + current.getExperience() + "]: ");
            String expInput = scanner.nextLine();
            int experience = expInput.isEmpty() ? current.getExperience() : Integer.parseInt(expInput);

            Veterinarian updated = new Veterinarian(vetId, name, phone, email, specialization, experience);
            boolean success = veterinarianDAO.update(updated);
            System.out.println(success ? "✓ Veterinarian updated!" : "✗ Failed to update veterinarian.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteVeterinarian() {
        try {
            System.out.println("\n=== DELETE VETERINARIAN ===");

            System.out.print("Enter veterinarian ID to delete: ");
            int vetId = Integer.parseInt(scanner.nextLine());

            System.out.print("Are you sure? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = veterinarianDAO.delete(vetId);
                System.out.println(success ? "✓ Veterinarian deleted!" : "✗ Failed to delete veterinarian.");
            } else {
                System.out.println("Delete cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchVeterinarianByName() {
        System.out.print("\nEnter veterinarian name: ");
        String name = scanner.nextLine();
        veterinarianDAO.searchByName(name);
    }

    private void searchVeterinarianByPhone() {
        System.out.print("\nEnter veterinarian phone: ");
        String phone = scanner.nextLine();
        veterinarianDAO.searchByPhone(phone);
    }

    private void getVeterinarianById() {
        try {
            System.out.print("\nEnter veterinarian ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Veterinarian vet = veterinarianDAO.getById(id);
            if (vet == null) {
                System.out.println("No veterinarian found with ID " + id);
            } else {
                System.out.println("Veterinarian found: " + vet);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ========== VACCINATE PET ==========
    private void vaccinatePet() {
        try {
            System.out.println("\n=== VACCINATE PET ===");

            System.out.print("Enter pet ID: ");
            int petId = Integer.parseInt(scanner.nextLine());

            Pet pet = petDAO.getById(petId);
            if (pet == null) {
                System.out.println("✗ Pet not found!");
                return;
            }

            System.out.println("Pet: " + pet);
            System.out.print("Confirm vaccination? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                pet.vaccinate();
                pet.setVaccinated(true);
                boolean success = petDAO.update(pet);
                System.out.println(success ? "✓ Pet vaccinated!" : "✗ Failed to update pet.");
            } else {
                System.out.println("Vaccination cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}