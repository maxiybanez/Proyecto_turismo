
package vistas;

import control.AlojamientoData;
import control.ClienteData;
import control.ConectarBD;
import control.DestinoData;
import control.ExtraAlojamientoData;
import control.PaqueteData;
import control.TransporteData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import modelo.Alojamiento;
import modelo.Cliente;
import modelo.Destino;
import modelo.ExtraAlojamiento;
import modelo.Transporte;
import modelo.Paquete;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class TestMain {

    public static void main(String[] args) {

        ConectarBD conexion = new ConectarBD();
        
        
                    
//******************************************************************************
       
        //--- BORRAMOS LA BASE DE DATOS ----------------------------------------
        
            String query;
            PreparedStatement prepStatem;

            try {

                query = "DELETE FROM cliente ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();

                query = "DELETE FROM extraalojamiento ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();   
                
                query = "DELETE FROM transporte ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();   
                                                
                query = "DELETE FROM alojamiento ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();   
                                                
                query = "DELETE FROM destino ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();              
                                                                
                query = "DELETE FROM paquete ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();              
                                
                
                JOptionPane.showMessageDialog(null,"La BD fue Borrada y Pre-Cargada!");
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null," Hubo un Problema al borrar las tablas de la BD!");
            }
        
//*********************************CLIENTE DATA*********************************
        
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
        
            System.out.println("\nBuscar Cliente x Id "
                                + cliente1.getIdCliente() + " : "                              
                                + cliente1.getNombre() + " "            
                                + cliente1.getApellido()); 
                                 
                    
         //---BUSCAR CLIENTE X DNI----------------------------------------------
        
            Cliente cliente2 = clienteData.buscarClienteXDni(sosaPedro.getDni());        
        
            System.out.println("\nBuscar Cliente x dni " 
                                + cliente2.getDni()+ " : "                              
                                + cliente2.getNombre() + " "            
                                + cliente2.getApellido()); 

        //---LISTAR CLIENTES----------------------------------------------------
       
            System.out.println("\nListar Clientes: " + clienteData.listarClientes());

            
            
//*******************************DESTINO DATA***********************************
        
        //--- TESTEMOS DESTINO DATA --------------------------------------------
        
        DestinoData destinoData = new DestinoData(conexion);
        
        
        Destino marDelPlata = new Destino("Mar del Plata","Argentina",true); 
        Destino cordoba = new Destino("Cordoba","Argentina",true); 
        Destino mendoza = new Destino("Mendoza","Argentina",true); 
        Destino santiagoDeChile = new Destino("Santiago de Chile","Chile",true); 
        Destino rioDeJaneiro = new Destino("Rio De Janeiro","Brazil",true); 

        //---GUARDAR DESTINO----------------------------------------------------    
                
        destinoData.guardarDestino(marDelPlata);
        destinoData.guardarDestino(cordoba);
        destinoData.guardarDestino(mendoza);
        destinoData.guardarDestino(santiagoDeChile);
        
        if (destinoData.guardarDestino(rioDeJaneiro)){
        
            JOptionPane.showMessageDialog(null," Los destinos fueron guardado Satisfactorioamente!");
        }
                     
        //---ACTUALIZAR DESTINO-------------------------------------------------    
        
            Destino destinoObj_1 = destinoData.buscarDestinoXid(rioDeJaneiro.getIdDestino());

            destinoObj_1.setNombre("Rio de Janeeeiiro");     //--- cambio la de de mayuscula a minuscula y el Janeiro

            if (destinoData.actualizarDestino(destinoObj_1)){

                JOptionPane.showMessageDialog(null," El destino fue actualizado Satisfactorioamente!");
            }
        
        //---DESACTIVAR DESTINO ------------------------------------------------  
        
            if ( destinoData.desactivarDestinoXId(mendoza.getIdDestino())){

                JOptionPane.showMessageDialog(null,"El destino fue desactivado Satisfactorioamente!");
            }

            
        //---BUSCAR DESTINO X ID------------------------------------------------   

            Destino destinoObj2 = destinoData.buscarDestinoXid(cordoba.getIdDestino());         
        
            System.out.println("\nBuscar Destino x ID: " + destinoObj2.getIdDestino()+ ": "                              
                                + destinoObj2.getNombre() + " " 
                                + destinoObj2.getPais()); 
 
            
        //---BUSCAR DESTINO X NOMBRE--------------------------------------------        
            
            Destino destinoObj5 = destinoData.buscarDestinoXNombre(santiagoDeChile.getNombre());         
        
            System.out.println("\nBuscar Destino x nombre: " + destinoObj5.getIdDestino()+ ": "                              
                                + destinoObj5.getNombre() + " " 
                                + destinoObj5.getPais());
        
        //---OBTENER - TODOS LOS DESTINOS--------------------------------------- 
        
            System.out.println("\nOBTENER - TODOS LOS DESTINOS:  " + destinoData.obtenerTodosLosDestinos());
        
        
        //---OBTENER - SOLO DESTINOS ACTIVADOS---------------------------------- 
        
        
            System.out.println("\nOBTENER - SOLO DESTINOS ACTIVADOS:  " + destinoData.obtenerTodosLosDestinosActivos());
        
        
        //---OBTENER - SOLO LOS DESTINOS DESACTIVADOS---------------------------   
        

            System.out.println("\nOBTENER - SOLO DESTINOS DESACTIVADOS:  " + destinoData.obtenerTodosLosDestinosNoActivos());       
      
        
    //*******************************TRANSPORTE DATA*******************************
        
        //--- TESTEMOS TRANSPORTE DATA -----------------------------------------
        
        TransporteData transporteData = new TransporteData(conexion);
        
        // Transportes a Mar del Plata
        Transporte avionMDP = new Transporte(marDelPlata,"Avion MDP",7000,true); 
        Transporte colectivoMDP = new Transporte(marDelPlata,"Colectivo MDP",3500,true); 
        Transporte autoPropioMDP = new Transporte(marDelPlata,"Auto Propio MDP",0,true); 
 
                
        // Transportes a Cordoba
        Transporte avionCBA = new Transporte(cordoba,"Avion CBA",7000,true); 
        Transporte colectivoCBA = new Transporte(cordoba,"Colectivo CBA",3500,true); 
        Transporte autoPropioCBA = new Transporte(cordoba,"Auto Popio CBA",0,true); 
 
                
        // Transportes a Mendoza
        Transporte avionMZA = new Transporte(mendoza,"Avion MZA",7000,true); 
        Transporte colectivoMZA = new Transporte(mendoza,"Colectivo MZA",3500,true); 
        Transporte autoPopioMZA = new Transporte(mendoza,"Auto propio MZA",0,true); 

        
        // Transportes a Santiago de Chile
        Transporte avionSTCH = new Transporte(santiagoDeChile,"Avion STCH",7000,true); 
        Transporte colectivoSTCH = new Transporte(santiagoDeChile,"Colectivo STCH",3500,true); 
        Transporte autoPopioSTCH = new Transporte(santiagoDeChile,"Auto propio STCH",0,true); 
    
        
        // Transportes a Rio de Janeiro
        Transporte avionRJ = new Transporte(rioDeJaneiro,"Avion RJ",7000,true); 
        Transporte colectivoRJ = new Transporte(rioDeJaneiro,"Colectivo RJ",3500,true); 
        Transporte autoPopioRJ = new Transporte(rioDeJaneiro,"Auto propio RJ",0,true); 


        //---GUARDAR TRANSPORTE----------------------------------------------------    
        
        // Transportes a Mar del Plata
        transporteData.guardarTransporte(avionMDP);
        transporteData.guardarTransporte(colectivoMDP);
        transporteData.guardarTransporte(autoPropioMDP);

        
        // Transportes a Cordoba
        transporteData.guardarTransporte(avionCBA);
        transporteData.guardarTransporte(colectivoCBA);
        transporteData.guardarTransporte(autoPropioCBA);

        
        // Transportes a Mendoza
        transporteData.guardarTransporte(avionMZA);
        transporteData.guardarTransporte(colectivoMZA);
        transporteData.guardarTransporte(autoPopioMZA);
        
        // Transportes a Santiago de Chile
        transporteData.guardarTransporte(avionSTCH);
        transporteData.guardarTransporte(colectivoSTCH);
        transporteData.guardarTransporte(autoPopioSTCH);
        
        // Transportes a Rio de Janeiro
        transporteData.guardarTransporte(avionRJ);
        transporteData.guardarTransporte(colectivoRJ);
        transporteData.guardarTransporte(autoPopioRJ);
        
        
        if (transporteData.guardarTransporte(autoPopioRJ)){
        
            JOptionPane.showMessageDialog(null," Los transportes fueron guardados Satisfactorioamente!");
        }   
       
        
        //---ACTUALIZAR TRANSPORTE----------------------------------------------------     
       
            Transporte transporteObj_1 = transporteData.obtenerTransporteXId(colectivoMZA.getIdTransporte());

            transporteObj_1.setTipoDeTransporte("Colectiiiivo MZA");     //--- agrego iii a Colectivo

            if (transporteData.actualizarTransporte(transporteObj_1)){

                JOptionPane.showMessageDialog(null," El transporte fue actualizado Satisfactorioamente!");
            }
        
        
        //---DESACTIVAR TRANSPORTE----------------------------------------------------     
        
            if (transporteData.desactivarTransporte(autoPopioSTCH.getIdTransporte())){

                JOptionPane.showMessageDialog(null,"El transporte fue desactivado Satisfactorioamente!");
            }
        

        //---OBTENER - TRANSPORTE X ID----------------------------------------------------     
            
            Transporte trasTransporteObj_5 = transporteData.obtenerTransporteXId(colectivoMDP.getIdTransporte());        
        
            System.out.println("\nOBTENER - TRANSPORTE X ID-: "
                                + trasTransporteObj_5.getDestino().getIdDestino() + " "
                                + trasTransporteObj_5.getIdTransporte()+ ": "                              
                                + trasTransporteObj_5.getTipoDeTransporte()+ " " 
                                + trasTransporteObj_5.getCosto());  
       
            
        //---OBTENER - TODOS LOS TRANSPORTES----------------------------------------------------     
        
            System.out.println("\nOBTENER - TODOS LOS TRANSPORTES:  " + transporteData.obtenerTodosLosTransportes());
                
        //---OBTENER - SOLO TRANSPORTES ACTIVOS----------------------------------------------------     
        
            System.out.println("\nOBTENER - SOLO TRANSPORTES ACTIVOS:  " + transporteData.obtenerTodosLosTransportesActivos());
        
        //---OBTENER - SOLO TRANSPORTES NO ACTIVOS-----------------------------------------------------     
                
            System.out.println("\nOBTENER - SOLO TRANSPORTES NO ACTIVOS:  " + transporteData.obtenerTodosLosTransportesNoActivos());
        

        //---OBTENER - TODOS LOS TRANSPORTES X DESTINO----------------------------------------------------     
        
            System.out.println("\nOBTENER - TODOS LOS TRANSPORTES X DESTINO:  " + transporteData.obtenerTodosLosTransportesXDestino(marDelPlata.getIdDestino()));
        
        
   
        
        
        
        
//*****************************ALOJAMIENTO DATA***************************************
    
        //--- TESTEMOS ALOJAMIENTO DATA --------------------------------------------
                
        AlojamientoData alojamientoData = new AlojamientoData(conexion);
        
        // ---Mar del Plata---    
        Alojamiento hotel_MDP_MDP = new Alojamiento(marDelPlata,"Hotel", 5970, "Mar del Plata (MDP)",true); 
        Alojamiento hotel_CG_MDP = new Alojamiento(marDelPlata,"Hotel", 7000, "Costa Galana (MDP)",true); 
        Alojamiento dpto_1_MDP = new Alojamiento(marDelPlata,"Departamento", 3550, "1 - uno (MDP)",true); 
        Alojamiento dpto_2_MDP = new Alojamiento(marDelPlata,"Departamento", 3970, "2 - dos (MDP)",true); 
     
        
        // ---Santiago de Chile---    
        Alojamiento hotel_SDCH_CH = new Alojamiento(santiagoDeChile,"Hotel", 7720, "Santiago de Chile (CH)",true); 
        Alojamiento hotel_CCH_CH = new Alojamiento(santiagoDeChile,"Hotel", 8500, "Costa Chile (CH)",true); 
        Alojamiento dpto_1_CH = new Alojamiento(santiagoDeChile,"Departamento", 3330, "1 - uno (CH)",true); 
        Alojamiento dpto_2_CH = new Alojamiento(santiagoDeChile,"Departamento", 3520, "2 - dos (CH)",true);        
        
        
        
        //---GUARDAR ALOJAMIENTO----------------------------------------------------    
        
        //-- Alojamientos en Mar del Plata
        alojamientoData.guardarAlojamiento(hotel_MDP_MDP);
        alojamientoData.guardarAlojamiento(hotel_CG_MDP);
        alojamientoData.guardarAlojamiento(dpto_1_MDP);
        alojamientoData.guardarAlojamiento(dpto_2_MDP);
        
        //-- Alojamientos en Chile
        alojamientoData.guardarAlojamiento(hotel_SDCH_CH);
        alojamientoData.guardarAlojamiento(hotel_CCH_CH);
        alojamientoData.guardarAlojamiento(dpto_1_CH);
            
        
        if (alojamientoData.guardarAlojamiento(dpto_2_CH)){
        
            JOptionPane.showMessageDialog(null," Los Alojamientos fueron guardados Satisfactorioamente!");
        }
                     
        
        //---ACTUALIZAR ALOJAMIENTO-------------------------------------------------    
        
            Alojamiento alojamientoObj_1 = alojamientoData.obtenerAlojamientoXId(hotel_CCH_CH.getIdAlojamiento());

            alojamientoObj_1.setNombre("Cosssta Chile");     //--- agrego ss a Costa

            if (alojamientoData.actualizarAlojamiento(alojamientoObj_1)){

                JOptionPane.showMessageDialog(null," El alojameinto fue actualizado Satisfactorioamente!");
            }
        
        //---DESACTIVAR ALOJAMIENTO ------------------------------------------------  
        
            if ( alojamientoData.desactivarAlojamiento(dpto_2_CH.getIdAlojamiento())){

                JOptionPane.showMessageDialog(null,"El alojamiento fue desactivado Satisfactorioamente!");
            }
        
        //---OBTENER - ALOJAMEINTOS X ID-------------------------------------------    
        
            Alojamiento alojamientoObj_2 = alojamientoData.obtenerAlojamientoXId(hotel_CG_MDP.getIdAlojamiento());         
        
            System.out.println("\nOBTENER - ALOJAMEINTOS X ID: "
                                + alojamientoObj_2.getIdAlojamiento()+ ": "                              
                                + alojamientoObj_2.getTipoAlojamiento() + " " 
                                + alojamientoObj_2.getNombre());  
        
        
        
        //---OBTENER - ALOJAMEINTOS X TIPO------------------------------------------------   


            Alojamiento alojamientoObj_3 = alojamientoData.obtenerAlojamientoXTipo(dpto_1_MDP.getTipoAlojamiento());         
        
            System.out.println("\nOBTENER - ALOJAMIENTOS X TIPO: "
                                + alojamientoObj_3.getIdAlojamiento()+ ": "                              
                                + alojamientoObj_3.getTipoAlojamiento() + " " 
                                + alojamientoObj_3.getNombre());  
       
            
        //---OBTENER - TODOS LOS ALOJAMEINTOS--------------------------------------- 
        
            System.out.println("\nOBTENER - TODOS LOS ALOJAMEINTOS:  " + alojamientoData.obtenerTodosLosAlojamiento());
        
        
        //---OBTENER - SOLO ALOJAMEINTOS ACTIVADOS---------------------------------- 
        
        
            System.out.println("\nOBTENER - SOLO ALOJAMEINTOS ACTIVADOS:  " + alojamientoData.obtenerTodosLosAlojamientoActivos());
        
        
        //---OBTENER - SOLO LOS ALOJAMEINTOS DESACTIVADOS---------------------------   
        

            System.out.println("\nOBTENER - SOLO ALOJAMEINTOS DESACTIVADOS:  " + alojamientoData.obtenerTodosLosAlojamientoNoActivos());       
                  
            
        //---OBTENER - TODOS LOS ALOJAMEINTOS X DESTINO--------------------------- 
        
            System.out.println("\nOBTENER - TODOS LOS ALOJAMEINTOS X DESTINO:  " + alojamientoData.obtenerTodosLosAlojamientoXDestino(marDelPlata.getIdDestino()));
        
            
        

//***************************EXTRAALOJAMEINTO DATA**********************************
    
        //--- TESTEMOS EXTRAALOJAMEINTO DATA --------------------------------------------
                
        ExtraAlojamientoData extraAlojamientoData = new ExtraAlojamientoData(conexion);
        
        // ---Mar Del Plata---
        
        // ExtraAlojamientos Hotel Mar Del Plata - Mar del Plata
        ExtraAlojamiento pensionCompleta_HT_MDP_MDP = new ExtraAlojamiento(hotel_MDP_MDP,"Pension Completa HT MDP (MPD)",1750,true); 
        ExtraAlojamiento mediaPension_HT_MDP = new ExtraAlojamiento(hotel_MDP_MDP,"Media Pension HT MDP (MDP)",1050,true); 
        ExtraAlojamiento sinPension_HT_MDP = new ExtraAlojamiento(hotel_MDP_MDP,"Sin Pension HT MDP (MDP)",0,true); 

        // ExtraAlojamientos Hotel Costa Galana - Mar del Plata
        ExtraAlojamiento pensionCompleta_HT_CG_MDP = new ExtraAlojamiento(hotel_CG_MDP,"Pension Completa HT CG (MDP)",2350,true); 
        ExtraAlojamiento mediaPension_HT_CG_MDP = new ExtraAlojamiento(hotel_CG_MDP,"Media Pension HT CG (MDP)",1350,true); 
        ExtraAlojamiento sinPension_HT_CG_MDP = new ExtraAlojamiento(hotel_CG_MDP,"Sin Pension HT CG (MDP)",0,true);         
        
        
        // ExtraAlojamientos Depto 1 - Mar del Plata
        ExtraAlojamiento sinPension_DPTO_1_MDP = new ExtraAlojamiento(dpto_1_MDP,"Sin Pension DPTO 1 (MDP)",0,true); 
      
        // ExtraAlojamientos Depto 2 - Mar del Plata
        ExtraAlojamiento mediaPension_DPTO_2_MDP = new ExtraAlojamiento(dpto_2_MDP,"Media Pension DPTO 2 (MDP)",500,true);
        ExtraAlojamiento sinPension_DPTO_2_MDP = new ExtraAlojamiento(dpto_2_MDP,"Sin Pension DPTO 2 (MDP)",0,true); 
      
        
                        //------------------------------------------------------------------
                        
                        
        // ---Santiago de Chile---
        
        // ExtraAlojamientos Hotel Santiago de Chile - Chile
        ExtraAlojamiento pensionCompleta_HT_SDCH_CH = new ExtraAlojamiento(hotel_SDCH_CH,"Pension Completa HT SDCH (CH)",1750,true); 
        ExtraAlojamiento mediaPension_HT_SDCH_CH = new ExtraAlojamiento(hotel_SDCH_CH,"Media Pension HT SDCH (CH)",1050,true); 
        ExtraAlojamiento sinPension_HT_SDCH_CH = new ExtraAlojamiento(hotel_SDCH_CH,"Sin Pension HT SDCH (CH)",0,true); 

        // ExtraAlojamientos Hotel Costa Chile - Chile
        ExtraAlojamiento pensionCompleta_HT_CCH_CH = new ExtraAlojamiento(hotel_CCH_CH,"Pension Completa HT CCH (CH)",2350,true); 
        ExtraAlojamiento mediaPension_HT_CCH_CH = new ExtraAlojamiento(hotel_CCH_CH,"Media Pension HT CCH (CH)",1350,true); 
        ExtraAlojamiento sinPension_HT_CCH_CH = new ExtraAlojamiento(hotel_CCH_CH,"Sin Pension HT CCH (CH)",0,true);         
        
        
        // ExtraAlojamientos Depto 1 - Chile
        ExtraAlojamiento sinPension_DPTO_1_CH = new ExtraAlojamiento(dpto_1_CH,"Sin Pension DPTO 1 (CH)",0,true); 
      
        // ExtraAlojamientos Depto 2 - Chile
        ExtraAlojamiento mediaPension_DPTO_2_CH = new ExtraAlojamiento(dpto_2_CH,"Media Pension DPTO 2 (CH)",500,true);
        ExtraAlojamiento sinPension_DPTO_2_CH = new ExtraAlojamiento(dpto_2_CH,"Sin Pension DPTO 2 (CH)",0,true);         
        
        
         //---GUARDAR EXTRA-ALOJAMIENTO----------------------------------------------------    
         
         
         // ---Mar Del Plata---
         
         extraAlojamientoData.guardarExtraAlo(pensionCompleta_HT_MDP_MDP);
         extraAlojamientoData.guardarExtraAlo(mediaPension_HT_MDP);
         extraAlojamientoData.guardarExtraAlo(sinPension_HT_MDP);
            
         extraAlojamientoData.guardarExtraAlo(pensionCompleta_HT_CG_MDP);
         extraAlojamientoData.guardarExtraAlo(mediaPension_HT_CG_MDP);
         extraAlojamientoData.guardarExtraAlo(sinPension_HT_CG_MDP);
   
         extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_1_MDP);
         
         extraAlojamientoData.guardarExtraAlo(mediaPension_DPTO_2_MDP);
         extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_2_MDP);

         
         // ---Santiago de Chile---
         
         extraAlojamientoData.guardarExtraAlo(pensionCompleta_HT_SDCH_CH);
         extraAlojamientoData.guardarExtraAlo(mediaPension_HT_SDCH_CH);
         extraAlojamientoData.guardarExtraAlo(sinPension_HT_SDCH_CH);
         
         extraAlojamientoData.guardarExtraAlo(pensionCompleta_HT_CCH_CH );
         extraAlojamientoData.guardarExtraAlo(mediaPension_HT_CCH_CH);
         extraAlojamientoData.guardarExtraAlo(sinPension_HT_CCH_CH);
                
         extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_1_CH);
                  
         extraAlojamientoData.guardarExtraAlo(mediaPension_DPTO_2_CH );
         extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_2_CH);
                     
        
        if (extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_2_CH)){
        
            JOptionPane.showMessageDialog(null," Los Extra-Alojamientos fueron guardados Satisfactorioamente!");
        }
                            
        //---ACTUALIZAR EXTRA-ALOJAMIENTO----------------------------------------------------     
        
            ExtraAlojamiento extraAlojamientoObj_1 = extraAlojamientoData.obtenerExtraXId(mediaPension_HT_SDCH_CH.getIdExtraAlojamiento());

            extraAlojamientoObj_1.setTipoMenu("Meeedia Pension HT SDCH (CH)");     //--- agrego eee a Media

            if (extraAlojamientoData.actualizarExtra(extraAlojamientoObj_1)){

                JOptionPane.showMessageDialog(null," El Extra-Alojameinto fue actualizado Satisfactorioamente!");
            }
        
        
        //---DESACTIVAR EXTRA-ALOJAMIENTO----------------------------------------------------     
        
            if ( extraAlojamientoData.desactivarExtra(sinPension_HT_CG_MDP.getIdExtraAlojamiento())){

                JOptionPane.showMessageDialog(null,"El Extra-Alojamiento fue desactivado Satisfactorioamente!");
            }
        
        //---OBTENER - EXTRA-ALOJAMIENTO X ID----------------------------------------------------     
            
            ExtraAlojamiento extraAlojamientoObj_2 = extraAlojamientoData.obtenerExtraXId(pensionCompleta_HT_CCH_CH.getIdExtraAlojamiento());        
        
            System.out.println("\nOBTENER - EXTRA-ALOJAMIENTO X ID: "
                                + extraAlojamientoObj_2.getIdExtraAlojamiento()+ ": "                              
                                + extraAlojamientoObj_2.getTipoMenu() + " " 
                                + extraAlojamientoObj_2.getCosto());  
        
        //---OBTENER - EXTRA-ALOJAMIENTO X TIPO----------------------------------------------------     

            ExtraAlojamiento extraAlojamientoObj_5 = extraAlojamientoData.obtenerExtraXTipo(mediaPension_HT_CCH_CH.getTipoMenu());         
        
            System.out.println("\nOBTENER - EXTRA-ALOJAMIENTOS X TIPO: "
                                + extraAlojamientoObj_5.getIdExtraAlojamiento() + ": "                              
                                + extraAlojamientoObj_5.getTipoMenu() + " " 
                                + extraAlojamientoObj_5.getCosto());    
                    
        //---BUSCAR - EXTRA-ALOJAMIENTO X ALOJAMIENTO----------------------------------------------------     
            ExtraAlojamiento extraAlojamientoObj_3 = extraAlojamientoData.buscarExtraXAlojamiento(hotel_SDCH_CH);        
                
            System.out.println("\nBUSCAR - EXTRA-ALOJAMIENTO X ALOJAMIENTO: "
                                + extraAlojamientoObj_3.getIdExtraAlojamiento()+ ": "                              
                                + extraAlojamientoObj_3.getTipoMenu() + " " 
                                + extraAlojamientoObj_3.getCosto());  
    
            
        //---LISTAR - TODOS LOS EXTRA-ALOJAMIENTO----------------------------------------------------     
        
            System.out.println("\nLISTAR - TODOS LOS EXTRA-ALOJAMIENTO:  " + extraAlojamientoData.listarExtras());
                
        //---LISTAR - SOLO EXTRA-ALOJAMIENTO ACTIVOS----------------------------------------------------     
        
            System.out.println("\nLISTAR - TODOS LOS EXTRA-ALOJAMIENTO ACTIVOS:  " + extraAlojamientoData.listarExtrasActivo());
        
        //---LISTAR - SOLO EXTRA-ALOJAMIENTO NO ACTIVOS-----------------------------------------------------     
                
            System.out.println("\nLISTAR - TODOS LOS EXTRA-ALOJAMIENTO NO ACTIVOS:  " + extraAlojamientoData.listarExtrasNoActivo());
        
        //---LISTAR - EXTRA-ALOJAMIENTO X ALOJAMIENTO----------------------------------------------------     
        
            System.out.println("\nLISTAR - EXTRA-ALOJAMIENTO X ALOJAMIENTO:  " + extraAlojamientoData.listarExtrasXAlojamiento( hotel_MDP_MDP.getIdAlojamiento())  );
        
        

//*******************************PAQUETE DATA*****************************************
    
        //--- TESTEMOS CLIENTE DATA --------------------------------------------


        PaqueteData paqueteData = new PaqueteData(conexion);
        
        Paquete paq_SuarezCarlos_HT_MDP_MPD_Avion = new Paquete(avionMDP, pensionCompleta_HT_MDP_MDP , suarezCarlos, LocalDate.of(2020,03,01), LocalDate.of(2020,11,01), LocalDate.of(2021,12,07), 35700, true); 
        Paquete paq_SuarezCarlos_DPTO_1_MPD_Colectivo = new Paquete(colectivoMDP, sinPension_DPTO_1_MDP, suarezCarlos, LocalDate.of(2021,03,01), LocalDate.of(2021,10,01), LocalDate.of(2021,12,07), 23500, true); 
        
        Paquete paq_ArceMariana_HT_CCH_CH_Avion = new Paquete(avionMDP, mediaPension_HT_CCH_CH, arceMariana, LocalDate.of(2022,02,20), LocalDate.of(2022,02,28), LocalDate.of(2021,10,19), 40900, true); 
 
            
        JOptionPane.showMessageDialog(null,"LOS PAQUETES SE GUARDARON SATISFACTORIAMENTE");
        
    //************************************************************************************

    }

        


}
