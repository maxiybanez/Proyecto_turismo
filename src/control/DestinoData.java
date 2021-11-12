package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import modelo.Destino;



public class DestinoData {

    private Destino destino;
    private Connection conexion = null;

    public DestinoData(ConectarBD conexionParam) {

        this.conexion = conexionParam.getConexion();
    }

    public boolean guardarDestino(Destino destinoParam) { // en este metodo se crea el destino y se guarda en la BD

        boolean estado = false;

        String query = " INSERT INTO destino(nombre, pais, activo) "
                + " VALUES (?, ?, ?) ";

        try {

            PreparedStatement prepDes = conexion.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);

            prepDes.setString(1, destinoParam.getNombre());
            prepDes.setString(2, destinoParam.getPais());
            prepDes.setBoolean(3, destinoParam.isActivo());

            prepDes.executeUpdate();

            ResultSet resultSet = prepDes.getGeneratedKeys();

            if (resultSet.next()) {
                destinoParam.setIdDestino(resultSet.getInt(1));
                estado = true;
              
            }

            prepDes.close();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "No Se Puede Elegir Destino" + ex);
        }

        return estado;
    }

    public boolean actualizarDestino(Destino destinoParam) {
        boolean estado = false;

        String query = "UPDATE destino "
                + "SET  nombre = ?, pais = ?, activo = ? "
                + "WHERE idDestino = ?";

        try {

            PreparedStatement prepSt = conexion.prepareStatement(query);

            prepSt.setString(1, destinoParam.getNombre());
            prepSt.setString(2, destinoParam.getPais());

            prepSt.setBoolean(3, destinoParam.isActivo());

            prepSt.setInt(4, destinoParam.getIdDestino());

            if (prepSt.executeUpdate() > 0) {
                estado = true;
            }

            prepSt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cambiar Destino!" + ex);
        }
        return estado;

    }

    public Destino buscarDestinoXNombre(String nombreDestParam) {  // buscamos a destino por nombre
       
        Destino desObj = null;
        
        String query = "SELECT * "
                + "FROM destino "
                + "WHERE nombre = ? ";

        try {

            PreparedStatement prepSt = conexion.prepareStatement(query);
            
            prepSt.setString(1, nombreDestParam);

            ResultSet resultSet = prepSt.executeQuery();

            while (resultSet.next()) {
                
                desObj = new Destino();
                
                desObj.setIdDestino(resultSet.getInt("idDestino"));
                desObj.setNombre(resultSet.getString("nombre"));
                desObj.setPais(resultSet.getString("pais"));

                desObj.setActivo(resultSet.getBoolean("activo"));
                        
            }

                prepSt.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR DESTINO X NOMBRE!" + ex);
            }
        
        return desObj;

        
    }

    public boolean desactivarDestinoXId(int idDestinoParam) {
        boolean estado = false;

        String query = "UPDATE destino "
                + "SET activo = false "
                + "WHERE idDestino = ? ";

        try {

            PreparedStatement prepSt = conexion.prepareStatement(query);

            prepSt.setInt(1, idDestinoParam);

            if (prepSt.executeUpdate() > 0) {
                estado = true;
            }

            prepSt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al desactivar destino!" + ex);
        }
        return estado;
    }

    
    
    public Destino buscarDestinoXid(int idDestinoParam) {  // buscamos a tipo de destino x id 
        Destino desObj = null;
        
        String query = "SELECT * "
                + "FROM destino "
                + "WHERE idDestino = ? ";

        try {

            PreparedStatement prepSt = conexion.prepareStatement(query);
            prepSt.setInt(1, idDestinoParam);

            ResultSet resultSet = prepSt.executeQuery();

            while (resultSet.next()) {

                desObj = new Destino();

                desObj.setIdDestino(resultSet.getInt("idDestino"));
                desObj.setNombre(resultSet.getString("nombre"));
                desObj.setPais(resultSet.getString("pais"));

                desObj.setActivo(resultSet.getBoolean("activo"));
            }

            prepSt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL BUSCAR DESTINO X ID!" + ex);
        }
        return desObj;

    }

    
    
    public ArrayList<Destino> obtenerTodosLosDestinos() {
        Destino destinoObj = null;
        ArrayList<Destino> destinoLista = new ArrayList<>();

        String sentenciaSql = "SELECT * "
                + "FROM destino ";
                

        try {

            PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql);

            ResultSet resultSet = prepSt.executeQuery();

            while (resultSet.next()) {

                destinoObj = new Destino();

                destinoObj.setIdDestino(resultSet.getInt("idDestino"));
                destinoObj.setNombre(resultSet.getString("nombre"));
                destinoObj.setPais(resultSet.getString("pais"));

                destinoObj.setActivo(resultSet.getBoolean("activo"));

                destinoLista.add(destinoObj);
            }

            prepSt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener todos los destinos!" + ex);
        }

        return destinoLista;

    }

    
    
    
    public ArrayList<Destino> obtenerTodosLosDestinosActivos() { // activos

        Destino destinoObj = null;
        ArrayList<Destino> destinoLista = new ArrayList<>();

        String sentenciaSql = "SELECT * "
                + "FROM destino "
                + "WHERE activo = true ";

        try {

            PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql);

            ResultSet resultSet = prepSt.executeQuery();

            while (resultSet.next()) {

                destinoObj = new Destino();

                destinoObj.setIdDestino(resultSet.getInt("idDestino"));
                destinoObj.setNombre(resultSet.getString("nombre"));
                destinoObj.setPais(resultSet.getString("pais"));

                destinoObj.setActivo(resultSet.getBoolean("activo"));

                destinoLista.add(destinoObj);
            }

            prepSt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener todos los de destinos activos!" + ex);
        }

        return destinoLista;

    }

    public ArrayList<Destino> obtenerTodosLosDestinosNoActivos() {
               Destino destinoObj = null;
        ArrayList<Destino> destinoLista = new ArrayList<>();

        String sentenciaSql = "SELECT * "
                + "FROM destino "
                + "WHERE activo = false ";

        try {

            PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql);

            ResultSet resultSet = prepSt.executeQuery();

            while (resultSet.next()) {

                destinoObj = new Destino();

                destinoObj.setIdDestino(resultSet.getInt("idDestino"));
                destinoObj.setNombre(resultSet.getString("nombre"));
                destinoObj.setPais(resultSet.getString("pais"));

                destinoObj.setActivo(resultSet.getBoolean("activo"));

                destinoLista.add(destinoObj);
            }

            prepSt.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener todos los de destinos no activos!" + ex);
        }

        return destinoLista;

    }
    
  

}
