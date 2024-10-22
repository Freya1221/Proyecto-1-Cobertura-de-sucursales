/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package interfaces;

import javax.swing.*;
import java.awt.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import proyecto1.cobertura.Grafo;
import proyecto1.cobertura.Lista;
import proyecto1.cobertura.ListaArray.ListaArray; 
import proyecto1.cobertura.Nodo;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author sebas
 */
public class VisualizarGrafo extends JFrame {

    private Viewer viewerActual;
    private boolean mousePressed = false;
    private ListaArray listaGrafos;
    private JComboBox<String> selectorGrafos;
    private Lista lineas;
    private JScrollPane scrollPane;

    // Constructor que recibe la lista de grafos y el array de líneas del JSON
    public VisualizarGrafo(ListaArray listaGrafos, Lista lineas) {
        // Inicializar los componentes
        this.listaGrafos = listaGrafos;
        this.lineas = lineas;
        setTitle("Visualizador de Grafos");
        setSize(800, 600);  // Ajustamos el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes de la interfaz
        JPanel panelSeleccion = new JPanel();
        selectorGrafos = new JComboBox<>();
        JButton botonMostrar = new JButton("Mostrar Grafo");

        // Llenar el ComboBox con los nombres de las líneas del JSON
        llenarSelectorConLineas();

        // Añadir los componentes al panel
        panelSeleccion.add(selectorGrafos);
        panelSeleccion.add(botonMostrar);

        // Añadir escuchador al botón
        botonMostrar.addActionListener(e -> mostrarGrafoSeleccionado());

        // Configurar el layout y añadir el panel
        setLayout(new BorderLayout());
        add(panelSeleccion, BorderLayout.NORTH);

        // Mostrar la ventana principal
        setVisible(true);
    }

    // Método para llenar el ComboBox con los nombres de las líneas
    private void llenarSelectorConLineas() {
        Nodo pointer = lineas.getHead();
        while (pointer!= null){
            selectorGrafos.addItem(pointer.getEstacion().getSucursal());
            pointer = pointer.getNext();
        }
    }

    // Método para mostrar el grafo seleccionado
    private void mostrarGrafoSeleccionado() {
        String lineaSeleccionada = (String) selectorGrafos.getSelectedItem();

        if (lineaSeleccionada != null) {
            Grafo grafoSeleccionado = null;

            // Buscar el grafo correspondiente en la lista
            for (int i = 0; i < listaGrafos.getSize(); i++) {
                Grafo grafo = (Grafo) listaGrafos.getArray()[i].getElement();
                if (grafo.getEstaciones().getHead().getEstacion().getSucursal().equals(lineaSeleccionada)) {
                    grafoSeleccionado = grafo;
                    break;
                }
            }

            // Si se encuentra el grafo
            if (grafoSeleccionado != null) {
                Graph graphStream = grafoSeleccionado.toGraphStream();  // Convertir a GraphStream
                System.setProperty("org.graphstream.ui", "swing");  // Usar Swing

                // Cerrar el viewer anterior si existe
                if (viewerActual != null) {
                    viewerActual.close();  // Cerrar la vista previa
                }

                // Crear un nuevo viewer para el grafo
                viewerActual = graphStream.display(false);  // Modo "no threading"

                viewerActual.disableAutoLayout();  // Desactivar auto layout

                // Asignar posiciones manuales para los nodos en una línea recta
                asignarPosicionesLineales(graphStream);

                // Obtener la vista principal
                View view = viewerActual.getDefaultView();

                // Habilitar el zoom
                habilitarZoom(viewerActual);

                Component viewComponent = (Component) view;  // Convertir a componente Swing

                if (scrollPane != null) {
                    remove(scrollPane);  // Remover el anterior JScrollPane si existe
                }

                // Crear y configurar JScrollPane con el viewComponent
                scrollPane = new JScrollPane(viewComponent);
                scrollPane.setPreferredSize(new Dimension(800, 600));  // Ajustar tamaño

                add(scrollPane, BorderLayout.CENTER);  // Añadir el JScrollPane al centro

                // Refrescar la ventana para mostrar los cambios
                revalidate();
                repaint();
            }
        }
    }
    
    
    
    // Asignar posiciones estáticas en una línea recta para los nodos
    private void asignarPosicionesLineales(Graph graphStream) {
        double posX = 0.0; // Posición inicial en el eje X
        double increment = 1.0; // Incremento de espacio entre nodos

        for (Node node : graphStream) {
            // Asigna coordenadas X incrementales y Y constante para formar una línea recta horizontal
            node.setAttribute("xyz", posX, 0.0, 0.0);
            posX += increment; // Incrementa la posición X para el siguiente nodo
        }
    }

    // Habilitar el zoom in/out usando el scroll del mouse
    private void habilitarZoom(Viewer viewer) {
        // Acceder al componente subyacente de Swing y añadir el MouseWheelListener
        View view = viewer.getDefaultView();
        Component viewComponent = (Component) view;  // Cast de View a Component
        Camera camera = viewer.getDefaultView().getCamera();

        viewComponent.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double factor = e.getPreciseWheelRotation() > 0 ? 1.1 : 0.9; // Zoom in o zoom out
                camera.setViewPercent(camera.getViewPercent() * factor);  // Ajustar el zoom de la cámara
            }
        });
    }
    
  
    // Método para inicializar la ventana desde fuera (ejemplo)
    public static void inicializarVentanaConGrafos(ListaArray listaGrafos, Lista lineas) {
        VisualizarGrafo visualizarGrafo = new VisualizarGrafo(listaGrafos, lineas); // Instancia la ventana con los grafos reales
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
