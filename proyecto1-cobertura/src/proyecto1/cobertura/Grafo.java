/**
 * En esta clase grafo se diseña un grafo de tipo matriz adyacente, junto con todos sus métodos
 *
 * @author Sebastián Arriaga
 */
package proyecto1.cobertura;

import proyecto1.cobertura.util.Estacion;
import proyecto1.cobertura.util.Lista;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Grafo {

    private Lista estaciones;
    private int[][] matrizAdyacencia;
    private int numVertices;

    public Grafo() {
        estaciones = new Lista();
    }

    public Lista getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(Lista estaciones) {
        this.estaciones = estaciones;
    }

    public int[][] getMatrizAdyacencia() {
        return matrizAdyacencia;
    }

    public void setMatrizAdyacencia(int[][] matrizAdyacencia) {
        this.matrizAdyacencia = matrizAdyacencia;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public void addEstacion(String sucursal, String nombre) {
        Estacion estacion = new Estacion(sucursal, nombre);
        if (!containsEstacion(estacion)) {
            estaciones.insertFinal(estacion);
        }
    }

    private boolean containsEstacion(Estacion estacion) {
        for (int i = 0; i < estaciones.getSize(); i++) {
            if (estaciones.obtener(i).getNombre().equalsIgnoreCase(estacion.getNombre())) {
                return true;
            }
        }
        return false;
    }

    private int findEstacionIndex(String nombre) {
        for (int i = 0; i < estaciones.getSize(); i++) {
            if (estaciones.obtener(i).getNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    public void agregarConexion(String nombre1, String nombre2) {
        int index1 = findEstacionIndex(nombre1);
        int index2 = findEstacionIndex(nombre2);

        if (index1 != -1 && index2 != -1) {
            matrizAdyacencia[index1][index2] = 1;
            matrizAdyacencia[index2][index1] = 1;
        }
    }

    public void inicializarMatriz(int numVertices) {
        this.numVertices = numVertices;
        matrizAdyacencia = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrizAdyacencia[i][j] = 0;
            }
        }
    }

    public void printMatriz() {
        for (int[] row : matrizAdyacencia) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    // Método para visualizar el grafo con GraphStream
    public void visualizarGrafo() {
        Graph graph = new SingleGraph("Grafo del sistema de transporte");

        // Agregar nodos (estaciones) al grafo
        for (int i = 0; i < estaciones.getSize(); i++) {
            Estacion estacion = estaciones.obtener(i);
            graph.addNode(estacion.getNombre()).setAttribute("ui.label", estacion.getNombre());
        }

        // Agregar aristas (conexiones) según la matriz de adyacencia
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    String nombre1 = estaciones.obtener(i).getNombre();
                    String nombre2 = estaciones.obtener(j).getNombre();
                    // Evitar duplicar aristas
                    if (graph.getEdge(nombre1 + "-" + nombre2) == null && graph.getEdge(nombre2 + "-" + nombre1) == null) {
                        graph.addEdge(nombre1 + "-" + nombre2, nombre1, nombre2);
                    }
                }
            }
        }
        System.setProperty("org.graphstream.ui", "swing");

        // Visualizar el grafo
        graph.display();
    }

    public Graph toGraphStream() {
        Graph graph = new SingleGraph("Grafo del sistema de transporte");

        // Agregar nodos (estaciones) al grafo de GraphStream
        for (int i = 0; i < estaciones.getSize(); i++) {
            Estacion estacion = estaciones.obtener(i);
            Node node = graph.addNode(estacion.getNombre());
            node.setAttribute("ui.label", estacion.getNombre());
            node.setAttribute("estacion", estacion);  // Asignar la estación como atributo al nodo
        }

        // Agregar aristas (conexiones) según la matriz de adyacencia
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdyacencia[i][j] == 1) {
                    String nombre1 = estaciones.obtener(i).getNombre();
                    String nombre2 = estaciones.obtener(j).getNombre();
                    // Evitar duplicar aristas
                    if (graph.getEdge(nombre1 + "-" + nombre2) == null && graph.getEdge(nombre2 + "-" + nombre1) == null) {
                        graph.addEdge(nombre1 + "-" + nombre2, nombre1, nombre2);
                    }
                }
            }
        }

        return graph;  // Retorna el grafo compatible con GraphStream
    }

}
