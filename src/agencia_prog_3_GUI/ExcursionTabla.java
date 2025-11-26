package agencia_prog_3_GUI;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import agencia_prog_3_data.Excursion;

public class ExcursionTabla extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private final String[] columnas = {"ID", "Título", "Descripción", "Precio", "Reservar"};
    private final List<Excursion> excursion;
    
    public ExcursionTabla(List<Excursion> exc) { 
    	this.excursion = exc; 
    	
    }
    @Override 
    public int getRowCount() {
    	return excursion.size(); 
    	
    }
    
    @Override 
    public int getColumnCount() { 
    	return columnas.length; 
    	
    }
    
    @Override 
    public String getColumnName(int c) {
    	return columnas[c]; 
    	
    }
    
    @Override 
    public boolean isCellEditable(int fila, int columna) { 
    	return columna == 4; 
    	
    }
    
    @Override 
    public Class<?> getColumnClass(int columna) {
        switch (columna) {
            case 0: {
            	return Integer.class;
            }
            case 3: {
            	return Double.class;
            }
            default: {
            	return Object.class;
            }
        }
    }
    
    @Override 
    public Object getValueAt(int fila, int columna) {
        Excursion exc = excursion.get(fila); //obtiene la excursion
        switch (columna) {
            case 0: {
            	return exc.getId();
            }
            case 1: {
            	return exc.getNombre();
            }
            case 2: {
            	return exc.getDescripcion();
            }
            case 3: {
            	return exc.getPrecio();
            }
            case 4: {
            	return "Reservar";
            }
            default: {
            	return null;
            }
        }
    }
    
    
    public Excursion getAt(int fila) { 
    //devuelve la excursion que esta en la fila indicada
    	return excursion.get(fila); 
    	}

}
