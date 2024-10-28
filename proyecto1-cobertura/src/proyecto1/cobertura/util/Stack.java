/*
 * Esta clase se crea para utilizar el tipo Stack, junto a todas
 * sus primitivas
 */
package proyecto1.cobertura.util;

/**
 * En este constructor pasamos como atributos el NodoStack y el size
 * 
 * @author sebas
 */
public class Stack {
    private NodoStack peak;
    private int size;
    
    public Stack() {
        this.peak = null;
        this.size = 0;
    }

    public NodoStack getPeak() {
        return peak;
    }

    public void setPeak(NodoStack peak) {
        this.peak = peak;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean isEmpty() {
        return getPeak() == null;
    }
    
    public NodoStack push(Object element) {
        NodoStack nodo = new NodoStack(element);
        if (isEmpty()) {
            setPeak(nodo);
        } else {
            nodo.setNext(getPeak());
            setPeak(nodo);
        }
        size++;
        return nodo;
    }
    
    public NodoStack pop() {
        if (isEmpty()) {
            System.out.println("The stack is empty");
            return null;
        } else {
            NodoStack pointer = getPeak();
            setPeak(pointer.getNext());
            pointer.setNext(null);
            size--;
            return pointer;
        }
    }
    
    public void print() {
        NodoStack pointer = getPeak();
        while (pointer != null) {
            System.out.println("["+pointer.getElement()+"]");
            pointer = pointer.getNext();
        }
    }
}
