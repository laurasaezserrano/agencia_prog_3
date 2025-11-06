package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.BorderFactory;

public class VentanaVueloYHotel extends JFrame{
	private JTextField titulo;
	private static final long serialVersionUID = 1L;
	private static final String CSV_PATH = "vuelosagencia_completo.csv";
	//Por si suceden futuras extensiones
	private String [] origenVuelos = {"Madrid"};
	private JTextField txtPersonas;
    private static final int VUELO_BOTON_HEIGHT = 50;
    
    
    
    private List<DatosVuelos> vuelos = new ArrayList<>();
    private JTable tablavuelos = new JTable();
    private JComboBox<String> cmbDestino;
    private JComboBox<String> cmbOrigen;
    private JButton botonBusqueda;
	private JLabel informacion = new JLabel("Selecciona un aeropuerto origen");

    
	public VentanaVueloYHotel() {
		setTitle("Búsqueda de vuelos");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        cmbOrigen.setPrototypeDisplayValue("Seleccione aeropuerto de origen");
        cmbOrigen.addActionListener((e) -> {
        	Object item = ((JComboBox<?>) e.getSource()).getSelectedItem();
        	vuelos = new ArrayList<>();
        	
        	
        	if (item != null && !item.toString().isEmpty()) {
				final String orig = item.toString().substring(0, item.toString().indexOf(" - "));
				
				if (!orig.isEmpty()) {
					Set<Aeropuertos> destinos = new HashSet<>();					
					cambiardestino(new ArrayList<Aeropuertos>(destinos));
				} else {
					cmbDestino.removeAllItems();
				}	
				
			}
        	cambiarvuelos();
        	informacion.setText("Selecciona aeropuerto de origen");
        });
        
        cmbDestino.setPrototypeDisplayValue("Seleccione aeropuerto de destino");
        cmbDestino.addActionListener((e) -> {
        	Object item2 = ((JComboBox<?>) e.getSource()).getSelectedItem();
        	vuelos = new ArrayList<>();
        	
        	
        	if (item2 != null && !item2.toString().isEmpty()) {
				final String dest = item2.toString().substring(0, item2.toString().indexOf(" - "));
				
				if (!dest.isEmpty() && cmbOrigen.getSelectedIndex()>0) {
					Object item = cmbOrigen.getSelectedItem();
					final String orig = item.toString().substring(0, item.toString().indexOf(" - "));
				} 
				}	
				cambiarvuelos();
        });
        	
        	
      tablavuelos.setRowHeight(30);
      tablavuelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      //((DefaultTableCellRenderer) tablavuelos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
      
        	
        	
        	
        	
        	
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setBackground(Color.WHITE);
		
		JPanel panelBusqueda = configurarPanelBusqueda();
		panelBusqueda.setAlignmentX(JComponent.CENTER_ALIGNMENT); 
		mainpanel.add(panelBusqueda, BorderLayout.NORTH);
		
		titulo = new JTextField("Búsqueda de Vuelos y Ofertas");
        titulo.setEditable(false);
        titulo.setHorizontalAlignment(JTextField.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        titulo.setAlignmentX(JComponent.CENTER_ALIGNMENT); 
//      mainpanel.add(titulo);
		
		JPanel vuelospanel = new JPanel();
		vuelospanel.setLayout(new BoxLayout(vuelospanel, BoxLayout.Y_AXIS));
        vuelospanel.setBackground(new Color(50, 150, 200)); 
        
        JButton botonInicio = new JButton("Atras"); //luego cambiarlo a un icono
        botonInicio.setBounds(0, 0, 10, 30);
		
		botonInicio.addActionListener(new ActionListener() {
		    
			@Override
		    public void actionPerformed(ActionEvent e) {
		        // Cierra la ventana actual
		        dispose(); 
		        
		        // Abre una nueva instancia de la ventana de inicio
		        VentanaInicio vInicio = new VentanaInicio();
		        vInicio.setVisible(true);
		    }
	});
		
        /**
         * IAG - IDEA BORDE VACIO PARA MEJOR ESTRUCTURA (setBorder - linea 58)
         */
        vuelospanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		for (int i = 1; i<10; i++) {
			JButton boton = new JButton("Vuelo" + i);
			boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			Dimension botonDim = new Dimension(850, 60);
			boton.setMaximumSize(botonDim);
			boton.setPreferredSize(botonDim);
			boton.setMaximumSize(botonDim);
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
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
        add(panelBusqueda, BorderLayout.NORTH);
        add(botonInicio,BorderLayout.SOUTH);
        
	}
	
	/**
	 * IAG - Para asegurarnos de que aunque el CSV no se lea (METODO obtenerDestinosUnicos):
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
		
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBusqueda.setBackground(new Color(230, 240, 255));
		
		//NUMERO PERSONAS
		panelBusqueda.add(new JLabel("Personas: "));
		JTextField txtpersonas = new JTextField("1", 6); // Valor por defecto 1 (Idea IAG)
		panelBusqueda.add(txtpersonas);
		
		//ORIGEN - Solo Madrid pero lo creamos para nuevas opciones posibles
		panelBusqueda.add(new JLabel("Origen: "));
		cmbOrigen = new JComboBox<>(this.origenVuelos);
		cmbOrigen.setPreferredSize(new Dimension(150, 25)); // Para que tenga un tamaño similar al Origen
		panelBusqueda.add(cmbOrigen);
		
		//DESTINO
		panelBusqueda.add(new JLabel("Destino:"));
		JComboBox<String> cmbDestino = new JComboBox<>(destinARRAY);
		cmbDestino.setPreferredSize(new Dimension(150, 25)); // Para que tenga un tamaño similar al Origen
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
