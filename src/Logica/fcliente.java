package Logica;

import Datos.vcliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class fcliente {

    private final Conexion mysql = new Conexion();
    private final Connection con = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalregistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Nombre", "Apaterno", "Amaterno", "Doc", "Numero Documento", "Direccion",
            "Telefono", "Email", "Codigo"};
        String[] registros = new String[10];
        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select p.idpersona,p.nombre,p.apaterno,p.amaterno,p.tipo_documento,p.num_documento,"
                + "p.direccion,p.telefono,p.email,c.codigo_cliente from persona p inner join cliente c "
                + "on p.idpersona=c.idpersona where num_documento like '%"
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
                registros[9] = rs.getString("codigo_cliente");

                totalregistros = totalregistros + 1;
                modelo.addRow(registros);

            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return null;
        }
    }

    public boolean insertar(vcliente datos) {
        sSQL = "insert into persona (nombre,apaterno,amaterno,tipo_documento,num_documento,direccion,"
                + "telefono,email) values (?,?,?,?,?,?,?,?)";
        sSQL2 = "insert into cliente (idpersona,codigo_cliente)"
                + "values ((select idpersona from persona order by idpersona desc limit 1),?)";

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

            ps2.setString(1, datos.getCodigo_cliente());

            int n = ps.executeUpdate();

            if (n != 0) {
                int result = ps2.executeUpdate();

                return result != 0;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    public boolean editar(vcliente datos) {

        sSQL = "update persona set nombre=?,apaterno=?,amaterno=?,tipo_documento=?,num_documento=?,"+
                "direccion=?,telefono=?,email=? where idpersona=?";
        sSQL2 = "update cliente set codigo_cliente=? where idpersona=?";
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

            ps2.setString(1, datos.getCodigo_cliente());
            ps2.setInt(2, datos.getIdpersona());

            int n = ps.executeUpdate();

            if (n != 0) {
                int n2 = ps2.executeUpdate();

                return n2 != 0;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    public boolean eliminar(vcliente datos) {

        sSQL = "delete from cliente where idpersona=?";
        sSQL2 = "delete from persona where idpersona=?";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            PreparedStatement ps2 = con.prepareStatement(sSQL2);

            
            ps.setInt(1, datos.getIdpersona());

            ps2.setInt(1, datos.getIdpersona());

            int n = ps.executeUpdate();

            if (n != 0) {
                int n2 = ps2.executeUpdate();

                return n2 != 0;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }
    }

}
