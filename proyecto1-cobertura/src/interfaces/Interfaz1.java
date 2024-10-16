/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author sebas
 */
public class Interfaz1 extends javax.swing.JFrame {
//    Auxiliar para mostrar el numero de archivos cargados

    /**
     * Creates new form Interfaz1
     */
    public Interfaz1() {
        initComponents();

        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        btnCargarArchivo = new javax.swing.JButton();
        btnEstablecerT = new javax.swing.JButton();
        TextFieldT = new javax.swing.JTextField();
        ConfirmarArchivos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaNuevaLinea = new javax.swing.JTextArea();
        btnAgregarNuevaLinea = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, -1, -1));

        btnCargarArchivo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCargarArchivo.setText("Cargar Archivo");
        btnCargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarArchivoActionPerformed(evt);
            }
        });
        getContentPane().add(btnCargarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, -1));

        btnEstablecerT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEstablecerT.setText("Aceptar");
        btnEstablecerT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstablecerTActionPerformed(evt);
            }
        });
        getContentPane().add(btnEstablecerT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, -1, -1));

        TextFieldT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldTActionPerformed(evt);
            }
        });
        getContentPane().add(TextFieldT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 130, 30));
        getContentPane().add(ConfirmarArchivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 180, 30));

        TextAreaNuevaLinea.setColumns(20);
        TextAreaNuevaLinea.setRows(5);
        jScrollPane2.setViewportView(TextAreaNuevaLinea);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, 280, 180));

        btnAgregarNuevaLinea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregarNuevaLinea.setText("Agregar Línea");
        getContentPane().add(btnAgregarNuevaLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, 280, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Establecer T:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, -1, -1));

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 36)); // NOI18N
        jLabel5.setText("Cobertura de Sucursales");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 450, 80));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Agregar nueva línea:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 190, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarArchivoActionPerformed
//        Con este boton busco cargar el archivo json para despues parsearlo y trabajar con el
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String filePath = archivoSeleccionado.getAbsolutePath();

            // Verificar si el archivo es .json
            if (filePath.endsWith(".json")) {
                try {
                    String contenidoJson = leerArchivoJson(filePath);
                    parsearJson(contenidoJson);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un archivo .json", "Archivo inválido", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCargarArchivoActionPerformed

    private String leerArchivoJson(String filePath) throws IOException {
//  Con este metodo busco transcribir el archivo json de forma tal que pueda utilizar su informacion
    StringBuilder contenidoJson = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
//            se agrega el salto de linea para mantener el formato original del archivo
            contenidoJson.append(linea).append("\n");
        }
    }
    return contenidoJson.toString();
}
    
    private void parsearJson(String contenidoJson) {
}
    
    private void btnEstablecerTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstablecerTActionPerformed
        // Establecer T:
        try {
            int T = Integer.parseInt(TextFieldT.getText());
        } catch (Exception e) {

        }


    }//GEN-LAST:event_btnEstablecerTActionPerformed

    private void TextFieldTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldTActionPerformed

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
            java.util.logging.Logger.getLogger(Interfaz1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ConfirmarArchivos;
    private javax.swing.JTextArea TextAreaNuevaLinea;
    private javax.swing.JTextField TextFieldT;
    private javax.swing.JButton btnAgregarNuevaLinea;
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnEstablecerT;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

}
