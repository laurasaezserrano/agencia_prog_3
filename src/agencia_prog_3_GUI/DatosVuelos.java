package agencia_prog_3_GUI;

import java.io.Serializable;
import java.util.List;

public class DatosVuelos implements Comparable<DatosVuelos>, Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private Aeropuertos origin;
	private Aeropuertos destination;
	private Aerolinea airline;
	private Avion plane;
	private List<ReservaVuelo> reservations;
	private int duration;
	private int seats;
	private float price;
	
	
	
	@Override
	public int compareTo(DatosVuelos o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
