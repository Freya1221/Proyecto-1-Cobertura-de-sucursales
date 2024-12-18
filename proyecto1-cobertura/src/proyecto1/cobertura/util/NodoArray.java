/**
 * Clase NodoArray para trabajar las listas array
 * 
 * @author Sebastián Arriaga
 */

package proyecto1.cobertura.util;

public class NodoArray {
    private Object element;
    private Integer next;

    public NodoArray(Object element) {
        this.element = element;
        this.next = null;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }
}
