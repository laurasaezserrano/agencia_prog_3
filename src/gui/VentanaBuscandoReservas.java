package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaBuscandoReservas extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lblTexto;
    private static final int ANIMACION_INTERVALO_MS = 300;
    private JLabel titulolabel;
    private JLabel mensajecentral;
    private JLabel estadolabel;
    private JProgressBar progressbar;
    private volatile boolean animar = false;
    private Thread anim;
    
    // Texto base del estado (sin puntos)
    private volatile String estadoBase = "Buscando reservas";


    public VentanaBuscandoReservas(Frame owner) {
        super(owner, "Buscando reservas...", true);
        JPanel contenido = new JPanel(new BorderLayout(12, 12));
        contenido.setBackground(new Color(240, 240, 240)); //color del fondo
        contenido.setBorder(BorderFactory.createLineBorder(new Color(51,102,153), 4));
        
        //cabecera
        titulolabel = new JLabel("Gestión de Reservas", SwingConstants.CENTER);
        titulolabel.setFont(new Font("Arial", Font.BOLD, 20));
        titulolabel.setForeground(new Color(51,102,153));//color principal
        titulolabel.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        contenido.add(titulolabel, BorderLayout.NORTH);

        //panelcentral
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 150, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setForeground(Color.WHITE);
        
        mensajecentral = new JLabel("Buscando reservas disponibles", SwingConstants.CENTER);
        mensajecentral.setFont(new Font("Arial", Font.BOLD, 18));
        mensajecentral.setForeground(new Color(51, 102, 153).darker());
        
        //progress bar
        progressbar = new JProgressBar();
        progressbar.setIndeterminate(true);
        progressbar.setBorderPainted(false);
        progressbar.setOpaque(true);
        
        panel.add(mensajecentral);
        panel.add(progressbar);
        
        contenido.add(panel, BorderLayout.CENTER);
        
        estadolabel = new JLabel(estadoBase, SwingConstants.CENTER);
        estadolabel.setFont(new Font("Arial", Font.PLAIN, 13));
        estadolabel.setForeground(new Color(51,102,153).darker());
        estadolabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 16, 16));
        contenido.add(estadolabel, BorderLayout.SOUTH);

        setContentPane(contenido);
        setSize(480, 260);
        setResizable(false);
        setLocationRelativeTo(owner);

        startAnimacion();
    }

    private void startAnimacion() {
		if (animar) return;
		animar = true;
		anim = new Thread(() -> {
            int dotCount = 0;
            while (animar) {
                final int currentDotCount = dotCount;
                SwingUtilities.invokeLater(() -> {
                    String dots = ".".repeat(currentDotCount % 4);
                    estadolabel.setText(estadoBase + dots);
                });
                dotCount++;
                try {
                    Thread.sleep(ANIMACION_INTERVALO_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "VentanaBuscandoReservas-AnimThread");

        anim.setDaemon(true);
        anim.start();
    }

	public void cambiarTexto(String texto) {
        if (texto == null) {
        	texto = "";
        }
        final String t = texto;
        SwingUtilities.invokeLater(() -> mensajecentral.setText(t));
    }
    
    public void cambiarEstado(String texto) {
        if (texto == null) {
        	texto = "";
        }
        estadoBase = texto;
        SwingUtilities.invokeLater(() -> estadolabel.setText(estadoBase));
    }

    public void cerrar() {
    	stopAnimacion();
    	SwingUtilities.invokeLater(this::dispose);
    }


	private void stopAnimacion() {
		animar = false;
		if (anim != null) {
			anim.interrupt();
		}
		
	}
}
