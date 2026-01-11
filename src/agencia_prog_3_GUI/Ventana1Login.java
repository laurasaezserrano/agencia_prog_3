package agencia_prog_3_GUI;

import javax.swing.*;
import agencia_prog_3_thread.VentanaCarga;
import db.VentanaGestionDB;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Ventana1Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	protected static JTextField userField;
    protected static JPasswordField passField;
    
    private JButton loginButton;
    private JLabel statusLabel;
    
    private String userCSV = "resources/data/user.csv";
    private HashMap<String, String> validUsers;

    public Ventana1Login() {
        validUsers = loadUsers(); 

        setTitle("Bienvenid@!");
        setSize(360, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        
        
        //panel principal
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        main.setBackground(new Color(245, 248, 255));
        add(main, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 248, 255));
        panel.setPreferredSize(new Dimension(320, 120));

        JLabel userLabel = new JLabel("Usuario:");
        JLabel passLabel = new JLabel("Contraseña:");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        userField = new JTextField();
        passField = new JPasswordField();
        
        //boton de ver contraseña
        final char echoChar = passField.getEchoChar();
        JToggleButton mostrarcontraseña = new JToggleButton("👁");
        mostrarcontraseña.setFocusable(false);
        mostrarcontraseña.setBorderPainted(false);
        mostrarcontraseña.setFocusPainted(false);
        mostrarcontraseña.setContentAreaFilled(false);
        mostrarcontraseña.setOpaque(false);
        mostrarcontraseña.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mostrarcontraseña.setToolTipText("Mostar/Ocultar");
        mostrarcontraseña.setPreferredSize(new Dimension(28, 28));
                
        mostrarcontraseña.addActionListener(e -> {
            if (mostrarcontraseña.isSelected()) {
                passField.setEchoChar((char) 0); //mostrar
            } else {
                passField.setEchoChar(echoChar);//ocultar
            }
        });
      
        
        // Panel para contraseña + ojo
        JPanel passPanel = new JPanel(new BorderLayout(6, 0));
        passPanel.setOpaque(false);
        passPanel.add(passField, BorderLayout.CENTER);
        passPanel.add(mostrarcontraseña, BorderLayout.EAST);
        
        
        loginButton = new JButton("Iniciar sesión");
        loginButton.setPreferredSize(new Dimension(180, 30));
        loginButton.setBackground(new Color(50, 150, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        
        
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(245, 248, 255));
        
        panel.add(userLabel);
        panel.add(userField);
        
        panel.add(passLabel);
        panel.add(passPanel);
        
        panel.add(new JLabel()); 
        panel.add(loginButton);

        JPanel centrado = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centrado.setBackground(new Color(245, 248, 255));
        centrado.add(panel);
        main.add(centrado, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> checkLogin());

        getRootPane().registerKeyboardAction(e -> { //al darle al esc se vacia el texto
        	userField.setText("");
        	passField.setText("");
        	statusLabel.setText("");
        	userField.requestFocusInWindow();
        }, 
        		KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), 
        		JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        KeyAdapter introListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkLogin();
                }
            }
        };
        
        KeyAdapter userFieldListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkLogin();
                } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    passField.requestFocusInWindow();
                    e.consume();
                }
            }
        };

        userField.addKeyListener(userFieldListener);
        passField.addKeyListener(introListener);
    }
    
    
    private HashMap<String, String> loadUsers () {
        File file = new File(userCSV);
        if (!file.exists()) return new HashMap<>();
        
        HashMap<String, String> usuarios = new HashMap<>();
        
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
        	
        	String linea = null;
        	int numlinea = 0;
        	
        	while ((linea = in.readLine()) != null) {
        		numlinea++;
            	if (numlinea == 1 && (linea.contains("Usuario") || linea.contains("contraseña"))) { 
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
    
    private void checkLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (user.equals("admin") && pass.equals("admin")) {
            statusLabel.setText("Bienvenido, Administrador!");
            JOptionPane.showMessageDialog(this, "Acceso de Administrador correcto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            VentanaGestionDB gestorDB = new VentanaGestionDB(); 
            gestorDB.setVisible(true);
            
            dispose(); // Cerrar la ventana de login
            return;
        }
	        																																																							
	        if (validUsers.containsKey(user) && validUsers.get(user).equals(pass)) {
            statusLabel.setText("Bienvenido, " + user + "!");
            JOptionPane.showMessageDialog(this, "Inicio de sesión correcto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            AlmacenajeSesion.iniciarSesion(user, pass); 

            this.setVisible(false);
            
            Runnable abrirVentanaInicio = () -> {
                VentanaInicio inicio = new VentanaInicio();
                inicio.setVisible(true);
                dispose(); 
            };
            
            VentanaCarga carga = new VentanaCarga(abrirVentanaInicio);
            carga.setVisible(true);
            
        } else if (!validUsers.containsKey(user)) {
            int option = JOptionPane.showConfirmDialog(this, "El usuario no está registrado. ¿Deseas registrarte?",
                    "Usuario no encontrado", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                registerUser();
                // La llamada a iniciarSesion se hace dentro de registerUser para evitar duplicados.
                
                // Si el registro es exitoso y quieres abrir VentanaInicio después:
                this.setVisible(false);
                Runnable abrirVentanaInicio = () -> {
                    VentanaInicio inicio = new VentanaInicio();
                    inicio.setVisible(true);
                    dispose(); 
                };
                
                VentanaCarga carga = new VentanaCarga(abrirVentanaInicio);
                carga.setVisible(true);
            }
        } else {
           statusLabel.setText("Contraseña incorrecta. Vuelva a intentarlo.");
           JOptionPane.showMessageDialog(this, "Contraseña incorrecta. Vuelva a intentarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
           }
        }

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
            saveUsers(user, pass); 
            AlmacenajeSesion.iniciarSesion(user, pass);
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            statusLabel.setText("Usuario: " + user + " registrado");
        }
    }
    
    private void saveUsers(String user, String password) {
        try {
        	BufferedWriter pw = new BufferedWriter (new FileWriter(userCSV, true)) ;
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
    
    public static void main(String[] args) {
    	/**
    	 * IAG - Claude (nueva forma de organziar el main)
    	 */
        SwingUtilities.invokeLater(() -> new Ventana1Login().setVisible(true));
    }
}