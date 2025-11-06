package agencia_prog_3_GUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aerolinea implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombre;
	private Paises pais;
	private List<DatosVuelos> vuelos;
	public Aerolinea(String codigo, String nombre, Paises pais, List<DatosVuelos> vuelos) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.pais = pais;
		this.vuelos = new ArrayList<>();
	}
	
	public void añadevuelos(DatosVuelos vuelo) {
		if (vuelos != null && !vuelos.contains(vuelo)) {
			vuelos.add(vuelo);
		}
	}
	public String getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public Paises getPais() {
		return pais;
	}
	public List<DatosVuelos> getVuelos() {
		return vuelos;
	}
	
	

	//HASHCODE, EQUALS Y TOSTRING -- FALTAN
	
	
}
