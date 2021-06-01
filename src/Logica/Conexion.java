package Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class Conexion {
    public String db = "basereserva";
//    public String url =  "jdbc:mysql//127.0.0.1/" +db;
    private String url = "jdbc:mysql://127.0.0.1/"+db;

    public String user = "root";
    public String password = "1234";

    public Conexion() {
    }
    
    public Connection conectar(){
        Connection link = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            link = DriverManager.getConnection(this.url, this.user, this.password);
            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
        }
        
        return link;
    }
    
}
