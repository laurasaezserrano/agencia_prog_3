package agencia_prog_3_thread;  // pon el tuyo

import javax.swing.*;
import java.awt.*;

public class VentanaBuscandoPerfil extends JDialog {

    private JLabel lblTexto;

    public VentanaBuscandoPerfil(Frame owner) {
        super(owner, "Cargando perfil...", true);

        lblTexto = new JLabel("Cargando tu perfil...", SwingConstants.CENTER);
        lblTexto.setFont(new Font("Arial", Font.BOLD, 18));
        lblTexto.setForeground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 150, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(lblTexto, BorderLayout.CENTER);

        setContentPane(panel);
        setSize(400, 150);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public void actualizarTexto(String texto) {
        lblTexto.setText(texto);
    }

    public void cerrar() {
        dispose();
    }
}
