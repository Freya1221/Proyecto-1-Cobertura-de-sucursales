/**
 * Clase Lista junto con sus primitivas
 * 
 * @author Sebastián Arriaga
 */

package proyecto1.cobertura.util;

public class Lista{
    
    private Nodo head;
    private int size;

    public Lista() {
        this.head = null;
        this.size = 0;
    }

    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    public Nodo insertFinal(Estacion estacion) {
        Nodo nodo = new Nodo(estacion);
        if (isEmpty()) {
            setHead(nodo);
        } else {
            Nodo pointer = getHead();
            while(pointer.getNext() != null) {
                pointer = pointer.getNext();
            }
            pointer.setNext(nodo);
        }
        size++;
        return nodo;
    }
    
    public Nodo insertFinal2(Par estacion) {
        Nodo nodo = new Nodo(estacion);
        if (isEmpty()) {
            setHead(nodo);
        } else {
            Nodo pointer = getHead();
            while(pointer.getNext() != null) {
                pointer = pointer.getNext();
            }
            pointer.setNext(nodo);
        }
        size++;
        return nodo;
    }
    
    
    public Nodo deleteBegin(){
        if (isEmpty()) {
            System.out.println("Error list is empty");
            return null;
        } else {
            Nodo pointer = getHead();
            setHead(pointer.getNext());
            pointer.setNext(null);
            return pointer;
        }
    }
    
    public Nodo deleteFinal() {
        if (isEmpty()) {
            System.out.println("Error list is empty");
            return null;
        } else {
            Nodo pointer = getHead();
            while (pointer.getNext().getNext() != null) {
                pointer = pointer.getNext();
            }
            Nodo temp = pointer.getNext();
            pointer.setNext(null);
            size--;
            return temp;
        }
    }
    
//    Con esta función se obtiene un nodo buscado por índice
    public Estacion obtener(int indice) {
        if (indice < 0 || indice >= getSize() ) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Nodo actual = getHead();
        for (int i = 0; i < indice; i++) {
            actual = actual.getNext();
        }
        return actual.getEstacion();
    }
    
    
    public void print() {
        Nodo pointer = getHead();
        while (pointer != null) {
            System.out.print("["+pointer.getEstacion()+"]");
            pointer = pointer.getNext();
        }
    }
}