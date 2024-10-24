/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package interfaces;

import javax.swing.*;
import java.awt.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.View;
import proyecto1.cobertura.Grafo;
import proyecto1.cobertura.Lista;
import proyecto1.cobertura.ListaArray.ListaArray;
import proyecto1.cobertura.Nodo;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;


import java.util.*;  // Para BFS

/**
 * En esta clase se abre una ventana para poder visualizar el grafo, establecer
 * sucursales y observar el radio.
 *
 * @author Sebastián Arriaga
 */
public class VisualizarGrafo extends JFrame {

    private Viewer viewerActual;
    private ListaArray listaGrafos;
    private JComboBox<String> selectorGrafos;
    private JComboBox<String> selectorEstaciones; // Nueva barra de selección de estaciones
    private JButton botonEstacionAceptar; // Botón de Aceptar para la estación
    private Lista lineas;
    private int t;
    private JScrollPane scrollPane;
    private Graph graphStream;  // El grafo que se mostrará

    private ViewPanel graphPanel;  // El panel que contiene el grafo de GraphStream

    private JFrame frame;

    public VisualizarGrafo(ListaArray listaGrafos, Lista lineas, int t) {

        this.listaGrafos = listaGrafos;
        this.lineas = lineas;
        this.t = t;

        setTitle("Visualizador de Grafos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        JPanel panelSeleccion = new JPanel();
        selectorGrafos = new JComboBox<>();
        selectorEstaciones = new JComboBox<>(); // Inicialización del selector de estaciones
        JButton botonMostrar = new JButton("Mostrar Grafo");
        botonEstacionAceptar = new JButton("Aceptar Estación"); // Botón para aceptar la estación

        llenarSelectorConLineas();
        panelSeleccion.add(selectorGrafos);
        panelSeleccion.add(botonMostrar);
        panelSeleccion.add(selectorEstaciones); // Añadimos el selector de estaciones
        panelSeleccion.add(botonEstacionAceptar); // Añadimos el botón de aceptar estación

        botonMostrar.addActionListener(e -> mostrarGrafoSeleccionado());
        botonEstacionAceptar.addActionListener(e -> mostrarRadioCobertura()); // Acción al aceptar estación

        setLayout(new BorderLayout());
        add(panelSeleccion, BorderLayout.NORTH); // Panel de selección en la parte superior
        setVisible(true);
    }

    // Método para llenar el JComboBox con las líneas del sistema de transporte
    private void llenarSelectorConLineas() {
        Nodo pointer = lineas.getHead();
        while (pointer != null) {
            selectorGrafos.addItem(pointer.getEstacion().getSucursal());
            pointer = pointer.getNext();
        }
    }

    // Método para mostrar el grafo seleccionado y aplicar zoom y desplazamiento
    private void mostrarGrafoSeleccionado() {
        String lineaSeleccionada = (String) selectorGrafos.getSelectedItem();

        if (lineaSeleccionada != null) {
            Grafo grafoSeleccionado = null;

            for (int i = 0; i < listaGrafos.getSize(); i++) {
                Grafo grafo = (Grafo) listaGrafos.getArray()[i].getElement();
                if (grafo.getEstaciones().getHead().getEstacion().getSucursal().equals(lineaSeleccionada)) {
                    grafoSeleccionado = grafo;
                    break;
                }
            }

            if (grafoSeleccionado != null) {
                // Asignar el graphStream
                graphStream = grafoSeleccionado.toGraphStream();  // << IMPORTANTE
                System.setProperty("org.graphstream.ui", "swing");

                // Cambiar estilo de nodos y etiquetas
                graphStream.setAttribute("ui.stylesheet",
                        "node { fill-color: black; size: 15px; text-alignment: under; text-size: 12px; text-color: black; }"
                        + "edge { fill-color: black; }");

                if (viewerActual != null) {
                    viewerActual.close();
                }

                viewerActual = graphStream.display(false); // Mostrar el grafo 
                viewerActual.disableAutoLayout();
                asignarPosicionesLineales(graphStream);

                View view = viewerActual.getDefaultView();
                habilitarZoom(viewerActual); // Habilitar zoom

                Component viewComponent = (Component) view;

                if (scrollPane != null) {
                    remove(scrollPane);
                }

                // Crear JScrollPane para permitir desplazamiento con barras
                scrollPane = new JScrollPane(viewComponent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setPreferredSize(new Dimension(800, 600));

                add(scrollPane, BorderLayout.CENTER); // Añadir al centro de la ventana
                llenarSelectorEstaciones(graphStream);

                revalidate(); // Refrescar la ventana
                repaint();   // Repintar la ventana
            }
        }
    }

    // Llenar el selector de estaciones basado en el grafo cargado
    private void llenarSelectorEstaciones(Graph graphStream) {
        selectorEstaciones.removeAllItems(); // Limpiar el JComboBox de estaciones
        for (Node node : graphStream) {
            selectorEstaciones.addItem(node.getId()); // Añadir cada estación al JComboBox
        }
    }

    // Método para mostrar el radio de cobertura de la estación seleccionada
    private void mostrarRadioCobertura() {
        if (graphStream == null) {
            System.err.println("Error: No se ha cargado ningún grafo.");
            return;
        }

        String estacionSeleccionada = (String) selectorEstaciones.getSelectedItem();
        if (estacionSeleccionada != null) {
            Node estacion = graphStream.getNode(estacionSeleccionada);
            if (estacion != null) {
                System.out.println("Estación seleccionada: " + estacion.getId());
                
                Set<Node> estacionesEnRadio = calcularEstacionesEnRadio(estacion, t);
                mostrarRadio(estacion, estacionesEnRadio);  // Mostrar el radio basado en las estaciones dentro del rango
            }
        }
    }
    
//    private Set<Node> calcularEstacionesEnRadio(Node estacionInicial, int t) {
//        Set<Node> visitados = new HashSet<>();
//        Queue<Node> cola = new LinkedList<>();
//        Map<Node, Integer> distancia = new HashMap<>();
//
//        cola.add(estacionInicial);
//        distancia.put(estacionInicial, 0);
//
//        while (!cola.isEmpty()) {
//            Node estacionActual = cola.poll();
//            int dist = distancia.get(estacionActual);
//
//            // Si la distancia actual es mayor que t, no continuar
//            if (dist >= t) {
//                continue;
//            }
//
//            // Obtener vecinos
//            for (Node vecino : estacionActual.getEachNeighbor()) {
//                if (!visitados.contains(vecino)) {
//                    distancia.put(vecino, dist + 1);
//                    cola.add(vecino);
//                    visitados.add(vecino);
//                }
//            }
//        }
//
//        return visitados;
//    }
    
    

    // Método para mostrar un círculo alrededor de la estación seleccionada
    private void mostrarRadio(Node estacion, double radio) {
        // Restaurar estilo anterior del nodo antes de aplicar cambios
        estacion.setAttribute("ui.style", "fill-color: black; size: 15px; text-alignment: under; text-size: 12px;");

        // Verificar si ya existe un nodo de radio anterior y eliminarlo
        String radioId = estacion.getId() + "_radio";
        if (graphStream.getNode(radioId) != null) {
            graphStream.removeNode(radioId);
        }

        // Crear un nodo adicional para representar el círculo del radio
        Node nodoRadio = graphStream.addNode(radioId);

        // Posicionar el nodo del círculo en la misma ubicación que la estación
        Object[] posicionObj = (Object[]) estacion.getAttribute("xyz");
        double[] posicion = new double[posicionObj.length];

// Convertimos el Object[] a double[]
        for (int i = 0; i < posicionObj.length; i++) {
            posicion[i] = (double) posicionObj[i];
        }

// Ahora puedes usar `posicion` como coordenadas xyz
        nodoRadio.setAttribute("xyz", posicion[0], posicion[1], posicion[2]);

        // Establecer el estilo para el círculo de radio con opacidad
        nodoRadio.setAttribute("ui.style", String.format(
                "size: %fgu; fill-color: rgba(0, 0, 255, 0.2); stroke-mode: plain; stroke-color: blue; stroke-width: 2px;",
                radio // Ajustar el tamaño del círculo según el valor `t`
        ));

        // Añadir un mensaje de depuración para verificar si el nodo se crea
        System.out.println("Nodo de radio creado: " + nodoRadio.getId() + " con tamaño: " + radio);
    }
    // Asignar posiciones estáticas en una línea recta para los nodos

    private void asignarPosicionesLineales(Graph graphStream) {
        double posX = 0.0;
        double increment = 1.0;

        for (Node node : graphStream) {
            node.setAttribute("xyz", posX, 0.0, 0.0); // Asignar coordenadas X e Y
            posX += increment;
        }
    }

    // Habilitar el zoom usando la rueda del ratón
    private void habilitarZoom(Viewer viewer) {
        View view = viewer.getDefaultView();
        Component viewComponent = (Component) view;
        Camera camera = viewer.getDefaultView().getCamera();

        viewComponent.addMouseWheelListener(e -> {
            double factor = e.getPreciseWheelRotation() > 0 ? 1.1 : 0.9;
            camera.setViewPercent(camera.getViewPercent() * factor); // Ajustar el zoom

            // Añadido: Ajustar las barras de desplazamiento cuando se hace zoom
            scrollPane.getViewport().revalidate();
            scrollPane.getViewport().repaint();
        });
    }

    public static void inicializarVentanaConGrafos(ListaArray listaGrafos, Lista lineas, int t) {
        new VisualizarGrafo(listaGrafos, lineas, t);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          

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
