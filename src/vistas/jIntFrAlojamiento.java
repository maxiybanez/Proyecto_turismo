
package vistas;

import control.AlojamientoData;
import control.ConectarBD;
import control.DestinoData;
import java.awt.Color;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Alojamiento;
import modelo.Destino;


public class jIntFrAlojamiento extends javax.swing.JInternalFrame {

    //--------------------------------------------------------------------------------------------Atributos
    ConectarBD conexion = new ConectarBD();
    private Destino destino;
    private DestinoData destinoData;
    private AlojamientoData alojamientoData;
    private Alojamiento alojamiento;
    
    //--------------------------------------------------------------------------------------------Constructor
    public jIntFrAlojamiento() {
        initComponents();
        destino = new Destino();
        destinoData = new DestinoData(conexion);
        alojamiento = new Alojamiento();
        alojamientoData = new AlojamientoData(conexion);
        llenarComboDestino();
        jcbAlojaCargados.setSelectedIndex(-1);
        jbBuscar.setEnabled(false);
    }
    
    //-------------------------------------------------------------------------------------------validar 
    private boolean validarCamposdeSoloLetras (String cadenaParam){
    
        return cadenaParam.matches("[a-zA-Z]{1,15}?[ ]?[a-zA-Z]{3,15}");                          
    }
    
    //-------------------------------------------------------------------------------------------llenar combo de destinos ya cargados
    private void llenarComboDestino(){

        jcbDestinos.removeAllItems();
        
        DestinoData destinoData = new DestinoData(conexion);
        
        ArrayList <Destino> destinos = (ArrayList) destinoData.obtenerTodosLosDestinos();
        
        for(Destino destinIT: destinos){
        
           jcbDestinos.addItem(destinIT);
        }
  
     } 
    //-------------------------------------------------------------------------------------------llenar combo de alojamiento cargados
     private void llenarComboAlojaCargados(){

        jcbAlojaCargados.removeAllItems();
                
        AlojamientoData data = new AlojamientoData(conexion);
        
        ArrayList <Alojamiento> alojaCargados = data.obtenerTodosLosAlojamientoXDestino(destino.getIdDestino());
        
        for(Alojamiento IT: alojaCargados){
        
           jcbAlojaCargados.addItem(IT);
        }
    } 
    //-------------------------------------------------------------------------------------------rellenar campos    
     private void rellenarCampos (){
    
         if(alojamiento != null ){
            jtTipo.setText((alojamiento.getTipoAlojamiento()));
            jtCosto.setText(valueOf(alojamiento.getCosto()));
            jtNombre.setText(alojamiento.getNombre());
            
            if (alojamiento.isActivo()){
                jCheckBoxActivo.setSelected(alojamiento.isActivo());
                jbtDesactivar.setText("Desactivar");
                jCheckBoxActivo.setForeground(Color.black);
                jbtDesactivar.setForeground(Color.black);
            }else{
                jCheckBoxActivo.setSelected(alojamiento.isActivo());   
                jbtDesactivar.setText("Activar");
                jCheckBoxActivo.setForeground(Color.red);
                jbtDesactivar.setForeground(Color.red);
            }
            jbtGuardar.setEnabled(true);
            jbtActualizar.setEnabled(false);
            jbtDesactivar.setEnabled(false);
            
         }else{
            JOptionPane.showMessageDialog(null, "El debe seleccionar un alojamiento");
        }   
    }

    //-------------------------------------------------------------------------------------------limpiar campos
         private void limpiarCampos(){
            
            jtNombre.setText("");
            jtCosto.setText("");
            jtTipo.setText("");
            
            jCheckBoxActivo.setSelected(false);
            jcbDestinos.setSelectedIndex(0);
            jcbAlojaCargados.setSelectedIndex(-1);
            jcbDestinos.requestFocus();
            
            jbtGuardar.setEnabled(true);
            jbtActualizar.setEnabled(false);
            jbtDesactivar.setEnabled(false);
            
         
            jbtDesactivar.setText("Desactivar");
            jCheckBoxActivo.setForeground(Color.BLACK);
            jbtDesactivar.setForeground(Color.BLACK);
    
    } 
   
         //-------------------------------------------------------------------------------------------Actualizar alojamiento
    private boolean actualizarAlojamiento( int idClienteParam){
       boolean estado = false;
     
        try {

                if (jcbDestinos.getSelectedIndex()== (-1) ) {
                    JOptionPane.showMessageDialog(null, "El debe seleccionar un destino");
                    jcbDestinos.requestFocus();
                }else if(jcbAlojaCargados.getSelectedIndex()== (-1)){
                    JOptionPane.showMessageDialog(null, "El debe seleccionar un Alojamiento");
                    jcbAlojaCargados.requestFocus();
                    }else if( jtTipo.getText().equals(" ")){   
                          JOptionPane.showMessageDialog(null, "Debe ingresar un tipo de alojamiento, ejemplo( hotel, departamento, etc.");
                          jtTipo.requestFocus();
                         }else if (!validarCamposdeSoloLetras(jtTipo.getText())){
                                JOptionPane.showMessageDialog(null, "Debe ingresar el tipo solo letras");
                                jtTipo.requestFocus();
                    
                            }else if(jtNombre.getText().equals(" ")){   
                                JOptionPane.showMessageDialog(null, "Debe ingresar un nombre, ejemplo( hotel-mar del plata)");
                                jtNombre.requestFocus();

                                    }else if (!validarCamposdeSoloLetras(jtTipo.getText())) {
                                        JOptionPane.showMessageDialog(null, "Debe ingresar el nombre solo letras");
                                        jtNombre.requestFocus();

                                        }else{    
                                            Alojamiento nuevoAloja= new Alojamiento();
                                            nuevoAloja.setDestino(destino);
                                            nuevoAloja.setNombre(jtNombre.getText());
                                            nuevoAloja.setTipoAlojamiento(jtTipo.getText());
                                            nuevoAloja.setActivo(jCheckBoxActivo.isSelected());
                                            float costo = Float.parseFloat(jtCosto.getText()); 
                                            nuevoAloja.setCosto(costo);
                                            
                                            if (alojamientoData.actualizarAlojamiento(nuevoAloja)) {
                                                
                                                    estado=true;
                                            }   
                                        }    

        } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(this, "El campo Costo solo acepta numeros");
                jtCosto.requestFocus();
        }
        
        return estado;

}
//-------------------------------------------------------------------------------------------desactivar alojamientos    
    private void desactivarAlojamiento(){
        
        
        if (alojamiento.isActivo()){                                                
            if (alojamientoData.desactivarAlojamiento(alojamiento.getIdAlojamiento())){
                
                JOptionPane.showMessageDialog(null,"El alojamiento fue desactivado Satisfactorioamente");
            }  
  
        }else{                                                                  //<---Si no, llama al metodo actualizarCliente (de la vista)
              
                jCheckBoxActivo.setSelected(true);
                actualizarAlojamiento(alojamiento.getIdAlojamiento());
        }
      
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtActualizar = new javax.swing.JButton();
        jbtDesactivar = new javax.swing.JButton();
        jbtLimpiarCampos = new javax.swing.JButton();
        jlbTituloClientes = new javax.swing.JLabel();
        jcbDestinos = new javax.swing.JComboBox<>();
        jlbClientes = new javax.swing.JLabel();
        jbtGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jlbApellido = new javax.swing.JLabel();
        jtCosto = new javax.swing.JTextField();
        jCheckBoxActivo = new javax.swing.JCheckBox();
        jlbNombre1 = new javax.swing.JLabel();
        jtTipo = new javax.swing.JTextField();
        jlbNombre2 = new javax.swing.JLabel();
        jtNombre = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jcbAlojaCargados = new javax.swing.JComboBox<>();
        jlbClientes1 = new javax.swing.JLabel();

        setClosable(true);

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

        jlbTituloClientes.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jlbTituloClientes.setText("-Alojamiento-");

        jcbDestinos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbDestinosActionPerformed(evt);
            }
        });

        jlbClientes.setText("Destinos");

        jbtGuardar.setText("Guardar");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jlbApellido.setText("Costo:");

        jtCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtCostoFocusLost(evt);
            }
        });

        jCheckBoxActivo.setText("Activo");
        jCheckBoxActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActivoActionPerformed(evt);
            }
        });

        jlbNombre1.setText("tipo:");

        jlbNombre2.setText("nombre:");

        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtTipo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtNombre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbApellido)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jCheckBoxActivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                                .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jtCosto)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbNombre1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbNombre2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxActivo)
                    .addComponent(jbBuscar))
                .addGap(58, 58, 58))
        );

        jcbAlojaCargados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAlojaCargadosActionPerformed(evt);
            }
        });

        jlbClientes1.setText("Alojamientos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jbtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(jbtGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jlbClientes1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbAlojaCargados, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jbtDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(jbtLimpiarCampos, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jlbClientes)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlbTituloClientes)
                                .addComponent(jcbDestinos, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbTituloClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDestinos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbClientes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbAlojaCargados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbClientes1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtGuardar)
                    .addComponent(jbtActualizar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtLimpiarCampos)
                    .addComponent(jbtDesactivar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed
     
      alojamiento = (Alojamiento) jcbAlojaCargados.getSelectedItem();
      
      if (actualizarAlojamiento(alojamiento.getIdAlojamiento())){
            JOptionPane.showMessageDialog(null, "Alojamiento actualizado Satisfactoriamente");
        }

        llenarComboAlojaCargados();
        llenarComboDestino();
       
    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void jbtDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDesactivarActionPerformed
        desactivarAlojamiento();
        limpiarCampos();
    }//GEN-LAST:event_jbtDesactivarActionPerformed

    private void jbtLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLimpiarCamposActionPerformed
        
        
        limpiarCampos();

    }//GEN-LAST:event_jbtLimpiarCamposActionPerformed

    private void jcbDestinosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbDestinosActionPerformed
        destino = (Destino) jcbDestinos.getSelectedItem();
        llenarComboAlojaCargados();
        
        jbtGuardar.setEnabled(true);
        jbtActualizar.setEnabled(false);
        jbtDesactivar.setEnabled(false);
    }//GEN-LAST:event_jcbDestinosActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed

         if (jcbDestinos.getSelectedIndex()== (-1) ) {
                    JOptionPane.showMessageDialog(null, "El debe seleccionar un destino");
                    jcbDestinos.requestFocus();
                
                   }else if( jtTipo.getText().equals(" ")){   
                          JOptionPane.showMessageDialog(null, "Debe ingresar un tipo de alojamiento, ejemplo( hotel, departamento, etc.");
                          jtTipo.requestFocus();
                         }else if (!validarCamposdeSoloLetras(jtTipo.getText())){
                                JOptionPane.showMessageDialog(null, "Debe ingresar el tipo solo letras");
                                jtTipo.requestFocus();
                    
                            }else if(jtNombre.getText().equals(" ")){   
                                JOptionPane.showMessageDialog(null, "Debe ingresar un nombre, ejemplo( hotel-mar del plata)");
                                jtNombre.requestFocus();

                                    }else if (!validarCamposdeSoloLetras(jtNombre.getText())) {
                                        JOptionPane.showMessageDialog(null, "Debe ingresar el nombre solo letras");
                                        jtNombre.requestFocus();

                                  }else{    
                                            Alojamiento nuevoAloja= new Alojamiento();
                                            nuevoAloja.setDestino(destino);
                                            nuevoAloja.setNombre(jtNombre.getText());
                                            nuevoAloja.setTipoAlojamiento(jtTipo.getText());
                                            nuevoAloja.setActivo(true);
                                            float costo = Float.parseFloat(jtCosto.getText()); 
                                            nuevoAloja.setCosto(costo);
                                            if (alojamientoData.guardarAlojamiento(nuevoAloja)) {
                                                 JOptionPane.showMessageDialog(null, "Alojamiento Cargado "); 
                                                 limpiarCampos();
                                            }else{
                                                 JOptionPane.showMessageDialog(null, "Alojamiento no cargado");  
                                            }
                                        } 
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jcbAlojaCargadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAlojaCargadosActionPerformed
        // TODO add your handling code here:
        alojamiento = (Alojamiento) jcbAlojaCargados.getSelectedItem(); 
        jbtGuardar.setEnabled(false);
        jbBuscar.setEnabled(true);
        jbtActualizar.setEnabled(true);
        jbtDesactivar.setEnabled(true);
        
    }//GEN-LAST:event_jcbAlojaCargadosActionPerformed

    private void jCheckBoxActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxActivoActionPerformed

    private void jtCostoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtCostoFocusLost
        // TODO add your handling code here:
         try {
             float costo = Float.parseFloat(jtCosto.getText()); 
         } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El campo Costo solo acepta numeros");
                jtCosto.requestFocus();
        }
        
    }//GEN-LAST:event_jtCostoFocusLost

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        // TODO add your handling code here:
        rellenarCampos();
        jbtActualizar.setEnabled(true);
        jbtDesactivar.setEnabled(true);
    }//GEN-LAST:event_jbBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxActivo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtDesactivar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JButton jbtLimpiarCampos;
    private javax.swing.JComboBox<Alojamiento> jcbAlojaCargados;
    private javax.swing.JComboBox<Destino> jcbDestinos;
    private javax.swing.JLabel jlbApellido;
    private javax.swing.JLabel jlbClientes;
    private javax.swing.JLabel jlbClientes1;
    private javax.swing.JLabel jlbNombre1;
    private javax.swing.JLabel jlbNombre2;
    private javax.swing.JLabel jlbTituloClientes;
    private javax.swing.JTextField jtCosto;
    private javax.swing.JTextField jtNombre;
    private javax.swing.JTextField jtTipo;
    // End of variables declaration//GEN-END:variables
}
