/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto.pkg1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author FREYA
 */
public class Cargar extends javax.swing.JFrame {

    static Grafo grafo;

    /**
     * Creates new form JFrame
     */
    public Cargar(Grafo grafo) {
        this.grafo = grafo;
        initComponents();
                this.setVisible(true);

    }

    public static String buscarArchivoJson() {
        // Crear un JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Establecer el filtro para mostrar solo archivos JSON
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos JSON", "json"));

        // Mostrar el cuadro de diálogo para seleccionar el archivo
        int returnValue = fileChooser.showOpenDialog(null);

        // Verificar si se seleccionó un archivo
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath(); // Devolver la ruta absoluta del archivo
        }

        return null; // Si no se seleccionó ningún archivo
    }

    public Grafo leerArchivo(String ruta) {
        String filePath = ruta;
        String jsonContent = readJsonFile(filePath);
        if (jsonContent != null) {
            JsonElement jsonElement = JsonParser.parseString(jsonContent);
            return printLineasYEstaciones(jsonElement);
        }
        return null;
    }

    public static String readJsonFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            return jsonContent.toString();
        } catch (IOException e) {
            System.out.println("Error leyendo archivo JSON: " + e.getMessage()); // 
            return null;
        }
    }

    public static Grafo printLineasYEstaciones(JsonElement jsonElement) {
        Grafo grafo = new Grafo();
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray lineas = jsonObject.getAsJsonArray("Metro de Caracas");

            for (JsonElement lineaElement : lineas) {
                JsonObject linea = lineaElement.getAsJsonObject();
                for (String nombreLinea : linea.keySet()) {
                    JsonArray estaciones = linea.getAsJsonArray(nombreLinea);
                    String[] e = new String[estaciones.size()];
                    int i = 0;
                    for (JsonElement estacionElement : estaciones) {
                        if (estacionElement.isJsonObject()) {
                            // Si es un objeto, imprimimos ambos nombres
                            JsonObject estacionObj = estacionElement.getAsJsonObject(); // Convertimos el elemento a objeto
                            for (String estacionNombre : estacionObj.keySet()) {
                                // Imprimimos el nombre de ambas estaciones
                                e[i] = estacionNombre + "/" + estacionObj.get(estacionNombre).getAsString();
                            }
                        } else {
                            String nombreEstacion = estacionElement.getAsString();
                            e[i] = nombreEstacion;
                        }
                        i++;
                    }
                    for (String nombre : e) {
                        if (!nombre.contains("/")) {
                            Nodo v = grafo.buscarEstacion(nombre);
                            if (v == null) {
                                grafo.agregarEstacion(nombre);
                            }
                        } else {
                            String[] n = nombre.split("/");
                            boolean found = false;
                            for (int j = 0; j < grafo.max; j++) {
                                if (grafo.estaciones[j].nombre.trim().contains("/")) {
                                }
                                if (grafo.estaciones[j].nombre.trim().contains(n[0].trim()) && grafo.estaciones[j].nombre.trim().contains(n[1].trim())) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                grafo.agregarEstacion(nombre);
                            }
                        }
                    }
                    int j = 0;
                    for (int k = 1; k < e.length; k++) {
                        grafo.agregarRelacion(e[j], e[k]);
                        j++;
                    }

                }
            }
        } else {
            // Mensaje de error si el contenido no es un objeto JSON válido
            System.out.println("El contenido no es un objeto JSON válido.");
        }
        return grafo;
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
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 36)); // NOI18N
        jLabel1.setText("Cargar Archivo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jButton1.setText("X");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 10, -1, -1));

        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jButton2.setText("Cargar Archivo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        VentanaInicio v1 = new VentanaInicio(grafo);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.grafo = this.leerArchivo(this.buscarArchivoJson());
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Cargar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cargar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cargar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cargar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cargar(grafo).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
