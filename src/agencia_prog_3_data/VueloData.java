package agencia_prog_3_data;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

// Clase para almacenar toda la información de un vuelo que se muestra en la tabla.
public class VueloData implements Serializable {
    private static final long serialVersionUID = 1L;

    // Campos primarios de la tabla Vuelo
    private int id;
    private String codigo;
    private String fecha; 
    private double duracion; // Almacenar en minutos/horas según la BD
    private double precio;

    // Campos relacionados (Claves Foráneas resueltas)
    // Usamos los objetos completos o solo los nombres para simplificar
    private Aerolinea aerolinea;
    private String nombreAvion;
    private String nombreOrigen;
    private String nombreDestino;
    
    // Campos de estado/interacción de la GUI
    private int asientosDisponibles; // Obtenido del Avion

    // CONSTRUCTOR (Ejemplo, puede ser adaptado a tu gusto)
    public VueloData(int id, String codigo, String fecha, double duracion, double precio,
            Aerolinea aerolinea, String nombreAvion, String nombreOrigen, String nombreDestino,
            int asientosDisponibles) {
        this.id = id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.duracion = duracion;
        this.precio = precio;
        this.aerolinea = aerolinea;
        this.nombreAvion = nombreAvion;
        this.nombreOrigen = nombreOrigen;
        this.nombreDestino = nombreDestino;
        this.asientosDisponibles = asientosDisponibles;
    }

    // GETTERS requeridos por Vuelo.java (el table model)
    
    // El table model busca getCodigo()
    public String getCodigo() {
        return codigo;
    }
    
    // El table model busca getAerolinea() - Devuelve un objeto Aerolinea
    public Aerolinea getAerolinea() {
        return aerolinea;
    }
    
    // El table model busca getOrigen() y getDestino()
    // Si la BD solo devuelve el nombre del aeropuerto, usa ese campo
    public String getOrigen() {
        return nombreOrigen;
    }
    
    public String getDestino() {
        return nombreDestino;
    }
    
    // El table model busca getDuracionvuelo()
    public double getDuracionvuelo() {
        return duracion; // Devolver duración en la unidad que uses (p.ej., minutos)
    }

    // El table model busca getPrecio()
    public double getPrecio() {
        return precio;
    }

    // El table model busca getAsientos() y getReservas()
    public int getAsientos() {
        return asientosDisponibles;
    }
}