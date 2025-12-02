package agencia_prog_3_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

public class VentanaUser extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color COLOR_PRINCIPAL = new Color(51, 102, 153);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final String RUTA_CSV = "usuarios.csv";

    private JTextField nusuario;
    private JPasswordField contraseña; 
    private JTextField nombre;
    private JTextField dni;
    private JTextField email;
    private JTextField telefono;
    private JTextField direccion;
    private JComboBox<String> cbIdioma;
    private JComboBox<String> cbMoneda;
    private JButton btnModificarContrasena;
    private JButton botonGuardar;
    private JToggleButton btnMostrarContrasena;
    private JFrame ventanaAnterior;

    private String nombreUsuarioActual;
    private String contrasenaActual;

    public VentanaUser(String user, String pass) {
        this(user, pass, null);
    }

    public VentanaUser(String user, String pass, JFrame ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
        this.nombreUsuarioActual = user;
        this.contrasenaActual = pass;
        
        setTitle("Perfil del Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        setResizable(false);
        
        JLabel titleLabel = new JLabel("MI PERFIL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PRINCIPAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel panelUsuario = new JPanel(new GridBagLayout());
        panelUsuario.setBackground(Color.WHITE);
        
        panelUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRINCIPAL.darker(), 1),
            BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 5, 6, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        
        int row = 0;
        
        JLabel lblUser = new JLabel("Nombre de usuario:");
        lblUser.setFont(labelFont);
        lblUser.setForeground(COLOR_PRINCIPAL.darker());
        nusuario = new JTextField(nombreUsuarioActual);
        nusuario.setEditable(false);
        nusuario.setBackground(COLOR_FONDO);
        addPairToGrid(panelUsuario, gbc, lblUser, nusuario, row++);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(labelFont);
        lblPass.setForeground(COLOR_PRINCIPAL.darker());

        contraseña = new JPasswordField(contrasenaActual);
        contraseña.setEditable(false);
        contraseña.setBackground(COLOR_FONDO);
        
        JPanel passContainer = new JPanel(new BorderLayout());
        passContainer.setBackground(contraseña.getBackground());
        
        btnMostrarContrasena = new JToggleButton();
        
        try {
            URL visibleUrl = getClass().getResource("/resources/images/eye_visible.png");
            URL hiddenUrl = getClass().getResource("/resources/images/eye_hidden.png");
            
            ImageIcon visibleIcon = (visibleUrl != null) ? new ImageIcon(new ImageIcon(visibleUrl).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)) : new ImageIcon();
            ImageIcon hiddenIcon = (hiddenUrl != null) ? new ImageIcon(new ImageIcon(hiddenUrl).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)) : new ImageIcon();
            
            btnMostrarContrasena.setIcon((hiddenIcon.getImage() != null) ? hiddenIcon : new ImageIcon());
            btnMostrarContrasena.setText((hiddenIcon.getImage() == null) ? "👁️" : "");
            
            btnMostrarContrasena.setSelectedIcon((visibleIcon.getImage() != null) ? visibleIcon : new ImageIcon());

        } catch (Exception ex) {
            btnMostrarContrasena.setText("👁️");
        }
        
        btnMostrarContrasena.setPreferredSize(new Dimension(30, 25));
        btnMostrarContrasena.setMargin(new Insets(2, 2, 2, 2));
        btnMostrarContrasena.setFocusPainted(false);
        
        passContainer.add(contraseña, BorderLayout.CENTER);
        passContainer.add(btnMostrarContrasena, BorderLayout.EAST);
        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3; gbc.anchor = GridBagConstraints.WEST; panelUsuario.add(lblPass, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 0.7; gbc.anchor = GridBagConstraints.EAST; panelUsuario.add(passContainer, gbc);
        
        btnModificarContrasena = new JButton("Modificar Contraseña");
        btnModificarContrasena.setFont(new Font("Arial", Font.BOLD, 10));
        btnModificarContrasena.setBackground(COLOR_PRINCIPAL.brighter());
        btnModificarContrasena.setForeground(Color.WHITE);
        btnModificarContrasena.setPreferredSize(new Dimension(140, 20)); 
        
        gbc.gridx = 2;
        gbc.gridy = row++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panelUsuario.add(btnModificarContrasena, gbc);

        nombre = new JTextField();
        addPairToGrid(panelUsuario, gbc, createLabel("Nombre completo:", labelFont), nombre, row++);
        
        dni = new JTextField();
        addPairToGrid(panelUsuario, gbc, createLabel("DNI / Pasaporte:", labelFont), dni, row++);
        
        email = new JTextField();
        addPairToGrid(panelUsuario, gbc, createLabel("Email de contacto:", labelFont), email, row++);
        
        telefono = new JTextField();
        addPairToGrid(panelUsuario, gbc, createLabel("Teléfono de contacto:", labelFont), telefono, row++);
        
        direccion = new JTextField();
        addPairToGrid(panelUsuario, gbc, createLabel("Dirección:", labelFont), direccion, row++);
        
        cbIdioma = new JComboBox<>(new String[]{"Español", "Ingles", "Francés"});
        addPairToGrid(panelUsuario, gbc, createLabel("Seleccione idioma:", labelFont), cbIdioma, row++);
        
        cbMoneda = new JComboBox<>(new String[]{"EUR", "USD", "GBP"});
        addPairToGrid(panelUsuario, gbc, createLabel("Seleccione moneda:", labelFont), cbMoneda, row++);
        
        add(panelUsuario, BorderLayout.CENTER);
        
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelSur.setBackground(COLOR_FONDO);
        
        JButton botonAtras = new JButton("Atrás");
        botonGuardar = new JButton("Guardar Perfil");
        
        botonAtras.setBackground(new Color(180, 180, 180));
        botonGuardar.setBackground(COLOR_PRINCIPAL);
        botonGuardar.setForeground(Color.WHITE);
        
        panelSur.add(botonAtras);
        panelSur.add(botonGuardar);
        add(panelSur, BorderLayout.SOUTH);

        btnMostrarContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnMostrarContrasena.isSelected()) {
                    contraseña.setEchoChar((char) 0);
                } else {
                    contraseña.setEchoChar('*');
                }
            }
        });

        btnModificarContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!contraseña.isEditable()) {
                    activarModoEdicionContrasena();
                } else {
                    guardarNuevaContrasena();
                }
            }
        });
        
        botonAtras.addActionListener(e -> {
        	VentanaInicio inicio = new VentanaInicio();
            inicio.setVisible(true);
        	dispose();
            
        });
        
        botonGuardar.addActionListener(e -> {
            String info = String.format("""
                    Perfil guardado
                    Nombre: %s
                    Documento: %s
                    Email: %s
                    Teléfono: %s
                    Dirección: %s
                    Idioma: %s
                    Moneda: %s
                    """,
                    nombre.getText(),
                    dni.getText(),
                    email.getText(),
                    telefono.getText(),
                    direccion.getText(),
                    cbIdioma.getSelectedItem(),
                    cbMoneda.getSelectedItem()
                    );
            JOptionPane.showMessageDialog(this, info, "Datos guardados", JOptionPane.INFORMATION_MESSAGE);
        });
        
        setVisible(true);
    }
    
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(COLOR_PRINCIPAL.darker());
        return label;
    }

    private void addPairToGrid(JPanel panel, GridBagConstraints gbc, JLabel label, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; 
        panel.add(component, gbc);
    }

    private void activarModoEdicionContrasena() {
        contraseña.setEditable(true);
        contraseña.setBackground(Color.WHITE);
        contraseña.setText("");
        contraseña.setEchoChar('*');
        btnModificarContrasena.setText("Guardar Nueva Contraseña");
        btnMostrarContrasena.setSelected(false);
        JOptionPane.showMessageDialog(this, "Modifique el campo Contraseña y pulse el botón 'Guardar Nueva Contraseña'.", "Modificación Activada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void guardarNuevaContrasena() {
        String nuevaContrasena = new String(contraseña.getPassword());
        
        if (nuevaContrasena.length() < 4) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (modificarContrasenaEnCSV(nombreUsuarioActual, nuevaContrasena)) {
            this.contrasenaActual = nuevaContrasena;
            contraseña.setText(nuevaContrasena);
            contraseña.setEditable(false);
            contraseña.setBackground(COLOR_FONDO);
            contraseña.setEchoChar('*');
            btnModificarContrasena.setText("Modificar Contraseña");
            btnMostrarContrasena.setSelected(false);
            JOptionPane.showMessageDialog(this, "Contraseña actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la contraseña en el archivo.", "Error de E/S", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean modificarContrasenaEnCSV(String usuario, String nuevaContrasena) {
        File inputFile = new File(RUTA_CSV);
        File tempFile = new File("temp_" + RUTA_CSV);
        boolean usuarioEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] datos = currentLine.split(",");
                if (datos.length >= 2 && datos[0].trim().equals(usuario)) {
                    datos[1] = nuevaContrasena;
                    writer.write(String.join(",", datos) + System.getProperty("line.separator"));
                    usuarioEncontrado = true;
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (usuarioEncontrado) {
            if (inputFile.delete()) {
                if (!tempFile.renameTo(inputFile)) {
                    System.err.println("Error: No se pudo renombrar el archivo temporal.");
                    return false;
                }
            } else {
                System.err.println("Error: No se pudo borrar el archivo original.");
                return false;
            }
        } else {
            tempFile.delete();
            return false; 
        }
        return true;
    }
}