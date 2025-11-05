package agencia_prog_3_GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ProcesoReserva extends JFrame {

	private static final long serialVersionUID = 1L;
	// Contador para el número de reserva
    private int contadorReservas = 0;
    
    // Nombre del archivo CSV donde se guardarán los datos
    private final String NOMBRE_ARCHIVO_CSV = "reservas.csv";
	
	public ProcesoReserva() {
		
        setTitle("Confirmar Reserva");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        
        JButton botonConfirmar = new JButton("Confirmar y Guardar Reserva");
        
        botonConfirmar.addActionListener(new ActionListener() {
            
        	@Override
            public void actionPerformed(ActionEvent e) {
                guardarReservaEnCSV();
            }
        });
        
        panel.add(botonConfirmar);
        add(panel);
    }

    //Escribe la reserva en el archivo reservas.csv
    private void guardarReservaEnCSV() {
        contadorReservas++;
        
        String nombreUsuario = Ventana1Login.userField.toString();
        String codigoReserva = "RESERVA" + contadorReservas;
        
        File archivo = new File(NOMBRE_ARCHIVO_CSV);
        boolean archivoVacio = !archivo.exists() || archivo.length() == 0;
        
        //IAG Gemini
        // Usamos try-with-resources para asegurarnos de que el FileWriter se cierra solo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) { // 'true' para modo "append" (añadir al final)
        //FINAL IAG
            
            if (archivoVacio) {
                writer.write("Usuario,CodigoReserva"); //escribe la cabecera del csv
                writer.newLine(); //añade una linea vacia
            }
            
            //Escribe los datos de la reserva
            writer.write(nombreUsuario + "," + codigoReserva);
            writer.newLine();
            
            JOptionPane.showMessageDialog(this, 
                "¡Reserva " + codigoReserva + " guardada con éxito en " + NOMBRE_ARCHIVO_CSV + "!",
                "Reserva Confirmada", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException ex) {
            //Muestra un mensaje de error si algo falla
            ex.printStackTrace(); //Imprime el error en la consola
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el archivo CSV: " + ex.getMessage(),
                "Error de Archivo", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
	
    public static void main(String[] args) {
		ProcesoReserva vProceso = new ProcesoReserva();
		vProceso.setVisible(true);
	}
    
}
