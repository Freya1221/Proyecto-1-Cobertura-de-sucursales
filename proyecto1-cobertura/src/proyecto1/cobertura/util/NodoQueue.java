/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura.util;

/**
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
