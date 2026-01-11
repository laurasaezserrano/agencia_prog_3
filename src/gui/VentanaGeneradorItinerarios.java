package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import agencia_prog_3_recursividad.GeneradorItinerariosRecursivos;
import agencia_prog_3_recursividad.GenerarRutasConectadas;

public class VentanaGeneradorItinerarios extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private List<DatosVuelos> vuelosDisponibles;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JTextField campoPresupuesto;
    private JComboBox<Integer> comboNumVuelos;
    private JComboBox<String> comboTipoBusqueda;
    private JTextField campoOrigen;
    private JTextField campoDestino;
    private JLabel labelResultados;
    private JButton btnGenerar;
    private JButton btnLimpiar;
    
	public VentanaGeneradorItinerarios(List<DatosVuelos> vuelos) {
        this.vuelosDisponibles = vuelos;
        
        setTitle("Generador de Itinerarios - Búsqueda Recursiva");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(50, 150, 200));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel superior: controles de búsqueda
        JPanel panelControles = crearPanelControles();
        mainPanel.add(panelControles, BorderLayout.NORTH);
        
        // Panel central: tabla de resultados
        JPanel panelTabla = crearPanelTabla();
        mainPanel.add(panelTabla, BorderLayout.CENTER);
        
        // Panel inferior: botones de acción
        JPanel panelBotones = crearPanelBotones();
        mainPanel.add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar listeners de Enter DESPUÉS de crear todos los componentes
        configurarEnterKeyListeners();
    }
	
	
	private JPanel crearPanelControles() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(new Color(230, 240, 255));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            "Parámetros de Búsqueda",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14)
        ));
        
        // Panel superior con tipo de búsqueda
        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelTipo.setBackground(new Color(230, 240, 255));
        panelTipo.add(new JLabel("Tipo de búsqueda:"));
        comboTipoBusqueda = new JComboBox<>(new String[]{"Itinerarios por presupuesto", "Rutas conectadas"});
        comboTipoBusqueda.addActionListener(e -> actualizarCamposSegunTipo());
        panelTipo.add(comboTipoBusqueda);
        panel.add(panelTipo, BorderLayout.NORTH);
        
        // Panel central con parámetros
        JPanel panelParams = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelParams.setBackground(new Color(230, 240, 255));
        
        // Número de vuelos
        panelParams.add(new JLabel("Número de vuelos:"));
        comboNumVuelos = new JComboBox<>();
        for (int i = 1; i <= 5; i++) {
            comboNumVuelos.addItem(i);
        }
        comboNumVuelos.setSelectedItem(2);
        panelParams.add(comboNumVuelos);
        
        // Presupuesto máximo
        panelParams.add(new JLabel("Presupuesto máximo (€):"));
        campoPresupuesto = new JTextField("500", 8);
        panelParams.add(campoPresupuesto);
        
        // Origen (para rutas conectadas)
        panelParams.add(new JLabel("Ciudad origen:"));
        campoOrigen = new JTextField("Madrid", 10);
        campoOrigen.setEnabled(false);
        panelParams.add(campoOrigen);
        
        // Destino (para rutas conectadas)
        panelParams.add(new JLabel("Ciudad destino:"));
        campoDestino = new JTextField("Londres", 10);
        campoDestino.setEnabled(false);
        panelParams.add(campoDestino);
        
        panel.add(panelParams, BorderLayout.CENTER);
        
        return panel;
    }

	private void actualizarCamposSegunTipo() {
        String tipo = (String) comboTipoBusqueda.getSelectedItem();
        boolean esRutaConectada = tipo.equals("Rutas conectadas");
        
        campoOrigen.setEnabled(esRutaConectada);
        campoDestino.setEnabled(esRutaConectada);
        campoPresupuesto.setEnabled(!esRutaConectada);
    }

	
	private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(50, 150, 200));
        
        // Label de resultados
        labelResultados = new JLabel("Resultados: 0 itinerarios encontrados");
        labelResultados.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelResultados.setHorizontalAlignment(JLabel.CENTER);
        labelResultados.setForeground(Color.WHITE);
        panel.add(labelResultados, BorderLayout.NORTH);
        
        // Crear modelo de tabla
        String[] columnas = {"#", "Itinerario", "Vuelos", "Precio Total", "Detalles", "Ver"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo columna "Ver" es editable
            }
        };
        
        // Crear tabla
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setRowHeight(40);
        tablaResultados.setFillsViewportHeight(true);
        tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaResultados.setShowGrid(true);
        tablaResultados.setGridColor(new Color(230, 230, 230));
        
        // Configurar cabecera
        JTableHeader cabecera = tablaResultados.getTableHeader();
        cabecera.setReorderingAllowed(false);
        cabecera.setFont(cabecera.getFont().deriveFont(Font.BOLD, 12f));
        
        // Ajustar anchos de columna
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(50);  // #
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(200); // Itinerario
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(80);  // Vuelos
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(100); // Precio
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(400); // Detalles
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(80);  // Ver
        
        // Centrar columnas numéricas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tablaResultados.getColumnModel().getColumn(0).setCellRenderer(centrado);
        tablaResultados.getColumnModel().getColumn(2).setCellRenderer(centrado);
        tablaResultados.getColumnModel().getColumn(3).setCellRenderer(centrado);
        
        // Renderer para botón "Ver"
        tablaResultados.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Ver"));
        tablaResultados.getColumnModel().getColumn(5).setCellEditor(
            new ButtonEditorItinerario(tablaResultados, this::verDetalleItinerario)
        );
        
        JScrollPane scrollPane = new JScrollPane(
            tablaResultados,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setBorder(new LineBorder(Color.BLACK, 2));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

	
	private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(230, 240, 255));
        
        btnGenerar = new JButton("Generar Itinerarios");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerar.setBackground(new Color(0, 120, 215));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setOpaque(true);
        btnGenerar.setBorderPainted(false);
        btnGenerar.addActionListener(e -> generarItinerarios());
        panel.add(btnGenerar);
        
        btnLimpiar = new JButton("Limpiar Resultados");
        btnLimpiar.addActionListener(e -> limpiarTabla());
        panel.add(btnLimpiar);
        
        JButton btnVolver = new JButton("Atrás");
        btnVolver.addActionListener(e -> {
            VentanaVueloYHotel ventana = new VentanaVueloYHotel(); 
            ventana.setVisible(true);
            this.dispose(); 
        });
        panel.add(btnVolver);
        
        return panel;
    }
    
	
    private void configurarEnterKeyListeners() {
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnGenerar.doClick();
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
                	btnLimpiar.doClick();
                }
            }
        };
        
        campoPresupuesto.addKeyListener(enterListener);
        campoOrigen.addKeyListener(enterListener);
        campoDestino.addKeyListener(enterListener);
        comboNumVuelos.addKeyListener(enterListener);
        comboTipoBusqueda.addKeyListener(enterListener);
        tablaResultados.addKeyListener(enterListener);
        
        // Al frame principal para que funcione desde cualquier punto
        this.addKeyListener(enterListener);
        setFocusable(true);
    }
    
    private void generarItinerarios() {
        try {
            String tipo = (String) comboTipoBusqueda.getSelectedItem();
            int numVuelos = (Integer) comboNumVuelos.getSelectedItem();
            
            List<List<DatosVuelos>> resultados = new ArrayList<>();
            
            if (tipo.equals("Itinerarios por presupuesto")) {
                double presupuesto = Double.parseDouble(campoPresupuesto.getText().trim());
                
                if (presupuesto <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "El presupuesto debe ser mayor que 0", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Llamar al método recursivo
                GeneradorItinerariosRecursivos.generarItinerarios(
                    vuelosDisponibles, resultados, numVuelos, presupuesto
                );
                
            } else { // Rutas conectadas
                String origen = campoOrigen.getText().trim();
                String destino = campoDestino.getText().trim();
                
                if (origen.isEmpty() || destino.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Debe especificar origen y destino", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Llamar al método recursivo de rutas conectadas
                GenerarRutasConectadas.generarRutasConectadas(
                    vuelosDisponibles, resultados, numVuelos - 1, origen, destino
                );
            }
            
            // Mostrar resultados en la tabla
            mostrarResultados(resultados);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "El presupuesto debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarResultados(List<List<DatosVuelos>> itinerarios) {
        limpiarTabla();
        
        if (itinerarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron itinerarios que cumplan los criterios", 
                "Sin resultados", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int contador = 1;
        for (List<DatosVuelos> itinerario : itinerarios) {
            String resumen = obtenerResumenItinerario(itinerario);
            int numVuelos = itinerario.size();
            double precioTotal = calcularPrecioTotal(itinerario);
            String detalles = obtenerDetallesItinerario(itinerario);
            
            modeloTabla.addRow(new Object[]{
                contador++,
                resumen,
                numVuelos,
                String.format("%.2f €", precioTotal),
                detalles,
                "Ver"
            });
        }
        
        labelResultados.setText("Resultados: " + itinerarios.size() + " itinerarios encontrados");
    }
    
    private String obtenerResumenItinerario(List<DatosVuelos> itinerario) {
        if (itinerario.isEmpty()) return "";
        
        DatosVuelos primero = itinerario.get(0);
        DatosVuelos ultimo = itinerario.get(itinerario.size() - 1);
        
        return primero.getOrigen() + " → " + ultimo.getDestino();
    }
    
    private String obtenerDetallesItinerario(List<DatosVuelos> itinerario) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itinerario.size(); i++) {
            DatosVuelos v = itinerario.get(i);
            sb.append(v.getCodigo()).append(" (")
              .append(v.getOrigen()).append("→").append(v.getDestino())
              .append(", ").append(v.getPrecio()).append("€)");
            if (i < itinerario.size() - 1) {
                sb.append(" | ");
            }
        }
        return sb.toString();
    }
    
    private double calcularPrecioTotal(List<DatosVuelos> itinerario) {
        double total = 0;
        for (DatosVuelos v : itinerario) {
            total += v.getPrecio();
        }
        return total;
    }
    
    private void verDetalleItinerario(int fila) {
        if (fila < 0 || fila >= modeloTabla.getRowCount()) return;
        
        String resumen = (String) modeloTabla.getValueAt(fila, 1);
        String detalles = (String) modeloTabla.getValueAt(fila, 4);
        String precio = (String) modeloTabla.getValueAt(fila, 3);
        
        String mensaje = "ITINERARIO #" + (fila + 1) + "\n" +
                        "═══════════════════════════════\n\n" +
                        "Ruta: " + resumen + "\n\n" +
                        "Vuelos:\n" + detalles.replace(" | ", "\n") + "\n\n" +
                        "PRECIO TOTAL: " + precio;
        
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Detalle del Itinerario", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
        labelResultados.setText("Resultados: 0 itinerarios encontrados");
    }
    
    // Editor de botón para la columna "Ver"
    static class ButtonEditorItinerario extends VentanaVueloYHotel.ButtonEditor {
        private static final long serialVersionUID = 1L;
        private final JTable tabla;
        private final java.util.function.Consumer<Integer> onClick;
        
        ButtonEditorItinerario(JTable tabla, java.util.function.Consumer<Integer> onClick) {
            super(new ArrayList<>(), tabla, null);
            this.tabla = tabla;
            this.onClick = onClick;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = tabla.getEditingRow();
            if (fila >= 0) {
                onClick.accept(fila);
            }
            fireEditingStopped();
        }
    }
    
    // Método main para pruebas
    public static void main(String[] args) {
        // Crear vuelos de ejemplo
        List<DatosVuelos> vuelos = new ArrayList<>();
        vuelos.add(new DatosVuelos("IB001", "Iberia", "Madrid", 120, "París", "10/01/2025", "10:00", 150, 100f, "Vuelo a París"));
        vuelos.add(new DatosVuelos("AF002", "Air France", "París", 90, "Londres", "10/01/2025", "14:00", 180, 80f, "Vuelo a Londres"));
        vuelos.add(new DatosVuelos("BA003", "British Airways", "Madrid", 150, "Londres", "10/01/2025", "11:00", 200, 120f, "Vuelo directo"));
        vuelos.add(new DatosVuelos("LH004", "Lufthansa", "Madrid", 180, "Berlín", "10/01/2025", "09:00", 170, 150f, "Vuelo a Berlín"));
        vuelos.add(new DatosVuelos("KL005", "KLM", "Madrid", 135, "Ámsterdam", "10/01/2025", "08:30", 160, 95f, "Vuelo a Ámsterdam"));
        vuelos.add(new DatosVuelos("LH006", "Lufthansa", "Berlín", 120, "Londres", "10/01/2025", "13:00", 140, 110f, "Berlín-Londres"));
        
        VentanaGeneradorItinerarios ventana = new VentanaGeneradorItinerarios(vuelos);
        ventana.setVisible(true);
    }
}

// Renderer de botón (reutilizado de tu código)
class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
    private static final long serialVersionUID = 1L;
    
    public ButtonRenderer(String text) {
        setText(text);
        setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
        setText(v == null ? getText() : v.toString());
        setForeground(t.getForeground());
        setBackground(s ? t.getSelectionBackground() : javax.swing.UIManager.getColor("Button.background"));
        return this;
    }
}