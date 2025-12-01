package db; 

import java.io.FileInputStream;
import java.io.FileReader;
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

import agencia_prog_3_data.Aerolinea;
import agencia_prog_3_data.Avion;
import agencia_prog_3_data.Aeropuertos;
import agencia_prog_3_data.VueloData;

public class GestorBD {

    // 1. Constantes y configuración
	//private final String PROPERTIES_FILE = "resources/config/app.properties";
	private static Logger logger = Logger.getLogger(GestorBD.class.getName());
	private final String CSV_AEROLINEAS = "resources/data/aerolineas.csv"; 
	
	private String driverName = "org.sqlite.JDBC";
	private String databaseFile = "data/agencia.db"; // Asumiendo este es el valor de "file"
	private String connectionString = "jdbc:sqlite:data/agencia.db"; // Asumiendo este es el valor de "connection"
	// ...

	public GestorBD() {
	    // Si la carga de logger.properties también da problemas, puedes envolver solo esa parte en un try/catch
	    try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
	        LogManager.getLogManager().readConfiguration(fis);
	    } catch (Exception ex) {
	        // Ignorar si no se encuentra el logger, pero reportarlo
	        logger.warning(String.format("Error al cargar logger.properties: %s", ex.getMessage()));
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
			
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4)) {
				
				// Ejecutar las sentencias de creación
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
		        	logger.info("Se han creado las 4 tablas de la Agencia de Viajes");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}
		
	}
	
	/**
	 * Borra las tablas y el fichero de la BBDD.
	 */
	public void borrarBBDD() {

            String sql1 = "DROP TABLE IF EXISTS Vuelo;"; 
			String sql2 = "DROP TABLE IF EXISTS Aerolinea;";
			String sql3 = "DROP TABLE IF EXISTS Avion;";
            String sql4 = "DROP TABLE IF EXISTS Aeropuerto;";
			

	        try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4)) {
				
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
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
			String sql1 = "DELETE FROM Vuelo;"; 
			String sql2 = "DELETE FROM Aerolinea;";
			String sql3 = "DELETE FROM Avion;";
            String sql4 = "DELETE FROM Aeropuerto;";
			
	        // ... (lógica para ejecutar DELETE) ...
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
                 PreparedStatement pStmt4 = con.prepareStatement(sql4)) {
				
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute() && !pStmt4.execute()) {
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
}