package database;

import model.Veterinarian;
import exception.InvalidInputException;
import java.sql.*;

public class VeterinarianDAO {

    // CREATE
    public boolean insert(Veterinarian vet) {
        String sql = "INSERT INTO veterinarians (name, phone, email, specialization, experience) VALUES (?, ?, ?, ?, ?)";
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

    // READ ALL
    public void displayAll() {
        String sql = "SELECT * FROM veterinarians ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== ALL VETERINARIANS FROM DATABASE ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Veterinarian vet = extractFromResultSet(resultSet);
                    System.out.println(vet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating vet: " + e.getMessage());
                }
            }

            System.out.println("Total: " + count + " veterinarian(s)");
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("✗ Failed to get veterinarians!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // READ BY ID
    public Veterinarian getById(int id) {
        String sql = "SELECT * FROM veterinarians WHERE vet_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Veterinarian vet = extractFromResultSet(resultSet);
                resultSet.close();
                statement.close();
                return vet;
            }

            resultSet.close();
            statement.close();

        } catch (SQLException | InvalidInputException e) {
            System.out.println("✗ Failed to get veterinarian by ID!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    // UPDATE
    public boolean update(Veterinarian vet) {
        String sql = "UPDATE veterinarians SET name = ?, phone = ?, email = ?, specialization = ?, experience = ? WHERE vet_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, vet.getName());
            statement.setString(2, vet.getPhone());
            statement.setString(3, vet.getEmail());
            statement.setString(4, vet.getSpecialization());
            statement.setInt(5, vet.getExperience());
            statement.setInt(6, vet.getId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("✗ Veterinarian update failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM veterinarians WHERE vet_id = ?";
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
        String sql = "SELECT * FROM veterinarians WHERE name ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== VETERINARIANS WITH NAME CONTAINING: '" + name + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Veterinarian vet = extractFromResultSet(resultSet);
                    System.out.println(vet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating vet: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " veterinarian(s)");
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
        String sql = "SELECT * FROM veterinarians WHERE phone ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + phone + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n=== VETERINARIANS WITH PHONE CONTAINING: '" + phone + "' ===");
            int count = 0;
            while (resultSet.next()) {
                try {
                    Veterinarian vet = extractFromResultSet(resultSet);
                    System.out.println(vet);
                    System.out.println("---");
                    count++;
                } catch (InvalidInputException e) {
                    System.out.println("Error creating vet: " + e.getMessage());
                }
            }

            System.out.println("Found: " + count + " veterinarian(s)");
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
    private Veterinarian extractFromResultSet(ResultSet resultSet) throws SQLException, InvalidInputException {
        int id = resultSet.getInt("vet_id");
        String name = resultSet.getString("name");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String specialization = resultSet.getString("specialization");
        int experience = resultSet.getInt("experience");

        return new Veterinarian(id, name, phone, email, specialization, experience);
    }
}