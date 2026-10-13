/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bdconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class DBConnect {

    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String user = "postgres";
    private static String password = "david";
    static Connection con;

    public static Connection getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                System.out.println("SUCCESS: Database connected successfully!");
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return con;
    }

    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        getDBConnection();
    }

}
