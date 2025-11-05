package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VentanaContacto extends JFrame {

	private static final long serialVersionUID = 1L;
	//Referencia a la ventana que estaba abierta antes
	private JFrame ventanaMadre;

	public VentanaContacto(JFrame padre) {
        super("Información de Contacto");
        this.ventanaMadre = padre;
        
        
        setSize(500, 300);
        setLocationRelativeTo(padre); // Centrar con respecto a la ventana anterior
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Solo cierra esta ventana, no toda la aplicación
        
   
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        Color amarilloPastelRGB = new Color(255, 255, 179);
        areaTexto.setBackground(amarilloPastelRGB);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        String infoAgencia =
                "            ========================================\n" +
                "                   AGENCIA PROG 3 - CONTACTO         \n" +
                "            ========================================\n\n" +
                "Dirección:          Calle Ficticia No. 10, Ciudad de Programación\n" +
                "Teléfono Principal: +34 555 123 456\n" +
                "Email de Soporte:   soporte@agenciaprog3.com\n" +
                "Email Comercial:    comercial@agenciaprog3.com\n\n" +
                "Horario de Atención: Lunes a Viernes, 9:00 - 18:00 (CET)\n";
        
        areaTexto.setText(infoAgencia);
        add(areaTexto, BorderLayout.CENTER);

        //Botón CERRAR
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botonCerrar = new JButton("Cerrar");
        
        botonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (ventanaMadre != null) {
                    ventanaMadre.setVisible(true);
                }
            }
        });
        
        panelSur.add(botonCerrar);
        add(panelSur, BorderLayout.SOUTH);
    }
}