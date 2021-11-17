
package control;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;

public class ClienteData {
    
        
    //---ATRIBUTO---------------------------------------------------------------
    private Connection conexion = null;                                         

    //---CONSTRUCTOR------------------------------------------------------------
    public ClienteData(ConectarBD conexionParam) {
        
        this.conexion = conexionParam.getConexion();
    }
    
    //---METODO-----------------------------------------------------------------
    public boolean guardarCliente(Cliente clienteParam){
        
        boolean estado = false;
        
        String query = "INSERT INTO cliente(nombre,apellido,dni,activo)"
                     + " VALUES (?,?,?,?)";                            
        
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS ); 
                        
            prepSt.setString(1, clienteParam.getNombre());   
            prepSt.setString(2, clienteParam.getApellido());                 
            prepSt.setLong(3, clienteParam.getDni()); 
            prepSt.setBoolean(4, clienteParam.isActivo()); 
      
            prepSt.executeUpdate();                                         
            
            ResultSet resultSet = prepSt.getGeneratedKeys();                
            
            if (resultSet.next()){
                clienteParam.setIdCliente(resultSet.getInt(1));                         
                estado = true;
            }
         
            prepSt.close();                                               
            
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "El Cliente se no puedo guardar" + ex);
        }
                    
       return estado;                                                          
    }
        
        
    //---METODO-----------------------------------------------------------------
    public boolean actualizarCliente(Cliente clienteParam){
            boolean estado = false ;                                          
        
        String query = "UPDATE cliente "
                            + "SET  nombre = ?, apellido = ?, dni = ?, activo = ? "
                            + "WHERE idCliente = ?";
                    
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query); 
            
            prepSt.setString(1, clienteParam.getNombre());                   
            prepSt.setString(2, clienteParam.getApellido());                 
            prepSt.setLong(3, clienteParam.getDni()); 
            prepSt.setBoolean(4, clienteParam.isActivo()); 
            
            prepSt.setInt(5, clienteParam.getIdCliente()); 
                  
            if( prepSt.executeUpdate() > 0){                              
                estado = true;
            }        
            
            prepSt.close();                                               
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al actualizar el Cliente!" + ex); 
        }
        return estado;     
       
    }
    //---METODO-----------------------------------------------------------------
    public boolean desactivarCliente(int idParam){
        boolean estado = false ;                                          
        
        String query = "UPDATE cliente "
                            + "SET activo = false "
                            + "WHERE idCliente = ?";
                                                 
                    
        try {
            
            PreparedStatement prepSt = conexion.prepareStatement(query); 
                  
            prepSt.setInt(1, idParam ); 
                  
            if( prepSt.executeUpdate() > 0){                                
               estado = true;
            }     
            
            prepSt.close();                                                 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al borrar Cliente!" + ex); 
        }
       return estado;   
    }
    //---METODO-----------------------------------------------------------------
    public Cliente buscarClienteXId (int idParam){
          Cliente clienteObj = null;
          String query = "SELECT * "
                              + "FROM cliente "
                              + "WHERE idCliente = ? ";                         // trae todos los cliente
                                                         
          
          try {
              
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setInt(1, idParam);

                ResultSet resultSet = prepSt.executeQuery();  

                while(resultSet.next()){                                        

                    clienteObj = new Cliente();

                    clienteObj.setIdCliente(resultSet.getInt("idCliente"));
                    clienteObj.setNombre(resultSet.getString("nombre"));
                    clienteObj.setApellido(resultSet.getString("apellido"));
                    clienteObj.setDni(resultSet.getLong("dni"));
                    clienteObj.setActivo(resultSet.getBoolean("activo"));
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar Cliente X Id!" + ex); 
            }

        return clienteObj;
    }
    //---METODO-----------------------------------------------------------------
    public Cliente buscarClienteXDni( long dniParam){
          Cliente clienteObj = null;
          String query = "SELECT * "
                              + "FROM cliente "
                              + "WHERE dni = ? " ;                              //Pregunta: traemos solo los activos?
                                                       
          
          try {
              
                PreparedStatement prepSt = conexion.prepareStatement(query); 
                prepSt.setLong(1, dniParam);

                ResultSet resultSet = prepSt.executeQuery();  

                while(resultSet.next()){                                        

                    clienteObj = new Cliente();

                    clienteObj.setIdCliente(resultSet.getInt("idCliente"));
                    clienteObj.setNombre(resultSet.getString("nombre"));
                    clienteObj.setApellido(resultSet.getString("apellido"));
                    clienteObj.setDni(resultSet.getLong("dni"));
                    clienteObj.setActivo(resultSet.getBoolean("activo"));
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar Cliente X DNI!" + ex); 
            }

        return clienteObj;
        
    }
    //---METODO-----------------------------------------------------------------
    public ArrayList<Cliente> listarClientes(){
        
          Cliente clienteObj = null;
          ArrayList<Cliente> clienteLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM cliente ";                          
          
          try {
              
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
         
                ResultSet resultSet = prepSt.executeQuery();  

                while(resultSet.next()){                                        

                    clienteObj = new Cliente();

                    clienteObj.setIdCliente(resultSet.getInt("idCliente"));
                    clienteObj.setNombre(resultSet.getString("nombre"));
                    clienteObj.setApellido(resultSet.getString("apellido"));
                    clienteObj.setDni(resultSet.getLong("dni"));
                    clienteObj.setActivo(resultSet.getBoolean("activo"));
                
                    clienteLista.add(clienteObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Clientes!" + ex); 
            }
   
        return clienteLista;
    }
    //--------------------------------------------------------------------------
        //---METODO-----------------------------------------------------------------
    public ArrayList<Cliente> listarSoloClientesActivos(){
        
          Cliente clienteObj = null;
          ArrayList<Cliente> clienteLista = new ArrayList<>();
          
          String sentenciaSql = "SELECT * "
                              + "FROM cliente "
                              + "WHERE activo = true ";                          
          
          try {
              
                PreparedStatement prepSt = conexion.prepareStatement(sentenciaSql); 
         
                ResultSet resultSet = prepSt.executeQuery();  

                while(resultSet.next()){                                        

                    clienteObj = new Cliente();

                    clienteObj.setIdCliente(resultSet.getInt("idCliente"));
                    clienteObj.setNombre(resultSet.getString("nombre"));
                    clienteObj.setApellido(resultSet.getString("apellido"));
                    clienteObj.setDni(resultSet.getLong("dni"));
                    clienteObj.setActivo(resultSet.getBoolean("activo"));
                
                    clienteLista.add(clienteObj);                                
                }    
                
                prepSt.close();                                             
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al obtener lista de Clientes!" + ex); 
            }
   
        return clienteLista;
    }
    //--------------------------------------------------------------------------
    
    
    
}
