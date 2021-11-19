
package vistas;

import com.toedter.calendar.JDateChooser;
import control.AlojamientoData;
import control.ClienteData;
import control.ConectarBD;
import control.DestinoData;
import control.ExtraAlojamientoData;
import control.PaqueteData;
import control.TransporteData;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Alojamiento;
import modelo.Cliente;
import modelo.Destino;
import modelo.ExtraAlojamiento;
import modelo.Paquete;
import modelo.Transporte;


public class jIntFrPaquete extends javax.swing.JInternalFrame {

    //Atributos
    ConectarBD conexion = new ConectarBD();
    
    private ClienteData clienteData; 
    private DestinoData destinoData; 
    private TransporteData transporteData; 
    private AlojamientoData alojamientoData; 
    private ExtraAlojamientoData extraAlojamientoData; 
    private PaqueteData paqueteData; 
    
    private Cliente clienteSeleccionado;
    private Destino destinoSeleccionado;
    private Alojamiento alojamientoSeleccionado;
    private Transporte transporteSeleccionado;
    private ExtraAlojamiento extraAlojamientoSeleccionado;
    
    private int filaSeleccionada = -2;
    
    private int idPaqueteSeleccionado = -2;;
    
        
    private DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      
    private DefaultTableModel modelo;    
    
   
    
    //Constructor
    public jIntFrPaquete() {
        initComponents();
        
        modelo = new DefaultTableModel(){                                       
            @Override
            public boolean isCellEditable (int filas, int columnas){            
            
                    if(columnas == 11){
                        return true;
                    }else{
                        return false;
                    }
            }
        };
                
        clienteData = new ClienteData(conexion);
        destinoData = new DestinoData(conexion);
        transporteData = new TransporteData(conexion);
        alojamientoData = new AlojamientoData(conexion);
        extraAlojamientoData = new ExtraAlojamientoData(conexion);
        paqueteData = new PaqueteData(conexion);
    
        ocultarTodosLosLabelNoAgregados();
        llenarComboCliente();
        llenarComboDestino();
        
        jcbxCliente.setSelectedIndex(-1);
        jcbxDestino.setSelectedIndex(-1);
        jcbxTransporte.setSelectedIndex(-1);
        jcbxAlojamiento.setSelectedIndex(-1);
        jcbxExtraAlojamiento.setSelectedIndex(-1);
        
        limpiarCampos();
        
        colocarTitulosTabla();
        cargarTablaConPaquetes();
        

        // Le damos el ancho a las columnas 
        jtablePaquetes.setAutoResizeMode(jtablePaquetes.AUTO_RESIZE_OFF);
        jtablePaquetes.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtablePaquetes.getColumnModel().getColumn(0).setResizable(false);
        jtablePaquetes.getColumnModel().getColumn(1).setPreferredWidth(80);
        jtablePaquetes.getColumnModel().getColumn(2).setPreferredWidth(130);
        jtablePaquetes.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtablePaquetes.getColumnModel().getColumn(4).setPreferredWidth(150);
        jtablePaquetes.getColumnModel().getColumn(5).setPreferredWidth(160);
        jtablePaquetes.getColumnModel().getColumn(6).setPreferredWidth(250);
        jtablePaquetes.getColumnModel().getColumn(7).setPreferredWidth(80);
        jtablePaquetes.getColumnModel().getColumn(8).setPreferredWidth(80);
        jtablePaquetes.getColumnModel().getColumn(9).setPreferredWidth(80);
        jtablePaquetes.getColumnModel().getColumn(10).setPreferredWidth(70);

    }

    //**************************** METODOS *************************************
    
    //-------------------------------------------------------------------------- 
    private void desactivarPaquete(){
        
        
        if (idPaqueteSeleccionado >= 0 && (paqueteData.obtenerPaqueteXId(idPaqueteSeleccionado)).isActivo()){   //<---Si el paquete esta Activo, llama al metodo desactivar (de Data)
            
            if (paqueteData.desactivarPaquete(idPaqueteSeleccionado)){
                
                JOptionPane.showMessageDialog(null,"El Paquete fue desactivado Satisfactorioamente!");
            }  
  
        }else{                                                                  //<---Si no, llama al metodo actualizar (de la vista)
                jchkbxActivo.setSelected(true);
                if (actualizarPaquete()){
                    JOptionPane.showMessageDialog(null,"El Paquete fue Activado Satisfactorioamente!");
                
                }
        }
        
        limpiarCampos();
        borrarFilas();
        cargarTablaConPaquetes();
        jchkbxActivo.setForeground(Color.black);
        jbtDesactivar.setText("Desactivar");
        jbtDesactivar.setForeground(Color.black);


    }    
    
    //-------------------------------------------------------------------------- 
    
    private void cargarTablaConPaquetes(){
        String activo = "";
        borrarFilas();
        
           
            ArrayList<Paquete> listaPaquetes = paqueteData.obtenerTodosLosPaquetes();
            
            if (listaPaquetes.size() > 0 ){
            
                for( Paquete listaPaqueteIt : listaPaquetes){

                    if(listaPaqueteIt.isActivo()){                         
                       activo = "Activo";

                    }else{
                        activo = "No Activo";
                    }                   

                    modelo.addRow(new Object[]{
                        
                                listaPaqueteIt.getIdPaquete(),
                                listaPaqueteIt.getFechaEmision(),
                                listaPaqueteIt.getCliente(),
                                listaPaqueteIt.getTransporte().getDestino(),
                                listaPaqueteIt.getTransporte(),
                                listaPaqueteIt.getExtraAlojamiento().getAlojameinto(),      
                                listaPaqueteIt.getExtraAlojamiento(),
                                listaPaqueteIt.getFechaInicio(),
                                listaPaqueteIt.getFechaFin(),
                                listaPaqueteIt.getCostoTotal(),
                                activo                              
                                
                                });
                }
            }    
    }
    
    //--------------------------------------------------------------------------  
        
    private void borrarFilas(){
    
        //---CADA FILA EN LA EN EL MODELO, ES  UN AREGLO DE OBJETOS---
        
        int fila = (modelo.getRowCount() -1 ); //---tengo la pocision d ela ultima fila  
        
        // borramnos de atras para adelante 
        
        for (int i =  fila ; i >= 0 ; i-- ){
            
            modelo.removeRow(i);
        }
        
        
    }
    //--------------------------------------------------------------------------
    
    public void colocarTitulosTabla(){    
        
        ArrayList <Object> columnasTabla = new ArrayList<>();
        

        columnasTabla.add("");
        columnasTabla.add("Fecha Emision");
        columnasTabla.add("Cliente");
        columnasTabla.add("Destino");
        columnasTabla.add("Transporte");
        columnasTabla.add("Alojamiento");
        columnasTabla.add("ExtraAlojamiento");
        columnasTabla.add("Fecha Inicio");
        columnasTabla.add("Fecha Fin");
        columnasTabla.add("Costo Total");
        columnasTabla.add("Activo");
        
      
        
        for(Object columnaIt: columnasTabla){
            
            modelo.addColumn(columnaIt);
        }
        
        jtablePaquetes.setModel(modelo);
        
    }

    //--------------------------------------------------------------------------
    private void ocultarTodosLosLabelNoAgregados(){
    
        jlbNoAgregadosClientesBD.setVisible(false);
        jlbNoAgregadosAlojamientoBD.setVisible(false);
        jlbNoAgregadosDestinoBD.setVisible(false);
        jlbNoAgregadosExtraAlojBD.setVisible(false);
        jlbNoAgregadosTransporteBD.setVisible(false);
        
    }
        
    //--------------------------------------------------------------------------
    private void llenarComboCliente(){

        jcbxCliente.removeAllItems();
        
        ArrayList <Cliente> listaClientes =  clienteData.listarSoloClientesActivos();

        if(listaClientes != null){
                
            jlbNoAgregadosClientesBD.setVisible(false);
         
                
            for(Cliente cliIt: listaClientes){
                jcbxCliente.addItem(cliIt);
            }
        }else{
            jlbNoAgregadosClientesBD.setVisible(true);
    
        }      
  
    }     
    //--------------------------------------------------------------------------
    private void llenarComboDestino(){

        jcbxDestino.removeAllItems();

        ArrayList <Destino> listaDestino =  destinoData.obtenerTodosLosDestinosActivos();
       
        
        if(listaDestino.size() > 0){
                
                jlbNoAgregadosDestinoBD.setVisible(false);
              
                
                for(Destino destIt: listaDestino){

                    jcbxDestino.addItem(destIt);
                }
        }else{
            jlbNoAgregadosDestinoBD.setVisible(true);
          
        }  
      
    }  

    //--------------------------------------------------------------------------
    private void llenarComboAlojamiento(){

        jcbxAlojamiento.removeAllItems();

        ArrayList <Alojamiento> listaAlojamiento =  alojamientoData.obtenerTodosLosAlojamientoXDestino(destinoSeleccionado.getIdDestino());

        if(listaAlojamiento.size() > 0){
                
                jlbNoAgregadosAlojamientoBD.setVisible(false);
              
                
                for(Alojamiento alojIt: listaAlojamiento){
                    jcbxAlojamiento.addItem(alojIt);
                }
        }else{
            jlbNoAgregadosAlojamientoBD.setVisible(true);
    
        }  
    }    
    //--------------------------------------------------------------------------
    private void llenarComboExtraAlojamiento(){

        jcbxExtraAlojamiento.removeAllItems();
        
        ArrayList <ExtraAlojamiento> listaExtraAlojamiento =  extraAlojamientoData.listarExtrasXAlojamiento(alojamientoSeleccionado.getIdAlojamiento());
                
        if(listaExtraAlojamiento.size() > 0){
            
           jlbNoAgregadosExtraAlojBD.setVisible(false);
         
           
           for(ExtraAlojamiento extraAlojIt: listaExtraAlojamiento){
        
                jcbxExtraAlojamiento.addItem(extraAlojIt);
            }
                
        }else{
            
            jlbNoAgregadosExtraAlojBD.setVisible(true);
       
        }          
        
    }    
    //--------------------------------------------------------------------------
    private void llenarComboTransporte(){

        jcbxTransporte.removeAllItems();
        
        ArrayList <Transporte> listaTransporte =  transporteData.obtenerTodosLosTransportesXDestino(destinoSeleccionado.getIdDestino());
        
        if (listaTransporte.size() > 0){

            jlbNoAgregadosTransporteBD.setVisible(false);
         
            
            for(Transporte transpIt: listaTransporte){

                jcbxTransporte.addItem(transpIt);
            }
        }else{
            
            jlbNoAgregadosTransporteBD.setVisible(true);
       
        }
    }    
    //--------------------------------------------------------------------------

    public String getfecha(JDateChooser jd){    // este metodo lo que nos permite es obtener la fecha del jDATECHOOSER pasandolo como parametro y te lo devuelve como un string
        
        SimpleDateFormat formato= new SimpleDateFormat("dd-MM-yyyy");
        if(jd.getDate()!= null){
            return formato.format(jd.getDate());
        }else{
            return null;
        }
     }
    //--------------------------------------------------------------------------
    private void limpiarCampos(){
    
      LocalDate hoy = LocalDate.now();
          
       jdtChosFechaDeEmision.setDate(Date.valueOf(hoy));
       
       jcbxCliente.setSelectedIndex(-1);
       jcbxDestino.setSelectedIndex(-1);
       
       jcbxTransporte.removeAllItems();
       jcbxAlojamiento.removeAllItems();
       jcbxExtraAlojamiento.removeAllItems();
       
       jchkbxActivo.setSelected(true);
       
       jdtChosFechaDeInicio.setDate(null);
       jdtChosFechaDeFin.setDate(null);
       
       jtxtCostoPaquete.setText("");
       jtxtCostoTotal.setText("");
       
       jSpinnerCantidadDePersonas.setValue(Integer.valueOf(1));
    
    
    }
    //--------------------------------------------------------------------------
    private float calcularCostoTotal(JDateChooser fechaInicioParam, JDateChooser fechaFinParam, DateTimeFormatter f){
        float costoTotal = 0;
        
        
        try{
            
        
           if ( fechaInicioParam.getDate() != null && fechaFinParam.getDate() != null  ){

                LocalDate FechIni =  LocalDate.parse(getfecha(fechaInicioParam),f );
              //  Date fechaInicio = Date.valueOf(FechIni);        

                LocalDate FechFin =  LocalDate.parse(getfecha(fechaFinParam),f );
             //  Date fechaFin = Date.valueOf(FechFin);  



                int cantidadDeDias = (int) ChronoUnit.DAYS.between(FechIni,FechFin );
                int MesInicio = FechIni.getMonthValue();

                System.out.println("Mes: " + MesInicio);

                //  Calculamos el costo total 

                float costoTrasporte = transporteSeleccionado.getCosto();

                float costoAlojamientoXLosDias = alojamientoSeleccionado.getCosto() * cantidadDeDias ;

                float costoExtraAlojamientoXLosDias  = (extraAlojamientoSeleccionado.getCosto() * cantidadDeDias );  

                costoTotal = (costoTrasporte + costoAlojamientoXLosDias + costoExtraAlojamientoXLosDias);


                //  Calculamos el costo total de acuerdo a las temporadas (Alta, Media o Baja)

                if( MesInicio == 1 || MesInicio == 7 ){ // temporada alta de acuerdo a las fecha de inicio

                    costoTotal *= 1.30;

                }else if (MesInicio == 2 || MesInicio == 6){ // temporada media

                    costoTotal *= 1.15;
                }else{                                      // temporada baja (lo colocamos asi para que quede explicito)

                    costoTotal *= 1;
                }


                if (costoTotal <= 0){
                    costoTotal = 0;
                }

            
           }
            
        }catch(NumberFormatException ex){
            
        }     
        
    
        return costoTotal ;
    }
    //--------------------------------------------------------------------------
   
    private boolean controlaFechasInicioFechaFIn(JDateChooser fechaInicioParam, JDateChooser fechaFinParam, DateTimeFormatter f){
        boolean estado = false;
    
        LocalDate FechIni =  LocalDate.parse(getfecha(fechaInicioParam),f );
        Date fechaInicio = Date.valueOf(FechIni);        
   
        LocalDate FechFin =  LocalDate.parse(getfecha(fechaFinParam),f );
        Date fechaFin = Date.valueOf(FechFin);        
        
        LocalDate hoy = LocalDate.now();
        Date fechaActual = Date.valueOf(hoy);
  
                 
            if(fechaInicio.after(fechaActual)){

                 if(fechaFin.after(fechaInicio)){
                     estado = true;
                 }    

           }else{
                     estado = false;
           }
        
        return estado;
    }    

    //--------------------------------------------------------------------------
    private boolean actualizarPaquete(){
        boolean estado = false;
                 
            try{
               
                    if (jcbxCliente.getSelectedIndex() == -1 ){
                        JOptionPane.showMessageDialog(this,"Debe seleccionar un Cliente");
                        jcbxCliente.requestFocus();

                    }else if(jcbxDestino.getSelectedIndex() == -1 ){
                            JOptionPane.showMessageDialog(this,"Debe seleccionar un Destino");
                            jcbxDestino.requestFocus();

                        }else if(jcbxTransporte.getSelectedIndex() == -1 ){
                                JOptionPane.showMessageDialog(this,"Debe seleccionar un Transporte");
                                jcbxTransporte.requestFocus();

                            }else if(jcbxAlojamiento.getSelectedIndex() == -1 ){
                                    JOptionPane.showMessageDialog(this,"Debe seleccionar un Alojamiento");
                                    jcbxAlojamiento.requestFocus();                        

                                }else if(jcbxExtraAlojamiento.getSelectedIndex() == -1 ){
                                        JOptionPane.showMessageDialog(this,"Debe seleccionar un Extra-Alojamiento");
                                        jcbxExtraAlojamiento.requestFocus();

                                    }else if(jdtChosFechaDeEmision.getDate() == null){
                                            JOptionPane.showMessageDialog(this,"La Fecha de Emision no es correcta");
                                            jdtChosFechaDeEmision.requestFocus();

                                        }else if (jdtChosFechaDeInicio.getDate() == null){
                                                JOptionPane.showMessageDialog(this,"Debe seleccionar una Fecha de Inicio");
                                                jdtChosFechaDeInicio.requestFocus();
                               
                                                    }else if(jdtChosFechaDeFin.getDate() == null){
                                                            JOptionPane.showMessageDialog(this,"Debe seleccionar una Fecha de Fin");
                                                            jdtChosFechaDeFin.requestFocus();

                                                       }else if(!(controlaFechasInicioFechaFIn (jdtChosFechaDeInicio, jdtChosFechaDeFin, f ))){
                                                       
                                                            JOptionPane.showMessageDialog(this,"La Fecha de Fin debe ser mayor a la Fecha de Inicio\n\ny la Fecha de Inicio debe ser mayor a la Fecha de Emision");
                                                            jdtChosFechaDeFin.requestFocus();
                                               
                    
                                                          }else if(jtxtCostoTotal.getText().equals("")){
                                                            JOptionPane.showMessageDialog(this,"El Costo Total no es correcto");
                                                            jdtChosFechaDeFin.requestFocus();
                                                    
                                
                                                            }else{    

                                                                

                                                                LocalDate fechaEmision = LocalDate.parse(getfecha(jdtChosFechaDeEmision), f );
                                                                
                                                                LocalDate fechaInicio = LocalDate.parse(getfecha(jdtChosFechaDeInicio), f );
                                                                
                                                                LocalDate fechaFin = LocalDate.parse(getfecha(jdtChosFechaDeFin), f );
                                                                
                                                                
                                                                
                                                                
                                                                
                                                                boolean activo = jchkbxActivo.isSelected();
                                                                float costoTotal = Float.parseFloat(jtxtCostoTotal.getText());

                                                                Paquete  paquete = new Paquete(idPaqueteSeleccionado,transporteSeleccionado, extraAlojamientoSeleccionado , clienteSeleccionado,fechaInicio ,fechaFin ,fechaEmision , costoTotal, activo); 
                                                                
                                                                if (paqueteData.actualizarPaquete(paquete)){
                                                                
                                                                    estado= true;
                                                                
                                                                }

                                                        }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Algo esta mal. Revise los datos ingresados\n\nSino llame a los tecnicos");
            } 
        
        return estado;
    
    }    
    
    
    
    //--------------------------------------------------------------------------
    private boolean guardarPaquete(){
        boolean estado = false;
        
         
            try{
                
                   if(idPaqueteSeleccionado >= 0 && paqueteData.obtenerPaqueteXId(idPaqueteSeleccionado).getIdPaquete() == idPaqueteSeleccionado ){
                        JOptionPane.showMessageDialog(this,"Este paquete ya esta guardado en la BD");
            
                   }else if (jcbxCliente.getSelectedIndex() == -1 ){
                        JOptionPane.showMessageDialog(this,"Debe seleccionar un Cliente");
                        jcbxCliente.requestFocus();

                    }else if(jcbxDestino.getSelectedIndex() == -1 ){
                            JOptionPane.showMessageDialog(this,"Debe seleccionar un Destino");
                            jcbxDestino.requestFocus();

                        }else if(jcbxTransporte.getSelectedIndex() == -1 ){
                                JOptionPane.showMessageDialog(this,"Debe seleccionar un Transporte");
                                jcbxTransporte.requestFocus();

                            }else if(jcbxAlojamiento.getSelectedIndex() == -1 ){
                                    JOptionPane.showMessageDialog(this,"Debe seleccionar un Alojamiento");
                                    jcbxAlojamiento.requestFocus();                        

                                }else if(jcbxExtraAlojamiento.getSelectedIndex() == -1 ){
                                        JOptionPane.showMessageDialog(this,"Debe seleccionar un Extra-Alojamiento");
                                        jcbxExtraAlojamiento.requestFocus();

                                    }else if(jdtChosFechaDeEmision.getDate() == null){
                                            JOptionPane.showMessageDialog(this,"La Fecha de Emision no es correcta");
                                            jdtChosFechaDeEmision.requestFocus();

                                        }else if (jdtChosFechaDeInicio.getDate() == null){
                                                JOptionPane.showMessageDialog(this,"Debe seleccionar una Fecha de Inicio");
                                                jdtChosFechaDeInicio.requestFocus();
                               
                                                    }else if(jdtChosFechaDeFin.getDate() == null){
                                                            JOptionPane.showMessageDialog(this,"Debe seleccionar una Fecha de Fin");
                                                            jdtChosFechaDeFin.requestFocus();

                                                       }else if(!(controlaFechasInicioFechaFIn (jdtChosFechaDeInicio, jdtChosFechaDeFin, f ))){
                                                       
                                                            JOptionPane.showMessageDialog(this,"La Fecha de Fin debe ser mayor a la Fecha de Inicio\n\ny la Fecha de Inicio debe ser mayor a la Fecha de Emision");
                                                            jdtChosFechaDeFin.requestFocus();
                                               
                    
                                                          }else if(jtxtCostoTotal.getText().equals("")){
                                                            JOptionPane.showMessageDialog(this,"El Costo Total no es correcto");
                                                            jdtChosFechaDeFin.requestFocus();
                                                    
                                
                                                            }else{    

                                                                

                                                                LocalDate fechaEmision = LocalDate.parse(getfecha(jdtChosFechaDeEmision), f );
                                                                LocalDate fechaInicio = LocalDate.parse(getfecha(jdtChosFechaDeInicio), f );
                                                                LocalDate fechaFin = LocalDate.parse(getfecha(jdtChosFechaDeFin), f );
                                                                boolean activo = jchkbxActivo.isSelected();
                                                                float costoTotal = Float.parseFloat(jtxtCostoTotal.getText());

                                                                Paquete  paquete = new Paquete(transporteSeleccionado, extraAlojamientoSeleccionado , clienteSeleccionado,fechaInicio ,fechaFin ,fechaEmision , costoTotal, activo); 
                                                                
                                                                if (paqueteData.guardarPaquete(paquete)){
                                                                
                                                                    estado= true;
                                                                
                                                                }

                                                        }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Algo esta mal. Revise los datos ingresados");
            } 
        
        return estado;
    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbxDestino = new javax.swing.JComboBox<>();
        jlbDestino = new javax.swing.JLabel();
        jlbTransporte = new javax.swing.JLabel();
        jcbxTransporte = new javax.swing.JComboBox<>();
        jcbxExtraAlojamiento = new javax.swing.JComboBox<>();
        jlbExtraAlojamiento = new javax.swing.JLabel();
        jlbAlojamiento = new javax.swing.JLabel();
        jcbxAlojamiento = new javax.swing.JComboBox<>();
        jcbxCliente = new javax.swing.JComboBox<>();
        jlbCliente = new javax.swing.JLabel();
        jlbFechaDeInicio = new javax.swing.JLabel();
        jlbFechaDeFin = new javax.swing.JLabel();
        jlbFechaDeEmision = new javax.swing.JLabel();
        jlbCostoTotal = new javax.swing.JLabel();
        jdtChosFechaDeFin = new com.toedter.calendar.JDateChooser();
        jdtChosFechaDeInicio = new com.toedter.calendar.JDateChooser();
        jdtChosFechaDeEmision = new com.toedter.calendar.JDateChooser();
        jtxtCostoTotal = new javax.swing.JTextField();
        jlbNoAgregadosClientesBD = new javax.swing.JLabel();
        jlbNoAgregadosDestinoBD = new javax.swing.JLabel();
        jlbNoAgregadosAlojamientoBD = new javax.swing.JLabel();
        jlbNoAgregadosTransporteBD = new javax.swing.JLabel();
        jlbNoAgregadosExtraAlojBD = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jbtCalcular = new javax.swing.JButton();
        jchkbxActivo = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtablePaquetes = new javax.swing.JTable();
        jbtDesactivar = new javax.swing.JButton();
        jSpinnerCantidadDePersonas = new javax.swing.JSpinner();
        jlbCostoTotal1 = new javax.swing.JLabel();
        jtxtCostoPaquete = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jlbImgPaquete = new javax.swing.JLabel();

        setClosable(true);

        jcbxDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxDestinoActionPerformed(evt);
            }
        });

        jlbDestino.setText("Destino:");

        jlbTransporte.setText("Transporte:");

        jcbxTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxTransporteActionPerformed(evt);
            }
        });

        jcbxExtraAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxExtraAlojamientoActionPerformed(evt);
            }
        });

        jlbExtraAlojamiento.setText("Extra-Alojamiento:");

        jlbAlojamiento.setText("Alojamiento:");

        jcbxAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxAlojamientoActionPerformed(evt);
            }
        });

        jcbxCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxClienteActionPerformed(evt);
            }
        });

        jlbCliente.setText("Cliente:");

        jlbFechaDeInicio.setText("Fecha Inicio:");

        jlbFechaDeFin.setText("Fecha Fin:");

        jlbFechaDeEmision.setText("Fecha Emision:");

        jlbCostoTotal.setText("Costo Total:");

        jdtChosFechaDeFin.setDateFormatString("dd-MM-yyyy");

        jdtChosFechaDeInicio.setDateFormatString("dd-MM-yyyy");

        jdtChosFechaDeEmision.setDateFormatString("dd-MM-yyyy");

        jtxtCostoTotal.setEditable(false);
        jtxtCostoTotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jlbNoAgregadosClientesBD.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jlbNoAgregadosClientesBD.setText("( No Cargado )");

        jlbNoAgregadosDestinoBD.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jlbNoAgregadosDestinoBD.setText("( No Cargado )");

        jlbNoAgregadosAlojamientoBD.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jlbNoAgregadosAlojamientoBD.setText("( No Cargado )");

        jlbNoAgregadosTransporteBD.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jlbNoAgregadosTransporteBD.setText("( No Cargado )");

        jlbNoAgregadosExtraAlojBD.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jlbNoAgregadosExtraAlojBD.setText("( No Cargado )");

        jButton1.setText("Guardar Paquete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Actualizar Paquete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Nuevo Paquete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jbtCalcular.setText("Calcular Costos");
        jbtCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCalcularActionPerformed(evt);
            }
        });

        jchkbxActivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkbxActivo.setText("Activo");
        jchkbxActivo.setEnabled(false);

        jtablePaquetes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtablePaquetes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtablePaquetesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtablePaquetes);

        jbtDesactivar.setText("Desactivar Paquete");
        jbtDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtDesactivarActionPerformed(evt);
            }
        });

        jSpinnerCantidadDePersonas.setModel(new javax.swing.SpinnerNumberModel(1, 1, 70, 1));
        jSpinnerCantidadDePersonas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerCantidadDePersonasStateChanged(evt);
            }
        });
        jSpinnerCantidadDePersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSpinnerCantidadDePersonasMouseClicked(evt);
            }
        });

        jlbCostoTotal1.setText("Costo Paquete :");

        jtxtCostoPaquete.setEditable(false);

        jLabel1.setText("Persona/s");

        jlbImgPaquete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo3.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 4, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlbFechaDeInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlbFechaDeFin, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jlbExtraAlojamiento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jlbCostoTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jdtChosFechaDeFin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jdtChosFechaDeInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(43, 43, 43)
                                                        .addComponent(jchkbxActivo))
                                                    .addComponent(jcbxExtraAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jlbNoAgregadosExtraAlojBD))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jtxtCostoPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(140, 140, 140))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                                .addComponent(jbtCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlbCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(jtxtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinnerCantidadDePersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jbtDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(174, 174, 174))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlbAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcbxAlojamiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlbTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcbxTransporte, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jlbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jcbxDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jlbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlbFechaDeEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jdtChosFechaDeEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jcbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlbNoAgregadosAlojamientoBD)
                                    .addComponent(jlbNoAgregadosClientesBD)
                                    .addComponent(jlbNoAgregadosTransporteBD)
                                    .addComponent(jlbNoAgregadosDestinoBD))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jlbImgPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdtChosFechaDeEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbFechaDeEmision))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbCliente)
                            .addComponent(jcbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbNoAgregadosClientesBD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlbDestino)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbNoAgregadosDestinoBD))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbxTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbTransporte)
                            .addComponent(jlbNoAgregadosTransporteBD))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbAlojamiento)
                            .addComponent(jcbxAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbNoAgregadosAlojamientoBD))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jlbNoAgregadosExtraAlojBD))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcbxExtraAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbExtraAlojamiento))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchkbxActivo)
                            .addComponent(jdtChosFechaDeInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlbFechaDeInicio, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jdtChosFechaDeFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbFechaDeFin))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxtCostoPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbCostoTotal1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtxtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbCostoTotal)
                                    .addComponent(jSpinnerCantidadDePersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jbtCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jbtDesactivar)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addComponent(jlbImgPaquete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(2, 2, 2)))
                .addGap(31, 31, 31))
        );

        jlbNoAgregadosClientesBD.getAccessibleContext().setAccessibleName("(No hay cargados)");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxDestinoActionPerformed
        
        jcbxTransporte.removeAllItems();
        jcbxAlojamiento.removeAllItems();
        jcbxExtraAlojamiento.removeAllItems();
        
        
        destinoSeleccionado = (Destino) jcbxDestino.getSelectedItem(); 
            
       if(destinoSeleccionado != null ){    
           llenarComboAlojamiento();
           llenarComboTransporte();
           
           alojamientoSeleccionado = (Alojamiento) jcbxAlojamiento.getSelectedItem();
           transporteSeleccionado = (Transporte) jcbxTransporte.getSelectedItem(); 
        }

    }//GEN-LAST:event_jcbxDestinoActionPerformed

    private void jcbxTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxTransporteActionPerformed
                
        transporteSeleccionado = (Transporte) jcbxTransporte.getSelectedItem(); 
    }//GEN-LAST:event_jcbxTransporteActionPerformed

    private void jcbxAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxAlojamientoActionPerformed
 
        jcbxExtraAlojamiento.removeAllItems();
        
        
        alojamientoSeleccionado = (Alojamiento) jcbxAlojamiento.getSelectedItem();
        extraAlojamientoSeleccionado = (ExtraAlojamiento) jcbxExtraAlojamiento.getSelectedItem(); 
        
       if(alojamientoSeleccionado != null ){    
            llenarComboExtraAlojamiento();
            extraAlojamientoSeleccionado = (ExtraAlojamiento) jcbxExtraAlojamiento.getSelectedItem();
            
            if(extraAlojamientoSeleccionado != null ){ 
                jlbNoAgregadosExtraAlojBD.setVisible(false);
            }else{
                jlbNoAgregadosExtraAlojBD.setVisible(true);
            }
            
            
       }
    }//GEN-LAST:event_jcbxAlojamientoActionPerformed

    private void jcbxClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxClienteActionPerformed
        
        jcbxDestino.setSelectedIndex(-1);
        jcbxTransporte.removeAllItems();
        jcbxAlojamiento.removeAllItems();
        jcbxExtraAlojamiento.removeAllItems();
        
        
        clienteSeleccionado = (Cliente) jcbxCliente.getSelectedItem(); 
    }//GEN-LAST:event_jcbxClienteActionPerformed

    private void jcbxExtraAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxExtraAlojamientoActionPerformed
 
      extraAlojamientoSeleccionado  = (ExtraAlojamiento) jcbxExtraAlojamiento.getSelectedItem();
    }//GEN-LAST:event_jcbxExtraAlojamientoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (actualizarPaquete()){
        
            JOptionPane.showMessageDialog(null," El Paquete fue Actualizado Satisfactorioamente!");
            limpiarCampos();
            cargarTablaConPaquetes();
        
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jbtCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCalcularActionPerformed
        
        float costoPaquete = (calcularCostoTotal(jdtChosFechaDeInicio,jdtChosFechaDeFin,f));
        
        jtxtCostoPaquete.setText(costoPaquete+"") ;
        
        jtxtCostoTotal.setText ((costoPaquete * (Integer)jSpinnerCantidadDePersonas.getValue())+"");
        
    }//GEN-LAST:event_jbtCalcularActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (guardarPaquete()){
        
            JOptionPane.showMessageDialog(null," El Paquete fue Guardado Satisfactorioamente!");
            limpiarCampos();
            borrarFilas();
            cargarTablaConPaquetes();
        
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtablePaquetesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtablePaquetesMouseClicked
       
        if (evt.getClickCount() == 1){
        
            limpiarCampos();
        
        }else if (evt.getClickCount() == 2){

            filaSeleccionada = jtablePaquetes.getSelectedRow() ;

            
            if ( filaSeleccionada != -1){
                
                idPaqueteSeleccionado = (Integer)jtablePaquetes.getValueAt(filaSeleccionada, 0);
                            
                
                jdtChosFechaDeEmision.setDate(  Date.valueOf( (LocalDate) jtablePaquetes.getValueAt(filaSeleccionada, 1))); // como la fila seleccionada trae un arreglo de objetos, la convierto a localdate 
                            
                
                
                
                clienteSeleccionado = (Cliente)jtablePaquetes.getValueAt(filaSeleccionada, 2);
                jcbxCliente.setSelectedItem(clienteSeleccionado);
                
                destinoSeleccionado = (Destino)jtablePaquetes.getValueAt(filaSeleccionada, 3);
                jcbxDestino.setSelectedItem(destinoSeleccionado);
                
                transporteSeleccionado = (Transporte)jtablePaquetes.getValueAt(filaSeleccionada, 4);
                jcbxTransporte.setSelectedItem(transporteSeleccionado);
                
                alojamientoSeleccionado = (Alojamiento)jtablePaquetes.getValueAt(filaSeleccionada, 5);
                jcbxAlojamiento.setSelectedItem(alojamientoSeleccionado);
                
                extraAlojamientoSeleccionado = (ExtraAlojamiento)jtablePaquetes.getValueAt(filaSeleccionada, 6);
                jcbxExtraAlojamiento.setSelectedItem(extraAlojamientoSeleccionado);
                
                
                
                
                jdtChosFechaDeInicio.setDate(Date.valueOf((LocalDate)jtablePaquetes.getValueAt(filaSeleccionada, 7)));
                
                jdtChosFechaDeFin.setDate(Date.valueOf((LocalDate) jtablePaquetes.getValueAt(filaSeleccionada, 8)));
                
                
                jtxtCostoPaquete.setText((jtablePaquetes.getValueAt(filaSeleccionada, 9))+"");
                jtxtCostoTotal.setText(jtxtCostoPaquete.getText());
                jSpinnerCantidadDePersonas.setValue(Integer.valueOf(1));
              
                
                String activoFila = (jtablePaquetes.getValueAt(filaSeleccionada, 10)+"");
                

                if(activoFila.equals("Activo")){                         
                   jchkbxActivo.setSelected(true);
                   jchkbxActivo.setForeground(Color.black);
                   jbtDesactivar.setText("Desactivar");
                   jbtDesactivar.setForeground(Color.black);
                   
                   
                }else if(activoFila.equals("No Activo")){
                    jchkbxActivo.setSelected (false);
                    jchkbxActivo.setForeground(Color.red);
                    jbtDesactivar.setText("Activar");
                    jbtDesactivar.setForeground(Color.red);
                }  
            
           }
        }  
        
        
            

        
        

        
    }//GEN-LAST:event_jtablePaquetesMouseClicked

    private void jbtDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDesactivarActionPerformed
        desactivarPaquete(); 
       
    }//GEN-LAST:event_jbtDesactivarActionPerformed

    private void jSpinnerCantidadDePersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSpinnerCantidadDePersonasMouseClicked
        jtxtCostoTotal.setText("");
    }//GEN-LAST:event_jSpinnerCantidadDePersonasMouseClicked

    private void jSpinnerCantidadDePersonasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerCantidadDePersonasStateChanged
        jtxtCostoTotal.setText("");
    }//GEN-LAST:event_jSpinnerCantidadDePersonasStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerCantidadDePersonas;
    private javax.swing.JButton jbtCalcular;
    private javax.swing.JButton jbtDesactivar;
    private javax.swing.JComboBox<Alojamiento> jcbxAlojamiento;
    private javax.swing.JComboBox<Cliente> jcbxCliente;
    private javax.swing.JComboBox<Destino> jcbxDestino;
    private javax.swing.JComboBox<ExtraAlojamiento> jcbxExtraAlojamiento;
    private javax.swing.JComboBox<Transporte> jcbxTransporte;
    private javax.swing.JCheckBox jchkbxActivo;
    private com.toedter.calendar.JDateChooser jdtChosFechaDeEmision;
    private com.toedter.calendar.JDateChooser jdtChosFechaDeFin;
    private com.toedter.calendar.JDateChooser jdtChosFechaDeInicio;
    private javax.swing.JLabel jlbAlojamiento;
    private javax.swing.JLabel jlbCliente;
    private javax.swing.JLabel jlbCostoTotal;
    private javax.swing.JLabel jlbCostoTotal1;
    private javax.swing.JLabel jlbDestino;
    private javax.swing.JLabel jlbExtraAlojamiento;
    private javax.swing.JLabel jlbFechaDeEmision;
    private javax.swing.JLabel jlbFechaDeFin;
    private javax.swing.JLabel jlbFechaDeInicio;
    private javax.swing.JLabel jlbImgPaquete;
    private javax.swing.JLabel jlbNoAgregadosAlojamientoBD;
    private javax.swing.JLabel jlbNoAgregadosClientesBD;
    private javax.swing.JLabel jlbNoAgregadosDestinoBD;
    private javax.swing.JLabel jlbNoAgregadosExtraAlojBD;
    private javax.swing.JLabel jlbNoAgregadosTransporteBD;
    private javax.swing.JLabel jlbTransporte;
    private javax.swing.JTable jtablePaquetes;
    private javax.swing.JTextField jtxtCostoPaquete;
    private javax.swing.JTextField jtxtCostoTotal;
    // End of variables declaration//GEN-END:variables
}
