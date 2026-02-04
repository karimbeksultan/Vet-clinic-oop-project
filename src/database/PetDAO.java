package database;

import model.Pet;
import model.Gender;
import exception.InvalidInputException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    public boolean insertPet(Pet pet) {
        String sql = "INSERT INTO pets (name, species, age, gender, vaccinated) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getSpecies());
            statement.setInt(3, pet.getAge());
            statement.setString(4, pet.getGender().toString());
            statement.setBoolean(5, pet.isVaccinated());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("✗ Pet insertion failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return pets;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    String name = resultSet.getString("name");
                    String species = resultSet.getString("species");
                    int age = resultSet.getInt("age");
                    Gender gender = Gender.valueOf(resultSet.getString("gender"));
                    boolean vaccinated = resultSet.getBoolean("vaccinated");

                    Pet pet = new Pet(name, species, age, gender, vaccinated);
                    pets.add(pet);
                } catch (InvalidInputException e) {
                    System.out.println("Error creating pet: " + e.getMessage());
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Failed to get pets!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return pets;
    }

    public boolean updatePet(Pet pet) {
        String sql = "UPDATE pets SET vaccinated = ? WHERE name = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, pet.isVaccinated());
            statement.setString(2, pet.getName());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("✗ Pet update failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }
}