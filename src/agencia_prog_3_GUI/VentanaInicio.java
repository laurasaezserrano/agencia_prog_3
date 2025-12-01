package agencia_prog_3_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private static final Color COLOR_PRINCIPAL = new Color(51, 102, 153);

    public VentanaInicio() {
        setTitle("Agencia de Viajes S.L. - Inicio");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema de Gestión de Viajes", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(COLOR_PRINCIPAL);
        
        JButton offersButton = new JButton("Ir a la Cuadrícula de Ofertas");
        offersButton.setFont(new Font("Arial", Font.BOLD, 16));
        offersButton.setBackground(COLOR_PRINCIPAL);
        offersButton.setForeground(Color.WHITE);
        offersButton.setFocusPainted(false);
        
        offersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CuadriculaOfertas ofertas = new CuadriculaOfertas();
                ofertas.setVisible(true);
                dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(offersButton);
        
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
}