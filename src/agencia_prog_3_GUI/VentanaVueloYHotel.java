package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class VentanaVueloYHotel extends JFrame{
	private JTextField titulo;
	private static final long serialVersionUID = 1L;
	private static final String CSV_PATH = "vuelosagencia_completo.csv";
	//Por si suceden futuras extensiones
	private String [] origenVuelos = {"Madrid"
										};
	private JTextField txtPersonas;
    private JComboBox<String> cmbDestino;
    private JComboBox<String> cmbOrigen;

	public VentanaVueloYHotel() {
		setTitle("Búsqueda de vuelos");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
		JPanel mainpanel = new JPanel();
		JPanel vuelospanel = new JPanel();
		vuelospanel.setLayout(new BoxLayout(vuelospanel, BoxLayout.Y_AXIS));
		
		JPanel panelBusqueda = configurarPanelBusqueda();
		mainpanel.add(panelBusqueda, BorderLayout.NORTH);
		
        vuelospanel.setBackground(new Color(245, 245, 245));
		
		for (int i = 0; i<5; i++) {
			JButton boton = new JButton("Vuelo" + i);
			boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			boton.setPreferredSize(new Dimension(150, 100));
			boton.setBackground(new Color(255, 255, 255));
			boton.setBorder(new LineBorder(Color.BLACK, 2));
//			boton.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					abrirvuelo(i);
//					
//				}
//			});
			
			vuelospanel.add(boton);
			vuelospanel.add(Box.createVerticalStrut(10));
		}
		
		JScrollPane scroll = new JScrollPane(vuelospanel);
        add(scroll, BorderLayout.CENTER);
       
        mainpanel.add(vuelospanel);
        add(mainpanel);
        
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

	public JPanel configurarPanelBusqueda() {
		Set<String> destinos = obtenerDestinosUnicos();
		String[] destinARRAY = destinos.toArray(new String[0]);
		
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelBusqueda.setBackground(new Color(230, 240, 255));
		
		//NUMERO PERSONAS
		panelBusqueda.add(new JLabel("Personas: "));
		JTextField txtpersonas = new JTextField("1", 4); // Valor por defecto 1 (Idea IAG)
		panelBusqueda.add(txtpersonas);
		
		//ORIGEN - Solo Madrid pero lo creamos para nuevas opciones posibles
		panelBusqueda.add(new JLabel("Origen: "));
		cmbOrigen = new JComboBox<>(this.origenVuelos);
		cmbOrigen.setPreferredSize(txtpersonas.getPreferredSize()); // Para que tenga un tamaño similar al Origen
		panelBusqueda.add(cmbOrigen);
		
		//DESTINO
		panelBusqueda.add(new JLabel("Destino:"));
		JComboBox<String> cmbDestino = new JComboBox<>(destinARRAY);
		cmbDestino.setPreferredSize(txtpersonas.getPreferredSize()); // Para que tenga un tamaño similar al Origen
		panelBusqueda.add(cmbDestino);
		
		 //BOTON BUSCAR
        JButton botonBusqueda = new JButton("Buscar");
        panelBusqueda.add(botonBusqueda);
        
        return panelBusqueda;

	}
	
	public static void main(String[] args) {
		VentanaVueloYHotel vuelosyhotel = new VentanaVueloYHotel();
		vuelosyhotel.setVisible(true);
	}
}
