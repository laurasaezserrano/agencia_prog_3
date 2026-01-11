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
    private Runnable onFinishCallback;

    public VentanaCarga(Runnable callback) {
        this.onFinishCallback = callback;
        
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBackground(COLOR_FONDO);
        
        JLabel titleLabel = new JLabel("Sunny Agencia", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(COLOR_PRINCIPAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        statusLabel = new JLabel("Inicializando servicios...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(COLOR_PRINCIPAL.darker());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(statusLabel, BorderLayout.SOUTH);

        // Panel central para el GIF con tamaño preferido
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(COLOR_FONDO);
        
        boolean gifLoaded = false;
        /** IAG - Solución ante el problema del gif en el thread
         * Fuente: Claude
         */
        try {
            // Intenta cargar desde "resources/images/avion_animado.gif"
            java.net.URL imageUrl = getClass().getResource("/resources/images/avion.gif");
            
            if (imageUrl == null) {
                // Si no está en /resources, intenta desde la raíz del classpath
                imageUrl = getClass().getResource("/avion.gif");
            }
            
            if (imageUrl == null) {
                // Última opción: busca en el paquete actual
                imageUrl = getClass().getResource("avion.gif");
            }
            
            if (imageUrl == null) {
                // Intenta cargar desde ruta del sistema de archivos (para desarrollo)
                java.io.File gifFile = new java.io.File("src/resources/images/avion.gif");
                if (!gifFile.exists()) {
                    gifFile = new java.io.File("resources/images/avion.gif");
                }
                if (!gifFile.exists()) {
                    gifFile = new java.io.File("avion_animado.gif");
                }
                if (gifFile.exists()) {
                    imageUrl = gifFile.toURI().toURL();
                }
            }
            
            if (imageUrl != null) {
                ImageIcon loadingIcon = new ImageIcon(imageUrl);
                int newWidth = 150;
                int newHeight = 150;
                Image scaledImage = loadingIcon.getImage().getScaledInstance(
                    newWidth, newHeight, Image.SCALE_DEFAULT
                );
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                
                JLabel logo = new JLabel(scaledIcon, SwingConstants.CENTER);
                logo.setPreferredSize(new Dimension(350, 350));
                centerPanel.add(logo, BorderLayout.CENTER);
                gifLoaded = true;
                System.out.println("✓ GIF cargado correctamente desde: " + imageUrl);
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar el GIF: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Si el GIF no se cargó, mostrar un placeholder
        if (!gifLoaded) {
            JLabel errorLabel = new JLabel("Animación no disponible", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            centerPanel.add(errorLabel, BorderLayout.CENTER);
            System.err.println("Advertencia: No se pudo cargar el archivo avion_animado.gif");
        }
        
        content.add(centerPanel, BorderLayout.CENTER);
        content.setBorder(BorderFactory.createLineBorder(COLOR_PRINCIPAL, 5));
        
        this.setContentPane(content);
        this.setSize(500, 380);
        this.setLocationRelativeTo(null);
        
        startLoadingProcess();
        startAnimationThread();
    }

    private void startLoadingProcess() {
        new Thread(() -> {
            try {
                Thread.sleep(TIEMPO_CARGA_SIMULADA_MS / 3);
                updateStatus("Conectando con base de datos");
                
                Thread.sleep(TIEMPO_CARGA_SIMULADA_MS / 3);
                updateStatus("Cargando datos de ofertas");
                
                Thread.sleep(TIEMPO_CARGA_SIMULADA_MS / 3);
                updateStatus("Inicialización completada");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                closeSplashAndStartApp();
            }
        }).start();
    }

    private void startAnimationThread() {
        new Thread(() -> {
            int dotCount = 0;
            while (keepAnimating) {
                final int currentDotCount = dotCount;
                SwingUtilities.invokeLater(() -> {
                    String currentStatus = statusLabel.getText();
                    String currentBaseText = currentStatus.replaceAll("\\.+", "");
                    String dots = ".".repeat(currentDotCount % 4);
                    statusLabel.setText(currentBaseText + dots);
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
            if (onFinishCallback != null) {
                onFinishCallback.run();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCarga splash = new VentanaCarga(() -> {
                System.out.println("Aplicación iniciada correctamente");
            });
            splash.setVisible(true);
        });
    }
}