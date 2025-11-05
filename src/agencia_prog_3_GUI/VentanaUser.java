package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaUser extends JFrame{
	private JTextField titulo;
	private JTextField nombre;
	private JTextField dni;
	private JTextField email;
	private JTextField telefono;
	private JTextField direccion;
	
	public VentanaUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));
		setResizable(false);
		
		//Titulo
		titulo = new JTextField("Perfil Usuario");
		titulo.setEditable(false);
		titulo.setFont(new Font("Times new Roman", 13, Font.BOLD));
		titulo.setHorizontalAlignment(JTextField.CENTER);
		add(titulo, BorderLayout.NORTH);
		
		
		
		
	}
//PERFIL
	
 // Ventana donde el usuario vea su informacion
	//OPCION A MODIFICAR PASSWORD
	// añadir una foto generica user
	// y que introduzca datos personales que despues se relacionaran con su incio para las reservas
}
