package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import db.GestorBD;
import domain.Reserva;


public class VentanaReservas extends JFrame {

	private static final long serialVersionUID = 1L;
    JPanel centerPanel = new JPanel();
    private String usuarioLogueado;
    
    private DefaultTableModel modeloTablaHoteles;
    private DefaultTableModel modeloTablaExcursiones ;
    private TableRowSorter<DefaultTableModel> sorterHoteles;
    private TableRowSorter<DefaultTableModel> sorterExcursiones;
    private JTabbedPane tabbedPane;
    
    private JTextField txtFiltroCiudad;
    
    private JTable tablaHoteles;
    private JTable tablaExcursiones;
    
    private GestorBD gestorBD;

	
	public VentanaReservas() {
		this.gestorBD = new GestorBD();
		gestorBD.crearBBDD();
		gestorBD.initilizeFromCSV();
		
		setTitle("Gestión de Reservas");
		setSize(900, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JLabel titleLabel = new JLabel("Mis Reservas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
 
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        
        JButton botonInicio = new JButton("Atras"); //luego cambiarlo a un icono
        
        panelSuperior.add(botonInicio, BorderLayout.WEST); // Botón a la izquierda
        panelSuperior.add(titleLabel, BorderLayout.CENTER); // Título en el centro

        
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Filtrar por Ciudad:"));
        txtFiltroCiudad = new JTextField(20); 
        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnLimpiarFiltro = new JButton("Limpiar Filtro");
        
        panelFiltro.add(txtFiltroCiudad);
        panelFiltro.add(btnFiltrar);
        panelFiltro.add(btnLimpiarFiltro);
        panelSuperior.add(panelFiltro, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane();
        
        try {
           this.usuarioLogueado = Ventana1Login.userField.getText();
       } catch(Exception e) {
           System.err.println("ADVERTENCIA: No se pudo leer 'Ventana1Login.userField'. Usando 'Test User'.");
           this.usuarioLogueado = "Test User";
       }
        
        
		botonInicio.addActionListener(new ActionListener() {
		    
			@Override
		    public void actionPerformed(ActionEvent e) {
		        dispose(); 
		        VentanaInicio vInicio = new VentanaInicio();
		        vInicio.setVisible(true);
		    }
		});
        
        String[] columnasHoteles  = {
            "Ciudad", "Hotel", "Email", "Habitación", "Adultos", 
            "Niños", "Salida", "Regreso", "Precio (€)", "Cancelar"
        };
        
        
        modeloTablaHoteles = new DefaultTableModel(columnasHoteles , 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna "Cancelar" (índice 9) es editable (es un botón)
                return column == 9; 
            }
        };
        
        tablaHoteles = new JTable(modeloTablaHoteles);
        
        sorterHoteles = new TableRowSorter<>(modeloTablaHoteles);
        tablaHoteles.setRowSorter(sorterHoteles);
        // Configuramos la tabla de hoteles (incluyendo el botón)
        configurarTabla(tablaHoteles, modeloTablaHoteles);
        JScrollPane scrollHoteles = new JScrollPane(tablaHoteles);
        tabbedPane.addTab("Hoteles y Alojamiento", scrollHoteles);
        
        String[] columnasExcursiones = {
                "Destino", "Excursión", "Email", "Tipo", "Adultos", 
                "Niños", "Fecha Inicio", "Fecha Fin", "Precio (€)", "Cancelar"
            };
        
         modeloTablaExcursiones = new DefaultTableModel(columnasExcursiones, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna "Cancelar" (índice 9) es editable
                return column == 9;
            }
        };
        
        tablaExcursiones = new JTable(modeloTablaExcursiones);
        sorterExcursiones = new TableRowSorter<>(modeloTablaExcursiones);
        tablaExcursiones.setRowSorter(sorterExcursiones);
        // Configuramos la tabla de excursiones (incluyendo el botón)
        configurarTabla(tablaExcursiones, modeloTablaExcursiones);
        JScrollPane scrollExcursiones = new JScrollPane(tablaExcursiones);
        tabbedPane.addTab("Excursiones", scrollExcursiones);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        txtFiltroCiudad.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { 
            	aplicarbusqueda();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
            	aplicarbusqueda(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
            	aplicarbusqueda(); 
            }
        });
 
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = txtFiltroCiudad.getText();
                RowFilter<DefaultTableModel, Object> filtro = null;
                if (texto.trim().length() > 0) {
                    try {
                        filtro = RowFilter.regexFilter("(?i)" + texto, 0);
                    } catch (java.util.regex.PatternSyntaxException ex) {
                        System.err.println("Error en el filtro regex");
                    }
                }
                sorterHoteles.setRowFilter(filtro);
                sorterExcursiones.setRowFilter(filtro);
            }
        });
		
        btnLimpiarFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFiltroCiudad.setText("");
                sorterHoteles.setRowFilter(null);
                sorterExcursiones.setRowFilter(null);
            }
        });
        
		cargarReservasDesdeBD(usuarioLogueado);
		cerrarventana(txtFiltroCiudad, tablaHoteles, tablaExcursiones, tabbedPane, btnFiltrar, btnLimpiarFiltro);
	}
	
			
	
	
	private void aplicarbusqueda() {
    	String texto = txtFiltroCiudad.getText();
        RowFilter<DefaultTableModel, Object> rf = null;
        try {
            // Filtra por la columna 0 (Ciudad/Destino)
            rf = RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorterHoteles.setRowFilter(rf);
        sorterExcursiones.setRowFilter(rf);
        tablaHoteles.repaint();
        tablaExcursiones.repaint();
    };

	
	static class ButtonRenderer extends JButton implements TableCellRenderer {
        private static final long serialVersionUID = 1L;

        public ButtonRenderer(String text) {
            setText(text);
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(new Color(200, 50, 50)); // Color rojo
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private static final long serialVersionUID = 1L;
        private final JButton button;
        private final JTable table;
        private final java.util.function.Consumer<JTable> onClick; // Acción a realizar

        public ButtonEditor(JTable table, java.util.function.Consumer<JTable> onClick) {
            this.table = table;
            this.onClick = onClick;
            this.button = new JButton("Cancelar");
            this.button.setOpaque(true);
            this.button.setForeground(Color.WHITE);
            this.button.setBackground(new Color(220, 60, 60)); // Rojo un poco más brillante al editar
            this.button.addActionListener(this);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        public Object getCellEditorValue() {
            return "Cancelar";
        }

        public void actionPerformed(ActionEvent e) {
            // Llama a la acción (el método 'cancelarReserva')
            onClick.accept(table);
            // Detiene la edición de la celda
            fireEditingStopped();
        }
    }
	
	private void cerrarventana(JTextField txtFiltroCiudad, JTable tablaHoteles, JTable tablaExcursiones, JTabbedPane tabbedPane, 
			JButton btnFiltrar, JButton btnLimpiarFiltro) {
		KeyAdapter listener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X) {
					dispose();
				}
			}
		};
		this.addKeyListener(listener);
		setFocusable(true);
		txtFiltroCiudad.addKeyListener(listener);
	    tablaHoteles.addKeyListener(listener);
	    tablaExcursiones.addKeyListener(listener);
	    tabbedPane.addKeyListener(listener);
	    btnFiltrar.addKeyListener(listener);
	    btnLimpiarFiltro.addKeyListener(listener);
	}
    
    
	public static void main(String[] args) {
		VentanaReservas vReservas = new VentanaReservas();
		vReservas.setVisible(true);
	}
	
}