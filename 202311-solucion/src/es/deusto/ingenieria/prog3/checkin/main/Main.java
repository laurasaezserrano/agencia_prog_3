package es.deusto.ingenieria.prog3.checkin.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import es.deusto.ingenieria.prog3.checkin.domain.Aircraft;
import es.deusto.ingenieria.prog3.checkin.domain.Seat;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.Letter;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;
import es.deusto.ingenieria.prog3.checkin.gui.JFrameCheckIn;

public class Main {

	//Default number of rows of a Plane
	private static int NUM_ROWS = 38;
	
	public static void main(String[] args) {		
		SwingUtilities.invokeLater(() -> new JFrameCheckIn(initPlane()));
	}
	
	public static Aircraft initPlane() {
		List<Seat> seats = new ArrayList<>();
		
		boolean occupied;
		SeatClass seatClass;
		
		for (int row = 1; row <= NUM_ROWS; row++) {
			for (Letter letter : Letter.values()) {
				//Por defecto los asientos etán libre y son de clase ECONOMY
				occupied = false;
				seatClass = SeatClass.ECONOMY;
				
				//FIRST_CLASS
				if (row <= 7) {
					seatClass = SeatClass.FIRST_CLASS;
					//En FIRST_CLASS los asientos B y E están reservados por defecto
					if (letter.equals(Letter.B) || letter.equals(Letter.E)) {
						occupied = true;
					}
				//Las filas 8, 9, 13 y 14 se reservan para la tripulación 
				} else if (row == 8 || row == 9 || row == 13 || row == 14) {
					occupied = true;
					seatClass = SeatClass.FIRST_CLASS;
				//Las filas 10 y 26 son las salidas de emergencia
				} else if (row == 10 || row == 26) {
					seatClass = SeatClass.EMERGENCY;
				}
				
				seats.add(new Seat(row, letter, occupied, seatClass));
			}			
		}		
				
		return new Aircraft("A321", "Airbus A321-100/200", seats);
	}
}
