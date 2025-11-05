package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaReservas() {
		setTitle("Gestión de Reservas");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JLabel titleLabel = new JLabel("Mis Reservas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);
        
        //Panel Central para las dos columnas (reservas anteriores y siguientes)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas, 10px de espacio horizontal
        centerPanel.setBackground(Color.LIGHT_GRAY);
        
	}
	
	
	private void cargarReservas() {
		
        File archivo = new File("reservas.csv");
        
        try {
            new FileWriter("reservas.csv").close(); // Abre en modo sobrescribir y cierra
            JOptionPane.showMessageDialog(this, "El archivo reservas.csv ha sido vaciado.");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al vaciar el archivo.");
        }
        
        int contador = 0;

        if (!archivo.exists()) {
//            areaReservas.setForeground(Color.RED);
//            areaReservas.setText("ERROR: No se encontró el archivo 'reservas.csv'");
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
//                    if (usuarioCSV.equals(this.nombreUsuario)) {
//                        sb.append("  • " + codigoReserva + "\n");
//                        contador++;
//                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
//            areaReservas.setForeground(Color.RED);
//            areaReservas.setText("Error al leer el archivo CSV: " + ex.getMessage());
            return; 
        }
        }

	
	
	
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}