package JavaClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost/fj21", "root", "admin");

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }   
}
