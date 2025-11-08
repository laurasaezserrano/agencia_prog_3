package agencia_prog_3_GUI;

import java.util.List;

import javax.swing.table.AbstractTableModel;

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
    public boolean isCellEditable(int row, int column) { 
    	return column == 4; 
    	
    }
    
    @Override 
    public Class<?> getColumnClass(int c) {
        return switch (c) {
            case 0 -> Integer.class;
            case 3 -> Double.class;
            default -> Object.class;
        };
    }
    
    @Override 
    public Object getValueAt(int r, int c) {
        Excursion e = excursion.get(r);
        return switch (c) {
            case 0 -> e.getId();
            case 1 -> e.getNombre();
            case 2 -> e.getDescripcion();
            case 3 -> e.getPrecio();
            case 4 -> "Reservar";
            default -> null;
        };
    }
    public Excursion getAt(int r) { return excursion.get(r); }

}
