package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Alojamiento;


public class AlojamientoData {
    
    private Connection conexion = null;  
    private DestinoData destinoData;

    public AlojamientoData(ConectarBD conexionParam) {
        this.conexion = conexionParam.getConexion();
        this.destinoData= new DestinoData(conexionParam);
        
}
    //-----------------------------------------------------------------------------------------------------------------------------Guardar Alojamiento          
     public boolean guardarAlojamiento(Alojamiento alojamiento){
        
        boolean estado = false;
        
        String query = "INSERT INTO alojamiento(idDestino, tipoAlojamiento, costo, nombre, activo)"
                     + " VALUES (?,?,?,?,?)";                            
        
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS ); 
                        
            prepSt.setInt(1, alojamiento.getDestino().getIdDestino());   
            prepSt.setString(2, alojamiento.getTipoAlojamiento());    
            prepSt.setFloat(3, alojamiento.getCosto());    
            prepSt.setString(4, alojamiento.getNombre());                 
            prepSt.setBoolean(5, alojamiento.isActivo()); 
      
            prepSt.executeUpdate();                                         
            
            ResultSet rs = prepSt.getGeneratedKeys();                
            
            if (rs.next()){
                alojamiento.setIdAlojamiento(rs.getInt(1));                         
                estado = true;
            }
         
            prepSt.close();                                               
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "El Alojamiento se no puedo guardar" + ex);
        }
                    
       return estado;                                                          
    }   
        
    //-----------------------------------------------------------------------------------------------------------------------------Actualizar Alojamiento      
    public boolean actualizarAlojamiento(Alojamiento alojamiento){
          
            boolean estado = false ;                                          
        
        String query = "UPDATE alojamiento "
                            + "SET   idDestino = ?, tipoAlojamiento = ?, costo = ?, activo = ? "
                            + "WHERE idAlojamiento = ?";
                    
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query); 
            prepSt.setInt(1, alojamiento.getDestino().getIdDestino());
            prepSt.setString(2, alojamiento.getTipoAlojamiento());
            prepSt.setFloat(3, alojamiento.getCosto());                 
            prepSt.setBoolean(4, alojamiento.isActivo()); 
            prepSt.setInt(5, alojamiento.getIdAlojamiento()); 
                  
            if( prepSt.executeUpdate() > 0){                              
                estado = true;
            }        
            
            prepSt.close();                                               
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al actualizar el alojamiento" + ex); 
        }
        return estado;     
       
    }
    //----------------------------------------------------------------------------------------------------------------------------DESACTIVAR ALOJAMIENTO 
    public boolean desactivarAlojamiento(int id){
        boolean estado = false ;                                          
        
        String query = "UPDATE alojamiento "
                            + "SET activo = false "
                            + "WHERE idAlojamiento = ? ";
                                                 
                    
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query); 
                  
            prepSt.setInt(1, id ); 
                  
            if( prepSt.executeUpdate() > 0){                                
               estado = true;
            }     
            
            prepSt.close();                                                 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al desactivar el alojamiento" + ex); 
        }
       return estado;   
    }  
    //------------------------------------------------------------------------------------------------------------------------------OBTENER ALOJAMIENTO POR ID
    public Alojamiento obtenerAlojamientoXId (int id){
          Alojamiento alojamientoObj = null;
          String query = "SELECT * "
                              + "FROM alojamiento "
                              + "WHERE idAlojamiento = ? ";                         
            
          try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setInt(1, id);
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    alojamientoObj = new Alojamiento();
                    alojamientoObj.setIdAlojamiento(resultSet.getInt("idAlojamiento"));
                    alojamientoObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    alojamientoObj.setTipoAlojamiento(resultSet.getString("tipoAlojamiento"));
                    alojamientoObj.setCosto(resultSet.getFloat("costo"));
                    alojamientoObj.setNombre(resultSet.getString("nombre"));
                    alojamientoObj.setActivo(resultSet.getBoolean("activo"));
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar el Alojamiento x id" + ex); 
            }

        return alojamientoObj;
    }
    //-----------------------------------------------------------------------------------------------------------------------------OBTENER ALOJAMIENTO POR TIPO
    public Alojamiento obtenerAlojamientoXTipo (String tipo){
          Alojamiento alojamientoObj = null;
          String query = "SELECT * "
                              + "FROM alojamiento "
                              + "WHERE tipoAlojamiento = ? ";                         
            try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setString(1, tipo);
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    alojamientoObj = new Alojamiento();
                    alojamientoObj.setIdAlojamiento(resultSet.getInt("idAlojamiento"));
                    alojamientoObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    alojamientoObj.setTipoAlojamiento(resultSet.getString("tipoAlojamiento"));
                    alojamientoObj.setCosto(resultSet.getFloat("costo"));
                    alojamientoObj.setNombre(resultSet.getString("nombre"));
                    alojamientoObj.setActivo(resultSet.getBoolean("activo"));
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar el Alojamiento" + ex); 
            }

        return alojamientoObj;
    }
    //---------------------------------------------------------------------------------------------------------------------------OBTENER TODOS LOS ALOJAMIENTO
    public ArrayList<Alojamiento> obtenerTodosLosAlojamiento(){
        
          Alojamiento alojamientoObj = null;
          ArrayList<Alojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM alojamiento ";
                              
                                                       
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    alojamientoObj = new Alojamiento();
                    alojamientoObj.setIdAlojamiento(resultSet.getInt("idAlojamiento"));
                    alojamientoObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    alojamientoObj.setTipoAlojamiento(resultSet.getString("tipoAlojamiento"));
                    alojamientoObj.setCosto(resultSet.getFloat("costo"));
                    alojamientoObj.setNombre(resultSet.getString("nombre"));
                    alojamientoObj.setActivo(resultSet.getBoolean("activo"));
                    extraLista.add(alojamientoObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Alojamiento" + ex); 
            }
   
        return extraLista; 
    }
    //---------------------------------------------------------------------------------------------------------------------------OBTENER TODOS LOS ALOJAMIENTO
    public ArrayList<Alojamiento> obtenerTodosLosAlojamientoActivos(){
            
          Alojamiento alojamientoObj = null;
          ArrayList<Alojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM alojamiento "
                              + "WHERE activo = true";
         
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    alojamientoObj = new Alojamiento();
                    alojamientoObj.setIdAlojamiento(resultSet.getInt("idAlojamiento"));
                    alojamientoObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    alojamientoObj.setTipoAlojamiento(resultSet.getString("tipoAlojamiento"));
                    alojamientoObj.setCosto(resultSet.getFloat("costo"));
                    alojamientoObj.setNombre(resultSet.getString("nombre"));
                    alojamientoObj.setActivo(resultSet.getBoolean("activo"));
                    extraLista.add(alojamientoObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Alojamiento" + ex); 
            }
   
        return extraLista; 
    }
    //---------------------------------------------------------------------------------------------------------------------------OBTENER TODOS LOS ALOJAMIENTO NO ACTIVOS
    public ArrayList<Alojamiento> obtenerTodosLosAlojamientoNoActivos(){
                 
          Alojamiento alojamientoObj = null;
          ArrayList<Alojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM alojamiento "
                              + "WHERE activo = false";
         
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    alojamientoObj = new Alojamiento();
                    
                    alojamientoObj.setIdAlojamiento(resultSet.getInt("idAlojamiento"));
                    alojamientoObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    alojamientoObj.setTipoAlojamiento(resultSet.getString("tipoAlojamiento"));
                    alojamientoObj.setCosto(resultSet.getFloat("costo"));
                    alojamientoObj.setNombre(resultSet.getString("nombre"));
                    alojamientoObj.setActivo(resultSet.getBoolean("activo"));
                    extraLista.add(alojamientoObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de alojamientos" + ex); 
            }
   
        return extraLista; 
    }
     
    //---------------------------------------------------------------------------------------------------------------------------OBTENER TODOS LOS ALOJAMIENTO POR DESTINO
    public ArrayList<Alojamiento> obtenerTodosLosAlojamientoXDestino(int destino){
                  
          Alojamiento alojamientoObj = null;
          ArrayList<Alojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM alojamiento "
                              + "WHERE idDestino = ?";
         
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                prepSt.setInt(1, destino);
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    alojamientoObj = new Alojamiento();
                    alojamientoObj.setIdAlojamiento(resultSet.getInt("idAlojamiento"));
                    alojamientoObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    alojamientoObj.setTipoAlojamiento(resultSet.getString("tipoAlojamiento"));
                    alojamientoObj.setCosto(resultSet.getFloat("costo"));
                    alojamientoObj.setNombre(resultSet.getString("nombre"));
                    alojamientoObj.setActivo(resultSet.getBoolean("activo"));
                    extraLista.add(alojamientoObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Alojamientos" + ex); 
            }
   
        return extraLista; 
    }
     
 
}
