/*
 * Clase para trabajar con colas junto a todos sus métodos
 * 
 */
package proyecto1.cobertura.util;

/**
 * Constructor toma un NodoQueue, que es un nodo para trabajar las colas
 * Diferente al nodo base porque el nodo base trabaja con el tipo Estación
 * 
 * @author sebas
 */
public class Queue {
    private NodoQueue head, tail;
    private int size;

    public Queue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public NodoQueue getHead() {
        return head;
    }

    public void setHead(NodoQueue head) {
        this.head = head;
    }

    public NodoQueue getTail() {
        return tail;
    }

    public void setTail(NodoQueue tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean isEmpty() {
        return getHead() == null && getTail() == null;
    }
    
    public NodoQueue enqueue(Object element) {
        NodoQueue nodo = new NodoQueue(element);
        if (isEmpty()) {
            setHead(nodo);
            setTail(nodo);
        } else {
            getTail().setNext(nodo);
            setTail(nodo);
        }
        size++;
        return nodo;
    }
    
    public NodoQueue dequeue() {
        if (isEmpty()) {
            System.out.println("The queue is empty");
            return null;
        } else {
            NodoQueue pointer = getHead();
            if (getTail() == getHead()) {
                setHead(null);
                setTail(null);
            } else {
                setHead(pointer.getNext());
                pointer.setNext(null);
            }
            size--;
            return pointer;
        }
    }
    
    public NodoQueue dispatch() {
        if (!isEmpty()){
            System.out.println("["+getHead().getElement()+"]");
        }
        return dequeue();
    }
    
    public void print() {
        NodoQueue pointer = getHead();
        while (pointer != null) {
            System.out.print("["+pointer.getElement()+"]");
            pointer = pointer.getNext();
        }
    } 
           
}
