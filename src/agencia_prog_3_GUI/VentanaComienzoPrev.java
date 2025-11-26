package agencia_prog_3_GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class VentanaComienzoPrev extends JFrame{
	private static final long serialVersionUID = 1L;
	// Se inicia la app con una imagen de fondo y un botón donde poder acceder
	private static final int ANCHO_VENTANA = 900;
    private static final int ALTO_VENTANA = 700;
    
	public  VentanaComienzoPrev() {
		super.setTitle("Bienvenid@! / Welcome! / Ongi Etorri!");
		setSize(ANCHO_VENTANA, ALTO_VENTANA); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		setLocationRelativeTo(null);
		//Ruta de la Imagen de Fondo
	    String rutaImagen = "images\\fondoVentana0.png";
		Image imagenAjustada = null ;
		
		try {
	    	imagenAjustada = ImageIO.read(new File(rutaImagen));
	    	imagenAjustada = imagenAjustada.getScaledInstance(ANCHO_VENTANA, ALTO_VENTANA, Image.SCALE_SMOOTH);
	    	
	    } catch (IOException e){
	    	System.err.println("Imagen no cargada correctamente: " + e.getMessage());
	    	//Cuando la imagen no se carga, mostramos el siguiente mensaje:
	    	JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de fondo.", 
	                "Error de Carga", JOptionPane.ERROR_MESSAGE);
	    }
	    
//	    JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel fondoLabel = new JLabel(new ImageIcon(imagenAjustada));
		fondoLabel.setBounds(0, 0, ANCHO_VENTANA, ALTO_VENTANA);	
	    
	    //JButton de comienzo
	    JButton boton = new JButton("START");
	    boton.setFont(new Font ("Calibri", Font.BOLD, 18));
	    boton.setForeground(Color.BLACK);
	    boton.setBackground(new Color(195, 176, 145));
	    int ANCHO_BOTON = 120; 
		int ALTO_BOTON = 60;
	    boton.setOpaque(true);
	    boton.setBorderPainted(false);
		    
		    //Posicion X - Centrado Horizontalmente
		    int botonX = 700;;
		    //Posicion Y - Centrado Verticalmente
		    int botonY = 200;;
		    
		    boton.setBounds(botonX, botonY, ANCHO_BOTON, ALTO_BOTON);
		    
	//Action Listener
	boton.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(VentanaComienzoPrev.this, "Has accedido a la app web de Sunny Agencia de Viajes!");
	
            // Cerrar la ventana actual
            dispose();
            
            // Abrir la nueva ventana
            Ventana1Login vLogin = new Ventana1Login();
            vLogin.setVisible(true);
        }
    });

	
	add(fondoLabel);
	add(boton);
	setVisible(true);
	
	}
	//MAIN
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new VentanaComienzoPrev().setVisible(true);
			}
		});
	}
}