package agencia_prog_3_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class Ventana1Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;

    // HashMap de usuarios
    private HashMap<String, String> validUsers;

    public Ventana1Login() {
        validUsers = loadUsers(); // Aquí se cargan los usuarios que se han registrado correctamente

        setTitle("Bienvenid@!");
        setSize(350, 160);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 3, 4));

        JLabel userLabel = new JLabel("Usuario:");
        JLabel passLabel = new JLabel("Contraseña:");
        userField = new JTextField();
        passField = new JPasswordField();
        loginButton = new JButton("Iniciar sesión");
        //registerButton = new JButton ("Registrarse");
        statusLabel = new JLabel("", SwingConstants.CENTER);
        
        
        // Añadimos los elementos al panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);
        //panel.add(registerButton);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(new JLabel()); // Espacio vacío en la primera columna
        panel.add(new JLabel()); // Espacio vacío en la segunda columna
        buttonPanel.add(loginButton); // Agregamos el botón centrado
        panel.add(buttonPanel);

        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Action listeners
        loginButton.addActionListener(e -> checkLogin());
        //registerButton.addActionListener(e -> registerUser());
    }
    
   
    private HashMap<String, String> loadUsers () {
        File file = new File("UserCSV.csv");
        if (!file.exists()) return new HashMap<>();
        HashMap<String, String> usuarios = new HashMap<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
        	
        	String linea = null;
        	int numlinea = 0;
        	
        	while ((linea = in.readLine()) != null) {
        		numlinea++;
	        	if (numlinea == 1 && linea.contains("Usuario")|| linea.contains("contraseña")) {
	        		continue;
	        	}
	        	String[] data = linea.split(",");
	        	String usuario = data[0].trim();
	        	String password = data[1].trim();
	        	usuarios.put(usuario, password);
        	}
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al cargar usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return usuarios;
    }
    
    //Comprobamos si esta en la base de datos
    private void checkLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validUsers.containsKey(user) && validUsers.get(user).equals(pass)) {
            statusLabel.setText("Bienvenido, " + user + "!"); //Parte inferior del Layout
            JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (!validUsers.containsKey(user)) {
            int option = JOptionPane.showConfirmDialog(this, "El usuario no está registrado. ¿Deseas registrarte?",
                    "Usuario no encontrado", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                registerUser();
//                JOptionPane.showMessageDialog(this, "Registro realizado con éxito", "Continuar", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
           // Usuario no existe
            statusLabel.setText("No tienes usuario. Regístrate.");
            JOptionPane.showMessageDialog(this, "No tienes usuario. Por favor, regístrate.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }

    // Registramos el usuario
    private void registerUser() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validUsers.containsKey(user)) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            validUsers.put(user, pass);
            validUsers.put(user, pass);
            saveUsers(user, pass); // Guardamos en archivo
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Usuario: " + user + " registrado");
        }
    }
    
    // Guardar usuarios en archivo
    private void saveUsers(String user, String password) {
        try {
        	BufferedWriter  pw =   new BufferedWriter (new FileWriter("UserCSV.csv", true)) ;
                pw.write(user + "," + password);
                pw.newLine();
                pw.close();
                
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al guardar usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
    
    //MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ventana1Login().setVisible(true));
    }
}
