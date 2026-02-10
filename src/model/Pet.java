package model;

public class Pet {
    private long id;
    private String name;
    private boolean vaccinated;
    private Animal animal;

    public Pet(String name, boolean vaccinated, Animal animal) {
        setName(name);
        setVaccinated(vaccinated);
        this.animal = animal;
    }

    public Pet(long id, String name, boolean vaccinated, Animal animal) {
        setId(id);
        setName(name);
        setVaccinated(vaccinated);
        setAnimal(animal);
    }

    public Animal getAnimal() {return animal;}
    public long getId() {return id;}
    public String getName() {return name;}
    public int getAge() {return animal.getAge();}
    public Gender getGender() {return animal.getGender();}
    public String getBreed() {return animal.getBreed();}
    public boolean isVaccinated() {return vaccinated;}

    public void setId(long id) {
        if (id >= 0) {
            this.id = id;
        }
        else {
            throw new IllegalArgumentException("Id is already exists, please try again with another id!");
        }
    }

    public void setName(String name) {
        if (Validating.isValidStr(name)) {
            this.name = name;
        }
        else {
            throw new IllegalArgumentException("Pet name is invalid, please try again with another name!");
        }
    }
    public void setAge(int age) {
        if (age >= 0 && age <= 100) {
            animal.setAge(age);
        }
        else {
            throw new IllegalArgumentException("Age is invalid, please try again with another age!");
        }
    }
    public void setGender(Gender gender) {
        if (gender != null) {
            animal.setGender(gender);
        }
        else {
            throw new IllegalArgumentException("Gender is invalid, please try again with another gender!");
        }
    }

    public void setBreed(String breed) {
        if (breed != null) {
            animal.setBreed(breed);
        }
        else {
            throw new IllegalArgumentException("Breed is invalid, please try again with another breed!");
        }
    }

    public void setAnimal(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("Animal cannot be null");
        this.animal = animal;
    }


    public void setVaccinated(boolean vaccinated) {this.vaccinated = vaccinated;}

    public void birthday(){
        animal.setAge(animal.getAge() + 1);
        System.out.printf("%s turned %d!\n", name, animal.getAge());
    }

    public void vaccinate(){
        if (!vaccinated){
            this.vaccinated = true;
            System.out.printf("%s has been vaccinated!\n", name);
        }
        else{
            System.out.printf("%s is already vaccinated!\n", name);
        }
    }

    @Override
    public String toString() {
        return "(" + id + ") " + name + " - " + animal.getType() + "\n" + animal.getAge() + " years old\nGender: " + animal.getGender() + "\nVaccinated: " + vaccinated;
    }
}