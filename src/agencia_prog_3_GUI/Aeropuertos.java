package agencia_prog_3_GUI;

import java.io.Serializable;

public class Aeropuertos implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre; 
    
    public Aeropuertos(String nombre) {
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