package model;

public class Cat extends Animal {

    protected boolean indoor;

    public Cat(int age, String breed, Gender gender, boolean indoor) {
        super("Cat", age, breed, gender);
        this.indoor = indoor;
    }

    public boolean isIndoor() {
        return indoor;
    }

    @Override
    public void makeSound() {
        System.out.println("Cat meows: Meow!");
    }

    @Override
    public void eat() {
        System.out.println("Cat eats food");
    }

    public void climb() {
        System.out.println("Cat is climbing");
    }

    @Override
    public void sleep() {
        System.out.println("Cat is sleeping");
    }
}