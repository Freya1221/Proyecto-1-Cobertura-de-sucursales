/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura;

/**
 *
 * @author freya
 */
public class Nodo {
    private String sucursal;
    private String cubierto;
    private String nombre;
    private Nodo next;

    public Nodo(String sucursal, String cubierto, String nombre) {
        this.sucursal = sucursal;
        this.cubierto = cubierto;
        this.nombre = nombre;
        this.next = null;
   
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getCubierto() {
        return cubierto;
    }

    public void setCubierto(String cubierto) {
        this.cubierto = cubierto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Nodo getNext() {
        return next;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }
    
    
}
