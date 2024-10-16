/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura;


public class Grafo {
    
    private Lista estaciones;
    private int[][] matrizAdyacente;

    public Grafo() {
        estaciones = new Lista();
    }
    
    public void addEstacion(String sucursal, Boolean cubierto, String nombre) {
        Estacion estacion = new Estacion(sucursal, cubierto, nombre);
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
            matrizAdyacente[index1][index2] = 1;
            matrizAdyacente[index2][index1] = 1;
        }
    }
    
    public void initializarMatriz() {
        int size = estaciones.getSize();
        matrizAdyacente = new int[size][size];
    }
    
    public void printMatriz() {
        for (int[] row : matrizAdyacente) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
}
