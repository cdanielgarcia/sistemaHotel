package Logica;

import Datos.vhabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class fhabitacion {

    private final Conexion mysql = new Conexion();
    private final Connection con = mysql.conectar();
    private String sSQL = "";
    public Integer totalregistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Numero", "Piso", "Descripcion", "Caracteristicas", "Precio diario", "Estado", "Tipo de habitacion"};
        String[] registros = new String[8];
        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select * from habitacion where piso like '%" + buscar + "%' order by idhabitacion";

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("idhabitacion");
                registros[1] = rs.getString("numero");
                registros[2] = rs.getString("piso");
                registros[3] = rs.getString("descripcion");
                registros[4] = rs.getString("caracteristicas");
                registros[5] = rs.getString("precio_diario");
                registros[6] = rs.getString("estado");
                registros[7] = rs.getString("tipo_habitacion");

                totalregistros = totalregistros + 1;
                modelo.addRow(registros);

            }
            return modelo;

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return null;
        }
    }

    public boolean insertar(vhabitacion datos) {
        sSQL = "insert into habitacion (numero,piso,descripcion,caracteristicas,precio_diario,estado,tipo_habitacion)"
                + "values (?,?,?,?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setString(1, datos.getNumero());
            ps.setString(2, datos.getPiso());
            ps.setString(3, datos.getDescripcion());
            ps.setString(4, datos.getCaracteristicas());
            ps.setDouble(5, datos.getPrecio_diario());
            ps.setString(6, datos.getEstado());
            ps.setString(7, datos.getTipo_habitacion());

            int n = ps.executeUpdate();

            return n != 0;

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    public boolean editar(vhabitacion datos) {

        sSQL = "update habitacion set numero=?, piso=?, descripcion=?, caracteristicas=?, precio_diario=?, estado=?, tipo_habitacion=?"
                + "where idhabitacion=?";
        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setString(1, datos.getNumero());
            ps.setString(2, datos.getPiso());
            ps.setString(3, datos.getDescripcion());
            ps.setString(4, datos.getCaracteristicas());
            ps.setDouble(5, datos.getPrecio_diario());
            ps.setString(6, datos.getEstado());
            ps.setString(7, datos.getTipo_habitacion());
            ps.setInt(8, datos.getIdhabitacion());

            int n = ps.executeUpdate();

            return n != 0;
        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    /*public vhabitacion getHabitationMoreCost() {
        vhabitacion objetoBaseDeDatos = null;
        String query = "SELECT * FROM habitacion;"; 
        List<vhabitacion> lista = new ArrayList<vhabitacion>();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //por cada resultado de la base de datos, hacemos una instancia nueva del objeto
                objetoBaseDeDatos = new vhabitacion();

                objetoBaseDeDatos.setIdhabitacion(resultSet.getInt("idhabitacion"));
                objetoBaseDeDatos.setNumero(resultSet.getString("numero"));
                objetoBaseDeDatos.setPiso(resultSet.getString("piso"));
                objetoBaseDeDatos.setDescripcion(resultSet.getString("descripcion"));
                objetoBaseDeDatos.setCaracteristicas(resultSet.getString("caracteristicas"));
                objetoBaseDeDatos.setPrecio_diario(resultSet.getDouble("precio_diario"));
                objetoBaseDeDatos.setEstado(resultSet.getString("estado"));
                objetoBaseDeDatos.setTipo_habitacion(resultSet.getString("tipo_habitacion"));
                //Metemos el objeto vhabitacion en la lista
                lista.add(objetoBaseDeDatos);
            }
        } catch (SQLException exception) {
            System.out.println("Exception en metodo obtenerHabitacionMasCostosa " + exception.getMessage());
        }

        return lista.stream()
                .max(Comparator.comparingDouble(vhabitacion::getPrecio_diario))
                .orElse(null);

    }*/

    public boolean eliminar(vhabitacion datos) {

        sSQL = "delete from habitacion where idhabitacion=?";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setInt(1, datos.getIdhabitacion());

            int n = ps.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

}
