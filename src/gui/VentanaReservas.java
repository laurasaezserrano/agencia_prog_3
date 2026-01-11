package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
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

	
	public VentanaReservas() {
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
		        // Cierra la ventana actual
		        dispose(); 
		        
		        // Abre una nueva instancia de la ventana de inicio
		        VentanaInicio vInicio = new VentanaInicio();
		        vInicio.setVisible(true);
		    }
		});
        
		// 1. Definir las columnas que leeremos del CSV (omitimos 'Usuario')
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
                        // Filtro por columna 0 (Ciudad)
                        filtro = RowFilter.regexFilter("(?i)" + texto, 0);
                    } catch (java.util.regex.PatternSyntaxException ex) {
                        System.err.println("Error en el filtro regex");
                    }
                }
                // Aplicamos el mismo filtro a ambos sorters
                sorterHoteles.setRowFilter(filtro);
                sorterExcursiones.setRowFilter(filtro);
            }
        });
		
        btnLimpiarFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFiltroCiudad.setText("");
                // Limpiamos ambos filtros
                sorterHoteles.setRowFilter(null);
                sorterExcursiones.setRowFilter(null);
            }
        });
        
		cargarReservasDesdeCSV(usuarioLogueado);
		cerrarventana(txtFiltroCiudad, tablaHoteles, tablaExcursiones, tabbedPane, btnFiltrar, btnLimpiarFiltro);
	}
	
			
	private void configurarTabla(JTable tabla, DefaultTableModel model) {
	    tabla.setRowHeight(28);
	    tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
	    tabla.setFillsViewportHeight(true);

	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

//	    TableCellRenderer colorRenderer = (table1, value, isSelected, hasFocus, row, column) -> {
//	        JLabel result = new JLabel(value != null ? value.toString() : "");
//            result.setFont(new Font("Arial", Font.PLAIN, 14));       
//	        if (!isSelected) {
//	            if (row % 2 == 0) {
//	                result.setBackground(new Color(250, 249, 249)); 
//	            } else {
//	                result.setBackground(new Color(235, 235, 235)); 
//	            }
//	        }
//	        
//	        result.setOpaque(true);
//	        return result;
//	    };

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
                // Usamos Regex para buscar el texto sin importar mayúsculas y ponerle fondo amarillo con HTML
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
	
	
	private void cargarReservasDesdeCSV(String usuarioFiltrar) {
		
        File archivo = new File("resources/data/reservas.csv");
        if (!archivo.exists()) {
            System.err.println("No se encontró el archivo reservas.csv");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            reader.readLine(); // Saltamos la cabecera
            int contadorHoteles = 0;
            int contadorExcursiones = 0;

            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(",");
                if (datos.length < 10) continue;

                String usuarioCSV = datos[0];
                if (usuarioCSV.compareToIgnoreCase(usuarioFiltrar) == 0) {
                    
                    // Extraemos los datos del CSV
                    String ciudad = datos[1];
                    String HotelOExcursion = datos[2].replace(";", ",");
                    String email = datos[3];
                    String HabOTipo = datos[4]; // Esta es la clave
                    int adultos = Integer.parseInt(datos[5]);
                    int ninos = Integer.parseInt(datos[6]);
                    String salida = datos[7];
                    String regreso = datos[8];
                    double precio = Double.parseDouble(datos[9]);
                    
                    // Creamos la fila (incluyendo el "Cancelar")
                    Object[] fila = {
                        ciudad, HotelOExcursion, email, HabOTipo, adultos, ninos, 
                        salida, regreso, precio, "Cancelar"
                    };
                    
                    
                    
                    // --- NUEVA LÓGICA DE CLASIFICACIÓN ---
                    if (HabOTipo.equalsIgnoreCase("Excursión")) {
                        modeloTablaExcursiones.addRow(fila);
                        contadorExcursiones++;
                    } else {
                        modeloTablaHoteles.addRow(fila);
                        contadorHoteles++;
                    }
                }
            }
            System.out.println("Cargadas " + contadorHoteles + " reservas de hotel y " + contadorExcursiones + " excursiones para " + usuarioFiltrar);
            
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de reservas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            if (eliminarReservaDelCSV(model, filaModel)) {
                // 3. Si se borra del CSV, eliminarla de la tabla (JTable)
                model.removeRow(filaModel);
                JOptionPane.showMessageDialog(this, "Reserva cancelada con éxito.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo cancelar la reserva del archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
	
	
	private boolean eliminarReservaDelCSV(DefaultTableModel model, int row) {
        File inputFile = new File("resources/data/reservas.csv");
        File tempFile = new File("reservas.temp.csv"); // Archivo temporal

        // Obtenemos los datos clave de la fila para identificarla
        // Índices del MODELO (0=Ciudad, 1=Reserva, 6=Salida, 8=Precio)
        String ciudad = model.getValueAt(row, 0).toString();
        String hotelOExcursion = model.getValueAt(row, 1).toString();
        String salida = model.getValueAt(row, 6).toString();
        Double precio = (Double) model.getValueAt(row, 8);
        String precioStr = String.format(Locale.US, "%.2f", precio); // Formato "59.00"

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String cabecera = reader.readLine();
            writer.println(cabecera); // Escribimos la cabecera en el temp

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(",");

                // Índices del CSV (0=Usuario, 1=Ciudad, 2=Hotel, 7=Salida, 9=Precio)
                if (datos.length < 10) continue; 
                
                String usuarioCSV = datos[0];
                String ciudadCSV = datos[1];
                String hotelCSV = datos[2].replace(";", ",");
                String salidaCSV = datos[7];
                String precioCSV = datos[9];

                // Comprobamos si esta línea es la que queremos borrar
                boolean esLaReservaABorrar = 
                        usuarioCSV.equalsIgnoreCase(usuarioLogueado) &&
                        ciudadCSV.equalsIgnoreCase(ciudad) &&
                        hotelCSV.equalsIgnoreCase(hotelOExcursion) &&
                        salidaCSV.equals(salida) &&
                        (Double.parseDouble(precioCSV) == Double.parseDouble( precioStr));
                
                if (!esLaReservaABorrar) {
                    writer.println(linea); // Si NO es la línea, la escribimos en el temp
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false; // Falló la re-escritura
        }

        // 4. Reemplazar el archivo original con el temporal
        if (!inputFile.delete()) {
            System.err.println("Error: No se pudo borrar el archivo original.");
            return false;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.err.println("Error: No se pudo renombrar el archivo temporal.");
            return false;
        }
        
        return true; // Éxito
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
        
        // Forzar a las tablas a repintarse para que el renderizador resalte el texto
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
            // Se ve igual si está seleccionado o no
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
    
    
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}