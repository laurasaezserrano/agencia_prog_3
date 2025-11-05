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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import agencia_prog_3_GUI.Ventana1Login;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
    JPanel centerPanel = new JPanel();

	
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

//        centerPanel.add(panelSuperior, BorderLayout.NORTH); // <-- MIRA AQUÍ (El panel completo va arriba)
		
        add(panelSuperior, BorderLayout.NORTH);
//        add(centerPanel, BorderLayout.CENTER);
        
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Añadimos el JScrollPane (que contiene el panel) al centro de la ventana
        add(scrollPane, BorderLayout.CENTER);
        
       
        
        
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
        
		
		cargarReservas();
		
		
		
		
	}
	
	
	private void cargarReservas() {
		
        File archivo = new File("reservas.csv");
        
        int contador = 0;

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
                    
                    if (usuarioCSV.equals(Ventana1Login.userField.getText())) {
                    	JPanel panelReserva = crearPanelReserva(codigoReserva);
                    	centerPanel.add(panelReserva);
                        contador++;
                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
//            areaReservas.setForeground(Color.RED);
//            areaReservas.setText("Error al leer el archivo CSV: " + ex.getMessage());
            return; 
        }
        
        if (contador == 0) {
            JLabel lblVacio = new JLabel("No se encontraron reservas para " + Ventana1Login.userField.getText());
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 14));
            lblVacio.setHorizontalAlignment(JLabel.CENTER);
            centerPanel.add(lblVacio);
        }
        
    }

	private JPanel crearPanelReserva(String codigoReserva) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panel.setBackground(Color.WHITE);
        
        // Creamos una etiqueta para el código de reserva
        JLabel lblCodigo = new JLabel(codigoReserva);
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Podrías añadir más cosas aquí (un icono, más detalles, etc.)
        panel.add(lblCodigo);
        
        
        return panel;
    }
	
	
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}