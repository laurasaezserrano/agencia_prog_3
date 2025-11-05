package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaUser extends JFrame{
	private JTextField titulo;
	private JTextField nombre;
	private JTextField dni;
	private JTextField email;
	private JTextField telefono;
	private JTextField direccion;
	
	public VentanaUser() {
		setTitle("Perfil del Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 350);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));
		setResizable(false);
		
		//Titulo
		titulo = new JTextField("Perfil Usuario");
		titulo.setEditable(false);
		titulo.setFont(new Font("Times new Roman", 13, Font.BOLD));
		titulo.setHorizontalAlignment(JTextField.CENTER);
		add(titulo, BorderLayout.NORTH);
		
		//Panel donde el usuario incluirá sus datos
		JPanel panelUsuario = new JPanel(new GridLayout(8, 2, 10, 10));
		panelUsuario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
				
		//Nombre
		panelUsuario.add(new JLabel("Nombre completo:"));
		JTextField nombre = new JTextField();
		panelUsuario.add(nombre);
		
		//dni
		panelUsuario.add(new JLabel("DNI / Pasaporte"));
		JTextField nombre = new JTextField();
				panelUsuario.add(nombre);
		
		
		//Boton para guardar datos del usuario
		JButton botonGuardar = new JButton("Guardar");
		panelUsuario.add(botonGuardar);
		add(panelUsuario);
	}
//PERFIL
	
 // Ventana donde el usuario vea su informacion
	//OPCION A MODIFICAR PASSWORD
	// añadir una foto generica user
	// y que introduzca datos personales que despues se relacionaran con su incio para las reservas
	public static void main(String[] args) {
		VentanaUser usuario = new VentanaUser();
		usuario.setVisible(true);
	}


}
