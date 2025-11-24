package es.deusto.ingenieria.prog3.checkin.domain.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.prog3.checkin.domain.Aircraft;
import es.deusto.ingenieria.prog3.checkin.domain.Seat;
import es.deusto.ingenieria.prog3.checkin.domain.SeatAllocator;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.Letter;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;

public class TestFindAdjacentGroups {

	private static Map<Integer, List<Seat>> seatsMap;
	private static SeatAllocator seatAllocator;
	
	public static void main(String[] args) {
		// Datos de prueba para ejemplo: 5 filas de 3 asientos cada una (A, B, C)
		// L = Libre, O = Ocupado
		// 		A	B	C
		//	1	L   L   O (First Class)
		//  2   L	O	L (First Class)
		//  3	O 	O	L (Emergency)
		//	4	L	L	L (Economy)
		//	5	L	L	L (Economy)
		seatsMap = new HashMap<>();
		seatsMap.put(1, new ArrayList<>());
		seatsMap.get(1).add(new Seat(1, Letter.A, false, SeatClass.FIRST_CLASS));
		seatsMap.get(1).add(new Seat(1, Letter.B, false, SeatClass.FIRST_CLASS));
		seatsMap.get(1).add(new Seat(1, Letter.C, true, SeatClass.FIRST_CLASS));
		
		seatsMap.put(2, new ArrayList<>());
		seatsMap.get(2).add(new Seat(2, Letter.A, false, SeatClass.FIRST_CLASS));
		seatsMap.get(2).add(new Seat(2, Letter.B, true, SeatClass.FIRST_CLASS));
		seatsMap.get(2).add(new Seat(2, Letter.C, false, SeatClass.FIRST_CLASS));
		
		seatsMap.put(3, new ArrayList<>());
		seatsMap.get(3).add(new Seat(3, Letter.A, true, SeatClass.EMERGENCY));
		seatsMap.get(3).add(new Seat(3, Letter.B, true, SeatClass.EMERGENCY));
		seatsMap.get(3).add(new Seat(3, Letter.C, false, SeatClass.EMERGENCY));
		
		seatsMap.put(4, new ArrayList<>());
		seatsMap.get(4).add(new Seat(4, Letter.A, false, SeatClass.ECONOMY));
		seatsMap.get(4).add(new Seat(4, Letter.B, false, SeatClass.ECONOMY));
		seatsMap.get(4).add(new Seat(4, Letter.C, false, SeatClass.ECONOMY));
		
		seatsMap.put(5, new ArrayList<>());
		seatsMap.get(5).add(new Seat(5, Letter.A, false, SeatClass.ECONOMY));
		seatsMap.get(5).add(new Seat(5, Letter.B, false, SeatClass.ECONOMY));
		seatsMap.get(5).add(new Seat(5, Letter.C, false, SeatClass.ECONOMY));
		
		Aircraft aircraft = new Aircraft("1111-1111", "Test Aircraft", new ArrayList<>());
        seatAllocator = new SeatAllocator(aircraft);
        
        // Se pide 1 asiento libre en FIRST_CLASS
        // Se esperan 3 grupos de asientos contiguos: [1A, 1B], [2A], [2C] 
        List<List<Seat>> expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.get(0).add(new Seat(1, Letter.A, false, SeatClass.FIRST_CLASS));
        expected.get(0).add(new Seat(1, Letter.B, false, SeatClass.FIRST_CLASS));        
        expected.add(new ArrayList<>());
        expected.get(1).add(new Seat(2, Letter.A, false, SeatClass.FIRST_CLASS));    
        expected.add(new ArrayList<>());
        expected.get(2).add(new Seat(2, Letter.C, false, SeatClass.FIRST_CLASS));        
        validateAdjacentGroups(expected, 1, SeatClass.FIRST_CLASS);
        
        // Se piden 2 asientos libres contiguos en FIRST_CLASS
        // Se espera 1 grupo de asientos contiguos: [1A, 1B] 
        expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.get(0).add(new Seat(1, Letter.A, false, SeatClass.FIRST_CLASS));
        expected.get(0).add(new Seat(1, Letter.B, false, SeatClass.FIRST_CLASS));                
        validateAdjacentGroups(expected, 2, SeatClass.FIRST_CLASS);
        
        // Se piden 3 asientos libres contiguos en FIRST_CLASS
        // Se esperan 0 grupos de asientos contiguo. 
        expected = new ArrayList<>();
        validateAdjacentGroups(expected, 3, SeatClass.FIRST_CLASS);
        
        // Se pide 2 asientos libres en ECONOMY
        // Se esperan 2 grupos de asientos contiguos: [4A, 4B, 4C], [5A, 5B, 5C]  
        expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.get(0).add(new Seat(4, Letter.A, false, SeatClass.ECONOMY));
        expected.get(0).add(new Seat(4, Letter.B, false, SeatClass.ECONOMY));        
        expected.get(0).add(new Seat(4, Letter.C, false, SeatClass.ECONOMY));
        expected.add(new ArrayList<>());
        expected.get(1).add(new Seat(5, Letter.A, false, SeatClass.ECONOMY));
        expected.get(1).add(new Seat(5, Letter.B, false, SeatClass.ECONOMY));        
        expected.get(1).add(new Seat(5, Letter.C, false, SeatClass.ECONOMY));    
        validateAdjacentGroups(expected, 2, SeatClass.ECONOMY);
        
        // Se pide 3 asientos libre en ECONOMY
        // Se esperan 2 grupos de asientos contiguos: [4A, 4B, 4C], [5A, 5B, 5C]  
        expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.get(0).add(new Seat(4, Letter.A, false, SeatClass.ECONOMY));
        expected.get(0).add(new Seat(4, Letter.B, false, SeatClass.ECONOMY));        
        expected.get(0).add(new Seat(4, Letter.C, false, SeatClass.ECONOMY));
        expected.add(new ArrayList<>());
        expected.get(1).add(new Seat(5, Letter.A, false, SeatClass.ECONOMY));
        expected.get(1).add(new Seat(5, Letter.B, false, SeatClass.ECONOMY));        
        expected.get(1).add(new Seat(5, Letter.C, false, SeatClass.ECONOMY));    
        validateAdjacentGroups(expected, 3, SeatClass.ECONOMY);
        
        // Se piden 4 asientos libres en ECONOMY
        // Se esperan 0 grupos de asientos contiguos
        expected = new ArrayList<>();
        validateAdjacentGroups(expected, 4, SeatClass.ECONOMY);
                
        System.out.println("Si no ha habido mensajes de error, se ha pasado la prueba.");
	}
	
	public static void validateAdjacentGroups(List<List<Seat>> expected, int numSeats, SeatClass seatClass) {
		try {
			List<List<Seat>> actual = seatAllocator.findAdjacentGroups(seatClass, numSeats, seatsMap);
			
			System.out.format("Solicitando %d asiento(s) contiguos en %s. Obtenidos: %s.\n", numSeats, seatClass, actual);		
			
			if (expected.size() != actual.size()) 
				System.out.format("ERROR. Se esperaban %d grupos de asientos contiguos en %s. Se obtuvieron %d.\n", expected.size(), seatClass, actual.size());
			
			for (int i = 0; i < expected.size(); i++) {
				if (!expected.get(i).equals(actual.get(i))) {
					System.out.format("ERROR. Se esperaba %s como grupo contiguo nº %d de %s. Se obtuvo %s.\n", expected.get(i), i + 1, seatClass, actual.get(i));
				}
			}
		} catch (Exception ex) {
			System.out.format("ERROR: %s.\n", ex.getMessage());
		}
	}
}
