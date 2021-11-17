
package vistas;

import control.ConectarBD;
import control.DestinoData;
import control.TransporteData;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Destino;
import modelo.Transporte;

public class jIntFrTransporte extends javax.swing.JInternalFrame {

    
    // Atributos
    private DefaultTableModel modelo;
   
    private Destino destinoSeleccionado;
    private DestinoData destinoData;
    private TransporteData transporteData;
    private boolean actualizoTransporte = false;
    private boolean guardadoTransporte = false;
    private int idTransporteSeleccionado = -2;
    private int idDestinoSeleccionado = -2;
    private int filaSeleccionada = -2;
        
    ConectarBD conexion = new ConectarBD();
    //Constructor
    public jIntFrTransporte() {
        initComponents();
                modelo = new DefaultTableModel(){                                       
            @Override
            public boolean isCellEditable (int filas, int columnas){            
            
                    if(columnas == 4){
                        return true;
                    }else{
                        return false;
                    }
            }
        };
        
        llenarCombo();
        colocarTitulosTabla();

        jcbxDestino.setSelectedIndex(-1);
        
        // Le damos el ancho a las columnas 
        jtbleTransportes.setAutoResizeMode(jtbleTransportes.AUTO_RESIZE_OFF);
        jtbleTransportes.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtbleTransportes.getColumnModel().getColumn(0).setResizable(false);
        jtbleTransportes.getColumnModel().getColumn(1).setPreferredWidth(250);
        jtbleTransportes.getColumnModel().getColumn(2).setPreferredWidth(100);
        jtbleTransportes.getColumnModel().getColumn(3).setPreferredWidth(65);
       jbtGuardar.setEnabled(false);
       jbtActualizar.setEnabled(false);
          jbtDesactivar.setEnabled(false);
          jCheckBActivo.setEnabled(false);
          jCheckBActivo.setForeground(Color.black);
    }

    //**************************** METODOS *************************************
     //-------------------------------------------------------------------------  
    
    
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
        columnasTabla.add("Tipo de Transporte");
        columnasTabla.add("Costo");
        columnasTabla.add("Activo");
      
        
        for(Object columnaIt: columnasTabla){
            
            modelo.addColumn(columnaIt);
        }
        
        jtbleTransportes.setModel(modelo);
        
    }
    
    //--------------------------------------------------------------------------
    private void llenarCombo(){

        jcbxDestino.removeAllItems();
        
        DestinoData destinoData = new DestinoData(conexion);
        
        ArrayList <Destino> destino = (ArrayList) destinoData.obtenerTodosLosDestinos();
        
        for(Destino destIt: destino){
        
            jcbxDestino.addItem(destIt);
        }
    }    
    
    //--------------------------------------------------------------------------
    private void cargarTablaConTransnportes(){
        String activo = "";
        borrarFilas();
        
        destinoSeleccionado = (Destino) jcbxDestino.getSelectedItem();               
        
        transporteData = new TransporteData(conexion);
         
        if(destinoSeleccionado != null ){
                   
            ArrayList<Transporte> listaTransportes = transporteData.obtenerTodosLosTransportes();
    
            for( Transporte listTranspjIt : listaTransportes){


      
                    if( listTranspjIt.getDestino().getIdDestino() == destinoSeleccionado.getIdDestino()){

                        if(listTranspjIt.isActivo()){                         
                           activo = activo = "Activo";
                        }else{
                            activo = "No Activo";
                        }                   
                      
                        modelo.addRow(new Object[]{listTranspjIt.getIdTransporte(),listTranspjIt.getTipoDeTransporte(), listTranspjIt.getCosto(), activo});
                    }
            }
        }    
    }
    
    //--------------------------------------------------------------------------
    
    private boolean guardarCambios(){
               
        destinoSeleccionado = (Destino) jcbxDestino.getSelectedItem(); 
        
        transporteData = new TransporteData(conexion);
        
        boolean estado = false;
    
        try {

                if (jtxtTipoDeTransporte.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Transporte debe ser completado");
                    jtxtTipoDeTransporte.requestFocus();
                    
                }else if(jtxtTipoDeTransporte.getText().length() > 50){   
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Transporte puede tener un maximo de 50 caracteres");
                    jtxtTipoDeTransporte.requestFocus();
                    
                                }else if (jtxtCostoTransporte.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "El campo Costo debe ser completado");
                                    jtxtCostoTransporte.requestFocus();

                                    }else{    

                                        String tipoTransporte = jtxtTipoDeTransporte.getText(); 
                                        float costo = Float.parseFloat(jtxtCostoTransporte.getText());  //<---Con este ParseFloat adentro del try, controlamos que sea solo un numero
                                        boolean activo = true;
                                        
                                        if (costo < 0){
                                            JOptionPane.showMessageDialog(null, "El Costo debe ser mayor a $0.00" );
                                        }else{  

                                            if (transporteData.guardarTransporte(new Transporte( destinoSeleccionado,tipoTransporte,costo, activo))) {
                                                limpiaJText();
                                                jCheckBActivo.setForeground(Color.black);
                                                jbtDesactivar.setText("Desactivar");
                                                jbtDesactivar.setForeground(Color.black);
                                                jcbxDestino.setSelectedIndex(-1);
                                                estado = true;
                                            }
                                        }    
                                    }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo Costo solo acepta un precio");
                jtxtCostoTransporte.requestFocus();
        }

        return estado;
    }
    
    //--------------------------------------------------------------------------    
    private void limpiaJText(){
        jtxtTipoDeTransporte.setText("");
        jtxtCostoTransporte.setText("");
        jCheckBActivo.setSelected(false);
        jbtDesactivar.setEnabled(false);

    
    }
    //--------------------------------------------------------------------------
        private void desactivarTransporte(){
        
        
        if (transporteData.obtenerTransporteXId(idTransporteSeleccionado).isActivo()){ //<---Si el extraAlojamiento esta Activo, llama al metodo desactivar (del Data)
            
            if (transporteData.desactivarTransporte(idTransporteSeleccionado)){
                
                JOptionPane.showMessageDialog(null,"El Transporte fue Desactivado Satisfactorioamente!");
            }  
  
        }else{                                                                  //<---Si no, llama al metodo actualizar (de la vista)
                
                jCheckBActivo.setSelected(true);
                if (actualizarTransporte(idTransporteSeleccionado)){
                    JOptionPane.showMessageDialog(null,"El Transporte fue Activado Satisfactorioamente!");
                }
        }

        
        
        llenarCombo();
        borrarFilas();
        limpiaJText();
        jCheckBActivo.setForeground(Color.black);
        jbtDesactivar.setText("Desactivar");
        jbtDesactivar.setForeground(Color.black);
        jcbxDestino.setSelectedIndex(-1);


    }
    //--------------------------------------------------------------------------  

    
       //--------------------------------------------------------------------------
    private boolean actualizarTransporte( int idParam){
        boolean estado = false;
    
        
        try {

            
                if (jtxtTipoDeTransporte.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Transporte debe ser completado");
                    jtxtTipoDeTransporte.requestFocus();
                    
                }else if(jtxtTipoDeTransporte.getText().length() > 50){   
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Transporte puede tener un maximo de 50 caracteres");
                    jtxtTipoDeTransporte.requestFocus();
                    
                                }else if (jtxtCostoTransporte.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "El campo Costo debe ser completado");
                                    jtxtCostoTransporte.requestFocus();

                                    }else{    

                                        String tipoTransporte = jtxtTipoDeTransporte.getText(); 
                                        float costo = Float.parseFloat(jtxtCostoTransporte.getText());  //<---Con este ParseFloat adentro del try, controlamos que sea solo un numero
                                        boolean activo = jCheckBActivo.isSelected();
                                        
                                        

                                        if (costo < 0){
                                            JOptionPane.showMessageDialog(null, "El Costo debe ser mayor a $0.00" );
                                        }else{  

                                            if (transporteData.actualizarTransporte(new Transporte(idParam, destinoSeleccionado,tipoTransporte,costo, activo))) {
                                                limpiaJText();
                                                jCheckBActivo.setForeground(Color.black);
                                                jbtDesactivar.setText("Desactivar");
                                                jbtDesactivar.setForeground(Color.black);
                                                jcbxDestino.setSelectedIndex(-1);
                                                estado = true;
                                            }
                                        }    
                                    }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo Costo solo acepta un precio");
                jtxtCostoTransporte.requestFocus();
        }

        return estado;
}
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbxDestino = new javax.swing.JComboBox<>();
        jlbDestino = new javax.swing.JLabel();
        jlbTituloTransporte = new javax.swing.JLabel();
        jlbTipoDeTransporte = new javax.swing.JLabel();
        jtxtTipoDeTransporte = new javax.swing.JTextField();
        jtxtCostoTransporte = new javax.swing.JTextField();
        jlbCosto = new javax.swing.JLabel();
        jCheckBActivo = new javax.swing.JCheckBox();
        jbtCancelar = new javax.swing.JButton();
        jbtDesactivar = new javax.swing.JButton();
        jbtActualizar = new javax.swing.JButton();
        jbtGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbleTransportes = new javax.swing.JTable();

        setClosable(true);

        jcbxDestino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbxDestinoMouseClicked(evt);
            }
        });
        jcbxDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxDestinoActionPerformed(evt);
            }
        });

        jlbDestino.setText("Destino :");

        jlbTituloTransporte.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jlbTituloTransporte.setText("Transportes");

        jlbTipoDeTransporte.setText("Tipo de Transporte:");

        jlbCosto.setText("Costo:                            $");

        jCheckBActivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jCheckBActivo.setText("Activo");

        jbtCancelar.setText("Cancelar");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        jbtDesactivar.setText("Desactivar");
        jbtDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtDesactivarActionPerformed(evt);
            }
        });

        jbtActualizar.setText("Actualizar");
        jbtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtActualizarActionPerformed(evt);
            }
        });

        jbtGuardar.setText("Guardar");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jtbleTransportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jtbleTransportes.setToolTipText("doble click");
        jtbleTransportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbleTransportesMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jtbleTransportesMouseExited(evt);
            }
        });
        jtbleTransportes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jtbleTransportesPropertyChange(evt);
            }
        });
        jtbleTransportes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtbleTransportesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtbleTransportes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlbTipoDeTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtTipoDeTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlbCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBActivo)
                                .addGap(157, 157, 157)
                                .addComponent(jbtCancelar)
                                .addGap(25, 25, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtCostoTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jlbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlbTituloTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jlbTituloTransporte)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbDestino))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTipoDeTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbTipoDeTransporte))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCostoTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbCosto))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBActivo)
                    .addComponent(jbtCancelar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtGuardar)
                    .addComponent(jbtDesactivar)
                    .addComponent(jbtActualizar))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbxDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxDestinoActionPerformed
        cargarTablaConTransnportes();
    }//GEN-LAST:event_jcbxDestinoActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed

        limpiaJText();

        jbtGuardar.setEnabled(false);
        jbtActualizar.setEnabled(false);
        jbtDesactivar.setEnabled(false);
        borrarFilas();
        jcbxDestino.requestFocus();
        jcbxDestino.setSelectedIndex(-1);
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDesactivarActionPerformed
        desactivarTransporte();
        
    }//GEN-LAST:event_jbtDesactivarActionPerformed

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed

        if (actualizarTransporte(idTransporteSeleccionado)){
            JOptionPane.showMessageDialog(null,"El Transporte fue Actualizado Satisfactorioamente!");
        }
    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        if ( guardarCambios()){
            JOptionPane.showMessageDialog(null, "Transporte Guardado!");
            actualizoTransporte = false;
            cargarTablaConTransnportes();
            jtxtTipoDeTransporte.setText("");
            jbtDesactivar.setEnabled(false);

        }
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jtbleTransportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbleTransportesMouseClicked
        
        
        
        if (evt.getClickCount() == 1){

            limpiaJText();

        }else if (evt.getClickCount() == 2){

            filaSeleccionada = jtbleTransportes.getSelectedRow() ;

            if ( filaSeleccionada != -1){

                idTransporteSeleccionado = (Integer)jtbleTransportes.getValueAt(filaSeleccionada, 0);

                jtxtTipoDeTransporte.setText((jtbleTransportes.getValueAt(filaSeleccionada, 1)+""));
                jtxtCostoTransporte.setText((jtbleTransportes.getValueAt(filaSeleccionada, 2)+""));

                String tipoTransporte = (jtbleTransportes.getValueAt(filaSeleccionada, 3)+"");

                if(tipoTransporte.equals("Activo")){
                    jCheckBActivo.setSelected(true);
                    jCheckBActivo.setForeground(Color.black);
                    jbtDesactivar.setText("Desactivar");
                    jbtDesactivar.setForeground(Color.black);

                }else if(tipoTransporte.equals("No Activo")){
                    jCheckBActivo.setSelected(false);
                    jCheckBActivo.setForeground(Color.red);
                    jbtDesactivar.setText("Activar");
                    jbtDesactivar.setForeground(Color.red);
                }

                jtxtCostoTransporte.requestFocus();
                jtxtCostoTransporte.selectAll();
                jbtDesactivar.setEnabled(true);
                jbtActualizar.setEnabled(true);
                destinoSeleccionado = (Destino)jcbxDestino.getSelectedItem();
                idDestinoSeleccionado = destinoSeleccionado.getIdDestino();

            }
        }
    }//GEN-LAST:event_jtbleTransportesMouseClicked

    private void jtbleTransportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbleTransportesMouseExited

    }//GEN-LAST:event_jtbleTransportesMouseExited

    private void jtbleTransportesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtbleTransportesPropertyChange

    }//GEN-LAST:event_jtbleTransportesPropertyChange

    private void jtbleTransportesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbleTransportesKeyPressed
        limpiaJText();
    }//GEN-LAST:event_jtbleTransportesKeyPressed

    private void jcbxDestinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbxDestinoMouseClicked
        // TODO add your handling code here:
        jbtGuardar.setEnabled(true);
    }//GEN-LAST:event_jcbxDestinoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBActivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtDesactivar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JComboBox<Destino> jcbxDestino;
    private javax.swing.JLabel jlbCosto;
    private javax.swing.JLabel jlbDestino;
    private javax.swing.JLabel jlbTipoDeTransporte;
    private javax.swing.JLabel jlbTituloTransporte;
    private javax.swing.JTable jtbleTransportes;
    private javax.swing.JTextField jtxtCostoTransporte;
    private javax.swing.JTextField jtxtTipoDeTransporte;
    // End of variables declaration//GEN-END:variables
}
