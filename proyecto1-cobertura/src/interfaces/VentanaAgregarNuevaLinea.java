/*
 * Con esta interfaz se puede agregar una nueva 
 * Línea al sistema
 */
package interfaces;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyecto1.cobertura.util.Estacion;
import proyecto1.cobertura.Grafo;
import proyecto1.cobertura.util.Lista;
import proyecto1.cobertura.util.ListaArray;

/**
 * Constructor que toma la lista de grafos y la los nombres de líneas
 * Obtenidos en la interfaz1
 * 
 * @author sebas
 */
public class VentanaAgregarNuevaLinea extends javax.swing.JFrame {

//    Modificar ListaArray
    private ListaArray grafos;
    private Lista nombresLineas;

    /**
     * Tomamos la lista de todos los grafos y tambien del 
     * nombre de las estaciones
     *
     * @param grafos
     * @param nombresLineas
     */
    public VentanaAgregarNuevaLinea(ListaArray grafos, Lista nombresLineas) {
        this.grafos = grafos; 
        this.nombresLineas = nombresLineas;      

        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        LabelNombreDeLaLínea = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LineaTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        EstacionesTextArea = new javax.swing.JTextArea();
        ConexionTextField = new javax.swing.JTextField();
        agregarLineaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("(Opcional)");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 160, 70));

        LabelNombreDeLaLínea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LabelNombreDeLaLínea.setText("Nombre de la línea: ");
        getContentPane().add(LabelNombreDeLaLínea, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 50));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("(separadas por comas)");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 190, 60));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Estaciones:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 100, 60));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Estación de conexión: ");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 200, 70));

        LineaTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LineaTextFieldActionPerformed(evt);
            }
        });
        getContentPane().add(LineaTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 260, 40));

        EstacionesTextArea.setColumns(20);
        EstacionesTextArea.setRows(5);
        jScrollPane1.setViewportView(EstacionesTextArea);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 280, 180));

        ConexionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConexionTextFieldActionPerformed(evt);
            }
        });
        getContentPane().add(ConexionTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 260, 40));

        agregarLineaButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        agregarLineaButton.setText("AGREGAR");
        agregarLineaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarLineaButtonActionPerformed(evt);
            }
        });
        getContentPane().add(agregarLineaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 530, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LineaTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LineaTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LineaTextFieldActionPerformed

    private void ConexionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConexionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConexionTextFieldActionPerformed

    private void agregarLineaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarLineaButtonActionPerformed

        String nombreLinea = LineaTextField.getText();
        String estaciones = EstacionesTextArea.getText();
        String conexion = ConexionTextField.getText();

        // Validación básica para que agreguen todos los datos
        if (nombreLinea.isEmpty() || estaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.");
            return;
        }

        // Crear un nuevo grafo para la nueva línea
        Grafo grafo = new Grafo();
        String[] listaEstaciones = estaciones.split(",");

        // Agregar las estaciones al grafo
        for (String estacion : listaEstaciones) {
            String nombreEstacion = estacion.trim(); 
            grafo.addEstacion(nombreLinea, nombreEstacion);  // Agregar cada estación
            System.out.println("Estación agregada: " + nombreEstacion);
        }

        // Inicializar la matriz de adyacencia para la nueva línea
        grafo.inicializarMatriz(grafo.getEstaciones().getSize());

        // Establecer conexiones 
        for (int j = 0; j < listaEstaciones.length - 1; j++) {
            String estacion1 = listaEstaciones[j].trim();  // Primera estación
            String estacion2 = listaEstaciones[j + 1].trim();  // Estación siguiente
            grafo.agregarConexion(estacion1, estacion2);  // Conectar ambas estaciones
            System.out.println("Conexión agregada entre " + estacion1 + " y " + estacion2);
        }

        // Agregar el grafo de la línea a la lista de grafos
        System.out.println("Intentando agregar el nuevo grafo...");
        if (grafos != null) {
            grafos.insertFinal(grafo);
            System.out.println("Nuevo grafo agregado correctamente. Total de grafos: " + grafos.getSize());
        } else {
            System.out.println("Error: La lista de grafos es nula.");
        }

        // Agregar el nombre de la línea a la lista de nombres de líneas
        nombresLineas.insertFinal(new Estacion(nombreLinea));

        // Mostrar los datos ingresados
        System.out.println("Línea agregada: " + nombreLinea);
        System.out.println("Estaciones: " + estaciones);

    }//GEN-LAST:event_agregarLineaButtonActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaAgregarNuevaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaAgregarNuevaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaAgregarNuevaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaAgregarNuevaLinea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ConexionTextField;
    private javax.swing.JTextArea EstacionesTextArea;
    private javax.swing.JLabel LabelNombreDeLaLínea;
    private javax.swing.JTextField LineaTextField;
    private javax.swing.JButton agregarLineaButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
