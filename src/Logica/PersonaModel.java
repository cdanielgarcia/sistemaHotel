/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class PersonaModel {

    private final Conexion conexion = new Conexion();
    private String sSQL = "";
    public Integer totalregistros;

    public List<Persona> mostrar() {
        List<Persona> personas = new ArrayList<>();
        sSQL = "select p.id_persona,p.nombre,p.apaterno,p.amaterno,p.documento,p.num_documento,"
                + "p.direccion,p.telefono,p.email"
                + " from persona p ";
//                + "where id_persona like '% "
//                + buscar + "%' order by idpersona desc";
        try {

            Statement st = conexion.conectar().createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            Persona persona;
            while (rs.next()) {
                persona = new Persona();
                persona.setIdpersona(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApaterno(rs.getString("apaterno"));
                persona.setAmaterno(rs.getString("amaterno"));
                persona.setTipo_documento(rs.getString("DOCUMENTO"));
                persona.setNum_documento(rs.getString("num_documento"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEmail(rs.getString("email"));
                personas.add(persona);
            }

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return personas;
    }

    public boolean insertar(Persona persona) {

        sSQL = "insert into persona (id_persona, nombre,apaterno,amaterno,DOCUMENTO,num_documento,direccion,"
                + "telefono,email) values (persona_pk.nextval, ?,?,?,?,?,?,?,?)";
        try {

            Connection connection = conexion.conectar();
            PreparedStatement ps = connection.prepareStatement(sSQL);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApaterno());
            ps.setString(3, persona.getAmaterno());
            ps.setString(4, persona.getTipo_documento());
            ps.setString(5, persona.getNum_documento());
            ps.setString(6, persona.getDireccion());
            ps.setString(7, persona.getTelefono());
            ps.setString(8, persona.getEmail());

            int n = ps.executeUpdate();

            return n == 1;

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }

    }

    public boolean editar(Persona persona) {

        sSQL = "update persona set nombre=?,apaterno=?,amaterno=?,documento=?,num_documento=?,"
                + "direccion=?,telefono=?,email=? where id_persona=?";
        try {
            PreparedStatement preparedStatement = conexion.conectar().prepareStatement(sSQL);
            preparedStatement.setString(1, persona.getNombre());
            preparedStatement.setString(2, persona.getApaterno());
            preparedStatement.setString(3, persona.getAmaterno());
            preparedStatement.setString(4, persona.getTipo_documento());
            preparedStatement.setString(5, persona.getNum_documento());
            preparedStatement.setString(6, persona.getDireccion());
            preparedStatement.setString(7, persona.getTelefono());
            preparedStatement.setString(8, persona.getEmail());
            preparedStatement.setInt(9, persona.getIdpersona());


            int n = preparedStatement.executeUpdate();

            return n == 1;

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean eliminar(int idPersona) {
        sSQL = "delete from persona where id_persona=?";
        try {

            PreparedStatement preparedStatement = conexion.conectar().prepareStatement(sSQL);

            preparedStatement.setInt(1, idPersona);

            int n = preparedStatement.executeUpdate();

            return n == 1;

        } catch (SQLException e) {
            JOptionPane.showInputDialog(null, e);
            return false;
        }
    }
}
