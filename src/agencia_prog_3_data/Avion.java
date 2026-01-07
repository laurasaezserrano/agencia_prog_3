package agencia_prog_3_data;

import java.io.Serializable;

public class Avion implements Serializable {
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombre; 
    
    public Avion(String nombre) {
        this.nombre = nombre;
    }
    
    public Avion(String codigo, String nombre) {
        this.codigo = codigo;
    	this.nombre = nombre;

    }
    
    public Avion(String codigo, String nombre, int i) {
        this.codigo = codigo;
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
