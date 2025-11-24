package es.deusto.ingenieria.prog3.checkin.domain.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.prog3.checkin.domain.Aircraft;
import es.deusto.ingenieria.prog3.checkin.domain.Seat;
import es.deusto.ingenieria.prog3.checkin.domain.SeatAllocator;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.Letter;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;

public class TestInitRowMaps {

    public static void main(String[] args) {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, Letter.A, false, SeatClass.FIRST_CLASS));
        seats.add(new Seat(2, Letter.A, false, SeatClass.EMERGENCY));
        seats.add(new Seat(3, Letter.A, false, SeatClass.ECONOMY));
        
        seats.add(new Seat(1, Letter.B, false, SeatClass.FIRST_CLASS));
        seats.add(new Seat(2, Letter.B, false, SeatClass.EMERGENCY));
        seats.add(new Seat(3, Letter.B, false, SeatClass.ECONOMY));
        
        seats.add(new Seat(2, Letter.C, false, SeatClass.ECONOMY));
        seats.add(new Seat(3, Letter.C, false, SeatClass.ECONOMY));
        seats.add(new Seat(3, Letter.D, false, SeatClass.ECONOMY));

        Aircraft aircraft = new Aircraft("1111-1111", "Test Aircraft", seats);
        SeatAllocator seatAllocator = new SeatAllocator(aircraft);     
        
        try {
	        Map<Integer, List<Seat>> seatsByRow = seatAllocator.initRowsMap(seats);
	                
	        if (seatsByRow.size() != 3) {
	        	System.out.format("Se esperaban 3 filas. Se obtuvieron %d.\n", seatsByRow.size());
	        }
	        
	        if (seatsByRow.get(1).size() != 2) {
	        	System.out.format("ERROR. Se esperaban 2 asientos en la fila 1. Se obtuvieron %d.\n", seatsByRow.get(1).size());
	        }
	        
	        if (seatsByRow.get(2).size() != 3) {
	        	System.out.format("ERROR. Se esperaban 3 asientos en la fila 2. Se obtuvieron %d.\n", seatsByRow.get(2).size());
	        }
	        
	        if (seatsByRow.get(3).size() != 4) {
	        	System.out.format("ERROR. Se esperaban 4 asientos en la fila 3. Se obtuvieron %d.\n", seatsByRow.get(3).size());
	        }
	        
	        System.out.println("Si no ha habido mensajes de error, se ha pasado la prueba.");
        } catch (Exception ex) {
        	System.out.format("ERROR: %s.\n", ex.getMessage());
        }
    }
}
