/**
 * En esta clase grafo se diseña un grafo de tipo matriz adyacente, junto con todos sus métodos
 * 
 * @author Sebastián Arriaga
 */

package proyecto1.cobertura;



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

}
