package agencia_prog_3_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class Ventana1Login extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;

    // HashMap de usuarios
    private HashMap<String, String> validUsers;

    public Ventana1Login() {
        validUsers = new HashMap<>(); // Aquí se cargan los usuarios que se han registrado correctamente

        setTitle("Bienvenid@!");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 5, 5));

        JLabel userLabel = new JLabel("Usuario:");
        JLabel passLabel = new JLabel("Contraseña:");
        userField = new JTextField();
        passField = new JPasswordField();
        loginButton = new JButton("Iniciar sesión");
        registerButton = new JButton ("Registrarse");
        statusLabel = new JLabel("", SwingConstants.CENTER);

        // Añadimos los elementos al panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Action listeners
        loginButton.addActionListener(e -> checkLogin());
        registerButton.addActionListener(e -> registerUser());
    }
    // IAG (CHAT GPT)
    @SuppressWarnings("unchecked")
    private HashMap<String, String> loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (HashMap<String, String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al cargar usuarios: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return new HashMap<>();
        }
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
            statusLabel.setText("✅ Bienvenido, " + user + "!");
            JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else if (!validUsers.containsKey(user)) {
            int option = JOptionPane.showConfirmDialog(this, "El usuario no está registrado. ¿Deseas registrarte?",
                    "Usuario no encontrado", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                registerUser();
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
        } else {
            validUsers.put(user, pass);
            validUsers.put(user, pass);
            saveUsers(); // Guardamos en archivo
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Usuario: " + user + " registrado");
        }
    }
    
    // Guardar usuarios en archivo
    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String user : validUsers.keySet()) {
                writer.println(user + ":" + validUsers.get(user));
            }
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
