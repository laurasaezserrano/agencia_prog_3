package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CuadriculaOfertas extends JFrame{
	private JTextField oferta;
	private JTextField descripcionoferta;
	
	public CuadriculaOfertas() {
		JPanel mainpanel = new JPanel();
		JPanel panel1 = new JPanel(new GridLayout(3, 3, 20, 20));
		for (int i = 0; i < 9; i++) {
			int numero = i + 1;
			JButton boton = new JButton("Oferta " + numero);
			boton.setPreferredSize(new Dimension(200, 120));
//			boton.setVerticalTextPosition(SwingConstants.TOP);
//			boton.setHorizontalTextPosition(SwingConstants.CENTER);
			boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					abriroferta(numero);
					
				}
			});
			
			panel1.add(boton);			
		}
		
		
		mainpanel.add(panel1);
		add(mainpanel);
		
		
		setTitle("OFERTAS RECIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setMaximumSize(getToolkit().getScreenSize());
		setResizable(false);
		setLocationRelativeTo(null);
				
		
		oferta = new JTextField();
		oferta.setText("OFERTAS RECIENTES");
		oferta.setEditable(false);
		oferta.setHorizontalAlignment(JTextField.CENTER);
		oferta.setFont(new Font("Times new Roman", Font.BOLD, 25));
		add(oferta, BorderLayout.NORTH);
		
		
		
	}
	private void abriroferta(int numero) {
		JFrame ventanaoferta = new JFrame("Oferta" + numero);
		ventanaoferta.setSize(800, 500);
		ventanaoferta.setLocationRelativeTo(this);
//		ventanaoferta.setLayout(new BorderLayout(10, 10));
		
		//Descripciones para cada oferta (predefinidas)
		 String[] descripciones = {
		        "¡Descubre las playas paradisíacas del Caribe! Incluye hotel 5 estrellas, vuelo directo y todo incluido. " +
		        "Disfruta de aguas cristalinas, arena blanca y un clima perfecto durante todo el año.",
		        
		        "Escapada romántica a París. Visita la Torre Eiffel, el Louvre y pasea por los Campos Elíseos. " +
		        "Incluye hotel boutique en el centro, desayuno continental y tour guiado por la ciudad.",
		        
		        "Aventura en los Alpes Suizos. Montañas nevadas y pueblos encantadores te esperan. " +
		        "Incluye teleférico panorámico, fondue suizo, chocolate artesanal y alojamiento en chalet alpino.",
		        
		        "Tour gastronómico por Italia. Roma, Florencia y Venecia te esperan. " +
		        "Incluye catas de vino, clases de cocina italiana y visitas a los monumentos más emblemáticos.",
		        
		        "Safari fotográfico en Kenia. Observa la fauna salvaje en su hábitat natural. " +
		        "Alojamiento en lodge de lujo, guía experto y transporte 4x4 para avistamiento de animales.",
		        
		        "Crucero por el Mediterráneo. Barcelona, Nápoles, Atenas y más. " +
		        "Todo incluido: comidas, entretenimiento y excursiones en cada puerto. Barco de última generación.",
		        
		        "Relax en Bali. Templos milenarios, playas y cultura única. " +
		        "Incluye spa, yoga matinal, excursiones a arrozales y visita a templos sagrados.",
		        
		        "Nueva York, la ciudad que nunca duerme. Broadway, Central Park y Times Square. " +
		        "Paquete con entradas a musicales, tour por Manhattan y subida al Empire State.",
		        
		        "Ruta por las Auroras Boreales en Islandia. Experiencia única e irrepetible. " +
		        "Incluye tour fotográfico nocturno, baño en aguas termales y alojamiento con vistas privilegiadas."
		    };
		
		JLabel titulo = new JLabel("Detalles de la Oferta " + numero, SwingConstants.CENTER);
		titulo.setFont(new Font("Times new Roman", Font.PLAIN, 22));
		ventanaoferta.add(titulo, BorderLayout.NORTH);
		
		//Descripcion
		JTextArea descripcionArea = new JTextArea();
		descripcionArea.setText(descripciones[numero - 1]); // Obtiene la descripción del array
        descripcionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setEditable(false);
        descripcionArea.setBackground(new Color(250, 250, 250));
        descripcionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);
        ventanaoferta.add(scrollDescripcion, BorderLayout.CENTER);
        
		JPanel centro = new JPanel(new BorderLayout());
		JLabel imagen = new JLabel();
		imagen.setOpaque(true);
		imagen.setBackground(new Color(200, 220, 255));
		imagen.setPreferredSize(new Dimension(200, 150));
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setText("Imagen de la oferta " + numero);
		
		descripcionoferta = new JTextField();
		descripcionoferta.setText("");
		
		ventanaoferta.setVisible(true);
	}
	
	
	
	
	
	public static void main(String[] args) {
		CuadriculaOfertas ofertas = new CuadriculaOfertas();
		ofertas.setVisible(true);
	}
	
	
}
