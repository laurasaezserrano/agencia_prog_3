package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import agencia_prog_3_GUI.Ventana1Login;
import agencia_prog_3_GUI.VentanaExcursiones.ButtonEditor;
import agencia_prog_3_GUI.VentanaExcursiones.ButtonRenderer;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
    JPanel centerPanel = new JPanel();
    private DefaultTableModel modeloTabla;
    private String usuarioLogueado;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private DefaultTableModel modeloTablaHoteles;
    private DefaultTableModel modeloTablaExcursiones ;
    private TableRowSorter<DefaultTableModel> sorterHoteles;
    private TableRowSorter<DefaultTableModel> sorterExcursiones;
    private JTabbedPane tabbedPane;

	
	public VentanaReservas() {
		setTitle("Gestión de Reservas");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JLabel titleLabel = new JLabel("Mis Reservas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
 
        //Panel Central para las dos columnas (reservas anteriores y siguientes)
//        centerPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas, 10px de espacio horizontal
//        centerPanel.setBackground(Color.LIGHT_GRAY);
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JButton botonInicio = new JButton("Atras"); //luego cambiarlo a un icono
        
        panelSuperior.add(botonInicio, BorderLayout.WEST); // Botón a la izquierda
        panelSuperior.add(titleLabel, BorderLayout.CENTER); // Título en el centro

        
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel para los filtros
		JLabel lblFiltrarCiudad = new JLabel("Filtrar por Ciudad:");
		JTextField txtFiltroCiudad = new JTextField(20); // Campo de texto de 20 cols
		JButton btnFiltrar = new JButton("Filtrar");
		JButton btnLimpiarFiltro = new JButton("Limpiar Filtro");
		
		panelFiltro.add(lblFiltrarCiudad);
		panelFiltro.add(txtFiltroCiudad);
		panelFiltro.add(btnFiltrar);
		panelFiltro.add(btnLimpiarFiltro);
		
		// Añadimos el panel de filtro debajo del título
		panelSuperior.add(panelFiltro, BorderLayout.SOUTH);
        
        
//        centerPanel.add(panelSuperior, BorderLayout.NORTH); // <-- MIRA AQUÍ (El panel completo va arriba)
		
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
        JTable tablaHoteles = new JTable(modeloTablaHoteles);
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
        JTable tablaExcursiones = new JTable(modeloTablaExcursiones);
        TableRowSorter sorterExcursiones = new TableRowSorter<>(modeloTablaExcursiones);
        tablaExcursiones.setRowSorter(sorterExcursiones);
        // Configuramos la tabla de excursiones (incluyendo el botón)
        configurarTabla(tablaExcursiones, modeloTablaExcursiones);
        JScrollPane scrollExcursiones = new JScrollPane(tablaExcursiones);
        tabbedPane.addTab("Excursiones", scrollExcursiones);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        
//        // 2. Crear el modelo de la tabla (vacío) y hacerlo no editable
//        modeloTabla = new DefaultTableModel(columnas, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//               return false; // Las celdas no se pueden editar
//            }
//        };
//
//        // 3. Crear la JTable usando el modelo
//        JTable tablaReservas = new JTable(modeloTabla);
//        tablaReservas.setRowHeight(25); // Filas más altas
//        tablaReservas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
//        tablaReservas.setFillsViewportHeight(true);
//        
//        sorter = new TableRowSorter<>(modeloTabla);
//		tablaReservas.setRowSorter(sorter);        
//        
//        // 4. Añadir la JTable a un JScrollPane
//        JScrollPane scrollPaneReservas = new JScrollPane(tablaReservas);
//        scrollPaneReservas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        
//        // 5. Añadir el JScrollPane al centro de la ventana
//        add(scrollPaneReservas, BorderLayout.CENTER);
        
        
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
		
		
		
	}
	
	private void configurarTabla(JTable tabla, DefaultTableModel model) {
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.setFillsViewportHeight(true);
        
        // --- NUEVO: Añadimos el ButtonRenderer y ButtonEditor a la última columna ---
        int columnaCancelar = 9; 
        tabla.getColumnModel().getColumn(columnaCancelar).setCellRenderer(new ButtonRenderer("Cancelar"));
        tabla.getColumnModel().getColumn(columnaCancelar).setCellEditor(
                new ButtonEditor(tabla, this::cancelarReserva) // Llama al método cancelarReserva
        );
        
        // (Opcional) Damos un ancho fijo a la columna del botón
        tabla.getColumnModel().getColumn(columnaCancelar).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(columnaCancelar).setMaxWidth(100);
        tabla.getColumnModel().getColumn(columnaCancelar).setMinWidth(100);
    }
	
	
//	private void cargarReservas(String usuarioFiltrar) {
//		
//        File archivo = new File("reservas.csv");
//        
//        int contador = 0;
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
//            
//            String linea;
//            reader.readLine(); // Saltamos la cabecera
//                        
//            // Leemos el resto del archivo línea por línea
//            while ((linea = reader.readLine()) != null) {
//                if (linea.trim().isEmpty()) {
//                    continue; // Ignora líneas vacías
//                }
//                
//                String[] datos = linea.split(",");
//                
//                // Formato CSV: Usuario,Ciudad,Hotel,Email,Habitacion,Adultos,Ninos,Salida,Regreso,Precio
//                if (datos.length < 10) {
//                    System.err.println("Línea CSV malformada: " + linea);
//                    continue; 
//                }
//                
//                String usuarioCSV = datos[0];
//                
//                // ¡AQUÍ ESTÁ EL FILTRO!
//                // Comparamos el usuario del CSV con el usuario logueado
//                if (usuarioCSV.equalsIgnoreCase(usuarioFiltrar)) {
//                    
//                    // Extraemos los datos (índices corridos por el usuario)
//                    String ciudad = datos[1];
//                    String hotel = datos[2].replace(";", ","); // Re-convierte ; a ,
//                    String email = datos[3];
//                    String hab = datos[4];
//                    int adultos = Integer.parseInt(datos[5]);
//                    int ninos = Integer.parseInt(datos[6]);
//                    String salida = datos[7];
//                    String regreso = datos[8];
//                    double precio = Double.parseDouble(datos[9]);
//                    
//                    // Creamos la fila para el modelo
//                    Object[] fila = {
//                        ciudad, hotel, email, hab, adultos, ninos, salida, regreso, precio
//                    };
//                    
//                    // Añadimos la fila al modelo de la JTable
//                    modeloTabla.addRow(fila);
//                    contador++;
//                }
//            }
//            
//            System.out.println("Se encontraron " + contador + " reservas para " + usuarioFiltrar);
//            
//        } catch (IOException | NumberFormatException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, 
//                "Error al leer el archivo de reservas: " + ex.getMessage(), 
//                "Error de Lectura", 
//                JOptionPane.ERROR_MESSAGE);
//        }
//        
//        if (modeloTabla.getRowCount() == 0) {
//             System.out.println("No se encontraron reservas para " + usuarioFiltrar);
//             // Opcional: mostrar un mensaje en la GUI
//        }
//        
//    }
	
	
	private void cargarReservasDesdeCSV(String usuarioFiltrar) {
		
        File archivo = new File("reservas.csv");
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
                    
                    Object[] filaEx = {
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
        File inputFile = new File("reservas.csv");
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

//	private JPanel crearPanelReserva(String codigoReserva) {
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
//        panel.setBackground(Color.WHITE);
//        
//        // Creamos una etiqueta para el código de reserva
//        JLabel lblCodigo = new JLabel(codigoReserva);
//        lblCodigo.setFont(new Font("Arial", Font.BOLD, 16));
//        
//        // Podrías añadir más cosas aquí (un icono, más detalles, etc.)
//        panel.add(lblCodigo);
//        
//        
//        return panel;
//    }
	
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
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}