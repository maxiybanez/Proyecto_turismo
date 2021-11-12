
package control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Alojamiento;
import modelo.Cliente;
import modelo.Destino;
import modelo.ExtraAlojamiento;
import modelo.Paquete;
import modelo.Transporte;


public class PaqueteData {
    
    //---ATRIBUTO---------------------------------------------------------------
    private Connection conexion = null;
    
    private TransporteData transporteData;
    private ExtraAlojamientoData extraAlojamientoData;
    private ClienteData clienteData;

    
        //---CONSTRUCTOR--------------------------------------------------------
        public PaqueteData(ConectarBD conexionParam) {

            this.conexion = conexionParam.getConexion();
            
            this.transporteData = new TransporteData(conexionParam);
            this.extraAlojamientoData = new ExtraAlojamientoData(conexionParam);
            this.clienteData = new ClienteData(conexionParam);
            
        }
   
        
    //***************************** METODOS ************************************  
        
    //---GUARDAR PAQUETE--------------------------------------------------------
    public boolean guardarPaquete(Paquete paqueteParam){
        boolean estado = false;
        //                                      1            2                3                 4          5            6          7           8           9 
        String query = "INSERT INTO paquete(idTransporte, idExtraAlojamiento,  idCliente, fechaInicio, fechaFin, costoTotal, fechaEmision, activo)" 
                     + " VALUES (? , ? , ? , ? , ? , ? , ? , ? , ?)"; 
        
        try {
            
            PreparedStatement prepStatem = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS ); 
           
            prepStatem.setInt(1, paqueteParam.getTransporte().getIdTransporte());                
            prepStatem.setInt(2, paqueteParam.getExtraAlojamiento().getIdExtraAlojamiento());                   
     
            prepStatem.setInt(3, paqueteParam.getCliente().getIdCliente()); 
            prepStatem.setDate(4, Date.valueOf(paqueteParam.getFechaInicio()));                
            prepStatem.setDate(5, Date.valueOf(paqueteParam.getFechaFin()));                
            prepStatem.setFloat(6, paqueteParam.getCostoTotal()); 
            prepStatem.setDate(7, Date.valueOf(paqueteParam.getFechaEmision()));  
            prepStatem.setBoolean(8, paqueteParam.isActivo()); 
            
            prepStatem.executeUpdate();                                         
              ResultSet resultSet = prepStatem.getGeneratedKeys();                  
            
            if (resultSet.next()){
                paqueteParam.setIdPaquete(resultSet.getInt(1));  
                estado = true;
               
            }
            prepStatem.close();                                                 
            
          } catch (SQLException ex) {
              estado=false;
            JOptionPane.showMessageDialog(null," Error. No se pudo guardar el Paquete" + ex);
        }   
        
        return estado;
    }
       
    //---ACTUALIZAR PAQUETE-----------------------------------------------------
    public boolean actualizarPaquete(Paquete paqueteParam){
        
        boolean estado = false;
        
        String query = "UPDATE paquete "
                    + " SET (idTransporte = ? , idExtraAlojamiento = ? , idCliente = ? , fechaInicio = ? , fechaFin = ? , costoTotal = ? , fechaEmision = ? , activo = ? ) "
                    + " WHERE IdPaquete = ? ";   
        
       try {
            
            PreparedStatement prepStatem = conexion.prepareStatement(query); 
            
            prepStatem.setInt(1, paqueteParam.getTransporte().getIdTransporte());                 
            prepStatem.setInt(2, paqueteParam.getExtraAlojamiento().getIdExtraAlojamiento());                  
                 
            prepStatem.setInt(3, paqueteParam.getCliente().getIdCliente());                 

            prepStatem.setDate(4, Date.valueOf( paqueteParam.getFechaInicio()));
            prepStatem.setDate(5, Date.valueOf( paqueteParam.getFechaFin()));
            
            prepStatem.setFloat(6,paqueteParam.getCostoTotal());
            
            prepStatem.setDate(7, Date.valueOf( paqueteParam.getFechaEmision()));
                        
            prepStatem.setBoolean(8, paqueteParam.isActivo()); 
            
            prepStatem.setInt(9, paqueteParam.getIdPaquete()); 
                  
            if( prepStatem.executeUpdate() > 0){                               
                estado = true;
            }        
            
            prepStatem.close();                                                
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al actualizar paquete!" + ex); 
        }
                
        return estado;
    }    
    
    //---DESACTIVAR PAQUETE-----------------------------------------------------
    public boolean desactivarPaquete(int idPaqueteParam){
        boolean estado = false;
        
        String sentenciaSql = "UPDATE paquete "
                            + "SET activo = false "
                            + "WHERE idPaquete = ?";
                                                 
                    
        try {
            
            PreparedStatement prepStatem = conexion.prepareStatement(sentenciaSql); 
                  
            prepStatem.setInt(1, idPaqueteParam ); 
                  
            if( prepStatem.executeUpdate() > 0){                                 
               estado = true;
            }     
            
            prepStatem.close();                                                
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al desactivar el Paquete!" + ex); 
        }   
                
        return estado;
    }
        
    
    //---OBTENER - UN PAQUETE PARTICULAR-------------(Activos y No Activos)-----
    public Paquete obtenerPaqueteXId(int idPaqueteParam){
          
        Paquete paqueteObj = null;

        String query = "SELECT * "
                            + "FROM paquete "
                            + "WHERE idPaquete = ? ";

        try {

              PreparedStatement prepStatem = conexion.prepareStatement(query); 
              prepStatem.setInt(1, idPaqueteParam);

              ResultSet resultSet = prepStatem.executeQuery();  

              while(resultSet.next()){                                        

                  paqueteObj = new Paquete();

                  paqueteObj.setIdPaquete(resultSet.getInt("idPaquete"));
   
                  Transporte transporte = transporteData.obtenerTransporteXId( resultSet.getInt("idTransporte") );
                  paqueteObj.setTransporte(transporte);

                  ExtraAlojamiento extraAlojamiento = extraAlojamientoData.obtenerExtraXId(resultSet.getInt("idAlojamiento"));
                  paqueteObj.setExtraAlojamiento(extraAlojamiento);

                  Cliente cliente = clienteData.buscarClienteXId(resultSet.getInt("idClientee"));
                  paqueteObj.setCliente(cliente);

                  paqueteObj.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());

                  paqueteObj.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());

                  paqueteObj.setCostoTotal(resultSet.getFloat("costoTotal"));

                  paqueteObj.setFechaEmision(resultSet.getDate("fechaEmision").toLocalDate());

                  paqueteObj.setActivo(resultSet.getBoolean("activo"));
              }    

              prepStatem.close();                                             

          } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null,"Error al obtener paquete por Id!" + ex); 
          }

        return paqueteObj;
    }   

    
    
    //---OBTENER - TODOS LOS PAQUETES---------(Activos y NO Activos)------------
    public ArrayList<Paquete> obtenerTodosLosPaquetes(){
        ArrayList<Paquete> listarPaquetes = new ArrayList<>();
         
            String query = "SELECT * FROM paquete "; 
        
        try {
            
            PreparedStatement prepStatem = conexion.prepareStatement(query);
            
            ResultSet resultSet = prepStatem.executeQuery(); 
            
            while(resultSet.next()){    
                
                    Paquete paqueteObj = new Paquete();
                    
                    paqueteObj.setIdPaquete(resultSet.getInt("idPaquete"));
                    
                    Transporte transporte = transporteData.obtenerTransporteXId(resultSet.getInt("idTransporte"));
                    paqueteObj.setTransporte(transporte);
                    
                    ExtraAlojamiento extraAlojamiento = extraAlojamientoData.obtenerExtraXId(resultSet.getInt("idAlojamiento"));
                    paqueteObj.setExtraAlojamiento(extraAlojamiento);
                          
                  
                    Cliente cliente = clienteData.buscarClienteXId(resultSet.getInt("idClientee"));
                    paqueteObj.setCliente(cliente);
                    
                    paqueteObj.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                    
                    paqueteObj.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());
                                        
                    paqueteObj.setCostoTotal(resultSet.getFloat("costoTotal"));
                    
                    paqueteObj.setFechaEmision(resultSet.getDate("fechaEmision").toLocalDate());
                                       
                    paqueteObj.setActivo(resultSet.getBoolean("activo"));
                   
                    
                    listarPaquetes.add(paqueteObj);
             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null," Error, en obtener todos los paquetes"+ ex);
        }
       
        return listarPaquetes;
    }   
    
    //---OBTENER - SOLO PAQUETES "ACTIVOS"----------(solo Activos)--------------
    public ArrayList<Paquete> obtenerSoloPaquetesActivos(){
        ArrayList<Paquete> listarPaquetes = new ArrayList<>();
         
            String query = "SELECT * FROM paquete WHERE activo = true"; 
        
        try {
            
            PreparedStatement prepStatem = conexion.prepareStatement(query);
            
            ResultSet resultSet = prepStatem.executeQuery(); 
            
            while(resultSet.next()){    
                
                    Paquete paqueteObj = new Paquete();
                    
                    paqueteObj.setIdPaquete(resultSet.getInt("idPaquete"));
                    
                    Transporte transporte = transporteData.obtenerTransporteXId(resultSet.getInt("idTransporte"));
                    paqueteObj.setTransporte(transporte);
                    
                    ExtraAlojamiento extraAlojamiento = extraAlojamientoData.obtenerExtraXId(resultSet.getInt("idAlojamiento"));
                    paqueteObj.setExtraAlojamiento(extraAlojamiento);
                    
                    Cliente cliente = clienteData.buscarClienteXId(resultSet.getInt("idClientee"));
                    paqueteObj.setCliente(cliente);
                    
                    paqueteObj.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                    
                    paqueteObj.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());
                                        
                    paqueteObj.setCostoTotal(resultSet.getFloat("costoTotal"));
                    
                    paqueteObj.setFechaEmision(resultSet.getDate("fechaEmision").toLocalDate());
                                       
                    paqueteObj.setActivo(resultSet.getBoolean("activo"));
                   
                    
                    listarPaquetes.add(paqueteObj);
             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null," Error, en obtener solo paquetes activos "+ ex);
        }
       
        return listarPaquetes;
    }   
        
    //---OBTENER - SOLO PAQUETES "NO ACTIVOS"-------(solo No Activos)-----------
    public ArrayList<Paquete> obtenerSoloPaquetesNoActivos(){
        ArrayList<Paquete> listarPaquetes = new ArrayList<>();
         
            String query = "SELECT * FROM paquete WHERE activo = false"; 
        
        try {
            
            PreparedStatement prepStatem = conexion.prepareStatement(query);
            
            ResultSet resultSet = prepStatem.executeQuery(); 
            
            while(resultSet.next()){    
                
                    Paquete paqueteObj = new Paquete();
                    
                    paqueteObj.setIdPaquete(resultSet.getInt("idPaquete"));
                    
                    Transporte transporte = transporteData.obtenerTransporteXId(resultSet.getInt("idTransporte"));
                    paqueteObj.setTransporte(transporte);
                    
                    ExtraAlojamiento extraAlojamiento = extraAlojamientoData.obtenerExtraXId(resultSet.getInt("idAlojamiento"));
                    paqueteObj.setExtraAlojamiento(extraAlojamiento);
                  
                    Cliente cliente = clienteData.buscarClienteXId(resultSet.getInt("idClientee"));
                    paqueteObj.setCliente(cliente);
                    
                    paqueteObj.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                    
                    paqueteObj.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());
                                        
                    paqueteObj.setCostoTotal(resultSet.getFloat("costoTotal"));
                    
                    paqueteObj.setFechaEmision(resultSet.getDate("fechaEmision").toLocalDate());
                                       
                    paqueteObj.setActivo(resultSet.getBoolean("activo"));
                   
                    
                    listarPaquetes.add(paqueteObj);
             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null," Error, en obtener solo paquetes NO activos "+ ex);
        }
       
        return listarPaquetes;
    }    

}
