package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class VentanaContacto extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame ventanaMadre;

    private static final Color COLOR_PRINCIPAL = new Color(51, 102, 153);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);

    public VentanaContacto(JFrame padre) {
        super("Información de Contacto - AGENCIA PROG 3");
        this.ventanaMadre = padre;

        setSize(550, 350);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_FONDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("AGENCIA PROG 3", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_PRINCIPAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        Border lineBorder = BorderFactory.createLineBorder(COLOR_PRINCIPAL.darker(), 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font valueFont = new Font("Arial", Font.PLAIN, 14);

        int row = 0;
        row = addContactRow(infoPanel, gbc, labelFont, valueFont, "Dirección Principal:", "Calle Ficticia No. 10, Ciudad de Programación", row);
        row = addContactRow(infoPanel, gbc, labelFont, valueFont, "Teléfono:", "+34 555 123 456", row);
        row = addContactRow(infoPanel, gbc, labelFont, valueFont, "Email de Soporte:", "soporte@agenciaprog3.com", row);
        row = addContactRow(infoPanel, gbc, labelFont, valueFont, "Email Comercial:", "comercial@agenciaprog3.com", row);
        row = addContactRow(infoPanel, gbc, labelFont, valueFont, "Horario (CET):", "Lunes a Viernes, 10:00 - 14:00 y 16:00 - 20:00", row);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setBackground(COLOR_FONDO);
        JButton botonCerrar = new JButton("Atrás");
        botonCerrar.setBackground(COLOR_PRINCIPAL);
        botonCerrar.setForeground(Color.WHITE);
        botonCerrar.setFont(labelFont);
        
        botonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (ventanaMadre != null) {
                    ventanaMadre.setVisible(true);
                }
            }
        });

        panelSur.add(botonCerrar);
        mainPanel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true);
    }
    
    private int addContactRow(JPanel panel, GridBagConstraints gbc, Font labelFont, Font valueFont, String labelText, String valueText, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(COLOR_PRINCIPAL.darker());
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.3;
        panel.add(label, gbc);

        JLabel value = new JLabel(valueText);
        value.setFont(valueFont);
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.7;
        panel.add(value, gbc);

        return row + 1;
    }
}