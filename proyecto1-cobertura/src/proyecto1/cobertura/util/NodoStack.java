/*
 * Nodo para utilizar la clase Stack
 * 
 */
package proyecto1.cobertura.util;

/**
 * Nodo con sus atributos
 * 
 * @author sebas
 */
public class NodoStack {
    private Object element;
    private NodoStack next;
    
    public NodoStack(Object element) {
        this.element = element;
        this.next = null;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public NodoStack getNext() {
        return next;
    }

    public void setNext(NodoStack next) {
        this.next = next;
    }
}
