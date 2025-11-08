package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import agencia_prog_3_GUI.Ventana1Login;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
    JPanel centerPanel = new JPanel();
    private DefaultTableModel modeloTabla;
    private String usuarioLogueado;
    private TableRowSorter<DefaultTableModel> sorter;

	
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
        centerPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas, 10px de espacio horizontal
        centerPanel.setBackground(Color.LIGHT_GRAY);
        
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
//        add(centerPanel, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Añadimos el JScrollPane (que contiene el panel) al centro de la ventana
        add(scrollPane, BorderLayout.CENTER);
        
        try {
            // ESTA LÍNEA ES DE TU CÓDIGO ORIGINAL
           this.usuarioLogueado = Ventana1Login.userField.getText();
       } catch(Exception e) {
           // Si Ventana1Login no está disponible (p.ej. probando esta clase sola)
           // usamos un valor por defecto.
           System.err.println("ADVERTENCIA: No se pudo leer 'Ventana1Login.userField'. Usando 'Test User'.");
           this.usuarioLogueado = "Test User"; // <-- CAMBIA ESTO SI ES NECESARIO
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
        String[] columnas = {
            "Ciudad", "Hotel", "Email", "Habitación", "Adultos", 
            "Niños", "Salida", "Regreso", "Precio (€)"
        };
        
        // 2. Crear el modelo de la tabla (vacío) y hacerlo no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false; // Las celdas no se pueden editar
            }
        };

        // 3. Crear la JTable usando el modelo
        JTable tablaReservas = new JTable(modeloTabla);
        tablaReservas.setRowHeight(25); // Filas más altas
        tablaReservas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaReservas.setFillsViewportHeight(true);
        
        sorter = new TableRowSorter<>(modeloTabla);
		tablaReservas.setRowSorter(sorter);        
        
        // 4. Añadir la JTable a un JScrollPane
        JScrollPane scrollPaneReservas = new JScrollPane(tablaReservas);
        scrollPaneReservas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // 5. Añadir el JScrollPane al centro de la ventana
        add(scrollPaneReservas, BorderLayout.CENTER);
        
        
        btnFiltrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String texto = txtFiltroCiudad.getText();
				if (texto.trim().length() == 0) {
					// Si el campo está vacío, no se aplica filtro
					sorter.setRowFilter(null);
				} else {
					// Aplicamos un filtro Regex.
					// "(?i)" lo hace insensible a mayúsculas/minúsculas.
					// El '0' indica que debe filtrar por la columna 0 (Ciudad).
					try {
						sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0));
					} catch (java.util.regex.PatternSyntaxException ex) {
						System.err.println("Error en la sintaxis del filtro: " + ex.getMessage());
					}
				}
			}
		});
		
		btnLimpiarFiltro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Limpiamos el campo de texto
				txtFiltroCiudad.setText("");
				// Quitamos cualquier filtro aplicado
				sorter.setRowFilter(null);
			}
		});
        
        
		cargarReservas(usuarioLogueado);
		
		
		
	}
	
	
	private void cargarReservas(String usuarioFiltrar) {
		
        File archivo = new File("reservas.csv");
        
        int contador = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            reader.readLine(); // Saltamos la cabecera
                        
            // Leemos el resto del archivo línea por línea
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue; // Ignora líneas vacías
                }
                
                String[] datos = linea.split(",");
                
                // Formato CSV: Usuario,Ciudad,Hotel,Email,Habitacion,Adultos,Ninos,Salida,Regreso,Precio
                if (datos.length < 10) {
                    System.err.println("Línea CSV malformada: " + linea);
                    continue; 
                }
                
                String usuarioCSV = datos[0];
                
                // ¡AQUÍ ESTÁ EL FILTRO!
                // Comparamos el usuario del CSV con el usuario logueado
                if (usuarioCSV.equalsIgnoreCase(usuarioFiltrar)) {
                    
                    // Extraemos los datos (índices corridos por el usuario)
                    String ciudad = datos[1];
                    String hotel = datos[2].replace(";", ","); // Re-convierte ; a ,
                    String email = datos[3];
                    String hab = datos[4];
                    int adultos = Integer.parseInt(datos[5]);
                    int ninos = Integer.parseInt(datos[6]);
                    String salida = datos[7];
                    String regreso = datos[8];
                    double precio = Double.parseDouble(datos[9]);
                    
                    // Creamos la fila para el modelo
                    Object[] fila = {
                        ciudad, hotel, email, hab, adultos, ninos, salida, regreso, precio
                    };
                    
                    // Añadimos la fila al modelo de la JTable
                    modeloTabla.addRow(fila);
                    contador++;
                }
            }
            
            System.out.println("Se encontraron " + contador + " reservas para " + usuarioFiltrar);
            
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al leer el archivo de reservas: " + ex.getMessage(), 
                "Error de Lectura", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        if (modeloTabla.getRowCount() == 0) {
             System.out.println("No se encontraron reservas para " + usuarioFiltrar);
             // Opcional: mostrar un mensaje en la GUI
        }
        
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
	
	
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}