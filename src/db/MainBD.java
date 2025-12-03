package db;

import agencia_prog_3_data.Aerolinea;
import agencia_prog_3_data.Aeropuertos;
import agencia_prog_3_data.Avion;
import agencia_prog_3_data.VueloData;

import java.util.ArrayList;
import java.util.List;

public class MainBD {

    public static void main(String[] args) {
        // Inicialización del gestor de BD
    	GestorBD gestorBD = new GestorBD();		
        
        System.out.println("=== PRUEBA DE GESTOR DE BASES DE DATOS ===\n");

        // 1. ESTRUCTURA: Borrar y Crear la BBDD
        System.out.println("--- 1. CREACIÓN DE BBDD ---");
        gestorBD.borrarBBDD();
        gestorBD.crearBBDD();

        // 2. INSERCIÓN DE DATOS INICIALES
        System.out.println("\n--- 2. INSERCIÓN DE DATOS PADRE ---");
        
        List<Aerolinea> aerolineas = initAerolineas();
        gestorBD.insertarAerolinea(aerolineas.toArray(new Aerolinea[0]));
        
        List<Aeropuertos> aeropuertos = initAeropuertos();
        gestorBD.insertarAeropuerto(aeropuertos.toArray(new Aeropuertos[0]));
        
        List<Avion> aviones = initAviones();
        gestorBD.insertarAvion(aviones.toArray(new Avion[0]));
        
        // 3. INSERCIÓN DE VUELOS
        System.out.println("\n--- 3. INSERCIÓN DE VUELOS ---");
        
        int idIberia = gestorBD.getAerolineaIdByNombre("Iberia");
        int idBoing747 = gestorBD.getAvionIdByCodigo("B747");
        int idMadrid = gestorBD.getAeropuertoIdByNombre("Aeropuerto de Madrid");
        int idParis = gestorBD.getAeropuertoIdByNombre("Aeropuerto de Paris");
        
        if (idIberia != -1 && idBoing747 != -1 && idMadrid != -1 && idParis != -1) {
            gestorBD.insertarVuelo(
                idIberia, 
                idBoing747, 
                idMadrid, 
                idParis, 
                "IB320",
                "2025-05-15",
                150.0,
                250.50
            );
        }  
        
        // 4. PRUEBAS CON RESERVAS
        System.out.println("\n--- 4. INSERCIÓN DE RESERVAS ---");
        
        // Insertar algunas reservas de prueba
        gestorBD.insertarReserva(
            "juan.perez",           // usuario
            "Madrid",               // ciudad
            "Hotel Ritz",           // reserva_nombre
            "juan@email.com",       // email
            "Suite",                // tipo_habitacion
            2,                      // adultos
            1,                      // ninos
            "2025-06-01",           // fecha_salida
            "2025-06-10",           // fecha_regreso
            450.00                  // precio
        );
        
        gestorBD.insertarReserva(
            "juan.perez",
            "Barcelona",
            "Hotel Arts",
            "juan@email.com",
            "Doble",
            2,
            0,
            "2025-07-15",
            "2025-07-20",
            380.00
        );
        
        gestorBD.insertarReserva(
            "maria.garcia",
            "Sevilla",
            "Tour Flamenco",
            "maria@email.com",
            "Excursión",
            2,
            0,
            "2025-08-10",
            "2025-08-10",
            75.00
        );
        
        gestorBD.insertarReserva(
            "juan.perez",
            "Valencia",
            "City Tour",
            "juan@email.com",
            "Excursión",
            2,
            1,
            "2025-09-05",
            "2025-09-05",
            90.00
        );
        
        // 5. LECTURA DE RESERVAS
        System.out.println("\n--- 5. LECTURA DE TODAS LAS RESERVAS ---");
        gestorBD.getTodasLasReservas();
        
        // 6. LECTURA DE RESERVAS POR USUARIO
        System.out.println("\n--- 6. LECTURA DE RESERVAS DE JUAN.PEREZ ---");
        List<Object[]> reservasJuan = gestorBD.getReservasPorUsuario("juan.perez");
        System.out.println("Reservas encontradas: " + reservasJuan.size());
        for (Object[] reserva : reservasJuan) {
            System.out.format("  -> %s en %s (Salida: %s, Precio: %.2f€)\n",
                reserva[1], // reserva_nombre
                reserva[0], // ciudad
                reserva[6], // fecha_salida
                (Double)reserva[8] // precio
            );
        }
        
        // 7. PRUEBA DE ELIMINACIÓN
        System.out.println("\n--- 7. PRUEBA DE ELIMINACIÓN ---");
        boolean eliminado = gestorBD.eliminarReserva(
            "juan.perez",
            "Barcelona",
            "Hotel Arts",
            "2025-07-15",
            380.00
        );
        
        if (eliminado) {
            System.out.println("✓ Reserva eliminada correctamente");
            System.out.println("\nReservas restantes de juan.perez:");
            reservasJuan = gestorBD.getReservasPorUsuario("juan.perez");
            for (Object[] reserva : reservasJuan) {
                System.out.format("  -> %s en %s\n", reserva[1], reserva[0]);
            }
        }
        
        // 8. LECTURA DE VUELOS (si hay)
        System.out.println("\n--- 8. LECTURA DE VUELOS ---");
        gestorBD.getVuelosCompletos();
        
        // 9. OPCIONAL: Limpiar todo
        System.out.println("\n--- 9. LIMPIEZA FINAL ---");
        System.out.println("Para borrar todos los datos, descomenta la siguiente línea:");
        gestorBD.borrarDatos();
        
        System.out.println("\n=== FIN DE PRUEBAS ===");
    }
    
    private static List<Aerolinea> initAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();
        aerolineas.add(new Aerolinea("Iberia"));
        aerolineas.add(new Aerolinea("Air France"));
        return aerolineas;
    }
    
    private static List<Aeropuertos> initAeropuertos() {
        List<Aeropuertos> aeropuertos = new ArrayList<>();
        aeropuertos.add(new Aeropuertos("Aeropuerto de Madrid"));
        aeropuertos.add(new Aeropuertos("Aeropuerto de Paris"));
        aeropuertos.add(new Aeropuertos("Aeropuerto de Nueva York"));
        return aeropuertos;
    }

    private static List<Avion> initAviones() {
        List<Avion> aviones = new ArrayList<>();
        aviones.add(new Avion("A320", "Airbus A320", 150));
        aviones.add(new Avion("B747", "Boeing 747", 400));
        return aviones;
    }
    
}