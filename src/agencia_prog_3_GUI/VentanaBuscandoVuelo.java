package agencia_prog_3_GUI;
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
        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color c1 = new Color(33, 150, 243);
                Color c2 = new Color(3, 169, 244);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);
            }
        };
        fondo.setLayout(new GridBagLayout());
        fondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel tarjeta = new JPanel();
        tarjeta.setOpaque(false);
        tarjeta.setLayout(new BorderLayout(10, 10));

        JPanel tarjetaContenido = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 240));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tarjetaContenido.setOpaque(false);
        tarjetaContenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelAvion = new PanelAvion();
        panelAvion.setPreferredSize(new Dimension(500, 120));
        panelAvion.setOpaque(false);

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.setOpaque(false);

        lblTitulo = new JLabel("Buscando vuelos...", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 20f));
        lblTitulo.setForeground(new Color(33, 33, 33));

        lblSubtitulo = new JLabel("Por favor, espera unos segundos", SwingConstants.CENTER);
        lblSubtitulo.setFont(lblSubtitulo.getFont().deriveFont(Font.PLAIN, 13f));
        lblSubtitulo.setForeground(new Color(90, 90, 90));

        JPanel panelTextos = new JPanel(new GridLayout(2, 1, 0, 3));
        panelTextos.setOpaque(false);
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);

        lblDestino = new JLabel("Cargando destinos...", SwingConstants.CENTER);
        lblDestino.setPreferredSize(new Dimension(420, 220));
        lblDestino.setOpaque(false);

        centro.add(panelTextos, BorderLayout.NORTH);
        centro.add(lblDestino, BorderLayout.CENTER);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        tarjetaContenido.add(panelAvion, BorderLayout.NORTH);
        tarjetaContenido.add(centro, BorderLayout.CENTER);
        tarjetaContenido.add(progressBar, BorderLayout.SOUTH);

        tarjeta.add(tarjetaContenido, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        fondo.add(tarjeta, gbc);

        setContentPane(fondo);
    }

    private void iniciarAnimaciones() {
        panelAvion.iniciarVuelo();
        cargarDestinos();

        timerDestinos = new Timer(2000, (ActionEvent e) -> {
            if (destinos.length == 0) return;
            lblDestino.setIcon(destinos[indiceDestino]);
            lblDestino.setText("");
            indiceDestino = (indiceDestino + 1) % destinos.length;
        });
        timerDestinos.start();
    }

    private void cargarDestinos() {
        destinos = new ImageIcon[] {
            new ImageIcon("paris.jpg"),
            new ImageIcon("roma.jpg"),
            new ImageIcon("ny.jpg"),
            new ImageIcon("tokio.jpg")
        };

        int ancho = 420;
        int alto = 220;
        for (int i = 0; i < destinos.length; i++) {
            Image img = destinos[i].getImage();
            Image esc = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            destinos[i] = new ImageIcon(esc);
        }
    }

    public void cerrarVentana() {
        if (timerDestinos != null) timerDestinos.stop();
        panelAvion.detenerVuelo();
        dispose();
    }
}
