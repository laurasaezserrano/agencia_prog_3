package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaUser extends JFrame{
	private JTextField nusuario;
	private JTextField contraseña;
	private JTextField titulo;
	private JTextField nombre;
	private JTextField dni;
	private JTextField email;
	private JTextField telefono;
	private JTextField direccion;
	/**
	 * Idioma y Moneda generados con IAG
	 */
	private final JComboBox<String> cbIdioma = new JComboBox<>(new String[]{"ES", "EN", "FR"});
    private final JComboBox<String> cbMoneda = new JComboBox<>(new String[]{"EUR", "USD", "GBP"});

	
	public VentanaUser(String user, String pass) {
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
		JPanel panelUsuario = new JPanel(new GridLayout(10, 2, 10, 10));
		panelUsuario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panelUsuario.setBackground(new Color(255, 255, 179));
		
				
		//nombre de usuario
		panelUsuario.add(new JLabel("Nombre de usuario:"));
		nusuario = new JTextField();
		String usuarioLogeado = AlmacenajeSesion.getNombreUsuario(); 
	    if (usuarioLogeado != null) {
	        nusuario.setText(usuarioLogeado);
	        nusuario.setEditable(false); 
	        nusuario.setBackground(Color.LIGHT_GRAY);
	    }
	    panelUsuario.add(nusuario);
		
		//contraseña
		panelUsuario.add(new JLabel("Contraseña:"));
		JTextField contraseña = new JTextField();
		String contraseñaUser = AlmacenajeSesion.getPassword(); 
	    if (contraseñaUser != null) {
	        nusuario.setText(contraseñaUser);
	        nusuario.setEditable(false); 
	        nusuario.setBackground(Color.LIGHT_GRAY);
	    }
		panelUsuario.add(contraseña);
				
		//nombre
		panelUsuario.add(new JLabel("Nombre completo:"));
		JTextField nombre = new JTextField();
		panelUsuario.add(nombre);
		
		//dni
		panelUsuario.add(new JLabel("DNI / Pasaporte:"));
		JTextField dni = new JTextField();
		panelUsuario.add(dni);
		
		//email
		panelUsuario.add(new JLabel("Email de contacto:"));
		JTextField email = new JTextField();
		panelUsuario.add(email);
		
		//telefono
		panelUsuario.add(new JLabel("Telefono de contacto:"));
		JTextField telefono = new JTextField();
		panelUsuario.add(telefono);
		
		//direccion
		panelUsuario.add(new JLabel("Direccion:"));
		JTextField direccion = new JTextField();
		panelUsuario.add(direccion);
		
		//idioma
		panelUsuario.add(new JLabel("Seleccione idioma:"));
		JComboBox<String> idioma = new JComboBox<>(new String[] {"Español","Euskera","Ingles"});
		panelUsuario.add(idioma);
		
		//moneda
		panelUsuario.add(new JLabel("Seleccione moneda:"));
		JComboBox<String> moneda = new JComboBox<>(new String[] {"EUR","USD","GBP"});
		panelUsuario.add(moneda);
		
		//Boton para guardar datos del usuario
		JButton botonGuardar = new JButton("Guardar");
		panelUsuario.add(botonGuardar);
		add(panelUsuario);
		
		/**
		 *  Action listener creado con ayuda de la IAG
		 */
		botonGuardar.addActionListener(e -> {
			String info = """
					Perfil guardado
					Nombre: %s
                    Documento: %s
                    Email: %s
                    Teléfono: %s
                    Dirección: %s
                    Idioma: %s
                    Moneda: %s
					""".formatted(
					nombre.getText(),
					dni.getText(),
					email.getText(),
					telefono.getText(),
					direccion.getText(),
					idioma.getSelectedItem(),
					moneda.getSelectedItem()
					);
			JOptionPane.showMessageDialog(this,  info, "Datos guardados", JOptionPane.INFORMATION_MESSAGE);

		});
		add(panelUsuario);
		
		
		
	}
	
	//OPCION A MODIFICAR PASSWORD
	// añadir una foto generica user
	// y que introduzca datos personales que despues se relacionaran con su incio para las reservas
	public static void main(String[] args) {
		String user = new String ();
		String pass = new String ();
		VentanaUser usuario = new VentanaUser(user,pass);
		usuario.setVisible(true);
	}


}
