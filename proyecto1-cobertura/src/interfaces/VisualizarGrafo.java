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
import proyecto1.cobertura.util.Lista;
import proyecto1.cobertura.util.ListaArray;
import proyecto1.cobertura.util.Nodo;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.graph.Edge;
import proyecto1.cobertura.util.Estacion;
import proyecto1.cobertura.util.Map;
import proyecto1.cobertura.util.Queue;
import proyecto1.cobertura.util.Set;

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

    // Método para mostrar el radio de cobertura basado en `t`
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
                // Aquí llamamos al método para calcular y mostrar el radio de cobertura
                mostrarRadio(estacion, t);  // `t` es el número de estaciones de cobertura
            }
        }
    }

    private Set calcularEstacionesEnRadio(Node estacionInicial, int t) {
        Set visitados = new Set();
        Queue cola = new Queue();
        Map distancia = new Map();

        // Recuperar la Estacion desde el Node usando el atributo
        Estacion estacionInicialReal = estacionInicial.getAttribute("estacion", Estacion.class);

        if (estacionInicialReal == null) {
            System.err.println("Error: El nodo no tiene una estación asociada.");
            return visitados; // Devolver un conjunto vacío en caso de error
        }

        // Añadir la estación inicial a la cola y marcarla como visitada
        cola.enqueue(estacionInicial);
        distancia.put(estacionInicialReal, 0);
        visitados.add(estacionInicialReal);

        while (!cola.isEmpty()) {
            Node estacionActual = (Node) cola.dequeue().getElement();
            Estacion estacionActualReal = estacionActual.getAttribute("estacion", Estacion.class);

            // Verificar si estacionActualReal es null
            if (estacionActualReal == null) {
                System.err.println("Error: El nodo actual no tiene una estación asociada.");
                continue;  // Omitir este nodo
            }

            Integer dist = distancia.get(estacionActualReal);

            // Si la distancia actual es mayor que t, no continuar
            if (dist >= t) {
                continue;
            }

            // Iterar sobre las aristas conectadas al nodo actual
            for (Edge arista : (Iterable<Edge>) estacionActual.edges()::iterator) {
                Node vecino = arista.getOpposite(estacionActual);
                Estacion vecinoEstacion = vecino.getAttribute("estacion", Estacion.class);

                if (vecinoEstacion != null && !visitados.contains(vecinoEstacion)) {
                    distancia.put(vecinoEstacion, dist + 1);
                    cola.enqueue(vecino);
                    visitados.add(vecinoEstacion);
                }
            }
        }

        return visitados;
    }

    // Método para calcular y mostrar el radio
    private void mostrarRadio(Node estacion, int t) {
        Set nodosEnRadio = calcularEstacionesEnRadio(estacion, t);
        Queue queue = new Queue();
        Map distancia = new Map();

        // Empezamos con la estación inicial
        nodosEnRadio.add((Estacion) estacion);
        queue.enqueue(estacion);
        distancia.put((Estacion) estacion, 0);  // La distancia inicial de la estación es 0

        // Nivel de BFS (Distancia)
        int nivel = 0;

        // Iteramos usando BFS hasta que el nivel sea mayor o igual a `t`
        while (!queue.isEmpty() && nivel < t) {
            int tamanoNivel = queue.getSize();  // Procesamos todos los nodos del nivel actual
            for (int i = 0; i < tamanoNivel; i++) {
                Node actual = (Node) queue.dequeue().getElement();  // Sacamos el nodo actual de la cola
                int dist = distancia.get((Estacion) actual);  // Obtenemos la distancia del nodo actual

                // Iteramos sobre las aristas (edges) del nodo actual
                actual.edges().forEach(arista -> {
                    // Obtenemos el nodo vecino a través de la arista
                    Node vecino = arista.getOpposite(actual);

                    // Si el vecino no ha sido visitado
                    if (!nodosEnRadio.contains((Estacion) vecino)) {
                        distancia.put((Estacion) vecino, dist + 1);  // Actualizamos la distancia del vecino
                        queue.enqueue(vecino);  // Añadimos el vecino a la cola para procesar
                        nodosEnRadio.add((Estacion) vecino);  // Añadimos el vecino a los nodos en el radio
                    }
                });
            }
            nivel++;  // Aumentamos el nivel de BFS
        }

        // Finalmente, mostramos el círculo de cobertura con los nodos encontrados
        mostrarCirculoDeCobertura(nodosEnRadio);
    }

    // Método para mostrar visualmente los nodos que están dentro del radio
    private void mostrarCirculoDeCobertura(Set nodosEnRadio) {
        // Restauramos el estilo de todos los nodos del grafo
        for (org.graphstream.graph.Node nodo : graphStream) {
            nodo.removeAttribute("ui.style");  // Restaurar el estilo a su estado original
        }

        // Establecemos el estilo para los nodos dentro del radio de cobertura
        for (int i = 0; i < nodosEnRadio.size(); i++) {
            org.graphstream.graph.Node nodoEnRadio = (org.graphstream.graph.Node) nodosEnRadio.obtener(i).getEstacion();
            nodoEnRadio.setAttribute("ui.style", "fill-color: rgba(0, 0, 255, 0.5); size: 20px;");
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

    // Asignar posiciones estáticas en una línea recta para los nodos
    private void asignarPosicionesLineales(Graph graphStream) {
        double posX = 0.0;
        double increment = 1.0;

        for (Node node : graphStream) {
            node.setAttribute("xyz", posX, 0.0, 0.0); // Asignar coordenadas X e Y
            posX += increment;
        }
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
