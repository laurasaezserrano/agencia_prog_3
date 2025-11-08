package agencia_prog_3_GUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatosVuelos implements Comparable<DatosVuelos>, Serializable {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private Aeropuertos origen;
	private Aeropuertos destino;
	private int duracionvuelo;
	private Aerolinea aerolinea;
	private Avion avion;
	private int asientos;
	private float precio;
	private List<ReservaVuelo> reservas;
	
	


	public DatosVuelos(String codigo, Aeropuertos origen, Aeropuertos destino, int duracionvuelo, Aerolinea aerolinea,
			Avion avion, int asientos, float precio, List<ReservaVuelo> reservas) {
		super();
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.duracionvuelo = duracionvuelo;
		this.aerolinea = aerolinea;
		this.avion = avion;
//		this.asientos = if (avion == null) {
//		    				this.asientos = 0;
//						} else {
//							this.asientos = avion.getNumeroasientos();
//						};
		this.precio = precio;
		this.reservas = new ArrayList<>();
	}

	
	public int asientosrestantes() {
		int asi = 0;
		for (ReservaVuelo r : reservas) {
			asi += r.getPasajeros().size();
		}
		return (asientos - asi);
	}



	public String getCodigo() {
		return codigo;
	}


	public Aeropuertos getOrigen() {
		return origen;
	}
	
	public Aeropuertos getDestino() {
		return destino;
	}

	public int getDuracionvuelo() {
		return duracionvuelo;
	}

	public Aerolinea getAerolinea() {
		return aerolinea;
	}

	public Avion getAvion() {
		return avion;
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

	public void setReservas(List<ReservaVuelo> reservas) {
		this.reservas = reservas;
	}

	//HASHCODE Y EQUALS Y EL TOSTRING -- FALTAN DE HACER

	@Override
	public int compareTo(DatosVuelos other) {
		return codigo.compareTo(other.codigo);
	}
}
