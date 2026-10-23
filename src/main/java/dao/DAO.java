package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/nico_db";

    private static final String USER = "postgres";

    private static final String PASSWORD = "post";

    public Connection getConnection() throws Exception {

        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
//