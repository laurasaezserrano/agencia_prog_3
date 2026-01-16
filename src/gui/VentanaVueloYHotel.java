package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;

public class VentanaVueloYHotel extends JFrame {
    private JTextField titulo;
    private static final long serialVersionUID = 1L;
    
    private final String[] codigos = {"IB1234", "AF2201", "LX3902", "AZ1010", "JL0080", "TG0909", "DL4501", "DY3107", "AZ2102", "IB1234", "IB1234", "IB1234", "IB1234", "IB1234", "IB1234", "IB1234", "IB1234"};
    private final String[] aerolineas = {"Iberia", "Air France", "Swiss Air", "Alitalia", "JAL", "Thai Airways", "Delta", "Norwegian", "Alitalia", "Iberia", "Iberia", "Iberia", "Iberia", "Iberia", "Iberia", "Iberia", "Iberia"};
    private final int[] duracionMinutos = {155, 120, 140, 135, 720, 780, 480, 210, 155, 155, 120, 140, 135, 135, 720, 780, 480};
    private final int[] asientosLibres = {180, 180, 160, 150, 280, 300, 240, 180, 180, 180, 180, 160, 150, 280, 300, 240, 180};
    
    
    String[] origen = {"Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid", "Madrid"};
    String[] destino = {"Toronto", "París", "Zurich", "Roma", "Tokio", "Bangkok", "Nueva York", "Oslo", "Florencia", "Bristol", "Bogota", "Cracovia", "Londres", "Zurich", "Roma", "Tokio", "Bangkok"};
    String[] fecha = {"12/12/2025", "15/12/2025", "18/12/2025", "20/12/2025", "21/12/2025", "26/12/2025", "27/12/2025", "28/12/2025", "30/12/2025", "12/12/2025", "15/12/2025", "18/12/2025", "20/12/2025", "21/12/2025", "26/12/2025", "27/12/2025", "28/12/2025", "30/12/2025"};
    String[] hora = {"09:35", "14:10", "08:20", "07:10", "06:30", "16:45", "13:05", "10:25", "11:50", "09:35", "14:10", "08:20", "07:10", "06:30", "16:45", "13:05", "10:25"};
    double[] precio = {489, 65, 79, 49, 399, 200, 678, 97, 59, 489, 65, 79, 49, 49, 399, 200, 678};
    String[] descripcion = {
        "Vuelo a Toronto. Conexión directa y llegada por la mañana.",
        "Vuelo a París. Ideal para escapada de fin de semana.",
        "Vuelo a Zúrich. Paisajes alpinos.",
        "Vuelo a Roma. Arte y cultura.",
        "Vuelo a Tokio. Tradición y templos.",
        "Vuelo a Bangkok. Playas y arrozales.",
        "Vuelo a Nueva York. Rascacielos y Broadway.",
        "Vuelo a Oslo. Fiordos y arquitectura nórdica.",
        "Vuelo a Florencia. Renacimiento italiano.",
        "Vuelo a Bristol.",
        "Vuelo a Bogota. Grandes paisajes y buen cafe.",
        "Vuelo a Cracovia.",
        "Vuelo a Londres.",
        "Vuelo a Zúrich. Paisajes alpinos.",
        "Vuelo a Roma. Arte y cultura.",
        "Vuelo a Tokio. Tradición y templos.",
        "Vuelo a Bangkok. Playas y arrozales."
      
    };
    
    private JTextField campofiltro;
    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> ordenado;
    private List<DatosVuelos> vuelos;
    private String textoBusqueda = "";
     
    public VentanaVueloYHotel() {
    	setTitle("Búsqueda de vuelos");
        setSize(1300, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
        mainpanel.setBackground(new Color(50, 150, 200));
        add(mainpanel, BorderLayout.CENTER);
        
        
        
        //titulo
        titulo = new JTextField("Búsqueda de Vuelos y Ofertas");
        titulo.setEditable(false);
        titulo.setHorizontalAlignment(JTextField.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        add(titulo, BorderLayout.NORTH);
        
      //panel busqueda
        JPanel panelBusqueda = configurarbusqueda();
        mainpanel.add(panelBusqueda, BorderLayout.NORTH);
        
        //tabla
        vuelos = crearlistavuelos();
        modelo = crearModelo();
        tabla = new StripedTable(modelo);
        tabla.setRowHeight(35);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230, 230, 230));
        
        ordenado = new TableRowSorter<>(tabla.getModel());
        tabla.setRowSorter(ordenado);
        
        //cabecera
        JTableHeader cabecera = tabla.getTableHeader();
        cabecera.setReorderingAllowed(false);
        cabecera.setFont(cabecera.getFont().deriveFont(Font.BOLD));
        
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        int[] ancho = {80, 120, 80, 110, 90, 100, 120, 80, 100, 100};
        for (int i = 0; i < ancho.length && i < tabla.getColumnCount(); i++) {
            TableColumn col = tabla.getColumnModel().getColumn(i);
            col.setPreferredWidth(ancho[i]);
            col.setMinWidth(Math.min(ancho[i], 50));
        }
        
        HighlightRenderer highlightRenderer = new HighlightRenderer();
        for (int c = 0; c < tabla.getColumnCount(); c++) {
            if (c != 6 && c != 9) { // Excepto precio y botón reservar
                tabla.getColumnModel().getColumn(c).setCellRenderer(highlightRenderer);
            }
        }
        
        tabla.getColumnModel().getColumn(6).setCellRenderer(new PrecioRenderer());

        tabla.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int viewRow = tabla.getSelectedRow();
                    if (viewRow >= 0) {
                        int modelRow = tabla.convertRowIndexToModel(viewRow);
                        if (modelRow < vuelos.size()) {
                            DatosVuelos v = vuelos.get(modelRow);
                            mostrarDescripcion(v);
                        }
                    }
                }
            }
        });

        int colReserva = 9;
        tabla.getColumnModel().getColumn(colReserva).setCellRenderer(new ButtonRenderer("Reservar"));
        tabla.getColumnModel().getColumn(colReserva).setCellEditor(new ButtonEditor(vuelos, tabla, this::abrirreserva));

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
        
        cerrarventana(campofiltro, tabla, atras);
    }
    
    private void cerrarventana(JTextField campofiltro, JTable tabla, JButton atras) {
    	KeyAdapter listener = new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
    				dispose();
    			}
    		}
		};
		this.addKeyListener(listener);
		setFocusable(true);
		campofiltro.addKeyListener(listener);
		tabla.addKeyListener(listener);
		atras.addKeyListener(listener);
    }

    private DefaultTableModel crearModelo() {
        String[] columnas = {"Código", "Aerolínea", "Origen", "Destino", "Fecha", "Hora", "Precio", "Asientos", "Duración", "Reservar"};
        DefaultTableModel m = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Solo la columna Reservar es editable
            }
        };
        
        for (DatosVuelos v : vuelos) {
            m.addRow(new Object[]{
                v.getCodigo(),
                v.getAerolinea(),
                v.getOrigen(),
                v.getDestino(),
                v.getFecha(),
                v.getHora(),
                v.getPrecio(),
                v.getAsientos(),
                v.getDuracionvuelo(),
                "Reservar"
            });
        }
        return m;
    }
    
    private List<DatosVuelos> crearlistavuelos() {
        List<DatosVuelos> salida = new ArrayList<>();
        
        for (int i = 0; i < codigos.length; i++) {
            salida.add(new DatosVuelos(
                codigos[i],
                aerolineas[i],
                origen[i],
                duracionMinutos[i],
                destino[i],
                fecha[i],
                hora[i],
                asientosLibres[i],
                (float) precio[i],
                descripcion[i]
            ));
        }
        return salida;
    }

    private JPanel configurarbusqueda() {
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel1.setBackground(new Color(230, 240, 255));
        panel1.add(new JLabel("Buscar (origen/destino/fecha):"));
        campofiltro = new JTextField(24);
        panel1.add(campofiltro);
        
        JButton buscarItinerario = new JButton("Buscar itinerario");
        buscarItinerario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				accionGenerarItinerario();
				
			}
		});
        panel1.add(buscarItinerario);

        campofiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { 
            	aplicarbusqueda();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
            	aplicarbusqueda(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
            	aplicarbusqueda(); 
            }
        });
        return panel1;
    }
        
    private void accionGenerarItinerario() {
		VentanaGeneradorItinerarios ventana = new VentanaGeneradorItinerarios(vuelos);
		ventana.setVisible(true);
		this.dispose();
	}

	private void aplicarbusqueda() {
        String txt = campofiltro.getText().trim();
        this.textoBusqueda = txt;
        
        if (txt.isEmpty()) {
            ordenado.setRowFilter(null);
        } else {
            RowFilter<TableModel, Object> rf = RowFilter.orFilter(Arrays.asList(
                RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 0), // Código
                RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 1), // Aerolínea
                RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 2), // Origen
                RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 3), // Destino
                RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 4)  // Fecha
            ));
            ordenado.setRowFilter(rf);
        }
        
        tabla.repaint();
        
    }
        
    private void mostrarDescripcion(DatosVuelos vuelo) {
        JDialog descripcion = new JDialog(this, "Información del vuelo", true);
        descripcion.setLayout(new BorderLayout(10, 10));
        descripcion.setSize(520, 320);
        descripcion.setLocationRelativeTo(this);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        String duracionFormateada = formatoDuracion(vuelo.getDuracionvuelo());
        
        texto.setText(
            "ID: " + vuelo.getCodigo() + "\n" +
            "Origen: " + vuelo.getOrigen() + "\n" +
            "Aerolínea: " + vuelo.getAerolinea() + "\n" +
            "Destino: " + vuelo.getDestino() + "\n" +
            "Duración: " + duracionFormateada + "\n" +
            "Asientos disponibles: " + vuelo.getAsientos() + "\n" +
            "Precio: " + PrecioRenderer.format(vuelo.getPrecio()) + " €\n"
        );

        descripcion.add(new JScrollPane(texto), BorderLayout.CENTER);
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> descripcion.dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(cerrar);
        descripcion.add(south, BorderLayout.SOUTH);
        descripcion.setVisible(true);
    }
    
    private String formatoDuracion(int minutos) {
        if (minutos <= 0) return "N/A";
        int horas = minutos / 60;
        int minRestantes = minutos % 60;
        return String.format("%d h %02d min", horas, minRestantes);
    }
    
    private void abrirreserva(DatosVuelos vuelo) {
        JDialog mensaje = new JDialog(this, "Realizar Reserva", true);
        mensaje.setLayout(new BorderLayout(10, 10));
        mensaje.setSize(600, 450);
        mensaje.setLocationRelativeTo(this);

        JPanel resumen1 = new JPanel();
        resumen1.setLayout(new BoxLayout(resumen1, BoxLayout.Y_AXIS));
        resumen1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titulo = new JLabel("Resumen del Vuelo Seleccionado");
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        resumen1.add(titulo);
        resumen1.add(Box.createVerticalStrut(8));

        JTextArea info = new JTextArea(
            "• Vuelo: " + vuelo.getOrigen() + " → " + vuelo.getDestino() + "\n" +
            "• Precio por persona: " + PrecioRenderer.format(vuelo.getPrecio()) + " €\n"
        );
        
        info.setEditable(false);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setBackground(new Color(245, 245, 245));
        info.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        JScrollPane spInfo = new JScrollPane(info,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        spInfo.setPreferredSize(new Dimension(500, 50));
        resumen1.add(spInfo);
        resumen1.add(Box.createVerticalStrut(15));
        
        JLabel tituloDatos = new JLabel("Datos del Cliente y Fechas");
        tituloDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
        tituloDatos.setFont(tituloDatos.getFont().deriveFont(Font.BOLD, 14f));
        resumen1.add(tituloDatos);
        resumen1.add(Box.createVerticalStrut(10));
        
        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelNombre.add(new JLabel("Nombre Completo:"));
        JTextField campoNombre = new JTextField(25);
        panelNombre.add(campoNombre);
        panelNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(panelNombre);
        resumen1.add(Box.createVerticalStrut(5));

        JPanel panelIda = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelIda.add(new JLabel("Fecha de Ida:      "));
        JTextField campoFechaIda = new JTextField(vuelo.getFecha(), 10);
        campoFechaIda.setEditable(false);
        panelIda.add(campoFechaIda);
        panelIda.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(panelIda);
        resumen1.add(Box.createVerticalStrut(5));

        JPanel panelRegreso = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelRegreso.add(new JLabel("Fecha de Regreso:"));
        JTextField campoRegreso = new JTextField("", 10);
        campoRegreso.setEditable(false);
        panelRegreso.add(campoRegreso);
        panelRegreso.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(panelRegreso);
        resumen1.add(Box.createVerticalStrut(5));

        JPanel personas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        personas.add(new JLabel("Número de Personas: "));
        JComboBox<Integer> combob = new JComboBox<>();
        for (int i = 1; i <= Math.min(10, vuelo.getAsientos()); i++) combob.addItem(i);
        personas.add(combob);
        personas.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(personas);
        resumen1.add(Box.createVerticalStrut(15));

        JLabel total = new JLabel("TOTAL A PAGAR: " + PrecioRenderer.format(vuelo.getPrecio()));
        total.setFont(new Font("Segoe UI", Font.BOLD, 16));
        total.setForeground(new Color(0, 100, 0));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumen1.add(total);
        resumen1.add(Box.createVerticalStrut(10));

        combob.addActionListener(e -> {
            int n = (Integer) combob.getSelectedItem();
            total.setText("TOTAL A PAGAR: " + PrecioRenderer.format(vuelo.getPrecio() * n));
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> mensaje.dispose());
        south.add(cancelar);
        
        JButton confirmar = new JButton("Confirmar Reserva");
        confirmar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        confirmar.setBackground(new Color(0, 120, 215));
        confirmar.setForeground(Color.WHITE);
        confirmar.setOpaque(true);
        confirmar.setBorderPainted(false);
        
        confirmar.addActionListener(e -> {
            String nombreCliente = campoNombre.getText().trim();
            String fechaIda = campoFechaIda.getText().trim();
            String fechaRegreso = campoRegreso.getText().trim();
            int numPersonas = (Integer) combob.getSelectedItem();
            double precioTotal = vuelo.getPrecio() * numPersonas;
            
            Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
            
            if (nombreCliente.isEmpty() || !datePattern.matcher(fechaIda).matches() || !datePattern.matcher(fechaRegreso).matches()) {
                JOptionPane.showMessageDialog(
                    mensaje,
                    "Por favor, complete su nombre y asegúrese de que ambas fechas usen el formato dd/mm/aaaa.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                mensaje,
                "RESERVA COMPLETADA CON ÉXITO\n" +
                "--------------------------------------------------\n" +
                "Cliente: " + nombreCliente + "\n" +
                "Vuelo: " + vuelo.getCodigo() + " (" + vuelo.getOrigen() + " → " + vuelo.getDestino() + ")\n" +
                "Fecha de Ida: " + fechaIda + "\n" +
                "Fecha de Regreso (Hotel): " + fechaRegreso + "\n" +
                "Personas: " + numPersonas + "\n" +
                "Total: " + PrecioRenderer.format(precioTotal) + " €",
                "¡Reserva Confirmada!",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            mensaje.dispose();
        });

        south.add(confirmar);

        mensaje.add(resumen1, BorderLayout.CENTER);
        mensaje.add(south, BorderLayout.SOUTH);
        mensaje.setVisible(true);
    }

    class HighlightRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setHorizontalAlignment(SwingConstants.CENTER);
                
                String cellText = value == null ? "" : value.toString();
                
                //ayudado por IAG para el matcher (método más sencillo) 
                // Si hay texto de búsqueda y no está vacío, resaltar
                if (!textoBusqueda.isEmpty() && cellText != null) {
                    Pattern pattern = Pattern.compile(Pattern.quote(textoBusqueda), Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(cellText);
                    
                    if (matcher.find()) {
                        // Construir HTML con resaltado
                        StringBuilder html = new StringBuilder("<html>");
                        int lastEnd = 0;
                        
                        matcher.reset();
                        while (matcher.find()) {
                            // Añadir texto antes de la coincidencia
                            html.append(cellText.substring(lastEnd, matcher.start()));
                            // Añadir coincidencia resaltada
                            html.append("<span style='background-color: yellow; color: black;'>");
                            html.append(cellText.substring(matcher.start(), matcher.end()));
                            html.append("</span>");
                            lastEnd = matcher.end();
                        }
                        // Añadir texto restante
                        html.append(cellText.substring(lastEnd));
                        html.append("</html>");
                        
                        label.setText(html.toString());
                    } else {
                        label.setText(cellText);
                    }
                } else {
                    label.setText(cellText);
                }
            }
            
            return c;
        }
    }
   
    static class StripedTable extends JTable {
        private static final long serialVersionUID = 1L;
        private final Color par = new Color(245, 250, 255);
        private final Color impar = Color.WHITE;
        
        public StripedTable(TableModel m) {
            super(m);
        }
        
        @Override 
        public Component prepareRenderer(TableCellRenderer r, int fila, int columna) {
        	
            Component comp = super.prepareRenderer(r, fila, columna);
            
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
        
        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
        }
        
        @Override 
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            setText(v == null ? getText() : v.toString());
            setForeground(t.getForeground());
            setBackground(s ? t.getSelectionBackground() : UIManager.getColor("Button.background"));
            return this;
        }
    }
      
    static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    	
        private static final long serialVersionUID = 1L;
        private final JButton button = new JButton("Reservar");
        private final List<DatosVuelos> vuelos;
        private final JTable table;
        private final java.util.function.Consumer<DatosVuelos> onClick;
        ButtonEditor(List<DatosVuelos> vuelos, JTable table, java.util.function.Consumer<DatosVuelos> onClick) {
            this.vuelos = vuelos;
            this.table = table;
            this.onClick = onClick;
            button.addActionListener(this);
        }
        
        @Override 
        public Component getTableCellEditorComponent(JTable t, Object vuelo, boolean s, int fila, int columna) {
            button.setText(vuelo == null ? "Reservar" : vuelo.toString());
            return button;
        }
        
        @Override 
        public Object getCellEditorValue() {
            return "Reservar";
        }
        
        @Override 
        public void actionPerformed(ActionEvent e) {
            int viewRow = table.getEditingRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                if (modelRow < vuelos.size()) {
                    DatosVuelos vuelo = vuelos.get(modelRow);
                    onClick.accept(vuelo);
                }
            }
            fireEditingStopped();
        }
    }
    
    static class PrecioRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;
        @SuppressWarnings("deprecation")
		private static final NumberFormat C = NumberFormat.getCurrencyInstance(new Locale("es", "ES")); //generado por IAG
        
        static String format(double d) {
            return C.format(d);
        }
        
        @Override 
        protected void setValue(Object value) {
            if (value instanceof Number) {
                setText(C.format(((Number) value).doubleValue()));
            } else {
                setText(value == null ? "" : value.toString());
            }
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
 
    public static void main(String[] args) {
        VentanaVueloYHotel vuelosyhotel = new VentanaVueloYHotel();
        vuelosyhotel.setVisible(true);
    }
}