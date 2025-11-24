package es.deusto.ingenieria.prog3.checkin.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import es.deusto.ingenieria.prog3.checkin.domain.Aircraft;
import es.deusto.ingenieria.prog3.checkin.domain.Seat;
import es.deusto.ingenieria.prog3.checkin.domain.SeatAllocator;

public class JFrameCheckIn extends JFrame {

	private static final long serialVersionUID = 1L;
	private Aircraft aircraft;	
	private JTable seatsTable;
	
	public JFrameCheckIn(Aircraft aircraft) {
		this.aircraft = aircraft;
		
		initTable();
		
		JScrollPane seatsScrollPane = new JScrollPane(this.seatsTable);
		seatsScrollPane.setBorder(new TitledBorder("Seats"));
		this.seatsTable.setFillsViewportHeight(true);

		//TAREA 3 - Evento de teclado
		//Al pulsar la combinación de teclas CTRL + C abrir el
		//cuadro de diálogo JDialogSeatAllocator para buscar y confirmar asientos
		KeyListener myKeyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
					//Se pasa como parámetro la tabla de asientos para repintarla
					new JDialogSeatAllocator(new SeatAllocator(aircraft), seatsTable);
				}
			}
		};

		this.seatsTable.addKeyListener(myKeyListener);
		
		this.setTitle("'" + aircraft.getName() + "' check-in window");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.getContentPane().setLayout(new BorderLayout());		
		this.getContentPane().add(seatsScrollPane, BorderLayout.CENTER);				

		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);		
	}
	
	private void initTable() {
		//TAREA 1 - Crear el modelo de datos e inicializarlo. 
		//Debes crear un modelo de datos para mostrar la información
		//de los asientos fila a fila. Para realizar este proceso debes
		//mirar la manera en que se crean y van añadiendo los asientos
		//a la lista de asientos del avión.		
		this.seatsTable = new JTable(new AircraftTableModel(this.aircraft.getSeats()));
				
		//TAREA 2 - Render personalizado para la tabla
		//Debes modificar la manera en que se dibuja la tabla
		//- En la cabecera debe aparecer la imagen de las letras de los asientos y color de fondo RGB(237, 237, 237).
		//- La primera y 4ª columna de la cabecera aparecen vaícas y con el color de fondo por defecto de la tabla.
		//- En la priemera columna aparece los números de fila con el color de fondo por defecto de la tabla.
		//- Las filas de FIRST_CLASS y EMERGENCY tienen colores de fondo personalizados RGB(245, 247, 220) y RGB(252, 191, 183)
		//  respectivamente.
		//- Cada asiento se renderiza con la imagen Occupied.png o Available.png según sea necesario.
		//- La columna 4ª, que representa el pasilla debe aparece con el color de fondo por defecto de la tabla.
		//- La altura por defecto de todas las filas de la tabla debe ser 32 píxeles.
		TableCellRenderer headerRenderer = (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> {
			JLabel label = new JLabel();
			
			if (!value.toString().isEmpty()) {
				label.setIcon(new ImageIcon("resources/images/" + value.toString() + ".png"));
			}
			
			if (column != 0 && column != 4) {
				label.setBackground(new Color(237, 237, 237));
			} else {
				label.setBackground(table.getBackground());
			}
			
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setPreferredSize(new Dimension(label.getWidth(), 36));
			label.setOpaque(true);			
			return label;
		};
		
		TableCellRenderer cellRenderer = (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> {
			JLabel label = new JLabel();
			
			if (value instanceof Seat) {
				Seat seat = (Seat) value;
				String occupied = seat.isOccupied() ? "occupied" : "available";
				label.setIcon(new ImageIcon("resources/images/" + occupied + ".png"));
				
				switch (seat.getSeatClass()) {
					case FIRST_CLASS:
						label.setBackground(new Color(245, 247, 220));
						break;
					case EMERGENCY:
						label.setBackground(new Color(252, 191, 183));
						break;
					default:
						label.setBackground(table.getBackground());
						break;
				}
			} else {
				label.setText(value.toString());
				label.setBackground(table.getBackground());
			}
						
			label.setHorizontalAlignment(JLabel.CENTER);			
			label.setOpaque(true);
			
			return label;
		};
		
		this.seatsTable.getTableHeader().setDefaultRenderer(headerRenderer);
		this.seatsTable.setDefaultRenderer(Object.class, cellRenderer);
		this.seatsTable.setRowHeight(32);
	}
}