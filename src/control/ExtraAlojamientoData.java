package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Alojamiento;
import modelo.ExtraAlojamiento;

/**
 *
 * @author Maxi Ybañez
 */
public class ExtraAlojamientoData {
    
    private Connection conexion = null;    
    private AlojamientoData alojamientoData;
   
    public ExtraAlojamientoData(ConectarBD conexionParam) {
         this.conexion = conexionParam.getConexion();
         this.alojamientoData= new AlojamientoData(conexionParam);
}
    //-----------------------------------------------------------------------------------------------------------------------------Guardar Extra alojamiento    
     public boolean guardarExtraAlo(ExtraAlojamiento extraAlo){
        
        boolean estado = false;
        
        String query = "INSERT INTO extraalojamiento(idAlojamiento, tipoDeMenu, costo, activo)"
                     + " VALUES (?,?,?,?)";                            
        
        try {
            PreparedStatement prepSt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS );     
            
            prepSt.setInt(1, extraAlo.getAlojameinto().getIdAlojamiento());  
            prepSt.setString(2, extraAlo.getTipoMenu());  
            prepSt.setFloat(3, extraAlo.getCosto());                 
            prepSt.setBoolean(4, extraAlo.isActivo()); 
            prepSt.executeUpdate();      
            
            ResultSet rs = prepSt.getGeneratedKeys();
            
            if (rs.next()){
                extraAlo.setIdExtraAlojamiento(rs.getInt(1));                         
                estado = true;
            }         
            prepSt.close();                                                           
        } catch (SQLException ex) {            
            JOptionPane.showMessageDialog(null, "El Extra no se puedo guardar" + ex);
        }                    
       return estado;                                                          
    }   
        
    //-----------------------------------------------------------------------------------------------------------------------------Actualizar Extra alojamiento 
      public boolean actualizarExtra(ExtraAlojamiento extraAlo){
          
            boolean estado = false ;                                          
        
        String query = "UPDATE extraalojamiento "
                            + "SET idAlojamiento = ?, tipoDeMenu = ?, costo = ?, activo = ? "
                            + "WHERE idExtraAlojamiento = ?";
                    
        try {
            PreparedStatement prepSt = conexion.prepareStatement(query); 
            prepSt.setInt(1, extraAlo.getAlojameinto().getIdAlojamiento());  
            prepSt.setString(2, extraAlo.getTipoMenu());   
            prepSt.setFloat(3, extraAlo.getCosto());                 
            prepSt.setBoolean(4, extraAlo.isActivo()); 
            prepSt.setInt(5, extraAlo.getIdExtraAlojamiento());    
            if( prepSt.executeUpdate() > 0){                              
                estado = true;
            }         
            prepSt.close();                                               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al actualizar el extra" + ex); 
        }
        return estado;     
       
    }
     //-----------------------------------------------------------------------------------------------------------------------------DESACTIVAR EXTRAALOJAMIENTO 
     public boolean desactivarExtra(int id){
        boolean estado = false ;                                          
        
        String query = "UPDATE extraalojamiento "
                            + "SET activo = false "
                            + "WHERE idExtraAlojamiento = ?";
                                                 
                    
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query); 
                  
            prepSt.setInt(1, id ); 
                  
            if( prepSt.executeUpdate() > 0){                                
               estado = true;
            }     
            
            prepSt.close();                                                 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al borrar el extra" + ex); 
        }
       return estado;   
    }  
    //-----------------------------------------------------------------------------------------------------------------------------OBTENER POR ID
     public ExtraAlojamiento obtenerExtraXId (int id){
          ExtraAlojamiento extraObj = null;
          String query = "SELECT * "
                              + "FROM extraalojamiento "
                              + "WHERE idExtraAlojamiento = ? ";                         
            
          try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setInt(1, id);
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    extraObj = new ExtraAlojamiento();
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar al Extra Alojamiento" + ex); 
            }

        return extraObj;
    }
    //-----------------------------------------------------------------------------------------------------------------------------OBTENER POR TIPO
       public ExtraAlojamiento obtenerExtraXTipo (String tipo){
          ExtraAlojamiento extraObj = null;
          String query = "SELECT * "
                              + "FROM extraalojamiento "
                              + "WHERE tipoDeMenu = ? ";   
          
            try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setString(1, tipo);
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    extraObj = new ExtraAlojamiento();
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));
                }    
                prepSt.close();                                           
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar al Extra Alojamiento x tipo" + ex); 
            }

        return extraObj;
    }
    //-----------------------------------------------------------------------------------------------------------------------------OBTENER POR ALOJAMIENTO          
      public ExtraAlojamiento buscarExtraXAlojamiento (Alojamiento alojamiento){
          ExtraAlojamiento extraObj = null;
          String query = "SELECT * "
                       + "FROM extraalojamiento "
                       + "WHERE idAlojamiento = ? ";                         
            try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setInt(1, alojamiento.getIdAlojamiento());
                ResultSet resultSet = prepSt.executeQuery();  
                
                while(resultSet.next()){                                        
                    extraObj = new ExtraAlojamiento();
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar al Extra Alojamiento" + ex); 
            }

        return extraObj;
    }
     //-----------------------------------------------------------------------------------------------------------------------------LISTAR EXTRASALOJAMIENTO
    public ArrayList<ExtraAlojamiento> listarExtras(){
        
          ExtraAlojamiento extraObj = null;
          ArrayList<ExtraAlojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM extraalojamiento ";                                                        
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    extraObj = new ExtraAlojamiento();
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));   
                    extraLista.add(extraObj);   
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Extras" + ex); 
            }
   
        return extraLista; 
    }
    //----------------------------------------------------------------------------------------------------------------------------LISTAR EXTRASALOJAMIENTO ACTIVOS
     public ArrayList<ExtraAlojamiento> listarExtrasActivo(){
        
          ExtraAlojamiento extraObj = null;
          ArrayList<ExtraAlojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM extraalojamiento "
                              + "WHERE activo = true ";                          
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    extraObj = new ExtraAlojamiento();
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));   
                    extraLista.add(extraObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Extras" + ex); 
            }
   
        return extraLista; 
    }
    //-----------------------------------------------------------------------------------------------------------------------------LISTAR EXTRASALOJAMIENTO NO ACTIVOS 
    public ArrayList<ExtraAlojamiento> listarExtrasNoActivo(){
        
          ExtraAlojamiento extraObj = null;
          ArrayList<ExtraAlojamiento> extraLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM extraalojamiento "
                              + "WHERE activo = false";                          
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                ResultSet resultSet = prepSt.executeQuery();  
                while(resultSet.next()){                                        
                    extraObj = new ExtraAlojamiento();
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));   
                    extraLista.add(extraObj);                              
                }    
                prepSt.close();                                          
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Extras" + ex); 
            }
        return extraLista; 
    }
    //--------------------------------------------------------------------------------------------------------------------------LISTAR EXTRAS ALOJAMIENTO POR ALOJAMIENTO 
    public ArrayList<ExtraAlojamiento> listarExtrasXAlojamiento(int alojamiento){
        
          ExtraAlojamiento extraObj = null;
          ArrayList<ExtraAlojamiento> extraLista = new ArrayList<>();
         
          String query = "SELECT * "
                              + "FROM extraalojamiento "
                              + "WHERE idAlojamiento = ? ";                         
            try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setInt(1, alojamiento);
                ResultSet resultSet = prepSt.executeQuery(); 
                
                while(resultSet.next()){             
                    
                    extraObj = new ExtraAlojamiento();
                    
                    extraObj.setIdExtraAlojamiento(resultSet.getInt("idExtraAlojamiento"));
                    extraObj.setAlojameinto(alojamientoData.obtenerAlojamientoXId(resultSet.getInt("idAlojamiento")));                  
                    extraObj.setTipoMenu(resultSet.getString("tipoDeMenu"));
                    extraObj.setCosto(resultSet.getFloat("costo"));
                    extraObj.setActivo(resultSet.getBoolean("activo"));
                    
                    extraLista.add(extraObj);
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar al Extra Alojamiento" + ex); 
            }


        return extraLista; 
    }
}
