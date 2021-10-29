
package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConectarBD {
      
    private String direccion = "jdbc:mysql://localhost/sistema_de_paquete_turisticog4";
    private Connection conexion = null;                                                          
    
    //---METODO-----------------------------------------------------------------
    public Connection getConexion(){
        
        try {
        
            Class.forName("com.mysql.jdbc.Driver");                             
            
            conexion = DriverManager.getConnection(direccion + "?useLegacyDatetimeCode=false&serverTimezone=UTC"
                    + "&user=" + "root" + "&password=" + "");
            
            System.out.println("La conexion fue establecida");  
            
        } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Hubo un problema en la conexion");
        }

        return conexion; 
    }
    
    
    public Connection getConexionActiva() {                                     //<---Que nos devuleva la conexion ya abierta con la BD
        return conexion;
    }

}
