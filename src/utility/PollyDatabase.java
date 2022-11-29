package utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PollyDatabase {

    private static String DATABASE_NAME = "pollygraydb";
    private static String DBMS = "mysql";
    private static String HOST = "localhost";
    private static String PORT = "3306";
    private static String USER = "root";
    private static String PASSWORD = "";

    private static Connection connection = null;

    static {
        //startServer();
        String URL = String.format("jdbc:%s://%s:%s/%s", DBMS, HOST, PORT, DATABASE_NAME);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("[DEBUG] CONNECTION CREATED");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void startServer(){
        String command = "CD c:\\xampp\\mysql\\bin\n" +
                "&& mysql -u root -p && ''";

        try
        {
            Process process = Runtime.getRuntime().exec(command);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private PollyDatabase() {}
}
