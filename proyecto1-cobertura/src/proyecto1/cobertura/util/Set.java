/*
 * Clase para manejar sets junto a sus primitivas
 * 
 */
package proyecto1.cobertura.util;


/**
 * Toma una lista de elementos como constructor y verifica
 * si ya está repetido
 * 
 * @author sebas
 */

public class Set {
    private Lista elementos;

    public Set() {
        this.elementos = new Lista();
    }

    public boolean add(Estacion estacion) {
        if (!contains(estacion)) {
            elementos.insertFinal(estacion);
            return true;
        }
        return false;
    }

    public boolean contains(Estacion estacion) {
        Nodo current = elementos.getHead();
        while (current != null) {
            if (current.getEstacion().equals(estacion)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean remove(Estacion estacion) {
        Nodo prev = null;
        Nodo current = elementos.getHead();

        while (current != null) {
            if (current.getEstacion().equals(estacion)) {
                if (prev == null) {
                    elementos.deleteBegin();
                } else {
                    prev.setNext(current.getNext());
                    current.setNext(null);
                }
                return true;
            }
            prev = current;
            current = current.getNext();
        }
        return false;
    }
    
    public Nodo obtener(int index) {
        Nodo current = elementos.getHead();
        int count = 0;
        while (current != null) {
            if (count == index) {
                return current;
            }
            count++;
            current = current.getNext();
        }
        return null; 
    }

    public int size() {
        return elementos.getSize();
    }

    public boolean isEmpty() {
        return elementos.isEmpty();
    }

    public void print() {
        elementos.print();
    }

    public Lista getElementos() {
        return elementos;
    }
}
