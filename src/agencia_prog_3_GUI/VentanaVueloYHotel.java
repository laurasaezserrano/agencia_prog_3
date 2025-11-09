package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;

public class VentanaVueloYHotel extends JFrame{
	private JTextField titulo;
	private static final long serialVersionUID = 1L;
	private static final String CSV_PATH = "vuelosagencia_completo.csv";
	
	//datos prueba CAMBIAR POR LOS DEL CSV
    String[] origen  = {"Madrid","Madrid","Madrid","Madrid","Madrid","Madrid","Madrid","Madrid","Madrid"};
    String[] destino = {"Toronto","París","Zurich","Roma","Japon","Bangkok","Nueva York","Oslo","Florencia"};
    String[] fecha   = {"12/12/2025","15/12/2025","18/12/2025","20/12/2025","21/12/2025","26/12/2025","27/12/2025","28/12/2025","30/12/2025"};
    String[] hora    = {"09:35","14:10","08:20","07:10","06:30","16:45","13:05","10:25","11:50"};
    double[] precio  = {489,65,79,49,399,200,678,97,59};
    String[] descripcion = {
            "Vuelo a Toronto. Conexión directa y llegada por la mañana.",
            "Vuelo a París. Ideal para escapada de fin de semana.",
            "Vuelo a Zúrich. Paisajes alpinos.",
            "Vuelo a Roma. Arte y cultura.",
            "Vuelo a Tokio. Tradición y templos.",
            "Vuelo a BAngkok. Playas y arrozales.",
            "Vuelo a Nueva York. Rascacielos y Broadway.",
            "Vuelo a Oslo. Fiordos y arquitectura nórdica.",
            "Vuelo a Florencia. Renacimiento italiano."
    };
    private final String[] origenVuelos = {"Madrid"}; //de momento solo Madrid
    private JTextField campofiltro;
    //tabla
    private JTable tabla;
    private Vuelo modelo;
    private TableRowSorter<TableModel> ordenado;
     
	public VentanaVueloYHotel() {
		setTitle("Búsqueda de vuelos");
		setSize(1300, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
        mainpanel.setBackground(new Color(50, 150, 200));
        add(mainpanel, BorderLayout.CENTER);
        
        //panel busqueda
        JPanel panelBusqueda = configurarbusqueda();
        mainpanel.add(panelBusqueda, BorderLayout.NORTH);
        
        //titulo
        JTextField titulo = new JTextField("Búsqueda de Vuelos y Ofertas");
        titulo.setEditable(false);
        titulo.setHorizontalAlignment(JTextField.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        add(titulo, BorderLayout.SOUTH);
        
        //tabla
        modelo = new Vuelo(crearlistavuelos());
        tabla = new StripedTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230,230,230));
        
        ordenado = new TableRowSorter<>(tabla.getModel());
        tabla.setRowSorter(ordenado);
        
        //cabecera
        JTableHeader cabecera = tabla.getTableHeader();
        cabecera.setReorderingAllowed(false);
        cabecera.setFont(cabecera.getFont().deriveFont(Font.BOLD));
        
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        int[] ancho = {80,120,160,110,90,100,120,130};
        for (int i = 0; i < ancho.length; i++) {
            TableColumn col = tabla.getColumnModel().getColumn(i);
            col.setPreferredWidth(ancho[i]);
            col.setMinWidth(Math.min(ancho[i], 50));
        }
        
        //centrado
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        for (int c=0;c<tabla.getColumnCount();c++) {
            tabla.getColumnModel().getColumn(c).setCellRenderer(centro);
        }

        //precio
        tabla.getColumnModel().getColumn(6).setCellRenderer(new PrecioRenderer());

        

        tabla.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
            	if (e.getClickCount() == 2) {
                    int viewRow = tabla.getSelectedRow();
                    if (viewRow >= 0) {
                        int modelRow = tabla.convertRowIndexToModel(viewRow);
                        DatosVuelos v = modelo.getAt(modelRow);
                        mostrarDescripcion(v);
                    }
            	}
            }
        });

        
        //boton reserva
        int colReserva = 9;
        tabla.getColumnModel().getColumn(colReserva).setCellRenderer(new ButtonRenderer("Reservar"));
        tabla.getColumnModel().getColumn(colReserva).setCellEditor(new ButtonEditor(modelo, tabla, this::abrirreserva));

        JScrollPane sp = new JScrollPane(
                tabla,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        sp.setBorder(new LineBorder(Color.BLACK, 2));
        mainpanel.add(sp, BorderLayout.CENTER);

        
        //boton de atras
        JButton atras = new JButton("Atrás");
        atras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new VentanaInicio().setVisible(true);
				
			}
		});
        mainpanel.add(atras, BorderLayout.SOUTH);
    }

    //crearlistavuelos
    private List<DatosVuelos> crearlistavuelos() {
        List<DatosVuelos> salida = new ArrayList<>();
        salida.add(new DatosVuelos("IB1234", null, null, 155, null, null, 180, 489f, null));
        salida.add(new DatosVuelos("AF2201", null, null, 120, null, null, 180, 65f, null));
        salida.add(new DatosVuelos("LX3902", null, null, 140, null, null, 160, 79f, null));
        salida.add(new DatosVuelos("AZ1010", null, null, 135, null, null, 150, 49f, null));
        salida.add(new DatosVuelos("JL0080", null, null, 720, null, null, 280, 399f, null));
        salida.add(new DatosVuelos("TG0909", null, null, 780, null, null, 300, 200f, null));
        salida.add(new DatosVuelos("DL4501", null, null, 480, null, null, 240, 678f, null));
        salida.add(new DatosVuelos("DY3107", null, null, 210, null, null, 180, 97f, null));
        salida.add(new DatosVuelos("AZ2102", null, null, 155, null, null, 180, 59f, null));

        return salida;
    }

        
    private JPanel configurarbusqueda() {
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        panel1.setBackground(new Color(230,240,255));
        panel1.add(new JLabel("Buscar (origen/destino/fecha):"));
        campofiltro = new JTextField(24);
        panel1.add(campofiltro);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(e -> aplicarbusqueda());
        panel1.add(buscar);

        // reacciona al tecleo
        campofiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void go(){ aplicarbusqueda(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e){ go(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e){ go(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e){ go(); }
        });
        return panel1;
    }
        
    private void aplicarbusqueda() {
        String txt = campofiltro.getText().trim();
        if (txt.isEmpty()) {
            ordenado.setRowFilter(null);
        } else {
            //filtra por origen, destino, fecha y hora
            RowFilter<TableModel, Object> rf = RowFilter.orFilter(Arrays.asList(
                    RowFilter.regexFilter("(?i)"+Pattern.quote(txt), 1),
                    RowFilter.regexFilter("(?i)"+Pattern.quote(txt), 2),
                    RowFilter.regexFilter("(?i)"+Pattern.quote(txt), 3),
                    RowFilter.regexFilter("(?i)"+Pattern.quote(txt), 4)
            ));
            ordenado.setRowFilter(rf);
        }
    }
        
        
    private void mostrarDescripcion(DatosVuelos vuelo) {
        JDialog descripcion = new JDialog(this, "Información del vuelo", true);
        descripcion.setLayout(new BorderLayout(10,10));
        descripcion.setSize(520, 320);
        descripcion.setLocationRelativeTo(this);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        texto.setText(
            "ID: " + vuelo.getCodigo() + "\n" +
            "Origen: " + vuelo.getOrigen() + "\n" +
            "Aerolinea" + vuelo.getAerolinea() + "\n" +
            "Destino: " + vuelo.getDestino() + "\n" +
            "Duracion: " + vuelo.getDuracionvuelo() + "\n" + 
            "Asientos disponibles: " + vuelo.getAsientos() + "\n" +
            "Precio:" + vuelo.getPrecio() + "€ \n"
           
        );

        descripcion.add(new JScrollPane(texto), BorderLayout.CENTER);
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> descripcion.dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(cerrar);
        descripcion.add(south, BorderLayout.SOUTH);
        descripcion.setVisible(true);
    }

    //info de la reserva
    private void abrirreserva(DatosVuelos vuelo) {
        JDialog mensaje = new JDialog(this, "Reserva", true);
        mensaje.setLayout(new BorderLayout(10,10));
        mensaje.setSize(560, 320);
        mensaje.setLocationRelativeTo(this);

        JPanel resumen1 = new JPanel();
        resumen1.setLayout(new BoxLayout(resumen1, BoxLayout.Y_AXIS));
        resumen1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Has seleccionado:");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        resumen1.add(titulo);
        resumen1.add(Box.createVerticalStrut(6));

        JTextArea info = new JTextArea(
            "• " + vuelo.getOrigen() + " → " + vuelo.getDestino() + " (" + vuelo.getDuracionvuelo() + " " + vuelo.getAsientos() + ")\n" +
            "• Precio por persona: " + PrecioRenderer.format(vuelo.getPrecio()) + " €\n\n"
    
        );
        
        info.setEditable(false);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        JScrollPane spInfo = new JScrollPane(info,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(spInfo);
        resumen1.add(Box.createVerticalStrut(8));

        //numero de personas
        JPanel personas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        personas.add(new JLabel("Personas: "));
        JComboBox<Integer> combob = new JComboBox<>();
        for (int i=1;i<=10;i++) combob.addItem(i);
        personas.add(combob);
        personas.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(personas);

        //precio total
        JLabel total = new JLabel("Total: " + PrecioRenderer.format(vuelo.getPrecio()));
        total.setFont(new Font("Segoe UI", Font.BOLD, 12));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(total);

        combob.addActionListener(e -> {
            int n = (Integer) combob.getSelectedItem();
            total.setText("Total: " + PrecioRenderer.format(vuelo.getPrecio()*n));
        });

        //confirmar reserva
        JButton confirmar = new JButton("Confirmar reserva");
        confirmar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        confirmar.addActionListener(e -> {
            int n = (Integer) combob.getSelectedItem();
            JOptionPane.showMessageDialog(
                mensaje,
                "Vuelo: " + vuelo.getOrigen() + " → " + vuelo.getDestino() + "\n" +
                "Personas: " + n + "\n" +
                "Total: " + PrecioRenderer.format(vuelo.getPrecio()*n),
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
            );
            mensaje.dispose();
        });

        JPanel south = new JPanel(new BorderLayout());
        south.add(total, BorderLayout.WEST);
        south.add(confirmar, BorderLayout.EAST);

        mensaje.add(resumen1, BorderLayout.CENTER);
        mensaje.add(south, BorderLayout.SOUTH);
        mensaje.setVisible(true);
    }
        
        
    static class StripedTable extends JTable {
        private static final long serialVersionUID = 1L;
        private final Color par = new Color(245,250,255);
        private final Color impar = Color.WHITE;
        public StripedTable(TableModel m){ 
        	super(m); 	
        }
        
        @Override 
        public Component prepareRenderer(TableCellRenderer r, int fila, int columna){
            Component comp = super.prepareRenderer(r,fila,columna);
            if (comp instanceof JComponent) {
                ((JComponent) comp).setOpaque(true);
            }

            if (isRowSelected(fila)) {
            	comp.setBackground(getSelectionBackground());
            	comp.setForeground(getSelectionForeground());
            } else {
            	comp.setBackground((fila % 2 == 0) ? par : impar);
            	comp.setForeground(getForeground());
            }
            return comp;
        }
    }
        
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        public ButtonRenderer(String text){ 
        	setText(text); 
        	setOpaque(true); 
        }
        @Override 
        public Component getTableCellRendererComponent(JTable t,Object v,boolean s,boolean f,int r,int c){
            setText(v==null?getText():v.toString());
            setForeground(t.getForeground());
            setBackground(s? t.getSelectionBackground(): UIManager.getColor("Button.background"));
            return this;
        }
    }
      
      
    static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private static final long serialVersionUID = 1L;
        private final JButton button = new JButton("Reservar");
        private final Vuelo model;
        private final JTable table;
        private final Consumer<DatosVuelos> onClick;
        
        ButtonEditor(Vuelo model, JTable table, Consumer<DatosVuelos> onClick){
            this.model = model; 
            this.table = table; 
            this.onClick = onClick;
            button.addActionListener(this);
        }
        @Override 
        public Component getTableCellEditorComponent(JTable t,Object vuelo,boolean s,int fila,int columna){
            button.setText(vuelo ==null? "Reservar" : vuelo.toString());
            return button;
        }
        
        @Override 
        public Object getCellEditorValue(){ 
        	return "Reservar"; 
        }
        
        @Override 
        public void actionPerformed(ActionEvent e){
            int viewRow = table.getEditingRow();
            int modelRow = table.convertRowIndexToModel(viewRow);
            DatosVuelos vuelo = model.getAt(modelRow);
            onClick.accept(vuelo);
            fireEditingStopped();
        }
    }
    
    
    /**
     * PrecioRenderer creado con IAG
     */
    static class PrecioRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		private static final NumberFormat C = NumberFormat.getCurrencyInstance(new Locale("es","ES"));
        static String format(double d){ return C.format(d); }
        
        @Override 
        protected void setValue(Object value){
            if (value instanceof Number) {
            	setText(C.format(((Number)value).doubleValue()));
            }else {
            	setText(value==null? "": value.toString());
            }
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    

	public static void main(String[] args) {
		VentanaVueloYHotel vuelosyhotel = new VentanaVueloYHotel();
		vuelosyhotel.setVisible(true);
	}
}

