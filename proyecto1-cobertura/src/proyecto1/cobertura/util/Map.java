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
        Nodo current = pares.getHead();
        while (current != null) {
            if (current.getEstacion().equals(clave)) {
                current.setValor(valor);
                return;
            }
            current = current.getNext();
        }
        Par newpar = new Par(clave, valor);
        pares.insertFinal2(newpar);
    }

    public Integer get(Estacion clave) {
        System.out.println(clave.getNombre());
        Nodo current = pares.getHead();
        while (current != null) {
            if (current.getEstacion().equals(clave)) {
                return current.getValor();
            }
            current = current.getNext();
        }
        return null;
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
        Nodo prev = null;
        Nodo current = pares.getHead();

        while (current != null) {
            if (current.getEstacion().equals(clave)) {
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