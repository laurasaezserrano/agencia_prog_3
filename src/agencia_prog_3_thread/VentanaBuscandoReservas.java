package agencia_prog_3_thread;  // o tu paquete real

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VentanaBuscandoReservas extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblPorcentaje;
    private JProgressBar progreso;

    private int valor = 0;
    private Timer timer;

    public VentanaBuscandoReservas(Frame owner) {
        super(owner, "Buscando reservas...", true);
        initComponents();
        iniciarProgreso();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {

        // Fondo azul
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(25, 118, 210));
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tarjeta blanca
        JPanel tarjeta = new JPanel(new BorderLayout(15, 15));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        lblTitulo = new JLabel("Buscando reservas...", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 33, 33));

        // Subtítulo
        lblSubtitulo = new JLabel("Comprobando tus reservas y disponibilidad", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 100, 100));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 5));
        textos.setBackground(Color.WHITE);
        textos.add(lblTitulo);
        textos.add(lblSubtitulo);

        // Barra de progreso real
        progreso = new JProgressBar(0, 100);
        progreso.setValue(0);
        progreso.setPreferredSize(new Dimension(300, 25));

        // Porcentaje
        lblPorcentaje = new JLabel("0%", SwingConstants.CENTER);
        lblPorcentaje.setFont(new Font("Arial", Font.BOLD, 15));

        JPanel panelProgreso = new JPanel(new BorderLayout(10, 0));
        panelProgreso.setBackground(Color.WHITE);
        panelProgreso.add(progreso, BorderLayout.CENTER);
        panelProgreso.add(lblPorcentaje, BorderLayout.EAST);

        tarjeta.add(textos, BorderLayout.NORTH);
        tarjeta.add(panelProgreso, BorderLayout.CENTER);

        fondo.add(tarjeta);
        setContentPane(fondo);
    }

    private void iniciarProgreso() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                valor += 3; // velocidad de avance
                progreso.setValue(valor);
                lblPorcentaje.setText(valor + "%");

                if (valor >= 100) {
                    timer.cancel();
                    cerrar();
                }
            }
        }, 0, 120); // cada 120 ms
    }

    public void cerrar() {
        dispose();
    }
}

