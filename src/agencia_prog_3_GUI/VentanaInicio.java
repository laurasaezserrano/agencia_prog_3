package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class VentanaInicio extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField titulo;
	
	public VentanaInicio(){
		JPanel mainpanel = new JPanel();
		JPanel panel1 = new JPanel(new GridLayout(2, 6, 40, 40));
		String [] nombresboton = {
			"Perfil",
			"Reservas", 
			"Ofertas",
			"Vuelo + Hotel",
			"Excursiones",
			"Contacto"
		};
		
		for (int i = 0; i < 6; i++) {
			int numero = i+1;
			JButton boton = new JButton(nombresboton [numero-1]);
			boton.setPreferredSize(new Dimension(220, 180));
			boton.setBackground(new Color(255, 255, 255));
			boton.setBorder(new LineBorder(Color.BLACK, 2));
			boton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String user = AlmacenajeSesion.getNombreUsuario();
					String pass = AlmacenajeSesion.getPassword();
					
					if (nombresboton[numero-1].equals("Ofertas")) {
						CuadriculaOfertas vOfertas = new CuadriculaOfertas();
						vOfertas.setVisible(true);
						//ventanaoferta.dispose(); para que no se vea de fondo la de ofertas 
						
					} else if (nombresboton[numero-1].equals("Vuelo + Hotel")) {
						VentanaVueloYHotel vVueloHotel = new VentanaVueloYHotel();
						vVueloHotel.setVisible(true);
					} else if (nombresboton[numero-1].equals("Contacto")) {
						VentanaContacto ventanaContacto = new VentanaContacto(VentanaInicio.this);
						ventanaContacto.setVisible(true);
					} else if (nombresboton[numero-1].equals("Reservas")) {
						VentanaReservas vReservas = new VentanaReservas();
						vReservas.setVisible(true);
					} else if (nombresboton[numero-1].equals("Excursiones")) {
						VentanaExcursiones vExcursiones = new VentanaExcursiones();
						vExcursiones.setVisible(true);
					}else if (nombresboton[numero-1].equals("Perfil")) {
						VentanaUser userInfo = new VentanaUser(user, pass );
						userInfo.setVisible(true);
					}
					
				}
			});
		panel1.add(boton);
		}
		
		mainpanel.add(panel1);
		add(mainpanel);
		setTitle("Bienvenid@ a SunnyAgencia");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setMaximumSize(getToolkit().getScreenSize());
		setResizable(false);
		setLocationRelativeTo(null);
		panel1.setBackground(new Color(50, 150, 200));
		mainpanel.setBackground(new Color(50, 150, 200));
		
		titulo = new JTextField();
		titulo.setText("Bienvenid@ a SunnyAgencia");
		titulo.setEditable(false);
		titulo.setHorizontalAlignment(JTextField.CENTER);
		titulo.setFont(new Font("Times new Roman", Font.BOLD, 30));
		titulo.setBackground(new Color(255, 255, 255));
		add(titulo, BorderLayout.NORTH);
		
	}
	
	
	public static void main(String[] args) {
		VentanaInicio VentanaInicial = new VentanaInicio();
		VentanaInicial.setVisible(true);
	}
}
