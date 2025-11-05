package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class VentanaExcursiones extends JFrame{
	private JTextField titulo;
	private JTextField personas;
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
	
	
	
	public VentanaExcursiones() {
		setTitle("Busqueda de excursiones");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));
		
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());
		mainpanel.setBackground(new Color(50, 150, 200));
		
		JPanel panelBusqueda = configurarBusquedaExcursion();
		panelBusqueda.setAlignmentX(JComponent.CENTER_ALIGNMENT); 
		mainpanel.add(panelBusqueda, BorderLayout.NORTH);
		
		JPanel excursiones = new JPanel();
		excursiones.setLayout(new GridLayout(3, 3, 10, 10));
		excursiones.setBackground(new Color(50, 150, 200));
		
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
        
        
        for (int i = 0; i<10; i++) {
        	int numero = i + 1;
			JButton boton = new JButton("Excursion " + numero);
			boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			Dimension botonDim = new Dimension(500, 60);
			boton.setMaximumSize(botonDim);
			boton.setPreferredSize(botonDim);
			boton.setMaximumSize(botonDim);
			boton.setBackground(new Color(255, 255, 255));
			boton.setBorder(new LineBorder(Color.BLACK, 2));
        
			
			boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					abrirexcursion(numero);
					
				}
			});
			excursiones.add(boton);
			
        }
        mainpanel.add(excursiones);
        add(mainpanel);
        add(botonInicio, BorderLayout.SOUTH);
        
	}  
        private void abrirexcursion(int numero) {
        	JFrame ventanaexcursion = new JFrame(tituloexcursion[numero - 1]);
        	ventanaexcursion.setSize(800, 500);
            ventanaexcursion.setLayout(new BorderLayout(10, 10));
        	String[] descrip = {
        			//
        			"Increible visita a las Cataratas del Niagara",
        			"Grana ruta del cafe por Colombia",
        			"Descubre las vistas de Suiza desde el teleferico de Lucerna",
        			"Descubre los tesoros del Vaticano",
        			"Descubre el templo de Nikko en Japón",
        			"Disfruta de un maravilloso dia en la gran ciudad de Bali",
        			"Descubre las maravillosas vistas desde el Empire State",
        			"Visita la famosa Opera de Oslo",
        			"Visita la maravillosa ciudad de Florencia"
        	};
        JLabel detalles = new JLabel("Detalles del viaje a " + tituloexcursion[numero - 1], SwingConstants.CENTER);
        detalles.setFont(new Font("Times new Roman", Font.PLAIN, 22));
		ventanaexcursion.add(titulo, BorderLayout.NORTH);
        
		JButton botonreserva = new JButton("Reservar esta oferta");
		botonreserva.setFont(new Font("Arial", Font.BOLD, 16));
		botonreserva.setBackground(new Color(0, 128, 0));
		botonreserva.setForeground(Color.WHITE);
		botonreserva.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//HACER BOTON DE RESERVAS
				
			}
		});
		
		
		JPanel panelBotonReserva = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBotonReserva.add(botonreserva);
		panelBotonReserva.setBackground(new Color(50, 150, 200)); 
		ventanaexcursion.add(panelBotonReserva, BorderLayout.SOUTH);
		ventanaexcursion.setVisible(true);
        }
        
        
        
        public JPanel configurarBusquedaExcursion() {
        	JPanel panelbusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        	panelbusqueda.setBackground(new Color(230, 240, 255));
        	//nº personas
        	panelbusqueda.add(new JLabel("Personas: "));
        	JTextField personas = new JTextField("1", 6);
        	panelbusqueda.add(personas);
        	
        	//ciudad de excursion FALTA POR HACER
//        	panelbusqueda.add(new JLabel("Ciudad: "));
//        	panelbusqueda.add(destinos);
        	
        	//boton de busqueda
        	JButton botonBusqueda = new JButton("Buscar");
        	panelbusqueda.add(botonBusqueda);
        	       	
        	
        	return panelbusqueda;
        }
        
 
	
	
	public static void main(String[] args) {
		VentanaExcursiones excurs = new VentanaExcursiones();
		excurs.setVisible(true);
	}
}
