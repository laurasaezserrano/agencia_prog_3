package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class VentanaExcursiones extends JFrame{
	private static final long serialVersionUID = 1L;
	String[] tituloexcursion = {
		    "Cataratas del Niagara",      
		    "Ruta del cafe",       
		    "Teleferico de Lucerna",       
		    "Visita al Vaticano",        
		    "Visita al templo de Nikko",     
		    "Dia en Bali",       
		    "Subida al Empire State",     
		    "Visita a la Opera de Oslo",  
		    "Visita a Florencia"         
		};
	String[] descrip = {
	        "Increible visita a las Cataratas del Niagara",
	        "Gran ruta del cafe por Colombia",
	        "Descubre las vistas de Suiza desde el teleferico de Lucerna",
	        "Descubre los tesoros del Vaticano",
	        "Descubre el templo de Nikko en Japón",
	        "Disfruta de un maravilloso dia en la gran ciudad de Bali",
	        "Descubre las maravillosas vistas desde el Empire State",
	        "Visita la famosa Opera de Oslo",
	        "Visita la maravillosa ciudad de Florencia"
	    };
	
	double[] precio = {89, 55, 64, 49, 72, 95, 38, 41, 59};
	private JTextField campoFiltro;
    private JTextField campoPersonas;

    private JTable tabla;
    private TableRowSorter<TableModel> ordena;
    private ExcursionTabla modelo;
	
	public VentanaExcursiones() {
		setTitle("Busqueda de excursiones");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));
		
		JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
		mainpanel.setBackground(new Color(50, 150, 200));
		add(mainpanel, BorderLayout.CENTER); 

		
		
		JPanel panelBusqueda = configurarBusquedaExcursion();
		mainpanel.add(panelBusqueda, BorderLayout.NORTH);
		
		//tabla
		modelo = new ExcursionTabla(crearListaExcursiones());
		tabla = new StripedTable(modelo);
		tabla.setRowHeight(28);
		tabla.setFillsViewportHeight(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(230, 230, 230));
		
		ordena = new TableRowSorter<>(tabla.getModel());
		tabla.setRowSorter(ordena);
		
		JTableHeader cabecera = tabla.getTableHeader();
		cabecera.setReorderingAllowed(false);
		cabecera.setFont(cabecera.getFont().deriveFont(Font.BOLD));
		
		//que no se modifiquen los tamaños de los huecos de la tabla
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int[] ancho = {50, 220, 420, 90, 110};
		for (int i = 0; i < ancho.length; i++) {
            TableColumn col = tabla.getColumnModel().getColumn(i);
            col.setPreferredWidth(ancho[i]);
            col.setMinWidth(Math.min(ancho[i], 50));
        }
		
		//Renderer
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(center); 
        tabla.getColumnModel().getColumn(2).setCellRenderer(new TruncatingRenderer(100)); // Descripción truncada
        tabla.getColumnModel().getColumn(3).setCellRenderer(new ExcursionPrecio()); // Precio formateado

		
		//Boton reserva
        int columnareserva = 4;
        tabla.getColumnModel().getColumn(columnareserva).setCellRenderer(new ButtonRenderer("Reservar"));
        tabla.getColumnModel().getColumn(columnareserva).setCellEditor(new ButtonEditor(modelo, tabla, this::abrirReserva));

        JScrollPane sp = new JScrollPane(tabla);
        sp.setBorder(new LineBorder(Color.BLACK, 1));
        mainpanel.add(sp, BorderLayout.CENTER);
 
        //Boton de retorno a interfaz principal
        JButton botonInicio = new JButton("Atras"); //luego cambiarlo a un icono
        botonInicio.setBounds(0, 0, 10, 30);
        botonInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				VentanaInicio ventanainicio = new VentanaInicio();
				ventanainicio.setVisible(true);
			}
        
		});
        
	}
        private List<Excursion> crearListaExcursiones() {
            List<Excursion> out = new ArrayList<>();
            for (int i = 0; i < tituloexcursion.length; i++) {
                out.add(new Excursion(i + 1, tituloexcursion[i], descrip[i], precio[i]));
            }
            return out;
        }
        
        //panel de eleccion de numero de personas + filtro de busqueda
        public JPanel configurarBusquedaExcursion() {
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panel1.setBackground(new Color(230, 240, 255));
            panel1.add(new JLabel("Personas:"));
            campoPersonas = new JTextField("1", 6);
            panel1.add(campoPersonas);
            panel1.add(new JLabel("Filtro por título:"));
            campoFiltro = new JTextField(24);
            panel1.add(campoFiltro);
            JButton buscar = new JButton("Buscar");
            buscar.addActionListener(e -> aplicabusqueda());
			panel1.add(buscar);
			
            campoFiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() { 
            	//permite saber que texto hay en la busqueda que estamos haciendo
                private void go() {
                	aplicabusqueda(); 
                	}
                public void insertUpdate(javax.swing.event.DocumentEvent e) { //cuando escribe el usuario
                	go(); 
                }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { //cuando el usuario elimina lo escrito
                	go(); 
                }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                	go(); 
                }
            });
            return panel1;
        }
        
        private void aplicabusqueda() {
            String txt = campoFiltro.getText().trim(); //obtiene el texto que estamos escribiendo
            if (txt.isEmpty()) { //si esta vacio enseña todas las excursiones
            	ordena.setRowFilter(null); 
            } else { //sino, muestra las coincidencias del buscador con las que tenemos
            	ordena.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 1)); 
            }}
        
        
        //Resumen de la excursion seleccionada
        private void abrirReserva(Excursion ex) {
            int personas;
            try {
				personas = Integer.parseInt(campoPersonas.getText().trim());
				if (personas <= 0) throw new NumberFormatException();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Introduce un número de personas válido (>0).",
		                "Dato incorrecto", JOptionPane.WARNING_MESSAGE);
				return;
			}
            JDialog mensaje = new JDialog(this, "Reserva", true);
            mensaje.setLayout(new BorderLayout(10, 10));
            mensaje.setSize(420, 260);
            mensaje.setLocationRelativeTo(this);

            JTextArea informacionreserva = new JTextArea(
                "Has seleccionado:\n" +
                "• " + ex.getNombre() + "\n" +
                "• " + ex.getDescripcion() + "\n" +
                "• Precio por persona: " + String.format(Locale.US, "%.2f €", ex.getPrecio()) + "\n" +
                "• Personas: " + personas + "\n\n" +
                "Total: " + String.format(Locale.US, "%.2f €", ex.getPrecio() * personas)
            );
            informacionreserva.setEditable(false);
            informacionreserva.setLineWrap(true);
            informacionreserva.setWrapStyleWord(true);
            informacionreserva.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            JButton confirmar = new JButton("Confirmar reserva");
            confirmar.addActionListener(e -> {
                JOptionPane.showMessageDialog(mensaje, "¡Reserva confirmada!\n" + ex.getNombre());
                mensaje.dispose();
            });

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panel.add(confirmar);

            mensaje.add(new JScrollPane(informacionreserva), BorderLayout.CENTER);
            mensaje.add(panel, BorderLayout.SOUTH);
            mensaje.setVisible(true);
        }

        
      ///Crear clase aparte ///
     // Zebra-striping sin romper la selección
        static class StripedTable extends JTable {
			private static final long serialVersionUID = 1L;
			private final Color par = new Color(245, 250, 255);
            private final Color impar  = Color.WHITE;
            public StripedTable(TableModel m) { 
            	super(m); 
            	
            }
            
            @Override 
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) c.setBackground((row % 2 == 0) ? par : impar);
                return c;
            }
        }

        
        
        
      ///Crear clase aparte ///
     // Descripción limitada
        static class TruncatingRenderer extends DefaultTableCellRenderer {
        	private static final long serialVersionUID = 1L;
			private final int maxChars; //maximo de caracteres por fila
            public TruncatingRenderer(int maxChars) { 
        		this.maxChars = maxChars; 
        		
				}
            @Override protected void setValue(Object value) {
                String s = (value == null) ? "" : value.toString();
                setToolTipText(s);
                if (s.length() > maxChars) s = s.substring(0, maxChars - 1) + "…";
                super.setValue(s);
            }
        }
//        
//        
        
        
      ///Crear clase aparte ///
        static class ButtonRenderer extends JButton implements TableCellRenderer {
        	private static final long serialVersionUID = 1L;
          	public ButtonRenderer(String text) { setText(text); setOpaque(true); }
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                     boolean hasFocus, int row, int column) {
                setText(value == null ? "Reservar" : value.toString());
                setForeground(table.getForeground());
                setBackground(isSelected ? table.getSelectionBackground() : UIManager.getColor("Button.background"));
                return this;
            }
        }
        
        

        static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
			private static final long serialVersionUID = 1L;
			private final JButton button = new JButton("Reservar");
            private final ExcursionTabla model;
            private final JTable table;
            private final java.util.function.Consumer<Excursion> onClick;
            public ButtonEditor(ExcursionTabla model, JTable table, java.util.function.Consumer<Excursion> onClick) {
                this.model = model; this.table = table; this.onClick = onClick;
                button.addActionListener(this);
            }
            @Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                button.setText(value == null ? "Reservar" : value.toString());
                return button;
            }
            @Override public Object getCellEditorValue() { return "Reservar"; }
            @Override public void actionPerformed(ActionEvent e) {
                int viewRow = table.getEditingRow();
                int modelRow = table.convertRowIndexToModel(viewRow);
                Excursion ex = model.getAt(modelRow);
                onClick.accept(ex);
                fireEditingStopped();
            }
        }
        
        
        
        
        
        
        
		

 
	
	
	public static void main(String[] args) {
		VentanaExcursiones excurs = new VentanaExcursiones();
		excurs.setVisible(true);
	}
}
