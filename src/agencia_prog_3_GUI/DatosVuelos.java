package agencia_prog_3_GUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatosVuelos implements Comparable<DatosVuelos>, Serializable{
	private static final long serialVersionUID = 1L;
	private String codigo;
	private Aeropuertos origen;
	private Aeropuertos destino;
	private int duracionvuelo;
	private Aerolinea aerolinea;
	private Avion avion;
	private int plazas;
	private float precio;
	private List<ReservaVuelo> reservas;
	

	public DatosVuelos(String codigo, Aeropuertos origen, Aeropuertos destino, int duracionvuelo, Aerolinea aerolinea,
			Avion avion, int plazas, float precio, List<ReservaVuelo> reservas) {
		super();
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.duracionvuelo = duracionvuelo;
		this.aerolinea = aerolinea;
		this.avion = avion;
		this.plazas = plazas;
		this.precio = precio;
		this.reservas = new ArrayList<>();
	}

	
	public DatosVuelos(String origen2, String destino2, String fechaSalida, String fechaRegreso, String aerolinea2,
			double precioVuelo, double precioHotel, int asientos) {
		// TODO Auto-generated constructor stub
	}


	public int asientosrestantes() {
		int asi = 0;
		for (ReservaVuelo r : reservas) {
			asi += r.getPasajeros().size();
		}
		return (plazas - asi);
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
		return plazas;
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


	@Override
	public int compareTo(DatosVuelos o) {
		return codigo.compareTo(o.codigo);
	}


	public String getFechaSalida() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getFechaRegreso() {
		// TODO Auto-generated method stub
		return null;
	}


	public double getPrecioVuelo() {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getPrecioHotel() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}