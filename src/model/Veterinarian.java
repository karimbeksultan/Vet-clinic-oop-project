package model;

import exception.InvalidInputException;

public class Veterinarian extends Person implements Servable {
    private int id;
    private String specialization;
    private int experience;

    // Конструктор без ID
    public Veterinarian(String name, String phone, String email,
                        String specialization, int experience)
            throws InvalidInputException {
        this(0, name, phone, email, specialization, experience);
    }

    // Конструктор с ID
    public Veterinarian(int id, String name, String phone, String email,
                        String specialization, int experience)
            throws InvalidInputException {
        super(name, phone, email, Gender.FEMALE); // Можно изменить на передачу gender
        this.id = id;
        setSpecialization(specialization);
        setExperience(experience);
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSpecialization() { return specialization; }

    public int getExperience() { return experience; }

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
        return "ID: " + id + " - " + name + " (Vet " + specialization + ", " + experience + " yrs)";
    }
}