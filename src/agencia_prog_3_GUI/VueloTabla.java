package agencia_prog_3_GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class VueloTabla extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private final List<String> columnas = Arrays.asList(
			"CÓDIGO",
	        "AEROLÍNEA",
	        "VUELO",
	        "ORIGEN",
	        "DESTINO",
	        "DURACIÓN",
	        "PRECIO",
	        "RESERVAS",
	        "ASIENTOS LIBRES",
	        "RESERVAS",
	        "RESERVAR"
	    );
	private List<DatosVuelos> data = new ArrayList<>();

    public VueloTabla(List<DatosVuelos> vuelos) {
        if (vuelos != null) {
        	this.data = new ArrayList<>(vuelos);
        }
    }

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.size();
	}
	
	@Override
	public String getColumnName(int columna) {
		return columnas.get(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
        	case 5: {
        		return Double.class; 
        	}
        	case 6: {
        		return Integer.class; 
        	}
        	case 7: {
        		return Integer.class; 
        	}
        	default: {
        		return String.class;
        	}
		}
	}
	
	
	@Override
	public Object getValueAt(int fila, int columna) {
		DatosVuelos vuel = data.get(fila);
		switch (columna) {
        	case 0: {
        		return vuel.getCodigo();
        	}
        	case 1:{
        		return vuel.getAerolinea();
        	}
        	case 3: {
        		return vuel.getOrigen();
        	}
        	case 4: {
        		return vuel.getDestino();
        	}
        	case 5: {
        		return (vuel.getDuracionvuelo()/60) + "h " + (vuel.getDuracionvuelo()%60) + " min";  
        	}
        	case 6: {
        		return vuel.getPrecio();
        	}
        	case 7: {
        		return vuel.getAsientos() -vuel.getReservas().size();
        	}
        	case 8: {
        		return vuel.getReservas().size();
        	}

        	default: {
        		return null;
        	}
		}
	}
	
	public DatosVuelos getAt(int row) { 
	    return data.get(row); 
	}

	public void setData(List<DatosVuelos> vuelos) {
		if (vuelos == null) {
		    this.data = new ArrayList<>();
		} else {
		    this.data = new ArrayList<>(vuelos);
		}
	}
}
	
	
	
	
