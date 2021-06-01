package Logica;

import Datos.vhabitacion;
import Datos.vproducto;
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
public class fproducto {

    private Conexion mysql = new Conexion();
    private Connection con = mysql.conectar();
    private String sSQL = "";
    public Integer totalregistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Producto", "Descripcion", "Unidad medidad","Precio venta"};
        String[] registros = new String[5];
        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select * from producto where nombre like '%" + buscar + "%' order by idproducto desc";

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("idproducto");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("descripcion");
                registros[3] = rs.getString("unidad_medida");
                registros[4] = rs.getString("precio_venta");
                

                totalregistros = totalregistros + 1;
                modelo.addRow(registros);

            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return null;
        }
    }

    public boolean insertar(vproducto datos) {
        sSQL = "insert into producto (nombre,descripcion,unidad_medida,precio_venta)"
                + "values (?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getDescripcion());
            ps.setString(3, datos.getUnidad_medida());
            ps.setDouble(4, datos.getPrecio_venta());
           

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

    public boolean editar(vproducto datos) {

        sSQL = "update producto set nombre=?, descripcion=?, unidad_medida=?, precio_venta=?"
                + "where idproducto=?";
        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getDescripcion());
            ps.setString(3, datos.getUnidad_medida());
            ps.setDouble(4, datos.getPrecio_venta());
            ps.setInt(5, datos.getIdproducto());

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

    public boolean eliminar(vproducto datos) {

        sSQL = "delete from producto where idproducto=?";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setInt(1, datos.getIdproducto());

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
