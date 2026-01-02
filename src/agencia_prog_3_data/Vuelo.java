package agencia_prog_3_data;
public class Vuelo {

    private String origen;
    private String destino;
    private String fechaSalida;
    private String fechaRegreso;
    private String aerolinea;
    private String precioEconomy;
    private String precioBusiness;
    private Integer plazasDisponibles;

    public Vuelo() {}

    public Vuelo(String origen, String destino, String fechaSalida,
                 String fechaRegreso, String aerolinea,
                 String precioEconomy, String precioBusiness,
                 Integer plazasDisponibles) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
        this.aerolinea = aerolinea;
        this.precioEconomy = precioEconomy;
        this.precioBusiness = precioBusiness;
        this.plazasDisponibles = plazasDisponibles;
    }

    // Getters y Setters
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(String fechaSalida) { this.fechaSalida = fechaSalida; }

    public String getFechaRegreso() { return fechaRegreso; }
    public void setFechaRegreso(String fechaRegreso) { this.fechaRegreso = fechaRegreso; }

    public String getAerolinea() { return aerolinea; }
    public void setAerolinea(String aerolinea) { this.aerolinea = aerolinea; }

    public String getPrecioEconomy() { return precioEconomy; }
    public void setPrecioEconomy(String precioEconomy) { this.precioEconomy = precioEconomy; }

    public String getPrecioBusiness() { return precioBusiness; }
    public void setPrecioBusiness(String precioBusiness) { this.precioBusiness = precioBusiness; }

    public Integer getPlazasDisponibles() { return plazasDisponibles; }
    public void setPlazasDisponibles(Integer plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }
}