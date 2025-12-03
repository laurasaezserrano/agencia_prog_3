package agencia_prog_3_thread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class VentanaRuletaDeLaSuerte extends JDialog {

    private static final long serialVersionUID = 1L;
    private JLabel labelDado1;
    private JLabel labelDado2;

    public VentanaRuletaDeLaSuerte(Frame owner) {
        super(owner, "Juego de los Dados: ¡Lanza y Gana!", true); 
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
        
        setSize(450, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10)); 
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel tituloAccion = new JLabel("¡Lanzando los Dados!", SwingConstants.CENTER);
        tituloAccion.setFont(new Font("SansSerif", Font.BOLD, 20));
        tituloAccion.setForeground(new Color(50, 150, 200));
        panel.add(tituloAccion, BorderLayout.NORTH);

        JLabel explicacion = new JLabel(
            "<html><div style='text-align: center;'>" +
            "Se lanzarán dos dados. Ganas el descuento si la **Suma Total es 13**." +
            "</div></html>", 
            SwingConstants.CENTER);
        explicacion.setFont(new Font("SansSerif", Font.ITALIC, 12));
        panel.add(explicacion, BorderLayout.CENTER);

        JPanel dadosPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        dadosPanel.setBackground(new Color(240, 240, 240));
        
        labelDado1 = new JLabel("Dado 1: ?", SwingConstants.CENTER);
        labelDado1.setFont(new Font("Monospaced", Font.BOLD, 30));
        labelDado1.setForeground(Color.RED);
        
        labelDado2 = new JLabel("Dado 2: ?", SwingConstants.CENTER);
        labelDado2.setFont(new Font("Monospaced", Font.BOLD, 30));
        labelDado2.setForeground(Color.BLUE);

        dadosPanel.add(labelDado1);
        dadosPanel.add(labelDado2);
        
        panel.add(dadosPanel, BorderLayout.SOUTH);
        
        this.add(panel);
    }
    
    public void actualizarDado(int dadoNumero, int resultado) {
        if (dadoNumero == 1) {
            labelDado1.setText("Dado 1: " + resultado);
        } else if (dadoNumero == 2) {
            labelDado2.setText("Dado 2: " + resultado);
        }
    }
    
    public void cerrarVentana() {
        this.dispose();
    }
}