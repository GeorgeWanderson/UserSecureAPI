package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=your_database_name";
    private static final String USER = "sa";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        try {
            // Estabelece a conexão com o banco de dados
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Em caso de erro, imprime o erro e lança uma exceção
            e.printStackTrace();
            throw new SQLException("Failed to establish database connection.", e);
        }
    }
}
