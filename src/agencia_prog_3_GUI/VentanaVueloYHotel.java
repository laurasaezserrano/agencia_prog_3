package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;

public class VentanaVueloYHotel extends JFrame{
	private JTextField titulo;
	private static final long serialVersionUID = 1L;
	private static final String CSV_PATH = "vuelosagencia_completo.csv";
	
    private final String[] origenVuelos = {"Madrid"}; //de momento solo Madrid
    
    private JTextField txtPersonas;
    private JComboBox<String> cmbDestino;
    private JComboBox<String> cmbOrigen;

    //tabla
    private JTable tabla;
    private VueloTabla modelo;
    private TableRowSorter<VueloTabla> ordenado;
    
	public VentanaVueloYHotel() {
		setTitle("Búsqueda de vuelos");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        //titulo
        JTextField titulo = new JTextField("Búsqueda de Vuelos y Ofertas");
        titulo.setEditable(false);
        titulo.setHorizontalAlignment(JTextField.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 22f));
        add(titulo, BorderLayout.SOUTH);
        
        //panel busqueda
        JPanel panelBusqueda = configurarbusqueda();
        panelBusqueda.add(panelBusqueda, BorderLayout.NORTH);
        
     	//cargar datos
        List<DatosVuelos> listaVuelos = cargarVuelosDesdeCSV();
        
        
        modelo = new VueloTabla(listaVuelos);
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        
        
        //alineacion en centro
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 8; i++) { 
            tabla.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        
        //precio del vuelo
        DefaultTableCellRenderer precioRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText(String.format("%.2f €", ((Number) value).doubleValue()));
                } else if (value != null) {
                    setText(value.toString());
                } else {
                    setText("");
                }
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        };
        tabla.getColumnModel().getColumn(5).setCellRenderer(precioRenderer);

        //boton de reservar
        ButtonRendererEditor nuevo = new ButtonRendererEditor();
        TableColumn reserva = tabla.getColumnModel().getColumn(8);
        reserva.setCellRenderer(nuevo);
        reserva.setCellEditor(nuevo);
        reserva.setPreferredWidth(110);
        
        ordenado = new TableRowSorter<>(modelo);
        tabla.setRowSorter(ordenado);
        add(new JScrollPane(tabla), BorderLayout.CENTER)
	}
        
	
		private JPanel configurarbusqueda() {
			Set<String> destinos = obtenerDestinosUnicos();
	        List<String> lista = new ArrayList<>(destinos);
	        Collections.sort(lista); //ordena la lista
	        lista.add(0, "Todos");  
	        String[] destinosarr = lista.toArray(new String[0]);

	        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        busqueda.setBackground(new Color(230, 240, 255));
	        busqueda.add(new JLabel("Personas:"));
	        txtPersonas = new JTextField("1", 6);
	        busqueda.add(txtPersonas);
	        busqueda.add(new JLabel("Seleccione origen:"));
	        cmbOrigen = new JComboBox<>(origenVuelos);
	        cmbOrigen.setPreferredSize(new Dimension(150, 25));
	        busqueda.add(cmbOrigen);
	        busqueda.add(new JLabel("Seleccione destino:"));
	        cmbDestino = new JComboBox<>(destinosarr);
	        cmbDestino.setPreferredSize(new Dimension(150, 25));
	        busqueda.add(cmbDestino);

	        //buscar
	        JButton botonBusqueda = new JButton("Buscar");
	        botonBusqueda.addActionListener(e -> applyFilters());
	        panelBusqueda.add(botonBusqueda);

	        JButton borrar = new JButton("Limpiar filtros");
	        borrar.addActionListener(e -> {
	            txtPersonas.setText("1");
	            cmbOrigen.setSelectedIndex(0);
	            cmbDestino.setSelectedIndex(0);
	            if (sorter != null) {
	            	sorter.setRowFilter(null);
	            }
	        });
	        busqueda.add(botonLimpiar);

	        return panelBusqueda;
	    }
	
	
	
	
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        mainpanel.setBackground(Color.WHITE);
        
//        cmbOrigen.setPrototypeDisplayValue("Seleccione aeropuerto de origen");
//        cmbOrigen.addActionListener((e) -> {
//        	Object item = ((JComboBox<?>) e.getSource()).getSelectedItem();
//        	vuelos = new ArrayList<>();
//        	
//        	
//        	if (item != null && !item.toString().isEmpty()) {
//				final String orig = item.toString().substring(0, item.toString().indexOf(" - "));
//				
//				if (!orig.isEmpty()) {
//					Set<Aeropuertos> destinos = new HashSet<>();					
//					cambiardestino(new ArrayList<Aeropuertos>(destinos));
//				} else {
//					cmbDestino.removeAllItems();
//				}	
//				
//			}
//        	cambiarvuelos();
//        	informacion.setText("Selecciona aeropuerto de origen");
//        });
//        
//        cmbDestino.setPrototypeDisplayValue("Seleccione aeropuerto de destino");
//        cmbDestino.addActionListener((e) -> {
//        	Object item2 = ((JComboBox<?>) e.getSource()).getSelectedItem();
//        	vuelos = new ArrayList<>();
//        	
//        	
//        	if (item2 != null && !item2.toString().isEmpty()) {
//				final String dest = item2.toString().substring(0, item2.toString().indexOf(" - "));
//				
//				if (!dest.isEmpty() && cmbOrigen.getSelectedIndex()>0) {
//					Object item = cmbOrigen.getSelectedItem();
//					final String orig = item.toString().substring(0, item.toString().indexOf(" - "));
//				} 
//				}	
//				cambiarvuelos();
//        });
//        	
//        	
//      tablavuelos.setRowHeight(30);
//      tablavuelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//      //((DefaultTableCellRenderer) tablavuelos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
//      //tablavuelos.setHorizontalAlignment(JLabel.RIGHT);
//        	
//      //Distribución de los elementos en el JFrame
//      JPanel panelorigen = new JPanel();		
//      panelorigen.add(new JLabel("Origen: "));		
//      panelorigen.add(this.cmbOrigen);
//
//      JPanel paneldestino = new JPanel();
//      paneldestino.add(new JLabel("Destino: "));
//      paneldestino.add(cmbDestino);
//    
      
      
//      JPanel panelbusqueda = new JPanel();
//      panelbusqueda.setBorder(new TitledBorder("Búsqueda de vuelos"));
//      panelbusqueda.setLayout(new GridLayout(3, 1));
//      panelbusqueda.add(panelorigen);
//      panelbusqueda.add(paneldestino);		
    						
//      add(panelbusqueda, BorderLayout.NORTH);
//      add(new JScrollPane(tablavuelos), BorderLayout.CENTER);
//      add(informacion, BorderLayout.SOUTH);
       
//      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      setTitle("Búsqueda de Vuelos");
//      setSize(1200, 600);
//      setLocationRelativeTo(null);
//      setVisible(true);
      
      titulo = new JTextField("Búsqueda de Vuelos y Ofertas");
      titulo.setEditable(false);
      titulo.setHorizontalAlignment(JTextField.CENTER);
      titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
      titulo.setAlignmentX(JComponent.CENTER_ALIGNMENT); 
      //mainpanel.add(titulo);
      	
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
		
		JPanel vuelospanel = new JPanel();
		vuelospanel.setLayout(new BoxLayout(vuelospanel, BoxLayout.Y_AXIS));
        vuelospanel.setBackground(new Color(50, 150, 200)); 
        

        /**
         * IAG - IDEA BORDE VACIO PARA MEJOR ESTRUCTURA (setBorder - linea 58)
         */
        vuelospanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
        //Tabla de vuelos
        List<DatosVuelos> listaVuelos = cargarVuelosDesdeCSV(); //FALTA LECTURA CSV
        Vuelo modelo = new Vuelo(listaVuelos);
        
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        
        //precio
        DefaultTableCellRenderer precioRenderer = new DefaultTableCellRenderer() {
        	@Override
        	protected void setValue(Object value) {
        		if (value instanceof Number) {
        			setText(String.format("%.2f €", ((Number) value).doubleValue()));
        		} else {
        			setText("");
        		}
        		setHorizontalAlignment(SwingConstants.CENTER);
        	};
        };
        tabla.getColumnModel().getColumn(5).setCellRenderer(precioRenderer);
        
        //Boton de reservas
        tabla.getColorModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent
        
        
        
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
        
        
//      private void cambiardestino(List<Aeropuertos> aeropuertos) {
//    	  this.cmbDestino.removeAllItems();
//    	  this.cmbDestino.addItem("");
//    	  Collections.sort(aeropuertos);
//    	  aeropuertos.forEach(a -> cmbDestino.addItem(String.format("%s - %s (%s)", 
//  				a.getCodigo(), a.getNombre(), a.getPais().getNombre())));
//    	  }
      
      
//      
//      private void cambiarvuelos() {
//    	  Comparator<DatosVuelos> preciocomparador = (f1, f2) -> {
//  			return Float.compare(f1.getPrecio(), f2.getPrecio());
//  			};
//  			Collections.sort(vuelos, preciocomparador);
//  			//tablavuelos.setModel(new FlightsTableModel(flights));	 FLIGHTS TABLA MODEL NO CREADO
//  			
//  			//Se define el render para todas las columnas de la tabla excepto la última
//  			FlightRenderer defaultRenderer = new FlightRenderer();
//  			
//  			for (int i=0; i<jTableFlights.getColumnModel().getColumnCount()-1; i++) {
//  				jTableFlights.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
//  			}
//
//  			//Se define el render y editor para la última columna de la tabla
//  			int lastColumn = tablavuelos.getColumnModel().getColumnCount()-1;
//  			
//  			tablavuelos.getColumnModel().getColumn(lastColumn).setCellRenderer(new BookRendererEditor(this));
//  			tablavuelos.getColumnModel().getColumn(lastColumn).setCellEditor(new BookRendererEditor(this));		
//  			
//  			informacion.setText(String.format("%d vuelos", vuelos.size()));
//  			
//  		
//      }
      
      

	public static void main(String[] args) {
		VentanaVueloYHotel vuelosyhotel = new VentanaVueloYHotel();
		vuelosyhotel.setVisible(true);
	}
}
}
