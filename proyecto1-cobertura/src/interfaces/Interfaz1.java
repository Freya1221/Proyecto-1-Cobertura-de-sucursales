/**
 * Interfaz1 es una clase que representa una interfaz gráfica de usuario
 * para cargar y procesar archivos JSON. Permite al usuario seleccionar un archivo
 * JSON, leer su contenido y establecer un valor T a través de un campo de texto.
 *
 * @author Sebastián Arriaga
 */

package interfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import proyecto1.cobertura.Grafo;
import proyecto1.cobertura.ListaArray.ListaArray;

/**
 *
 * @author sebas
 */
public class Interfaz1 extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz1
     */
    public Interfaz1() {

        initComponents();

        setSize(800, 600);
        setLocationRelativeTo(null);
        LabelT.setVisible(false);
        btnEstablecerT.setVisible(false);
        TextFieldT.setVisible(false);
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
        jLabel5 = new javax.swing.JLabel();
        TextArchivoCargado = new javax.swing.JLabel();
        LabelT = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

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
        getContentPane().add(btnCargarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        btnEstablecerT.setText("OK");
        btnEstablecerT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstablecerTActionPerformed(evt);
            }
        });
        getContentPane().add(btnEstablecerT, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 50, 20));

        TextFieldT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldTActionPerformed(evt);
            }
        });
        getContentPane().add(TextFieldT, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 50, -1));
        getContentPane().add(ConfirmarArchivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 180, 30));

        TextAreaNuevaLinea.setColumns(20);
        TextAreaNuevaLinea.setRows(5);
        jScrollPane2.setViewportView(TextAreaNuevaLinea);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, 280, 180));

        btnAgregarNuevaLinea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregarNuevaLinea.setText("Agregar Línea");
        getContentPane().add(btnAgregarNuevaLinea, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, 280, -1));

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 36)); // NOI18N
        jLabel5.setText("Cobertura de Sucursales");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 450, 80));

        TextArchivoCargado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        getContentPane().add(TextArchivoCargado, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 190, 30));

        LabelT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LabelT.setText("Valor T:");
        getContentPane().add(LabelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 70, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Agregar nueva línea:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 190, 30));

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
        JOptionPane.showMessageDialog(null, "Arhivo cargado con éxito");
        return contenidoJson.toString();
    }

    public void parsearJson(String contenidoJson) {
        // Limpiar el contenido para evitar errores de formato
        contenidoJson = contenidoJson.replaceAll("\\s+", ""); // Eliminar espacios en blanco
        contenidoJson = contenidoJson.replaceAll("\\n", ""); // Eliminar saltos de línea

        // Extraer el contenido de "Metro de Caracas"
        String[] lineas = contenidoJson.split("\\},\\{");
        String nombreSistema = contenidoJson.substring(contenidoJson.indexOf("{\"") + 2, contenidoJson.indexOf("\":[{"));

        // Limpiar el inicio y final para manejar bordes del JSON
        lineas[0] = lineas[0].replace("{\"" + nombreSistema + "\":[{", "");
        lineas[lineas.length - 1] = lineas[lineas.length - 1].replace("}]}", "");

        // Lista para almacenar los grafos de cada línea
        ListaArray grafos = new ListaArray(lineas.length);

        // Iterar sobre cada línea del metro
        for (String linea : lineas) {
            // Separar el nombre de la línea de sus estaciones
            String nombreLinea = linea.split(":\\[")[0].replaceAll("[\"{}]", ""); // Extraer nombre de la línea
            System.out.println("Procesando " + nombreLinea);

            String estacionesRaw = linea.split(":\\[")[1].replaceAll("\\]", ""); // Extraer las estaciones crudas
            String[] estaciones = estacionesRaw.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Manejar bien las comas dentro de objetos

            // Crear un nuevo grafo para la línea actual
            Grafo grafo = new Grafo();

            // Agregar estaciones al grafo
            for (String estacion : estaciones) {
                String nombreEstacion;

                // Verificar si es una estación con una conexión (objeto)
                if (estacion.contains("{")) {
                    // Estación con conexión, manejar el objeto manualmente
                    String[] estacion1 = estacion.replaceAll("[\"{}]", "").split(":");
                    nombreEstacion = estacion1[0];

                    // Agregar ambas estaciones y su conexión
                    System.out.println("Estación: " + nombreEstacion);
                    grafo.addEstacion(nombreLinea, nombreEstacion);
                } else {
                    // Estación normal
                    nombreEstacion = estacion.replaceAll("[\"{}]", "");
                    System.out.println("Estación: " + nombreEstacion);
                    grafo.addEstacion(nombreLinea, nombreEstacion);
                }
            }

            // Inicializar la matriz de adyacencia para la línea actual
            grafo.inicializarMatriz(grafo.getEstaciones().getSize());

            // Establecer conexiones entre estaciones consecutivas
            for (int j = 0; j < estaciones.length - 1; j++) {
                String estacion1 = estaciones[j].replaceAll("[\"{}]", "");
                String estacion2 = estaciones[j + 1].replaceAll("[\"{}]", "");

                if (!estacion1.contains(":") && !estacion2.contains(":")) {
                    // Conectar estaciones solo si no tienen conexiones especiales
                    grafo.agregarConexion(estacion1, estacion2);
                } else {
                    if (estacion1.contains(":") && estacion2.contains(":")) {
                        // Ambos tienen dos estaciones conectadas
                        String[] partes1 = estacion1.split(":");
                        String estacionLinea1A = partes1[0];  // Estación que pertenece a esta línea
                        String estacionLinea2A = partes1[1];  // Estación de la otra línea

                        String[] partes2 = estacion2.split(":");
                        String estacionLinea1B = partes2[0];
                        String estacionLinea2B = partes2[1];

                        // Conectar las estaciones de la misma línea
                        grafo.agregarConexion(estacionLinea1A, estacionLinea1B);
                    } else if (estacion1.contains(":")) {
                        // Solo la primera estación tiene una conexión especial
                        String[] partes1 = estacion1.split(":");
                        String estacionLinea1 = partes1[0];
                        String estacionLinea2 = partes1[1];

                        // Conectar la estación de esta línea con la siguiente estación
                        grafo.agregarConexion(estacionLinea1, estacion2);
                    } else {
                        // Solo la segunda estación tiene una conexión especial
                        String[] partes2 = estacion2.split(":");
                        String estacionLinea1 = partes2[0];
                        String estacionLinea2 = partes2[1];

                        // Conectar la primera estación con la estación de esta línea en la conexión especial
                        grafo.agregarConexion(estacion1, estacionLinea1);

                    }

                }
            }

            // Agregar el grafo de la línea a la lista de grafos
            grafos.insertFinal(grafo);
            
        }
        TextArchivoCargado.setText("Archivo cargado");
        establecerValorTSistema(nombreSistema);
        // Imprimir las matrices de adyacencia para cada línea
        for (int i = 0; i < grafos.getSize(); i++) {
            Grafo g = (Grafo) grafos.getArray()[i].getElement();
            g.printMatriz();
            System.out.println("");
        }
    }

// Método que establece el valor de t según el sistema de transporte cargado
    int t;

    public void establecerValorTSistema(String nombreSistema) {
        t = switch (nombreSistema.toLowerCase()) {
            case "metrodecaracas" ->
                3;
            case "transmilenio" ->
                10;
            default ->
                0;
        }; // Valor por defecto si no se reconoce el sistema

        // Mostrar el valor de 't' en el textField
        TextFieldT.setText(String.valueOf(t));

        // Hacer visibles los componentes después de cargar el sistema
        LabelT.setVisible(true);
        TextFieldT.setVisible(true);
        btnEstablecerT.setVisible(true);
    }


    private void btnEstablecerTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstablecerTActionPerformed
        try {
            int nuevoT = Integer.parseInt(TextFieldT.getText());
            t = nuevoT;  // Actualizar el valor de t
            System.out.println("Valor de t actualizado a: " + t);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un número entero válido.");
        }
    }//GEN-LAST:event_btnEstablecerTActionPerformed

    private void TextFieldTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldTActionPerformed

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
    private javax.swing.JLabel LabelT;
    private javax.swing.JLabel TextArchivoCargado;
    private javax.swing.JTextArea TextAreaNuevaLinea;
    private javax.swing.JTextField TextFieldT;
    private javax.swing.JButton btnAgregarNuevaLinea;
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnEstablecerT;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

}
