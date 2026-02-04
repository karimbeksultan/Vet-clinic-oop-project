package model;

import exception.InvalidInputException;

public class Veterinarian extends Person implements Servable {
    private String specialization;
    private int experience;

    public Veterinarian(String name, String phone, String email,
                        String specialization, int experience)
            throws InvalidInputException {
        super(name, phone, email, Gender.FEMALE); // Changed to Gender.FEMALE
        setSpecialization(specialization);
        setExperience(experience);
    }

    // Getter methods
    public String getSpecialization() {
        return specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setSpecialization(String specialization) throws InvalidInputException {
        if (!Validating.isValidStr(specialization))
            throw new InvalidInputException("Specialization is invalid");
        this.specialization = specialization;
    }

    public void setExperience(int experience) throws InvalidInputException {
        if (experience < 0)
            throw new InvalidInputException("Experience cannot be negative");
        this.experience = experience;
    }

    @Override
    public String getRole() {
        return "Veterinarian";
    }

    @Override
    public void serve() {
        System.out.println(name + " treats animals!");
    }

    @Override
    public String toString() {
        return name + " (Vet " + specialization + ", " + experience + " yrs)";
    }
}