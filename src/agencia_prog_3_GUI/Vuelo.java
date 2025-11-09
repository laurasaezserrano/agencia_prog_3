package agencia_prog_3_GUI;

import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Vuelo extends DefaultTableModel{
	private static final long serialVersionUID = 1L;
	private List<DatosVuelos> vuelos; //guarda una lista con los datos del vuelo
	private final List<String> datos = Arrays.asList( //definimos los nombres de las columnas
			"CODIGO",
			"AEROLÍNEA", 
			"VUELO", 
			"ORIGEN", 
			"DESTINO", 
			"DURACIÓN", 
			"PRECIO", 
			"ASIENTOS LIBRES",
			"RESERVAS",
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
	public boolean isCellEditable(int fila, int columna) {
		return columna == 8; //solo la columna de reservas se puede editar
	}
	
	@Override
	public Class<?> getColumnClass(int columna) {
		switch (columna) {
        	case 5: { //precio
        		return Double.class;   
        	}
        	case 6: { //reservas
        		return Integer.class;  
        	}
        	case 7: { //asientos libres
        		return Integer.class;  
        	}
        	default: {
        		return String.class;
        	}
    }
	}
	
	//Para saber que tiene que devolver cada columna
	@Override
	public Object getValueAt(int fila, int columna) { 
		DatosVuelos vuelo = vuelos.get(fila);
		switch (columna) {
		case 0: {
			return vuelo.getCodigo();
			}
		case 1: {
			return vuelo.getAerolinea();
			}
		case 2: {
			return vuelo.getAvion();}
		case 3: {
			return vuelo.getOrigen();
				}
		case 4: {
			return vuelo.getDestino();
			}
		case 5: {
			return (vuelo.getDuracionvuelo()/60) + "h " + (vuelo.getDuracionvuelo()%60) + " min"; 
			}
		case 6: {
			return vuelo.getPrecio();
			}
		case 7: {
			return vuelo.getAsientos() - vuelo.getReservas().size() ;
			}
		case 8: {
			return vuelo.getReservas().size();
			}
		
		default: {
			return null;}
		}
	}
	
	public DatosVuelos getAt(int fila) {
		return vuelos.get(fila);
	}
	
}