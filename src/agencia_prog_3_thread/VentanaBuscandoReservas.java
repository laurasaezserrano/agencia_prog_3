package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;

public class VentanaBuscandoReservas extends JDialog {

    private JLabel lblTexto;

    public VentanaBuscandoReservas(Frame owner) {
        super(owner, "Buscando reservas...", true);

        lblTexto = new JLabel("Iniciando proceso...", SwingConstants.CENTER);
        lblTexto.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 150, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        lblTexto.setForeground(Color.WHITE);

        panel.add(lblTexto, BorderLayout.CENTER);
        setContentPane(panel);

        setSize(400, 150);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public void cambiarTexto(String texto) {
        lblTexto.setText(texto);
    }

    public void cerrar() {
        dispose();
    }
}
