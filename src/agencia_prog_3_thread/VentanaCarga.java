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
        
        JLabel titleLabel = new JLabel("Agencia de Viajes S.L.", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(COLOR_PRINCIPAL);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        statusLabel = new JLabel("Inicializando servicios...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setForeground(COLOR_PRINCIPAL.darker());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        
        content.add(titleLabel, BorderLayout.NORTH);
        content.add(statusLabel, BorderLayout.SOUTH);

        try {
            java.net.URL imageUrl = getClass().getResource("/avion_animado.gif");
            if (imageUrl == null) {
                throw new Exception("Recurso GIF no encontrado.");
            }
            ImageIcon loadingIcon = new ImageIcon(imageUrl);
            JLabel logo = new JLabel(loadingIcon, SwingConstants.CENTER);
            content.add(logo, BorderLayout.CENTER);
        } catch (Exception e) {
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
}