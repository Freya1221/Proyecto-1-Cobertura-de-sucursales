/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura.util;

/**
 *
 * @author sebas
 */
public class Map {

    private Lista pares;

    public Map() {
        this.pares = new Lista();
    }

    public void put(Estacion clave, Integer valor) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
        Nodo current = pares.getHead();

        // Verificar si la clave ya existe
        while (current != null) {
            Par parExistente = current.getElement1();
            if (parExistente != null && clave.equals(parExistente.getClave())) {
                parExistente.setValor(valor); // Actualizar valor
                return;
            }
            current = current.getNext();
        }
        // Insertar nuevo par si la clave no existe
        pares.insertFinal2(new Par(clave, valor));
    }

    public Integer get(Estacion clave) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
        Nodo current = pares.getHead();

        // Buscar el nodo con el par correspondiente a la clave
        while (current != null) {
            Par par = current.getElement1();
            if (par != null && clave.equals(par.getClave())) {
                return par.getValor(); // Retornar el valor si la clave coincide
            }
            current = current.getNext();
        }
        return null; // Retornar null si la clave no existe en el mapa
    }

    public boolean containsKey(Estacion clave) {
        Nodo current = pares.getHead();
        while (current != null) {
            if (current.getEstacion().equals(clave)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public void remove(Estacion clave) {
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
        Nodo prev = null;
        Nodo current = pares.getHead();

        while (current != null) {
            if (clave.equals(current.getEstacion())) {  // Usar equals de la clave
                if (prev == null) {
                    pares.deleteBegin();
                } else {
                    prev.setNext(current.getNext());
                    current.setNext(null);
                }
                return;
            }
            prev = current;
            current = current.getNext();
        }
    }

    public int size() {
        return pares.getSize();
    }
}
