package agencia_prog_3_thread;  

import javax.swing.*;
import java.awt.*
;

public class VentanaBuscandoPerfil extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblTexto;
    private Timer animacion;
    private int puntos = 0;

    public VentanaBuscandoPerfil(Frame owner) {
        super(owner, "Cargando perfil...", true);

        lblTexto = new JLabel("Cargando tu perfil...", SwingConstants.CENTER);
        lblTexto.setFont(new Font("Arial", Font.BOLD, 18));
        lblTexto.setForeground(Color.WHITE);

        JProgressBar spinner = new JProgressBar();
        spinner.setIndeterminate(true); //barra que se mueve 
        spinner.setBorderPainted(false); //oculta el borde de la tabla
        spinner.setBackground(new Color(40, 120, 160));
        spinner.setForeground(Color.WHITE);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 150, 200));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(lblTexto, BorderLayout.CENTER);
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
            lblTexto.setText("Cargando tu perfil" + dots);
        });
        animacion.start();
    }

    public void actualizarTexto(String texto) {
        lblTexto.setText(texto);
    }

    public void cerrar() {
    	if (animacion != null) animacion.stop();
        dispose();
    }
}
