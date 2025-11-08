package agencia_prog_3_GUI;

import java.util.Locale;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ExcursionPrecio extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	public ExcursionPrecio() {
		setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	@Override //
    protected void setValue(Object value) {
        if (value instanceof Number n) setText(String.format(Locale.US, "%.2f €", n.doubleValue()));
        else super.setValue(value);
    }
}
