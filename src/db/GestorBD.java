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
import agencia_prog_3_data.Aeropuertos;
import agencia_prog_3_data.VueloData;

public class GestorBD {

    // 1. Constantes y configuración
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	private final String CSV_AEROLINEAS = "resources/data/aerolineas.csv"; 
	
	private String driverName = "org.sqlite.JDBC";
	private String databaseFile = "resources/data/agencia.db";
	private String connectionString = "jdbc:sqlite:resources/data/agencia.db";
	// ...

	public GestorBD() {
	    // Si la carga de logger.properties también da problemas, puedes envolver solo esa parte en un try/catch
	    try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
	        LogManager.getLogManager().readConfiguration(fis);
	    } catch (Exception ex) {
	        // Ignorar si no se encuentra el logger, pero reportarlo
	        //logger.warning(String.format("Error al cargar logger.properties: %s", ex.getMessage()));
	    }
	    
	    // El resto de inicialización, sin usar properties:
	    try {
	        Class.forName(driverName);
	    } catch (Exception ex) {
	        logger.warning(String.format("Error al cargar el driver de BBDD: %s", ex.getMessage()));
	    }
	}
	
	/**
	 * Crear las 4 tablas.
	 */
	public void crearBBDD() {
			
            // Tabla 1: AEROLINEA
			String sql1 = "CREATE TABLE IF NOT EXISTS Aerolinea (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " nombre TEXT UNIQUE NOT NULL);";
	
            // Tabla 2: AVION
			String sql2 = "CREATE TABLE IF NOT EXISTS Avion (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " codigo TEXT UNIQUE NOT NULL,\n"
	                + " nombre TEXT NOT NULL,\n"
	                + " numero_asientos INTEGER NOT NULL);";
	
            // Tabla 3: AEROPUERTO
			String sql3 = "CREATE TABLE IF NOT EXISTS Aeropuerto (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " nombre TEXT UNIQUE NOT NULL);"; 
            
            // Tabla 4: VUELO
            String sql4 = "CREATE TABLE IF NOT EXISTS Vuelo (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " codigo TEXT UNIQUE NOT NULL,\n"
                    + " fecha TEXT NOT NULL,\n"
                    + " duracion REAL,\n"
                    + " precio REAL NOT NULL,\n"

                    + " id_aerolinea INTEGER NOT NULL,\n"
                    + " id_avion INTEGER NOT NULL,\n"
                    + " id_aeropuerto_origen INTEGER NOT NULL,\n"
                    + " id_aeropuerto_destino INTEGER NOT NULL,\n"

                    + " FOREIGN KEY(id_aerolinea) REFERENCES Aerolinea(id) ON DELETE CASCADE,\n"
                    + " FOREIGN KEY(id_avion) REFERENCES Avion(id) ON DELETE CASCADE,\n"
                    + " FOREIGN KEY(id_aeropuerto_origen) REFERENCES Aeropuerto(id) ON DELETE CASCADE,\n"
                    + " FOREIGN KEY(id_aeropuerto_destino) REFERENCES Aeropuerto(id) ON DELETE CASCADE\n"
                    + ");";
            
            String sql5 = "CREATE TABLE IF NOT EXISTS Reserva (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " usuario TEXT NOT NULL,\n"
                    + " ciudad TEXT NOT NULL,\n"
                    + " reserva_nombre TEXT NOT NULL,\n"
                    + " email TEXT NOT NULL,\n"
                    + " tipo_habitacion TEXT NOT NULL,\n"
                    + " adultos INTEGER NOT NULL,\n"
                    + " ninos INTEGER NOT NULL,\n"
                    + " fecha_salida TEXT NOT NULL,\n"
                    + " fecha_regreso TEXT NOT NULL,\n"
                    + " precio REAL NOT NULL);";
			
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5)) {
				
				// Ejecutar las sentencias de creación
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute()) {
		        	logger.info("Se han creado las 5 tablas de la Agencia de Viajes");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}
		
	}
	
	/**
	 * Borra las tablas y el fichero de la BBDD.
	 */
	public void borrarBBDD() {

			String sql1 = "DROP TABLE IF EXISTS Reserva;";
            String sql2 = "DROP TABLE IF EXISTS Vuelo;"; 
			String sql3 = "DROP TABLE IF EXISTS Aerolinea;";
			String sql4 = "DROP TABLE IF EXISTS Avion;";
            String sql5 = "DROP TABLE IF EXISTS Aeropuerto;";
            resetearAutoincrement("Aeropuerto");

	        try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4);
	        	 PreparedStatement pStmt5 = con.prepareStatement(sql5)) {
				
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute()) {
		        	logger.info("Se han borrado las tablas de la Agencia de Viajes");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar las tablas: %s", ex.getMessage()));
			}
			
			try {
				Files.delete(Paths.get(databaseFile));
				logger.info("Se ha borrado el fichero de la BBDD");
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar el fichero de la BBDD: %s", ex.getMessage()));
			}
		
	}
	
	/**
	 * Borra los datos de las 4 tablas (mantiene la estructura de tablas).
	 */
	public void borrarDatos() {
			String sql1 = "DELETE FROM Reserva;";
			String sql2 = "DELETE FROM Vuelo;"; 
			String sql3 = "DELETE FROM Aerolinea;";
			String sql4 = "DELETE FROM Avion;";
            String sql5 = "DELETE FROM Aeropuerto;";
            // Reiniciar los autoincrementos
            resetearAutoincrement("Aeropuerto");
            resetearAutoincrement("Aerolinea");
	        resetearAutoincrement("Avion");
	        resetearAutoincrement("Vuelo");
	        resetearAutoincrement("Reserva");

			
	        // ... (lógica para ejecutar DELETE) ...
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5)) {
				
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute() && !pStmt5.execute()) {
		        	logger.info("Se han borrado los datos de la Agencia de Viajes");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar los datos: %s", ex.getMessage()));
			}
		
	}

    /**
	 * Inserta Aeropuertos en la BBDD.
	 */
	public void insertarAeropuerto(Aeropuertos... aeropuertos) {
		String sql = "INSERT INTO Aeropuerto (nombre) VALUES (?);";
		
		try (Connection con = DriverManager.getConnection(connectionString);

			 PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
									
			for (Aeropuertos a : aeropuertos) {
				pStmt.setString(1, a.getNombre());
				
				if (pStmt.executeUpdate() != 1) {					
					logger.warning(String.format("No se ha insertado el Aeropuerto: %s", a.getNombre()));
				} else {
                    try (ResultSet rs = pStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            // a.setId(rs.getInt(1)); // Descomentar si implementas setId() en Aeropuertos.java
                            logger.info(String.format("Se ha insertado el Aeropuerto con ID %d: %s", rs.getInt(1), a.getNombre()));
                        }
                    }
				}
			}
			logger.info(String.format("%d Aeropuertos insertados en la BBDD", aeropuertos.length));
		} catch (Exception ex) {
			logger.warning(String.format("Error al insertar Aeropuertos: %s", ex.getMessage()));
		}			
	}
    
	public int getAeropuertoIdByNombre(String nombre) {
	    String sql = "SELECT id FROM Aeropuerto WHERE nombre = ?;";
	    
	    try (Connection con = DriverManager.getConnection(connectionString);
	         PreparedStatement pStmt = con.prepareStatement(sql)) {
	        
	        pStmt.setString(1, nombre);
	        
	        try (ResultSet rs = pStmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("id"); // Retorna el ID encontrado
	            }
	        }
	    } catch (Exception ex) {
	        logger.warning(String.format("Error al buscar ID de Aeropuerto %s: %s", nombre, ex.getMessage()));
	    }
	    return -1;
	}
	
    /**
	 * Inserta Vuelos en la BBDD.
	 */
	public void insertarVuelo(int idAerolinea, int idAvion, int idOrigen, int idDestino, String codigo, String fecha, double duracion, double precio) {
		String sql = "INSERT INTO Vuelo (codigo, fecha, duracion, precio, id_aerolinea, id_avion, id_aeropuerto_origen, id_aeropuerto_destino) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection con = DriverManager.getConnection(connectionString);
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
	


	public void cargarDependenciasVueloDesdeCSV(String csvVuelosPath) {
	    logger.info("Iniciando carga de Aerolíneas y Aeropuertos únicos desde CSV de Vuelos.");
	    
	    java.util.Set<String> aerolineasUnicas = new java.util.HashSet<>();
	    java.util.Set<String> aeropuertosUnicos = new java.util.HashSet<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(csvVuelosPath))) {
	        String linea;
	        // Omitir la cabecera (Origen, Destino, Fecha Salida,...)
	        if ((linea = br.readLine()) != null) { 
	            logger.info("Cabecera de vuelos omitida: " + linea);
	        }

	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(",");
	            if (partes.length >= 5) {
	                // Columna 0: Origen (Aeropuerto)
	                String origen = partes[0].trim();
	                // Columna 1: Destino (Aeropuerto)
	                String destino = partes[1].trim();
	                // Columna 4: Aerolinea
	                String aerolinea = partes[4].trim();
	                
	                if (!origen.isEmpty() && !origen.equals("N/A")) aeropuertosUnicos.add(origen);
	                if (!destino.isEmpty() && !destino.equals("N/A")) aeropuertosUnicos.add(destino);
	                if (!aerolinea.isEmpty() && !aerolinea.equals("N/A")) aerolineasUnicas.add(aerolinea); 
	            }
	        }
	    } catch (Exception e) {
	        logger.warning("Error leyendo el CSV de vuelos para dependencias: " + e.getMessage());
	        return;
	    }

	    // Insertar Aeropuertos únicos
	    Aeropuertos[] arrayAeropuertos = aeropuertosUnicos.stream()
	        .map(Aeropuertos::new)
	        .toArray(Aeropuertos[]::new);
	    insertarAeropuerto(arrayAeropuertos); 
	    
	    // Insertar Aerolíneas únicas
	    Aerolinea[] arrayAerolineas = aerolineasUnicas.stream()
	        .map(Aerolinea::new)
	        .toArray(Aerolinea[]::new);
	    insertarAerolinea(arrayAerolineas); 
	    
	    logger.info(String.format("Carga de dependencias finalizada. %d Aerolíneas y %d Aeropuertos procesados.", 
	        aerolineasUnicas.size(), aeropuertosUnicos.size()));
	}
	
	

	public void cargarVuelosDesdeCSV(String csvVuelosPath) {
	    logger.info("Iniciando carga de Vuelos desde CSV.");
	    
	    final String CODIGO_AVION_DEFECTO = "DEF001";
	    int avionesID = getAvionIdByCodigo(CODIGO_AVION_DEFECTO);

	    if (avionesID == -1) {
	        insertarAvion(new Avion(CODIGO_AVION_DEFECTO, "Avion Default para CSV", 200)); 
	        avionesID = getAvionIdByCodigo(CODIGO_AVION_DEFECTO);
	        if (avionesID == -1) {
	            logger.severe("CRÍTICO: No se pudo obtener/crear un ID de Avión válido. Abortando carga de Vuelos.");
	            return;
	        }
	    }

	    int vuelosCargados = 0;
	    try (BufferedReader br = new BufferedReader(new FileReader(csvVuelosPath))) {
	        String linea;
	        br.readLine(); // Omitir la cabecera del CSV de Vuelos

	        while ((linea = br.readLine()) != null) {
	            String[] partes = linea.split(",");
	            if (partes.length < 8) continue; 

	            try {
	                String origenNombre = partes[0].trim();
	                String destinoNombre = partes[1].trim();
	                String fechaSalida = partes[2].trim(); // Formato YYYY-MM-DD en este CSV.
	                String aerolineaNombre = partes[4].trim();
	                
	                String precioStr = partes[5].trim().replace(" EUR", "").replace(".", "").replace(",", ".");
	                double precioEconomy = Double.parseDouble(precioStr);
	                
	                double duracion = 2.0; 

	                int idOrigen = getAeropuertoIdByNombre(origenNombre);
	                int idDestino = getAeropuertoIdByNombre(destinoNombre);
	                int idAerolinea = getAerolineaIdByNombre(aerolineaNombre);
	                
	                // Genera un código de vuelo único usando un contador
	                String codigoVuelo = aerolineaNombre.substring(0, Math.min(aerolineaNombre.length(), 3)).toUpperCase() 
	                                   + origenNombre.substring(0, Math.min(origenNombre.length(), 3)).toUpperCase() 
	                                   + destinoNombre.substring(0, Math.min(destinoNombre.length(), 3)).toUpperCase()
	                                   + fechaSalida.replace("-", "")
	                                   + "-" + (vuelosCargados + 1); // <--- ID INCREMENTAL

	                if (idOrigen != -1 && idDestino != -1 && idAerolinea != -1) {
	                    insertarVuelo(
	                        idAerolinea, avionesID, idOrigen, idDestino,
	                        codigoVuelo, fechaSalida, duracion, precioEconomy
	                    );
	                    vuelosCargados++;
	                } else {
	                    logger.warning(String.format("Fallo: IDs de FK no encontrados para Vuelo con Aerolínea '%s', Origen '%s', Destino '%s'.",
	                        aerolineaNombre, origenNombre, destinoNombre));
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
	    cargarVuelosDesdeCSV(csvVuelosPath);
	    cargarReservasDesdeCSV(csvReservasPath);
	}
    
}