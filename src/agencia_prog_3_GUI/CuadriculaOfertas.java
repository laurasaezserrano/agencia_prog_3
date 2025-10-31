package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CuadriculaOfertas extends JFrame{
	private JTextField oferta;
	private JTextField descripcionoferta;
	
	public CuadriculaOfertas() {
		JPanel mainpanel = new JPanel();
		JPanel panel1 = new JPanel(new GridLayout(3, 3, 20, 20));
		for (int i = 0; i < 9; i++) {
			int numero = i + 1;
			JButton boton = new JButton("Oferta " + numero);
			boton.setPreferredSize(new Dimension(200, 120));
//			boton.setVerticalTextPosition(SwingConstants.TOP);
//			boton.setHorizontalTextPosition(SwingConstants.CENTER);
			boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					abriroferta(numero);
					
				}
			});
			
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
	private void abriroferta(int numero) {
		JFrame ventanaoferta = new JFrame("Oferta" + numero);
		ventanaoferta.setSize(800, 500);
		ventanaoferta.setLocationRelativeTo(this);
//		ventanaoferta.setLayout(new BorderLayout(10, 10));
		
		JLabel titulo = new JLabel("Detalles de la Oferta " + numero, SwingConstants.CENTER);
		titulo.setFont(new Font("Times new Roman", Font.PLAIN, 22));
		ventanaoferta.add(titulo, BorderLayout.NORTH);
		
		JPanel centro = new JPanel(new BorderLayout());
		JLabel imagen = new JLabel();
		imagen.setOpaque(true);
		imagen.setBackground(new Color(200, 220, 255));
		imagen.setPreferredSize(new Dimension(200, 150));
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setText("Imagen de la oferta " + numero);
		
		descripcionoferta = new JTextField();
		descripcionoferta.setText("");
		
		ventanaoferta.setVisible(true);
	}
	
	
	
	
	
	public static void main(String[] args) {
		CuadriculaOfertas ofertas = new CuadriculaOfertas();
		ofertas.setVisible(true);
	}
	
	
}
