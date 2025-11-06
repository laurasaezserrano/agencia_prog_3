package agencia_prog_3_GUI;

import java.io.Serializable;

public class Aeropuertos implements Comparable<Aeropuertos>, Serializable {
	private String nombre;
	private String ciudad;
	private String pais;
	private String codigo;
	public Aeropuertos(String nombre, String ciudad, String pais, String codigo) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.pais = pais;
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public String getCiudad() {
		return ciudad;
	}
	public String getPais() {
		return pais;
	}
	public String getCodigo() {
		return codigo;
	}
	
	//TO STRING, HASHCODE, EQUALS -- FALTAN DE HACER
	
	@Override
	public int compareTo(Aeropuertos other) {
		// TODO Auto-generated method stub
		return this.getCodigo().compareTo(other.getCodigo());
	}
	
	
	
	
	
	
	
	
	
}
