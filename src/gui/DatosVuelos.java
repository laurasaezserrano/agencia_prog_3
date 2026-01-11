package gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import domain.Aerolinea;
import domain.Aeropuerto;

public class DatosVuelos implements Comparable<DatosVuelos>, Serializable{
	private static final long serialVersionUID = 1L;
	private String codigo;
	private Aerolinea aerolinea;
	private Aeropuerto origen;
	private int duracionvuelo;
	private Aeropuerto destino;
	private String fecha;
    private String hora;
    private int asientos; // El campo se llama 'asientos'
    private float precio;
    private String descripcion;
	private List<ReservaVuelo> reservas;
	

	public DatosVuelos(
	    String codigo, 
	    String aerolineaNombre, 
	    String origenNombre, 
	    int duracionvuelo, 
	    String destinoNombre, 
	    String fecha, 
	    String hora, 
	    int asientos, 
	    float precio, 
	    String descripcion
	) {
        this.codigo = codigo;
        this.aerolinea = new Aerolinea(aerolineaNombre); 
        this.origen = new Aeropuerto(origenNombre);
        this.duracionvuelo = duracionvuelo;
        this.destino = new Aeropuerto(destinoNombre);
        
        // Campos que estaban sin inicializar:
        this.fecha = fecha; 
        this.hora = hora;
        this.asientos = asientos; // Se inicializa el campo 'asientos'
        this.precio = precio;
        this.descripcion = descripcion;
        this.reservas = new ArrayList<>(); // Inicializamos la lista para evitar NullPointerException
    }
    
 	
	public int asientosrestantes() {
		int asi = 0;
		if (reservas != null) {
			for (ReservaVuelo r : reservas) {
				// Se asume que getPasajeros() devuelve una lista
				asi += r.getPasajeros().size(); 
			}
		}
		return (asientos - asi); 
	}

	public String getCodigo() {
		return codigo;
	}


	public Aeropuerto getOrigen() {
		return origen;
	}
	
	public Aeropuerto getDestino() {
		return destino;
	}

	public int getDuracionvuelo() {
		return duracionvuelo;
	}

	public Aerolinea getAerolinea() {
		return aerolinea;
	}
	
	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	public int getAsientos() { 
		return asientos;
	}

	public float getPrecio() {
		return precio;
	}

	public List<ReservaVuelo> getReservas() {
		return reservas;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setReservas(List<ReservaVuelo> reservas) {
		this.reservas = reservas;
	}

	@Override
	public int compareTo(DatosVuelos o) {
		return codigo.compareTo(o.codigo);
	}
}