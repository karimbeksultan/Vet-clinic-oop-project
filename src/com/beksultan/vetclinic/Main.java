package com.beksultan.vetclinic;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Питомцы
        Pet rocky = new Pet("Rocky", "pug", 3, Gender.MALE, false);
        Pet balu  = new Pet("Balu", "honeybadger", 4, Gender.MALE, false);
        Pet ozzie = new Pet("Ozzie", "ostrich", 2, Gender.FEMALE, true);

        // Владельцы
        Owner arlan    = new Owner("Arlan", "+7 701 123 45 67", "arlan@turan.kz", "Turan Avenue", Gender.MALE);
        Owner beksultan= new Owner("Beksultan", "+7 702 234 56 78", "beksultan@turan.kz", "Turan Avenue", Gender.MALE);
        Owner arystan  = new Owner("Arystan", "+7 703 345 67 89", "arystan@turan.kz", "Turan Avenue", Gender.MALE);

        arlan.addPet(rocky);
        beksultan.addPet(balu);
        arystan.addPet(ozzie);

        // Ветеринар
        Veterinarian aidana = new Veterinarian("Aidana", "+7 705 456 78 90", "Exotics & Small Animals", 7);
        aidana.startWork();
        aidana.addRating(5);

        // Коллекции
        List<Owner> owners = Arrays.asList(arlan, beksultan, arystan);
        Map<String, Pet> petsByName = new HashMap<>();
        for (Owner o : owners) {
            for (Pet p : o.getPets()) {
                petsByName.put(p.getName().toLowerCase(), p);
            }
        }

        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("\n=== Меню VetClinic ===");
            System.out.println("1. Список владельцев и питомцев");
            System.out.println("2. Вакцинация питомца");
            System.out.println("3. День рождения питомца");
            System.out.println("4. Информация о ветеринаре");
            System.out.println("5. Выход");
            System.out.print("Ваш выбор: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    for (Owner o : owners) {
                        System.out.println(o);
                        for (Pet p : o.getPets()) {
                            System.out.println("  - " + p);
                        }
                    }
                    break;
                case "2":
                    System.out.print("Введите имя питомца: ");
                    String vName = sc.nextLine().toLowerCase();
                    Pet vPet = petsByName.get(vName);
                    if (vPet != null) vPet.vaccinate();
                    else System.out.println("Питомец не найден.");
                    break;
                case "3":
                    System.out.print("Введите имя питомца: ");
                    String bName = sc.nextLine().toLowerCase();
                    Pet bPet = petsByName.get(bName);
                    if (bPet != null) bPet.birthday();
                    else System.out.println("Питомец не найден.");
                    break;
                case "4":
                    System.out.println(aidana);
                    break;
                case "5":
                    run = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
        sc.close();
        aidana.endWork();
    }
}

