package agencia_prog_3_data;

import java.sql.Date;

public class Reserva {

    private String usuario;
    private String ciudad;
    private String nombreHotel;
    private String email;
    private String tipoHabitacion;
    private Integer numAdultos;
    private Integer numNiños;
    private Date fechaEntrada;
    private Date fechaSalida;
    private Double precioNoche;

    public Reserva() {}

    public Reserva(String usuario, String ciudad, String nombreHotel,
                   String email, String tipoHabitacion,
                   Integer numAdultos, Integer numNiños,
                   Date fechaEntrada, Date fechaSalida,
                   Double precioNoche) {
        this.usuario = usuario;
        this.ciudad = ciudad;
        this.nombreHotel = nombreHotel;
        this.email = email;
        this.tipoHabitacion = tipoHabitacion;
        this.numAdultos = numAdultos;
        this.numNiños = numNiños;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precioNoche = precioNoche;
    }

    // Getters y Setters
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getNombreHotel() { return nombreHotel; }
    public void setNombreHotel(String nombreHotel) { this.nombreHotel = nombreHotel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoHabitacion() { return tipoHabitacion; }
    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public Integer getNumAdultos() { return numAdultos; }
    public void setNumAdultos(Integer numAdultos) { this.numAdultos = numAdultos; }

    public Integer getNumNiños() { return numNiños; }
    public void setNumNiños(Integer numNiños) { this.numNiños = numNiños; }

    public Date getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getPrecioNoche() { return precioNoche; }
    public void setPrecioNoche(Double precioNoche) {
        this.precioNoche = precioNoche;
    }
}