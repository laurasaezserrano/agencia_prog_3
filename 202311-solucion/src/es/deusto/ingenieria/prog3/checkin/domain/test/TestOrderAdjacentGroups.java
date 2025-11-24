package es.deusto.ingenieria.prog3.checkin.domain.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.deusto.ingenieria.prog3.checkin.domain.Aircraft;
import es.deusto.ingenieria.prog3.checkin.domain.Seat;
import es.deusto.ingenieria.prog3.checkin.domain.SeatAllocator;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.Letter;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;

public class TestOrderAdjacentGroups {

	public static void main(String[] args) {		
		// tenemos los grupos de prueba en clase ECONOMY
		// [2A, 2B], [1A], [3A, 3B, 3C], [2B]
		List<List<Seat>> seatGroups = new ArrayList<>();
		seatGroups.add(Arrays.asList(
			new Seat(2, Letter.A, false, SeatClass.ECONOMY),
			new Seat(2, Letter.B, false, SeatClass.ECONOMY)
		));
		
		seatGroups.add(Arrays.asList(
			new Seat(1, Letter.A, false, SeatClass.ECONOMY)
		));
		
		seatGroups.add(Arrays.asList(
			new Seat(3, Letter.A, false, SeatClass.ECONOMY),
			new Seat(3, Letter.B, false, SeatClass.ECONOMY),
			new Seat(3, Letter.C, false, SeatClass.ECONOMY)
		));
		
		seatGroups.add(Arrays.asList(
			new Seat(2, Letter.B, false, SeatClass.ECONOMY)
		));
		
		// una vez ordenados se espera obtener 
		// [2B], [1A], [2A, 2B], [3A, 3B, 3C] 
		List<List<Seat>> expected = new ArrayList<>();
		expected.add(Arrays.asList(
			new Seat(2, Letter.B, false, SeatClass.ECONOMY)
		));
		
		expected.add(Arrays.asList(
			new Seat(1, Letter.A, false, SeatClass.ECONOMY)
		));
		
		expected.add(Arrays.asList(
			new Seat(2, Letter.A, false, SeatClass.ECONOMY),
			new Seat(2, Letter.B, false, SeatClass.ECONOMY)
		));
		
		
		expected.add(Arrays.asList(
			new Seat(3, Letter.A, false, SeatClass.ECONOMY),
			new Seat(3, Letter.B, false, SeatClass.ECONOMY),
			new Seat(3, Letter.C, false, SeatClass.ECONOMY)
		));
		
		Aircraft aircraft = new Aircraft("1111-1111", "Test Aircraft", new ArrayList<>());
        SeatAllocator seatAllocator = new SeatAllocator(aircraft);
		
        try {
        	seatAllocator.orderAdjacentGroups(seatGroups);
		
        	if (!expected.equals(seatGroups)) {
        		System.out.println("ERROR. La lista ordenada no concuerda con la esperada.");
        		System.out.format("Esperado: %s.\n", expected);
        		System.out.format("Obtenido: %s.\n", seatGroups);
        	}
		
        	System.out.println("Si no ha habido mensajes de error, se ha pasado la prueba.");
        } catch (Exception ex) {
        	System.out.format("ERROR: %s.\n", ex.getMessage());
        }        
	}
}
