package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.List;

public class VentanaCarga extends JDialog {
    private static final long serialVersionUID = 1L;
    private JLabel animationLabel;
    private JProgressBar progressBar;

    public VentanaCarga(JFrame parent) {
        super(parent, "Cargando aplicación...", true);
        setSize(350, 180);
        setLocationRelativeTo(parent);
        
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { }
        });
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        animationLabel = new JLabel();
        animationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Usar ImageIcon con URL para animaciones GIF
        try {
            
            URL imageUrl = getClass().getResource("/resources/images/avion_animado.gif");
          
            if (imageUrl != null) {
                
                animationLabel.setIcon(new ImageIcon(imageUrl));
            } else {
                // El recurso NO se encuentra en el Classpath.
                animationLabel.setText("Animación de avión no encontrada. (Error de CLASSPATH)");
            }
        } catch (Exception e) {
            animationLabel.setText("Error al cargar la animación.");
        }
        
        JLabel messageLabel = new JLabel("Inicializando servicios de la agencia...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(false);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(animationLabel, BorderLayout.CENTER);
        topPanel.add(messageLabel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }
    
    public void startLoading(Runnable onFinish) {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            
            @Override
            protected Void doInBackground() throws Exception {
                int totalSteps = 40;
                int sleepTime = 75;
                
                for (int i = 0; i <= totalSteps; i++) {
                    int percent = (int) (((double) i / totalSteps) * 100);
                    publish(percent); 
                    Thread.sleep(sleepTime); 
                }
                return null;
            }
            
            @Override
            protected void process(List<Integer> chunks) {
                if (!chunks.isEmpty()) {
                    int latestPercent = chunks.get(chunks.size() - 1); 
                    progressBar.setValue(latestPercent);
                }
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
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