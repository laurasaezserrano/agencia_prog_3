package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class VentanaCarga extends JDialog {
    private static final long serialVersionUID = 1L;
    private JLabel animationLabel;
    private JProgressBar progressBar;

    /**
     * Constructor para la ventana de carga.
     * @param parent La ventana padre (Ventana1Login)
     */
    public VentanaCarga(JFrame parent) {
        // JDialog modal para bloquear la Ventana1Login
        super(parent, "Cargando aplicación...", true);
        
        setSize(350, 180); // Tamaño ajustado para la imagen/GIF
        setLocationRelativeTo(parent);
        
        // Bloquea el cierre manual
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // No permitir cerrar
            }
        });
        
        setResizable(false);

        // Contenedor principal
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE); // Fondo blanco para resaltar

        // --- 1. Etiqueta de Animación (GIF) ---
        animationLabel = new JLabel();
        animationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Carga la imagen o GIF desde los recursos
        try {
            // La ruta es relativa al CLASSPATH cuando se usa getResource()
            URL imageUrl = getClass().getResource("\"C:\\Users\\laura.saez.serrano\\prog apps\\agencia_prog_3\\resources\\images\\avion_animado.gif\""); 
            if (imageUrl == null) {
                // Mensaje si no se encuentra el recurso
                animationLabel.setText("Cargando: recurso de animación no encontrado.");
            } else {
                ImageIcon icon = new ImageIcon(imageUrl);
                // Si es un GIF, se reproducirá automáticamente.
                // Si deseas escalar (solo si es muy grande), usa:
                // Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
                // animationLabel.setIcon(new ImageIcon(image));
                animationLabel.setIcon(icon);
            }
        } catch (Exception e) {
            animationLabel.setText("Error al cargar la animación.");
        }
        
        // Etiqueta de mensaje
        JLabel messageLabel = new JLabel("Inicializando servicios de la agencia...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        // --- 2. Barra de Progreso ---
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true); // Animación continua
        
        // Panel superior para la imagen y el mensaje
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(animationLabel, BorderLayout.CENTER);
        topPanel.add(messageLabel, BorderLayout.SOUTH);

        // Ensamblaje
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }
    
    /**
     * Inicia la simulación de carga en un hilo de fondo.
     * @param onFinish Código (Runnable) a ejecutar después de que la carga termine (abrir VentanaInicio).
     */
    public void startLoading(Runnable onFinish) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulación de la carga de 3 segundos
                Thread.sleep(3000); 
                return null;
            }

            @Override
            protected void done() {
                // Se ejecuta en el EDT
                dispose(); 
                if (onFinish != null) {
                    onFinish.run(); 
                }
            }
        };
        
        worker.execute();
        setVisible(true); 
    }
}