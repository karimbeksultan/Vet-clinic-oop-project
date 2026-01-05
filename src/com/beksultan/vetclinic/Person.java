package com.beksultan.vetclinic;

public abstract class Person {
    protected String name;
    protected String phone;
    protected String email;
    protected Gender gender;

    public Person(String name, String phone, String email, Gender gender) {
        setName(name);
        setPhone(phone);
        setEmail(email);
        setGender(gender);
    }


    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public Gender getGender() { return gender; }

    public void setName(String name) {
        if (Validating.isValidStr(name)) this.name = name;
        else throw new IllegalArgumentException("Name invalid!");
    }
    public void setPhone(String phone) {
        if (Validating.isValidStr(phone) && Validating.isNumber(phone)) this.phone = phone;
        else throw new IllegalArgumentException("Phone invalid!");
    }
    public void setEmail(String email) {
        if (Validating.isValidStr(email) && email.contains("@")) this.email = email;
        else throw new IllegalArgumentException("Email invalid! Must contain '@'");
    }
    public void setGender(Gender gender) {
        if (gender != null) this.gender = gender;
        else throw new IllegalArgumentException("Gender invalid!");
    }


    public abstract String work(); // Each child class overrides
}
