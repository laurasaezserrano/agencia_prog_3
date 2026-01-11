package domain;

import java.io.Serializable;

public class Aerolinea implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre; // Campo para guardar el nombre de la aerolínea
    
    // CONSTRUCTOR REQUERIDO: Acepta el nombre como String
    public Aerolinea(String nombre) {
        this.nombre = nombre;
    }
    
    // Método para devolver el nombre (necesario para mostrarlo en la descripción)
    @Override
    public String toString() {
        return nombre;
    }
    
    // Si necesitas el getter:
    public String getNombre() {
        return nombre;
    }
    
}