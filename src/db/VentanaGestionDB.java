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


	/* podemos hacer una base de datos con los aeropuertos, aviones, aerolinea,
	 * vuelos... 
	 */
	
