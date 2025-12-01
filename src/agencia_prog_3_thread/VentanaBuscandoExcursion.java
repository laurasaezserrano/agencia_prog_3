package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;

public class VentanaBuscandoExcursion extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lblTexto;
    private JProgressBar barra;

    public VentanaBuscandoExcursion(Frame owner) {
        super(owner, "Cargando Excursiones...", true);
        initComponents();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(50, 150, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTexto = new JLabel("Buscando excursiones...", SwingConstants.CENTER);
        lblTexto.setFont(new Font("Arial", Font.BOLD, 18));
        lblTexto.setForeground(Color.WHITE);

        barra = new JProgressBar();
        barra.setIndeterminate(true);
        barra.setPreferredSize(new Dimension(300, 25));

        panel.add(lblTexto, BorderLayout.NORTH);
        panel.add(barra, BorderLayout.CENTER);

        setContentPane(panel);
    }

    public void cerrar() {
        dispose();
    }
}
