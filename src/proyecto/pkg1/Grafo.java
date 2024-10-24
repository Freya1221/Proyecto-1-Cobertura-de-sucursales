/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg1;

/**
 *
 * @author RUKAYA
 */
public class Grafo {

    public int max;
    public int act;
    public Nodo[] estaciones;
    public int t;

    public Grafo() {
        max = 250;
        act = 0;
        this.estaciones = new Nodo[max];
        for (int i = 0; i < max; i++) {
            this.estaciones[i] = new Nodo("");
        }
        t = 3;
    }

    public void agregarEstacion(String nombre) {
        for (int i = 0; i < max; i++) {
            if (this.estaciones[i].nombre.equals("")) {
                this.estaciones[i].nombre = nombre;
                break;
            }
        }
    }

    public Nodo buscarEstacion(String nombre) {
        for (int i = 0; i < max; i++) {
            if (this.estaciones[i].nombre.equals(nombre)) {
                return this.estaciones[i];
            }
        }
        return null;
    }

    public void agregarRelacion(String estacionA, String estacionB) {
        Nodo estA = this.buscarEstacion(estacionA);
        Nodo estB = this.buscarEstacion(estacionB);

        if (estA != estB && estA != null && estB != null) {
            estA.paradas.insertar(estacionB);
            estB.paradas.insertar(estacionA);
        }
    }

    public void añadirSucursal(String nombre) {
        this.buscarEstacion(nombre).es_sucursal = true;
    }

    //procedimiento recursivo
    public void recorrerProfundidad(int v, boolean[] visitados, int contador) {
//se marca el vértice v como visitado
        visitados[v] = true;
//el tratamiento del vértice consiste únicamente en imprimirlo en pantalla
        System.out.println(v);

        if (contador != t) {
//se examinan los vértices adyacentes a v para continuar el recorrido
            for (int i = 0; i < this.max; i++) {
                if ((v != i) && (!visitados[i]) && this.estaciones[i].paradas.buscar(this.estaciones[v].nombre) != null) {
                    recorrerProfundidad(i, visitados, contador + 1);
                }
            }
        }
    }
//procedimiento no recursivo

    public void profundidad() {
        boolean visitados[] = new boolean[this.max];

        for (int i = 0; i < this.max; i++) //inicializar vector con campos false
        {
            visitados[i] = false;
        }
        for (int i = 0; i < this.max; i++) { //Relanza el recorrido en cada
            if (!visitados[i]) //vértice visitado
            {
                recorrerProfundidad(i, visitados, 0);
            }
        }
    }
    
    public boolean existeArista(int i, int v){
        if(this.estaciones[i].paradas.buscar(this.estaciones[v].nombre) != null){
            return true;
        }
        return false;
    }

    public void amplitud() {
        Cola cola = new Cola();
        boolean visitados[] = new boolean[this.max];
        int v; //vértice actual
//Se inicializa el vector visitados [] a false
        for (int i = 0; i < this.max; i++) {
            visitados[i] = false;
        }
//El recorrido en amplitud se inicia en cada vértice no visitado
        for (int i = 0; i < this.max; i++) {
//se pone en la cola el vértide de partida y se marca como visitado
            if (!visitados[i]) {
                cola.encolar(this.estaciones[i].nombre);
                visitados[i] = true;
                while (cola.cabeza != null) {
                    String des = cola.desencolar().nombre; //desencolar y tratar el vértice
                    v = -1;
                    for (int j = 0; j < this.max; j++) {
                        if(this.estaciones[i].nombre.equals(des)){
                            v = i;
                        }
                    }
                    System.out.println(v);
//y encolo los nodos adyacentes a v.
                    for (int j = 0; j < this.max; j++) {
                        if ((v != j) && this.existeArista(v, j) && (!visitados[j]))  {
                            cola.encolar(this.estaciones[j].nombre);
                            visitados[j] = true;
                        }
                    }
                }
            }
        }
    }
}
