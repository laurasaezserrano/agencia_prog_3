package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
	
	public VentanaVueloYHotel() {
		JPanel mainpanel = new JPanel();
		JPanel vuelospanel = new JPanel();
		vuelospanel.setLayout(new BoxLayout(vuelospanel, BoxLayout.Y_AXIS));
		
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
		
		setTitle("Búsqueda de vuelos");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        titulo = new JTextField();
        
        
        mainpanel.add(vuelospanel);
        add(mainpanel);
	}
	
	
	public static void main(String[] args) {
		VentanaVueloYHotel vuelosyhotel = new VentanaVueloYHotel();
		vuelosyhotel.setVisible(true);
	}
}
