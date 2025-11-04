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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.border.LineBorder;
import java.util.List;
import java.util.Random;

//FALTA
//Añadir boton de reserva para que acabe de confirmar la reserva con los datos necesarios y añadirla a su perfil
//Crear metodo especificado despues de Lectura CSV

public class CuadriculaOfertas extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField oferta;
	private JTextField descripcionoferta;
	private HashMap<String, List<Hotel>> hotelesPorCiudad;
	
	public CuadriculaOfertas() {
		this.hotelesPorCiudad = cargarHotelesDesdeCSV();
		JPanel mainpanel = new JPanel();
		JPanel panel1 = new JPanel(new GridLayout(3, 3, 20, 20));
		
//		String[] ciudades = {
//	            "Caribe",
//	            "Paris",
//	            "Suiza",
//	            "Roma",
//	            "Toronto",
//	            "Tokyo",
//	            "Bangkok",
//	            "Nueva York",
//	            "Oslo"
//	        };
//		for (String ciudad : ciudades) {
//			JButton boton = new JButton(ciudad);
//			boton.setPreferredSize(new Dimension(200, 120));
//			boton.setBackground(new Color(255, 255, 255));
//			boton.setBorder(new LineBorder(Color.BLACK, 2));
//			boton.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					abriroferta(numero);	
//				}
//			});
//			
//			panel1.add(boton);	
//		}
		
		String[] ciudadesOferta = {
			    "Caribe",      
			    "Paris",       
			    "Suiza",       
			    "Roma",        
			    "Toronto",     
			    "Tokyo",       
			    "Bangkok",     
			    "Nueva York",  
			    "Oslo"         
			};
		
		for (int i = 0; i < 9; i++) {
			int numero = i + 1;
			JButton boton = new JButton(ciudadesOferta [numero-1]);
		
			boton.setPreferredSize(new Dimension(200, 120));
//			boton.setVerticalTextPosition(SwingConstants.TOP);
//			boton.setHorizontalTextPosition(SwingConstants.CENTER);
			boton.setBackground(new Color(255, 255, 255));
			boton.setBorder(new LineBorder(Color.BLACK, 2));
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
		panel1.setBackground(new Color(50, 150, 200));//azul
		mainpanel.setBackground(new Color(50, 150, 200));//azul
		
		oferta = new JTextField();
		oferta.setText("OFERTAS RECIENTES");
		oferta.setEditable(false);
		oferta.setHorizontalAlignment(JTextField.CENTER);
		oferta.setFont(new Font("Times new Roman", Font.BOLD, 25));
		oferta.setBackground(new Color(255, 255, 255)); //blanco
		add(oferta, BorderLayout.NORTH);
		
		
		
	}
	private void abriroferta(int numero) {
		JFrame ventanaoferta = new JFrame("Oferta" + numero);
		ventanaoferta.setSize(800, 500);
		ventanaoferta.setLocationRelativeTo(this);
		ventanaoferta.setLayout(new BorderLayout(10, 10));
		
		//CAMBIAR EL HOTEL PARA QUE SEA UNO DE LA LISTA DE HOTELES RANDOM
		//Descripciones para cada oferta (predefinidas)
		 String[] descripciones = {
				 
		        //CARIBE
				 "¡Descubre las playas paradisíacas del Caribe! \nIncluye vuelo directo desde Madrid con estancia en el Hotel Palace Aurora (4⭐) junto con un todo incluido. " 
		        + "Disfruta de aguas cristalinas, arena blanca, café de origen, ritmo caribeño y clima perfecto durante todo el año." + "Este maravilloso paquete incluye un city tour"
		        + "por La Candelaria, ruta del cafe y cata de origen, visita a la isla colombiana de San Andres, famosa por su 'mar de los 7 colores', alli podras hacer snorkel "
		        + "y ver una enorme diversidad de vida marina, tambien visitaras Cartagena de Indias donde apreciaras la ciudad amurallada y las Islas del Rosario.",
		        
		        //PARIS
		        "Escapada romántica a París, 'La Ciudad de la Luz' y 'La Ciudad del Amor'. \n Visita la Torre Eiffel, el Louvre y pasea por los Campos Elíseos. " 
		        + "Incluye vuelo + estancia en Suites Andres (4★), desayuno continental y tour guiado por la ciudad, asi como crucero al atardecer por el rio Sena y dia completo en "
		        + "Disneyland® Paris (opcion de familias).",
		        
		        //ALPES
		        "Aventura en los Alpes Suizos con estancia en el Hotel Montaña (5★) ubicado en Zurich, Suiza. \nMontañas nevadas, lagos "
		        + "turquesa y pueblos encantadores te esperan. \nEste paquete inclute vuelo directo desde Madrid, entrada para el teleférico "
		        + "panorámico por Lucerna y Monte Pilatus, dia completo por Interlaken & Jungfraujoch, Titlis: puente colgante y glaciar, "
		        + "tren panoramico Glacier Express, cata de comida tradicional como el fondue suizo o el chocolate artesanal entre otras muchas actividades. ",
		        
		        //ROMA
		        "El Resort Victoria (5★) te espera en Roma. \nNo te pierdas la historia viva, las plazas y la gran gastronoia italiana. \nIncluye catas de vino, "
		        + "visita al Vaticano, dia completo en Florencia, Napoles o Pompeya, clases de cocina italiana, una inigualable ruta gastronomica por Trastevere "
		        + "y visitas a los monumentos más emblemáticos como el Coliseo.",
		        
		        //TORONTO
		        "Siente la energía de Toronto: lagos, rascacielos y la excursión a Niágara. Vuela desde Madrid y alójate en el Hotel Plaza Sol (3★) al mejor precio. "
		        + "\nToronto combina rascacielos, barrios multiculturales, y el encanto del Lago Ontario. Es una base perfecta para disfrutar de museos, deporte, "
		        + "gastronomia internacional y, por supuesto, la excursion mas famosa de Canadá: La visita a las Cataratas del Niagara, una experiencia inolvidable "
		        + "que podreis disfrutar en familia. \n A parte de las mencionadas anteriormente, tambien incluye un visita a CN Tower, Harbourfront, Distillery District "
		        + "y St. Lawrence Market. ",
		        
		        //TOKIO
		        "Tokio es un contraste perfecto entre templos milenarios y barrios futuristas. Desde Asakusa a Shibuya, la ciudad vibra 24/7 con izakayas, sushi de "
		        + "mercado y centros comerciales infinitos. Ideal para foodies, parejas y viajeros curiosos. Tokio en 4 días: ¡vive Japón a tu ritmo con vuelo desde "
		        + "Madrid y estancia en uno de los mejores hoteles de la ciudad, el Hotel Bahia (5★). \nPodras valorar Tokyo desde el Mirador Tokyo Skytree y presenciar "
		        + "la cantidad de gente que transcurre por el cruce de Shibuya. Podras apreciar la cultura pop y tech asi como visitar el templo de Nikko y la ciudad de "
		        + "Kamakura. Tambien podras pasar un dia en Hakone y ver las vistas del Monte Fuji." ,
		        
		        "Hotel Palace Pacifico (4⭐) en Bangkok + vuelo directo desde Madrid. \nCultura milenaria, templos "
		        + "dorados y vida callejera vibrante. Ideal para combinar ciudad + naturaleza, con opción de extensión "
		        + "a Bali uno de los destinos mas aclamados de Tailandia. " + "Este paquete incluye aparte del vuelo y hotel, "
		        + "excuriones como: Tours guiados por el Gran Palacio, Wat Pho y Wat Arun, un dia completo por las ruinas de "
		        + "Ayutthaya, visita a los mercados flotantes, spa, yoga matinal, excursiones a arrozales y visita a la faosa ciudad de Ubud.",
		        
		        //NEW YORK
		        "Hotel Resort Imperial (4⭐) en Nueva York + vuelo directo desde Madrid. \n" + "'La ciudad que nunca duerme' ofrece rascacielos, "
		        + "cultura pop, museos, musicales de Broadway, barrios iconicos como Times Square, Central Park y diversidad de planes para "
		        + "todo tipo de viajeros. \n"+ "El paquete incluye diferentes excursiones como: Paseo en ferry hasta la Estatua de la Libertad, "
		        + "entradas a musicales, tour por Manhattan, ruta gastronomica y subida al Empire State.",
		        
		        //OSLO
		        "Oslo te espera con aire puro, arquitectura vanguardista y el encanto de los fiordos noruegos. Disfruta de una escapada nórdica desde Madrid con vuelos directos "
		        + "y alojamiento 3★ en el Hotel Plaza Laguna. ¡Descubre el norte en su estado más puro. \nOslo combina modernidad y naturaleza salvaje en una ciudad compacta y "
		        + "acogedora. Entre sus montañas, fiordos y museos, descubriras una capital limpia, segura y culturalmente vibrante. Perfecta para quienes buscan una escapada "
		        + "tranquila, sostenible y con aire fresco. \nEl paquete incluye excursiones como visita al museo Munch, visita a la futurista Opera de Oslo, visita al puerto "
		        + "de Aker Brygger entre mutras otras mas."
		        
		    };
		
		JLabel titulo = new JLabel("Detalles de la Oferta " + numero, SwingConstants.CENTER);
		titulo.setFont(new Font("Times new Roman", Font.PLAIN, 22));
		ventanaoferta.add(titulo, BorderLayout.NORTH);
		
		JPanel contenidoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel imagenLabel = new JLabel();
	    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    //Ruta imagen
	    //Habra que añadir un try / catch por si no la encuentra o no la carga
	    String rutaImagen = "images/Oferta" + numero + ".png";
	    try {
	    	Image originalImage = ImageIO.read(new File(rutaImagen));
	    	
	        // Falta colocal la imagen a la derecha (BorderLayout.EAST) - PARA AÑADIR ABAJO BOTON DE RESERVA
	        // Define las dimensiones que quieres para la imagen en la ventana (ej: 400x300)
	    	
	        int anchoDeseado = 300; 
	        int altoDeseado = 300; 
	        
	        //Ajusta la imagen
	        Image scaledImage = originalImage.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
	        ImageIcon imageIcon = new ImageIcon(scaledImage);
	        
	        imagenLabel.setIcon(imageIcon);
	        imagenLabel.setText(null);
	    } catch (IOException e) {
	    	imagenLabel.setText("Imagen no encontrada: " + rutaImagen);
	        imagenLabel.setForeground(Color.RED);
	        System.err.println("Error al cargar imagen " + rutaImagen + ": " + e.getMessage());
	    	}	
	    
	    JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelImagen.add(imagenLabel);
//	    ventanaoferta.add(panelImagen,BorderLayout.NORTH);
	    
		//Descripcion
		JTextArea descripcionArea = new JTextArea();
		descripcionArea.setText(descripciones[numero - 1]);
        descripcionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setEditable(false);
        descripcionArea.setBackground(new Color(250, 250, 250));
        descripcionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);
//      ventanaoferta.add(scrollDescripcion, BorderLayout.CENTER);
        
        contenidoPanel.add(scrollDescripcion);
        contenidoPanel.add(panelImagen);
        ventanaoferta.add(contenidoPanel, BorderLayout.CENTER);
        
		JPanel centro = new JPanel(new BorderLayout());
		JLabel imagen = new JLabel();
		imagen.setOpaque(true);
		imagen.setBackground(new Color(200, 220, 255));
		imagen.setPreferredSize(new Dimension(200, 150));
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setText("Imagen de la oferta " + numero);
		
		descripcionoferta = new JTextField();
		descripcionoferta.setText("");
		
		JButton btnReservar = new JButton("Reservar esta oferta");
		btnReservar.setFont(new Font("Arial", Font.BOLD, 16));
		btnReservar.setBackground(new Color(0, 128, 0));
		btnReservar.setForeground(Color.WHITE);
		btnReservar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO CREAR VENTANA PARA PROCESAR LA RESERVA
				
			}
			
		});
		
		JPanel panelBotonReserva = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBotonReserva.add(btnReservar);
		panelBotonReserva.setBackground(new Color(50, 150, 200)); 
		ventanaoferta.add(panelBotonReserva, BorderLayout.SOUTH);
		
		
		ventanaoferta.setVisible(true);
	}
	
	//Lectura del CSV
		public HashMap<String, List<Hotel>> cargarHotelesDesdeCSV() {
		    HashMap<String, List<Hotel>> hotelesPorCiudad = new HashMap<>();
		    
		    try (BufferedReader br = new BufferedReader(new FileReader("hoteles_mundiales_variedad_EUR.csv"))) {
		        String linea;
		        int numeroLinea = 0;
		        
		     // Saltar la cabecera
		        while ((linea = br.readLine()) != null) {
		        	numeroLinea++;
		        	if (numeroLinea <= 4)
		                continue;
		            
		          
		            String[] datos = linea.split(",");
		            
		            if (datos.length >= 6) {
		                String nombre = datos[0].trim();
		                String ciudad = datos[1].trim();
		                String pais = datos[2].trim();
		                int estrellas = Integer.parseInt(datos[3].trim());
		                int habitaciones = Integer.parseInt(datos[4].trim());
		                double precio = Double.parseDouble(datos[5].trim());
		                
		                Hotel hotel = new Hotel(nombre, ciudad, pais, estrellas,habitaciones, precio);
		                
		                // Añadir al HashMap
		                hotelesPorCiudad.putIfAbsent(ciudad, new ArrayList<>());
		                hotelesPorCiudad.get(ciudad).add(hotel);
		            }
		        }
		        } catch (IOException e) {
		        System.err.println("Error al leer el archivo: " + e.getMessage());
		        } catch (NumberFormatException e) {
		        System.err.println("Error al convertir datos: " + e.getMessage());
		        }
		    
			    return hotelesPorCiudad;
			}
	
	//HOTEL MÁS BARATO
	public Hotel obtenerHotelMasBaratoDeCiudad(HashMap<String, List<Hotel>> hotelesPorCiudad, String ciudad) {
		List<Hotel> hotelesDisponibles = hotelesPorCiudad.get(ciudad);
	    
	    if (hotelesDisponibles == null || hotelesDisponibles.isEmpty()) {
	        System.err.println("No hay hoteles disponibles en " + ciudad);
	        return null;
	    }
	    
	    Hotel hotelMasBarato = hotelesDisponibles.get(0);
	    
	    for (Hotel hotel : hotelesDisponibles) {
	        if (hotel.getPrecio() < hotelMasBarato.getPrecio()) {
	            hotelMasBarato = hotel;
	        }
	    }
	    
	    return hotelMasBarato;
	}
		
	//MÁS BARATO por ciudad con multiples
		public List<Hotel> obtenerHotelesMasBaratosMultiplesCiudades(HashMap<String, List<Hotel>> hotelesPorCiudad, String[] ciudades) {
		    List<Hotel> hotelesSeleccionados = new ArrayList<>();
		    
		    for (String ciudad : ciudades) {
		        Hotel hotelMasBarato = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, ciudad);
		        
		        if (hotelMasBarato != null) {
		            hotelesSeleccionados.add(hotelMasBarato);
		        } else {
		            System.err.println("No se pudo obtener hotel para: " + ciudad);
		        }
		    }
		    
		    return hotelesSeleccionados;
		}
	
		// Método  para obtener los hoteles MÁS BARATOS según el número de oferta
//		public List<Hotel> obtenerHotelesParaOferta(HashMap<String, List<Hotel>> hotelesPorCiudad, int numeroOferta) {
//		    List<Hotel> hotelesOferta = new ArrayList<>();
//		    
//		    switch (numeroOferta) {
//		        case 1: // Caribe (Colombia: Bogotá, San Andrés, Cartagena)
//		            String[] ciudadesCaribe = {"Bogota", "San Andres", "Cartagena"};
//		            hotelesOferta = obtenerHotelesMasBaratosMultiplesCiudades(hotelesPorCiudad, ciudadesCaribe);
//		            break;
//		            
//		        case 2: // París
//		            Hotel hotelParis = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, "Paris");
//		            if (hotelParis != null) hotelesOferta.add(hotelParis);
//		            break;
//		            
//		        case 3: // Suiza (Zurich, Lucerna, Interlaken)
//		            String[] ciudadesSuiza = {"Zurich", "Lucerna", "Interlaken"};
//		            hotelesOferta = obtenerHotelesMasBaratosMultiplesCiudades(hotelesPorCiudad, ciudadesSuiza);
//		            break;
//		            
//		        case 4: // Roma (Roma, Florencia, Nápoles)
//		            String[] ciudadesItalia = {"Roma", "Florencia", "Napoles"};
//		            hotelesOferta = obtenerHotelesMasBaratosMultiplesCiudades(hotelesPorCiudad, ciudadesItalia);
//		            break;
//		            
//		        case 5: // Toronto
//		            Hotel hotelToronto = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, "Toronto");
//		            if (hotelToronto != null) hotelesOferta.add(hotelToronto);
//		            break;
//		            
//		        case 6: // Tokio (Tokio, Nikko, Kamakura, Hakone)
//		            String[] ciudadesJapon = {"Tokyo", "Nikko", "Kamakura", "Hakone"};
//		            hotelesOferta = obtenerHotelesMasBaratosMultiplesCiudades(hotelesPorCiudad, ciudadesJapon);
//		            break;
//		            
//		        case 7: // Bangkok (Bangkok, Ayutthaya, Ubud)
//		            String[] ciudadesTailandia = {"Bangkok", "Ayutthaya", "Ubud"};
//		            hotelesOferta = obtenerHotelesMasBaratosMultiplesCiudades(hotelesPorCiudad, ciudadesTailandia);
//		            break;
//		            
//		        case 8: // Nueva York
//		            Hotel hotelNY = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, "Nueva York");
//		            if (hotelNY != null) hotelesOferta.add(hotelNY);
//		            break;
//		            
//		        case 9: // Oslo
//		            Hotel hotelOslo = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, "Oslo");
//		            if (hotelOslo != null) hotelesOferta.add(hotelOslo);
//		            break;
//		            
//		        default:
//		            System.err.println("Número de oferta no válido: " + numeroOferta);
//		    }
//		    
//		    return hotelesOferta;
//		}
		
	static class Hotel {
        private String nombre;
        private String ciudad;
        private String pais;
        private int estrellas;
        private int habitaciones;
        private double precio;
        
        public Hotel(String nombre, String ciudad, String pais, int estrellas, int habitaciones, double precio) {
            this.nombre = nombre;
            this.ciudad = ciudad;
            this.pais = pais;
            this.estrellas = estrellas;
            this.habitaciones = habitaciones;
            this.precio = precio;
        }
        
        public String getNombre() { return nombre; }
        public String getCiudad() { return ciudad; }
        public String getPais() {return pais;}
        public int getHabitaciones() {return habitaciones;}
        public int getEstrellas() { return estrellas; }
        public double getPrecio() { return precio; }

	}
	public static void main(String[] args) {
		CuadriculaOfertas ofertas = new CuadriculaOfertas();
		ofertas.setVisible(true);
		}
	
}

