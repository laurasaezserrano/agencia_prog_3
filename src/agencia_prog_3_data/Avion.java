package agencia_prog_3_data;

import java.io.Serializable;

public class Avion implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombre;
	private int numeroasientos;
	
	
	public Avion(String codigo, String nombre, int numeroasientos) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.numeroasientos = numeroasientos;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public int getNumeroasientos() {
		return numeroasientos;
	}


	
	

}
