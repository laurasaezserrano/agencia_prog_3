package db;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import domain.Hotel;
import domain.Reserva;
import domain.User;
import domain.Vuelo;
import gui.Ventana1Login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaGestionDB extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> cmbTablas;
    private JTable tablaDatos;
    private DefaultTableModel modeloTabla;
    private JButton btnInsertar, btnActualizar, btnEliminar;
    private JLabel lblEstadoDB;

    private GestorBD gestorBD;

    public VentanaGestionDB() {
        super("Gestión de Base de Datos - Agencia de Viajes");
        
        gestorBD = new GestorBD();
        gestorBD.crearBBDD();
        gestorBD.initilizeFromCSV(); 

        inicializarComponentes();
        configurarLayout();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        
        // Carga inicial
        cmbTablas.setSelectedItem("Reserva");
        manejarCargarDatos(null);
    }

    private void inicializarComponentes() {
        cmbTablas = new JComboBox<>(new String[] {
            "Reserva", "Vuelo", "Hotel", "User"
        });
        
        cmbTablas.addActionListener(this::manejarCargarDatos);
        
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaDatos = new JTable(modeloTabla);
        tablaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaDatos.setRowHeight(25);
        
        JTableHeader cabecera = tablaDatos.getTableHeader();
        cabecera.setReorderingAllowed(false);
        cabecera.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnInsertar = new JButton("Insertar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        lblEstadoDB = new JLabel("✓ Gestor BD Activo");
        
        btnInsertar.addActionListener(this::manejarInsertar);
        btnEliminar.addActionListener(this::manejarEliminar);
        btnActualizar.addActionListener(this::manejarActualizar);
    }

    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.add(new JLabel("Seleccionar Tabla:"));
        panelNorte.add(cmbTablas);
        panelNorte.add(lblEstadoDB);
        add(panelNorte, BorderLayout.NORTH);
        
        add(new JScrollPane(tablaDatos), BorderLayout.CENTER);
        
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton botonInicio = new JButton("Volver al Login");
        
        panelSur.add(botonInicio);
        panelSur.add(btnInsertar);
        panelSur.add(btnActualizar);
        panelSur.add(btnEliminar);
        add(panelSur, BorderLayout.SOUTH);
        
        botonInicio.addActionListener(e -> {
            dispose(); 
            new Ventana1Login().setVisible(true);
        });
    }

    private void manejarCargarDatos(ActionEvent e) {
        String tabla = (String) cmbTablas.getSelectedItem();
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        
        switch(tabla) {
            case "Reserva":
                cargarDatosReservas();
                break;
            case "Vuelo":
                cargarDatosVuelos();
                break;
            case "Hotel":
                cargarDatosHoteles();
                break;
            case "User":
                cargarDatosUsuarios();
                break;
        }
        centrarColumnas();
    }
    
    private void cargarDatosVuelos() {
        String[] columnas = {"ORIGEN", "DESTINO", "F_SALIDA", "F_REGRESO", "AEROLINEA", 
                            "P_ECONOMY", "P_BUSINESS", "PLAZAS"};
        modeloTabla.setColumnIdentifiers(columnas);
        
        List<Vuelo> lista = gestorBD.getVuelos();
        for (Vuelo v : lista) {
            modeloTabla.addRow(new Object[]{
                v.getOrigen(), v.getDestino(), v.getFechaSalida(), v.getFechaRegreso(),
                v.getAerolinea(), v.getPrecioEconomy(), v.getPrecioBusiness(), 
                v.getPlazasDisponibles()
            });
        }
        lblEstadoDB.setText("✓ " + lista.size() + " vuelos cargados");
    }
    
    private void cargarDatosHoteles() {
        String[] columnas = {"NOMBRE", "CIUDAD", "PAÍS", "ESTRELLAS", "CAPACIDAD", 
                            "PRECIO/NOCHE", "MONEDA"};
        modeloTabla.setColumnIdentifiers(columnas);
        
        List<Hotel> lista = gestorBD.getHoteles();
        for (Hotel h : lista) {
            modeloTabla.addRow(new Object[]{
                h.getNombre(), h.getCiudad(), h.getPais(), h.getEstrellas(),
                h.getCapacidad(), h.getPrecioNoche(), h.getMoneda()
            });
        }
        lblEstadoDB.setText("✓ " + lista.size() + " hoteles cargados");
    }
    
    private void cargarDatosUsuarios() {
        String[] columnas = {"USUARIO", "PASSWORD", "NOMBRE", "DNI", "EMAIL", 
                            "TELÉFONO", "DIRECCIÓN", "IDIOMA", "MONEDA"};
        modeloTabla.setColumnIdentifiers(columnas);
        
        List<User> lista = gestorBD.getUsuarios();
        for (User u : lista) {
            modeloTabla.addRow(new Object[]{
                u.getUsuario(), u.getPassword(), u.getNombre(), u.getDni(),
                u.getEmail(), u.getTelefono(), u.getDireccion(), 
                u.getIdioma(), u.getMoneda()
            });
        }
        lblEstadoDB.setText("✓ " + lista.size() + " usuarios cargados");
    }

    private void cargarDatosReservas() {
        String[] columnas = {"USUARIO", "CIUDAD", "HOTEL", "EMAIL", "HABITACIÓN", "ADULTOS", "NIÑOS", "ENTRADA", "SALIDA", "PRECIO"};
        modeloTabla.setColumnIdentifiers(columnas);

        List<Reserva> lista = gestorBD.getListaTodasLasReservas();
        for (Reserva r : lista) {
            Object[] fila = {
                r.getUsuario(), r.getCiudad(), r.getNombreHotel(), r.getEmail(),
                r.getTipoHabitacion(), r.getNumAdultos(), r.getNumNiños(),
                r.getFechaEntrada(), r.getFechaSalida(), r.getPrecioNoche()
            };
            modeloTabla.addRow(fila);
        }
        lblEstadoDB.setText("✓ " + lista.size() + " reservas cargadas");
    }

    private void manejarInsertar(ActionEvent event) {
        String tabla = (String) cmbTablas.getSelectedItem();
        
        if ("Reserva".equals(tabla)) {
            mostrarDialogoInsertarReserva();
        } else if ("User".equals(tabla)) {
            mostrarDialogoInsertarUsuario();
        }
    }

    private void mostrarDialogoInsertarReserva() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField[] campos = {
            new JTextField(), new JTextField(), new JTextField(), new JTextField(),
            new JTextField(), new JTextField("2"), new JTextField("0"),
            new JTextField("2026-01-01"), new JTextField("2026-01-05"), new JTextField("80.0")
        };
        String[] etiquetas = {"Usuario:", "Ciudad:", "Hotel:", "Email:", "Habitación:", "Adultos:", "Niños:", "F. Entrada:", "F. Salida:", "Precio:"};
        
        for (int i = 0; i < etiquetas.length; i++) {
            panel.add(new JLabel(etiquetas[i]));
            panel.add(campos[i]);
        }

        if (JOptionPane.showConfirmDialog(this, panel, "Nueva Reserva", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Reserva r = new Reserva();
                r.setUsuario(campos[0].getText());
                r.setCiudad(campos[1].getText());
                r.setNombreHotel(campos[2].getText());
                r.setEmail(campos[3].getText());
                r.setTipoHabitacion(campos[4].getText());
                r.setNumAdultos(Integer.parseInt(campos[5].getText()));
                r.setNumNiños(Integer.parseInt(campos[6].getText()));
                r.setFechaEntrada(java.sql.Date.valueOf(campos[7].getText()));
                r.setFechaSalida(java.sql.Date.valueOf(campos[8].getText()));
                r.setPrecioNoche(Double.parseDouble(campos[9].getText()));

                gestorBD.insertarReserva(r);
                manejarCargarDatos(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void mostrarDialogoInsertarUsuario() {
        JTextField txtUser = new JTextField();
        JTextField txtPass = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Usuario:")); panel.add(txtUser);
        panel.add(new JLabel("Password:")); panel.add(txtPass);

        if (JOptionPane.showConfirmDialog(this, panel, "Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            User u = new User();
            u.setUsuario(txtUser.getText());
            u.setPassword(txtPass.getText());
            gestorBD.insertarUsuario(u);
            manejarCargarDatos(null);
        }
    }

    private void manejarActualizar(ActionEvent e) {
        manejarCargarDatos(null);
        lblEstadoDB.setText("Datos actualizados");
    }

    private void manejarEliminar(ActionEvent event) {
        int fila = tablaDatos.getSelectedRow();
        if (fila < 0) return;

        String tabla = (String) cmbTablas.getSelectedItem();
        
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar registro?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if ("Reserva".equals(tabla)) {
                // Obtenemos los campos que forman la Primary Key 
                String user = modeloTabla.getValueAt(fila, 0).toString();
                String hotel = modeloTabla.getValueAt(fila, 2).toString();
                String ciudad = modeloTabla.getValueAt(fila, 1).toString();
                Object oEntrada = modeloTabla.getValueAt(fila, 7);
                Object oSalida  = modeloTabla.getValueAt(fila, 8);
                /**
                 * AYUDA IAG 
                 */
                java.sql.Date fEntrada = (oEntrada instanceof java.sql.Date)
                        ? (java.sql.Date) oEntrada
                        : java.sql.Date.valueOf(oEntrada.toString().substring(0, 10));
                java.sql.Date fSalida = (oSalida instanceof java.sql.Date)
                        ? (java.sql.Date) oSalida
                        : java.sql.Date.valueOf(oSalida.toString().substring(0, 10));
                /**
                 * FIN DE AYUDA IAG
                 */

                
                if (gestorBD.eliminarReserva(user, hotel, ciudad, fEntrada, fSalida)) {
                    manejarCargarDatos(null);
                }
            } else if ("User".equals(tabla)) {
                String user = modeloTabla.getValueAt(fila, 0).toString();
                if (gestorBD.eliminarUser(user)) manejarCargarDatos(null);
            } else if("Vuelo".equals(tabla)) {
                String origen = modeloTabla.getValueAt(fila, 0).toString();
                String destino = modeloTabla.getValueAt(fila, 1).toString();
                Object oFSalida = modeloTabla.getValueAt(fila, 2);
                Object oFRegreso = modeloTabla.getValueAt(fila, 3);

                java.sql.Date fSalida = (oFSalida instanceof java.sql.Date)
                        ? (java.sql.Date) oFSalida
                        : java.sql.Date.valueOf(oFSalida.toString().substring(0, 10));

                java.sql.Date fRegreso = (oFRegreso instanceof java.sql.Date)
                        ? (java.sql.Date) oFRegreso
                        : java.sql.Date.valueOf(oFRegreso.toString().substring(0, 10));

                String aerolinea = modeloTabla.getValueAt(fila, 4).toString();

                if (gestorBD.eliminarVuelo(origen, destino, fSalida, fRegreso, aerolinea)) {
                    manejarCargarDatos(null);
                }

            } else if ("Hotel".equals(tabla)) {
                String nombre = modeloTabla.getValueAt(fila, 0).toString();
                String ciudad = modeloTabla.getValueAt(fila, 1).toString();

                if (gestorBD.eliminarHotel(nombre, ciudad)) {
                    manejarCargarDatos(null);
                }
            }
        }
    }

    private void centrarColumnas() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaDatos.getColumnCount(); i++) {
            tablaDatos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaGestionDB().setVisible(true));
    }
}