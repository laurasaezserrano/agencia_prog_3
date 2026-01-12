package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class VentanaCarga extends JWindow{
	private static final long serialVersionUID = 1L;
    private static final int TIEMPO_CARGA_SIMULADA_MS = 3000;

    private JLabel label;
    private volatile boolean keepAnimating = true;
    private Runnable onFinishCallback;
    
    public VentanaCarga(Runnable callback) {
    	this.onFinishCallback = callback;
    	
    	JPanel contenido = new JPanel(new BorderLayout(10, 10));
    	contenido.setBackground(new Color(51, 102, 153));
    	
    	JLabel titulo = new JLabel("Sunny Agencia", SwingConstants.CENTER);
    	titulo.setFont(new Font("Arial", Font.BOLD, 30));
    	titulo.setForeground(new Color(240, 240, 240));
    	titulo.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
    	
    	label = new JLabel("Inicializando servicios...", SwingConstants.CENTER);
    	label.setFont(new Font("Arial", Font.PLAIN, 14));
    	label.setForeground(new Color(51, 102, 153).darker());
        label.setBorder(BorderFactory.createEmptyBorder(10,0,20,0));
        
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(label, BorderLayout.SOUTH);
        
        JPanel panelcentro = new JPanel(new BorderLayout());
        panelcentro.setBackground(new Color(240,240,240));
        
        
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
                panelcentro.add(logo, BorderLayout.CENTER);
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
            panelcentro.add(errorLabel, BorderLayout.CENTER);
            System.err.println("Advertencia: No se pudo cargar el archivo avion_animado.gif");
        }
        
        contenido.add(panelcentro, BorderLayout.CENTER);
        contenido.setBorder(BorderFactory.createLineBorder(new Color(51, 102, 153), 5));
        
        this.setContentPane(contenido);
        this.setSize(500, 380);
        this.setLocationRelativeTo(null);
        
        startLoadingProcess();
        startAnimationThread();
    }

	private void startAnimationThread() {
		new Thread(() -> {
            int dotCount = 0;
            while (keepAnimating) {
                final int currentDotCount = dotCount;
                SwingUtilities.invokeLater(() -> {
                    String currentStatus = label.getText();
                    String currentBaseText = currentStatus.replaceAll("\\.+", "");
                    String dots = ".".repeat(currentDotCount % 4);
                    label.setText(currentBaseText + dots);
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
	            label.setText(status);
	        });
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

