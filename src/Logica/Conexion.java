package Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class Conexion {

    static Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Conexion() {
    }

    public Connection conectar() {
        Connection conexion = null;
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "SYSTEM", "1234");
            if (conexion == null) {
                System.out.println("No hay conexion");
            } else {
                return conexion;
            }
        } catch (SQLException e) {
            System.out.println("conexion error: " + e.getMessage());
        }
        return conexion;
    }

}
