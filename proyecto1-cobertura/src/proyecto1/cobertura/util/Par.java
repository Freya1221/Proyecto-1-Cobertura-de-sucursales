/*
 * Clase Par para poder representar clave-valor y poder utilizar Map
 * 
 */
package proyecto1.cobertura.util;

/**
 *
 * @author sebas
 */

public class Par {
    private Estacion clave;
    private Integer valor;

    public Par(Estacion clave, Integer valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public Estacion getClave() {
        return clave;
    }

    public void setClave(Estacion clave) {
        this.clave = clave;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}