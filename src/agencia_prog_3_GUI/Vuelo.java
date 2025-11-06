package agencia_prog_3_GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Vuelo extends DefaultTableModel{
	private List<DatosVuelos> vuelos; //crear clase de datosvuelos
	private final List<String> datos = Arrays.asList(
			"AEROLÍNEA", 
			"VUELO", 
			"ORIGEN", 
			"DESTINO", 
			"DURACIÓN", 
			"PRECIO", 
			"RESERVAS",
			"ASIENTOS LIBRES",
			"DISPONIBILIDAD", //Columna para la disponibilidad
			"RESERVAR");
			
	
	public Vuelo(List<DatosVuelos> vuelos) {
		this.vuelos = vuelos;
	}
	
	
	
}
