/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg1;

/**
 *
 * @author RUKAYA
 */
public class Nodo {
    String nombre;
    Nodo siguiente;
    boolean es_sucursal;
    Lista paradas;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.siguiente = null;
        this.es_sucursal = false;
        this.paradas = new Lista();
    }
    
    
    
    
}
