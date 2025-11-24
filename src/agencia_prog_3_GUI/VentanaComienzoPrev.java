package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class VentanaComienzoPrev extends JFrame{
	private static final long serialVersionUID = 1L;
	// Se inicia la app con una imagen de fondo y un botón donde poder acceder
	public  VentanaComienzoPrev() {
		super.setTitle("Bienvenid@! / Welcome! / Ongi Etorri!");
		setSize(600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		setLocationRelativeTo(null);
		//Ruta de la Imagen de Fondo
	    String rutaImagen = "images\\fondoVentana0.png";
		Image imagenAjustada = null ;
		
		try {
	    	imagenAjustada = ImageIO.read(new File(rutaImagen));
	    	imagenAjustada = imagenAjustada.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
	    	
	    } catch (IOException e){
	    	System.err.println("Imagen no cargada correctamente: " + e.getMessage());
	    	//Cuando la imagen no se carga, mostramos el siguiente mensaje:
	    	JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de fondo.", 
	                "Error de Carga", JOptionPane.ERROR_MESSAGE);
	    }
	    
//	    JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel fondoLabel = new JLabel(new ImageIcon(imagenAjustada));
		fondoLabel.setBounds(0, 0, 600, 400);
	
	    
	    //JButton de comienzo
	    JButton boton = new JButton("Start");
	    boton.setFont(new Font ("Calibri", Font.BOLD, 18));
	    boton.setForeground(Color.BLACK);
	    boton.setBackground(new Color(0, 120, 215, 180));
	    int ANCHO_BOTON = 120; 
		int ALTO_BOTON = 60;
	    boton.setOpaque(true);
	    boton.setBorderPainted(false);
		    
		    //Posicion X - Centrado Horizontalmente
	    	int margen = 20;
		    int botonX = 400;
		    //Posicion Y - Centrado Verticalmente
		    int botonY = 60;
		    
		    boton.setBounds(botonX, botonY, 85, 50);
    
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