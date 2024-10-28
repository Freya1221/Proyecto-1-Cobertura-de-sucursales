/*
 * Clase Nodo específica para las colas
 * 
 */
package proyecto1.cobertura.util;

/**
 * Constructor del nodo que toma variables de tipo Objeto
 * 
 * @author sebas
 */
public class NodoQueue {
    private NodoQueue next;
    private Object element;

    public NodoQueue(Object element) {
        this.next = null;
        this.element = element;
    }

    public NodoQueue getNext() {
        return next;
    }

    public void setNext(NodoQueue next) {
        this.next = next;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }
    
}
