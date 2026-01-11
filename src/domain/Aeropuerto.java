package domain;

import java.io.Serializable;

public class Aeropuerto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre; 
    
    public Aeropuerto(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    // Si necesitas el getter:
    public String getNombre() {
        return nombre;
    }
   
}
