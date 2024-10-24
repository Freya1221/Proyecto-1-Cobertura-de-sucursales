/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg1;

/**
 *
 * @author Freya
 */
public class Cola {
    Nodo cabeza;
    Nodo cola;
    
    public Cola(){
        this.cabeza = this.cola = null;
    }
    
    public void encolar(String estacion){
        if(this.cabeza == null){
            this.cabeza = this.cola = new Nodo(estacion);
        }else{
            this.cola.siguiente = new Nodo(estacion);
            this.cola = this.cola.siguiente;
        }
    }
    
    public Nodo desencolar(String estacion){
        if(this.cabeza == null){
            return null;
        }else{
            Nodo aux = this.cabeza;
            this.cabeza = this.cabeza.siguiente;
            return aux;
        }
    }
}
