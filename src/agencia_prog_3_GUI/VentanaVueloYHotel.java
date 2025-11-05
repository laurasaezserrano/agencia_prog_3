package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

	public class VentanaVueloYHotel extends JFrame {
		//Procesar CSV para tener toda la información manejo de la información y actualizarla
		//Configurar la busqueda para que los resultados sean los esperados por el cliente
		//configurar la imagen e info con la opcion proporcionada
		//Añadir boton de reserva para que acabe de confirmar la reserva con los datos necesarios y añadirla a su perfil

			//componentes
			private static final long serialVersionUID = 1L;
			private static final String CSV_PATH = "vuelosagencia_completo.csv";
			private String [] origenVuelos = {"Madrid"
												};
					

			public VentanaVueloYHotel() {
				setTitle("Búsqueda de vuelos + Hotel");
				setSize(900, 600);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        setLocationRelativeTo(null);
		        setLayout(new BorderLayout(10, 10));
		        
		        configurarPanelBusqueda();
			}
			
			/**
			 * IAG - Para asegurarnos de que aunque el CSV no se lea:
		     * Extrae los destinos únicos del archivo CSV para poblar el JComboBox.
		     * @return Un Set de Strings con los nombres de los destinos.
		     */
		    private Set<String> obtenerDestinosUnicos() {
		        Set<String> destinos = new HashSet<>();
		        
		        destinos.add("Bogota");
		        destinos.add("Paris");
		        destinos.add("Zurich");
		        destinos.add("Roma");
		        destinos.add("Toronto");
		        destinos.add("Tokyo");
		        destinos.add("Bangkok");
		        destinos.add("Nueva York");
		        destinos.add("Oslo");

		        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
		            String linea;
		            int numLinea = 0;
		            
		            while ((linea = br.readLine()) != null) {
		                numLinea++;
		                if (numLinea <= 1) continue; // Saltar la cabecera si la hay
		                
		                String[] datos = linea.split(",");
		                // Suponemos que la ciudad de destino está en la columna 1 (índice 1) del CSV de vuelos
		                if (datos.length > 1) {
		                    destinos.add(datos[1].trim());
		                }
		            }
		        } catch (IOException e) {
		            System.err.println("Error al leer el archivo CSV de vuelos: " + e.getMessage() + 
		                               "\nUsando destinos simulados.");
		        } catch (Exception e) {
		            System.err.println("Error general al procesar el CSV: " + e.getMessage());
		        }
		        
		        return destinos;
		    }

			public void configurarPanelBusqueda() {
				Set<String> destinos = obtenerDestinosUnicos();
				String[] destinARRAY = destinos.toArray(new String[0]);
				
				JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
				panelBusqueda.setBackground(new Color(230, 240, 255));
				
				//ORIGEN - Solo Madrid pero lo creamos para nuevas opciones posibles
				panelBusqueda.add(new JLabel("Origen: "));
				JComboBox<String> cmbOrigen = new JComboBox<>(destinARRAY);
				cmbOrigen.setPreferredSize(txtPersonas.getPreferredSize()); // Para que tenga un tamaño similar al Origen
				panelBusqueda.add(cmbOrigen);
				
				//DESTINO
				panelBusqueda.add(new JLabel("Destino:"));
				JComboBox<String> cmbDestino = new JComboBox<>(destinARRAY);
				cmbDestino.setPreferredSize(txtPersonas.getPreferredSize()); // Para que tenga un tamaño similar al Origen
				panelBusqueda.add(cmbDestino);
				
				//NUMERO PERSONAS
				panelBusqueda.add(new JLabel("Personas:"));
		        JTextField txtPersonas = new JTextField("1", 4); // Valor por defecto 1
		        panelBusqueda.add(txtPersonas);
		        
		        //BOTON BUSCAR
		        JButton botonBusqueda = new JButton("Buscar");
		        panelBusqueda.add(botonBusqueda);
			}
			
			
//			  FALTA LAYOUT CON TODAS LAS OFERTAS DE VUELOS
//		 	  listModel = new DefaultListModel<>();
//		    listaResultados = new JList<>(listModel);
//		    listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		    listaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
//		    JScrollPane scrollResultados = new JScrollPane(listaResultados);
//		    scrollResultados.setBorder(BorderFactory.createTitledBorder("Vuelos Disponibles"));
		//
//		    // Panel Derecho: Detalles del vuelo seleccionado
//		    JPanel panelDetalles = new JPanel(new BorderLayout(10, 10));
//		    panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Vuelo"));
			
			
			public static void main(String[] args) {
		        // Buena práctica para iniciar GUIs de Swing
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                new VentanaVueloYHotel().setVisible(true);
		            }
		        });
		    }
			
			
		}