package agencia_prog_3_data;

import java.io.Serializable;

public enum Paises implements Serializable{
	ES("España"),
	GB("Reino Unido"),
	US("Estados Unidos"),
	TH("Tailandia"),
	NO("Noruega"),
	NL("Paises Bajos"),
	MX("Mexico"),
	JP("Japon"),
	IT("Italia"),
	CH("Suiza"),
	CO("Colombia"),
	FR("France"),
	CA("Canada"),
	BE("Belgica"),
	IE("Irlanda");
	
	private String nombre;

	private Paises(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}
	
}
