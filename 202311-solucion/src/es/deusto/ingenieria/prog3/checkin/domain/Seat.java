package es.deusto.ingenieria.prog3.checkin.domain;

import java.util.Objects;

public class Seat {
	
	public enum Letter {
		A,
		B,
		C,
		D,
		E,
		F
	};
		
	public enum SeatClass {
		FIRST_CLASS,
		BUSINESS,
		EMERGENCY,
		ECONOMY
	}
	
	public int row;
	public Letter letter;
	public boolean occupied;
	public SeatClass seatClass;
	
	public Seat(int row, Letter letter, boolean occupied, SeatClass seatClass) {
		this.row = row;
		this.letter = letter;
		this.occupied = occupied;
		this.seatClass = seatClass;
	}

	public int getRow() {
		return row;
	}

	public Letter getLetter() {
		return letter;
	}

	public boolean isOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(letter, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		return letter == other.letter && row == other.row;
	}

	@Override
	public String toString() {
		return occupied ? String.format("%d%s %s", row, letter.toString(), "Occupied") : 
			              String.format("%d%s %s", row, letter.toString(), "Available");
	}
}