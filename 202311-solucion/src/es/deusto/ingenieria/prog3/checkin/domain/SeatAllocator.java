package es.deusto.ingenieria.prog3.checkin.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;

public class SeatAllocator {
	private Aircraft aircraft;
	private Map<Integer, List<Seat>> seatsMap;

	public SeatAllocator(Aircraft aircraft) {
		this.aircraft = aircraft;
		this.seatsMap = initRowsMap(aircraft.getSeats());		
	}
	
	public Aircraft getAircraft() {
		return aircraft;
	}
	
	public Map<Integer, List<Seat>> getSeatsMap() {
		return seatsMap;
	}
	
	public int getRowSize() {
		for (List<Seat> rowSeats : this.seatsMap.values()) {
			return rowSeats.size();
		}
		
		return 0;

		// Con streams
		// return this.seatsMap.values().iterator().next().size();	
	}
	
	public List<SeatClass> getSeatClasses() {
		List<SeatClass> result = new ArrayList<>();
		
		for (List<Seat> rowSeats : this.seatsMap.values()) {
			if (!result.contains(rowSeats.get(0).getSeatClass())) {
				result.add(rowSeats.get(0).getSeatClass());
			}			
		}
		
		// Con streams
		//	seatsMap.values().forEach(rowSeats -> {
		//		if (!result.contains(rowSeats.get(0).getSeatClass())) {
		//			result.add(rowSeats.get(0).getSeatClass());
		//		}			
		//	});

		return result;
	}
	
	//TAREA 4 - Inicializa el mapa de asientos por fila.
	//A partir de la lista de asientos List<Seat> debes crear el mapa
	//que agrupa los asientos por número de fila.
	public Map<Integer, List<Seat>> initRowsMap(List<Seat> seats) {
		Map<Integer, List<Seat>> seatsByRow = new HashMap<>();
		
		for (Seat seat : seats) {
			if (!seatsByRow.containsKey(seat.getRow())) {
				seatsByRow.put(seat.getRow(), new ArrayList<>());
			}
			
			seatsByRow.get(seat.getRow()).add(seat);
		}
		
		// Con streams:
		//	seats.forEach(seat -> {
		//		seatsByRow.putIfAbsent(seat.getRow(), new ArrayList<>());
		//		seatsByRow.get(seat.getRow()).add(seat);
		//	});

		return seatsByRow;
	}

	//TAREA 5 - Crea la lista de grupos de asientos contiguos.
	//A partir de una clase de asiento (FIRST_CLASS, EMERGENCY, ECONOMY) y un número de asientos,
	//debes crear la lista que contenga listas de asientos contiguos de la misma fila.
	//Debes procesar los asientos de cada fila (que están en el mapa seatsMap) para ir agrupando 
	//los asientos libres que estén juntos. Tienes agrupar los asientos contiguos de cada fila 
	//y guardar los grupos cuyo tamaño sea mayor o igual al número de asientos necesarios.
	//Para realizar este proceso, ten en cuenta que los asientos de cada fila están 
	//ordenados de la A a la F y que los asientos con letras C y D también se consideran contiguos.
	//Si no existe ningún grupo de asientos que cumpla las condiciones de la clase y el 
	//número de asientos, la lista devuelta estará vacía.
	public List<List<Seat>> findAdjacentGroups(SeatClass seatClass, int number,  Map<Integer, List<Seat>> seatsMap) {
		List<List<Seat>> adjacentGroups = new ArrayList<>();
		List<Seat> adjacentGroup;
		
		// Se recorren las filas
		for (Integer row : seatsMap.keySet()) {
			// Se inicializa la lista de asientos libres de la fila actual
			adjacentGroup = new ArrayList<>();

			//Si los asientos de la fila actual son de la clase buscada
			if (seatsMap.get(row).get(0).getSeatClass().equals(seatClass)) {
				// Se recorre la lista de asientos de la fila actual
				for (Seat seat : seatsMap.get(row)) {				
					//Si el asiento está libre
					if (!seat.isOccupied()) {
						// Se añade al grupo de asientos libres
						adjacentGroup.add(seat);
						// Si el asiento está ocupado
					} else {
						//Si el grupo tiene al menos el temaño deseado se guarda
						if (adjacentGroup.size() >= number) {
							adjacentGroups.add(adjacentGroup);
						}
					
						//Se inicia un nuevo grupo vació
						adjacentGroup = new ArrayList<>();
					}
				}

				//Al terminar de recorrer los asientos de la fila actual
				//Se analiza el grupo que se estaba creando
				//Si el grupo tiene al menos el temaño deseado se guarda
				if (adjacentGroup.size() >= number) {
					adjacentGroups.add(adjacentGroup);
				}
			}
		}
		
		return adjacentGroups;
	}
	
	//TAREA 6 - Ordena la lista de grupos de asientos.
	//Debes ordenar la lista de listas de asientos siguiendo estos criterios:
	//- Criterio 1: De menor a mayor número de asientos de cada lista.
	//- Criterio 2: De mayor a menor número de fila de los asientos de cada lista.
	public void orderAdjacentGroups(List<List<Seat>> adjacentGroups) {
		// Con comparadores consecutivos:
		Comparator<List<Seat>> seatNumerComparator = (l1, l2) -> {
			return Integer.compare(l1.size(), l2.size());
		};
		
		Comparator<List<Seat>> rowNumberComparator = (l1, l2) -> {
			return Integer.compare(l1.get(0).getRow(), l2.get(0).getRow()) * -1;
		};

		Collections.sort(adjacentGroups, seatNumerComparator.thenComparing(rowNumberComparator));
		
		// Con un solo comparador:
		//	Comparator<List<Seat>> seatComparator = (l1, l2) -> {
		//		int ret = Integer.compare(l1.size(), l2.size());
		//		if (ret == 0) {
		//			ret = Integer.compare(l1.get(0).getRow(), l2.get(0).getRow()) * -1;
		//		}
		//		return ret;
		//	};
		//	Collections.sort(adjacentGroups, seatComparator);
	}	
	
	public List<Seat> findSeats(SeatClass seatClass, int number) {
		List<Seat> result = null;
		
		if (seatsMap != null && seatClass != null && number >= 0 && number <= this.getRowSize()) {
			List<List<Seat>> groupsSeats = findAdjacentGroups(seatClass, number, seatsMap);
			
			orderAdjacentGroups(groupsSeats);
			
			if (!groupsSeats.isEmpty()) {
				result = new ArrayList<>();
				
				for (int i=0; i<number; i++) {
					result.add(groupsSeats.get(0).get(i));
				}
			}
		}
		
		return result;
	}
	
	public void confirmSeats(List<Seat> seatsGroup) {
		if (seatsGroup != null) {
			seatsGroup.forEach(seat -> seat.setOccupied(true));		
		}
	}
}