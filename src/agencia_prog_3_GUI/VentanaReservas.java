package agencia_prog_3_GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel areaReservas = new JLabel();
	String nombreUsuario = Ventana1Login.userField.getText();
	
	public VentanaReservas() {
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new FlowLayout());
		mainpanel.setBackground(Color.WHITE);
		setTitle("Reservas del usuario");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(10, 10, 10));
        JLabel lblTitulo = new JLabel("Reservas de " + nombreUsuario, JLabel.CENTER);
        
        
        
	}
	
	
	
	
	private void cargarReservas() {
        File archivo = new File("reservas.csv");
        
        // Usamos un StringBuilder para construir el texto
        StringBuilder sb = new StringBuilder();
        int contador = 0;

        if (!archivo.exists()) {
            areaReservas.setForeground(Color.RED);
            areaReservas.setText("ERROR: No se encontró el archivo 'reservas.csv'");
            return;
        }

        // Usamos try-with-resources para que el reader se cierre solo
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            reader.readLine(); // Saltamos la cabecera ("Usuario,CodigoReserva")
            
            // Leemos el resto del archivo línea por línea
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue; // Ignora líneas vacías
                }
                
                String[] datos = linea.split(",");
                
                // Comprobamos que la línea tiene el formato esperado
                if (datos.length >= 2) {
                    String usuarioCSV = datos[0];
                    String codigoReserva = datos[1];
                    
                    // Esta es la comprobación clave
                    if (usuarioCSV.equals(this.nombreUsuario)) {
                        sb.append("  • " + codigoReserva + "\n");
                        contador++;
                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
            areaReservas.setForeground(Color.RED);
            areaReservas.setText("Error al leer el archivo CSV: " + ex.getMessage());
            return; 
        }
        }
	
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}
