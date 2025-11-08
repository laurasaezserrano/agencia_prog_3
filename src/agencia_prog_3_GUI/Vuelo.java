package agencia_prog_3_GUI;

import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Vuelo extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	private List<DatosVuelos> vuelos; //guarda una lista con los datos del vuelo
	private final List<String> datos = Arrays.asList( //definimos los nombres de las columnas
			"AEROLÍNEA", 
			"VUELO", 
			"ORIGEN", 
			"DESTINO", 
			"DURACIÓN", 
			"PRECIO", 
			"RESERVAS",
			"ASIENTOS LIBRES",
			"DISPONIBILIDAD", 
			"RESERVAR");
			
	
	public Vuelo(List<DatosVuelos> vuelos) {
		this.vuelos = vuelos;
	}
	
	
	@Override
	public int getRowCount() {
		if (vuelos == null) {
			return 0;
		} else {
			return vuelos.size();
		}
	}
	
	@Override
	public int getColumnCount() {
		return datos.size();
	}
	
	@Override
	public String getColumnName(int columna) {
		return datos.get(columna);
	}
	
	@Override
	public Object getValueAt(int fila, int columna) {
		DatosVuelos vuelo = vuelos.get(fila);
		switch (columna) {
		case 0: {
			return vuelo.getAerolinea();}
		case 1: {
			return vuelo.getCodigo();}
		case 2: {
			return vuelo.getOrigen();}
		case 3: {
			return vuelo.getDestino();}
		case 4: {
			return vuelo.getDuracionvuelo();}
		case 5: {
			return vuelo.getPrecio();}
		case 6: {
			return vuelo.getReservas();}
		case 7: {
			return vuelo.getAsientos();}
		case 8: {
			return "Reservar";}
		default: {
			return null;}
		}
	}
	
}
