package model;

import exception.InvalidInputException;

public class Owner extends Person {

    private String address;

    public Owner(String name, String phone, String email, String address, Gender gender)
            throws InvalidInputException {
        super(name, phone, email, gender);
        setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws InvalidInputException {
        if (!Validating.isValidStr(address)) {
            throw new InvalidInputException("Address cannot be empty");
        }
        this.address = address;
    }

    @Override
    public String getRole() {
        return "Owner";
    }

    @Override
    public String toString() {
        return name + " (Owner) [ID: " + id + "]\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n" +
                "Address: " + address;
    }
}