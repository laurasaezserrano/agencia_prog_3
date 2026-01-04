package agencia_prog_3_GUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import agencia_prog_3_data.Aerolinea;
import agencia_prog_3_data.Aeropuerto;

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
	

	// CONSTRUCTOR DE 10 PARÁMETROS (CORREGIDO PARA COINCIDIR CON LA LLAMADA DESDE VentanaVueloYHotel)
	// NOTA: Se asume que Aerolinea y Aeropuertos tienen un constructor que acepta un String.
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
        // CONVERSIÓN ASUMIDA: Crea un objeto Aerolinea/Aeropuertos a partir del nombre String.
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
    
    // El constructor anterior ha sido eliminado o comentado para evitar conflictos.
    // Si aún necesitas el constructor anterior, asegúrate de que tome 10 parámetros
    // o que el constructor que uses en VentanaVueloYHotel tenga la firma correcta.
	
	
	public int asientosrestantes() {
		int asi = 0;
		if (reservas != null) {
			for (ReservaVuelo r : reservas) {
				// Se asume que getPasajeros() devuelve una lista
				asi += r.getPasajeros().size(); 
			}
		}
		// CORRECCIÓN: Usa el campo 'asientos' en lugar de la variable 'plazas'
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

	// El campo Avion no está definido, se elimina su getter si existiera.
	/* public Avion getAvion() {
		return avion;
	} */

	// CORRECCIÓN: Usa el campo 'asientos' en lugar de la variable 'plazas'
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