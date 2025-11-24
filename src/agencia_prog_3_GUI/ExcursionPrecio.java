package agencia_prog_3_GUI;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ExcursionPrecio extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	public ExcursionPrecio() {
		setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	@Override //
    protected void setValue(Object value) {
        if (value instanceof Number n) { //
        	setText(String.format("%.2f €", value)); 
        }else {
        	setText("");; 
        }
    }
}
