/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.Habitacion;
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
public class fhabitacion {

    private Conexion mysql = new Conexion();
    private Connection con = mysql.conectar();
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

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return null;
        }
    }

    public boolean insertar(Habitacion datos) {
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

    public boolean editar(Habitacion datos) {

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

    public boolean eliminar(Habitacion datos) {

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
