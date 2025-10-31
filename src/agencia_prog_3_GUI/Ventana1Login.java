package agencia_prog_3_GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Ventana1Login extends JFrame {
		private JTextField userField;
	    private JPasswordField passField;
	    private JButton loginButton;
	    private JButton registerButton;
	    private JLabel statusLabel;
	    
	    public Ventana1Login () {
	    	
	    	// Archivo donde se guardan los usuarios registrados y nuevos usuarios
	       // private static final String FILE_NAME = "usuarios.dat";
	        //private HashMap<String, String> validUsers;
	        
	        //validUsers = loadUsers();

	    	// Se crea la ventana de inicio de sesión 
	        setSize(350, 200);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null); // Centrar la ventana
	        setResizable(false);
	    	
	        // Configuramos el tamaño del panel 
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 2, 5, 5));
	        
	        //Creamos los elementos del Layout
	        JLabel userLabel = new JLabel("Usuario:");
	        JLabel passLabel = new JLabel("Contraseña:");
	        userField = new JTextField();
	        passField = new JPasswordField();
	        loginButton = new JButton("Iniciar sesión");
	        registerButton = new JButton("Registrarse");
	        statusLabel = new JLabel("", SwingConstants.CENTER); // Muestra mensajes como contraseña incorrecta o registrate
	        
	        //Action listeners
	        loginButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                checkLogin();
	            }

				private void checkLogin() {
					String user = userField.getText().trim();
					String pass = new String(passField.getPassword());
					
					if (user.isEmpty() || pass.isEmpty()) {
					    JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
					    return;
						}
					
					if (validUsers.containsKey(user) && validUsers.get(user).equals(pass)) {
					    statusLabel.setText("✅ Bienvenido, " + user + "!");
					    JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					} else if (!validUsers.containsKey(user)) {
					    int option = JOptionPane.showConfirmDialog(this,"El usuario no está registrado. ¿Deseas registrarte?",
					            "Usuario no encontrado",
					            JOptionPane.YES_NO_OPTION);
					    if (option == JOptionPane.YES_OPTION) {
					        registerUser();
					    	}
					} else {
					    statusLabel.setText("Contraseña incorrecta.");
					    JOptionPane.showMessageDialog(this, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} 
					
					
				}
	        });
	            
	        registerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                registerUser();
	            	}
	        });

				private void registerUser() {
					//Método para registrar un nuevo usuario
					private void registerUser() {
					String user = userField.getText().trim(); // Quitamos espacios extra que hayan sido añadidos con .trim
					String pass = new String(passField.getPassword());
					
					if (user.isEmpty() || pass.isEmpty()) {
					    JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
					    return;
					}
					
					if (validUsers.containsKey(user)) {
					  JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
					    validUsers.put(user, pass);
					    saveUsers();
					    JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					
				}
	        
		        
		        // Añadimos los elementos
		            this.setTitle("Bienvenid@!");
		            panel.add(userLabel);
		            panel.add(userField);
		            panel.add(passLabel);
		            panel.add(passField);
		            panel.add(new JLabel());
		            panel.add(loginButton);
		            panel.add(registerButton);

		            
		            add(panel, BorderLayout.CENTER);
		            add(statusLabel, BorderLayout.SOUTH);
		        

				
	    }

		public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> {
			    new Ventana1Login().setVisible(true);
			}
		}}


//// ✅ Cargar usuarios desde archivo (si existe)
//@SuppressWarnings("unchecked")
//private HashMap<String, String> loadUsers() {
//File file = new File(FILE_NAME);
//if (!file.exists()) {
//    return new HashMap<>(); // si no existe el archivo, devolvemos un mapa vacío
//}
//
//try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
//    return (HashMap<String, String>) in.readObject();
//} catch (IOException | ClassNotFoundException e) {
//    JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//    return new HashMap<>();
//}
//}
//