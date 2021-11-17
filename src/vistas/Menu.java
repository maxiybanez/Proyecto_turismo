
package vistas;

import control.AlojamientoData;
import control.ClienteData;
import control.ConectarBD;
import control.DestinoData;
import control.ExtraAlojamientoData;
import control.PaqueteData;
import control.TransporteData;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Alojamiento;
import modelo.Cliente;
import modelo.Destino;
import modelo.ExtraAlojamiento;
import modelo.Paquete;
import modelo.Transporte;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;


public class Menu extends javax.swing.JFrame {
   
    //----ATRIBUTOS ------------------------------------------------------------
    
        ConectarBD conexion = new ConectarBD();
        
    //----CONSTRUCTOR-----------------------------------------------------------
    public Menu() {
        initComponents();
        
        borraTablasBD();                                                        
        preCargaBD();
        
        //Centramos el menu
        this.setLocationRelativeTo(null);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        
         setIconImage( new ImageIcon(getClass().getResource("/imagenes/icono 5.png")).getImage());
                
    }

    //--- METODO BORRAR TABLAS -------------------------------------------------
    
        private void borraTablasBD(){

        
            String query;
            PreparedStatement prepStatem;

            try {
          
                query = "DELETE FROM paquete ";
                prepStatem = conexion.getConexion().prepareStatement(query);
                prepStatem.executeUpdate();              

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
                                                                
                       
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null," Hubo un Problema al borrar las tablas de la BD!");
            }
    
    }
        
   //--- METODO PRE CARGA BD ---------------------------------------------
    
    private void preCargaBD(){
    
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
            clienteData.guardarCliente(suarezCarlos);
      
        


            
            
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
        destinoData.guardarDestino(rioDeJaneiro);
        
  
    //*******************************TRANSPORTE DATA*******************************
        
        //--- TESTEMOS TRANSPORTE DATA -----------------------------------------
        
        TransporteData transporteData = new TransporteData(conexion);
        
        // Transportes a Mar del Plata
        Transporte avionMDP = new Transporte(marDelPlata,"Avion MDP",5000,true); 
        Transporte colectivoMDP = new Transporte(marDelPlata,"Colectivo MDP",3750,true); 
        Transporte autoPropioMDP = new Transporte(marDelPlata,"Auto Propio MDP",0,true); 
 
                
        // Transportes a Cordoba
        Transporte avionCBA = new Transporte(cordoba,"Avion CBA",5500,true); 
        Transporte colectivoCBA = new Transporte(cordoba,"Colectivo CBA",3250,true); 
        Transporte autoPropioCBA = new Transporte(cordoba,"Auto Popio CBA",0,true); 
 
                
        // Transportes a Mendoza
        Transporte avionMZA = new Transporte(mendoza,"Avion MZA",7977,true); 
        Transporte colectivoMZA = new Transporte(mendoza,"Colectivo MZA",3515,true); 
        Transporte autoPopioMZA = new Transporte(mendoza,"Auto propio MZA",0,true); 

        
        // Transportes a Santiago de Chile
        Transporte avionSTCH = new Transporte(santiagoDeChile,"Avion STCH",7000,true); 
        Transporte colectivoSTCH = new Transporte(santiagoDeChile,"Colectivo STCH",3500,true); 
        Transporte autoPopioSTCH = new Transporte(santiagoDeChile,"Auto propio STCH",0,true); 
    
        
        // Transportes a Rio de Janeiro
        Transporte avionRJ = new Transporte(rioDeJaneiro,"Avion RJ",9700,true); 
        Transporte colectivoRJ = new Transporte(rioDeJaneiro,"Colectivo RJ",5350,true); 
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
        transporteData.guardarTransporte(autoPopioRJ);

        
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
        alojamientoData.guardarAlojamiento(dpto_2_CH);    
        
 
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
                
     //    extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_1_CH);
                  
         extraAlojamientoData.guardarExtraAlo(mediaPension_DPTO_2_CH );
         extraAlojamientoData.guardarExtraAlo(sinPension_DPTO_2_CH);
       
        
   

//*******************************PAQUETE DATA*****************************************
    
        //--- TESTEMOS PAQUETE DATA --------------------------------------------


        PaqueteData paqueteData = new PaqueteData(conexion);
        
        Paquete paq_SuarezCarlos_HT_MDP_MPD_Avion = new Paquete(avionMDP, pensionCompleta_HT_MDP_MDP , suarezCarlos, LocalDate.of(2020,03,01), LocalDate.of(2020,11,01), LocalDate.of(2021,12,07), 35700, true); 
        Paquete paq_SuarezCarlos_DPTO_1_MPD_Colectivo = new Paquete(colectivoMDP, sinPension_DPTO_1_MDP, suarezCarlos, LocalDate.of(2021,03,01), LocalDate.of(2021,10,01), LocalDate.of(2021,12,07), 23500, true); 
        
        Paquete paq_ArceMariana_HT_CCH_CH_Avion = new Paquete(avionMDP, mediaPension_HT_MDP, arceMariana, LocalDate.of(2022,02,20), LocalDate.of(2022,02,28), LocalDate.of(2021,10,19), 40900, true); 
 
        if (paqueteData.guardarPaquete(paq_ArceMariana_HT_CCH_CH_Avion)){

          JOptionPane.showMessageDialog(null,"La BD fue Borrada y Pre-Cargada!");
        }      
        
    //************************************************************************************
        
       
    
    }           
    
    
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/fondo1.jpg"));
        Image miImagen = icono.getImage();
        jDPEscritorio = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                g.drawImage(miImagen, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        jmArchivo = new javax.swing.JMenu();
        jmiPaquete = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiCliente = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmiAlojamiento = new javax.swing.JMenuItem();
        jmiExtraAlojamiento = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmiDestino = new javax.swing.JMenuItem();
        jmiTransporte = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmiSalir = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDPEscritorioLayout = new javax.swing.GroupLayout(jDPEscritorio);
        jDPEscritorio.setLayout(jDPEscritorioLayout);
        jDPEscritorioLayout.setHorizontalGroup(
            jDPEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1087, Short.MAX_VALUE)
        );
        jDPEscritorioLayout.setVerticalGroup(
            jDPEscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        jmArchivo.setText("Archivo");

        jmiPaquete.setText("Paquete");
        jmiPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPaqueteActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiPaquete);
        jmArchivo.add(jSeparator1);

        jmiCliente.setText("Cliente");
        jmiCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClienteActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiCliente);
        jmArchivo.add(jSeparator3);

        jmiAlojamiento.setText("Alojamiento");
        jmiAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAlojamientoActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiAlojamiento);

        jmiExtraAlojamiento.setText("ExtraAlojamiento");
        jmiExtraAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExtraAlojamientoActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiExtraAlojamiento);
        jmArchivo.add(jSeparator4);

        jmiDestino.setText("Destino");
        jmiDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiDestinoActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiDestino);

        jmiTransporte.setText("Transporte");
        jmiTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTransporteActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiTransporte);
        jmArchivo.add(jSeparator2);

        jmiSalir.setText("Salir");
        jmiSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSalirActionPerformed(evt);
            }
        });
        jmArchivo.add(jmiSalir);

        jMenuBar1.add(jmArchivo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDPEscritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDPEscritorio, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAlojamientoActionPerformed
  
        jDPEscritorio.removeAll();
        jDPEscritorio.repaint();
        
        jIntFrAlojamiento form = new jIntFrAlojamiento();
        
        // agregamos la vista al escritorio
        jDPEscritorio.add(form);
        // centramos la vista dentro del escritoio
        Dimension desktopSize = jDPEscritorio.getSize();
        Dimension FrameSize = form.getSize();
        form.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        // Seteamos visible al JInternalFrame
        form.setVisible(true);
    }//GEN-LAST:event_jmiAlojamientoActionPerformed

    private void jmiDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiDestinoActionPerformed
       
        jDPEscritorio.removeAll();
        jDPEscritorio.repaint();
        
        jIntFrDestino form = new jIntFrDestino();
        
        // agregamos la vista al escritorio
        jDPEscritorio.add(form);
        // centramos la vista dentro del escritoio
        Dimension desktopSize = jDPEscritorio.getSize();
        Dimension FrameSize = form.getSize();
        form.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        // Seteamos visible al JInternalFrame
        form.setVisible(true);
    }//GEN-LAST:event_jmiDestinoActionPerformed

    private void jmiPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPaqueteActionPerformed
     
        jDPEscritorio.removeAll();
        jDPEscritorio.repaint();
        
        jIntFrPaquete form = new jIntFrPaquete();
        
        // agregamos la vista al escritorio
        jDPEscritorio.add(form);
        // centramos la vista dentro del escritoio
        Dimension desktopSize = jDPEscritorio.getSize();
        Dimension FrameSize = form.getSize();
        form.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        // Seteamos visible al JInternalFrame
        form.setVisible(true);
        
    }//GEN-LAST:event_jmiPaqueteActionPerformed

    private void jmiClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClienteActionPerformed

        jDPEscritorio.removeAll();
        jDPEscritorio.repaint();
        
        jIntFrCliente form = new jIntFrCliente();
        
        // agregamos la vista al escritorio
        jDPEscritorio.add(form);
        // centramos la vista dentro del escritoio
        Dimension desktopSize = jDPEscritorio.getSize();
        Dimension FrameSize = form.getSize();
        form.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        // Seteamos visible al JInternalFrame
        form.setVisible(true);        
    }//GEN-LAST:event_jmiClienteActionPerformed

    private void jmiSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jmiSalirActionPerformed

    private void jmiExtraAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExtraAlojamientoActionPerformed
  
        jDPEscritorio.removeAll();
        jDPEscritorio.repaint();
        
        jIntFrExtraAlojamiento form = new jIntFrExtraAlojamiento();
        
        // agregamos la vista al escritorio
        jDPEscritorio.add(form);
        // centramos la vista dentro del escritoio
        Dimension desktopSize = jDPEscritorio.getSize();
        Dimension FrameSize = form.getSize();
        form.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        // Seteamos visible al JInternalFrame
        form.setVisible(true);
    }//GEN-LAST:event_jmiExtraAlojamientoActionPerformed

    private void jmiTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTransporteActionPerformed
  
        jDPEscritorio.removeAll();
        jDPEscritorio.repaint();
        
        jIntFrTransporte form = new jIntFrTransporte();
        
        // agregamos la vista al escritorio
        jDPEscritorio.add(form);
        // centramos la vista dentro del escritoio
        Dimension desktopSize = jDPEscritorio.getSize();
        Dimension FrameSize = form.getSize();
        form.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        // Seteamos visible al JInternalFrame
        form.setVisible(true);
    }//GEN-LAST:event_jmiTransporteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDPEscritorio;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenu jmArchivo;
    private javax.swing.JMenuItem jmiAlojamiento;
    private javax.swing.JMenuItem jmiCliente;
    private javax.swing.JMenuItem jmiDestino;
    private javax.swing.JMenuItem jmiExtraAlojamiento;
    private javax.swing.JMenuItem jmiPaquete;
    private javax.swing.JMenuItem jmiSalir;
    private javax.swing.JMenuItem jmiTransporte;
    // End of variables declaration//GEN-END:variables
}
