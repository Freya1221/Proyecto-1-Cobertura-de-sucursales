/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura;

/**
 *
 * @author sebas
 */
public class Lista {

    Nodo head;
    int size;

    public Lista() {
        this.head = null;
        this.size = 0;
    }

    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean EsVacia() {
        return getHead() == null;
    }

    public Nodo AgregarAlFinal(String sucursal, String cubierto, String nombre) {
        Nodo nodo = new Nodo(sucursal, cubierto, nombre);
        if (EsVacia()) {
            setHead(nodo);
            return nodo;
        } else {
            Nodo pointer = getHead();
            while (pointer.getNext() != null) {
                pointer = pointer.getNext();
            }
            pointer.setNext(nodo);
            return nodo;

        }
    }

}
