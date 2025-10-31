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
// Se inicia la app con una imagen de fondo y un botón donde poder acceder
	public  VentanaComienzoPrev() {
		super.setTitle("Bienvenid@! / Welcome! / Ongi Etorri!");
		setSize(600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	
		//Creamos un tipo de panel que permite superponer capas - Idea IA + Implementacion Propia
		//JLayerdedPane
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(600,400));
		
		//Ruta de la Imagen de Fondo
		//"C:\Users\laura.saez.serrano\prog apps\agencia_prog_3\images\fondoVentana0.png"
		JLabel imagenLabel = new JLabel();
	    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    String rutaImagen = "images\\fondoVentana0.png";
		ImageIcon imageIcon = null;
		Image imagenAjustada = null ;
		try {
	    	Image fondo = ImageIO.read(new File(rutaImagen));
	    	//Ajustamos el tamaño de la imagen al tamaño del layout - IA (Porque causaba problemas el tamaño)
	    	imagenAjustada = fondo.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
	    	ImageIcon image = new ImageIcon(imagenAjustada);
	    	imagenLabel.setIcon(image); 
	        imagenLabel.setText(null);
	        
	    } catch (IOException e){
	    	System.err.println("Imagen no cargada correctamente: " + e.getMessage());
	    	//Cuando la imagen no se carga, mostramos el siguiente mensaje:
	    	JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de fondo.", 
	                "Error de Carga", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelImagen.add(imagenLabel);
	    layeredPane.add(panelImagen,BorderLayout.NORTH);
	    
	    //JButton de comienzo
	    JButton boton = new JButton("Start");
	    boton.setFont(new Font ("Calibri", Font.BOLD, 18));
	    boton.setForeground(Color.BLACK);
	    boton.setBackground(new Color(0, 120, 215, 180));
	    boton.setOpaque(true);
	    boton.setBorderPainted(false);
		
		    //Posicion en la imagen
		    int botonAncho = 85;
		    int botonAltura = 50;
		    
		    //Posicion X - Centrado Horizontalmente
		    int botonX = (layeredPane.getPreferredSize().width - botonAncho) / 2;
		    //Posicion Y - Centrado Verticalmente
		    int botonY = (layeredPane.getPreferredSize().height - botonAltura) / 2;
		    
		    boton.setBounds(botonX, botonY, botonAncho, botonAltura);
		    layeredPane.add(boton, JLayeredPane.PALETTE_LAYER);//.PALETTE_LAYER para insertar en una capa superior
		    this.add(layeredPane);
    
	//Action Listener
	boton.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(VentanaComienzoPrev.this, "Has accecido a la app web de Sunny Agencia de Viajes!");
		}
	});
		setVisible(true);
	
	//Action Listener para enlazar con metodo de otra clase - PRUEBA 
	boton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Cerrar la ventana actual
            dispose();
            
            // Abrir la nueva ventana
            new Ventana1Login();
        }
    });
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