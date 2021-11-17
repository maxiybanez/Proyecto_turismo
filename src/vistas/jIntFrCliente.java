
package vistas;

import control.ClienteData;
import control.ConectarBD;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;

public class jIntFrCliente extends javax.swing.JInternalFrame {

    //Atributos
    private Cliente clienteSeleccioando;
    private ClienteData clienteData;
    
    ConectarBD conexion = new ConectarBD();
    
    
    //Constructor
 
    public jIntFrCliente() {
        initComponents();

        llenarCombo();
        jcbClientes.setSelectedIndex(-1);
        jCheckBoxActivo.setEnabled(false);
        jbtDesactivar.setForeground(Color.gray);
        limpiarCampos();

    }

    //******************************* METODOS **********************************
    //--------------------------------------------------------------------------  
    private boolean validarCamposdeSoloLetras (String cadenaParam){
    
        return cadenaParam.matches("[a-zA-Z]{1,15}?[ ]?[a-zA-Z]{3,15}");                           //<---Validamos con esta expresion regular que solo senas letras minusculas o mayusculas y que el rango sea de 3 a 30 caracteres y solo un espacio
    }    
    
    
    //--------------------------------------------------------------------------    
    private void llenarCombo(){

        jcbClientes.removeAllItems();
        
        ClienteData clienteData = new ClienteData(conexion);
        
        ArrayList <Cliente> clientes = (ArrayList) clienteData.listarClientes();
        
        for(Cliente cliIt: clientes){
        
            jcbClientes.addItem(cliIt);
        }
  
    }    
    //--------------------------------------------------------------------------  
    private void rellenarCampos (){
    
        clienteSeleccioando = (Cliente) jcbClientes.getSelectedItem(); 
    
        clienteData = new ClienteData(conexion);
    
         if(clienteSeleccioando != null ){
        
            jtxtNombre.setText(clienteSeleccioando.getNombre());
            jtxtApellido.setText(clienteSeleccioando.getApellido());
            jtxtDni.setText(clienteSeleccioando.getDni() + "");
            
            
            if (clienteSeleccioando.isActivo()){
                
                jCheckBoxActivo.setSelected(clienteSeleccioando.isActivo());
           
                jbtDesactivar.setText("Desactivar");
                jCheckBoxActivo.setForeground(Color.black);
                jbtDesactivar.setForeground(Color.black);
                
            }else{
                
                jCheckBoxActivo.setSelected(clienteSeleccioando.isActivo());
                       
                jbtDesactivar.setText("Activar");
                jCheckBoxActivo.setForeground(Color.red);
                jbtDesactivar.setForeground(Color.red);
               
            }
            
                        
            
            jbtGuardar.setEnabled(true);
            jbtActualizar.setEnabled(false);
            jbtDesactivar.setEnabled(false);
            
         }   
    }

    //--------------------------------------------------------------------------
    private void limpiarCampos(){
            llenarCombo();
            jtxtNombre.setText("");
            jtxtApellido.setText("");
            jtxtDni.setText("");
            
            jCheckBoxActivo.setSelected(true);
            jcbClientes.setSelectedIndex(-1);
            
            jtxtNombre.requestFocus();
            
            jbtGuardar.setEnabled(true);
            jbtActualizar.setEnabled(false);
            jbtDesactivar.setEnabled(false);
         
            jbtDesactivar.setText("Desactivar");
            jCheckBoxActivo.setForeground(Color.BLACK);
            jbtDesactivar.setForeground(Color.BLACK);
    
    }    
    
    //--------------------------------------------------------------------------
    private boolean guardarCliente(){
        
        boolean estado = false;
        long minimoDni = 1000000;
        long maximoDni = 99000000;
        
        try {

            
                if (jtxtNombre.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Nombre debe ser completado");
                    jtxtNombre.requestFocus();
                
                }else if(!validarCamposdeSoloLetras(jtxtNombre.getText())){   
                    JOptionPane.showMessageDialog(null, "El campo Nombre solo debe solo letras hasta 30 caracteres\n\nSolo se permiten hasta dos Nombres");
                    jtxtNombre.requestFocus();
                    
                    }else if (jtxtApellido.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Apellido debe ser completado");
                    jtxtApellido.requestFocus();
                    

                            }else if(!validarCamposdeSoloLetras(jtxtApellido.getText())){   
                                JOptionPane.showMessageDialog(null, "El campo Apellido solo debe solo letras hasta 30 caracteres\n\nSolo se permiten hasta dos Apellidos");
                                jtxtApellido.requestFocus();

                                    }else if (jtxtDni.getText().equals("")) {
                                        JOptionPane.showMessageDialog(null, "El campo D.N.I debe ser completado");
                                        jtxtDni.requestFocus();

                                        }else{    

                                            String nombre = jtxtNombre.getText(); 
                                            String apellido = jtxtApellido.getText(); 
                                            long dni = Long.parseLong(jtxtDni.getText());  //<---Con este ParseLong adentro del try, controlamos que sea solo un numero
                                            

                                            if (dni < minimoDni || dni > maximoDni){
                                                JOptionPane.showMessageDialog(null, "El D.N.I. Debe estar en un rango entre " 
                                                                                   + "1.000.000 y " 
                                                                                   + "99.000.000");
                                            }else{  

                                                if (clienteData.guardarCliente( new Cliente(nombre, apellido, dni, true))) {
                                                    limpiarCampos();
                                                    estado=true;
                                                }

                                            }    
                                        }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo D.N.I solo acepta numeros");
                jtxtDni.requestFocus();
        }
        
        return estado;
}
    
    //--------------------------------------------------------------------------
    private boolean actualizarCliente( int idClienteParam){
        boolean estado = false;
        long minimoDni = 1000000;
        long maximoDni = 99000000;
        
        try {

            
                if (jtxtNombre.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Nombre debe ser completado");
                    jtxtNombre.requestFocus();
                    
                }else if(!validarCamposdeSoloLetras(jtxtNombre.getText())){   
                    JOptionPane.showMessageDialog(null, "El campo Nombre solo debe solo letras hasta 30 caracteres\n\nSolo se permiten hasta dos Nombres");
                    jtxtNombre.requestFocus();
                    
                    }else if (jtxtApellido.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "El campo Apellido debe ser completado");
                    jtxtApellido.requestFocus();
                    
                            }else if(!validarCamposdeSoloLetras(jtxtApellido.getText())){   
                                JOptionPane.showMessageDialog(null, "El campo Apellido solo debe solo letras hasta 30 caracteres\n\nSolo se permiten hasta dos Apellidos");
                                jtxtApellido.requestFocus();


                                }else if (jtxtDni.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "El campo D.N.I debe ser completado");
                                    jtxtDni.requestFocus();

                                    }else{    

                                        String nombre = jtxtNombre.getText(); 
                                        String apellido = jtxtApellido.getText(); 
                                        long dni = Long.parseLong(jtxtDni.getText());  //<---Con este ParseLong adentro del try, controlamos que sea solo un numero
                                        boolean activo = jCheckBoxActivo.isSelected();

                                        if (dni < minimoDni || dni > maximoDni){
                                            JOptionPane.showMessageDialog(null, "El D.N.I. Debe estar en un rango entre " 
                                                                               + "1.000.000 y " 
                                                                               + "99.000.000");
                                        }else{  

                                            if (clienteData.actualizarCliente(new Cliente(idClienteParam, nombre, apellido, dni, activo))) {
                                                limpiarCampos();
                                                estado = true;
                                            }

                                        }    
                                    }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo D.N.I solo acepta numeros");
                jtxtDni.requestFocus();
        }

        return estado;
}
    
//------------------------------------------------------------------------------

    private void desactivarCliente(){
        
        
        if (clienteSeleccioando.isActivo()){                                    //<---Si el cliente esta Activo, llama al metodo desactivarCliente (de cLienteData)
            
            if (clienteData.desactivarCliente(clienteSeleccioando.getIdCliente())){
                
                JOptionPane.showMessageDialog(null,"El Cliente fue desactivado Satisfactorioamente!");
            }  
  
        }else{                                                                  //<---Si no, llama al metodo actualizarCliente (de la vista)
                
                jCheckBoxActivo.setSelected(true);
                actualizarCliente(clienteSeleccioando.getIdCliente());
        }

        
        
        llenarCombo();
        limpiarCampos();
        


    }

   //---------------------------------------------------------------------------     
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlbTituloClientes = new javax.swing.JLabel();
        jcbClientes = new javax.swing.JComboBox<>();
        jlbNombre = new javax.swing.JLabel();
        jtxtApellido = new javax.swing.JTextField();
        jlbApellido = new javax.swing.JLabel();
        jtxtDni = new javax.swing.JTextField();
        jlbDni = new javax.swing.JLabel();
        jlbClientes = new javax.swing.JLabel();
        jbtGuardar = new javax.swing.JButton();
        jbtActualizar = new javax.swing.JButton();
        jbtDesactivar = new javax.swing.JButton();
        jbtLimpiarCampos = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBoxActivo = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        jtxtNombre = new javax.swing.JTextField();

        setClosable(true);

        jlbTituloClientes.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jlbTituloClientes.setText("- Clientes -");

        jcbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesActionPerformed(evt);
            }
        });

        jlbNombre.setText("Nombre:");

        jlbApellido.setText("Apellido");

        jtxtDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDniActionPerformed(evt);
            }
        });

        jlbDni.setText("D.N.I :");

        jlbClientes.setText("Clientes:");

        jbtGuardar.setText("Guardar");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

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

        jbtLimpiarCampos.setText("Limpiar Campos");
        jbtLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLimpiarCamposActionPerformed(evt);
            }
        });

        jCheckBoxActivo.setText("Activo");
        jCheckBoxActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlbApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlbNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jlbDni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtApellido)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBoxActivo)
                                .addGap(42, 42, 42))
                            .addComponent(jtxtNombre)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jbtGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbtLimpiarCampos, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlbTituloClientes)
                .addGap(125, 125, 125))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jlbTituloClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbClientes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbNombre)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbApellido)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbDni)
                    .addComponent(jCheckBoxActivo))
                .addGap(35, 35, 35)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtGuardar)
                    .addComponent(jbtActualizar))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtLimpiarCampos)
                    .addComponent(jbtDesactivar))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDniActionPerformed

    }//GEN-LAST:event_jtxtDniActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
         
        if (guardarCliente()){
              JOptionPane.showMessageDialog(null, "Cliente registrado Satisfactoriamente!");
              rellenarCampos();
              limpiarCampos();
        }      
        
  
        
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jCheckBoxActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxActivoActionPerformed

    private void jcbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesActionPerformed
        rellenarCampos();
        jbtGuardar.setEnabled(false);
        jbtActualizar.setEnabled(true);
        jbtDesactivar.setEnabled(true);
    }//GEN-LAST:event_jcbClientesActionPerformed

    private void jbtLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLimpiarCamposActionPerformed
        limpiarCampos();
  
    }//GEN-LAST:event_jbtLimpiarCamposActionPerformed

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed
    
        
        if (actualizarCliente(clienteSeleccioando.getIdCliente())){
              JOptionPane.showMessageDialog(null, "Cliente actualizado Satisfactoriamente!");
        }
        
        llenarCombo();
        limpiarCampos();
    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void jbtDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDesactivarActionPerformed
        desactivarCliente();
    }//GEN-LAST:event_jbtDesactivarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxActivo;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtDesactivar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JButton jbtLimpiarCampos;
    private javax.swing.JComboBox<Cliente> jcbClientes;
    private javax.swing.JLabel jlbApellido;
    private javax.swing.JLabel jlbClientes;
    private javax.swing.JLabel jlbDni;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbTituloClientes;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtDni;
    private javax.swing.JTextField jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
