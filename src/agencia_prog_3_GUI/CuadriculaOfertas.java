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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;
import java.util.List;
import agencia_prog_3_thread.VentanaConfirmacionReserva;

//FALTA
//Añadir boton de reserva para que acabe de confirmar la reserva con los datos necesarios y añadirla a su perfil

public class CuadriculaOfertas extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField oferta;
	private JTextField descripcionoferta;
	private HashMap<String, List<Hotel>> hotelesPorCiudad;
	
	String[] ciudadesOferta = {
		    "Ciudad de México",      
		    "París",       
		    "Zúrich",       
		    "Roma",        
		    "Toronto",     
		    "Tokio",       
		    "Bangkok",     
		    "Nueva York",  
		    "Oslo"         
		};
	
	/**IAG - Descripciones generadas por Gemini y ChatGPT
	 * 
	 */
	String[] descripcionesOfertas = {
			 
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
	        
	        //Bangkok
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

	    
	    public class VentanaReserva extends JFrame {
	    	private static final long serialVersionUID = 1L;
	    	
	    	private double precioBaseNoche; 
	    	private JLabel lblPrecioTotal;
	    	private JLabel lblHotelSeleccionado;
	    	private Hotel hotelSeleccionado;
	    	private JTextField txtNombre;
	    	private JTextField txtEmail;
	    	private JSpinner spinAdultos; // Para ajustar los numeros de 1 hasta ...
	    	private JSpinner spinNinos;
	    	private JComboBox<String> cmbHabitacion;
	    	private JSpinner spinFechaIda;
	    	private JSpinner spinFechaVuelta;
	    	private String ciudadSeleccionada;
	    	
	    	private double precioFinalCalculado = 0.0;
	    	    
	    public VentanaReserva(String ciudadSeleccionada, double precioBaseNoche) { 
	                this.ciudadSeleccionada = ciudadSeleccionada;
	                this.precioBaseNoche = precioBaseNoche;
	                this.hotelSeleccionado = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, ciudadSeleccionada);;
	                
	                if (hotelSeleccionado != null) {
	                    this.precioBaseNoche = hotelSeleccionado.getPrecio();
	                } else {
	                    this.precioBaseNoche = 0.0;
	                }
	                
	        
	        setTitle("Confirmar Reserva: " + ciudadSeleccionada);
	        setSize(550, 450);
	        setLocationRelativeTo(null); 
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	        setLayout(new BorderLayout(10, 10)); 
	        
	        //TÍTULO
	        JLabel lblTitulo = new JLabel("Reserva para el viaje a " + ciudadSeleccionada, JLabel.CENTER);
	        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
	        lblTitulo.setForeground(new Color(50, 150, 200)); 
	        add(lblTitulo, BorderLayout.NORTH);

	        //PANEL CENTRAL (CAMPOS DE DATOS)
	        JPanel panelDatos = new JPanel(new GridLayout(9, 2, 10, 10));
	        panelDatos.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
	        
	        //Nombre
	        panelDatos.add(new JLabel("Nombre Completo:"));
	        txtNombre = new JTextField(20);
	        panelDatos.add(txtNombre);

	        //Email
	        panelDatos.add(new JLabel("Email de Contacto:"));
	        txtEmail = new JTextField(20);
	        panelDatos.add(txtEmail);

	        //Tipo de Habitación
	        panelDatos.add(new JLabel("Tipo de Habitación:"));
	        String[] tiposHabitacion = {"Doble Estándar", "Doble Superior", "Familiar", "Suite"};
	        cmbHabitacion = new JComboBox<>(tiposHabitacion);
	        panelDatos.add(cmbHabitacion);

	        //Adultos
	        panelDatos.add(new JLabel("Adultos (18+):"));
	        SpinnerNumberModel modelAdultos = new SpinnerNumberModel(1, 1, 10, 1);
	        spinAdultos = new JSpinner(modelAdultos);
	        panelDatos.add(spinAdultos);

	        // Niños
	        panelDatos.add(new JLabel("Niños (menores de 12):"));
	        SpinnerNumberModel modelNinos = new SpinnerNumberModel(0, 0, 10, 1);
	        spinNinos = new JSpinner(modelNinos);
	        panelDatos.add(spinNinos);
	        
	        //Fecha de Ida (A partir de hoy)
	        panelDatos.add(new JLabel("Fecha de Salida:"));
	        Calendar calendario = Calendar.getInstance();
	        
	        SpinnerDateModel modelIda = new SpinnerDateModel( calendario.getTime(), null, null, java.util.Calendar.DAY_OF_MONTH);
	        spinFechaIda = new JSpinner(modelIda);
	        JSpinner.DateEditor editorIda = new JSpinner.DateEditor(spinFechaIda, "dd/MM/yyyy");
	        spinFechaIda.setEditor(editorIda);
	        panelDatos.add(spinFechaIda);
	        calendario.add(Calendar.DATE, 1); //Calculamos el día de mañana
	        
	        //Fecha de Vuelta
	        panelDatos.add(new JLabel("Fecha de Regreso:"));
	        SpinnerDateModel modelVuelta = new SpinnerDateModel( calendario.getTime(), null, null, java.util.Calendar.DAY_OF_MONTH);
	        spinFechaVuelta = new JSpinner(modelVuelta);
	        JSpinner.DateEditor editorVuelta = new JSpinner.DateEditor(spinFechaVuelta, "dd/MM/yyyy");
	        spinFechaVuelta.setEditor(editorVuelta);
	        panelDatos.add(spinFechaVuelta);
	        
	        //HOTEL
	        lblHotelSeleccionado = new JLabel("");
	        lblHotelSeleccionado.setFont(new Font("Arial", Font.ITALIC, 14));
	        lblHotelSeleccionado.setForeground(new Color(50, 150, 200)); // Color azul
	        
	        panelDatos.add(new JLabel("HOTEL ELEGIDO:")); // Etiqueta
	        panelDatos.add(lblHotelSeleccionado); // Valor
	        
	        //Precio TOTAL
	        lblPrecioTotal = new JLabel("Precio Total Estimado: Calculando...");
	        lblPrecioTotal.setFont(new Font("Arial", Font.BOLD, 16));
	        lblPrecioTotal.setForeground(new Color(255, 69, 0)); // Naranja fuerte
	        panelDatos.add(new JLabel("PRECIO TOTAL:"));
	        panelDatos.add(lblPrecioTotal);
	        
	        //Hotel SELECCIONADO
	        mostrarHotelSeleccionado(); // <--- NUEVA LLAMADA
	        actualizarPrecioTotal();
	        
	        add(panelDatos, BorderLayout.CENTER);
	        
	        
	        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
	        
	        JButton btnConfirmar = new JButton("CONTINUAR");
	        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
	        btnConfirmar.setBackground(new Color(0, 128, 0)); 
	        btnConfirmar.setForeground(Color.WHITE);
	        
	        JButton btnCancelar = new JButton("Cancelar");
	        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
	        btnCancelar.setBackground(new Color(200, 50, 50)); 
	        btnCancelar.setForeground(Color.WHITE);

	        btnConfirmar.addActionListener(e -> {
	        	
	        	String nombreUsuario = txtNombre.getText().trim();
	            
	            if (nombreUsuario.isEmpty() || txtEmail.getText().trim().isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Por favor, rellena tu nombre y email.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            if (txtNombre.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Por favor, rellena tu nombre y email.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	            
	            JOptionPane.showMessageDialog(this, 
	                "Reserva de " + txtNombre.getText() + " para " + ciudadSeleccionada + " solicitada.", 
	                "Reserva en proceso", 
	                JOptionPane.INFORMATION_MESSAGE);
	            
	            String ciudad = ciudadSeleccionada;
	            String hotel = (hotelSeleccionado != null) ? hotelSeleccionado.getNombre() : "N/A";
	            String email = txtEmail.getText().trim();
	            String hab = (String) cmbHabitacion.getSelectedItem();
	            int adultos = (int) spinAdultos.getValue();
	            int ninos = (int) spinNinos.getValue();
	            Date salida = (Date) spinFechaIda.getValue();
	            Date regreso = (Date) spinFechaVuelta.getValue();
	            // Usamos la variable de clase actualizada por actualizarPrecioTotal()
	            double precio = this.precioFinalCalculado; 

	            // 2. Llamar al método de guardado
	            guardarReservaEnCSV(nombreUsuario, ciudad, hotel, email, hab, adultos, ninos, salida, regreso, precio);
	            
	            // 3. Confirmar y cerrar
	            JOptionPane.showMessageDialog(this, 
	                "Reserva de " + nombreUsuario + " para " + ciudad + " confirmada.", 
	                "Reserva Exitosa", 
	                JOptionPane.INFORMATION_MESSAGE);
	            dispose();
	            VentanaConfirmacionReserva vConfirmacion = new VentanaConfirmacionReserva();
	            vConfirmacion.setVisible(true);
	        });
 
	        btnCancelar.addActionListener(e -> {
	            dispose(); 
	        });
	        
	        panelBotones.add(btnConfirmar);
	        panelBotones.add(btnCancelar);
	        add(panelBotones, BorderLayout.SOUTH);
	        
	        cmbHabitacion.addActionListener(e -> actualizarPrecioTotal());
	        spinAdultos.addChangeListener(e -> actualizarPrecioTotal());
	        spinNinos.addChangeListener(e -> actualizarPrecioTotal());
	        spinFechaIda.addChangeListener(e -> actualizarPrecioTotal());
	        spinFechaVuelta.addChangeListener(e -> actualizarPrecioTotal());
	        cerrarventana2();
	    }
	    
	    
	   private void mostrarHotelSeleccionado() {
	        if (hotelSeleccionado != null) {
	            String estrellas = "*".repeat(hotelSeleccionado.getEstrellas());
	            String infoHotel = String.format("%s (%s)", 
	                hotelSeleccionado.getNombre(), 
	                estrellas);
	            lblHotelSeleccionado.setText(infoHotel);
	        } else {
	            lblHotelSeleccionado.setText("Hotel no disponible.");
	        	}
	    	}
	   
		private void actualizarPrecioTotal() {
	        //DURACION
	        Date fechaIda = (Date) spinFechaIda.getValue();
	        Date fechaVuelta = (Date) spinFechaVuelta.getValue();
	        
	       int duracionViaje = diasEntreFechas(fechaIda, fechaVuelta);
	        if ( duracionViaje  <= 0) {
	            lblPrecioTotal.setText("Error: Fechas incorrectas.");
	            return;
	        }
	
	        // Número de personas
	        int adultos = (int) spinAdultos.getValue();
	        double ninos = (int) spinNinos.getValue()/0.5;
	        double totalPersonas = adultos + ninos;
	
	        // Tipo de habitación
	        double factorHabitacion = 1.0;
	        String tipoHabitacion = (String) cmbHabitacion.getSelectedItem();
	        
	        switch (tipoHabitacion) {
	            case "Doble Estándar": factorHabitacion = 1.0; break;
	            case "Doble Superior": factorHabitacion = 1.2; break;
	            case "Familiar": factorHabitacion = 1.4; break;
	            case "Suite": factorHabitacion = 1.7; break;
	        }
	
	        //PRECIO TOTAL
		        // Fórmula: (PrecioBaseNoche * FactorHabitación * TotalPersonas * Días)
		        // Usamos el precio base por habitación del hotel, y luego multiplicamos por personas.
		        // Asumimos que el precioBaseNoche del CSV es por persona y noche, o por habitación doble.
		        // Lo trataremos como Precio por Habitación Estándar por Noche:
	        
	        double totalPersonasFactor = adultos + ninos;
	        
	        double precioFinal = (precioBaseNoche * factorHabitacion) * totalPersonasFactor * duracionViaje;
	        
	        this.precioFinalCalculado = precioFinal;
	        
	        // Si hay más de 2 adultos/niños, cobramos un extra por persona adicional
	        double extraPersonas = Math.max(0, totalPersonas - 2) * 0.3; // 30% extra por persona adicional (simplificado)
	        
	
	        lblPrecioTotal.setText(String.format("%.2f € (%d días)", precioFinal, duracionViaje));
	    }
		
		
		private void cerrarventana2() {
		    KeyAdapter listener = new KeyAdapter() {
		        @Override
		        public void keyPressed(KeyEvent e) {
		            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
		                dispose(); // cierra VentanaReserva
		            }
		        }
		    };
		    this.addKeyListener(listener);
		    this.setFocusable(true);
		    txtNombre.addKeyListener(listener);
		    txtEmail.addKeyListener(listener);
		    spinAdultos.addKeyListener(listener);
		    spinNinos.addKeyListener(listener);
		    spinFechaIda.addKeyListener(listener);
		    spinFechaVuelta.addKeyListener(listener);
		    cmbHabitacion.addKeyListener(listener);
		}		
}
	    
	public CuadriculaOfertas() {
		/**
		 * Ayuda de ña IAG para que el tool tip saliese mas rapido
		 */
		ToolTipManager.sharedInstance().setInitialDelay(0); 
		ToolTipManager.sharedInstance().setDismissDelay(8000);
		
		
		this.hotelesPorCiudad = cargarHotelesDesdeCSV();
		setLayout(new BorderLayout());
		setTitle("OFERTAS RECIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
		mainpanel.setBackground(new Color(50, 150, 200));
		add(mainpanel, BorderLayout.CENTER);
		
		oferta = new JTextField("OFERTAS RECIENTES");
		oferta.setEditable(false);
		oferta.setHorizontalAlignment(JTextField.CENTER);
		oferta.setFont(new Font("Times New Roman", Font.BOLD, 25));
		oferta.setBackground(Color.WHITE); //blanco
	    oferta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		mainpanel.add(oferta, BorderLayout.NORTH);
		
		JPanel panel1 = new JPanel(new GridLayout(3, 3, 20, 20));
		panel1.setBackground(new Color(50, 150, 200));
		
		JButton botonInicio = new JButton();
		ImageIcon iconoCasa = null;
		Image imageIcon = null;

		try {
		    imageIcon = ImageIO.read(new File("resources/images/icono_casa.png"));

		    int anchoDeseado = 30; //30 píxeles de ancho
		    int altoDeseado = 30;  //30 píxeles de alto

		    Image scaledImage = imageIcon.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
		    iconoCasa = new ImageIcon(scaledImage);
		    botonInicio.setIcon(iconoCasa);
		    botonInicio.setPreferredSize(new Dimension(anchoDeseado, altoDeseado));
		} catch (IOException e) {
		    // Si la imagen no se encuentra o hay un error de lectura
		    System.err.println("¡ERROR! No se pudo cargar o redimensionar la imagen 'icono_casa.png': " + e.getMessage());
		    System.err.println("La ruta actual intentada fue: resources/images/icono_casa.png");
		    
		    // Como alternativa, el botón tendrá solo texto
		    botonInicio.setText("Atras");
		    botonInicio.setPreferredSize(new Dimension(80, 30)); // Un tamaño razonable para el texto
		    botonInicio.setBorderPainted(true); // Vuelve a poner el borde si hay texto
		    botonInicio.setContentAreaFilled(true);
		    
		} catch (IllegalArgumentException e) {
		    // Esto podría pasar si la imagen no se lee correctamente y originalImage es null
		    System.err.println("¡ERROR! La imagen leída era nula o inválida: " + e.getMessage());
		    botonInicio.setText("Atras");
		    botonInicio.setPreferredSize(new Dimension(80, 30));
		    botonInicio.setBorderPainted(true);
		    botonInicio.setContentAreaFilled(true);
		}
				
				botonInicio.addActionListener(new ActionListener() {
					@Override
				    public void actionPerformed(ActionEvent e) {

				        dispose(); 
				        VentanaInicio vInicio = new VentanaInicio();
				        vInicio.setVisible(true);
				    }
				}
				);
				
				JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
				panelSur.setBackground(new Color(50, 150, 200));
				panelSur.add(botonInicio);
				mainpanel.add(panelSur, BorderLayout.SOUTH);

		
		for (int i = 0; i < 9; i++) {
			int numero = i + 1;
			ImageIcon iconoOriginal = null;
			ImageIcon iconoSuperior = null;
			Image originalImage = null;
			
			try {
	            // Carga la imagen original
	            originalImage = ImageIO.read(new File("resources/images/Oferta"+numero+".png"));
	            
	            int anchoDeseado = 110; 
	            int altoDeseado = 80;  
	            
	            Image scaledImage = originalImage.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
	            iconoOriginal = new ImageIcon(scaledImage);
	            
				
	        } catch (IOException e) {
	            System.err.println("Error al cargar imagen " + "resources/images/Oferta"+numero+".png" + ": " + e.getMessage());
	            iconoOriginal = null; 
	        }
			
			try {
		        Image originalImageHover = ImageIO.read(new File("resources/images/Oferta" + numero + "_sup.jpg"));
		        
		        if (originalImageHover != null) {
			        int anchoIconSup = 200;
		            int altoIconSup = 140;
		        
			        Image scaledImageHover = originalImageHover.getScaledInstance(anchoIconSup, altoIconSup, Image.SCALE_SMOOTH);
			        iconoSuperior = new ImageIcon(scaledImageHover);
		        } else {
		        	iconoSuperior = iconoOriginal;
		        }
		        
		    } catch (IOException e) {
		        System.err.println("Error al cargar imagen HOVER Oferta" + numero + "_Hover.png: " + e.getMessage());
		        // Si no se encuentra la imagen de hover, usa la imagen normal
		        iconoSuperior = iconoOriginal; 
		    }
			
			JButton boton = new JButton(ciudadesOferta[i], iconoOriginal);
			boton.setPreferredSize(new Dimension(200, 120));
			boton.setHorizontalTextPosition(SwingConstants.CENTER); // Centra horizontalmente el texto
			boton.setVerticalTextPosition(SwingConstants.BOTTOM);
			
			String desc = descripcionesOfertas[i];
				String descripcioncorta;
				if (desc.length() > 180) {
				    descripcioncorta = desc.substring(0, 180) + "...";
				} else {
				    descripcioncorta = desc;
				}
				final String tooltipHtml =
					    "<html><div style='width:320px; padding:6px;'>" +
					    "<b>" + ciudadesOferta[i] + "</b><br><br>" +
					    descripcioncorta.replace("\n", "<br>") +
					    "</div></html>";
				
			
			boton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						abriroferta(numero);
						CuadriculaOfertas.this.dispose();
						}
					});
			final ImageIcon iconoInicial = iconoOriginal;
			final ImageIcon iconoAlternativo = iconoSuperior;
			final JButton botonActual = boton;

			boton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent evt) {
					// 1. CAMBIAR IMAGEN (a la versión ligeramente más grande)
					botonActual.setIcon(iconoAlternativo); 
					
					botonActual.setBackground(new Color(200, 220, 255)); // Fondo azul claro
					botonActual.setBorder(new LineBorder(new Color(0, 100, 255), 3)); // Borde azul resaltado
					/**
					 * IAG - tool tip generado con ayuda de ChatGPT		
					 */
					botonActual.setToolTipText(tooltipHtml);
					ToolTipManager.sharedInstance().mouseMoved(
							new MouseEvent(
									botonActual,
									MouseEvent.MOUSE_MOVED,
									System.currentTimeMillis(),
									0,
									10, 10, 0, false
	               ) );
				}
				
				@Override
				public void mouseExited(MouseEvent evt) {
					botonActual.setIcon(iconoInicial); 
			
					botonActual.setBackground(new Color(255, 255, 255)); // Fondo blanco
				}
			});
				
				panel1.add(boton);	
				
				
				
			}
		
		
		mainpanel.add(panel1, BorderLayout.CENTER);
		
		cerrarventana();

		
	}
	
	private void abriroferta(int numero) {
		JFrame ventanaoferta = new JFrame(ciudadesOferta [numero-1]);
		ventanaoferta.setSize(800, 500);
		ventanaoferta.setLocationRelativeTo(this);
		ventanaoferta.setLayout(new BorderLayout(10, 10));
		ventanaoferta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		
		
		
		 
		
		JLabel titulo = new JLabel("Detalles del viaje a " + ciudadesOferta [numero-1], SwingConstants.CENTER);
		titulo.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		ventanaoferta.add(titulo, BorderLayout.NORTH);
		
		JPanel contenidoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel imagenLabel = new JLabel();
	    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    //Ruta imagen
	    //Habra que añadir un try / catch por si no la encuentra o no la carga
	    String rutaImagen = "resources/images/Oferta" + numero + ".png" ;
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
	    
		//Descripcion
		JTextArea descripcionArea = new JTextArea();
		descripcionArea.setText(descripcionesOfertas[numero - 1]);
        descripcionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setEditable(false);
        descripcionArea.setBackground(new Color(250, 250, 250));
        descripcionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);
        
        contenidoPanel.add(scrollDescripcion);
        contenidoPanel.add(panelImagen);
        ventanaoferta.add(contenidoPanel, BorderLayout.CENTER);
        
		JLabel imagen = new JLabel();
		imagen.setOpaque(true);
		imagen.setBackground(new Color(200, 220, 255));
		imagen.setPreferredSize(new Dimension(200, 150));
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setText("Imagen de la oferta " + numero);
		
		descripcionoferta = new JTextField();
		descripcionoferta.setText("");
		
		JButton btnReservar = new JButton("Reservar esta oferta");
		JButton btnRetorno = new JButton("Atras");
		btnReservar.setFont(new Font("Arial", Font.BOLD, 16));
		btnReservar.setBackground(new Color(0, 128, 0));
		btnReservar.setForeground(Color.WHITE);
		
		btnRetorno.setFont(new Font("Arial", Font.BOLD, 16));
		btnRetorno.setBackground(new Color(0, 128, 0));
		btnRetorno.setForeground(Color.WHITE);
		
		btnReservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        // Obtenemos la ciudad seleccionada
		        String ciudadElegida = ciudadesOferta [numero-1];
		        Hotel hotelBarato = obtenerHotelMasBaratoDeCiudad(hotelesPorCiudad, ciudadElegida);
		        double precioBase = 0.0;
		        if (hotelBarato != null) {
		            precioBase = hotelBarato.getPrecio();
		        } else {JOptionPane.showMessageDialog(ventanaoferta, "No hay datos de hoteles disponibles para " + ciudadElegida, "Error de Datos", JOptionPane.ERROR_MESSAGE);
	            return;
		        }
		        VentanaReserva vReserva = new VentanaReserva(ciudadElegida, precioBase); //PASAMOS EL PRECIO BASE
		        vReserva.setVisible(true);
		    }
			
		});
		
		btnRetorno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaoferta.dispose(); 
		        CuadriculaOfertas cuadOfert = new CuadriculaOfertas();
		        cuadOfert.setVisible(true);
		    }
	});
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelInferior.add(btnReservar);
		panelInferior.add(btnRetorno);
		ventanaoferta.add(panelInferior, BorderLayout.SOUTH);
		
		
		ventanaoferta.setVisible(true);
	}
	
	//Lectura del CSV
		public HashMap<String, List<Hotel>> cargarHotelesDesdeCSV() {
		    HashMap<String, List<Hotel>> hotelesPorCiudad = new HashMap<>();
		    
		    
		    try (BufferedReader br = new BufferedReader(new FileReader("resources/data/hoteles.csv"))) {
		    	String linea;
		        
		     // Saltar la cabecera
		        while ((linea = br.readLine()) != null) {
		            String[] datos = linea.split(",");
		            String nombre = datos[0].trim();
		            String ciudad = datos[1].trim();
		            String pais = datos[2].trim();
		                
		                //ANALISIS PARA VER DONDE FALLA
		             int estrellas;
		             int habitaciones;
		             double precio;
		             try {
		            	 	estrellas = Integer.parseInt(datos[3].trim());
			                habitaciones = Integer.parseInt(datos[4].trim());
			                String precioStr = datos[5].trim().replace(" EUR","").trim();
			                precio = Double.parseDouble(precioStr);
			                
		              } catch (NumberFormatException e) {
		                  System.err.println("Error de formato numérico ");
		                  continue; // Saltar línea con datos mal formados
		                }
		                
		                Hotel hotel = new Hotel(nombre, ciudad, pais, estrellas,habitaciones, precio);
		                hotelesPorCiudad.putIfAbsent(ciudad, new ArrayList<>());
		                hotelesPorCiudad.get(ciudad).add(hotel);
		            
		        		}
		    		} catch (IOException e) {
				        // IOException ahora solo captura errores *durante* la lectura del stream
				        System.err.println("Error de lectura de datos del CSV: " + e.getMessage());
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
	
	private void guardarReservaEnCSV(String usuario, String ciudad, String hotel, String email, 
            String hab, int adultos, int ninos, Date salida, Date regreso, 
            double precioFinal) {
                
		final String FILE_NAME = "resources/data/reservas.csv";
		// Formato de fecha estándar para CSV
		final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

		File file = new File(FILE_NAME);
		// Comprueba si el archivo es nuevo para añadir la cabecera
		boolean needsHeader = !file.exists() || file.length() == 0;

		// Usamos try-with-resources para asegurar que se cierre el writer
		try (FileWriter fw = new FileWriter(file, true); // true = modo append
				PrintWriter pw = new PrintWriter(fw)) {

			if (needsHeader) {
				// El "Usuario" es la primera columna, para filtrar en VentanaReservas
				pw.println("Usuario,Ciudad,Hotel,Email,Habitacion,Adultos,Ninos,Salida,Regreso,Precio");
			}

			// Formatea la línea CSV
			String csvLine = String.format("%s,%s,%s,%s,%s,%d,%d,%s,%s,%.2f",
					usuario,
					ciudad,
					hotel.replace(",", ";"), // Evita problemas si el nombre tiene comas
					email,
					hab,
					adultos,
					ninos,
					SDF.format(salida),
					SDF.format(regreso),
					precioFinal
					);

			// Escribe la nueva reserva
			pw.println(csvLine);

		} catch (IOException e) {
			System.err.println("Error al guardar la reserva en CSV: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int diasEntreFechas (Date inicio, Date fin) {
		return (int) ((fin.getTime() - inicio.getTime()) / 86400000);
	}
	
	
	private void cerrarventana() {
 	   KeyAdapter listener = new KeyAdapter() {
 		   @Override
 		public void keyPressed(KeyEvent e) {
 			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
 				dispose(); //se cierra
 			}
 		}
		};
		this.addKeyListener(listener);
 	setFocusable(true);
    }
	
	public static void main(String[] args) {
		CuadriculaOfertas ofertas = new CuadriculaOfertas();
		ofertas.setVisible(true);
		}
	
}
