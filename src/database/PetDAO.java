package database;

import model.Pet;
import model.Gender;
import exception.InvalidInputException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    // CREATE
    public boolean insert(Pet pet) {
        String sql = "INSERT INTO pets (name, species, age, gender, vaccinated, owner_id) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getSpecies());
            statement.setInt(3, pet.getAge());
            statement.setString(4, pet.getGender().toString());
            statement.setBoolean(5, pet.isVaccinated());
            statement.setInt(6, pet.getOwnerId());

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

    // READ ALL
    public void displayAll() {
        String sql = "SELECT * FROM pets ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== ALL PETS FROM DATABASE ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Pet pet = extractFromResultSet(resultSet);
                    System.out.println(pet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating pet: " + e.getMessage());
                }
            }

            System.out.println("Total: " + count + " pet(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Failed to get pets!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // READ BY ID
    public Pet getById(int id) {
        String sql = "SELECT * FROM pets WHERE pet_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Pet pet = extractFromResultSet(resultSet);
                resultSet.close();
                statement.close();
                return pet;
            }

            resultSet.close();
            statement.close();

        } catch (SQLException | InvalidInputException e) {
            System.out.println("✗ Failed to get pet by ID!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    // UPDATE
    public boolean update(Pet pet) {
        String sql = "UPDATE pets SET name = ?, species = ?, age = ?, gender = ?, vaccinated = ?, owner_id = ? WHERE pet_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getSpecies());
            statement.setInt(3, pet.getAge());
            statement.setString(4, pet.getGender().toString());
            statement.setBoolean(5, pet.isVaccinated());
            statement.setInt(6, pet.getOwnerId());
            statement.setInt(7, pet.getId());

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

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM pets WHERE pet_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("✗ Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    // SEARCH BY NAME
    public void searchByName(String name) {
        String sql = "SELECT * FROM pets WHERE name ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== PETS WITH NAME CONTAINING: '" + name + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Pet pet = extractFromResultSet(resultSet);
                    System.out.println(pet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating pet: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " pet(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // SEARCH BY AGE
    public void searchByAge(int age) {
        String sql = "SELECT * FROM pets WHERE age = ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, age);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== PETS WITH AGE: " + age + " ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Pet pet = extractFromResultSet(resultSet);
                    System.out.println(pet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating pet: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " pet(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Search by age failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // SEARCH BY SPECIES
    public void searchBySpecies(String species) {
        String sql = "SELECT * FROM pets WHERE species ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + species + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== PETS WITH SPECIES: '" + species + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Pet pet = extractFromResultSet(resultSet);
                    System.out.println(pet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating pet: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " pet(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Search by species failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // SEARCH BY OWNER NAME
    public void searchByOwnerName(String ownerName) {
        String sql = "SELECT p.* FROM pets p JOIN owners o ON p.owner_id = o.owner_id WHERE o.name ILIKE ? ORDER BY p.name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + ownerName + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== PETS WITH OWNER NAME: '" + ownerName + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Pet pet = extractFromResultSet(resultSet);
                    System.out.println(pet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating pet: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " pet(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Search by owner name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // HELPER METHOD
    private Pet extractFromResultSet(ResultSet resultSet) throws SQLException, InvalidInputException {
        int id = resultSet.getInt("pet_id");
        String name = resultSet.getString("name");
        String species = resultSet.getString("species");
        int age = resultSet.getInt("age");
        Gender gender = Gender.valueOf(resultSet.getString("gender"));
        boolean vaccinated = resultSet.getBoolean("vaccinated");
        int ownerId = resultSet.getInt("owner_id");

        Pet pet = new Pet(id, name, species, age, gender, vaccinated);
        pet.setOwnerId(ownerId);
        return pet;
    }
}