/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.cobertura;

/**
 *
 * @author sebas
 */
public class Estacion {
    private String sucursal;
    private Boolean cubierto;
    private String nombre;

    public Estacion(String sucursal, Boolean cubierto, String nombre) {
        this.sucursal = sucursal;
        this.cubierto = cubierto;
        this.nombre = nombre;
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
