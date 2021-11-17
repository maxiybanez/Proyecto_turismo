
package vistas;

import control.AlojamientoData;
import control.ConectarBD;
import control.ExtraAlojamientoData;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Alojamiento;

import modelo.ExtraAlojamiento;


public class jIntFrExtraAlojamiento extends javax.swing.JInternalFrame {

    // Atributos
    private DefaultTableModel modelo;
   
    private Alojamiento alojamientoSeleccionado;
    private AlojamientoData alojamientoData;
    private ExtraAlojamientoData extraAlojamientoData;
    private boolean actualizoExtraAlojameinto = false;
    private boolean guardarExtraAlojameinto = false;
     private int idExtraAlojamientoSeleccionado = -2;
    private int idAlojamientoSeleccionado = -2;
    private int filaSeleccionada = -2;
        
    ConectarBD conexion = new ConectarBD();
    
    //Constructor
    public jIntFrExtraAlojamiento() {
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

        jcbxAlojamiento.setSelectedIndex(-1);
        
        // Le damos el ancho a las columnas 
        jtbleExtraAlojamientosXAlojamiento.setAutoResizeMode(jtbleExtraAlojamientosXAlojamiento.AUTO_RESIZE_OFF);
        jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumn(0).setPreferredWidth(0);
        jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumn(0).setResizable(false);
        jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumn(1).setPreferredWidth(250);
        jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumn(2).setPreferredWidth(100);
        jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumn(3).setPreferredWidth(65);
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
        columnasTabla.add("Tipo de Menu");
        columnasTabla.add("Costo");
        columnasTabla.add("Activo");
      
        
        for(Object columnaIt: columnasTabla){
            
            modelo.addColumn(columnaIt);
        }
        
        jtbleExtraAlojamientosXAlojamiento.setModel(modelo);
        
    }
    
    //--------------------------------------------------------------------------
    private void llenarCombo(){

        jcbxAlojamiento.removeAllItems();
        
        AlojamientoData alojamientoData = new AlojamientoData(conexion);
        
        ArrayList <Alojamiento> alojamiento = (ArrayList) alojamientoData.obtenerTodosLosAlojamiento();
        
        for(Alojamiento alojIt: alojamiento){
        
            jcbxAlojamiento.addItem(alojIt);
        }
    }    
    
    //--------------------------------------------------------------------------
    private void cargarTablaConExtraAlojamientos(){
        String activo = "";
        borrarFilas();
        
        
        alojamientoSeleccionado = (Alojamiento) jcbxAlojamiento.getSelectedItem();               
        
        extraAlojamientoData = new ExtraAlojamientoData(conexion);
         
        if(alojamientoSeleccionado != null ){
                   
            ArrayList<ExtraAlojamiento> listaExtraAlojamientos = extraAlojamientoData.listarExtras();
    
            for( ExtraAlojamiento listExtraAlojIt : listaExtraAlojamientos){


      
                    if( listExtraAlojIt.getAlojameinto().getIdAlojamiento() == alojamientoSeleccionado.getIdAlojamiento()){

                        if(listExtraAlojIt.isActivo()){                         
                           activo = activo = "Activo";
                           
                        }else{
                            activo = "No Activo";
                        }                   
                      
                        modelo.addRow(new Object[]{listExtraAlojIt.getIdExtraAlojamiento(),listExtraAlojIt.getTipoMenu(), listExtraAlojIt.getCosto(), activo});
                    }
            }
        }    
    }
    
    //--------------------------------------------------------------------------
    
    private boolean guardarCambios(){
        
        alojamientoSeleccionado = (Alojamiento) jcbxAlojamiento.getSelectedItem();
        
        extraAlojamientoData = new ExtraAlojamientoData(conexion);
               
        boolean estado = false;
            
        try {

                if (jtxtTipoDeMenu.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Menu debe ser completado");
                    jtxtTipoDeMenu.requestFocus();
                    
                }else if(jtxtTipoDeMenu.getText().length() > 50){   
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Menu puede tener un maximo de 50 caracteres");
                    jtxtTipoDeMenu.requestFocus();
                    
                                }else if (jtxtCostoMenu.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "El campo Costo debe ser completado");
                                    jtxtCostoMenu.requestFocus();

                                    }else{    

                                        String tipoMenu = jtxtTipoDeMenu.getText(); 
                                        float costo = Float.parseFloat(jtxtCostoMenu.getText());  //<---Con este ParseFloat adentro del try, controlamos que sea solo un numero
                                        boolean activo = true;
                                        
                                        if (costo < 0){
                                            JOptionPane.showMessageDialog(null, "El Costo debe ser mayor a $0.00" );
                                        }else{  

                                            if (extraAlojamientoData.guardarExtraAlo(new ExtraAlojamiento(alojamientoSeleccionado,tipoMenu,costo, activo))) {
                                                limpiaJText();
                                                jCheckBActivo.setForeground(Color.black);
                                                jbtDesactivar.setText("Desactivar");
                                                jbtDesactivar.setForeground(Color.black);
                                                jcbxAlojamiento.setSelectedIndex(-1);
                                                estado = true;
                                            }
                                        }    
                                    }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo Costo solo acepta un precio");
                jtxtCostoMenu.requestFocus();
        }

        return estado;
}
    
    //--------------------------------------------------------------------------    
    private void limpiaJText(){
        jtxtTipoDeMenu.setText("");
        jtxtCostoMenu.setText("");
        jCheckBActivo.setSelected(false);

    
    }
    //--------------------------------------------------------------------------
        private void desactivarExtraAlojamieto(){
        
        
        if (extraAlojamientoData.obtenerExtraXId(idExtraAlojamientoSeleccionado).isActivo()){                                    //<---Si el extraAlojamiento esta Activo, llama al metodo desactivar (del Data)
            
            if (extraAlojamientoData.desactivarExtra(idExtraAlojamientoSeleccionado)){
                
                JOptionPane.showMessageDialog(null,"El Extra-Alojamiento fue Desactivado Satisfactorioamente!");
            }  
  
        }else{                                                                  //<---Si no, llama al metodo actualizar (de la vista)
                
                jCheckBActivo.setSelected(true);
                if (actualizarExtraAlojamiento(idExtraAlojamientoSeleccionado)){
                    JOptionPane.showMessageDialog(null,"El Extra-Alojamiento fue Activado Satisfactorioamente!");
                }
        }

        
        
        llenarCombo();
        borrarFilas();
        limpiaJText();
        jCheckBActivo.setForeground(Color.black);
        jbtDesactivar.setText("Desactivar");
        jbtDesactivar.setForeground(Color.black);
        jcbxAlojamiento.setSelectedIndex(-1);


    }
    //--------------------------------------------------------------------------  

    
       //--------------------------------------------------------------------------
    private boolean actualizarExtraAlojamiento( int idParam){
        
        boolean estado = false;
    
        
        try {

            
                if (jtxtTipoDeMenu.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Menu debe ser completado");
                    jtxtTipoDeMenu.requestFocus();
                    
                }else if(jtxtTipoDeMenu.getText().length() > 50){   
                    JOptionPane.showMessageDialog(null, "El campo Tipo de Menu puede tener un maximo de 50 caracteres");
                    jtxtTipoDeMenu.requestFocus();
                    
                                }else if (jtxtCostoMenu.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "El campo Costo debe ser completado");
                                    jtxtCostoMenu.requestFocus();

                                    }else{    

                                        String tipoMenu = jtxtTipoDeMenu.getText(); 
                                        float costo = Float.parseFloat(jtxtCostoMenu.getText());  //<---Con este ParseFloat adentro del try, controlamos que sea solo un numero
                                        boolean activo = jCheckBActivo.isSelected();
                                        
                                        

                                        if (costo < 0){
                                            JOptionPane.showMessageDialog(null, "El Costo debe ser mayor a $0.00" );
                                        }else{  

                                            if (extraAlojamientoData.actualizarExtra(new ExtraAlojamiento(idParam, alojamientoSeleccionado,tipoMenu,costo, activo))) {
                                                limpiaJText();
                                                jCheckBActivo.setForeground(Color.black);
                                                jbtDesactivar.setText("Desactivar");
                                                jbtDesactivar.setForeground(Color.black);
                                                jcbxAlojamiento.setSelectedIndex(-1);
                                                estado = true;
                                            }
                                        }    
                                    }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo Costo solo acepta un precio");
                jtxtCostoMenu.requestFocus();
        }

        return estado;
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtCancelar = new javax.swing.JButton();
        jbtGuardar = new javax.swing.JButton();
        jtxtTipoDeMenu = new javax.swing.JTextField();
        jlbTipoDeMenu = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbleExtraAlojamientosXAlojamiento = new javax.swing.JTable();
        jlbAlojamiento = new javax.swing.JLabel();
        jlbTituloExtraAlojamientos = new javax.swing.JLabel();
        jlbCosto = new javax.swing.JLabel();
        jtxtCostoMenu = new javax.swing.JTextField();
        jCheckBActivo = new javax.swing.JCheckBox();
        jbtActualizar = new javax.swing.JButton();
        jbtDesactivar = new javax.swing.JButton();
        jcbxAlojamiento = new javax.swing.JComboBox<>();

        setClosable(true);

        jbtCancelar.setText("Cancelar");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        jbtGuardar.setText("Guardar");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jlbTipoDeMenu.setText("Tipo de Menu:");

        jtbleExtraAlojamientosXAlojamiento.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbleExtraAlojamientosXAlojamiento.setToolTipText("doble click");
        jtbleExtraAlojamientosXAlojamiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbleExtraAlojamientosXAlojamientoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jtbleExtraAlojamientosXAlojamientoMouseExited(evt);
            }
        });
        jtbleExtraAlojamientosXAlojamiento.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jtbleExtraAlojamientosXAlojamientoPropertyChange(evt);
            }
        });
        jtbleExtraAlojamientosXAlojamiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtbleExtraAlojamientosXAlojamientoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtbleExtraAlojamientosXAlojamiento);
        if (jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumnCount() > 0) {
            jtbleExtraAlojamientosXAlojamiento.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        jlbAlojamiento.setText("Alojamiento :");

        jlbTituloExtraAlojamientos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jlbTituloExtraAlojamientos.setText("Extra - Alojamientos");

        jlbCosto.setText("Costo:            $");

        jCheckBActivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jCheckBActivo.setText("Activo");

        jbtActualizar.setText("Actualizar");
        jbtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtActualizarActionPerformed(evt);
            }
        });

        jbtDesactivar.setText("Desactivar");
        jbtDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtDesactivarActionPerformed(evt);
            }
        });

        jcbxAlojamiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbxAlojamientoMouseClicked(evt);
            }
        });
        jcbxAlojamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxAlojamientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jbtDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jbtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbtGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jCheckBActivo)
                            .addGap(198, 198, 198)
                            .addComponent(jbtCancelar))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jlbAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jcbxAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jlbTituloExtraAlojamientos)
                                .addGap(95, 95, 95)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jlbTipoDeMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jtxtTipoDeMenu)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlbCosto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtCostoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jlbTituloExtraAlojamientos)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbAlojamiento)
                    .addComponent(jcbxAlojamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTipoDeMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbTipoDeMenu))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCostoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbCosto))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBActivo)
                    .addComponent(jbtCancelar))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtGuardar)
                    .addComponent(jbtDesactivar)
                    .addComponent(jbtActualizar))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed

        limpiaJText();
       
        jbtGuardar.setEnabled(false);
        jbtActualizar.setEnabled(false);
        jbtDesactivar.setEnabled(false);
        borrarFilas();
        jcbxAlojamiento.requestFocus();
        jcbxAlojamiento.setSelectedIndex(-1);
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        if ( guardarCambios()){
            JOptionPane.showMessageDialog(null, "Extra-ALojamiento Agregar Exitosamente");
           actualizoExtraAlojameinto = false;
            cargarTablaConExtraAlojamientos();
            jtxtTipoDeMenu.setText("");
            jbtDesactivar.setEnabled(false);
  

        }
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jtbleExtraAlojamientosXAlojamientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbleExtraAlojamientosXAlojamientoMouseClicked

        if (evt.getClickCount() == 1){
        
            limpiaJText();
        
        }else if (evt.getClickCount() == 2){

            filaSeleccionada = jtbleExtraAlojamientosXAlojamiento.getSelectedRow() ;

            if ( filaSeleccionada != -1){

                idExtraAlojamientoSeleccionado = (Integer)jtbleExtraAlojamientosXAlojamiento.getValueAt(filaSeleccionada, 0);
                
                jtxtTipoDeMenu.setText((jtbleExtraAlojamientosXAlojamiento.getValueAt(filaSeleccionada, 1)+""));
                jtxtCostoMenu.setText((jtbleExtraAlojamientosXAlojamiento.getValueAt(filaSeleccionada, 2)+""));
 
                String tipoMenu = (jtbleExtraAlojamientosXAlojamiento.getValueAt(filaSeleccionada, 3)+"");

                if(tipoMenu.equals("Activo")){                         
                   jCheckBActivo.setSelected(true);
                   jCheckBActivo.setForeground(Color.black);
                   jbtDesactivar.setText("Desactivar");
                   jbtDesactivar.setForeground(Color.black);
                   
                   
                }else if(tipoMenu.equals("No Activo")){
                    jCheckBActivo.setSelected (false);
                    jCheckBActivo.setForeground(Color.red);
                    jbtDesactivar.setText("Activar");
                    jbtDesactivar.setForeground(Color.red);
                }                   

                jtxtCostoMenu.requestFocus();
                jtxtCostoMenu.selectAll();
                jbtDesactivar.setEnabled(true);
                jbtActualizar.setEnabled(true);
                
                alojamientoSeleccionado = (Alojamiento)jcbxAlojamiento.getSelectedItem();
                idAlojamientoSeleccionado = alojamientoSeleccionado.getIdAlojamiento();

            }
        }
    }//GEN-LAST:event_jtbleExtraAlojamientosXAlojamientoMouseClicked

    private void jtbleExtraAlojamientosXAlojamientoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbleExtraAlojamientosXAlojamientoMouseExited

    }//GEN-LAST:event_jtbleExtraAlojamientosXAlojamientoMouseExited

    private void jtbleExtraAlojamientosXAlojamientoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtbleExtraAlojamientosXAlojamientoPropertyChange

    }//GEN-LAST:event_jtbleExtraAlojamientosXAlojamientoPropertyChange

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed
       
        if (actualizarExtraAlojamiento(idExtraAlojamientoSeleccionado)){
            JOptionPane.showMessageDialog(null,"El Extra-Alojamiento fue Actualizado Satisfactorioamente!");
       }     
    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void jbtDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDesactivarActionPerformed
        desactivarExtraAlojamieto();
    }//GEN-LAST:event_jbtDesactivarActionPerformed

    private void jcbxAlojamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxAlojamientoActionPerformed
        cargarTablaConExtraAlojamientos();
        
    }//GEN-LAST:event_jcbxAlojamientoActionPerformed

    private void jtbleExtraAlojamientosXAlojamientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbleExtraAlojamientosXAlojamientoKeyPressed
        limpiaJText();
    }//GEN-LAST:event_jtbleExtraAlojamientosXAlojamientoKeyPressed

    private void jcbxAlojamientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbxAlojamientoMouseClicked
        // TODO add your handling code here:
        jbtGuardar.setEnabled(true);
    }//GEN-LAST:event_jcbxAlojamientoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBActivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtDesactivar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JComboBox<Alojamiento> jcbxAlojamiento;
    private javax.swing.JLabel jlbAlojamiento;
    private javax.swing.JLabel jlbCosto;
    private javax.swing.JLabel jlbTipoDeMenu;
    private javax.swing.JLabel jlbTituloExtraAlojamientos;
    private javax.swing.JTable jtbleExtraAlojamientosXAlojamiento;
    private javax.swing.JTextField jtxtCostoMenu;
    private javax.swing.JTextField jtxtTipoDeMenu;
    // End of variables declaration//GEN-END:variables
}
