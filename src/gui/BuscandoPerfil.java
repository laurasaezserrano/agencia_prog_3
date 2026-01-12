package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class BuscandoPerfil extends JDialog{
	private static final long serialVersionUID = 1L;
	private JLabel labeltexto;
	private Timer animacion;
	private int puntos = 0;
	
	public BuscandoPerfil(Frame owner) {
		super(owner, "Cargando perfil...", true);
		
		labeltexto = new JLabel("Cargarndo tu perfil...", SwingConstants.CENTER);
		labeltexto.setFont(new Font("Arial", Font.BOLD, 18));
		labeltexto.setForeground(Color.WHITE);
		
		JProgressBar spinner = new JProgressBar();
		spinner.setIndeterminate(true); 
		spinner.setBorderPainted(false);
		spinner.setBackground(new Color(40, 120, 160));
		spinner.setForeground(Color.WHITE);
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(50, 150, 200));
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.add(labeltexto, BorderLayout.CENTER);
		panel.add(spinner, BorderLayout.SOUTH);
		
		setContentPane(panel);
		setSize(400, 150);
		setLocationRelativeTo(owner);
		setResizable(false);
		iniciaranimacion();
	}

	 private void iniciaranimacion() {
	        animacion = new Timer(450, e -> {
	            puntos = (puntos + 1) % 4;
	            String dots = ".".repeat(puntos);
	            labeltexto.setText("Cargando tu perfil" + dots);
	        });
	        animacion.start();
	    }
	
	public void actualizarTexto(String texto) {
        labeltexto.setText(texto);
    }

    public void cerrar() {
    	if (animacion != null) animacion.stop();
        dispose();
    }
}
