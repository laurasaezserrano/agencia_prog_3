package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import db.GestorBD;
import domain.Reserva;


public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
    JPanel centerPanel = new JPanel();
    private String usuarioLogueado;
    
    private DefaultTableModel modeloTablaHoteles;
    private DefaultTableModel modeloTablaExcursiones ;
    private TableRowSorter<DefaultTableModel> sorterHoteles;
    private TableRowSorter<DefaultTableModel> sorterExcursiones;
    private JTabbedPane tabbedPane;
    
    private JTextField txtFiltroCiudad;
    
    private JTable tablaHoteles;
    private JTable tablaExcursiones;
    
    private GestorBD gestorBD;

	
	public VentanaReservas() {
		this.gestorBD = new GestorBD();
		gestorBD.crearBBDD();
		gestorBD.initilizeFromCSV();
		
		setTitle("Gestión de Reservas");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JLabel titleLabel = new JLabel("Mis Reservas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
 
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JButton botonInicio = new JButton("Atras"); //luego cambiarlo a un icono
        
        panelSuperior.add(botonInicio, BorderLayout.WEST); // Botón a la izquierda
        panelSuperior.add(titleLabel, BorderLayout.CENTER); // Título en el centro

        
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Filtrar por Ciudad:"));
        txtFiltroCiudad = new JTextField(20); 
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnLimpiarFiltro = new JButton("Limpiar Filtro");
        
        panelFiltro.add(txtFiltroCiudad);
        panelFiltro.add(btnFiltrar);
        panelFiltro.add(btnLimpiarFiltro);
        panelSuperior.add(panelFiltro, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane();
        
        try {
           this.usuarioLogueado = Ventana1Login.userField.getText();
       } catch(Exception e) {
           System.err.println("ADVERTENCIA: No se pudo leer 'Ventana1Login.userField'. Usando 'Test User'.");
           this.usuarioLogueado = "Test User";
       }
        
        
		botonInicio.addActionListener(new ActionListener() {
		    
			@Override
		    public void actionPerformed(ActionEvent e) {
		        dispose(); 
		        VentanaInicio vInicio = new VentanaInicio();
		        vInicio.setVisible(true);
		    }
		});
        
        String[] columnasHoteles  = {
            "Ciudad", "Hotel", "Email", "Habitación", "Adultos", 
            "Niños", "Salida", "Regreso", "Precio (€)", "Cancelar"
        };
        
        
        modeloTablaHoteles = new DefaultTableModel(columnasHoteles , 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna "Cancelar" (índice 9) es editable (es un botón)
                return column == 9; 
            }
        };
        
        tablaHoteles = new JTable(modeloTablaHoteles);
        
        sorterHoteles = new TableRowSorter<>(modeloTablaHoteles);
        tablaHoteles.setRowSorter(sorterHoteles);
        // Configuramos la tabla de hoteles (incluyendo el botón)
        configurarTabla(tablaHoteles, modeloTablaHoteles);
        JScrollPane scrollHoteles = new JScrollPane(tablaHoteles);
        tabbedPane.addTab("Hoteles y Alojamiento", scrollHoteles);
        
        String[] columnasExcursiones = {
                "Destino", "Excursión", "Email", "Tipo", "Adultos", 
                "Niños", "Fecha Inicio", "Fecha Fin", "Precio (€)", "Cancelar"
            };
        
         modeloTablaExcursiones = new DefaultTableModel(columnasExcursiones, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna "Cancelar" (índice 9) es editable
                return column == 9;
            }
        };
        
        tablaExcursiones = new JTable(modeloTablaExcursiones);
        sorterExcursiones = new TableRowSorter<>(modeloTablaExcursiones);
        tablaExcursiones.setRowSorter(sorterExcursiones);
        // Configuramos la tabla de excursiones (incluyendo el botón)
        configurarTabla(tablaExcursiones, modeloTablaExcursiones);
        JScrollPane scrollExcursiones = new JScrollPane(tablaExcursiones);
        tabbedPane.addTab("Excursiones", scrollExcursiones);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        txtFiltroCiudad.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
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
 
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = txtFiltroCiudad.getText();
                RowFilter<DefaultTableModel, Object> filtro = null;
                if (texto.trim().length() > 0) {
                    try {
                        filtro = RowFilter.regexFilter("(?i)" + texto, 0);
                    } catch (java.util.regex.PatternSyntaxException ex) {
                        System.err.println("Error en el filtro regex");
                    }
                }
                sorterHoteles.setRowFilter(filtro);
                sorterExcursiones.setRowFilter(filtro);
            }
        });
		
        btnLimpiarFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFiltroCiudad.setText("");
                sorterHoteles.setRowFilter(null);
                sorterExcursiones.setRowFilter(null);
            }
        });
        
		cargarReservasDesdeBD(usuarioLogueado);
		cerrarventana(txtFiltroCiudad, tablaHoteles, tablaExcursiones, tabbedPane, btnFiltrar, btnLimpiarFiltro);
	}
	
			
	private void configurarTabla(JTable tabla, DefaultTableModel model) {
	    tabla.setRowHeight(28);
	    tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
	    tabla.setFillsViewportHeight(true);

	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

	    int columnas = tabla.getColumnCount();

	    // Aplicamos tanto el centrado como el color alternado a todas menos la última
	    for (int i = 0; i < columnas - 1; i++) {
	        tabla.getColumnModel().getColumn(i).setCellRenderer(highlightRenderer);
	    }

	    // Botón cancelar
	    int columnaCancelar = columnas - 1;
	    tabla.getColumnModel().getColumn(columnaCancelar).setCellRenderer(new ButtonRenderer("Cancelar"));
	    tabla.getColumnModel().getColumn(columnaCancelar).setCellEditor(
	            new ButtonEditor(tabla, this::cancelarReserva)
	    );

	    tabla.getColumnModel().getColumn(columnaCancelar).setPreferredWidth(100);
	    tabla.getColumnModel().getColumn(columnaCancelar).setMaxWidth(100);
	    tabla.getColumnModel().getColumn(columnaCancelar).setMinWidth(100);
	}
	
	
	TableCellRenderer highlightRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String textoFiltro = txtFiltroCiudad.getText();
            String valorCelda = (value != null) ? value.toString() : "";

            if (!textoFiltro.isEmpty() && column == 0 && !isSelected) {
                String pattern = "(?i)(" + Pattern.quote(textoFiltro) + ")";
                String resaltado = valorCelda.replaceAll(pattern, "<span style='background: yellow;'>$1</span>");
                c.setText("<html>" + resaltado + "</html>");
            } else {
                c.setText(valorCelda);
            }

            // Mantener colores alternos de filas si no está seleccionado
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? new Color(250, 249, 249) : new Color(235, 235, 235));
            }
            c.setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    };
	
	
    private void cargarReservasDesdeBD(String usuarioFiltrar) {
        try {
        	modeloTablaHoteles.setRowCount(0);
            modeloTablaExcursiones.setRowCount(0);
            
            List<Reserva> reservas = gestorBD.getListaTodasLasReservas();
            
            int contadorHoteles = 0;
            int contadorExcursiones = 0;
            
            for (Reserva r : reservas) {
                if (r.getUsuario().equalsIgnoreCase(usuarioFiltrar)) {
                    Object[] fila = {
                        r.getCiudad(),
                        r.getNombreHotel(),
                        r.getEmail(),
                        r.getTipoHabitacion(),
                        r.getNumAdultos(),
                        r.getNumNiños(),
                        r.getFechaEntrada(),
                        r.getFechaSalida(),
                        r.getPrecioNoche(),
                        "Cancelar"
                    };
                    
                    if (r.getTipoHabitacion().equalsIgnoreCase("Excursión")) {
                        modeloTablaExcursiones.addRow(fila);
                        contadorExcursiones++;
                    } else {
                        modeloTablaHoteles.addRow(fila);
                        contadorHoteles++;
                    }
                }
            }
            
            System.out.println("Cargadas " + contadorHoteles + " reservas de hotel y " + 
                             contadorExcursiones + " excursiones");
                             
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al cargar reservas: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void cancelarReserva(JTable tabla) {
        int filaView = tabla.getEditingRow();
        if (filaView < 0) return; // Si no hay fila, no hagas nada

        // Convertir la fila visual (si está filtrada) al índice real del modelo
        int filaModel = tabla.convertRowIndexToModel(filaView);
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();

        // 1. Pedir confirmación
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres cancelar esta reserva?\n" + model.getValueAt(filaModel, 1),
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // 2. Eliminar la reserva del archivo CSV
            if (eliminarReservaDeBD(model, filaModel)) {
                // 3. Si se borra del CSV, eliminarla de la tabla (JTable)
                model.removeRow(filaModel);
                JOptionPane.showMessageDialog(this, "Reserva cancelada con éxito.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo cancelar la reserva del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	
	private boolean eliminarReservaDeBD(DefaultTableModel model, int row) {
        try {
            String usuario = usuarioLogueado;
            String nombreHotel = model.getValueAt(row, 1).toString();
            String ciudad = model.getValueAt(row, 0).toString();
            java.sql.Date fechaEntrada = (java.sql.Date) model.getValueAt(row, 6);
            java.sql.Date fechaSalida = (java.sql.Date) model.getValueAt(row, 7);
            
            boolean eliminado = gestorBD.eliminarReserva(usuario, nombreHotel, ciudad, 
                                                          fechaEntrada, fechaSalida);
            
            if (eliminado) {
                model.removeRow(row);
                JOptionPane.showMessageDialog(this, "Reserva cancelada", "Éxito", 
                                            JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar", "Error", 
                                            JOptionPane.ERROR_MESSAGE);
            }
            return eliminado;
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
	
	private void aplicarbusqueda() {
    	String texto = txtFiltroCiudad.getText();
        RowFilter<DefaultTableModel, Object> rf = null;
        try {
            // Filtra por la columna 0 (Ciudad/Destino)
            rf = RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorterHoteles.setRowFilter(rf);
        sorterExcursiones.setRowFilter(rf);
        tablaHoteles.repaint();
        tablaExcursiones.repaint();
    };

	
	static class ButtonRenderer extends JButton implements TableCellRenderer {
        private static final long serialVersionUID = 1L;

        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(new Color(200, 50, 50)); // Color rojo
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private static final long serialVersionUID = 1L;
        private final JButton button;
        private final JTable table;
        private final java.util.function.Consumer<JTable> onClick; // Acción a realizar

        public ButtonEditor(JTable table, java.util.function.Consumer<JTable> onClick) {
            this.table = table;
            this.onClick = onClick;
            this.button = new JButton("Cancelar");
            this.button.setOpaque(true);
            this.button.setForeground(Color.WHITE);
            this.button.setBackground(new Color(220, 60, 60)); // Rojo un poco más brillante al editar
            this.button.addActionListener(this);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        public Object getCellEditorValue() {
            return "Cancelar";
        }

        public void actionPerformed(ActionEvent e) {
            // Llama a la acción (el método 'cancelarReserva')
            onClick.accept(table);
            // Detiene la edición de la celda
            fireEditingStopped();
        }
    }
	
	private void cerrarventana(JTextField txtFiltroCiudad, JTable tablaHoteles, JTable tablaExcursiones, JTabbedPane tabbedPane, 
			JButton btnFiltrar, JButton btnLimpiarFiltro) {
		KeyAdapter listener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
					dispose();
				}
			}
		};
		this.addKeyListener(listener);
		setFocusable(true);
		txtFiltroCiudad.addKeyListener(listener);
	    tablaHoteles.addKeyListener(listener);
	    tablaExcursiones.addKeyListener(listener);
	    tabbedPane.addKeyListener(listener);
	    btnFiltrar.addKeyListener(listener);
	    btnLimpiarFiltro.addKeyListener(listener);
	}
	
	class TestCargaReservas {
	    
	    public static void main(String[] args) {
	        System.out.println("=== INICIANDO DIAGNÓSTICO ===\n");
	        
	        GestorBD gestorBD = new GestorBD();
	        
	        // 1. Crear la base de datos
	        System.out.println("1. Creando base de datos...");
	        gestorBD.crearBBDD();
	        
	        // 2. Inicializar desde CSV
	        System.out.println("2. Inicializando desde CSV...");
	        gestorBD.initilizeFromCSV();
	        
	        // 3. Obtener todas las reservas
	        System.out.println("\n3. Obteniendo reservas de la BD...");
	        List<Reserva> reservas = gestorBD.getListaTodasLasReservas();
	        
	        System.out.println("Total de reservas encontradas: " + reservas.size());
	        System.out.println("\n=== LISTADO DE RESERVAS ===");
	        
	        if (reservas.isEmpty()) {
	            System.out.println("⚠️ NO HAY RESERVAS EN LA BASE DE DATOS");
	            System.out.println("\nPosibles causas:");
	            System.out.println("1. El archivo CSV está vacío o mal formateado");
	            System.out.println("2. La propiedad 'loadCSV' no está en 'true' en app.properties");
	            System.out.println("3. La ruta al CSV es incorrecta");
	            System.out.println("4. Hay errores en la lectura del CSV (revisa los logs)");
	        } else {
	            int contador = 1;
	            for (Reserva r : reservas) {
	                System.out.println("\n--- Reserva #" + contador + " ---");
	                System.out.println("Usuario: " + r.getUsuario());
	                System.out.println("Ciudad: " + r.getCiudad());
	                System.out.println("Hotel: " + r.getNombreHotel());
	                System.out.println("Email: " + r.getEmail());
	                System.out.println("Tipo Habitación: " + r.getTipoHabitacion());
	                System.out.println("Adultos: " + r.getNumAdultos());
	                System.out.println("Niños: " + r.getNumNiños());
	                System.out.println("Fecha Entrada: " + r.getFechaEntrada());
	                System.out.println("Fecha Salida: " + r.getFechaSalida());
	                System.out.println("Precio: " + r.getPrecioNoche());
	                contador++;
	            }
	        }
	        
	        // 4. Verificar archivo CSV
	        System.out.println("\n\n=== VERIFICANDO ARCHIVO CSV ===");
	        java.io.File csvFile = new java.io.File("resources/data/reservas.csv");
	        System.out.println("Ruta absoluta del CSV: " + csvFile.getAbsolutePath());
	        System.out.println("¿Existe el archivo? " + csvFile.exists());
	        System.out.println("¿Se puede leer? " + csvFile.canRead());
	        
	        if (csvFile.exists()) {
	            System.out.println("Tamaño del archivo: " + csvFile.length() + " bytes");
	            
	            // Mostrar las primeras líneas del CSV
	            System.out.println("\n--- Primeras 5 líneas del CSV ---");
	            try (java.io.BufferedReader br = new java.io.BufferedReader(
	                    new java.io.FileReader(csvFile))) {
	                String linea;
	                int numLinea = 0;
	                while ((linea = br.readLine()) != null && numLinea < 5) {
	                    System.out.println("Línea " + numLinea + ": " + linea);
	                    numLinea++;
	                }
	            } catch (Exception e) {
	                System.out.println("Error al leer CSV: " + e.getMessage());
	            }
	        }
	        
	        // 5. Verificar app.properties
	        System.out.println("\n\n=== VERIFICANDO app.properties ===");
	        java.io.File propsFile = new java.io.File("resources/conf/app.properties");
	        System.out.println("Ruta absoluta: " + propsFile.getAbsolutePath());
	        System.out.println("¿Existe? " + propsFile.exists());
	        
	        if (propsFile.exists()) {
	            try (java.io.BufferedReader br = new java.io.BufferedReader(
	                    new java.io.FileReader(propsFile))) {
	                System.out.println("\n--- Contenido de app.properties ---");
	                String linea;
	                while ((linea = br.readLine()) != null) {
	                    System.out.println(linea);
	                }
	            } catch (Exception e) {
	                System.out.println("Error al leer properties: " + e.getMessage());
	            }
	        }
	        
	        System.out.println("\n=== FIN DEL DIAGNÓSTICO ===");
	    }
	}
    
    
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}