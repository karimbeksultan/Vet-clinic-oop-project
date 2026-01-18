package model;

public abstract class Person {
    protected String name;
    protected String phone;
    protected String email;
    protected Gender gender;

    public Person(String name, String phone, String email, Gender gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    public abstract String getRole();
}
