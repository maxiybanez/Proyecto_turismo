
package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Destino;

import modelo.Transporte;

public class TransporteData {

   
    private DestinoData destinoData; 

    private Connection conexion = null;

    public TransporteData(ConectarBD conexionParam) {

        this.conexion = conexionParam.getConexion();
        this.destinoData = new DestinoData(conexionParam);
    }

    public boolean guardarTransporte(Transporte transporteParam) {

        boolean estado = false;

        String query = "INSERT INTO transporte(idDestino, tipoDeTransporte, costo, activo)"
                    + " VALUES (?,?,?,?)"; 

        try {

            PreparedStatement prepDes = conexion.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);

            prepDes.setInt(1, transporteParam.getDestino().getIdDestino()); 
            prepDes.setString(2, transporteParam.getTipoDeTransporte());
            prepDes.setFloat(3, transporteParam.getCosto());
            prepDes.setBoolean(4, transporteParam.isActivo());

            prepDes.executeUpdate();


            ResultSet rs = prepDes.getGeneratedKeys();
            
            if (rs.next()){
                transporteParam.setIdTransporte(rs.getInt(1));                         
                estado = true;
            }         
            prepDes.close();                                                           
        } catch (SQLException ex) {            
            JOptionPane.showMessageDialog(null, "Erro al guardar trasporte" + ex);
        }    

        return estado;
    }

    public boolean actualizarTransporte(Transporte transporteParam) {
        boolean estado = false ;                                          
        
        String query = "UPDATE transporte "
                     + "SET   idDestino = ?, tipoDeTransporte = ?, costo = ?, activo = ? "
                     + "WHERE idTransporte = ? ";
                           
        try {
            PreparedStatement prepSt = conexion.prepareStatement(query); 
            
            prepSt.setInt(1, transporteParam.getDestino().getIdDestino());  
            prepSt.setString(2, transporteParam.getTipoDeTransporte());   
            prepSt.setFloat(3, transporteParam.getCosto());                 
            prepSt.setBoolean(4, transporteParam.isActivo()); 
            prepSt.setInt(5, transporteParam.getIdTransporte()); 
            
            if( prepSt.executeUpdate() > 0){                              
                estado = true;
            }         
            prepSt.close();                                               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al actualizar transporte " + ex); 
        }
        return estado;    

    }

    public boolean desactivarTransporte(int idTransporteParam) {
        boolean estado = false ;                                          
        
        String query = "UPDATE transporte "
                            + "SET activo = false "
                            + "WHERE idTransporte = ?";
                                                 
                    
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query); 
                  
            prepSt.setInt(1, idTransporteParam ); 
                  
            if( prepSt.executeUpdate() > 0){                                
               estado = true;
            }     
            
            prepSt.close();                                                 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al desactivar transporte" + ex); 
        }
       return estado;   
    }
    
    
    
    
    public Transporte obtenerTransporteXId(int idTransporteParam) {
          Transporte transporteObj = null;
          String query = "SELECT * "
                       + "FROM transporte "
                       + "WHERE idTransporte = ? ";                         
            
          try {
              
                PreparedStatement prepSt = conexion.prepareStatement(query);
                
                prepSt.setInt(1, idTransporteParam);
                
                ResultSet resultSet = prepSt.executeQuery();  
                
                while(resultSet.next()){                                        
                    transporteObj = new Transporte();
                    
                    transporteObj.setIdTransporte(resultSet.getInt("idTransporte"));
                    
                    Destino destino = destinoData.buscarDestinoXid(resultSet.getInt("idDestino"));
                    
                    transporteObj.setDestino(destino);
       
                    transporteObj.setTipoDeTransporte(resultSet.getString("tipoDeTransporte"));
                    transporteObj.setCosto(resultSet.getFloat("costo"));
                    transporteObj.setActivo(resultSet.getBoolean("activo"));
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener transporte x id" + ex); 
            }

        return transporteObj;

    }
    
   

    public ArrayList<Transporte> obtenerTodosLosTransportes() {
          
          Transporte transporteObj = null;
          ArrayList<Transporte> transporteArray = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM transporte ";                                                        
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                
                ResultSet resultSet = prepSt.executeQuery();  
                
                while(resultSet.next()){                                        
                    transporteObj = new Transporte();
                    
                    transporteObj.setIdTransporte(resultSet.getInt("idTransporte"));
                    transporteObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    transporteObj.setTipoDeTransporte(resultSet.getString("tipoDeTransporte"));
                    transporteObj.setCosto(resultSet.getFloat("costo"));
                    transporteObj.setActivo(resultSet.getBoolean("activo")); 
                    
                    transporteArray .add(transporteObj);   
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener todos los transportes" + ex); 
            }
   
        return transporteArray; 

    }

    public ArrayList<Transporte> obtenerTodosLosTransportesActivos() {
          Transporte transporteObj = null;
          ArrayList<Transporte> transporteArray = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM transporte "
                              + "WHERE activo = true ";                                                        
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                
                ResultSet resultSet = prepSt.executeQuery();  
                
                while(resultSet.next()){                                        
                    transporteObj = new Transporte();
                    
                    transporteObj.setIdTransporte(resultSet.getInt("idTransporte"));
                    transporteObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    transporteObj.setTipoDeTransporte(resultSet.getString("tipoDeTransporte"));
                    transporteObj.setCosto(resultSet.getFloat("costo"));
                    transporteObj.setActivo(resultSet.getBoolean("activo")); 
                    
                    transporteArray .add(transporteObj);   
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener todos los transportes Activos" + ex); 
            }
   
        return transporteArray; 

    }

    public ArrayList<Transporte> obtenerTodosLosTransportesNoActivos() {
          Transporte transporteObj = null;
          ArrayList<Transporte> transporteArray = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM transporte "
                              + "WHERE activo = false ";                                                        
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
                
                ResultSet resultSet = prepSt.executeQuery();  
                
                while(resultSet.next()){                                        
                    transporteObj = new Transporte();
                    
                    transporteObj.setIdTransporte(resultSet.getInt("idTransporte"));
                    transporteObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    transporteObj.setTipoDeTransporte(resultSet.getString("tipoDeTransporte"));
                    transporteObj.setCosto(resultSet.getFloat("costo"));
                    transporteObj.setActivo(resultSet.getBoolean("activo")); 
                    
                    transporteArray .add(transporteObj);   
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener todos los transportes No Activos" + ex); 
            }
   
        return transporteArray; 

    }

    public ArrayList<Transporte> obtenerTodosLosTransportesXDestino(int idDestinoParam) {
 
          Transporte transporteObj = null;
          ArrayList<Transporte> transporteArray = new ArrayList<>();
          
          String query = "SELECT * "
                       + "FROM transporte "
                       + "WHERE idDestino = ? ";                                                        
          
          try {
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setInt(1, idDestinoParam);
                ResultSet resultSet = prepSt.executeQuery(); 
                
                while(resultSet.next()){   
                    
                    transporteObj = new Transporte();
                    
                    transporteObj.setIdTransporte(resultSet.getInt("idTransporte"));
                    transporteObj.setDestino(destinoData.buscarDestinoXid(resultSet.getInt("idDestino")));
                    transporteObj.setTipoDeTransporte(resultSet.getString("tipoDeTransporte"));
                    transporteObj.setCosto(resultSet.getFloat("costo"));
                    transporteObj.setActivo(resultSet.getBoolean("activo")); 
                    
                    transporteArray .add(transporteObj);   
                }    
                prepSt.close();                                             
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener todos los transportes x destino" + ex); 
            }
   
        return transporteArray; 
        

    }
    
    
    
    
    
}
