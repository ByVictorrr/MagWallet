package DB;

import Wallet.Wallet;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfiguration {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://ec2-13-59-188-28.us-east-2.compute.amazonaws.com:3306/phpmyadmin", "victor", "angels33");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean create_new_user(Wallet wall){
        int result;
        JSONObject o = new JSONObject();
        try {
            o.put("Cards", "empty");
            getConnection().createStatement().executeUpdate("INSERT INTO Wallet " + "VALUES ('" + wall.getUser_name() + "','"+ wall.getPassword() + "','" + o.toString() + "')");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



}
