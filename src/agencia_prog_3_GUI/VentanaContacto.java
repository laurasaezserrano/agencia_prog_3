package agencia_prog_3_GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaContacto extends JFrame {

    public static void main(String[] args) {
        
        // Información de la Agencia formateada con saltos de línea (\n)
        String infoAgencia = 
        		
        	//IAG(Gemini) - Texto Generado por IA (info)
        		
            "========================================\n" +
            "      AGENCIA PROG 3 - CONTACTO         \n" +
            "========================================\n\n" +
            "Dirección:          Calle Ficticia No. 10, Ciudad de Programación\n" +
            "Teléfono Principal: +34 555 123 456\n" +
            "Email de Soporte:   soporte@agenciaprog3.com\n" +
            "Email Comercial:    comercial@agenciaprog3.com\n\n" +
            "Horario de Atención: Lunes a Viernes, 9:00 - 18:00 (CET)\n";

        // Muestra la ventana de diálogo de información
        JOptionPane.showMessageDialog(
            null, // El componente padre
            infoAgencia, // El mensaje con el contenido de texto plano
            "Información de Contacto", // El título de la ventana
            JOptionPane.PLAIN_MESSAGE // Ícono de mensaje plano (sin el ícono 'i')
        );
    }
}