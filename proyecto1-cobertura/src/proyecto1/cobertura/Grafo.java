/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura;

import java.util.Arrays;

/**
 *
 * @author freya
 */
public class Grafo {

    private int size;
    private int[][] matrizAdyacencia;

    public Grafo(int size) {
        this.size = size;
        matrizAdyacencia = new int[size][size];

        for (int[] fila : matrizAdyacencia) {
            Arrays.fill(fila, 0);
        }
    }
}
