package model.util;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static final String URL = "jdbc:mysql://localhost:3306/cadastro_de_eventos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static java.sql.Connection getConnection() throws SQLException {

          return  DriverManager.getConnection(URL, USER, PASSWORD);

    }

}