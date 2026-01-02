package db; 

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import agencia_prog_3_data.Aerolinea;
import agencia_prog_3_data.Avion;
import agencia_prog_3_data.Hotel;
import agencia_prog_3_data.Reserva;
import agencia_prog_3_data.User;
import agencia_prog_3_data.Vuelo;
import agencia_prog_3_data.Aeropuertos;
import agencia_prog_3_data.VueloData;

public class GestorBD {

    //Configuración
	private final String PROPERTIES_FILE = "resources/conf/app.properties";
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	
	//Constantes (rutas csv)
	private final String CSV_HOTELES = "resources/data/hoteles.csv";
	private final String CSV_USER = "resources/data/user.csv"; 
	private final String CSV_RESERVAS = "resources/data/reservas.csv"; 
	private final String CSV_VUELOS = "resources/data/vuelos.csv"; 
	
	private String driverName;
	private String db;
	private String connection ;
	private Properties properties;
	
	public GestorBD() {
	    try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
	        LogManager.getLogManager().readConfiguration(fis);
	        properties = new Properties();
	        driverName = properties.getProperty("driver");
	        db = properties.getProperty("database");
	        connection = properties.getProperty("connection");
	        
	        Class.forName(driverName);
	    } catch (Exception ex) {
	        logger.warning(String.format("Error al cargar el driver de BBDD: %s", ex.getMessage()));
	    }
	}
	
	//Creacion de las tablas (Reservas, hoteles, user(usuarios), vuelos) 
	//Las utilizadas en la app
	
	public void crearBBDD() {
		//USER
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
		
		//HOTEL
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

		//VUELO
		String sqlVuelo =
			    "CREATE TABLE IF NOT EXISTS VUELO (\n" +
			    "    origen TEXT,\n" +
			    "    destino TEXT,\n" +
			    "    fecha_salida TEXT,\n" +
			    "    fecha_regreso TEXT,\n" +
			    "    aerolinea TEXT,\n" +
			    "    precio_economy TEXT,\n" +
			    "    precio_business TEXT,\n" +
			    "    plazas_disponibles INTEGER,\n" +
			    "    PRIMARY KEY (origen, destino, fecha_salida, fecha_regreso, aerolinea)\n" +
			    ");";
			 
		//RESERVA
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
			    "    FOREING KEY (usuario) REFERENCES USER(USUARIO)\n" +
			    ");";
            
			
			try (Connection con = DriverManager.getConnection(connection);
			     PreparedStatement pStmt1 = con.prepareStatement(sqlUser);
				 PreparedStatement pStmt2 = con.prepareStatement(sqlHotel);
				 PreparedStatement pStmt3 = con.prepareStatement(sqlVuelo);
                 PreparedStatement pStmt4 = con.prepareStatement(sqlReserva)){
				
				// Ejecutar las sentencias de creación
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
		        	logger.info("Se han creado las 4 tablas");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}	
	}
	
	//Eliminar tablas y base
	public void borrarBBDD() {

			String sql1 = "DROP TABLE IF EXISTS USER;";
            String sql2 = "DROP TABLE IF EXISTS VUELO;"; 
			String sql3 = "DROP TABLE IF EXISTS HOTEL;";
			String sql4 = "DROP TABLE IF EXISTS RESERVA;";

	        try (Connection con = DriverManager.getConnection(connection);
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
	 * Borra los datos de las 4 tablas (mantiene la estructura de tablas).
	 */
	
	public void borrarDatos() {
			String sql1 = "DELETE FROM USER;";
			String sql2 = "DELETE FROM VUELO;"; 
			String sql3 = "DELETE FROM RESERVA;";
			String sql4 = "DELETE FROM HOTEL;";

			try (Connection con = DriverManager.getConnection(connection);
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
		
		try (Connection con = DriverManager.getConnection(connection);

			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (User u : users) {
				pStmt.setString(1, u.getUsuario());
				pStmt.setString(2, u.getPassword());
				pStmt.setString(3, u.getNombre());
				pStmt.setString(4, u.getDni());
				pStmt.setString(5, u.getEmail());
				pStmt.setLong(6, u.getTelefono());
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
		
		try (Connection con = DriverManager.getConnection(connection);

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
		String sql = "INSERT INTO VUELO (ORIGEN, DESTINO, FEHCA_SALIDA, FECHA_REGRESO, AEROLINEA, PRECIO_ECONOMY, PRECIO_BUSINESS, PLAZAS_DISPONIBLES) "+
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = DriverManager.getConnection(connection);

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
					logger.warning(String.format("No se ha insertado el Vuelo: %s", v.getOrigen(),"+",v.getDestino()));
				} else {
                            logger.info(String.format("Se ha insertado el Vuelo: %s", v.getOrigen(),"+",v.getDestino()));
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
	
		try (Connection con = DriverManager.getConnection(connection);

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
				logger.warning(String.format("No se ha insertado la Reserva: %s", r.getUsuario(),"+",r.getCiudad(),"+",r.getNombreHotel()));
				} else {
                        logger.info(String.format("Se ha insertado la  Reserva: %s", r.getUsuario(),"+",r.getCiudad(),"+",r.getNombreHotel()));
				}
			}
			logger.info(String.format("%d Reservas insertados en la BBDD", reservas.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Reservas: %s", ex.getMessage()));
		}			
	}
	
    /**
	 * Inserta Vuelo en la BBDD. (Un unico vuelo)
	 */
	public void insertarVuelo(int idAerolinea, int idAvion, int idOrigen, int idDestino, String codigo, String fecha, double duracion, double precio) {
		String sql = "INSERT INTO Vuelo (codigo, fecha, duracion, precio, id_aerolinea, id_avion, id_aeropuerto_origen, id_aeropuerto_destino) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = DriverManager.getConnection(connection);
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pStmt.setString(1, codigo);
            pStmt.setString(2, fecha);
            pStmt.setDouble(3, duracion);
            pStmt.setDouble(4, precio);
            pStmt.setInt(5, idAerolinea);
            pStmt.setInt(6, idAvion);
            pStmt.setInt(7, idOrigen);
            pStmt.setInt(8, idDestino);
				
			if (pStmt.executeUpdate() != 1) {					
				logger.warning(String.format("No se ha insertado el Vuelo con código: %s", codigo));
			} else {
                try (ResultSet rs = pStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        logger.info(String.format("Se ha insertado el Vuelo con ID %d y código %s", idGenerado, codigo));
                    }
                }
			}
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar vuelo %s: %s", codigo, ex.getMessage()));
		}			
	}
	/**
	 * Inserta una reserva en la BBDD.
	 */
	public void insertarReserva(String usuario, String ciudad, String reservaNombre, 
			String email, String tipoHabitacion, int adultos, int ninos, 
			String fechaSalida, String fechaRegreso, double precio) {
		
		String sql = "INSERT INTO Reserva (usuario, ciudad, reserva_nombre, email, " +
				"tipo_habitacion, adultos, ninos, fecha_salida, fecha_regreso, precio) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pStmt.setString(1, usuario);
            pStmt.setString(2, ciudad);
            pStmt.setString(3, reservaNombre);
            pStmt.setString(4, email);
            pStmt.setString(5, tipoHabitacion);
            pStmt.setInt(6, adultos);
            pStmt.setInt(7, ninos);
            pStmt.setString(8, fechaSalida);
            pStmt.setString(9, fechaRegreso);
            pStmt.setDouble(10, precio);
				
			if (pStmt.executeUpdate() != 1) {					
				logger.warning(String.format("No se ha insertado la Reserva para: %s", usuario));
			} else {
                try (ResultSet rs = pStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        logger.info(String.format("Se ha insertado la Reserva con ID %d para %s", 
                        		rs.getInt(1), usuario));
                    }
                }
			}
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar reserva: %s", ex.getMessage()));
		}			
	}
	
	/**
	 * Recupera todas las reservas de un usuario específico.
	 * @return Lista de Object[] donde cada array contiene los datos de una reserva
	 */
	public List<Object[]> getReservasPorUsuario(String usuario) {
		List<Object[]> reservas = new ArrayList<>();
		String sql = "SELECT ciudad, reserva_nombre, email, tipo_habitacion, adultos, ninos, " +
				"fecha_salida, fecha_regreso, precio FROM Reserva WHERE usuario = ?;";
		
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {
			
			pStmt.setString(1, usuario);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				Object[] fila = {
					rs.getString("ciudad"),
					rs.getString("reserva_nombre"),
					rs.getString("email"),
					rs.getString("tipo_habitacion"),
					rs.getInt("adultos"),
					rs.getInt("ninos"),
					rs.getString("fecha_salida"),
					rs.getString("fecha_regreso"),
					rs.getDouble("precio"),
					"Cancelar" // Para el botón
				};
				reservas.add(fila);
			}
			
			rs.close();
			logger.info(String.format("Se han recuperado %d reservas para %s", reservas.size(), usuario));
			
		} catch (Exception ex) {
			logger.warning(String.format("Error al recuperar reservas: %s", ex.getMessage()));
		}
		
		return reservas;
	}
	
	/**
	 * Elimina una reserva específica de la BBDD.
	 */
	public boolean eliminarReserva(String usuario, String ciudad, String reservaNombre, 
			String fechaSalida, double precio) {
		
		String sql = "DELETE FROM Reserva WHERE usuario = ? AND ciudad = ? " +
				"AND reserva_nombre = ? AND fecha_salida = ? AND precio = ?;";
		
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
            
            pStmt.setString(1, usuario);
            pStmt.setString(2, ciudad);
            pStmt.setString(3, reservaNombre);
            pStmt.setString(4, fechaSalida);
            pStmt.setDouble(5, precio);
			
			int filasAfectadas = pStmt.executeUpdate();
			
			if (filasAfectadas > 0) {
				logger.info(String.format("Reserva eliminada: %s en %s", reservaNombre, ciudad));
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
	 * Recupera todas las reservas (para testing o admin).
	 */
	public void getTodasLasReservas() {
		String sql = "SELECT * FROM Reserva;";

		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			ResultSet rs = pStmt.executeQuery();			
			int count = 0;

			while (rs.next()) {
                String info = String.format("Reserva ID %d: %s - %s en %s (Salida: %s, Precio: %.2f€)",
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("reserva_nombre"),
                        rs.getString("ciudad"),
                        rs.getString("fecha_salida"),
                        rs.getDouble("precio"));
                
                logger.info(info);
				count++;
			}
			
			rs.close();
			logger.info(String.format("Se han recuperado %d reservas en total.", count));			
		} catch (Exception ex) {
			logger.warning(String.format("Error al recuperar las reservas: %s", ex.getMessage()));						
		}		
	}
	
	
	
	
	
    /**
	 * Recupera todos los vuelos con la información completa de las entidades relacionadas.
	 */
	public void getVuelosCompletos() {

		String sql = "SELECT V.codigo, V.fecha, V.duracion, V.precio, "
                + "AERO_LINEA.nombre AS aerolinea_nombre, "
                + "AV.codigo AS avion_codigo, "
                + "AP_ORIGEN.nombre AS origen_nombre, "
                + "AP_DESTINO.nombre AS destino_nombre "
                + "FROM Vuelo V "
                + "JOIN Aerolinea AERO_LINEA ON V.id_aerolinea = AERO_LINEA.id "
                + "JOIN Avion AV ON V.id_avion = AV.id "
                + "JOIN Aeropuerto AP_ORIGEN ON V.id_aeropuerto_origen = AP_ORIGEN.id "
                + "JOIN Aeropuerto AP_DESTINO ON V.id_aeropuerto_destino = AP_DESTINO.id;";


		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			ResultSet rs = pStmt.executeQuery();			
			int count = 0;

			while (rs.next()) {

                String info = String.format("Vuelo %s (%s) de %s a %s. Aerolínea: %s. Avión: %s. Precio: %.2f",
                        rs.getString("codigo"), rs.getString("fecha"),
                        rs.getString("origen_nombre"), rs.getString("destino_nombre"),
                        rs.getString("aerolinea_nombre"), rs.getString("avion_codigo"),
                        rs.getDouble("precio"));
                
                logger.info(info);
				count++;
			}
			
			rs.close();
			
			logger.info(String.format("Se han recuperado %d vuelos completos.", count));			
		} catch (Exception ex) {
			logger.warning(String.format("Error recuperar los vuelos: %s", ex.getMessage()));						
		}		
	}

	/**
     * Busca y retorna el ID de un Avion por su código.
     * Retorna -1 si no lo encuentra o si hay un error.
     */
    public int getAvionIdByCodigo(String codigo) {
        String sql = "SELECT id FROM Avion WHERE codigo = ?;";
        
        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement pStmt = con.prepareStatement(sql)) {
                
            pStmt.setString(1, codigo);
                
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception ex) {
            logger.warning(String.format("Error al buscar ID de Avion con código %s: %s", codigo, ex.getMessage()));
        }
        return -1; // No encontrado
    }

    /**
     * Busca y retorna el ID de una Aerolinea por su nombre.
     * Retorna -1 si no la encuentra o si hay un error.
     */
    public int getAerolineaIdByNombre(String nombre) {
        String sql = "SELECT id FROM Aerolinea WHERE nombre = ?;";
        
        try (Connection con = DriverManager.getConnection(connectionString);
             PreparedStatement pStmt = con.prepareStatement(sql)) {
                
            pStmt.setString(1, nombre);
                
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception ex) {
            logger.warning(String.format("Error al buscar ID de Aerolínea %s: %s", nombre, ex.getMessage()));
        }
        return -1; // No encontrado
    }

    /**
	 * Inserta Aerolineas en la BBDD.
	 */
	public void insertarAerolinea(Aerolinea... aerolineas) {
		String sql = "INSERT INTO Aerolinea (nombre) VALUES (?);";
		
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (Aerolinea a : aerolineas) {
				pStmt.setString(1, a.getNombre());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado la Aerolínea: %s", a.getNombre()));
				}
			}
			logger.info(String.format("%d Aerolíneas insertadas en la BBDD", aerolineas.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Aerolíneas: %s", ex.getMessage()));
		}			
	}


    /**
	 * Inserta Aviones en la BBDD.
	 */
	public void insertarAvion(Avion... aviones) {
		String sql = "INSERT INTO Avion (codigo, nombre, numero_asientos) VALUES (?, ?, ?);";
		
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (Avion a : aviones) {
				pStmt.setString(1, a.getCodigo());
				pStmt.setString(2, a.getNombre());
				pStmt.setInt(3, a.getNumeroasientos());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Avión: %s (%s)", a.getCodigo(), a.getNombre()));
				}
			}
			logger.info(String.format("%d Aviones insertados en la BBDD", aviones.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Aviones: %s", ex.getMessage()));
		}			
	}
	
	
	
	private Connection getConnectionWithFK() throws Exception {
	    Connection con = DriverManager.getConnection(connectionString);
	    try (Statement st = con.createStatement()) {
	        st.execute("PRAGMA foreign_keys = ON"); 
	    }
	    return con;
	}

	

	/**
	 * Obtiene todas las reservas de la BBDD en formato de matriz de objetos.
	 */
	public List<Object[]> getListaTodasLasReservas() {
	    List<Object[]> reservas = new ArrayList<>();
	    // Ajusta la consulta SQL y los nombres de las columnas a tu esquema de DB real
	    String sql = "SELECT * FROM Reserva"; 
	    
	    try (Connection con = DriverManager.getConnection(connectionString);
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            // **IMPORTANTE**: Ajusta el orden y tipo de datos de Object[] 
	            // para que coincida con las columnas de tu JTable en VentanaGestionDB
	            Object[] reserva = new Object[] {
//	                rs.getInt("id_reserva"),
//	                rs.getString("usuario"),
//	                rs.getString("ciudad"),
//	                rs.getString("nombre_reserva"),
//	                rs.getString("fecha_salida"),
//	                rs.getDouble("precio")
	                // ... otras columnas
	            };
	            reservas.add(reserva);
	        }
	    } catch (Exception ex) {
	        logger.warning("Error al obtener todas las reservas: " + ex.getMessage());
	    }
	    return reservas;
	}
	// ...

	public boolean eliminarAeropuertoPorId(int id) {
		String sql = "DELETE FROM Aeropuerto WHERE id = ?;";
	    try (Connection con = getConnectionWithFK();
	         PreparedStatement pStmt = con.prepareStatement(sql)) {

	        pStmt.setInt(1, id);
	        int filas = pStmt.executeUpdate();
	        if (filas > 0) {
	            logger.info(String.format("Aeropuerto (id=%d) eliminado.", id));
	            return true;
	        } else {
	            logger.warning(String.format("No se encontró Aeropuerto con id=%d para eliminar.", id));
	            return false;
	        }
	    } catch (Exception ex) {
	        logger.warning(String.format("Error al eliminar Aeropuerto id=%d: %s", id, ex.getMessage()));
	        return false;
	    }
	}

	// Opcional, por nombre 
	public boolean eliminarAeropuertoPorNombre(String nombre) {
	    String sql = "DELETE FROM Aeropuerto WHERE nombre = ?;";
	    try (Connection con = getConnectionWithFK();
	         PreparedStatement pStmt = con.prepareStatement(sql)) {

	        pStmt.setString(1, nombre);
	        int filas = pStmt.executeUpdate();
	        if (filas > 0) {
	            logger.info(String.format("Aeropuerto '%s' eliminado.", nombre));
	            return true;
	        } else {
	            logger.warning(String.format("No se encontró Aeropuerto '%s' para eliminar.", nombre));
	            return false;
	        }
	    } catch (Exception ex) {
	        logger.warning(String.format("Error al eliminar Aeropuerto '%s': %s", nombre, ex.getMessage()));
	        return false;
	    }
	}
	

	
	public void resetearAutoincrement(String tabla) {
	    String sql = "DELETE FROM sqlite_sequence WHERE name = ?;";
	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, tabla);
	        ps.executeUpdate();
	        logger.info("Secuencia AUTOINCREMENT reseteada para tabla: " + tabla);
	    } catch (Exception ex) {
	        logger.warning("Error al resetear AUTOINCREMENT de " + tabla + ": " + ex.getMessage());
	    }
	}

	
	
	
	
	
	/**
	 * ================ CARGA DE DATOS DESDE LOS CSV ================
	 */
	


	// En la clase GestorBD.java

	public void cargarDependenciasVueloDesdeCSV(String csvVuelosPath) {
	    logger.info("Iniciando carga de Aerolíneas y Aeropuertos únicos desde CSV de Vuelos.");
	    
	    java.util.Set<String> aerolineasUnicas = new java.util.HashSet<>();
	    java.util.Set<String> aeropuertosUnicos = new java.util.HashSet<>();

	    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvVuelosPath))) {
	        String linea;
	        br.readLine(); // Omitir la cabecera del CSV de Vuelos
	        
	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(",");
	            if (partes.length >= 5) {
	                // Leer y normalizar (MAYÚSCULAS y trim) la ciudad
	                String origenCiudad = partes[0].trim().toUpperCase(); 
	                String destinoCiudad = partes[1].trim().toUpperCase();
	                String aerolinea = partes[4].trim().toUpperCase();
	                
	                // Insertamos el nombre descriptivo del aeropuerto en el Set
	                if (!origenCiudad.isEmpty() && !origenCiudad.equals("N/A")) {
	                    aeropuertosUnicos.add("AEROPUERTO DE " + origenCiudad); // <-- CORRECCIÓN: Nombre descriptivo
	                }
	                if (!destinoCiudad.isEmpty() && !destinoCiudad.equals("N/A")) {
	                    aeropuertosUnicos.add("AEROPUERTO DE " + destinoCiudad); // <-- CORRECCIÓN: Nombre descriptivo
	                }
	                
	                if (!aerolinea.isEmpty() && !aerolinea.equals("N/A")) aerolineasUnicas.add(aerolinea); 
	            }
	        }
	    } catch (Exception e) {
	        logger.warning("Error leyendo el CSV de vuelos para dependencias: " + e.getMessage());
	        return;
	    }

	    // El resto del método permanece igual, insertando los nombres descriptivos
	    agencia_prog_3_data.Aeropuertos[] arrayAeropuertos = aeropuertosUnicos.stream().map(agencia_prog_3_data.Aeropuertos::new).toArray(agencia_prog_3_data.Aeropuertos[]::new);
	    insertarAeropuerto(arrayAeropuertos); 
	    
	    agencia_prog_3_data.Aerolinea[] arrayAerolineas = aerolineasUnicas.stream().map(agencia_prog_3_data.Aerolinea::new).toArray(agencia_prog_3_data.Aerolinea[]::new);
	    insertarAerolinea(arrayAerolineas); 
	    
	    logger.info(String.format("Carga de dependencias finalizada. %d Aerolíneas y %d Aeropuertos procesados.", 
	        aerolineasUnicas.size(), aeropuertosUnicos.size()));
	}
	
	

	// En la clase GestorBD.java

	public void cargarVuelosDesdeCSV(String csvVuelosPath) {
	    logger.info("Iniciando carga de Vuelos desde CSV.");
	    
	    final String CODIGO_AVION_DEFECTO = "DEF001";
	    int avionesID = getAvionIdByCodigo(CODIGO_AVION_DEFECTO);
	    // ... (Inserción de Avión si es necesario) ...

	    int vuelosCargados = 0;
	    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvVuelosPath))) {
	        String linea;
	        br.readLine(); // Omitir la cabecera

	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(",");
	            if (partes.length < 8) continue; 

	            try {
	                String origenNombre = partes[0].trim(); // e.g., "Madrid"
	                String destinoNombre = partes[1].trim(); // e.g., "Barcelona"
	                String fechaSalida = partes[2].trim(); 
	                String aerolineaNombre = partes[4].trim();
	                
	                // Normalizar la ciudad para crear el nombre descriptivo de BÚSQUEDA
	                String origenBusqueda = "AEROPUERTO DE " + origenNombre.toUpperCase(); // <-- CORRECCIÓN CLAVE
	                String destinoBusqueda = "AEROPUERTO DE " + destinoNombre.toUpperCase(); // <-- CORRECCIÓN CLAVE
	                String aerolineaBusqueda = aerolineaNombre.toUpperCase();
	                
	                String precioStr = partes[5].trim().replace(" EUR", "").replace(".", "").replace(",", ".");
	                double precioEconomy = Double.parseDouble(precioStr);
	                
	                double duracion = 2.0; 
	                
	                // Búsqueda de IDs usando el nombre descriptivo completo
	                int idOrigen = getAeropuertoIdByNombre(origenBusqueda);
	                int idDestino = getAeropuertoIdByNombre(destinoBusqueda);
	                int idAerolinea = getAerolineaIdByNombre(aerolineaBusqueda);
	                
	                // Genera un código de vuelo (usando la ciudad, no el nombre completo)
	                String codigoVuelo = aerolineaBusqueda.substring(0, Math.min(aerolineaBusqueda.length(), 3))
	                                   + origenNombre.substring(0, Math.min(origenNombre.length(), 3)).toUpperCase() 
	                                   + destinoNombre.substring(0, Math.min(destinoNombre.length(), 3)).toUpperCase()
	                                   + fechaSalida.replace("-", "")
	                                   + "-" + (vuelosCargados + 1); 

	                if (idOrigen != -1 && idDestino != -1 && idAerolinea != -1) {
	                    insertarVuelo(
	                        idAerolinea, avionesID, idOrigen, idDestino,
	                        codigoVuelo, fechaSalida, duracion, precioEconomy
	                    );
	                    vuelosCargados++;
	                } else {
	                    // Mantener el log de advertencia claro
	                    logger.warning(String.format("Fallo (Vuelo): FK no encontrado para Aerolínea='%s', Origen='%s', Destino='%s'. (Buscado: Origen='%s', Destino='%s')",
	                        aerolineaNombre, origenNombre, destinoNombre, origenBusqueda, destinoBusqueda));
	                }

	            } catch (NumberFormatException e) {
	                logger.warning("Error de formato numérico (Precio) en línea de vuelo: " + linea + " | " + e.getMessage());
	            } catch (Exception e) {
	                logger.warning("Error SQL al insertar vuelo: " + linea + " | " + e.getMessage());
	            }
	        }
	    } catch (Exception e) {
	        logger.severe("Error de I/O al cargar vuelos: " + e.getMessage());
	    }
	    logger.info(String.format("Carga de Vuelos finalizada. %d vuelos cargados.", vuelosCargados));
	}
	
	
	/**
	 * Carga modelos de Avión únicos basándose en la aerolínea y las plazas disponibles del CSV de vuelos.
	 * Utiliza Aerolinea-Capacidad para generar un código único de avión.
	 */
	public void cargarAvionesDesdeCSV(String csvVuelosPath) {
	    logger.info("Iniciando carga de Aviones únicos desde CSV de Vuelos.");

	    // Set para almacenar la clave única "AEROLINEA-CAPACIDAD" y evitar duplicados
	    java.util.Set<String> avionesUnicos = new java.util.HashSet<>();
	    int contador = 1;

	    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvVuelosPath))) {
	        String linea;
	        br.readLine(); // Omitir la cabecera del CSV de Vuelos

	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(",");
	            if (partes.length < 8) continue; // Mínimo 8 columnas

	            try {
	                String aerolinea = partes[4].trim().toUpperCase();
	                String plazasDisponiblesStr = partes[7].trim(); // Plazas Disponibles

	                if (aerolinea.isEmpty() || aerolinea.equals("N/A") || plazasDisponiblesStr.isEmpty() || plazasDisponiblesStr.equals("N/A")) {
	                    continue; // Saltar si faltan datos clave
	                }

	                int numAsientos = Integer.parseInt(plazasDisponiblesStr);
	                String clave = aerolinea + "-" + numAsientos;
	                
	                if (avionesUnicos.add(clave)) {
	                    // Generar un código único basado en el patrón AEROLINEA+CAPACIDAD
	                    String codigoAvion = aerolinea.substring(0, Math.min(aerolinea.length(), 2)) + numAsientos;
	                    String nombreAvion = aerolinea + " Modelo " + contador;
	                    
	                    agencia_prog_3_data.Avion nuevoAvion = new agencia_prog_3_data.Avion(
	                        codigoAvion, 
	                        nombreAvion, 
	                        numAsientos
	                    );
	                    
	                    insertarAvion(nuevoAvion); // Asegúrate de que este método exista
	                    contador++;
	                }
	            } catch (NumberFormatException e) {
	                logger.warning("Error de formato numérico (Plazas Disponibles) en línea de vuelo: " + linea + " | " + e.getMessage());
	            }
	        }
	    } catch (Exception e) {
	        logger.severe("Error de I/O al cargar aviones: " + e.getMessage());
	    }

	    logger.info(String.format("Carga de Aviones finalizada. %d modelos de avión únicos procesados.", avionesUnicos.size()));
	}
	

	public void cargarReservasDesdeCSV(String csvReservasPath) {
	    logger.info("Iniciando carga de Reservas desde CSV.");
	    int reservasCargadas = 0;
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(csvReservasPath))) {
	        String linea;
	        br.readLine(); // Omitir la cabecera del CSV de Reservas

	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(",");
	            // El CSV de reservas tiene 11 campos de datos útiles (0 a 10)
	            if (partes.length < 11) continue; 

	            try {
	                String usuario = partes[0].trim();
	                String ciudad = partes[1].trim();
	                String reservaNombre = partes[2].trim();
	                String email = partes[3].trim();
	                String tipoHabitacion = partes[4].trim();
	                
	                int adultos = 0;
	                if (!partes[5].trim().isEmpty() && !partes[5].trim().equals("N/A")) {
	                    adultos = Integer.parseInt(partes[5].trim());
	                }

	                int ninos = 0;
	                if (!partes[6].trim().isEmpty() && !partes[6].trim().equals("N/A")) {
	                    ninos = Integer.parseInt(partes[6].trim());
	                }

	                // ¡CORRECCIÓN CRÍTICA DE FECHA! Se usa el formateador
	                String fechaSalida = formatearFecha(partes[7]);
	                String fechaRegreso = formatearFecha(partes[8]);
	                
	                if (fechaSalida == null || fechaRegreso == null) {
	                    logger.warning("Reserva omitida: Error al formatear una o ambas fechas. Línea: " + linea);
	                    continue;
	                }
	                
	                // Unificación del precio (Entero, Decimal)
	                double precioEntero = Double.parseDouble(partes[9].trim());
	                double precioDecimal = Double.parseDouble(partes[10].trim()); 
	                double precioFinal = precioEntero + (precioDecimal / 100.0);
	                
	                
	                insertarReserva(
	                    usuario, ciudad, reservaNombre, email, tipoHabitacion,
	                    adultos, ninos, fechaSalida, fechaRegreso, precioFinal
	                );
	                reservasCargadas++;

	            } catch (NumberFormatException e) {
	                logger.warning(String.format("Error de formato numérico en línea de reserva: %s | %s", 
	                    linea, e.getMessage()));
	            } catch (Exception e) {
	                logger.warning(String.format("Error SQL al insertar reserva: %s | %s", 
	                    linea, e.getMessage()));
	            }
	        }
	    } catch (Exception e) {
	        logger.severe("Error de I/O al cargar reservas: " + e.getMessage());
	    }
	    logger.info(String.format("Carga de Reservas finalizada. %d reservas cargadas.", reservasCargadas));
	}
	
	
	
	/**
	 * Convierte el formato de fecha DD/MM/YYYY a YYYY-MM-DD para SQLite.
	 */
	private String formatearFecha(String fechaDDMMYYYY) {
	    if (fechaDDMMYYYY == null || fechaDDMMYYYY.isEmpty() || fechaDDMMYYYY.toUpperCase().equals("N/A")) {
	        return null;
	    }
	    try {
	        // Formato de entrada: 07/11/2025
	        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
	        // Formato de salida: 2025-11-07
	        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
	        
	        Date fecha = formatoEntrada.parse(fechaDDMMYYYY.trim());
	        return formatoSalida.format(fecha);
	    } catch (Exception e) {
	        return null; 
	    }
	}
	

	public void cargarDatosDesdeCSVs(String csvReservasPath, String csvVuelosPath) {
	    cargarDependenciasVueloDesdeCSV(csvVuelosPath);
	    cargarAvionesDesdeCSV(csvVuelosPath);
	    cargarVuelosDesdeCSV(csvVuelosPath);
	    cargarReservasDesdeCSV(csvReservasPath);
	}
    
	
	
	
	// En la clase GestorBD.java

	public boolean insertarReservaDirecta(String usuario, String codigoReserva, String tipoReserva, 
	                                     String ciudad, String servicioNombre, String email, 
	                                     int adultos, int ninos, String fechaSalida, 
	                                     String fechaRegreso, double precioFinal) {
	    
	    // Consulta SQL para insertar en la tabla Reserva
	    String sql = "INSERT INTO Reserva (Usuario, CodigoReserva, TipoReserva, CiudadDestino, " +
	                 "NombreServicio, Email, Adultos, Ninos, FechaSalida, FechaRegreso, PrecioFinal, FechaReserva) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, DATE('now'))";
	                 
	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement pstmt = con.prepareStatement(sql)) {
	        
	        pstmt.setString(1, usuario);
	        pstmt.setString(2, codigoReserva);
	        pstmt.setString(3, tipoReserva.toUpperCase()); 
	        pstmt.setString(4, ciudad.isEmpty() || ciudad.equalsIgnoreCase("N/A") ? "N/A" : ciudad); 
	        pstmt.setString(5, servicioNombre);
	        pstmt.setString(6, email);
	        pstmt.setInt(7, adultos);
	        pstmt.setInt(8, ninos);
	        pstmt.setString(9, formatearFecha(fechaSalida));   // Usar el helper para DD/MM/YYYY -> YYYY-MM-DD
	        pstmt.setString(10, formatearFecha(fechaRegreso)); // Usar el helper para DD/MM/YYYY -> YYYY-MM-DD
	        pstmt.setDouble(11, precioFinal);
	        
	        return pstmt.executeUpdate() > 0;
	        
	    } catch (Exception e) {
	        logger.severe("Error al insertar reserva directa: " + e.getMessage());
	        return false;
	    }
	}
	
	
	
}