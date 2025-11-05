package agencia_prog_3_GUI;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaReservas() {
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(new FlowLayout());
		mainpanel.setBackground(Color.WHITE);
		setTitle("Reservas del usuario");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(10, 10, 10));
        
        
        
        
        
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}
