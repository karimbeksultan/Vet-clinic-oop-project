package model;

public abstract class Animal {

    protected String type;
    protected int age;
    protected Gender gender;
    protected String breed;

    public Animal(String type, int age, String breed, Gender gender) {
        this.type = type;
        this.age = age;
        this.breed = breed;
        this.gender = gender;
    }

    public void makeSound() {
        System.out.println("Animal makes a sound");
    }

    public void eat() {
        System.out.println("Animal is eating");
    }

    public void info() {
        System.out.println(gender + breed + " Animal " + type + ", age: " + age);
    }

    public void sleep() {
        System.out.println("Animal is sleeping");
    }

    public String getType() {
        return getClass().getSimpleName();
    }
    public int getAge() {return age;}
    public String getBreed() {return breed;}
    public Gender getGender() {return gender;}

    public void setAge(int age) {this.age = age;}
    public void setBreed(String breed) {this.breed = breed;}
    public void setGender(Gender gender) {this.gender = gender;}
}