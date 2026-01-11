package domain;

import java.io.Serializable;

public class Avion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombre; 
    private int duracion;
    
    public Avion(String nombre) {
        this.nombre = nombre;
    }
    
    public Avion(String codigo, String nombre) {
        this.codigo = codigo;
    	this.nombre = nombre;

    }
    
    public Avion(String codigo, String nombre, int duracion) {
        this.codigo = codigo;
    	this.nombre = nombre;
    	this.duracion = duracion;
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
