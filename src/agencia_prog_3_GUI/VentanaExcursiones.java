698package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class VentanaExcursiones extends JFrame{
	private static final long serialVersionUID = 1L;
	String[] tituloexcursion = {
		    "Cataratas del Niagara",      
		    "Ruta del cafe",       
		    "Teleferico de Lucerna",       
		    "Visita al Vaticano",        
		    "Visita al templo de Nikko",     
		    "Dia en Bali",       
		    "Subida al Empire State",     
		    "Visita a la Opera de Oslo",  
		    "Visita a Florencia"         
		};
	String[] descrip = {
	        "Al acercarte a la zona de las cataratas, ya se escucha el rugido del agua cayendo con fuerza. "
	        + "Las cataratas están en la frontera entre Canadá y Estados Unidos, divididas por el río Niágara.\n"
	        + "La vista más famosa se encuentra en el lado canadiense (Ontario), desde donde se puede observar mejor "
	        + "el conjunto formado por:\n\n * Horseshoe Falls (la Herradura), la más grande y curvada.\n"
	        + " * American Falls (la Americana).\n * Bridal Veil Falls (el Velo de Novia), más pequeña "
	        + "pero muy pintoresca.\n \n Desde el paseo junto al río, el aire está cargado de humedad y se forma una niebla constante que brilla "
	        + "con el sol, creando arcoíris casi permanentes.",
	        
	        "La ruta comienza en Manizales, ciudad de montaña con aire fresco y vistas impresionantes del Nevado del Ruiz.\r\n"
	        + "Aquí visitaras las fincas cafeteras tradicionales donde los caficultores te explicaran cada etapa del proceso. \n"
	        + "Desde Manizales, el viaje continúa hacia el Quindío, pasando por Salento, un pueblo encantador de casas coloridas y balcones de flores.\r\n"
	        + "El Valle de Cocora, con sus palmas de cera gigantes, ofrece un paisaje de cuento. En Armenia, capital del Quindío, se encuentra el famoso "
	        + "Parque del Café, una mezcla entre parque temático y museo vivo del cultivo.\r\n"
	        + "Entre montañas y cafetales puedes:\n Ver recreaciones del proceso cafetero.\n Disfrutar de montañas rusas, teleféricos y miradores.\n"
	        + "Participar en una catación profesional, guiada por baristas expertos.\r\n El ambiente combina diversión familiar y aprendizaje sobre "
	        + "la cultura cafetera.",
	        
	        "Desde Lucerna, en Suiza, se toma el teleférico panorámico que asciende al Monte Pilatus, una de las montañas más emblemáticas del país.\n"
	        + "El recorrido empieza en Kriens, sube entre bosques y praderas hasta Fräkmüntegg, y luego continúa en el moderno Dragon Ride, que ofrece "
	        + "vistas espectaculares del lago de los Cuatro Cantones y los Alpes.\n En la cima (2.132 m) hay miradores, senderos y restaurantes "
	        + "con vistas impresionantes.\n El descenso puede hacerse en el tren cremallera más empinado del mundo, que baja hacia Alpnachstad.\n"
	        + "Una experiencia corta pero inolvidable, perfecta para disfrutar la esencia alpina de Suiza.",
	        
	        "El Vaticano, en el corazón de Roma, es el Estado más pequeño del mundo y el centro espiritual de la Iglesia Católica.\n"
	        + "Su joya principal es la Basílica de San Pedro, con la imponente cúpula de Miguel Ángel y la Plaza de San Pedro, diseñada por Bernini.\n"
	        + "Dentro, se puede admirar la Piedad, esculturas y mosaicos únicos.\n Los Museos Vaticanos guardan siglos de arte, culminando "
	        + "en la Capilla Sixtina, famosa por los frescos de Miguel Ángel.\n El ambiente mezcla silencio, historia y devoción, haciendo "
	        + "de la visita una experiencia cultural y espiritual inolvidable.",
	        
	        "El Santuario Tōshō-gū, en Nikkō, es uno de los templos más impresionantes de Japón. Rodeado de cedros milenarios, combina arquitectura "
	        + "tradicional con una decoración muy elaborada.\n Fue construido en honor a Tokugawa Ieyasu, fundador del shogunato Tokugawa.\r\n"
	        + "Sus puertas doradas, tallas de animales sagrados —como los famosos tres monos sabios— y la atmósfera de niebla entre los árboles "
	        + "crean un ambiente místico y solemne.\n Es Patrimonio de la Humanidad y una de las joyas espirituales del país.",
	        
	        "Sales de Bangkok al amanecer, vuelo rumbo a Bali con destino Denpasar. Al llegar, un guía te lleva directo a los arrozales de Tegallalang,"
	        + " donde el verde de las terrazas parece un océano.\n Luego visitas el Templo de Tirta Empul, participas en una ceremonia de purificación "
	        + "y almuerzas con vista al volcán Batur.\n Por la tarde, paseo por las playas de Seminyak y una puesta de sol mágica en Tanah Lot, con el "
	        + "templo flotando sobre las olas.\n Después de cenar frente al mar, te alojaras entre arrozales. \n Al dia siguiente"
	        + "desayuno temprano y salida hacia el Templo de Uluwatu, en lo alto de un acantilado sobre el océano Índico.\n Baño y relax en la playa de "
	        + "Padang Padang o Nusa Dua.\n Al atardecer, visita al Templo de Tanah Lot para disfrutar una de las puestas de sol más famosas del mundo.\n"
	        + "Cena junto al mar en Jimbaran antes del vuelo nocturno de regreso a Bangkok. ",
	        
	        "El Empire State Building, en el corazón de Manhattan, es uno de los símbolos más reconocidos de Nueva York.\n El ascensor lleva en segundos "
	        + "hasta los miradores del piso 86 o 102, desde donde se disfruta una vista panorámica de la ciudad: Central Park, Times Square, el río Hudson"
	        + " y, a lo lejos, la Estatua de la Libertad.\n De día, las vistas son nítidas y llenas de vida; de noche, el horizonte brilla con miles de luces.\n"
	        + "El interior conserva su estilo Art Déco, y el ambiente mezcla historia, glamour y emoción.\n Una visita imprescindible para sentir el alma "
	        + "de Nueva York desde las alturas.",
	        
	        "La Ópera de Oslo, situada frente al fiordo, es una joya moderna de la arquitectura nórdica.\n Su techo blanco de mármol inclinado permite"
	        + " caminar sobre él, ofreciendo vistas espectaculares del mar y la ciudad.\n En el interior, la madera y la luz natural crean un ambiente"
	        + " cálido y elegante.\n Es sede del Ballet y la Ópera Nacional de Noruega, con espectáculos de gran nivel internacional.\n"
	        + "Incluso sin asistir a una función, el edificio es una obra de arte en sí misma: símbolo de diseño, cultura y apertura al público.",
	        
	        "En solo 1 hora y media en tren rápido, se llega desde Roma a Florencia, cuna del Renacimiento.\n\n El recorrido puede comenzar en la Catedral"
	        + " de Santa María del Fiore, con su famosa cúpula de Brunelleschi, seguir por el Puente Vecchio y la Piazza della Signoria, rodeada de "
	        + "esculturas históricas.\r\n Una visita a la Galería de los Uffizi o al David de Miguel Ángel completa el día.\r\n Antes de regresar, nada "
	        + "mejor que un café o un helado frente al Arno, disfrutando del encanto toscano.\n Un día intenso entre arte, historia y belleza italiana."
	    };
	
	double[] precio = {89, 65, 70, 49, 72, 95, 38, 30, 59};
	private JTextField campoFiltro;
    private JTable tabla;
    private TableRowSorter<TableModel> ordena;
    private ExcursionTabla modelo;
	
	public VentanaExcursiones() {
		setTitle("Busqueda de excursiones");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));
		
		JPanel mainpanel = new JPanel(new BorderLayout(10, 10));
		mainpanel.setBackground(new Color(50, 150, 200));
		add(mainpanel, BorderLayout.CENTER);
		
		JPanel panelBusqueda = configurarBusquedaExcursion();
		mainpanel.add(panelBusqueda, BorderLayout.NORTH);
		
		//tabla
		modelo = new ExcursionTabla(crearListaExcursiones());
		tabla = new StripedTable(modelo);
		tabla.setRowHeight(28);
		tabla.setFillsViewportHeight(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(230, 230, 230));
		
		ordena = new TableRowSorter<>(tabla.getModel());
		tabla.setRowSorter(ordena);
		
		// configuracion del encabezado de la tabla
		JTableHeader cabecera = tabla.getTableHeader();
		cabecera.setReorderingAllowed(false); //no se puedecambiar el orden de las columnas
		cabecera.setFont(cabecera.getFont().deriveFont(Font.BOLD));
		
		//que la tabla se ajuste a la ventana
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		int[] ancho = {50, 200, 90, 90, 90};
		for (int i = 0; i < ancho.length; i++) {
            TableColumn col = tabla.getColumnModel().getColumn(i);
            col.setPreferredWidth(ancho[i]);
            col.setMinWidth(Math.min(ancho[i], 50));
        }
		
		//Renderer
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(center); //se centra el texto
        tabla.getColumnModel().getColumn(2).setCellRenderer(verInfoboton); // Boton para ver la informacion
        tabla.getColumnModel().getColumn(3).setCellRenderer(new ExcursionPrecio()); // Precio formateado
        tabla.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int fila = tabla.rowAtPoint(e.getPoint());
        		int columna = tabla.columnAtPoint(e.getPoint());
        		if (columna == 2 && fila >= 0) {
        			int modelo = tabla.convertRowIndexToModel(fila);
        			ExcursionTabla exc = (ExcursionTabla) tabla.getModel();
        			Excursion excur = exc.getAt(modelo);
        			mostrardescripcion(excur);
        		}
        		
        	}
        	
        	//muestra la descripcion de la excursion
			private void mostrardescripcion(Excursion exc) {
				JDialog dialogo = new JDialog(VentanaExcursiones.this, "Informacion", true);
				dialogo.setLayout(new BorderLayout(10, 10));
				dialogo.setSize(480, 300);
				dialogo.setLocationRelativeTo(VentanaExcursiones.this);
				
				JTextArea texto = new JTextArea();
				texto.setText(exc.getDescripcion());
				texto.setEditable(false);
				texto.setLineWrap(true); //hace salto de linea automatico
				texto.setWrapStyleWord(true); //salto de linea entre palabaras completas
				texto.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(180, 200, 230)),
						BorderFactory.createEmptyBorder(12,12,12,10)));
				texto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				
				//boton de cerrar
				JButton cerrar = new JButton("Cerrar");
				cerrar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						VentanaExcursiones ventanaexc = new VentanaExcursiones();
						ventanaexc.setVisible(true);
						
					}
				});
				JPanel añadir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				añadir.add(cerrar);
				dialogo.add(new JScrollPane(texto), BorderLayout.CENTER);
				dialogo.setVisible(true);
			}
        });
		
		//Boton reserva
        int columnareserva = 4;
        tabla.getColumnModel().getColumn(columnareserva).setCellRenderer(new ButtonRenderer("Reservar"));
        tabla.getColumnModel().getColumn(columnareserva).setCellEditor(new ButtonEditor(modelo, tabla, this::abrirReserva));
        
        //scroll pane para deslizarse entre las diferentes excursiones
        JScrollPane sp = new JScrollPane(tabla, 
        		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBorder(new LineBorder(Color.BLACK, 2));
        sp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);//texto a la izquierda y sp a la derecha
        mainpanel.add(sp, BorderLayout.CENTER); 
 
        //Boton de retorno a interfaz principal
        JButton botonInicio = new JButton("Atras"); //luego cambiarlo a un icono
        botonInicio.setBounds(0, 0, 10, 30);
        botonInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				VentanaInicio ventanainicio = new VentanaInicio();
				ventanainicio.setVisible(true);
			}
         
		});
        mainpanel.add(botonInicio, BorderLayout.SOUTH);
        
	}
	
		
        private List<Excursion> crearListaExcursiones() { 
            List<Excursion> out = new ArrayList<>();
            for (int i = 0; i < tituloexcursion.length; i++) {
                out.add(new Excursion(i + 1, tituloexcursion[i], descrip[i], precio[i])); //añade a la lista la excursion
            }
            return out;
        }
        
        //panel de eleccion de numero de personas + filtro de busqueda
        public JPanel configurarBusquedaExcursion() {
            JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panel1.setBackground(new Color(230, 240, 255));
            panel1.add(new JLabel("Busqueda de excursion:"));
            campoFiltro = new JTextField(24);
            panel1.add(campoFiltro);
            JButton buscar = new JButton("Buscar");
            buscar.addActionListener(e -> aplicabusqueda());
			panel1.add(buscar);
			
			//permite detectar cambios cuando escribe el usuario
            campoFiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() { 
                private void go() {
                	aplicabusqueda(); 
                	}
                public void insertUpdate(javax.swing.event.DocumentEvent e) { //cuando escribe el usuario
                	go(); 
                }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { //cuando el usuario elimina lo escrito
                	go(); 
                }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                	go(); 
                }
            });
            return panel1;
        }
        
        private void aplicabusqueda() {
            String txt = campoFiltro.getText().trim(); //obtiene el texto que estamos escribiendo
            if (txt.isEmpty()) { //si esta vacio enseña todas las excursiones
            	ordena.setRowFilter(null); 
            } else { //sino, muestra las coincidencias del buscador con las que tenemos
            	ordena.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(txt), 1)); 
            }}
        
        
        //Resumen de la excursion seleccionada
        private void abrirReserva(Excursion ex) {           
            JDialog mensaje = new JDialog(this, "Reserva", true);
            mensaje.setLayout(new BorderLayout(10, 10));
            mensaje.setSize(560, 460);
            mensaje.setLocationRelativeTo(this);

            //panel de resumen
            JPanel resumen = new JPanel();
            resumen.setLayout(new BoxLayout(resumen, BoxLayout.Y_AXIS));
            resumen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            //titulo    
            JLabel titulo = new JLabel("Has seleccionado:");
            titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
            titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
            resumen.add(titulo);
            resumen.add(Box.createVerticalStrut(6));
            
            //informacion de la reserva
            JTextArea informacionreserva = new JTextArea(
            		"• " + ex.getNombre() + "\n" +
            		"• " + ex.getDescripcion() + "\n\n" +
            		"• Precio por persona: " + String.format("%.2f €", ex.getPrecio())
            );
            
            informacionreserva.setEditable(false);
            informacionreserva.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            informacionreserva.setLineWrap(true);
            informacionreserva.setWrapStyleWord(true);

            JScrollPane sp = new JScrollPane(informacionreserva,
            		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            sp.setAlignmentX(Component.LEFT_ALIGNMENT);
            resumen.add(sp);
            resumen.add(Box.createVerticalStrut(8));
           
            //desplegable de numero de personas
            JPanel personasdesplegar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            personasdesplegar.add(new JLabel("Personas: "));
            JComboBox<Integer> numpersonas = new JComboBox<>();
            for (int i=1; i<= 10; i++) {
            	numpersonas.addItem(i);
            }
            numpersonas.setSelectedItem(1);
            personasdesplegar.add(numpersonas);
            personasdesplegar.setAlignmentX(Component.LEFT_ALIGNMENT);
            resumen.add(personasdesplegar);
           
            //total
            int per = (Integer) numpersonas.getSelectedItem();
            JLabel textototal = new JLabel("Total: " + String.format("%.2f €", ex.getPrecio() * per));
            textototal.setFont(new Font("Segoe UI", Font.BOLD, 12));		
            textototal.setAlignmentX(Component.LEFT_ALIGNMENT);
            resumen.add(textototal);
            
          //actualiza el precio total
            numpersonas.addActionListener(change ->  {
            	int seleccion = (Integer) numpersonas.getSelectedItem();
            	double totalprecio = (ex.getPrecio() * seleccion);
            	textototal.setText("Total: " + String.format("%.2f €", totalprecio));
            });
             
            //Boton de confirmar reserva
            JButton confirmar = new JButton("Confirmar reserva");
            confirmar.setFont(new Font("Segoe UI", Font.BOLD, 12));
            confirmar.addActionListener(e -> {

                // --- 1. RECOGER DATOS (de los componentes de ESTE diálogo) ---
                int numPersonas = (Integer) numpersonas.getSelectedItem();
                double totalPrecioCalculado = (ex.getPrecio() * numPersonas);
                
                // --- 2. GESTIONAR DATOS FALTANTES (Fecha y Ciudad) ---
                
                // FECHA: Tu diálogo no pide fecha, así que usamos la fecha actual.
                // (Si quieres pedir una fecha, debes añadir un JSpinner de fecha a este diálogo)
                java.util.Date fechaExcursion = new java.util.Date(); 

                // CIUDAD: Tu objeto Excursion no tiene ciudad. La ponemos como "N/A".
                String ciudadExcursion = "N/A"; 
                // (La alternativa es intentar adivinarla del nombre, pero es complicado)
                
                // --- 3. OBTENER USUARIO (de Ventana1Login) ---
                String usuario = "UsuarioDesconocido"; // Valor por defecto
                try {
                     usuario = Ventana1Login.userField.getText();
                } catch(Exception ex2) {
                     System.err.println("No se pudo leer el usuario de Ventana1Login");
                }
                
                // --- 4. "MAPEAR" DATOS AL FORMATO CSV ---
                String ciudad_csv = ciudadExcursion;
                String hotel_csv = ex.getNombre();          // TRUCO: Nombre de excursión en columna "Hotel"
                String email_csv = "N/A";
                String habitacion_csv = "Excursión";        // TRUCO: Identificador en columna "Habitación"
                int adultos_csv = numPersonas;
                int ninos_csv = 0;
                java.util.Date salida_csv = fechaExcursion;
                java.util.Date regreso_csv = fechaExcursion; // Mismo día
                double precioFinal_csv = totalPrecioCalculado;

                // --- 5. LLAMAR AL MÉTODO DE GUARDADO (¡ANTES DE CERRAR!) ---
                guardarReservaEnCSV(
                    usuario, 
                    ciudad_csv, 
                    hotel_csv, 
                    email_csv, 
                    habitacion_csv, 
                    adultos_csv, 
                    ninos_csv, 
                    salida_csv, 
                    regreso_csv, 
                    precioFinal_csv
                );

                // --- 6. CONFIRMAR AL USUARIO ---
                // (Esto es de tu código original, está bien)
                JOptionPane.showMessageDialog(
                        mensaje,
                        "Excursión: " + ex.getNombre() + "\n" +
                        "Personas: " + numPersonas + "\n" +
                        "Total: " + String.format(Locale.US, "%.2f €", totalPrecioCalculado) + "\n\n" +
                        "¡Reserva guardada en su perfil!", // Mensaje añadido
                        "Confirmación",
                        JOptionPane.INFORMATION_MESSAGE
                );
                
                // --- 7. CERRAR EL DIÁLOGO ---
                mensaje.dispose();
            });

            
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(textototal, BorderLayout.WEST);
            panel.add(confirmar, BorderLayout.EAST);

            mensaje.add(sp, BorderLayout.CENTER);
            mensaje.add(personasdesplegar, BorderLayout.NORTH);
            mensaje.add(panel, BorderLayout.SOUTH);
            mensaje.setVisible(true);
        }

        
      
     // Crea la tabla de dos colores
        static class StripedTable extends JTable {
			private static final long serialVersionUID = 1L;
			private final Color par = new Color(245, 250, 255);
            private final Color impar  = Color.WHITE;
            public StripedTable(TableModel m) { 
            	super(m); 
            	
            }
            
            @Override //filas pares unn color e impares de otro
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                	if (row % 2 == 0) {
                		c.setBackground(par);
                	} else {
                		c.setBackground(impar);
                	}
                }
               
                return c;
            }
            
            @Override
            public String getToolTipText(java.awt.event.MouseEvent e) {
                java.awt.Point p = e.getPoint();
                int viewRow = rowAtPoint(p); // Fila sobre la que está el ratón

                if (viewRow >= 0) {
                    try {
                        // Convierte la fila de la vista al modelo (importante si la tabla está ordenada)
                        int modelRow = convertRowIndexToModel(viewRow);
                        
                        // Obtiene el modelo de la tabla
                        ExcursionTabla model = (ExcursionTabla) getModel();
                        
                        // Obtiene la excursión de esa fila
                        Excursion ex = model.getAt(modelRow);
                        
                        // Obtiene la descripción completa
                        String fullDescription = ex.getDescripcion();
                        
                        // Acorta la descripción para la previsualización (p.ej. 150 caracteres)
                        String shortDesc = fullDescription;
                        if (fullDescription.length() > 150) {
                            shortDesc = fullDescription.substring(0, 150) + "...";
                        }
                        
                        // Devuelve el texto formateado en HTML.
                        // El 'width' hace que el texto se ajuste automáticamente si es largo.
                        return "<html><p width='300px'>" + shortDesc + "</p></html>";

                    } catch (Exception ex) {
                        System.err.println("Error al generar tooltip: " + ex.getMessage());
                    }
                }
                return null; // Sin tooltip si no está sobre una fila válida
            }
            
            
        }
      
        static class ButtonRenderer extends JButton implements TableCellRenderer {
        	private static final long serialVersionUID = 1L;
          	public ButtonRenderer(String text) { setText(text); setOpaque(true); }
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                     boolean hasFocus, int row, int column) {
                setText(value == null ? "Reservar" : value.toString());
                setForeground(table.getForeground());
                setBackground(isSelected ? table.getSelectionBackground() : UIManager.getColor("Button.background"));
                return this;
            }
        }
        
        

        static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
			private static final long serialVersionUID = 1L;
			private final JButton button = new JButton("Reservar");
            private final ExcursionTabla model;
            private final JTable table;
            private final java.util.function.Consumer<Excursion> onClick;
            public ButtonEditor(ExcursionTabla model, JTable table, java.util.function.Consumer<Excursion> onClick) {
                this.model = model; this.table = table; this.onClick = onClick;
                button.addActionListener(this);
            }
            @Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                button.setText(value == null ? "Reservar" : value.toString());
                return button;
            }
            @Override public Object getCellEditorValue() { return "Reservar"; }
            @Override public void actionPerformed(ActionEvent e) {
                int viewRow = table.getEditingRow();
                int modelRow = table.convertRowIndexToModel(viewRow);
                Excursion ex = model.getAt(modelRow);
                onClick.accept(ex);
                fireEditingStopped();
            }
        }
        
        //Ver informacion de las descripciones
        DefaultTableCellRenderer verInfoboton = new DefaultTableCellRenderer() {
    	   public Component getTableCellRendererComponent (JTable tabla, 
    			   											Object value, 
    			   											boolean seleccionado, 
    			   											boolean hasFocus, 
    			   											int fila, 
    			   											int columna) {
    		   JButton boton = new JButton("Ver info");
    		   boton.setFocusable(false); //mejora de IAG
    		   boton.setBackground(Color.WHITE);
    		   return boton;
    	   };
       };
     
       
       
       
       private void guardarReservaEnCSV(String usuario, String ciudad, String hotel, String email, 
               String hab, int adultos, int ninos, Date salida, Date regreso, 
               double precioFinal) {
                   
   		final String FILE_NAME = "reservas.csv";
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
       
       
    
	
	public static void main(String[] args) {
		VentanaExcursiones excurs = new VentanaExcursiones();
		excurs.setVisible(true);
	}
}
