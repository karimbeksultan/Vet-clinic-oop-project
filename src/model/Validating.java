package model;

import java.util.List;

public class Validating {
    public static boolean isNumber(String input){
        char[] chars = input.toCharArray();

        for(int i = 0; i < chars.length; i++){
            char c = chars[i];

            if(i == 0 && c == '+'){
                continue;
            }

            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public static boolean isValidStr(String input){
        if(input != null && !input.isBlank()){
            return true;
        }
        return false;
    }

    public static boolean isPetIdUnique(int id, List<Pet> pets) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOwnerIdUnique(int id, List<Owner> owners) {
        for (Owner owner : owners) {
            if (owner.getId() == id) {
                return false;
            }
        }
        return true;
    }

    public static boolean isVetIdUnique(int id, List<Veterenarian> vets) {
        for (Veterenarian vet : vets) {
            if (vet.getId() == id) {
                return false;
            }
        }
        return true;
    }
}
