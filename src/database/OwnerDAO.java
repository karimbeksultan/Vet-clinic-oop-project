package database;

import model.Owner;
import model.Gender;
import exception.InvalidInputException;
import java.sql.*;

public class OwnerDAO {

    // CREATE
    public boolean insert(Owner owner) {
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

    // READ ALL
    public void displayAll() {
        String sql = "SELECT * FROM owners ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== ALL OWNERS FROM DATABASE ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Owner owner = extractFromResultSet(resultSet);
                    System.out.println(owner);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating owner: " + e.getMessage());
                }
            }

            System.out.println("Total: " + count + " owner(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Failed to get owners!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // READ BY ID
    public Owner getById(int id) {
        String sql = "SELECT * FROM owners WHERE owner_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Owner owner = extractFromResultSet(resultSet);
                resultSet.close();
                statement.close();
                return owner;
            }

            resultSet.close();
            statement.close();

        } catch (SQLException | InvalidInputException e) {
            System.out.println("✗ Failed to get owner by ID!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    // UPDATE
    public boolean update(Owner owner) {
        String sql = "UPDATE owners SET name = ?, phone = ?, email = ?, address = ?, gender = ? WHERE owner_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, owner.getName());
            statement.setString(2, owner.getPhone());
            statement.setString(3, owner.getEmail());
            statement.setString(4, owner.getAddress());
            statement.setString(5, owner.getGender().toString());
            statement.setInt(6, owner.getId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("✗ Owner update failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM owners WHERE owner_id = ?";
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
        String sql = "SELECT * FROM owners WHERE name ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== OWNERS WITH NAME CONTAINING: '" + name + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Owner owner = extractFromResultSet(resultSet);
                    System.out.println(owner);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating owner: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " owner(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // SEARCH BY PHONE
    public void searchByPhone(String phone) {
        String sql = "SELECT * FROM owners WHERE phone ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + phone + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== OWNERS WITH PHONE CONTAINING: '" + phone + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Owner owner = extractFromResultSet(resultSet);
                    System.out.println(owner);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating owner: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " owner(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Search by phone failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // HELPER METHOD
    private Owner extractFromResultSet(ResultSet resultSet) throws SQLException, InvalidInputException {
        int id = resultSet.getInt("owner_id");
        String name = resultSet.getString("name");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String address = resultSet.getString("address");
        Gender gender = Gender.valueOf(resultSet.getString("gender"));

        return new Owner(id, name, phone, email, address, gender);
    }
}