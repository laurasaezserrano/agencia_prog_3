package es.deusto.ingenieria.prog3.checkin.domain;

import java.util.List;

public class Aircraft {	
	private String code;
	private String name;
	private List<Seat> seats;
	
	public Aircraft(String code, String name, List<Seat> seats) {
		this.code = code;
		this.name = name;
		this.seats = seats;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	@Override
	public String toString() {
		return "Aircraft [code=" + code + ", name=" + name + ", seats=" + seats.size() + "]";
	}
}