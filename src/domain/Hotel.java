package agencia_prog_3_data;
public class Hotel {

    private String nombre;
    private String ciudad;
    private String pais;
    private Integer estrellas;
    private Integer capacidad;
    private Double precioNoche;
    private String moneda;

    public Hotel() {}

    public Hotel(String nombre, String ciudad, String pais,
                 Integer estrellas, Integer capacidad,
                 Double precioNoche, String moneda) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estrellas = estrellas;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        this.moneda = moneda;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public Integer getEstrellas() { return estrellas; }
    public void setEstrellas(Integer estrellas) { this.estrellas = estrellas; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public Double getPrecioNoche() { return precioNoche; }
    public void setPrecioNoche(Double precioNoche) { this.precioNoche = precioNoche; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}