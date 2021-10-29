
package vistas;

import control.ClienteData;
import control.ConectarBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import modelo.Cliente;

public class TestMain {

    public static void main(String[] args) {

        ConectarBD conexion = new ConectarBD();
                    
        //**********************************************************************
       
        //--- BORRAMOS LA BASE DE DATOS ----------------------------------------
        
            String query;
            PreparedStatement prepStatem;

            try {

                query = "DELETE FROM cliente ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();
           
                JOptionPane.showMessageDialog(null,"La BD fue Borrada y Pre-Cargada!");
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null," Hubo un Problema al borrar las tablas de la BD!");
            }
        
        
        //--- TESTEMOS CLIENTE DATA --------------------------------------------
                
        ClienteData clienteData = new ClienteData(conexion);
        
        Cliente suarezCarlos = new Cliente("Suarez","Carlos",27894457,true); 
        Cliente luceroEstella = new Cliente("Lucero","Estella",20265147,true); 
        Cliente arceMariana = new Cliente("Arce","Mariana",33215335,true); 
        Cliente sosaPedro = new Cliente("Sosa","Pedro",38654780,true); 
    
        
        //---GUARDAR CLIENTE----------------------------------------------------
        
            clienteData.guardarCliente(luceroEstella);
            clienteData.guardarCliente(arceMariana);
            clienteData.guardarCliente(sosaPedro);
                       
        if (clienteData.guardarCliente(suarezCarlos)){
        
            JOptionPane.showMessageDialog(null," El Cliente fue guardado Satisfactorioamente!");
       
        }

        
        //--- ACTUALIZAR CLIENTE -----------------------------------------------
        
            luceroEstella = clienteData.buscarClienteXDni(luceroEstella.getDni());

            luceroEstella.setNombre("Stella");

            if (clienteData.actualizarCliente(luceroEstella)){

                JOptionPane.showMessageDialog(null," El Cliente fue actualizado Satisfactorioamente!");

            }
            
        //---DESACTIVAR CLIENTE -------------------------------------------------
               
            if ( clienteData.desactivarCliente(suarezCarlos.getIdCliente())){

                JOptionPane.showMessageDialog(null,"El Cliente fue desactivado Satisfactorioamente!");

            }        
                
        //---BUSCAR CLIENTE X ID------------------------------------------------
        
            Cliente cliente1 = clienteData.buscarClienteXId(arceMariana.getIdCliente());        
        
            System.out.println("\nBuscar Cliente x Id " + cliente1.getIdCliente() + " : "                              
                                                    + cliente1.getNombre() + " "            
                                                    + cliente1.getApellido()); 
                                 
                    
         //---BUSCAR CLIENTE X DNI----------------------------------------------
        
            Cliente cliente2 = clienteData.buscarClienteXDni(sosaPedro.getDni());        
        
            System.out.println("\nBuscar Cliente x dni " + cliente2.getDni()+ " : "                              
                                                    + cliente2.getNombre() + " "            
                                                    + cliente2.getApellido()); 

        //---LISTAR CLIENTES----------------------------------------------------
       
            System.out.println("\nListar Clientes: " + clienteData.listarClientes());

    }
    
}
