package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//FALTA
//Añadir boton de reserva para que acabe de confirmar la reserva con los datos necesarios y añadirla a su perfil

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
		        "¡Descubre las playas paradisíacas del Caribe! \nIncluye vuelo directo desde Madrid con estancia en el Hotel Palace Aurora (4⭐) junto con un todo incluido. " +
		        "Disfruta de aguas cristalinas, arena blanca, café de origen, ritmo caribeño y clima perfecto durante todo el año." + "Este maravilloso paquete incluye un city tour"
		        		+ "por La Candelaria, ruta del cafe y cata de origen, visita a la isla colombiana de San Andres, famosa por su 'mar de los 7 colores', alli podras hacer snorkel "
		        		+ "y ver una enorme diversidad de vida marina, tambien visitaras Cartagena de Indias donde apreciaras la ciudad amurallada y las Islas del Rosario.",
		        
		        "Escapada romántica a París, 'La Ciudad de la Luz' y 'La Ciudad del Amor'. \n Visita la Torre Eiffel, el Louvre y pasea por los Campos Elíseos. " +
		        "Incluye vuelo + estancia en Suites Andres (4★), desayuno continental y tour guiado por la ciudad, asi como crucero al atardecer por el rio Sena y dia completo en "
		        + "Disneyland® Paris (opcion de familias).",
		        
		        "Aventura en los Alpes Suizos con estancia en el Hotel Montaña (5★) ubicado en Zurich, Suiza. \nMontañas nevadas, lagos turquesa y pueblos encantadores te esperan. " +
		        "\nEste paquete inclute vuelo directo desde Madrid, entrada para el teleférico panorámico por Lucerna y Monte Pilatus, dia completo por Interlaken & Jungfraujoch, Titlis: puente colgante y glaciar, "
		        + "tren panoramico Glacier Express, cata de comida tradicional como el fondue suizo o el chocolate artesanal entre otras muchas actividades. ",
		        
		        "El Resort Victoria (5★) te espera en Roma. \nNo te pierdas la historia viva, las plazas y la gran gastronoia italiana. \nIncluye catas de vino, visita al Vaticano, dia completo en Florencia, Napoles o Pompeya,"
		        + " clases de cocina italiana, una inigualable ruta gastronomica por Trastevere y visitas a los monumentos más emblemáticos como el Coliseo.",
		        
		        //cambiar imagen 5 a imagen de Toronto
		        "Safari fotográfico en Kenia. Observa la fauna salvaje en su hábitat natural. " +
		        "Alojamiento en lodge de lujo, guía experto y transporte 4x4 para avistamiento de animales.",
		        
		        //hay que cmabiar imagen 6 a una de tokyo
		        "Tokio es un contraste perfecto entre templos milenarios y barrios futuristas. Desde Asakusa a Shibuya, la ciudad vibra 24/7 con izakayas, sushi de mercado y centros comerciales "
		        + "infinitos. Ideal para foodies, parejas y viajeros curiosos. Tokio en 4 días: ¡vive Japón a tu ritmo con vuelo desde Madrid y estancia en uno de los mejores hoteles de la ciudad, "
		        + "el Hotel Bahia (5★). \nPodras valorar Tokyo desde el Mirador Tokyo Skytree y presenciar la cantidad de gente que transcurre por el cruce de Shibuya. Podras apreciar la cultura pop y tech asi como visitar el templo de "
		        + "Nikko y la ciudad de Kamakura. Tambien podras pasar un dia en Hakone y ver las vistas del Monte Fuji." ,
		        
		        "Hotel Palace Pacifico (4⭐) en Bangkok + vuelo directo desde Madrid. \n Cultura milenaria, templos "
		        + "dorados y vida callejera vibrante. Ideal para combinar ciudad + naturaleza, con opción de extensión "
		        + "a Bali uno de los destinos mas aclamados de Tailandia. " + "Este paquete incluye aparte del vuelo y hotel, "
		        		+ "excuriones como: Tours guiados por el Gran Palacio, Wat Pho y Wat Arun, un dia completo por las ruinas de "
		        		+ "Ayutthaya, visita a los mercados flotantes, spa, yoga matinal, excursiones a arrozales y visita a la faosa ciudad de Ubud.",
		        
		        "Hotel Resort Imperial (4⭐) en Nueva York + vuelo directo desde Madrid. \n" + "'La ciudad que nunca duerme' ofrece rascacielos, "
		        		+ "cultura pop, museos, musicales de Broadway, barrios iconicos como Times Square, Central Park y diversidad de planes para "
		        		+ "todo tipo de viajeros. \n"+ "El paquete incluye diferentes excursiones como: Paseo en ferry hasta la Estatua de la Libertad, "
		        				+ "entradas a musicales, tour por Manhattan, ruta gastronomica y subida al Empire State.",
		        
		        //cambiar imagen 9 por imagen de Mexico
		        "Aventura nordica y ruta por las Auroras Boreales en Islandia. Experiencia única e irrepetible. " +
		        "Incluye tour fotográfico nocturno, baño en aguas termales y alojamiento con vistas privilegiadas."
		    };
		
		JLabel titulo = new JLabel("Detalles de la Oferta " + numero, SwingConstants.CENTER);
		titulo.setFont(new Font("Times new Roman", Font.PLAIN, 22));
		ventanaoferta.add(titulo, BorderLayout.CENTER);
		
		JPanel contenidoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel imagenLabel = new JLabel();
	    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    //Ruta imagen
	    //Habra que añadir un try / catch por si no la encuentra o no la carga
	    String rutaImagen = "images/Oferta" + numero + ".png";
	    try {
	    	Image originalImage = ImageIO.read(new File(rutaImagen));
	        
	        // Define las dimensiones que quieres para la imagen en la ventana (ej: 400x300)
	        int anchoDeseado = 350; 
	        int altoDeseado = 350; 
	        
	        //Ajusta la imagen
	        Image scaledImage = originalImage.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
	        ImageIcon imageIcon = new ImageIcon(scaledImage);
	        
	        imagenLabel.setIcon(imageIcon); // Establece la imagen al JLabel
	        imagenLabel.setText(null);
	    } catch (IOException e) {
	    	imagenLabel.setText("Imagen no encontrada: " + rutaImagen);
	        imagenLabel.setForeground(Color.RED);
	        System.err.println("Error al cargar imagen " + rutaImagen + ": " + e.getMessage());
	    	}	
	    
	    JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelImagen.add(imagenLabel);
	    ventanaoferta.add(panelImagen,BorderLayout.NORTH);
	    
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
