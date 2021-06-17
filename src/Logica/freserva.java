package Logica;

import Datos.vhabitacion;
import Datos.vproducto;
import Datos.vreserva;
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
public class freserva {

    private Conexion mysql = new Conexion();
    private Connection con = mysql.conectar();
    private String sSQL = "";
    public Integer totalregistros;

    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"Id", "Idhabitacion", "Numero", "IdCliente", "Cliente","IdTrabajador","Trabajador", 
            "TipoReserva", "FechaReserva","FechaIngreso", "FechaSalida", "Costo", "Estado"};
        String[] registros = new String[13];
        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "select r.idreserva,r.idhabitacion,h.numero,r.idcliente,"
                + "(select nombre from persona where idpersona=r.idcliente)as clienten,"
                + "(select apaterno from persona where idpersona=r.idcliente)as clienteap,"
                + "r.idtrabajador,(select nombre from persona where idpersona=r.idtrabajador)as trabajadorn,"
                + "(select apaterno from persona where idpersona=r.idtrabajador)as trabajadorap,"
                + "r.tipo_reserva,r.fecha_reserva,r.fecha_ingresa,r.fecha_salida,r.costo_alojamiento,"
                + "r.estado from reserva r inner join habitacion h on r.idhabitacion=h.idhabitacion "
                + "where r.fecha_reserva like '%" + buscar + "%' order by idreserva desc";

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {

                registros[0] = rs.getString("idreserva");
                registros[1] = rs.getString("idhabitacion");
                registros[2] = rs.getString("numero");
                registros[3] = rs.getString("idcliente");
                registros[4] = rs.getString("clienten") + " " + rs.getString("clienteap");
                registros[5] = rs.getString("idtrabajador");
                registros[6] = rs.getString("trabajadorn") + " " + rs.getString("trabajadorap");;
                registros[7] = rs.getString("tipo_reserva");
                registros[8] = rs.getString("fecha_reserva");
                registros[9] = rs.getString("fecha_ingresa");
                registros[10] = rs.getString("fecha_salida");
                registros[11] = rs.getString("costo_alojamiento");
                registros[12] = rs.getString("estado");
                

                totalregistros = totalregistros + 1;
                modelo.addRow(registros);

            }
            return modelo;

        } catch (Exception e) {
            JOptionPane.showInputDialog(null, e);
            return null;
        }
    }

    public boolean insertar(vreserva datos) {
        sSQL = "insert into reserva (idhabitacion,idcliente,idtrabajador,tipo_reserva,fecha_reserva,"
                + "fecha_ingresa,fecha_salida,costo_alojamiento,estado)"
                + " values (?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setInt(1, datos.getIdhabitacion());
            ps.setInt(2, datos.getIdcliente());
            ps.setInt(3, datos.getIdtrabajador());
            ps.setString(4, datos.getTipo_reserva());
            ps.setDate(5, datos.getFecha_reserva());
            ps.setDate(6, datos.getFecha_ingresa());
            ps.setDate(7, datos.getFecha_salida());
            ps.setDouble(8, datos.getCosto_alojamiento());
            ps.setString(9, datos.getEstado());
           

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

    public boolean editar(vreserva datos) {

        sSQL = "update reserva set idhabitacion=?,idcliente=?,idtrabajador=?,tipo_reserva=?,"
                + "fecha_reserva=?,fecha_ingresa=?,fecha_salida=?,costo_alojamiento=?,estado=?"
                + " where idreserva=?";
        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setInt(1, datos.getIdhabitacion());
            ps.setInt(2, datos.getIdcliente());
            ps.setInt(3, datos.getIdtrabajador());
            ps.setString(4, datos.getTipo_reserva());
            ps.setDate(5, datos.getFecha_reserva());
            ps.setDate(6, datos.getFecha_ingresa());
            ps.setDate(7, datos.getFecha_salida());
            ps.setDouble(8, datos.getCosto_alojamiento());
            ps.setString(9, datos.getEstado());
            ps.setInt(10, datos.getIdreserva());
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

    public boolean eliminar(vreserva datos) {

        sSQL = "delete from reserva where idreserva=?";

        try {

            PreparedStatement ps = con.prepareStatement(sSQL);
            ps.setInt(1, datos.getIdreserva());

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
