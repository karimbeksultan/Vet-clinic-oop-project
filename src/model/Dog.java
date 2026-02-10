package model;

public class Dog extends Animal {

    protected boolean trained;

    public Dog(int age, String breed, Gender gender, boolean trained) {
        super("Dog", age, breed, gender);
        this.trained = trained;
    }

    public boolean isTrained() {
        return trained;
    }

    @Override
    public void makeSound() {
        System.out.println("Dog barks: Woof!");
    }

    @Override
    public void eat() {
        System.out.println("Dog eats food");
    }

    public void fetch() {
        System.out.println("Dog is fetching a ball");
    }

    public void guardHouse() {
        System.out.println("Dog is guarding the house");
    }

    @Override
    public void sleep() {
        System.out.println("Dog is sleeping");
    }
}