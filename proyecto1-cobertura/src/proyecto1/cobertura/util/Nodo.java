/*
 * Nodo para trabajar con las listas y los maps
 * 
 */
package proyecto1.cobertura.util;

/**
 * Constructor toma elementos de diferente tipo para ampliar
 * versatilidad
 * 
 * @author sebas
 */
public class Nodo {
    private Nodo next;
    private Estacion element;
    private Integer valor;
    private Par element1;

    public Nodo(Estacion element) {
        this.next = null;
        this.element = element;
    }

    public Nodo(Par element1) {
        this.next = null;
        this.element1 = element1;
    }

    public Nodo getNext() {
        return next;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }

    public Estacion getEstacion() {
        return element;
    }

    public void setEstacion(Estacion element) {
        this.element = element;
    }

    public Par getElement1() {
        return element1;
    }

    public void setElement1(Par element1) {
        this.element1 = element1;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
}
