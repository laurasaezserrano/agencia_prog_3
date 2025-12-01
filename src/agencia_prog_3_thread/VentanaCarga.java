package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;

public class VentanaCarga extends JWindow {

    private static final long serialVersionUID = 1L;
    private static final Color COLOR_PRINCIPAL = new Color(51, 102, 153);
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final int TIEMPO_CARGA_SIMULADA_MS = 3000;

    private JLabel statusLabel;
    private volatile boolean keepAnimating = true;

    public VentanaCarga() {
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(COLOR_FONDO);
        
        JLabel titleLabel = new JLabel("Sunny Agencia", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(COLOR_PRINCIPAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        statusLabel = new JLabel("Inicializando servicios...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(COLOR_PRINCIPAL.darker());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(statusLabel, BorderLayout.SOUTH);

        // Cargamos el GIF usando ClassLoader para que funcione dentro de un JAR
        try {
            // NOTA: Asegúrate de que "avion_animado.gif" esté en la raíz de tu classpath
            java.net.URL imageUrl = getClass().getResource("resources/images/avion_animado.gif");
            if (imageUrl == null) {
                throw new Exception("Recurso GIF no encontrado. Usando espacio vacío.");
            }
            ImageIcon loadingIcon = new ImageIcon(imageUrl);
            JLabel logo = new JLabel(loadingIcon, SwingConstants.CENTER);
            content.add(logo, BorderLayout.CENTER);
        } catch (Exception e) {
            // Si hay un error al cargar el GIF, se añade un mensaje de error o un espacio
            JLabel errorLabel = new JLabel("Animación no disponible", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            content.add(errorLabel, BorderLayout.CENTER);
        }

        content.setBorder(BorderFactory.createLineBorder(COLOR_PRINCIPAL, 5));
        
        this.setContentPane(content);
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        
        startLoadingProcess();
        startAnimationThread();
    }

    private void startLoadingProcess() {
        // HILO DE TRABAJO PESADO (EL HILO PROFESIONAL COMPLEJO)
        new Thread(() -> {
            try {
                Thread.sleep(TIEMPO_CARGA_SIMULADA_MS / 3);
                updateStatus("Conectando con base de datos...");
                
                Thread.sleep(TIEMPO_CARGA_SIMULADA_MS / 3);
                updateStatus("Cargando datos de ofertas...");
                
                Thread.sleep(TIEMPO_CARGA_SIMULADA_MS / 3);
                updateStatus("Inicialización completada.");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                closeSplashAndStartApp();
            }
        }).start();
    }

    private void startAnimationThread() {
        // HILO DE ANIMACIÓN
        new Thread(() -> {
            String baseText = "Inicializando servicios";
            int dotCount = 0;
            
            while (keepAnimating) {
                final int currentDotCount = dotCount;
                
                SwingUtilities.invokeLater(() -> {
                    String dots = ".".repeat(currentDotCount % 4);
                    statusLabel.setText(baseText + dots);
                });
                
                dotCount++;
                try {
                    Thread.sleep(300); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
        });
    }

    private void closeSplashAndStartApp() {
        keepAnimating = false;
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            
            // Aquí iría la llamada a la ventana principal de tu aplicación (VentanaInicio/CuadriculaOfertas)
            JOptionPane.showMessageDialog(null, "Aplicación lista para iniciar.", 
                                          "Carga Exitosa", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCarga splash = new VentanaCarga();
            splash.setVisible(true);
        });
    }
}