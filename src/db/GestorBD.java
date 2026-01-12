package db; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import domain.Aerolinea;
import domain.Aeropuerto;
import domain.Excursion;
import domain.Hotel;
import domain.Reserva;
import domain.User;
import domain.Vuelo;

public class GestorBD {

	private final String PROPERTIES_FILE = "resources/conf/app.properties";
	private final String LOG_FOLDER = "resources/log";


	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	
	private final String CSV_HOTELES = "resources/data/hoteles.csv";
	private final String CSV_USER = "resources/data/user.csv"; 
	private final String CSV_RESERVAS = "resources/data/reservas.csv"; 
	private final String CSV_VUELOS = "resources/data/vuelos.csv"; 
	
	private String driverName;
	private String db;
	private String connection ;
	private Properties properties;
	
	public GestorBD() {
	    try (FileInputStream fis = new FileInputStream("resources/conf/logger.properties")) {
	        LogManager.getLogManager().readConfiguration(fis);
	        
	        FileInputStream prop = new FileInputStream(PROPERTIES_FILE);
	        properties = new Properties();
	        properties.load(prop);
	        driverName = properties.getProperty("driver");
	        db = properties.getProperty("database");
	        connection = properties.getProperty("connection");
	        
	        File dir = new File(LOG_FOLDER);
	        if (!dir.exists()) {
				dir.mkdirs();
			}
	        
	        dir = new File(db.substring(0, db.lastIndexOf("/")));
			if (!dir.exists()) {
				dir.mkdirs();
			}
	        
	        Class.forName(driverName);
	    } catch (Exception ex) {
	        logger.warning(String.format("Error al cargar el driver de BBDD: %s", ex.getMessage()));
	    }
	}
	
	public void crearBBDD() {
		String sqlUser =
			    "CREATE TABLE IF NOT EXISTS USER (\n" +
			    "    usuario TEXT,\n" +
			    "    password TEXT,\n" +
			    "    nombre TEXT,\n" +
			    "    dni TEXT,\n" +
			    "    email TEXT,\n" +
			    "    telefono INTEGER,\n" +
			    "    direccion TEXT,\n" +
			    "    idioma TEXT,\n" +
			    "    moneda TEXT,\n" +
			    "    PRIMARY KEY (usuario)\n" +
			    ");";
		
		String sqlHotel =
			    "CREATE TABLE IF NOT EXISTS HOTEL (\n" +
			    "    nombre TEXT,\n" +
			    "    ciudad TEXT,\n" +
			    "    pais TEXT,\n" +
			    "    estrellas INTEGER,\n" +
			    "    capacidad INTEGER,\n" +
			    "    precio_noche REAL,\n" +
			    "    moneda TEXT,\n" +
			    "    PRIMARY KEY (nombre, ciudad)\n" +
			    ");";

		String sqlVuelo =
			    "CREATE TABLE IF NOT EXISTS VUELO (\n" +
			    "    origen TEXT,\n" +
			    "    destino TEXT,\n" +
			    "    fecha_salida TEXT,\n" +
			    "    fecha_regreso TEXT,\n" +
			    "    aerolinea TEXT,\n" +
			    "    precio_economy REAL,\n" +
			    "    precio_business REAL,\n" +
			    "    plazas_disponibles INTEGER,\n" +
			    "    PRIMARY KEY (origen, destino, fecha_salida, fecha_regreso, aerolinea)\n" +
			    ");";
			 
		String sqlReserva =
			    "CREATE TABLE IF NOT EXISTS RESERVA (\n" +
			    "    usuario TEXT,\n" +
			    "    ciudad TEXT,\n" +
			    "    nombre_Hotel TEXT,\n" +
			    "    email TEXT,\n" +
			    "    tipo_Habitacion TEXT,\n" +
			    "    num_adultos INTEGER,\n" +
			    "    num_niños INTEGER,\n" +
			    "    fecha_Entrada TEXT,\n" +
			    "    fecha_Salida TEXT,\n" +
			    "    precio_Noche REAL,\n" +
			    "    PRIMARY KEY (usuario, nombre_Hotel, ciudad, fecha_Entrada, fecha_Salida),\n" +
			    "    FOREIGN KEY (usuario) REFERENCES USER(usuario)\n" +
			    ");";
            
			
			try (Connection con = getConnectionWithFK();
			     PreparedStatement pStmt1 = con.prepareStatement(sqlUser);
				 PreparedStatement pStmt2 = con.prepareStatement(sqlHotel);
				 PreparedStatement pStmt3 = con.prepareStatement(sqlVuelo);
                 PreparedStatement pStmt4 = con.prepareStatement(sqlReserva)){
				
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
		        	logger.info("Se han creado las 4 tablas");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}	
	}
	
	public void borrarBBDD() {

			String sql1 = "DROP TABLE IF EXISTS USER;";
            String sql2 = "DROP TABLE IF EXISTS VUELO;"; 
			String sql3 = "DROP TABLE IF EXISTS HOTEL;";
			String sql4 = "DROP TABLE IF EXISTS RESERVA;";

	        try (Connection con = getConnectionWithFK();
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4)) {
				
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
		        	logger.info("Se han borrado las tablas");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar las tablas: %s", ex.getMessage()));
			}
			
			try {
				Files.delete(Paths.get(db));
				logger.info("Se ha borrado el fichero de la BBDD");
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar el fichero de la BBDD: %s", ex.getMessage()));
			}
		
	}
	
	/**
	 * Borra los datos de las 4 tablas (pero mantiene la estructura de tablas).
	 */
	
	public void borrarDatos() {
			String sql1 = "DELETE FROM USER;";
			String sql2 = "DELETE FROM VUELO;"; 
			String sql3 = "DELETE FROM RESERVA;";
			String sql4 = "DELETE FROM HOTEL;";

			try (Connection con = getConnectionWithFK();
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4))
			{
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
		        	logger.info("Se han borrado los datos de la Agencia de Viajes");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar los datos: %s", ex.getMessage()));
			}
		
	}
	
    /**
	 * Inserta Users en la BBDD.
	 */
	
	public void insertarUsuarios (User... users) {
		String sql = "INSERT INTO USER (USUARIO, PASSWORD, NOMBRE, DNI, EMAIL, TELEFONO, DIRECCION, IDIOMA, MONEDA) VALUES "+
						"(?, ?, ?, ?, ?, ?, ?, ?,? );";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (User u : users) {
				pStmt.setString(1, u.getUsuario());
				pStmt.setString(2, u.getPassword());
				pStmt.setString(3, u.getNombre());
				pStmt.setString(4, u.getDni());
				pStmt.setString(5, u.getEmail());
				Integer tel = u.getTelefono();
				if (tel == null) {
				    pStmt.setNull(6, java.sql.Types.INTEGER);
				} else {
				    pStmt.setInt(6, tel);
				}
				pStmt.setString(7, u.getDireccion());
				pStmt.setString(8, u.getIdioma());
				pStmt.setString(9, u.getMoneda());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el User: %s", u.getUsuario()));
				} else {
                            logger.info(String.format("Se ha insertado el User: %s", u.getUsuario()));
				}
			}
			logger.info(String.format("%d Usuarios insertados en la BBDD", users.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Users: %s", ex.getMessage()));
		}			
	}
    
    /**
	 * Inserta Hoteles en la BBDD.
	 */
	
	public void insertarHoteles (Hotel... hoteles) {
		String sql = "INSERT INTO HOTEL (NOMBRE, CIUDAD, PAIS, ESTRELLAS, CAPACIDAD, PRECIO_NOCHE, MONEDA) VALUES "+
						"(?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (Hotel h : hoteles) {
				pStmt.setString(1, h.getNombre());
				pStmt.setString(2, h.getCiudad());
				pStmt.setString(3, h.getPais());
				pStmt.setInt(4, h.getEstrellas());
				pStmt.setInt(5, h.getCapacidad());
				pStmt.setDouble(6, h.getPrecioNoche());
				pStmt.setString(7, h.getMoneda());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Hotel: %s", h.getNombre()));
				} else {
                            logger.info(String.format("Se ha insertado el Hotel: %s", h.getNombre()));
				}
			}
			logger.info(String.format("%d Hoteles insertados en la BBDD", hoteles.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Hoteles: %s", ex.getMessage()));
		}			
	}
	 /**
		 * Inserta Vuelos en la BBDD.
		 */
	public void insertarVuelos (Vuelo... vuelos) {
		String sql = "INSERT INTO VUELO (ORIGEN, DESTINO, FECHA_SALIDA, FECHA_REGRESO, AEROLINEA, PRECIO_ECONOMY, PRECIO_BUSINESS, PLAZAS_DISPONIBLES) "+
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (Vuelo v : vuelos) {
				pStmt.setString(1, v.getOrigen());
				pStmt.setString(2, v.getDestino());
				pStmt.setDate(3, v.getFechaSalida());
				pStmt.setDate(4, v.getFechaRegreso());
				pStmt.setString(5, v.getAerolinea());
				pStmt.setDouble(6, v.getPrecioEconomy());
				pStmt.setDouble(7, v.getPrecioBusiness());
				pStmt.setInt(8, v.getPlazasDisponibles());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Vuelo: "+ v.getOrigen() + v.getDestino()));
				} else {
                            logger.info(String.format("Se ha insertado el Vuelo: " + v.getOrigen() + v.getDestino()));
				}
			}
			logger.info(String.format("%d Vuelos insertados en la BBDD", vuelos.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Vuelos: %s", ex.getMessage()));
		}			
	}
	
	 /**
	 * Inserta Reservas en la BBDD.
	 */
	public void insertarReservas (Reserva... reservas) {
		String sql = "INSERT INTO RESERVA (USUARIO, CIUDAD, NOMBRE_HOTEL, EMAIL, TIPO_HABITACION, NUM_ADULTOS, NUM_NIÑOS, FECHA_ENTRADA, FECHA_SALIDA, PRECIO_NOCHE) "+
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
		try (Connection con = getConnectionWithFK();
				PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
								
			for (Reserva r : reservas) {
				pStmt.setString(1, r.getUsuario());
				pStmt.setString(2, r.getCiudad());
				pStmt.setString(3, r.getNombreHotel());
				pStmt.setString(4, r.getEmail());
				pStmt.setString(5, r.getTipoHabitacion());
				pStmt.setInt(6, r.getNumAdultos());
				pStmt.setInt(7, r.getNumNiños());
				pStmt.setDate(8, r.getFechaEntrada());
				pStmt.setDate(9, r.getFechaSalida());
				pStmt.setDouble(10, r.getPrecioNoche());
				
				if (pStmt.executeUpdate() != 1) {					
				logger.warning(String.format("No se ha insertado la Reserva:"+ r.getUsuario()+r.getCiudad()+ r.getNombreHotel()));
				} else {
                        logger.info(String.format("Se ha insertado la  Reserva: %s"+ r.getUsuario() + r.getCiudad()+ r.getNombreHotel()));
				}
			}
			logger.info(String.format("%d Reservas insertados en la BBDD", reservas.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Reservas: %s", ex.getMessage()));
		}			
	}
	
    /**
	 * Inserta Vuelo en la BBDD
	 */
	public void insertarVuelo(Vuelo vuelo) {
		String sql = "INSERT INTO VUELO (ORIGEN, DESTINO, FECHA_SALIDA, FECHA_REGRESO, AEROLINEA, PRECIO_ECONOMY, PRECIO_BUSINESS, PLAZAS_DISPONIBLES) "+
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pStmt.setString(1, vuelo.getOrigen());
            pStmt.setString(2, vuelo.getDestino());
            pStmt.setDate(3, vuelo.getFechaSalida());
            pStmt.setDate(4, vuelo.getFechaRegreso());
            pStmt.setString(5, vuelo.getAerolinea());
            pStmt.setDouble(6, vuelo.getPrecioEconomy());
            pStmt.setDouble(7, vuelo.getPrecioBusiness());
            pStmt.setInt(8, vuelo.getPlazasDisponibles());
				
			if (pStmt.executeUpdate() != 1) {					
				logger.warning(String.format("No se ha insertado el Vuelo: %s",vuelo.getOrigen(),"+", vuelo.getDestino(), ":", vuelo.getFechaSalida()));
			} else {
                try (ResultSet rs = pStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        logger.info(String.format("Se ha insertado el Vuelo", vuelo.getOrigen(),"+", vuelo.getDestino(), ":", vuelo.getFechaSalida()));
                    }
                }
			}
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar vuelo %s: %s", vuelo.getOrigen(),"+", vuelo.getDestino(), ":", vuelo.getFechaSalida()));
		}			
	}
	
	/**
	 * Inserta una reserva en la BBDD.
	 */
	public void insertarReserva(Reserva reserva) {
		
		String sql = "INSERT INTO RESERVA (USUARIO, CIUDAD, NOMBRE_HOTEL, EMAIL, TIPO_HABITACION, NUM_ADULTOS, NUM_NIÑOS, FECHA_ENTRADA, FECHA_SALIDA, PRECIO_NOCHE) "+
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pStmt.setString(1, reserva.getUsuario());
            pStmt.setString(2, reserva.getCiudad());
            pStmt.setString(3, reserva.getNombreHotel());
            pStmt.setString(4, reserva.getEmail());
            pStmt.setString(5, reserva.getTipoHabitacion());
            pStmt.setInt(6, reserva.getNumAdultos());
            pStmt.setInt(7, reserva.getNumNiños());
            pStmt.setDate(8, reserva.getFechaEntrada());
            pStmt.setDate(9, reserva.getFechaSalida());
            pStmt.setDouble(10, reserva.getPrecioNoche());
				
			if (pStmt.executeUpdate() != 1) {					
				logger.warning(String.format("No se ha insertado la Reserva para:"+ reserva.getUsuario()+" ->"+reserva.getCiudad()+":"+reserva.getNombreHotel()));
			} else {
                try (ResultSet rs = pStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        logger.info(String.format("Se ha insertado la Reserva"+reserva.getUsuario()+ reserva.getCiudad()+":"+ reserva.getNombreHotel()));
                    }
                }
			}
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar reserva: %s", ex.getMessage()));
		}			
	}
	
	 /**
		 * Inserta Hotel en la BBDD.
		 */
		public void insertarHotel(Hotel hotel) {
			String sql = "INSERT INTO HOTEL (NOMBRE, CIUDAD, PAIS, ESTRELLAS, CAPACIDAD, PRECIO_NOCHE, MONEDA) VALUES "+
					"(?, ?, ?, ?, ?, ?, ?);";
			
			try (Connection con = getConnectionWithFK();
				 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            
	            pStmt.setString(1, hotel.getNombre());
	            pStmt.setString(2, hotel.getCiudad());
	            pStmt.setString(3, hotel.getPais());
	            pStmt.setInt(4, hotel.getEstrellas());
	            pStmt.setInt(5, hotel.getCapacidad());
	            pStmt.setDouble(6, hotel.getPrecioNoche());
	            pStmt.setString(7, hotel.getMoneda());
					
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Hotel para: %s",hotel.getNombre()));
				} else {
	                try (ResultSet rs = pStmt.getGeneratedKeys()) {
	                    if (rs.next()) {
	                        logger.info(String.format("Se ha insertado el Hotel", hotel.getNombre()));
	                    }
	                }
				}
			} catch (Exception ex) {
				logger.warning(String.format("Error al insertar hotel: %s", ex.getMessage()));
			}			
		}


	    /**
		 * Inserta User en la BBDD.
		 */
		public void insertarUsuario(User user) {

			String sql = "INSERT INTO USER (USUARIO, PASSWORD, NOMBRE, DNI, EMAIL, TELEFONO, DIRECCION, IDIOMA, MONEDA) VALUES "+
					"(?, ?, ?, ?, ?, ?, ?, ?,? );";
			
			try (Connection con = getConnectionWithFK();
				 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            
	            pStmt.setString(1, user.getUsuario());
	            pStmt.setString(2, user.getPassword());
	            pStmt.setString(3, user.getNombre());
	            pStmt.setString(4, user.getDni());
	            pStmt.setString(5, user.getEmail());
	            Integer tel = user.getTelefono();
	            if (tel == null) {
	                pStmt.setNull(6, java.sql.Types.INTEGER);
	            } else {
	                pStmt.setInt(6, tel);
	            }
	            pStmt.setString(7, user.getDireccion());
	            pStmt.setString(8, user.getIdioma());
	            pStmt.setString(9, user.getMoneda());
					
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Usuario: %s", user.getUsuario()));
				} else {
	                try (ResultSet rs = pStmt.getGeneratedKeys()) {
	                    if (rs.next()) {
	                        logger.info(String.format("Se ha insertado el Usuario",user.getUsuario()));
	                    }
	                }
				}
			} catch (Exception ex) {
				logger.warning(String.format("Error al insertar user: %s", ex.getMessage()));
			}			
		}
		
		
	/**
	 * Elimina una reserva específica de la BBDD.
	 */
	//PRIMARY KEY (usuario, nombre_Hotel, ciudad, fecha_Entrada, fecha_Salida)
	public boolean eliminarReserva(String usuario, String nombreHotel, String ciudad, 
			java.sql.Date fechaEntrada, java.sql.Date fechaSalida) {
		
		String sql = "DELETE FROM RESERVA WHERE usuario = ? AND nombre_Hotel = ? " +
				"AND ciudad = ? AND fecha_Entrada = ? AND fecha_Salida = ?;";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
            
            pStmt.setString(1, usuario);
            pStmt.setString(2, nombreHotel);
            pStmt.setString(3, ciudad);
            pStmt.setDate(4, fechaEntrada);
            pStmt.setDate(5, fechaSalida);
			
			int filasAfectadas = pStmt.executeUpdate();
			
			if (filasAfectadas > 0) {
				logger.info(String.format("Reserva eliminada: "+ usuario + ":" + ciudad + "->"+ nombreHotel));
				return true;
			} else {
				logger.warning("No se encontró la reserva para eliminar");
				return false;
			}
			
		} catch (Exception ex) {
			logger.warning(String.format("Error al eliminar reserva: %s", ex.getMessage()));
			return false;
		}
	}
	
	/**
	 * Elimina un vuelo específico de la BBDD.
	 */
	// PRIMARY KEY (origen, destino, fecha_salida, fecha_regreso, aerolinea)
	public boolean eliminarVuelo(String origen, String destino,
			java.sql.Date fechaSalida, java.sql.Date fechaRegreso, String aerolinea) {
		
		String sql = "DELETE FROM VUELO WHERE ORIGEN = ? AND DESTINO = ? " +
                "AND FECHA_SALIDA = ? AND FECHA_REGRESO = ? AND AEROLINEA = ?;";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
            
            pStmt.setString(1, origen);
            pStmt.setString(2, destino);
            pStmt.setDate(3, fechaSalida); 
            pStmt.setDate(4, fechaRegreso);
            pStmt.setString(5, aerolinea);
			
			int filasAfectadas = pStmt.executeUpdate();
			
			if (filasAfectadas > 0) {
				logger.info(String.format("Vuelo eliminado: %s -> %s (%s - %s) [%s]",
	                    origen, destino, fechaSalida, fechaRegreso, aerolinea));				
				return true;
			} else {
				logger.warning("No se encontró el vuelo para eliminar");
				return false;
			}
			
		} catch (Exception e) {
			logger.warning(String.format("Error al eliminar vuelo: %s", e.getMessage()));
			return false;
		}
	}
	
	/**
	 * Elimina un user específico de la BBDD.
	 */
	// PRIMARY KEY (usuario)
	public boolean eliminarUser(String usuario) {
		
		String sql = "DELETE FROM USER WHERE usuario = ?;";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
            
            pStmt.setString(1, usuario);
			
			int filasAfectadas = pStmt.executeUpdate();
			
			if (filasAfectadas > 0) {
				logger.info(String.format("User eliminado: %s"+ usuario));
				return true;
			} else {
				logger.warning("No se encontró el user para eliminar");
				return false;
			}
			
		} catch (Exception e) {
			logger.warning(String.format("Error al eliminar vuelo: %s", e.getMessage()));
			return false;
		}
	}
	
	/**
	 * Elimina un hotel específico de la BBDD.
	 */
	//  PRIMARY KEY (nombre, ciudad)
	public boolean eliminarHotel(String nombre, String ciudad) {
		
		String sql = "DELETE FROM HOTEL WHERE nombre = ? AND ciudad = ?;";
		
		try (Connection con = getConnectionWithFK();
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
            
            pStmt.setString(1, nombre);
            pStmt.setString(2, ciudad);
			
			int filasAfectadas = pStmt.executeUpdate();
			
			if (filasAfectadas > 0) {
				logger.info(String.format("Hotel eliminado: %s (%s)", nombre, ciudad));
	            return true;
			} else {
				logger.warning("No se encontró el hotel para eliminar");
				return false;
			}
			
		} catch (Exception ex) {
			logger.warning(String.format("Error al eliminar hotel: %s", ex.getMessage()));
			return false;
		}
	}

	private Connection getConnectionWithFK() throws Exception {
	    Connection con = DriverManager.getConnection(connection);
	    try (Statement st = con.createStatement()) {
	        st.execute("PRAGMA foreign_keys = ON"); 
	    }
	    return con;
	}

	/**
	 * Obtiene todas las reservas de la BBDD
	 */
	

	public void updateReservas(List<Reserva> reservas, List<User> usuarios, List<Vuelo> listaVuelos,
			List<Hotel> listaHoteles) {
		if (reservas == null) {
			return;
		}
		if (usuarios != null) {
			for (Reserva r : reservas) {
				if (r == null) {
					continue;
				}
				String usuarioreserva = (r.getUsuario() == null) ? null : r.getUsuario().trim();
				if(usuarioreserva == null || usuarioreserva.isEmpty()) {
					continue;
				}
				for (User u : usuarios) {
					if (u == null || u.getUsuario() == null) {
						continue;
					}
					if (u.getUsuario().trim().equalsIgnoreCase(usuarioreserva)) {
						r.setUsuario(u.getUsuario().trim());
						if (u.getEmail() != null && !u.getEmail().trim().isEmpty()) {
							String emailrese = (r.getEmail() == null) ? "" : r.getEmail().trim();
							if (emailrese.isEmpty() || !emailrese.equalsIgnoreCase(u.getEmail().trim())) {
								r.setEmail(u.getEmail().trim());
							} else {
								r.setEmail(emailrese);
							}
						} else if (r.getEmail() != null) {
							r.setEmail(r.getEmail().trim());
						}
						break;
					}
				}
			}
		} else {
			for (Reserva r : reservas) {
				if (r == null) {
					continue;
				}
				if (r.getUsuario() != null) {
					r.setUsuario(r.getUsuario().trim());
				}
				if (r.getEmail() != null) {
					r.setEmail(r.getEmail().trim());
				}
			}
		}
		
		if (listaHoteles != null) {
			for (Reserva r : reservas) {
				if (r == null) {
					continue;
				}
				String ciudadreserva = (r.getCiudad() == null) ? null : r.getCiudad().trim();
	            String hotelreserva = (r.getNombreHotel() == null) ? null : r.getNombreHotel().trim(); 
	            if (ciudadreserva == null || ciudadreserva.isEmpty()) {
	            	continue;
	            }
	            if (hotelreserva == null || hotelreserva.isEmpty()) {
	            	continue;
	            }
	            
	            r.setCiudad(ciudadreserva);
	            r.setNombreHotel(hotelreserva);
	            
	            for (Hotel h : listaHoteles) {
	            	if (h == null || h.getCiudad() == null || h.getNombre() == null) {
	            		continue;
	            	}
	            	boolean mismaciudad = h.getCiudad().trim().equalsIgnoreCase(ciudadreserva);
	            	boolean mismohotel = h.getNombre().trim().equalsIgnoreCase(hotelreserva);
	            	if (mismaciudad && mismohotel) {
	            		r.setCiudad(h.getCiudad().trim());
	            		r.setNombreHotel(h.getNombre().trim());
	            		r.setPrecioNoche(h.getPrecioNoche());
	            		break;
	            	}
	            }
			}
		} else {
			for (Reserva r : reservas) {
				if (r == null) {
					continue;
				}
				if (r.getCiudad() != null) {
					r.setCiudad(r.getCiudad().trim());
				}
	            if (r.getNombreHotel() != null) {
	            	r.setNombreHotel(r.getNombreHotel().trim());
	            }
			}
		}
		
		for (Reserva r : reservas) {
			if (r == null) {
				continue;
			}
			if (r.getTipoHabitacion() != null) {
				r.setTipoHabitacion(r.getTipoHabitacion().trim());
			}
		}
		
	}

	public List<User> getUsuarios() {
		List<User> usuarios = new ArrayList<>();
		String sql = "SELECT * FROM USER;";
		try (Connection con = getConnectionWithFK();
		         PreparedStatement pStmt = con.prepareStatement(sql);
		         ResultSet rs = pStmt.executeQuery()) {

			while (rs.next()) {
				User u = new User();
		        u.setUsuario(rs.getString("USUARIO"));
		        u.setPassword(rs.getString("PASSWORD"));
		        u.setNombre(rs.getString("NOMBRE"));
		        u.setDni(rs.getString("DNI"));
		        u.setEmail(rs.getString("EMAIL"));
		        int tel = rs.getInt("TELEFONO");
		        if (rs.wasNull()) {
		            u.setTelefono(null);
		        } else {
		            u.setTelefono(tel);
		        }
		        u.setDireccion(rs.getString("DIRECCION"));
		        u.setIdioma(rs.getString("IDIOMA"));
		        u.setMoneda(rs.getString("MONEDA"));

		        usuarios.add(u);
			}
		 
			logger.info(String.format("Se han recuperado %d usuarios.", usuarios.size()));

		} catch (Exception e) {
			logger.warning(String.format("Error al recuperar usuarios: %s", e.getMessage()));
		}
	    return usuarios;
	}

	
	public List<Reserva> getListaTodasLasReservas() {
	    List<Reserva> reservas = new ArrayList<>();
	    String sql = "SELECT * FROM Reserva"; 
	    
	    try (Connection con = getConnectionWithFK();
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            Reserva reserva = new Reserva() ;
                reserva.setUsuario(rs.getString("usuario"));
	            reserva.setCiudad(rs.getString("ciudad"));
                reserva.setNombreHotel(rs.getString("nombre_Hotel"));
	            reserva.setEmail(rs.getString("email"));
	            reserva.setTipoHabitacion(rs.getString("tipo_Habitacion"));
	            reserva.setNumAdultos(rs.getInt("num_adultos"));
	            reserva.setNumNiños(rs.getInt("num_niños"));
	            reserva.setFechaEntrada(rs.getDate("fecha_Entrada"));
	            reserva.setFechaSalida(rs.getDate("fecha_Salida"));
	            reserva.setPrecioNoche(rs.getDouble("precio_Noche"));
	            reservas.add(reserva);
	        }
	    } catch (Exception ex) {
	        logger.warning("Error al obtener todas las reservas: " + ex.getMessage());
	    }
	    return reservas;
	}
	

	//metodo para cargar los vuelos del csv
	private List<Vuelo> loadVuelos() {
		List<Vuelo> vuelos = new ArrayList<>();
		
		try(BufferedReader in = new BufferedReader(new FileReader(CSV_VUELOS))){
			String linea;
			in.readLine(); //se omite la cabecera
			while ((linea = in.readLine()) != null) {
				linea = linea.trim();
				if (linea.isEmpty()) {
					continue;
				}
				String[] campos = linea.split(";", -1);
				if (campos.length != 8) {
					logger.warning(String.format("Línea de vuelos inválida (se esperaban 8 campos): %s", linea));
					continue;
				}
				String origen = campos[0].trim();
	            String destino = campos[1].trim();
	            String fechaSalidaStr = campos[2].trim();
	            String fechaRegresoStr = campos[3].trim();
	            String aerolinea = campos[4].trim();
	            double precioEconomy = parsePrecio(campos[5]);
	            double precioBusiness = parsePrecio(campos[6]);
	            int plazasDisponibles = Integer.parseInt(campos[7].trim());
	            
	            Vuelo v = new Vuelo();
	            v.setOrigen(origen);
	            v.setDestino(destino);
	            v.setFechaSalida(java.sql.Date.valueOf(fechaSalidaStr)); // YYYY-MM-DD
	            v.setFechaRegreso(java.sql.Date.valueOf(fechaRegresoStr));
	            v.setAerolinea(aerolinea);
	            v.setPrecioEconomy(precioEconomy);
	            v.setPrecioBusiness(precioBusiness);
	            v.setPlazasDisponibles(plazasDisponibles);
	            
	            vuelos.add(v);
			}
	        logger.info(String.format("Se han cargado %d vuelos desde el CSV.", vuelos.size()));
		}catch (Exception e) {
	        logger.warning(String.format("Error leyendo vuelos del CSV: %s", e.getMessage()));
		}
		return vuelos;
			
		}
	

	private double parsePrecio(String precio) {
		if (precio == null) {
	        return 0.0;
	    }
	    String limpio = precio.trim();
	    //nos quedamos con la parte numérica
	    int idx = limpio.indexOf(' ');
	    if (idx > 0) {
	        limpio = limpio.substring(0, idx).trim();
	    }
	    limpio = limpio.replace(',', '.');
	    return Double.parseDouble(limpio);
	}
	
	
	private List<Reserva> loadReservas() {
		List<Reserva> reservas = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new FileReader(CSV_RESERVAS))){
			String linea;
			while ((linea = in.readLine()) != null) {
				if (linea == null) {
					continue;
				}
				linea = linea.trim();
				if (linea.isEmpty()) {
					continue;
				}
				String[] campos = linea.split(";", -1);
				if (campos.length != 10) {
	                logger.warning(String.format("Línea de reservas inválida (se esperaban 10 campos): %s", linea));
	                continue;
	            }
				
				Reserva r = new Reserva();
				//usuario
				if (!campos[0].trim().isEmpty()) {
					r.setUsuario(campos[0].trim());
				}
				//ciudad
				if (!campos[1].trim().isEmpty()) {
	                r.setCiudad(campos[1].trim());
				}
				//nombre hotel
				if (!campos[2].trim().isEmpty()) {
	                r.setNombreHotel(campos[2].trim());
	            }
				//email
				if (!campos[3].trim().isEmpty()) {
	                r.setEmail(campos[3].trim());
	            }
				//tipo habitacion
				if (!campos[4].trim().isEmpty()) {
	                r.setTipoHabitacion(campos[4].trim());
	            }
				//num adultos
				if (!campos[5].trim().isEmpty()) {
	                r.setNumAdultos(Integer.parseInt(campos[5].trim()));
	            }
				//mun de niños
	            if (!campos[6].trim().isEmpty()) {
	                r.setNumNiños(Integer.parseInt(campos[6].trim()));
	            }
	            //fecha entrada
	            try {
	                if (!campos[7].trim().isEmpty()) {
	                    r.setFechaEntrada(java.sql.Date.valueOf(campos[7].trim()));
	                }
	            } catch (Exception e) {
	                r.setFechaEntrada(null);
	            }
	            //fecha salida
	            try {
	                if (!campos[8].trim().isEmpty()) {
	                    r.setFechaSalida(java.sql.Date.valueOf(campos[8].trim()));
	                }
	            } catch (Exception e) {
	                r.setFechaSalida(null);
	            }
	            //precio noche
	            try {
	                if (!campos[9].trim().isEmpty()) {
	                    String precio = campos[9].trim().replace(',', '.');
	                    r.setPrecioNoche(Double.parseDouble(precio));
	                }
	            } catch (Exception e) {
	                r.setPrecioNoche(null);
	            }
	            reservas.add(r);
			}
			logger.info(String.format("Se han cargado %d reservas desde el CSV.", reservas.size()));
		} catch (Exception e) {
			logger.warning(String.format("Error leyendo reservas del CSV: %s", e.getMessage()));		
			}
		return reservas;
	}

	private List<User> loadUsers() {
		List<User> usuarios = new ArrayList<>();

	    try (BufferedReader in = new BufferedReader(new FileReader(CSV_USER))) {
	        String linea;

	        while ((linea = in.readLine()) != null) {
	            linea = linea.trim();
	            if (linea.isEmpty()) {
	                continue;
	            }
	            String[] campos = linea.split(";", -1);
	            if (campos.length != 2) {
	                logger.warning(String.format("Línea de usuarios inválida (se esperaban 2 campos): %s", linea));
	                continue;
	            }
	            User u = new User();
	            u.setUsuario(valornull(campos[0]));
	            u.setPassword(valornull(campos[1]));
	            u.setNombre(null);
	            u.setDni(null);
	            u.setEmail(null);
	            u.setTelefono(null);
	            u.setDireccion(null);
	            u.setIdioma(null);
	            u.setMoneda(null);

	            usuarios.add(u);
	        }
	        logger.info(String.format("Se han cargado %d usuarios desde el CSV.", usuarios.size()));
	    } catch (Exception e) {
	    	logger.warning(String.format("Error leyendo usuarios del CSV: %s", e.getMessage()));
		}
	    return usuarios;
	}
	
	
	private String valornull(String string) {
		if (string == null) {
			return null;
		}
		String t = string.trim();
		if (t.isEmpty()) {
			return null;
		}
		return t;
	}

	//metodo para cargar hoteles del csv
	private List<Hotel> loadHoteles() {
		List<Hotel> hoteles = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new FileReader(CSV_HOTELES))){
			String linea;
			while ((linea = in.readLine()) != null) {
				linea = linea.trim();
				if (linea.isEmpty()) {
					continue;
				}
				String[] campos = linea.split(";", -1); //el -1 ayuda de la IAG para no perder campos vacios
	            if (campos.length != 6) {
	                logger.warning(String.format("Línea de hoteles inválida (se esperaban 6 campos): %s", linea));
	                continue;
	            }
	            String nombre = campos[0].trim();
	            String ciudad = campos[1].trim();
	            String pais = campos[2].trim();
	            int estrellas = Integer.parseInt(campos[3].trim());
	            int capacidad = Integer.parseInt(campos[4].trim());
	            String precioMoneda = campos[5].trim();
	            String[] preciocampo = precioMoneda.split(" ");
	            double precioNoche = Double.parseDouble(preciocampo[0].replace(',', '.'));
	                String moneda = (preciocampo.length > 1) ? preciocampo[1] : "";
			
	            Hotel h = new Hotel();
	            h.setNombre(nombre);
	            h.setCiudad(ciudad);
	            h.setPais(pais);
	            h.setEstrellas(estrellas);
	            h.setCapacidad(capacidad);
	            h.setPrecioNoche(precioNoche);
	            h.setMoneda(moneda);

	            hoteles.add(h);
			}
			logger.info(String.format("Se han cargado %d hoteles desde el CSV.", hoteles.size()));
		} catch (Exception e) {
			logger.warning(String.format("Error leyendo hoteles del CSV: %s", e.getMessage()));
		}
		return hoteles;
		
	}
	
	
	//metodo que guarda los vuelos
	public void storeVuelos(List<Vuelo> vuelos){
		if (vuelos != null) {
			try (PrintWriter out = new PrintWriter(new File(CSV_VUELOS))){
				out.println("ORIGEN;DESTINO;FECHA_SALIDA;FECHA_REGRESO;AEROLINEA;PRECIO_ECONOMY;PRECIO_BUSINESS;PLAZAS_DISPONIBLES");
				vuelos.forEach(v -> {
	                String linea = String.format(
	                    "%s;%s;%s;%s;%s;%.2f EUR;%.2f EUR;%d",
	                    v.getOrigen(),
	                    v.getDestino(),
	                    v.getFechaSalida().toString(),   // YYYY-MM-DD
	                    v.getFechaRegreso().toString(),  // YYYY-MM-DD
	                    v.getAerolinea(),
	                    v.getPrecioEconomy(),
	                    v.getPrecioBusiness(),
	                    v.getPlazasDisponibles()
	                );
	                out.println(linea);
	            });
	            logger.info("Se han guardado los vuelos en un CSV.");
			} catch (Exception e) {
				logger.warning(String.format("Error guardando vuelos en el CSV: %s", e.getMessage()));			}
		}
		
	}
	
	//metodo que guarda los hoteles
	public void storeHoteles (List<Hotel> hoteles) {
		if (hoteles != null) {
			try (PrintWriter out = new PrintWriter(new File(CSV_HOTELES))){
	            hoteles.forEach(h -> {
	            	String linea = String.format(
	            			"%s;%s;%s;%d;%d;%.2f %s",
	            			h.getNombre(), 
	            			h.getCiudad(),
	            			h.getPais(), 
	            			h.getEstrellas(),
	            			h.getCapacidad(),
	            			h.getPrecioNoche(),
	            			h.getMoneda());
	            	out.println(linea);
	            });
				logger.info("Se han guardado los hoteles en un CSV.");

			} catch (Exception e) {
	            logger.warning(String.format("Error guardando hoteles en el CSV: %s", e.getMessage()));
			}
		}
	}

	
	public void updateVuelos(List<Vuelo> listaVuelos, List<Aeropuerto> listaAeropuertos) {
	    if (listaVuelos == null || listaAeropuertos == null) {
	    	return;
	    }
		for (Vuelo v : listaVuelos) {
			if (v == null) {
				continue;
			}
			String origen = v.getOrigen();
			if (origen != null) {
				for (Aeropuerto a : listaAeropuertos) {
					if (a != null && a.getNombre() != null && a.getNombre().equalsIgnoreCase(origen.trim())) {
						v.setOrigen(a.getNombre());
						break;
					}
				}
			}
			String destino = v.getDestino();
			if (destino != null) {
				for (Aeropuerto a : listaAeropuertos) {
					if (a != null && a.getNombre() != null && a.getNombre().equalsIgnoreCase(destino.trim())) {
						v.setDestino(a.getNombre());
						break;
					}
				}
			}
		}
	}
	
	public List<Vuelo> getVuelos() {
		List<Vuelo> vuelos = new ArrayList<>();
		String sql = "SELECT * FROM VUELO;";
		try (Connection conn = getConnectionWithFK();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()){
			while (rs.next()) {
				Vuelo v = new Vuelo(
						rs.getString("ORIGEN"),
						rs.getString("DESTINO"),
						java.sql.Date.valueOf(rs.getString("FECHA_SALIDA")),
						java.sql.Date.valueOf(rs.getString("FECHA_REGRESO")),
						rs.getString("AEROLINEA"),
						rs.getDouble("PRECIO_ECONOMY"),
						rs.getDouble("PRECIO_BUSINESS"),
						rs.getInt("PLAZAS_DISPONIBLES"));
				vuelos.add(v);
			}
			logger.info(String.format("Se han recuperado %d vuelos.", vuelos.size()));
		} catch (Exception e) {
			logger.warning(String.format("Error al recuperar vuelos: %s", e.getMessage()));		
		}
		return vuelos;
	}

	public List<Hotel> getHoteles() {
		List<Hotel> hoteles = new ArrayList<>();
		String sql = "SELECT * FROM HOTEL;";
		try (Connection con = getConnectionWithFK();
		         PreparedStatement pStmt = con.prepareStatement(sql);
		         ResultSet rs = pStmt.executeQuery()){
			while (rs.next()) {
				Hotel h = new Hotel(
						rs.getString("NOMBRE"),
						rs.getString("CIUDAD"),
						rs.getString("PAIS"),
						rs.getInt("ESTRELLAS"),
						rs.getInt("CAPACIDAD"),
						rs.getDouble("PRECIO_NOCHE"),
						rs.getString("MONEDA")
						);
				hoteles.add(h);
			}
			logger.info(String.format("Se han recuperado %d hoteles.", hoteles.size()));
		} catch (Exception e) {
			logger.warning(String.format("Error al recuperar hoteles: %s", e.getMessage()));		}
		return hoteles;
	}

	public void initilizeFromCSV() {
		if (properties.get("loadCSV") != null && properties.get("loadCSV").equals("true")) {

	        this.borrarDatos();
	        List<User> usuarios = this.loadUsers();    
	        List<Hotel> hoteles = this.loadHoteles();
	        List<Vuelo> vuelos = this.loadVuelos();
	        List<Reserva> reservas = this.loadReservas();  

	        this.updateReservas(reservas, usuarios, vuelos, hoteles);
	        if (usuarios != null && !usuarios.isEmpty()) {
	            this.insertarUsuarios(usuarios.toArray(new User[usuarios.size()]));
	        }
	        if (hoteles != null && !hoteles.isEmpty()) {
	            this.insertarHoteles(hoteles.toArray(new Hotel[hoteles.size()]));
	        }
	        if (vuelos != null && !vuelos.isEmpty()) {
	            this.insertarVuelos(vuelos.toArray(new Vuelo[vuelos.size()]));
	        }
	        if (reservas != null && !reservas.isEmpty()) {
	            this.insertarReservas(reservas.toArray(new Reserva[reservas.size()]));
	        }

	        logger.info("Inicialización de la BBDD desde CSV completada.");
	    }
		
	}

	public List<Excursion> getExcursiones() {
		List<Excursion> excursiones = new ArrayList<>();
		String sql = "SELECT id, nombre, descripcion, precio FROM EXCURSION;";
		try (Connection conn = DriverManager.getConnection(connection);
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()){
			while(rs.next()) {
				Excursion e = new Excursion(
						rs.getInt("id"),
						rs.getString("nombre"),
						rs.getString("descripcion"),
						rs.getDouble("precio"));
				excursiones.add(e);
			}
			logger.info(String.format("Se han recuperado %d excursiones.", excursiones.size()));
		} catch (Exception e) {
			logger.warning(String.format("Error al recuperar excursiones: %s", e.getMessage()));		}
		return excursiones;
	}

	public List<Aeropuerto> getAeropuertos() {
		List<Aeropuerto> aeropuertos = new ArrayList<>();
		
	    String sql = "SELECT NOMBRE FROM AEROPUERTO;";
		try (Connection con = DriverManager.getConnection(connection);
				PreparedStatement pstm = con.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()){
			while(rs.next()) {
				Aeropuerto a = new Aeropuerto(
						rs.getString("NOMBRE"));
				aeropuertos.add(a);
			}
			logger.info(String.format("Se han recuperado %d aeropuertos.", aeropuertos.size()));
			
		} catch (Exception e) {
			logger.warning(String.format("Error al recuperar aeropuertos: %s", e.getMessage()));
		}

		return aeropuertos;
	}

	
	
	
}
