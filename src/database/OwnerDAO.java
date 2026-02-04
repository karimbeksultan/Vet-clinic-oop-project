package database;

import model.Owner;
import model.Gender;
import exception.InvalidInputException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {

    public boolean insertOwner(Owner owner) {
        String sql = "INSERT INTO owners (name, phone, email, address, gender) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, owner.getName());
            statement.setString(2, owner.getPhone());
            statement.setString(3, owner.getEmail());
            statement.setString(4, owner.getAddress());
            statement.setString(5, owner.getGender().toString());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("✗ Owner insertion failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    public List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owners ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return owners;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                try {
                    String name = resultSet.getString("name");
                    String phone = resultSet.getString("phone");
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");
                    Gender gender = Gender.valueOf(resultSet.getString("gender"));

                    Owner owner = new Owner(name, phone, email, address, gender);
                    owners.add(owner);
                } catch (InvalidInputException e) {
                    System.out.println("Error creating owner: " + e.getMessage());
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Failed to get owners!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return owners;
    }
}