/**
 * Clase Estación para trabajar con cada estación de ambos sistemas
 * 
 * @author Sebastián Arriaga
 */

package proyecto1.cobertura.util;


public class Estacion {
    private String sucursal;
    private Boolean cubierto;
    private String nombre;

    public Estacion(String sucursal, String nombre) {
        this.sucursal = sucursal;
        this.cubierto = false;
        this.nombre = nombre;
    }

    public Estacion(String sucursal) {
        this.sucursal = sucursal;
    }
    
    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Boolean getCubierto() {
        return cubierto;
    }

    public void setCubierto(Boolean cubierto) {
        this.cubierto = cubierto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
