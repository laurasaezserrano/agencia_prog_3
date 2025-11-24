package es.deusto.ingenieria.prog3.checkin.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import es.deusto.ingenieria.prog3.checkin.domain.Seat;

public class AircraftTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Seat> seatsList;
	private Map<Integer, List<Seat>> seatsMap;	
	private List<String> header = Arrays.asList("", "A", "B", "C", "", "D", "E", "F");
	
	public AircraftTableModel(List<Seat> seats) {
		this.seatsList = seats;
		
		seatsMap = new HashMap<>();

		for (Seat seat : seats) {
			if (!seatsMap.containsKey(seat.getRow())) {
				seatsMap.put( seat.getRow(), new ArrayList<>() );
			}
			seatsMap.get(seat.getRow()).add(seat);		
		}
		// Con streams sería
		//	seats.forEach(seat -> {
		// 		seatsMap.putIfAbsent(seat.getRow(), new ArrayList<>());
		// 		seatsMap.get(seat.getRow()).add(seat);		
		//	});
	}

	public List<Seat> getSeats() {
		return this.seatsList;
	}

	@Override
	public int getRowCount() {
		if (this.seatsMap != null) {
			return seatsMap.keySet().size();
		} else {
			return 0;
		}
	}
	
	@Override
	public Object getValueAt(int row, int column) {	
		try {
			if (column==0) {
				return row+1;
			} else if (column == 4) {
				return "";
			} else if (column <= 3) {  // Columns A-C
				return seatsMap.get(row+1).get(column-1);
			} else {  // Columns D-F
				return seatsMap.get(row+1).get(column-2);
			}
		} catch (Exception ex) {
			System.out.println("Fila: " + row + " - Columna: " + column);
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		return header.get(column);
	}

	@Override
	public int getColumnCount() {
		return 8;
	}
}