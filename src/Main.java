import database.DatabaseConnection;
import menu.MenuManager;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // 1. Тестируем соединение с базой данных
        System.out.println("=== Testing Database Connection ===");
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            System.out.println("✓ Database connection successful!");
            DatabaseConnection.closeConnection(connection);
        } else {
            System.out.println("✗ Database connection failed!");
            return;
        }

        // 2. Запускаем меню
        System.out.println("\n=== Starting Vet Clinic Application ===");
        MenuManager menu = new MenuManager();
        menu.run();
    }
}