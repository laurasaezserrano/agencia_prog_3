package agencia_prog_3_thread;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class VentanaConfirmacionReserva extends JFrame {

    private JLabel statusLabel;
    private JTextArea logArea;

    public VentanaConfirmacionReserva() {
        super("Simulador de Reserva");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout(10, 10));

        statusLabel = new JLabel("Esperando confirmación...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.LIGHT_GRAY);
        statusLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        statusLabel.setPreferredSize(new Dimension(500, 50));

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        add(statusPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                iniciarReserva();
            }
        });
    }

    private void iniciarReserva() {
        logArea.setText(""); 
        statusLabel.setText("Iniciando proceso...");
        statusLabel.setBackground(new Color(173, 216, 230));

        new ReservaWorker().execute();
    }

    private class ReservaWorker extends SwingWorker<Void, String> {

        private Color[] colores = {
            new Color(173, 216, 230),
            new Color(255, 200, 100),
            new Color(200, 150, 255),
            new Color(255, 255, 150)
        };

        @Override
        protected Void doInBackground() throws Exception {
            String[] steps = {
                "Reserva enviada, esperando respuesta del servidor...",
                "Procesando pago...",
                "Contactando con el hotel...",
                "Verificación final del sistema...",
            };

            for (int i = 0; i < steps.length; i++) {
                TimeUnit.SECONDS.sleep(1 + (int)(Math.random() * 2));
                publish(steps[i] + "|" + i);
            }
            
            TimeUnit.SECONDS.sleep(1);
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String data : chunks) {
                String[] parts = data.split("\\|");
                String message = parts[0];
                int colorIndex = Integer.parseInt(parts[1]);
                
                statusLabel.setText(message);
                statusLabel.setBackground(colores[colorIndex]);
                
                logArea.append(message + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
        }

        @Override
        protected void done() {
            try {
                get(); 
                
                String finalMessage = "Reserva confirmada. ¡Disfrute de su viaje! (200 OK)";
                statusLabel.setText(finalMessage);
                statusLabel.setBackground(new Color(144, 238, 144));
                logArea.append("\n" + finalMessage + "\n");
                
            } catch (Exception e) {
                String errorMessage = "ERROR: La reserva ha fallado. Motivo: " + e.getMessage();
                statusLabel.setText(errorMessage);
                statusLabel.setBackground(new Color(255, 160, 160));
                logArea.append("\n" + errorMessage + "\n");
            } finally {
                // Inicia un Timer para esperar 3 segundos (3000ms) sin bloquear el EDT
                Timer timer = new Timer(3000, new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        dispose();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaConfirmacionReserva().setVisible(true);
        });
    }
}