/**
 * En esta clase se abre una ventana para poder visualizar el grafo, establecer
 * sucursales y observar el radio.
 *
 * @author Sebastián Arriaga
 */
package interfaces;

import javax.swing.*;
import java.awt.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import proyecto1.cobertura.Grafo;
import proyecto1.cobertura.util.Lista;
import proyecto1.cobertura.util.ListaArray;
import proyecto1.cobertura.util.Nodo;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;
import proyecto1.cobertura.util.Estacion;
import proyecto1.cobertura.util.Map;
import proyecto1.cobertura.util.NodoArray;
import proyecto1.cobertura.util.Queue;
import proyecto1.cobertura.util.Set;
import proyecto1.cobertura.util.Stack;

/**
 * Se declaran los elementos poder trabajar la visualización del grafo, toma
 * como parámetros la Lista de grafos, el nombre de las línea y t
 *
 * @author Sebastián Arriaga
 */
public class VisualizarGrafo extends JFrame {

    private Viewer viewerActual;
    private ListaArray listaGrafos;
    private JComboBox<String> selectorGrafos;
    private JComboBox<String> selectorEstaciones;
    private JButton btnBFS;
    private JButton btnDFS;
    private Lista lineas;
    private int t;
    private JScrollPane scrollPane;
    private Graph graphStream;
    String lineaSeleccionada = new String();
    private Set estacionesSeleccionadas;
    private JComboBox<String> selectorEstacionesSeleccionadas;
    private JButton btnDeseleccionar;

    public VisualizarGrafo(ListaArray listaGrafos, Lista lineas, int t) {

        this.listaGrafos = listaGrafos;
        this.lineas = lineas;
        this.t = t;

//        Se establecen sus características
        setTitle("Visualizador de Grafos");
        setSize(900, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        Aquí se inicia el panel de selección
        JPanel panelSeleccion = new JPanel();
        selectorGrafos = new JComboBox<>();
        selectorEstaciones = new JComboBox<>();
        JButton botonMostrar = new JButton("Mostrar Grafo");

        // Botón para búsqueda en anchura (BFS)
        btnBFS = new JButton("Buscar por BFS");

        // Botón para búsqueda en profundidad (DFS)
        btnDFS = new JButton("Buscar por DFS");

        // Aquí se combinan todos para dar paso a la construcción de la ventana
        llenarSelectorConLineas();
        panelSeleccion.add(selectorGrafos);
        panelSeleccion.add(botonMostrar);
        panelSeleccion.add(selectorEstaciones);
        panelSeleccion.add(btnDFS);
        panelSeleccion.add(btnBFS);

        botonMostrar.addActionListener(e -> mostrarGrafoSeleccionado(lineaSeleccionada));
        btnDFS.addActionListener(e -> mostrarRadioCobertura(true));
        btnBFS.addActionListener(e -> mostrarRadioCobertura(false));

        estacionesSeleccionadas = new Set();
        selectorEstacionesSeleccionadas = new JComboBox<>();
        btnDeseleccionar = new JButton("Deseleccionar");

        // Agregar los componentes al panel de selección
        panelSeleccion.add(selectorEstacionesSeleccionadas);
        panelSeleccion.add(btnDeseleccionar);

        // Añadir el evento de deselección
        btnDeseleccionar.addActionListener(e -> deseleccionarEstacion());

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

    // Método para configurar y aplicar estilos al grafo
    private void configurarEstilos(Grafo grafo) {
        String css = """
    node {
        fill-color: black;
        size: 15px;
        text-size: 14px;
        text-color: black;
        text-background-mode: rounded-box;
        text-background-color: #FFFFFFAA;
        text-alignment: center;
    }
    node.enRadio {
        fill-color: rgba(0, 0, 255, 0.7);
        size: 25px;
        stroke-mode: plain;
        stroke-color: blue;
        stroke-width: 3px;
        text-color: blue;
    }
    """;

        // Aplicar la hoja de estilo CSS al grafo
        grafo.toGraphStream().setAttribute("ui.stylesheet", css);
    }

    // Método para mostrar el grafo seleccionado y aplicar zoom y desplazamiento
    private void mostrarGrafoSeleccionado(String lineaSeleccionada) {
        lineaSeleccionada = (String) selectorGrafos.getSelectedItem();

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
                graphStream = grafoSeleccionado.toGraphStream();
                System.setProperty("org.graphstream.ui", "swing");

                // Cambiar estilo de los nodos
                graphStream.setAttribute("ui.stylesheet",
                        "node { fill-color: black; size: 15px; text-alignment: under; text-size: 9px; text-color: black; }"
                        + "edge { fill-color: black; }");

                if (viewerActual != null) {
                    viewerActual.close();
                }
                // Mostrar el grafo
                viewerActual = graphStream.display(false);
                viewerActual.disableAutoLayout();
                asignarPosicionesLineales(graphStream);
                configurarEstilos(grafoSeleccionado);

                habilitarZoom(viewerActual);

                if (scrollPane != null) {
                    remove(scrollPane);
                }

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

//    Método para el radio de cobertura, se llama DFS o BFS dependiendo de la elección
    private void mostrarRadioCobertura(boolean usarDFS) {
        String estacionSeleccionada = (String) selectorEstaciones.getSelectedItem();

        if (estacionSeleccionada == null) {
            System.err.println("Error: No se ha seleccionado ninguna estación.");
            return;
        }

        Integer headIndex = listaGrafos.getHead();

        if (headIndex != null) {
            NodoArray pointer = listaGrafos.getArray()[headIndex];
            while (pointer != null) {
                Grafo grafo = (Grafo) pointer.getElement();
                Nodo estacionPointer = grafo.getEstaciones().getHead();
                while (estacionPointer != null) {
                    Estacion estacion = estacionPointer.getEstacion();
                    if (estacion.getNombre().equals(estacionSeleccionada)) {
                        seleccionarEstacion(estacion);
                        System.out.println("Estación seleccionada: " + estacion.getNombre());

                        Set nodosEnRadio;
                        if (usarDFS) {
                            nodosEnRadio = calcularEstacionesEnRadioDFS(estacion, t, grafo);
                        } else {
                            nodosEnRadio = calcularEstacionesEnRadio(estacion, t, grafo);
                        }

                        configurarEstilos(grafo);
                        mostrarCirculoDeCobertura(nodosEnRadio, grafo);
                        return;
                    }
                    estacionPointer = estacionPointer.getNext();
                }

                if (pointer.getNext() != null) {
                    pointer = listaGrafos.getArray()[pointer.getNext()];
                } else {
                    pointer = null;
                }
            }
        } else {
            System.out.println("La lista de grafos está vacía.");
        }
    }

// Método de búsqueda DFS para calcular el radio de cobertura
    private Set calcularEstacionesEnRadioDFS(Estacion estacionInicial, int t, Grafo grafo) {
        Set visitados = new Set();
        Stack pila = new Stack();
        Map distancia = new Map();

        pila.push(estacionInicial);
        distancia.put(estacionInicial, 0);
        visitados.add(estacionInicial);

        while (!pila.isEmpty()) {
            Estacion estacionActual = (Estacion) pila.pop().getElement();
            int dist = distancia.get(estacionActual);

            // Si se alcanza el limite, se para
            if (dist >= t) {
                continue;
            }

            int estacionIndex = grafo.findEstacionIndex(estacionActual.getNombre());
            if (estacionIndex == -1) {
                continue;
            }

            for (int j = 0; j < grafo.getNumVertices(); j++) {
                if (grafo.getMatrizAdyacencia()[estacionIndex][j] == 1) { // Si hay conexión
                    Estacion vecino = grafo.getEstaciones().obtener(j);

                    if (!visitados.contains(vecino)) {
                        distancia.put(vecino, dist + 1); // Se actualiza la distancia
                        pila.push(vecino);               // Se agrega el vecino a la pila
                        visitados.add(vecino);           // Se agrega el vecino a `visitados`
                    }
                }
            }
        }

        System.out.println("Estaciones en el radio de cobertura de distancia " + t + " usando DFS:");
        visitados.print();

        return visitados;
    }

    // Método de búsqueda BFS para calcular el radio de cobertura
    private Set calcularEstacionesEnRadio(Estacion estacionInicial, int t, Grafo grafo) {
        Set visitados = new Set();      // Almacena estaciones visitadas
        Queue cola = new Queue();       // Cola para BFS
        Map distancia = new Map();      // Distancia desde la estación inicial

        cola.enqueue(estacionInicial);
        distancia.put(estacionInicial, 0);
        distancia.get(estacionInicial);
        visitados.add(estacionInicial);

        while (!cola.isEmpty()) {
            Estacion estacionActual = (Estacion) cola.dequeue().getElement();
            System.out.println(estacionActual.getNombre());
            int dist = distancia.get(estacionActual);
            System.out.println(dist);

            // Si hemos alcanzado el límite de distancia `t`, dejamos de explorar esta ruta
            if (dist >= t) {
                continue;
            }

            // Obtener el índice de la estación actual en la lista de estaciones del grafo
            int estacionIndex = grafo.findEstacionIndex(estacionActual.getNombre());
            if (estacionIndex == -1) {
                continue;  // Saltar si la estación no se encuentra en el grafo
            }

            // Iterar sobre la fila correspondiente en la matriz de adyacencia
            for (int j = 0; j < grafo.getNumVertices(); j++) {
                if (grafo.getMatrizAdyacencia()[estacionIndex][j] == 1) {  // Si hay conexión
                    Estacion vecino = grafo.getEstaciones().obtener(j);

                    // Agregar el vecino a los nodos visitados si aún no se ha procesado
                    if (!visitados.contains(vecino)) {
                        distancia.put(vecino, dist + 1); // Actualizamos la distancia
                        cola.enqueue(vecino);
                        visitados.add(vecino);           // Añadimos el vecino a `visitados`
                    }
                }
            }
        }

        // Verificar qué estaciones fueron alcanzadas dentro del radio `t`
        System.out.println("Estaciones en el radio de cobertura de distancia " + t + ":");
        visitados.print();

        return visitados;
    }

    // Método para mostrar el círculo de cobertura y estilizar los nodos alcanzados
    private void mostrarCirculoDeCobertura(Set nodosEnRadio, Grafo grafo) {
//        Establecer el predeterminado
        Nodo estacionPointer = grafo.getEstaciones().getHead();
        while (estacionPointer != null) {
            Estacion estacion = estacionPointer.getEstacion();
            Node nodo = grafo.toGraphStream().getNode(estacion.getNombre());

            if (nodo != null) {
                nodo.removeAttribute("ui.class");
                nodo.setAttribute("ui.style", "fill-color: black; size: 15px;"); // Restaurar a estilo original
            }

            estacionPointer = estacionPointer.getNext();
        }

        // Aplicar estilo predeterminado a las estaciones abarcadas por el radio
        int contadorEstaciones = 0;
        for (int i = 0; i < nodosEnRadio.size(); i++) {
            Estacion estacionEnRadio = (Estacion) nodosEnRadio.obtener(i).getEstacion();
            Node nodoRadio = grafo.toGraphStream().getNode(estacionEnRadio.getNombre());

            if (nodoRadio != null) {
                nodoRadio.setAttribute("ui.class", "enRadio"); // Asignar la clase `enRadio`
                nodoRadio.setAttribute("ui.style", "fill-color: blue; size: 25px; stroke-mode: plain; stroke-color: blue; stroke-width: 3px;");
                contadorEstaciones++;
            }
        }

        // Refrescar la vista del grafo para que los cambios sean visibles
        revalidate();
        repaint();

        // Muestra el número total de estaciones en el radio de cobertura en la consola
        System.out.println("Número de estaciones en el radio de cobertura: " + contadorEstaciones);
    }

// Habilitar el zoom usando la rueda del ratón
    private void habilitarZoom(Viewer viewer) {
        View view = viewer.getDefaultView();
        Component viewComponent = (Component) view;
        Camera camera = viewer.getDefaultView().getCamera();

        viewComponent.addMouseWheelListener(e -> {
            double factor = e.getPreciseWheelRotation() > 0 ? 1.1 : 0.9;
            camera.setViewPercent(camera.getViewPercent() * factor); // Ajustar el zoom

        });
    }

    // Asignar posiciones estáticas en una línea recta para los nodos
    private void asignarPosicionesLineales(Graph graphStream) {
        double posX = 0.0;
        double increment = 1.0;

        for (Node node : graphStream) {
            node.setAttribute("xyz", posX, 0.0, 0.0);
            posX += increment;
        }
    }

//    Almacena estaciones seleccionadas
    private void seleccionarEstacion(Estacion estacion) {
        if (!estacionesSeleccionadas.contains(estacion)) {
            estacionesSeleccionadas.add(estacion);
            selectorEstacionesSeleccionadas.addItem(estacion.getNombre());
            actualizarEstiloEstacion(estacion, "seleccionada");
        }
    }

//    Método para deseleccionar estaciones seleccionadas
    private void deseleccionarEstacion() {
        String estacionNombre = (String) selectorEstacionesSeleccionadas.getSelectedItem();
        if (estacionNombre != null) {
            Estacion estacion = obtenerEstacionPorNombre(estacionNombre);

            if (estacion != null) {
                estacionesSeleccionadas.remove(estacion);
                selectorEstacionesSeleccionadas.removeItem(estacionNombre);
                actualizarEstiloEstacion(estacion, "default");
            } else {
                System.err.println("Error: La estación a deseleccionar no se encontró.");
            }
        } else {
            System.err.println("Error: No se seleccionó ninguna estación.");
        }
    }

//    Buscar estación por nombre
    private Estacion obtenerEstacionPorNombre(String nombre) {
        if (nombre == null) {
            System.err.println("Error: El nombre de la estación a buscar es nulo.");
            return null;
        }

        Nodo pointer = lineas.getHead();
        while (pointer != null) {
            Estacion estacion = pointer.getEstacion();

            // Verificamos que la estación y su nombre no sean nulos
            if (estacion != null && nombre.equalsIgnoreCase(estacion.getNombre())) {
                return estacion;
            }

            pointer = pointer.getNext();
        }

        // Mensaje si no se encuentra la estación
        System.err.println("Error: No se encontró la estación con el nombre " + nombre);
        return null;
    }

    private void actualizarEstiloEstacion(Estacion estacion, String estilo) {
        if (estacion == null || estacion.getNombre() == null) { // Verificar nulos
            System.err.println("Error: La estación es nula o no tiene nombre.");
            return; // Salir si la estación es nula o no tiene nombre
        }

        Node nodo = graphStream.getNode(estacion.getNombre());
        if (nodo != null) {
            if ("seleccionada".equals(estilo)) {
                nodo.setAttribute("ui.class", "seleccionada");
                nodo.setAttribute("ui.style", "fill-color: blue; size: 15px; stroke-mode: plain; stroke-color: green; stroke-width: 3px;");
            } else {
                nodo.setAttribute("ui.style", "fill-color: black; size: 5px;"); // Estilo predeterminado
            }
        } else {
            System.err.println("Error: No se encontró el nodo en el grafo.");
        }
    }

    public static void inicializarVentanaConGrafos(ListaArray listaGrafos, Lista lineas, int t) {
        new VisualizarGrafo(listaGrafos, lineas, t);
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
