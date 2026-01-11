package domain;

import java.sql.Date;

public class Vuelo {

    private String origen;
    private String destino;
    private Date fechaSalida;
    private Date fechaRegreso;
    private String aerolinea;
    private Double precioEconomy;
    private Double precioBusiness;
    private Integer plazasDisponibles;

    public Vuelo() {}

    public Vuelo(String origen, String destino, Date fechaSalida,
                 Date fechaRegreso, String aerolinea,
                 Double precioEconomy, Double precioBusiness,
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

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public Date getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }

    public Date getFechaRegreso() { return fechaRegreso; }
    public void setFechaRegreso(Date fechaRegreso) { this.fechaRegreso = fechaRegreso; }

    public String getAerolinea() { return aerolinea; }
    public void setAerolinea(String aerolinea) { this.aerolinea = aerolinea; }

    public double getPrecioEconomy() { return precioEconomy; }
    public void setPrecioEconomy(double precioEconomy) { this.precioEconomy = precioEconomy; }

    public double getPrecioBusiness() { return precioBusiness; }
    public void setPrecioBusiness(double precioBusiness) { this.precioBusiness = precioBusiness; }

    public Integer getPlazasDisponibles() { return plazasDisponibles; }
    public void setPlazasDisponibles(Integer plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }
}