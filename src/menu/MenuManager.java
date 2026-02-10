package menu;

import database.VetclinicDao;
import exception.InvalidInputException;
import model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuManager implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final VetclinicDao vetclinicDao = new VetclinicDao();

    private static class CommonAnimalFields {
        String name;
        boolean vaccinated;
        int age;
        String breed;
        Gender gender;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=================================");
        System.out.println("      VET CLINIC MANAGEMENT ");
        System.out.println("=================================");
        System.out.println("1. Add Dog");
        System.out.println("2. Add Cat");
        System.out.println("3. Add Fish");
        System.out.println("4. View All Patients");
        System.out.println("5. View Dogs Only");
        System.out.println("6. View Cats Only");
        System.out.println("7. View Fish Only");
        System.out.println("8. Update Patient by ID");
        System.out.println("9. Delete Patient by ID");
        System.out.println("10. Search by Name");
        System.out.println("11. Search by Age Range");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    @Override
    public void run() {
        int choice = -1;

        while (choice != 0) {
            try {
                displayMenu();
                choice = readInt();

                switch (choice) {
                    case 1 -> addDog();
                    case 2 -> addCat();
                    case 3 -> addFish();
                    case 4 -> viewList(vetclinicDao.findAll());
                    case 5 -> viewList(vetclinicDao.findByType("DOG"));
                    case 6 -> viewList(vetclinicDao.findByType("CAT"));
                    case 7 -> viewList(vetclinicDao.findByType("FISH"));
                    case 8 -> updatePatient();
                    case 9 -> deletePatient();
                    case 10 -> searchByName();
                    case 11 -> searchByAgeRange();
                    case 0 -> System.out.println("Exiting clinic management...");
                    default -> throw new InvalidInputException("Invalid choice: " + choice);
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a NUMBER.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }
    }

    private CommonAnimalFields readCommonFields() throws InvalidInputException {
        CommonAnimalFields f = new CommonAnimalFields();

        System.out.print("Enter Patient Name: ");
        f.name = readNonEmpty();

        System.out.print("Vaccinated (true/false)?: ");
        f.vaccinated = readBoolean();

        System.out.print("Enter age: ");
        f.age = readInt();

        System.out.print("Enter breed: ");
        f.breed = readNonEmpty();

        System.out.print("Enter gender (MALE/FEMALE): ");
        f.gender = Gender.valueOf(readNonEmpty().toUpperCase());

        return f;
    }

    private void addDog() throws InvalidInputException, SQLException {
        CommonAnimalFields f = readCommonFields();

        System.out.print("Is trained (true/false)?: ");
        boolean trained = readBoolean();

        Pet patient = new Pet(
                f.name,
                f.vaccinated,
                new Dog(f.age, f.breed, f.gender, trained)
        );

        boolean ok = vetclinicDao.insert(patient);
        System.out.println(ok ? "Patient registered!" : "Registration failed!");
    }

    private void addCat() throws InvalidInputException, SQLException {
        CommonAnimalFields f = readCommonFields();

        System.out.print("Is indoor (true/false)?: ");
        boolean indoor = readBoolean();

        Pet patient = new Pet(
                f.name,
                f.vaccinated,
                new Cat(f.age, f.breed, f.gender, indoor)
        );

        boolean ok = vetclinicDao.insert(patient);
        System.out.println(ok ? "Patient registered!" : "Registration failed!");
    }

    private void addFish() throws InvalidInputException, SQLException {
        CommonAnimalFields f = readCommonFields();

        System.out.print("Has scales (true/false)?: ");
        boolean hasScales = readBoolean();

        Pet patient = new Pet(
                f.name,
                f.vaccinated,
                new Fish(f.age, f.breed, f.gender, hasScales)
        );

        boolean ok = vetclinicDao.insert(patient);
        System.out.println(ok ? "Patient registered!" : "Registration failed!");
    }

    private void viewList(List<Pet> patients) {
        System.out.println("\n============ CLINIC PATIENTS ===========");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        for (Pet patient : patients) {
            System.out.println(patient);

            Animal animal = patient.getAnimal();
            animal.makeSound();
            animal.eat();

            if (animal instanceof Dog dog) dog.fetch();
            else if (animal instanceof Cat cat) cat.climb();
            else if (animal instanceof Fish fish) fish.swim();

            System.out.println("--------------------------------");
        }
    }

    private void updatePatient() throws InvalidInputException, SQLException {
        System.out.print("Enter Patient ID to update: ");
        long id = readLong();

        Pet existing = vetclinicDao.findById(id);
        if (existing == null) {
            throw new InvalidInputException("Patient not found with ID: " + id);
        }

        System.out.println("Current patient:");
        System.out.println(existing);

        System.out.print("New name (Enter to keep): ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) newName = existing.getName();

        System.out.print("Vaccinated (true/false) (Enter to keep): ");
        String vacStr = scanner.nextLine().trim();
        boolean newVac = vacStr.isEmpty()
                ? existing.isVaccinated()
                : parseBooleanStrict(vacStr);

        Animal oldA = existing.getAnimal();
        String type = oldA.getClass().getSimpleName().toUpperCase();

        System.out.print("New age (Enter to keep): ");
        String ageStr = scanner.nextLine().trim();
        int newAge = ageStr.isEmpty()
                ? oldA.getAge()
                : Integer.parseInt(ageStr);

        System.out.print("New breed (Enter to keep): ");
        String breedStr = scanner.nextLine().trim();
        String newBreed = breedStr.isEmpty()
                ? oldA.getBreed()
                : breedStr;

        System.out.print("New gender (MALE/FEMALE) (Enter to keep): ");
        String gStr = scanner.nextLine().trim();
        Gender newGender = gStr.isEmpty()
                ? oldA.getGender()
                : Gender.valueOf(gStr.toUpperCase());

        Animal newAnimal;

        if (type.equals("DOG")) {
            boolean trained = (oldA instanceof Dog d) && d.isTrained();
            System.out.print("Trained (true/false) (Enter to keep): ");
            String tStr = scanner.nextLine().trim();
            if (!tStr.isEmpty()) trained = parseBooleanStrict(tStr);
            newAnimal = new Dog(newAge, newBreed, newGender, trained);

        } else if (type.equals("CAT")) {
            boolean indoor = (oldA instanceof Cat c) && c.isIndoor();
            System.out.print("Indoor (true/false) (Enter to keep): ");
            String iStr = scanner.nextLine().trim();
            if (!iStr.isEmpty()) indoor = parseBooleanStrict(iStr);
            newAnimal = new Cat(newAge, newBreed, newGender, indoor);

        } else {
            boolean hasScales = (oldA instanceof Fish f) && f.hasScales();
            System.out.print("Has scales (true/false) (Enter to keep): ");
            String hStr = scanner.nextLine().trim();
            if (!hStr.isEmpty()) hasScales = parseBooleanStrict(hStr);
            newAnimal = new Fish(newAge, newBreed, newGender, hasScales);
        }

        Pet updated = new Pet(id, newName, newVac, newAnimal);

        boolean ok = vetclinicDao.update(updated);
        System.out.println(ok ? "Patient updated!" : "Update failed!");
    }

    private void deletePatient() throws InvalidInputException, SQLException {
        System.out.print("Enter Patient ID to delete: ");
        int id = readInt();

        Pet existing = vetclinicDao.findById(id);
        if (existing == null) {
            throw new InvalidInputException("Patient not found with ID: " + id);
        }

        System.out.println("Will delete patient:");
        System.out.println(existing);

        System.out.print("Are you sure? (yes/no): ");
        String ans = readNonEmpty().toLowerCase();

        if (!ans.equals("yes")) {
            System.out.println("Cancellation confirmed.");
            return;
        }

        boolean ok = vetclinicDao.deleteById(id);
        System.out.println(ok ? "Patient removed!" : "Removal failed!");
    }

    private void searchByName() throws InvalidInputException, SQLException {
        System.out.print("Enter name part: ");
        String part = readNonEmpty();
        viewList(vetclinicDao.searchByName(part));
    }

    private void searchByAgeRange() throws InvalidInputException, SQLException {
        System.out.print("Min age: ");
        int min = readInt();
        System.out.print("Max age: ");
        int max = readInt();
        viewList(vetclinicDao.searchByAgeRange(min, max));
    }

    private int readInt() {
        String s = scanner.nextLine();
        return Integer.parseInt(s.trim());
    }

    private boolean readBoolean() throws InvalidInputException {
        String s = readNonEmpty().toLowerCase();
        if (s.equals("true")) return true;
        if (s.equals("false")) return false;
        throw new InvalidInputException("Enter true or false!");
    }

    private boolean parseBooleanStrict(String s) throws InvalidInputException {
        s = s.trim().toLowerCase();
        if (s.equals("true")) return true;
        if (s.equals("false")) return false;
        throw new InvalidInputException("Enter true or false!");
    }

    private String readNonEmpty() throws InvalidInputException {
        String s = scanner.nextLine();
        if (s == null || s.trim().isEmpty()) {
            throw new InvalidInputException("Input cannot be empty!");
        }
        return s.trim();
    }

    private long readLong() {
        String s = scanner.nextLine();
        return Long.parseLong(s.trim());
    }
}
