package DB;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionConfiguration {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
