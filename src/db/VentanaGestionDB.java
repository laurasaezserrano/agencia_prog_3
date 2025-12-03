//package db;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class VentanaGestionDB extends JFrame {
//
//	private static final long serialVersionUID = 1L;
//    
//	// Componentes de la GUI
//	private JComboBox<String> cmbTablas;
//	private JTable tablaDatos;
//	private JButton btnCargar;
//	private JButton btnInsertar;
//	private JButton btnActualizar;
//	private JButton btnEliminar;
//	private JLabel lblEstadoDB;
//
//	// Objeto de Conexión a la DB
//	private Connection connection;
//
//	public VentanaGestionDB() {
//		super("Gestión de Base de Datos - Agencia de Viajes");
//		
//		inicializarConexionDB();
//		inicializarComponentes();
//		configurarLayout();
//
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setSize(900, 600);
//		setLocationRelativeTo(null);
//	}
//
//    // --- CONEXIÓN Y DESCONEXIÓN DE LA BASE DE DATOS ---
//
//	private void inicializarConexionDB() {
//		// Lógica para cargar el driver JDBC y establecer 'connection'.
//	}
//    
//    public void cerrarConexionDB() {
//        // Lógica para cerrar 'connection' de forma segura.
//    }
//
//	// --- CONFIGURACIÓN DE LA INTERFAZ DE USUARIO (GUI) ---
//
//	private void inicializarComponentes() {
//		// Inicialización de cmbTablas, tablaDatos, y botones.
//		// Asignación de ActionListener a los botones.
//	}
//
//	private void configurarLayout() {
//		// Configuración del BorderLayout, Paneles Norte (selector), 
//		// Centro (tabla con scroll) y Sur (botones CRUD).
//	}
//
//	// --- MANEJO DE EVENTOS Y LÓGICA DE INTERACCIÓN CON LA DB (CRUD) ---
//
//	private void manejarCargarDatos(ActionEvent event) {
//		// Lógica para ejecutar SELECT * FROM <tablaSeleccionada>
//		// y actualizar el contenido de 'tablaDatos'.
//	}
//	
//	private void manejarInsertar(ActionEvent event) {
//		// Lógica para abrir un diálogo de inserción y ejecutar INSERT INTO.
//	}
//
//	private void manejarActualizar(ActionEvent event) {
//		// Lógica para obtener la fila seleccionada, abrir un diálogo 
//		// de modificación, y ejecutar UPDATE.
//	}
//	
//	private void manejarEliminar(ActionEvent event) {
//		// Lógica para confirmar la eliminación de la fila seleccionada 
//		// y ejecutar DELETE FROM.
//	}
//
//	// --- MÉTODO PRINCIPAL ---
//    
//	public static void main(String[] args) {
//		// Creación de la instancia y configuración del cierre de conexión.
//	}
//}





package db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import agencia_prog_3_GUI.Ventana1Login;
import agencia_prog_3_GUI.VentanaInicio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpHeaders;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaGestionDB extends JFrame {

	private static final long serialVersionUID = 1L;
    
	// Componentes de la GUI
	private JComboBox<String> cmbTablas;
	private JTable tablaDatos;
	private DefaultTableModel modeloTabla;
	private JButton btnCargar;
	private JButton btnInsertar;
	private JButton btnActualizar;
	private JButton btnEliminar;
	private JLabel lblEstadoDB;

	// Conexión y gestor de BD
	private Connection connection;
	private GestorBD gestorBD;
	
	private static final String CONNECTION_STRING = "jdbc:sqlite:resources/data/agencia.db";
	private static final String DRIVER = "org.sqlite.JDBC";

	public VentanaGestionDB() {
		super("Gestión de Base de Datos - Agencia de Viajes");
		
		gestorBD = new GestorBD();
		inicializarConexionDB();
		inicializarComponentes();
		configurarLayout();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000, 650);
		setLocationRelativeTo(null);
		
		// Cerrar conexión al cerrar ventana
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				cerrarConexionDB();
			}
		});
		
		if (cmbTablas != null) {
	        cmbTablas.setSelectedItem("Reserva"); // 1. Selecciona la tabla de Reservas
	    }
	    manejarCargarDatos(null);
	    
	    
	}

    // --- CONEXIÓN Y DESCONEXIÓN DE LA BASE DE DATOS ---

	private void inicializarConexionDB() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(CONNECTION_STRING);
			lblEstadoDB = new JLabel("✓ Conectado a la BD");
			lblEstadoDB.setForeground(Color.GREEN);
			System.out.println("Conexión a BD establecida");
		} catch (Exception ex) {
			lblEstadoDB = new JLabel("✗ Error de conexión");
			lblEstadoDB.setForeground(Color.RED);
			System.err.println("Error al conectar con la BD: " + ex.getMessage());
		}
	}
    
    public void cerrarConexionDB() {
        try {
        	if (connection != null && !connection.isClosed()) {
        		connection.close();
        		System.out.println("Conexión cerrada correctamente");
        	}
        } catch (SQLException ex) {
        	System.err.println("Error al cerrar conexión: " + ex.getMessage());
        }
    }

	// --- CONFIGURACIÓN DE LA INTERFAZ DE USUARIO (GUI) ---

	private void inicializarComponentes() {
		// ComboBox con las tablas disponibles
		cmbTablas = new JComboBox<>(new String[] {
			"Reserva", "Vuelo", "Aeropuerto", "Aerolinea", "Avion"
		});
		
		// Tabla de datos
		modeloTabla = new DefaultTableModel();
		tablaDatos = new JTable(modeloTabla);
		tablaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaDatos.setRowHeight(25);
		tablaDatos.setAlignmentX(CENTER_ALIGNMENT);
		
		//cabecera
        JTableHeader cabecera = tablaDatos.getTableHeader();
        cabecera.setReorderingAllowed(false);
        cabecera.setAlignmentX(CENTER_ALIGNMENT);
        cabecera.setFont(cabecera.getFont().deriveFont(Font.BOLD));
        
        
		// Botones CRUD
		btnCargar = new JButton("Cargar Datos");
		btnInsertar = new JButton("Insertar");
		btnActualizar = new JButton("Actualizar");
		btnEliminar = new JButton("Eliminar");
		
		// ActionListeners
		btnCargar.addActionListener(this::manejarCargarDatos);
		btnInsertar.addActionListener(this::manejarInsertar);
		btnActualizar.addActionListener(this::manejarActualizar);
		btnEliminar.addActionListener(this::manejarEliminar);
	}

	
	private void configurarLayout() {
		setLayout(new BorderLayout(10, 10));
		
		// Panel Norte: Selector de tabla y estado
		JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelNorte.add(new JLabel("Seleccionar Tabla:"));
		panelNorte.add(cmbTablas);
		panelNorte.add(btnCargar);
		panelNorte.add(lblEstadoDB);
		add(panelNorte, BorderLayout.NORTH);
		
		// Panel Centro: Tabla con scroll
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		add(scrollPane, BorderLayout.CENTER);
		
		// Panel Sur: Botones CRUD
		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelSur.add(btnInsertar);
		panelSur.add(btnActualizar);
		panelSur.add(btnEliminar);
		add(panelSur, BorderLayout.SOUTH);
		
		
		JButton botonInicio = new JButton("Inicio"); //luego cambiarlo a un icono
        
        panelSur.add(botonInicio, BorderLayout.WEST); // Botón a la izquierda
        
	    botonInicio.addActionListener(new ActionListener() {
		    
			@Override
		    public void actionPerformed(ActionEvent e) {
		        // Cierra la ventana actual
		        dispose(); 
		        
		        // Abre una nueva instancia de la ventana de inicio
		        Ventana1Login vInicio = new Ventana1Login();
		        vInicio.setVisible(true);
		    }
		});
	}
	
	private void manejarCargarDatos(ActionEvent e) {
	    String tablaSeleccionada = (String) cmbTablas.getSelectedItem();

	    if (tablaSeleccionada == null) {
	        // En la carga inicial desde el constructor, cmbTablas podría ser nulo.
	        // Forzamos la carga de Reservas si es la primera vez que se abre.
	        tablaSeleccionada = "Reserva"; 
	    }

	    if (tablaSeleccionada.equals("Reserva")) {
	        cargarDatosReservas();
	    } 

	    modeloTabla.setRowCount(0);
		modeloTabla.setColumnCount(0);
		
		try (PreparedStatement pStmt = connection.prepareStatement(
				"SELECT * FROM " + tablaSeleccionada + ";");
		     ResultSet rs = pStmt.executeQuery()) {
			
			// Configurar columnas
			int numColumnas = rs.getMetaData().getColumnCount();
			for (int i = 1; i <= numColumnas; i++) {
				modeloTabla.addColumn(rs.getMetaData().getColumnName(i));
			}
			
			// Cargar datos
			int contador = 0;
			while (rs.next()) {
				Object[] fila = new Object[numColumnas];
				for (int i = 1; i <= numColumnas; i++) {
					fila[i-1] = rs.getObject(i);
				}
				modeloTabla.addRow(fila);
				contador++;
			}
			
			lblEstadoDB.setText("✓ " + contador + " registros cargados de " + tablaSeleccionada);
			lblEstadoDB.setForeground(Color.GREEN);
			
		} catch (SQLException ex) {
			lblEstadoDB.setText("✗ Error al cargar datos");
			lblEstadoDB.setForeground(Color.RED);
			JOptionPane.showMessageDialog(this, 
				"Error al cargar datos: " + ex.getMessage(),
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarDatosReservas() {
	    // 1. Establecer las cabeceras de la tabla
	    String[] nombresColumna = {"ID", "Usuario", "Ciudad", "Reserva", "Fecha Salida", "Precio"}; 
	    modeloTabla.setColumnIdentifiers(nombresColumna);

	    // 2. Limpiar la tabla
	    modeloTabla.setRowCount(0);

	    // 3. Obtener datos y añadir filas
	    java.util.List<Object[]> reservas = gestorBD.getListaTodasLasReservas(); // Llamada al GestorBD modificado
	    
	    for (Object[] reserva : reservas) {
	        modeloTabla.addRow(reserva);
	    }
	}
	
	
	private void manejarInsertar(ActionEvent event) {
		String tablaSeleccionada = (String) cmbTablas.getSelectedItem();
		
		if (tablaSeleccionada == null) return;
		
		// Crear diálogo específico según la tabla
		if (tablaSeleccionada.equals("Reserva")) {
			mostrarDialogoInsertarReserva();
		} else if (tablaSeleccionada.equals("Aeropuerto")) {
			mostrarDialogoInsertarAeropuerto();
		} else {
			JOptionPane.showMessageDialog(this,
				"Inserción para la tabla " + tablaSeleccionada + " no implementada aún",
				"Info", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void mostrarDialogoInsertarReserva() {
		JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));
		
		JTextField txtUsuario = new JTextField();
		JTextField txtCiudad = new JTextField();
		JTextField txtReservaNombre = new JTextField();
		JTextField txtEmail = new JTextField();
		JTextField txtTipoHab = new JTextField();
		JTextField txtAdultos = new JTextField("2");
		JTextField txtNinos = new JTextField("0");
		JTextField txtFechaSalida = new JTextField("2025-06-01");
		JTextField txtFechaRegreso = new JTextField("2025-06-10");
		JTextField txtPrecio = new JTextField("100.00");
		
		panel.add(new JLabel("Usuario:"));
		panel.add(txtUsuario);
		panel.add(new JLabel("Ciudad:"));
		panel.add(txtCiudad);
		panel.add(new JLabel("Nombre Reserva:"));
		panel.add(txtReservaNombre);
		panel.add(new JLabel("Email:"));
		panel.add(txtEmail);
		panel.add(new JLabel("Tipo Habitación:"));
		panel.add(txtTipoHab);
		panel.add(new JLabel("Adultos:"));
		panel.add(txtAdultos);
		panel.add(new JLabel("Niños:"));
		panel.add(txtNinos);
		panel.add(new JLabel("Fecha Salida:"));
		panel.add(txtFechaSalida);
		panel.add(new JLabel("Fecha Regreso:"));
		panel.add(txtFechaRegreso);
		panel.add(new JLabel("Precio:"));
		panel.add(txtPrecio);
		
		int resultado = JOptionPane.showConfirmDialog(this, panel, 
			"Insertar Nueva Reserva", JOptionPane.OK_CANCEL_OPTION);
		
		if (resultado == JOptionPane.OK_OPTION) {
			try {
				gestorBD.insertarReserva(
					txtUsuario.getText(),
					txtCiudad.getText(),
					txtReservaNombre.getText(),
					txtEmail.getText(),
					txtTipoHab.getText(),
					Integer.parseInt(txtAdultos.getText()),
					Integer.parseInt(txtNinos.getText()),
					txtFechaSalida.getText(),
					txtFechaRegreso.getText(),
					Double.parseDouble(txtPrecio.getText())
				);
				
				JOptionPane.showMessageDialog(this, "Reserva insertada correctamente");
				manejarCargarDatos(null); // Recargar datos
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, 
					"Error al insertar: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void mostrarDialogoInsertarAeropuerto() {
		String nombre = JOptionPane.showInputDialog(this, 
			"Nombre del Aeropuerto:", "Insertar Aeropuerto", 
			JOptionPane.PLAIN_MESSAGE);
		
		if (nombre != null && !nombre.trim().isEmpty()) {
			try {
				gestorBD.insertarAeropuerto(new agencia_prog_3_data.Aeropuertos(nombre));
				JOptionPane.showMessageDialog(this, "Aeropuerto insertado correctamente");
				manejarCargarDatos(null);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, 
					"Error al insertar: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void manejarActualizar(ActionEvent event) {
		int filaSeleccionada = tablaDatos.getSelectedRow();
		
		if (filaSeleccionada < 0) {
			JOptionPane.showMessageDialog(this,
				"Por favor, selecciona una fila para actualizar",
				"Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(this,
			"Funcionalidad de actualización en desarrollo.\n" +
			"Fila seleccionada: " + (filaSeleccionada + 1),
			"Info", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void manejarEliminar(ActionEvent event) {
		int filaSeleccionada = tablaDatos.getSelectedRow();
		String tablaSeleccionada = (String) cmbTablas.getSelectedItem();
		
		if (filaSeleccionada < 0) {
			JOptionPane.showMessageDialog(this,
				"Por favor, selecciona una fila para eliminar",
				"Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this,
			"¿Estás seguro de eliminar este registro?",
			"Confirmar Eliminación",
			JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (tablaSeleccionada.equals("Reserva")) {
				eliminarReserva(filaSeleccionada);
			} else if (tablaSeleccionada.equals("Aeropuerto")) {
	            eliminarAeropuerto(filaSeleccionada);
			} else {
				JOptionPane.showMessageDialog(this,
					"Eliminación para " + tablaSeleccionada + " no implementada aún");
			}
		}
	}
	
	private void eliminarAeropuerto(int filaSeleccionada) {
		try {
	        Object idObj = modeloTabla.getValueAt(filaSeleccionada, 0);
	        if (idObj == null) throw new IllegalArgumentException("ID de aeropuerto no encontrado");

	        int id = Integer.parseInt(idObj.toString());
	        String nombre = String.valueOf(modeloTabla.getValueAt(filaSeleccionada, 1));

	        boolean eliminado = gestorBD.eliminarAeropuertoPorId(id); // llama al nuevo método

	        if (eliminado) {
	            JOptionPane.showMessageDialog(this,
	                "Aeropuerto eliminado correctamente" + (nombre != null ? ": " + nombre : ""));
	            manejarCargarDatos(null); // Recargar la tabla actual
	        } else {
	            JOptionPane.showMessageDialog(this,
	                "No se pudo eliminar el aeropuerto (puede que no exista).",
	                "Aviso", JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this,
	            "Error al eliminar aeropuerto: " + ex.getMessage(),
	            "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void eliminarReserva(int fila) {
		try {
			// Obtener datos de la fila (asumiendo que columna 1 es usuario, 2 ciudad, etc.)
			String usuario = modeloTabla.getValueAt(fila, 1).toString();
			String ciudad = modeloTabla.getValueAt(fila, 2).toString();
			String reservaNombre = modeloTabla.getValueAt(fila, 3).toString();
			String fechaSalida = modeloTabla.getValueAt(fila, 8).toString();
			double precio = Double.parseDouble(modeloTabla.getValueAt(fila, 10).toString());
			
			boolean eliminado = gestorBD.eliminarReserva(usuario, ciudad, 
				reservaNombre, fechaSalida, precio);
			
			if (eliminado) {
				JOptionPane.showMessageDialog(this, "Reserva eliminada correctamente");
				manejarCargarDatos(null); // Recargar
			} else {
				JOptionPane.showMessageDialog(this, "No se pudo eliminar la reserva");
			}
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, 
				"Error al eliminar: " + ex.getMessage(),
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// --- MÉTODO PRINCIPAL ---
    
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VentanaGestionDB ventana = new VentanaGestionDB();
			ventana.setVisible(true);
		});
	}
}
