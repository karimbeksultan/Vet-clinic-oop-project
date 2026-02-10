package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VetclinicDao {

    public boolean insert(Pet patient) throws SQLException {
        String sql = """
            INSERT INTO pets (name, vaccinated, animal_type, age, breed, gender, trained, indoor, has_scales)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            fillStatementFromPet(ps, patient);
            return ps.executeUpdate() == 1;
        }
    }

    public List<Pet> findAll() throws SQLException {
        String sql = "SELECT * FROM pets ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Pet> list = new ArrayList<>();
            while (rs.next()) list.add(mapRowToPet(rs));
            return list;
        }
    }

    public List<Pet> findByType(String type) throws SQLException {
        String sql = "SELECT * FROM pets WHERE animal_type = ? ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                List<Pet> list = new ArrayList<>();
                while (rs.next()) list.add(mapRowToPet(rs));
                return list;
            }
        }
    }

    public Pet findById(long id) throws SQLException {
        String sql = "SELECT * FROM pets WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapRowToPet(rs);
            }
        }
    }

    public boolean update(Pet patient) throws SQLException {
        String sql = """
            UPDATE pets
               SET name = ?, vaccinated = ?, animal_type = ?, age = ?, breed = ?, gender = ?, trained = ?, indoor = ?, has_scales = ?
             WHERE id = ?
        """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, patient.getName());
            ps.setBoolean(2, patient.isVaccinated());

            Animal a = patient.getAnimal();
            String type = a.getClass().getSimpleName().toUpperCase();

            ps.setString(3, type);
            ps.setInt(4, a.getAge());
            ps.setString(5, a.getBreed());
            ps.setString(6, a.getGender().name());

            ps.setObject(7, null);
            ps.setObject(8, null);
            ps.setObject(9, null);

            if (a instanceof Dog d) ps.setBoolean(7, d.isTrained());
            if (a instanceof Cat ccat) ps.setBoolean(8, ccat.isIndoor());
            if (a instanceof Fish f) ps.setBoolean(9, f.hasScales());

            ps.setLong(10, patient.getId());

            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public List<Pet> searchByName(String part) throws SQLException {
        String sql = "SELECT * FROM pets WHERE name ILIKE ? ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + part + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Pet> list = new ArrayList<>();
                while (rs.next()) list.add(mapRowToPet(rs));
                return list;
            }
        }
    }

    public List<Pet> searchByAgeRange(int min, int max) throws SQLException {
        String sql = "SELECT * FROM pets WHERE age BETWEEN ? AND ? ORDER BY age DESC";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, min);
            ps.setInt(2, max);

            try (ResultSet rs = ps.executeQuery()) {
                List<Pet> list = new ArrayList<>();
                while (rs.next()) list.add(mapRowToPet(rs));
                return list;
            }
        }
    }

    public List<Pet> searchByMinAge(int minAge) throws SQLException {
        String sql = "SELECT * FROM pets WHERE age >= ? ORDER BY age DESC";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, minAge);

            try (ResultSet rs = ps.executeQuery()) {
                List<Pet> list = new ArrayList<>();
                while (rs.next()) list.add(mapRowToPet(rs));
                return list;
            }
        }
    }

    private void fillStatementFromPet(PreparedStatement ps, Pet patient) throws SQLException {
        ps.setString(1, patient.getName());
        ps.setBoolean(2, patient.isVaccinated());

        Animal a = patient.getAnimal();
        String type = a.getClass().getSimpleName().toUpperCase();

        ps.setString(3, type);
        ps.setInt(4, a.getAge());
        ps.setString(5, a.getBreed());
        ps.setString(6, a.getGender().name());

        ps.setObject(7, null);
        ps.setObject(8, null);
        ps.setObject(9, null);

        if (a instanceof Dog d) ps.setBoolean(7, d.isTrained());
        else if (a instanceof Cat c) ps.setBoolean(8, c.isIndoor());
        else if (a instanceof Fish f) ps.setBoolean(9, f.hasScales());
    }

    private Pet mapRowToPet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        boolean vaccinated = rs.getBoolean("vaccinated");

        String type = rs.getString("animal_type");
        int age = rs.getInt("age");
        String breed = rs.getString("breed");
        Gender gender = Gender.valueOf(rs.getString("gender"));

        Animal animal;
        switch (type.toUpperCase()) {
            case "DOG" -> animal = new Dog(age, breed, gender, rs.getBoolean("trained"));
            case "CAT" -> animal = new Cat(age, breed, gender, rs.getBoolean("indoor"));
            case "FISH" -> animal = new Fish(age, breed, gender, rs.getBoolean("has_scales"));
            default -> throw new IllegalArgumentException("Unknown animal_type in DB: " + type);
        }

        return new Pet(id, name, vaccinated, animal);
    }
}