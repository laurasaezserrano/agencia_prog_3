package agencia_prog_3_thread;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaBuscandoVuelo extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lblDestino;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private PanelAvion panelAvion;
    private Timer timerDestinos;
    private ImageIcon[] destinos;
    private int indiceDestino = 0;
    private JProgressBar progressBar;

    public VentanaBuscandoVuelo(Frame owner) {
        super(owner, "Buscando vuelos...", true);
        initComponents();
        iniciarAnimaciones();
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
    	JPanel fondo = new JPanel();
    	fondo.setBackground(new Color(50, 150, 200));
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel tarjetaContenido = new JPanel(new BorderLayout(10, 10)); //tarjeta blanca sobre la que iran las imagenes y texto
        tarjetaContenido.setBackground(Color.WHITE);
        tarjetaContenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelAvion = new PanelAvion();
        panelAvion.setPreferredSize(new Dimension(500, 120));
        panelAvion.setOpaque(false);

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.setOpaque(false);

        lblTitulo = new JLabel("Buscando vuelos...", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 20f));
        lblTitulo.setForeground(Color.BLACK);

        lblSubtitulo = new JLabel("Por favor, espera unos segundos", SwingConstants.CENTER);
        lblSubtitulo.setFont(lblSubtitulo.getFont().deriveFont(Font.PLAIN, 13f));
        lblSubtitulo.setForeground(new Color(90, 90, 90));

        JPanel panelTextos = new JPanel(new GridLayout(2, 1, 0, 3));
        panelTextos.setOpaque(false);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);

        lblDestino = new JLabel("Cargando destinos...", SwingConstants.CENTER);
        lblDestino.setPreferredSize(new Dimension(220, 220));
        lblDestino.setOpaque(false);

        centro.add(panelTextos, BorderLayout.NORTH);
        centro.add(lblDestino, BorderLayout.CENTER);

        /**
         * Progress bar generada con IAG
         */
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        tarjetaContenido.add(panelAvion, BorderLayout.NORTH);
        tarjetaContenido.add(centro, BorderLayout.CENTER);
        tarjetaContenido.add(progressBar, BorderLayout.SOUTH);

        /**
         * GridBagConstraints generada con IAG para una mejor vision de la ventana
         */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        fondo.add(tarjetaContenido, gbc);

        setContentPane(fondo);
    }

    private void iniciarAnimaciones() {
        panelAvion.iniciarVuelo();
        cargarDestinos();

        timerDestinos = new Timer(1500, (ActionEvent e) -> {
            if (destinos.length == 0) return;
            lblDestino.setIcon(destinos[indiceDestino]);
            lblDestino.setText("");
            indiceDestino = (indiceDestino + 1) % destinos.length;
        });
        timerDestinos.start();
    }

    private void cargarDestinos() {
        destinos = new ImageIcon[] { //imagenes de destinos
            new ImageIcon("resources/images/oferta1.png"),
            new ImageIcon("resources/images/oferta2.png"),
            new ImageIcon("resources/images/oferta3.png"),
            new ImageIcon("resources/images/oferta4.png"),
            new ImageIcon("resources/images/oferta5.png"),
            new ImageIcon("resources/images/oferta6.png"),
            new ImageIcon("resources/images/oferta7.png"),
            new ImageIcon("resources/images/oferta8.png"),
            new ImageIcon("resources/images/oferta9.png")
        };
        int ancho = 420;
        int alto = 220;
        for (int i = 0; i < destinos.length; i++) {
            Image img = destinos[i].getImage();
            Image esc = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH); //IAG
            destinos[i] = new ImageIcon(esc);
        }
    }

    public void cerrarVentana() {
        if (timerDestinos != null) timerDestinos.stop();
        panelAvion.detenerVuelo();
        dispose();
    }
}
