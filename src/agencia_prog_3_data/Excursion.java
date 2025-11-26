package agencia_prog_3_data;

public class Excursion {
	private final int id; 
	private final String nombre; 
	private final String descripcion; 
	private final double precio;
	
    public Excursion(int id, String nombre, String descripcion, double precio) {
        this.id=id; this.nombre=nombre; this.descripcion=descripcion; this.precio=precio;
    }
    
    public int getId() { 
    	return id; 
    	
    }
    
    public String getNombre() {
    	return nombre; 
    	
    }
    
    public String getDescripcion() {
    	return descripcion; 
    	
    }
    
    public double getPrecio() { 
    	return precio; 
    	
    }
}
