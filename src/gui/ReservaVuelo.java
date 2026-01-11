package agencia_prog_3_GUI;

import java.io.Serializable;
import java.util.List;


public class ReservaVuelo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String codigoreserva;
	private DatosVuelos vuelo;
	private long fecha;
	private List<String> pasajeros;
	
	
	public ReservaVuelo(String codigoreserva, DatosVuelos vuelo, long fecha, List<String> pasajeros) {
		super();
		this.codigoreserva = codigoreserva;
		this.vuelo = vuelo;
		this.fecha = fecha;
		this.pasajeros = pasajeros;
	}


	public String getCodigoreserva() {
		return codigoreserva;
	}


	public DatosVuelos getVuelo() {
		return vuelo;
	}


	public long getFecha() {
		return fecha;
	}


	public List<String> getPasajeros() {
		return pasajeros;
	}

	
}
