/**
 * Clase nodo para trabajar las listas
 * 
 * @author Sebastián Arriaga
 */

package proyecto1.cobertura;


public class Nodo {
    private Estacion estacion;
    private Nodo next;

    public Nodo(Estacion estacion) {
        this.estacion = estacion;
        this.next = null;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Nodo getNext() {
        return next;
    }

    public void setNext(Nodo siguiente) {
        this.next = siguiente;
    }
    
    
      
}
