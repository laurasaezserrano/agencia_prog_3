package domain;

import java.io.Serializable;

public class Aerolinea implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre; 
    
    public Aerolinea(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
}