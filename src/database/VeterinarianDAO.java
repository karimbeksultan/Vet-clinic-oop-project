package database;

import model.Veterinarian;
import exception.InvalidInputException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarianDAO {

    public boolean insertVeterinarian(Veterinarian vet) {
        String sql = "INSERT INTO veterinarians (name, phone, email, specialization, experience_years) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, vet.getName());
            statement.setString(2, vet.getPhone());
            statement.setString(3, vet.getEmail());
            statement.setString(4, vet.getSpecialization());
            statement.setInt(5, vet.getExperience());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("✗ Veterinarian insertion failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public List<Veterinarian> getAllVeterinarians() {
        List<Veterinarian> vets = new ArrayList<>();
        String sql = "SELECT * FROM veterinarians ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return vets;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String specialization = resultSet.getString("specialization");
                    int experience = resultSet.getInt("experience_years");

                    Veterinarian vet = new Veterinarian(name, phone, email, specialization, experience);
                    vets.add(vet);
                } catch (InvalidInputException e) {
                    System.out.println("Error creating vet: " + e.getMessage());
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Failed to get veterinarians!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return vets;
    }
}