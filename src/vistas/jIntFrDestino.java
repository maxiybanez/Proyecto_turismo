package vistas;

import control.DestinoData;
import control.ConectarBD;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Destino;

public class jIntFrDestino extends javax.swing.JInternalFrame {

    //Atributos
    private Destino destinoSeleccioando;
    private DestinoData destinoData;
    ConectarBD conexion = new ConectarBD();

    //Constructor
    public jIntFrDestino() {
        initComponents();

        llenarCombo();
        
        jcbDestinos.setSelectedIndex(-1);
        jCheckBoxActivo.setEnabled(false);
        jbtDesactivar.setForeground(Color.gray);
        limpiarCampos();
    }

    // METODOS---//
    private boolean validarCamposdeSoloLetras(String cadenaParam) {

        return cadenaParam.matches("[a-z A-Z]{0,30}?[ ]?[a-z A-Z]{0,30}?[ ]?[a-z A-Z]{0,30}");                           //se valida letras minusculas o mayusculas y que el rango sea de 3 a 30 caracteres y solo un espacio
    }

    private void llenarCombo() {

        jcbDestinos.removeAllItems();

        DestinoData destinoData = new DestinoData(conexion);

        ArrayList<Destino> destinos = (ArrayList) destinoData.obtenerTodosLosDestinos();

        for (Destino desIt : destinos) {

            jcbDestinos.addItem(desIt);
        }

    }

    private void rellenarCampos() {

        destinoSeleccioando = (Destino) jcbDestinos.getSelectedItem();

        destinoData = new DestinoData(conexion);

        if (destinoSeleccioando != null) {

            jtxtNombre.setText(destinoSeleccioando.getNombre());
            jtxtPais.setText(destinoSeleccioando.getPais());

            if (destinoSeleccioando.isActivo()) {

                jCheckBoxActivo.setSelected(destinoSeleccioando.isActivo());

                jbtDesactivar.setText("Desactivar");
                jCheckBoxActivo.setForeground(Color.black);
                jbtDesactivar.setForeground(Color.black);

            } else {

                jCheckBoxActivo.setSelected(destinoSeleccioando.isActivo());

                jbtDesactivar.setText("Activar");
                jCheckBoxActivo.setForeground(Color.red);
                jbtDesactivar.setForeground(Color.red);

            }

            jbtGuardar.setEnabled(true);
            jbtActualizar.setEnabled(false);
            jbtDesactivar.setEnabled(false);

        }
    }

    private void limpiarCampos() {
        llenarCombo();
        jtxtNombre.setText("");
        jtxtPais.setText("");

        jCheckBoxActivo.setSelected(true);
        jcbDestinos.setSelectedIndex(-1);

        jtxtNombre.requestFocus();

        jbtGuardar.setEnabled(true);
        jbtActualizar.setEnabled(false);
        jbtDesactivar.setEnabled(false);

        jbtDesactivar.setText("Desactivar");
        jCheckBoxActivo.setForeground(Color.BLACK);
        jbtDesactivar.setForeground(Color.BLACK);

    }
    //*****************************************************//

    private boolean guardarDestino() {

        boolean estado = false;

        try {

            if (jtxtNombre.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El campo Nombre debe ser completado");
                jtxtNombre.requestFocus();

            } else if (!validarCamposdeSoloLetras(jtxtNombre.getText())) {
                JOptionPane.showMessageDialog(null, "El campo Nombre solo debe solo letras hasta 30 caracteres\n\nSolo se permiten hasta dos Nombres");
                jtxtNombre.requestFocus();

            } else if (jtxtPais.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El campo Pais debe ser completado");
                jtxtPais.requestFocus();

            } else if (!validarCamposdeSoloLetras(jtxtPais.getText())) {
                JOptionPane.showMessageDialog(null, "El campo Pais solo debe solo letras hasta 30 caracteres\n\nSolo se permiten hasta dos Apellidos");
                jtxtPais.requestFocus();

            } else {

                String nombre = jtxtNombre.getText();
                String pais = jtxtPais.getText();
                boolean activo = jCheckBoxActivo.isSelected();
                if (destinoData.guardarDestino(new Destino(nombre, pais, activo))) {
                    limpiarCampos();
                    estado = true;
                }

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "Revise los datos ingresados Por Favor !");

        }

        return estado;
    }

    private boolean actualizarDestino(int idDestinoParam) {
        boolean estado = false;

        try {

            if (jtxtNombre.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El campo Nombre debe ser completado");
                jtxtNombre.requestFocus();

            } else if (!validarCamposdeSoloLetras(jtxtNombre.getText())) {
                JOptionPane.showMessageDialog(null, "El campo Nombre solo debe tener letras hasta 30 caracteres\n\nSolo se permiten hasta dos Nombres");
                jtxtNombre.requestFocus();

            } else if (jtxtPais.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "El campo Pais debe ser completado");
                jtxtPais.requestFocus();

            } else if (!validarCamposdeSoloLetras(jtxtPais.getText())) {
                JOptionPane.showMessageDialog(null, "El campo Pais solo debe solo letras hasta 30 caracteres");
                jtxtPais.requestFocus();

            } else {

                String nombre = jtxtNombre.getText();
                String apellido = jtxtPais.getText();

                boolean activo = jCheckBoxActivo.isSelected();

                if (destinoData.actualizarDestino(new Destino(idDestinoParam, nombre, apellido, activo))) {
                    limpiarCampos();
                    estado = true;
                }

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "Actualizado");

        }

        return estado;
    }

    private void desactivarDestinoXId() {

        if (destinoSeleccioando.isActivo()) {                                   

            if (destinoData.desactivarDestinoXId(destinoSeleccioando.getIdDestino())) {

                JOptionPane.showMessageDialog(null, "El Destino fue desactivado Satisfactorioamente!");
            }

        } else {                                                                  

            jCheckBoxActivo.setSelected(true);
           actualizarDestino(destinoSeleccioando.getIdDestino());
        }

        llenarCombo();
        limpiarCampos();
        
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlbTituloDestino = new javax.swing.JLabel();
        jbtLimpiarCampos = new javax.swing.JButton();
        jcbDestinos = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jlbNombre = new javax.swing.JLabel();
        jCheckBoxActivo = new javax.swing.JCheckBox();
        jtxtPais = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jlbPais = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jlbDestino = new javax.swing.JLabel();
        jbtGuardar = new javax.swing.JButton();
        jbtActualizar = new javax.swing.JButton();
        jbtDesactivar = new javax.swing.JButton();

        setClosable(true);

        jlbTituloDestino.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jlbTituloDestino.setText("-DESTINO-");

        jbtLimpiarCampos.setText("Limpiar Campos");
        jbtLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLimpiarCamposActionPerformed(evt);
            }
        });

        jcbDestinos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbDestinosMouseClicked(evt);
            }
        });
        jcbDestinos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbDestinosActionPerformed(evt);
            }
        });

        jlbNombre.setText("Nombre:");

        jCheckBoxActivo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jCheckBoxActivo.setText("Activo");
        jCheckBoxActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxActivoActionPerformed(evt);
            }
        });

        jlbPais.setText("Pais");

        jlbDestino.setText("Destinos:");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbDestinos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator2)
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlbPais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlbNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtPais)
                            .addComponent(jtxtNombre)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jCheckBoxActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(jlbTituloDestino)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbTituloDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbDestinos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbDestino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbNombre)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbPais)
                    .addComponent(jtxtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxActivo)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLimpiarCamposActionPerformed
        limpiarCampos();
        jbtGuardar.setEnabled(true);
    }//GEN-LAST:event_jbtLimpiarCamposActionPerformed

    private void jcbDestinosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbDestinosActionPerformed
        rellenarCampos();
        jbtGuardar.setEnabled(false);
        jbtActualizar.setEnabled(true);
        jbtDesactivar.setEnabled(true);
    }//GEN-LAST:event_jcbDestinosActionPerformed

    private void jCheckBoxActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxActivoActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed

        if (guardarDestino()) {
            JOptionPane.showMessageDialog(null, "Se agrego un nuevo Destino");
            rellenarCampos();
            limpiarCampos();
        }

    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed

        if (actualizarDestino(destinoSeleccioando.getIdDestino())) {
            JOptionPane.showMessageDialog(null, "Se Actualizo Destino!");
        }

        llenarCombo();
        limpiarCampos();
    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void jbtDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDesactivarActionPerformed
        desactivarDestinoXId();
    }//GEN-LAST:event_jbtDesactivarActionPerformed

    private void jcbDestinosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbDestinosMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jcbDestinosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxActivo;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtDesactivar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JButton jbtLimpiarCampos;
    private javax.swing.JComboBox<Destino> jcbDestinos;
    private javax.swing.JLabel jlbDestino;
    private javax.swing.JLabel jlbNombre;
    private javax.swing.JLabel jlbPais;
    private javax.swing.JLabel jlbTituloDestino;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtPais;
    // End of variables declaration//GEN-END:variables
}
