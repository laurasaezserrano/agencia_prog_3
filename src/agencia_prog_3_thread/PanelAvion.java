package agencia_prog_3_thread;

import javax.swing.*;
import java.awt.*;

public class PanelAvion extends JPanel {

	private static final long serialVersionUID = 1L;
	private int xAvion = -120;
    private int yAvion = 50;
    private Timer timerVuelo;
    private Image avionImg;

    public PanelAvion() {
        ImageIcon icon = new ImageIcon("avion.png"); // ajusta ruta si hace falta
        avionImg = icon.getImage();
        setOpaque(false);
    }

    public void iniciarVuelo() {
        timerVuelo = new Timer(25, e -> {
            xAvion += 4;
            if (xAvion > getWidth()) {
                xAvion = -120;
            }
            repaint();
        });
        timerVuelo.start();
    }

    public void detenerVuelo() {
        if (timerVuelo != null) {
            timerVuelo.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (avionImg != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(255, 255, 255, 160));
            g2.fillRoundRect(xAvion - 10, yAvion - 10, 120, 60, 30, 30);

            g2.drawImage(avionImg, xAvion, yAvion, 90, 40, this);
            g2.dispose();
        }
    }
}
