package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaReservas() {
		setTitle("Gestión de Reservas");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JLabel titleLabel = new JLabel("Mis Reservas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);
        
        //Panel Central para las dos columnas (reservas anteriores y siguientes)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas, 10px de espacio horizontal
        centerPanel.setBackground(Color.LIGHT_GRAY);
        
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}