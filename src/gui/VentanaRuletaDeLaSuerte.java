package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaRuletaDeLaSuerte extends JDialog {
    private static final long serialVersionUID = 1L;
    private JLabel labelDado1;
    private JLabel labelDado2;
    private JLabel labelResultado;
    private JLabel labelDescuento;
    private JButton btnLanzar;
    private JButton btnSalir;
    private boolean enLanzamiento = false;

    public VentanaRuletaDeLaSuerte(Frame owner) {
        super(owner, "Juego de los Dados: ¡Lanza y Gana!", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Título
        JLabel tituloAccion = new JLabel("¡Lanzando los Dados!", SwingConstants.CENTER);
        tituloAccion.setFont(new Font("SansSerif", Font.BOLD, 20));
        tituloAccion.setForeground(new Color(50, 150, 200));
        panel.add(tituloAccion, BorderLayout.NORTH);

        // Panel central con explicación
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 240, 240));

        JLabel explicacion = new JLabel(
        	/**
        	 * IAG - Texto generado + formato
        	 * FUENTE: Claude
        	 */
            "<html><div style='text-align: center;'>" +
            "<b>Reglas del Juego:</b><br>" +
            "Se lanzarán dos dados simultáneamente.<br>" +
            "Gana <b style='color: green;'>20% de descuento</b> si la suma es <b>12</b>" +
            "</div></html>",
            
            SwingConstants.CENTER);
        explicacion.setFont(new Font("SansSerif", Font.PLAIN, 11));
        centerPanel.add(explicacion, BorderLayout.NORTH);

        // Panel de dados
        JPanel dadosPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        dadosPanel.setBackground(new Color(240, 240, 240));

        labelDado1 = new JLabel("Dado 1: ?", SwingConstants.CENTER);
        labelDado1.setFont(new Font("Monospaced", Font.BOLD, 40));
        labelDado1.setForeground(Color.RED);
        labelDado1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelDado1.setPreferredSize(new Dimension(100, 100));

        labelDado2 = new JLabel("Dado 2: ?", SwingConstants.CENTER);
        labelDado2.setFont(new Font("Monospaced", Font.BOLD, 40));
        labelDado2.setForeground(Color.BLUE);
        labelDado2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        labelDado2.setPreferredSize(new Dimension(100, 100));

        dadosPanel.add(labelDado1);
        dadosPanel.add(labelDado2);
        centerPanel.add(dadosPanel, BorderLayout.CENTER);

        // Resultado
        labelResultado = new JLabel("Suma: --", SwingConstants.CENTER);
        labelResultado.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelResultado.setForeground(new Color(100, 100, 100));
        centerPanel.add(labelResultado, BorderLayout.SOUTH);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Panel de descuento
        labelDescuento = new JLabel("Descuento: --", SwingConstants.CENTER);
        labelDescuento.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelDescuento.setForeground(new Color(0, 150, 0));
        labelDescuento.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        labelDescuento.setPreferredSize(new Dimension(0, 40));
        panel.add(labelDescuento, BorderLayout.SOUTH);

        // Panel de botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.setBackground(new Color(240, 240, 240));

        btnLanzar = new JButton("🎲 Lanzar Dados");
        btnLanzar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnLanzar.setBackground(new Color(50, 150, 200));
        btnLanzar.setForeground(Color.WHITE);
        btnLanzar.setFocusPainted(false);
        btnLanzar.addActionListener(e -> lanzarDados());
        botonesPanel.add(btnLanzar);

        btnSalir = new JButton("Cerrar");
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnSalir.setBackground(new Color(200, 50, 50));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> cerrarVentana());
        botonesPanel.add(btnSalir);

        this.add(panel, BorderLayout.CENTER);
        this.add(botonesPanel, BorderLayout.SOUTH);
    }

    private void lanzarDados() {
        if (enLanzamiento) {
            JOptionPane.showMessageDialog(this, "¡Espera a que terminen los dados!", "Juego en curso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        btnLanzar.setEnabled(false);
        enLanzamiento = true;
        labelResultado.setText("Suma: --");
        labelDescuento.setText("Descuento: --");

        // Crear hilos para cada dado
        HiloDado hiloD1 = new HiloDado(1, this);
        HiloDado hiloD2 = new HiloDado(2, this);

        hiloD1.start();
        hiloD2.start();

        // Hilo para esperar a que terminen ambos dados
        Thread validador = new Thread(() -> {
            try {
                hiloD1.join();
                hiloD2.join();
                int suma = hiloD1.getResultado() + hiloD2.getResultado();
                validarResultado(suma);
                enLanzamiento = false;
                btnLanzar.setEnabled(true);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        validador.start();
    }

    private void validarResultado(int suma) {
        SwingUtilities.invokeLater(() -> {
            labelResultado.setText("Suma Total: " + suma);
            String descuento = "";
            Color color = Color.BLACK;

            if (suma == 12) {
                descuento = "¡¡¡ GANASTE 20% DE DESCUENTO !!!";
                color = new Color(0, 150, 0);
                reproducirSonidoGanador();
            } else {
                descuento = "Lo sentimos, no hay descuento esta vez. ¡Intenta de nuevo!";
                color = new Color(150, 0, 0);
            }

            labelDescuento.setText(descuento);
            labelDescuento.setForeground(color);
        });
    }

    public void actualizarDado(int dadoNumero, int resultado) {
        SwingUtilities.invokeLater(() -> {
            if (dadoNumero == 1) {
                labelDado1.setText(String.valueOf(resultado));
            } else if (dadoNumero == 2) {
                labelDado2.setText(String.valueOf(resultado));
            }
        });
    }

    private void reproducirSonidoGanador() {
        // Bip simple usando Toolkit
        Toolkit.getDefaultToolkit().beep();
    }

    public void cerrarVentana() {
        this.dispose();
    }
}

// ============ CLASE DEL HILO DEL DADO ============
class HiloDado extends Thread {
    private int numero;
    private int resultado;
    private VentanaRuletaDeLaSuerte ventana;
    private static final int DURACION_LANZAMIENTO = 800; // milisegundos

    public HiloDado(int numero, VentanaRuletaDeLaSuerte ventana) {
        this.numero = numero;
        this.ventana = ventana;
        this.resultado = 0;
    }

    @Override
    public void run() {
        long tiempoInicio = System.currentTimeMillis();

        // Simular lanzamiento del dado con múltiples valores
        while (System.currentTimeMillis() - tiempoInicio < DURACION_LANZAMIENTO) {
            if (numero == 1) {
                // Dado 1: solo valores del 1 al 5
                resultado = (int) (Math.random() * 5) + 1;
            } else {
                // Dado 2: valores normales del 1 al 6
                resultado = (int) (Math.random() * 6) + 1;
            }
            ventana.actualizarDado(numero, resultado);
            try {
                Thread.sleep(50); // Actualizar cada 50ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Resultado final
        if (numero == 1) {
            resultado = (int) (Math.random() * 5) + 1;
        } else {
            resultado = (int) (Math.random() * 6) + 1;
        }
        ventana.actualizarDado(numero, resultado);
    }

    public int getResultado() {
        return resultado;
    }
}