package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VentanaBuscandoExcursion extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblPorcentaje;
    private JProgressBar progreso;

    private int valor = 0;
    private Timer timer;

    public VentanaBuscandoExcursion(Frame owner) {
        super(owner, "Buscando excursiones...", true);
        initComponents();
        iniciarProgreso();
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void initComponents() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(new Color(50, 150, 200)); 
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel tarjeta = new JPanel(new BorderLayout(15, 15));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTitulo = new JLabel("Buscando excursiones...", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLACK);
        lblSubtitulo = new JLabel("Encontrando las mejores actividades para ti", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.BLACK);

        // Panel textos
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 5));
        textos.setBackground(Color.WHITE);
        textos.add(lblTitulo);
        textos.add(lblSubtitulo);
        
        // Barra de progreso
        progreso = new JProgressBar(0, 100);
        progreso.setValue(0);
        progreso.setPreferredSize(new Dimension(300, 25));
        progreso.setMaximumSize(new Dimension(300, 25));
        progreso.setMinimumSize(new Dimension(300, 25));
        pack();

        lblPorcentaje = new JLabel("0%", SwingConstants.CENTER);
        lblPorcentaje.setFont(new Font("Arial", Font.BOLD, 15));

        // Panel barra + porcentaje
        JPanel panelProgreso = new JPanel(new BorderLayout(10, 0));
        panelProgreso.setBackground(Color.WHITE);
        panelProgreso.add(progreso, BorderLayout.CENTER);
        panelProgreso.add(lblPorcentaje, BorderLayout.EAST);

        tarjeta.add(textos, BorderLayout.NORTH);
        tarjeta.add(panelProgreso, BorderLayout.CENTER);
        fondo.add(tarjeta);
        setContentPane(fondo);
    }

    //progreso de la progressbar
    private void iniciarProgreso() {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                valor += 2; // valor de progreso
                progreso.setValue(valor);
                lblPorcentaje.setText(valor + "%");

                if (valor >= 100) {
                    timer.cancel();
                    cerrar();
                }
            }
        }, 0, 120); // cada 120ms avanza
    }

    public void cerrar() {
        dispose();
    }
}
