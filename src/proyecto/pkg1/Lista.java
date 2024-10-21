/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg1;

/**
 *
 * @author RUYAYA
 */
public class Lista {
    public Nodo primero;
    public Nodo ultimo;
    
    public Lista(){
        this.primero =  null;
        this.ultimo =  null;

    }
    
    public void insertar(String estacion){
        if(this.primero == null){
            this.primero = this.ultimo = new Nodo(estacion);
        }else{
            this.ultimo.siguiente = new Nodo(estacion);
            this.ultimo = this.ultimo.siguiente;
        }
    }
    
    public Nodo buscar(String estacion){
        if(this.primero == null){
            return null;
        }else{
            Nodo aux = this.primero;
            while(aux != null&& !aux.nombre.equals(estacion)){
                aux = aux.siguiente; 
            }
            return aux;
        }
    }
    
    public String verEstaciones(){
        if(this.primero == null){
            return  "";
        }else{
            Nodo aux = this.primero;
            String est = "";
            while(aux != null){
                est += aux.nombre + " - ";
                aux = aux.siguiente; 
            }
            return est;
        }
    }
}
