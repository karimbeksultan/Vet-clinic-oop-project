package model;

import exception.InvalidInputException;

public abstract class Person {

    protected int id;
    protected String name;
    protected String phone;
    protected String email;
    protected Gender gender;

    public Person(String name, String phone, String email, Gender gender) throws InvalidInputException {
        setName(name);
        setPhone(phone);
        setEmail(email);
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidInputException {
        if (!Validating.isValidStr(name)) {
            throw new InvalidInputException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws InvalidInputException {
        if (!Validating.isNumber(phone)) {
            throw new InvalidInputException("Phone must contain only numbers");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidInputException {
        if (!Validating.isValidStr(email) || !email.contains("@")) {
            throw new InvalidInputException("Email must contain '@'");
        }
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public abstract String getRole();
}