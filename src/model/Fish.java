package model;

public class Fish extends Animal {

    protected boolean hasScales;

    public Fish(int age, String breed, Gender gender, boolean hasScales) {
        super("Fish", age, breed, gender);
        this.hasScales = hasScales;
    }

    public boolean hasScales() {
        return hasScales;
    }

    @Override
    public void makeSound() {
        System.out.println("blubs: Bubble!");
    }

    @Override
    public void eat() {
        System.out.println("Fish eats algae and plankton");
    }

    public void swim() {
        System.out.println("swish-swish-swish...");
    }
}