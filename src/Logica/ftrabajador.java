package Logica;

import Datos.vcliente;
import Datos.vtrabajador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ftrabajador {
    
    private Conexion mysql = new Conexion();
    private Connection con = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalregistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Nombre", "Apaterno", "Amaterno", "Doc", "Numero Documento", "Direccion",
            "Telefono", "Email", "Sueldo", "Acceso", "Login", "Clave", "Estado"};
        String[] registros = new String[14];
        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select p.idpersona,p.nombre,p.apaterno,p.amaterno,p.tipo_documento,p.num_documento,"
                + "p.direccion,p.telefono,p.email,t.sueldo,t.acceso,t.login,t.password,t.estado"
                + " from persona p inner join trabajador t "
                + "on p.idpersona=t.idpersona where num_documento like '%"
                + buscar + "%' order by idpersona desc";

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("idpersona");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("apaterno");
                registros[3] = rs.getString("amaterno");
                registros[4] = rs.getString("tipo_documento");
                registros[5] = rs.getString("num_documento");
                registros[6] = rs.getString("direccion");
                registros[7] = rs.getString("telefono");
                registros[8] = rs.getString("email");
                registros[9] = rs.getString("sueldo");
                registros[10] = rs.getString("acceso");
                registros[11] = rs.getString("login");
                registros[12] = rs.getString("password");
                registros[13] = rs.getString("estado");

                totalregistros = totalregistros + 1;
                modelo.addRow(registros);

            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return null;
        }
    }

    public boolean insertar(vtrabajador datos) {
        sSQL = "insert into persona (nombre,apaterno,amaterno,tipo_documento,num_documento,direccion,"
                + "telefono,email) values (?,?,?,?,?,?,?,?)";
        sSQL2 = "insert into trabajador (idpersona,sueldo,acceso,login,password,estado)"
                + " values ((select idpersona from persona order by idpersona desc limit 1),?,?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            PreparedStatement ps2 = con.prepareStatement(sSQL2);

            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getApaterno());
            ps.setString(3, datos.getAmaterno());
            ps.setString(4, datos.getTipo_documento());
            ps.setString(5, datos.getNum_documento());
            ps.setString(6, datos.getDireccion());
            ps.setString(7, datos.getTelefono());
            ps.setString(8, datos.getEmail());

            ps2.setDouble(1, datos.getSueldo());
            ps2.setString(2, datos.getAcceso());
            ps2.setString(3, datos.getLogin());
            ps2.setString(4, datos.getPassword());
            ps2.setString(5, datos.getEstado());
            
            int n = ps.executeUpdate();

            if (n != 0) {
                int n2 = ps2.executeUpdate();

                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    public boolean editar(vtrabajador datos) {

        sSQL = "update persona set nombre=?,apaterno=?,amaterno=?,tipo_documento=?,num_documento=?,"+
                "direccion=?,telefono=?,email=? where idpersona=?";
        sSQL2 = "update trabajador set sueldo=?,acceso=?,login=?,password=?,estado=? where idpersona=?";
        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            PreparedStatement ps2 = con.prepareStatement(sSQL2);

            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getApaterno());
            ps.setString(3, datos.getAmaterno());
            ps.setString(4, datos.getTipo_documento());
            ps.setString(5, datos.getNum_documento());
            ps.setString(6, datos.getDireccion());
            ps.setString(7, datos.getTelefono());
            ps.setString(8, datos.getEmail());
            ps.setInt(9, datos.getIdpersona());

            ps2.setDouble(1, datos.getSueldo());
            ps2.setString(2, datos.getAcceso());
            ps2.setString(3, datos.getLogin());
            ps2.setString(4, datos.getPassword());
            ps2.setString(5, datos.getEstado());
            ps2.setInt(6, datos.getIdpersona());

            int n = ps.executeUpdate();

            if (n != 0) {
                int n2 = ps2.executeUpdate();

                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    public boolean eliminar(vtrabajador datos) {

        sSQL = "delete from trabajador where idpersona=?";
        sSQL2 = "delete from persona where idpersona=?";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            PreparedStatement ps2 = con.prepareStatement(sSQL2);

            
            ps.setInt(1, datos.getIdpersona());

            ps2.setInt(1, datos.getIdpersona());

            int n = ps.executeUpdate();

            if (n != 0) {
                int n2 = ps2.executeUpdate();

                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }
    }
    
}
