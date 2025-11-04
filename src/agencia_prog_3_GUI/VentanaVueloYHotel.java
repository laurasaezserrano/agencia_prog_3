package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
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

	public VentanaVueloYHotel() {
		setTitle("Búsqueda de vuelos");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
	}


	public void panelBuscarVuelo() {
		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBusqueda.setBackground(new Color(230, 240, 255));
		
		panelBusqueda.add(new JLabel("Origen: "));
		JTextField txtOrigen = new JTextField(15);
        panelBusqueda.add(txtOrigen);
        
        panelBusqueda.add(new JLabel("Destino:"));
        JTextField txtDestino = new JTextField(15);
        panelBusqueda.add(txtDestino);
		
        JButton btnBuscar = new JButton("Buscar Vuelos");
        panelBusqueda.add(btnBuscar);
        
        add(panelBusqueda, BorderLayout.NORTH);
	}
	
	
//	  FALTA LAYOUT CON TODAS LAS OFERTAS DE VUELOS
// 	  listModel = new DefaultListModel<>();
//    listaResultados = new JList<>(listModel);
//    listaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//    listaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
//    JScrollPane scrollResultados = new JScrollPane(listaResultados);
//    scrollResultados.setBorder(BorderFactory.createTitledBorder("Vuelos Disponibles"));
//
//    // Panel Derecho: Detalles del vuelo seleccionado
//    JPanel panelDetalles = new JPanel(new BorderLayout(10, 10));
//    panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Vuelo"));
	
	
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
