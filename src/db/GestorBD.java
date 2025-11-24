package db;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class GestorBD {
	protected static String DRIVER_NAME;
	protected static String DATABASE_FILE;
	protected static String CONNECTION_STRING;
    
	public GestorBD() {		
		try {
			Properties connectionProperties = new Properties();
			
			DRIVER_NAME = connectionProperties.getProperty("DRIVER_NAME");
			DATABASE_FILE = connectionProperties.getProperty("DATABASE_FILE");
			CONNECTION_STRING = connectionProperties.getProperty("CONNECTION_STRING") + DATABASE_FILE;
			
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (Exception ex) {
			System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void crearBBDD() {
		//Se abre la conexión y se obtiene el Statement
		//Al abrir la conexión, si no existía el fichero, se crea la base de datos
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {			
	        String sql = ""; //TODO datos de la primera tabla
	        
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        
	        if (!pstmt.execute()) {
	        	System.out.println("\n- Se ha creado la tabla");
	        }
	        
	        pstmt.close();		
		} catch (Exception ex) {
			System.err.format("\n* Error al crear la BBDD: %s", ex.getMessage());
			ex.printStackTrace();			
		}
	}
	
	public void borrarBBDD() {
		//Se abre la conexión y se obtiene el Statement
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {			
	        
			String sql = "DROP TABLE IF EXISTS "; //TODO
			
	        PreparedStatement pstmt = con.prepareStatement(sql);
			
	        //Se ejecuta la sentencia de creación de la tabla Estudiantes
	        if (!pstmt.execute()) {
	        	System.out.println("\n- Se ha borrado la tabla");
	        }

	        pstmt.close();		
		} catch (Exception ex) {
			System.err.format("\n* Error al borrar la BBDD: %s", ex.getMessage());
			ex.printStackTrace();			
		}
		
		try {
//			//Se borra el fichero de la BBDD
//			Files.delete(Paths.get(DATABASE_FILE));
//			System.out.println("\n- Se ha borrado el fichero de la BBDD");
		} catch (Exception ex) {
			System.err.format("\n* Error al borrar el archivo de la BBDD: %s", ex.getMessage());
			ex.printStackTrace();						
		}
	}
}
