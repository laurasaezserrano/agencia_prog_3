package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CuadriculaOfertas extends JFrame{
	private JTextField oferta;
	
	
	public CuadriculaOfertas() {
		JPanel mainpanel = new JPanel();
		JPanel panel1 = new JPanel(new GridLayout(3, 3, 20, 20));
		for (int i = 0; i < 9; i++) {
			JButton boton = new JButton("Oferta " + (i + 1));
			boton.setPreferredSize(new Dimension(200, 120));
//			boton.setVerticalTextPosition(SwingConstants.TOP);
//			boton.setHorizontalTextPosition(SwingConstants.CENTER);
			panel1.add(boton);			
		}
		
	
		
		mainpanel.add(panel1);
		add(mainpanel);
		
		
		setTitle("OFERTAS RECIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setMaximumSize(getToolkit().getScreenSize());
		setResizable(false);
		setLocationRelativeTo(null);
				
		
		oferta = new JTextField();
		oferta.setText("OFERTAS RECIENTES");
		oferta.setEditable(false);
		oferta.setHorizontalAlignment(JTextField.CENTER);
		oferta.setFont(new Font("Times new Roman", Font.BOLD, 25));
		add(oferta, BorderLayout.NORTH);
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		CuadriculaOfertas ofertas = new CuadriculaOfertas();
		ofertas.setVisible(true);
	}
	
	
}
