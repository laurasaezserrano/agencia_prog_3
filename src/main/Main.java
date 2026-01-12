package main;

import java.util.ArrayList;
import java.util.List;

import db.GestorBD;
import domain.Aerolinea;
import domain.Aeropuerto;
import domain.Avion;
import domain.Excursion;
import domain.Hotel;
import domain.Reserva;
import domain.User;
import domain.Vuelo;

import gui.VentanaComienzoPrev;

public class Main {

    public static void main(String[] args) {
        // 1. Inicialización del Gestor de Base de Datos
        GestorBD gbd = new GestorBD();
        
        gbd.borrarBBDD();
        gbd.crearBBDD();
        gbd.initilizeFromCSV();
        
        // 2. Carga de datos maestros desde la BD
        // Cargamos los elementos principales que tu agencia necesita mostrar
        List<Vuelo> listaVuelos = gbd.getVuelos();
        List<Hotel> listaHoteles = gbd.getHoteles();
        List<Excursion> listaExcursiones = gbd.getExcursiones();
        List<Aeropuerto> listaAeropuertos = gbd.getAeropuertos();
        
        // 3. Carga de usuarios y sus reservas
        List<User> usuarios = gbd.getUsuarios();
        List<Reserva> reservas = gbd.getListaTodasLasReservas();
        
        // 4. Lógica de vinculación (Update)
        // Al igual que con los equipos, aquí podrías necesitar conectar objetos
        gbd.updateVuelos(listaVuelos, listaAeropuertos);
        gbd.updateReservas(reservas, usuarios, listaVuelos, listaHoteles);

        // 5. Lanzamiento de la Interfaz Gráfica
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaComienzoPrev ventana = new VentanaComienzoPrev();
            ventana.setVisible(true);
        });
        
        List<Aerolinea> aerolineas = initAerolineas();
        gbd.insertarAerolinea(aerolineas.toArray(new Aerolinea[0]));
        
        List<Aeropuerto> aeropuertos = initAeropuertos();
        gbd.insertarAeropuerto(aeropuertos.toArray(new Aeropuerto[0]));
        
        List<Avion> aviones = initAviones();
        gbd.insertarAvion(aviones.toArray(new Avion[0]));
        
        int idIberia = gbd.getAerolineaIdByNombre("Iberia");
        int idBoing747 = gbd.getAvionIdByCodigo("B747");
        int idMadrid = gbd.getAeropuertoIdByNombre("Aeropuerto de Madrid");
        int idParis = gbd.getAeropuertoIdByNombre("Aeropuerto de Paris");
        
        if (idIberia != -1 && idBoing747 != -1 && idMadrid != -1 && idParis != -1) {
        	gbd.insertarVuelo(
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
        gbd.insertarReserva(
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
        
        gbd.insertarReserva(
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
        
        gbd.insertarReserva(
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
        
        gbd.insertarReserva(
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
        
        gbd.getTodasLasReservas();
        
        List<Object[]> reservasJuan = gbd.getReservasPorUsuario("juan.perez");
        System.out.println("Reservas encontradas: " + reservasJuan.size());
        for (Object[] reserva : reservasJuan) {
            System.out.format("  -> %s en %s (Salida: %s, Precio: %.2f€)\n",
                reserva[1], // reserva_nombre
                reserva[0], // ciudad
                reserva[6], // fecha_salida
                (Double)reserva[8] // precio
            );
        }
        
        boolean eliminado = gbd.eliminarReserva(
            "juan.perez",
            "Barcelona",
            "Hotel Arts",
            "2025-07-15",
            380.00
        );
        
        if (eliminado) {
            System.out.println("✓ Reserva eliminada correctamente");
            System.out.println("\nReservas restantes de juan.perez:");
            reservasJuan = gbd.getReservasPorUsuario("juan.perez");
            for (Object[] reserva : reservasJuan) {
                System.out.format("  -> %s en %s\n", reserva[1], reserva[0]);
            }
        }
        
        System.out.println("\n--- 8. LECTURA DE VUELOS ---");
        gbd.getVuelosCompletos();
        
        // 9. OPCIONAL: Limpiar todo
        System.out.println("\n--- 9. LIMPIEZA FINAL ---");
        System.out.println("Para borrar todos los datos, descomenta la siguiente línea:");
        gbd.borrarDatos();
   
        gbd.cargarDatosDesdeCSV("resources/data/reservas.csv", "resources/data/vuelosagencia_completo.csv");
        
     
    }
    
    private static List<Aerolinea> initAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();
        aerolineas.add(new Aerolinea("Iberia"));
        aerolineas.add(new Aerolinea("Air France"));
        return aerolineas;
    }
    
    private static List<Aeropuerto> initAeropuertos() {
        List<Aeropuerto> aeropuertos = new ArrayList<>();
        aeropuertos.add(new Aeropuerto("Aeropuerto de Madrid"));
        aeropuertos.add(new Aeropuerto("Aeropuerto de Paris"));
        aeropuertos.add(new Aeropuerto("Aeropuerto de Nueva York"));
        return aeropuertos;
    }

    private static List<Avion> initAviones() {
        List<Avion> aviones = new ArrayList<>();
        aviones.add(new Avion("A320", "Airbus A320", 150));
        aviones.add(new Avion("B747", "Boeing 747", 400));
        return aviones;
    }
    
        System.out.println("Agencia de Viajes iniciada correctamente.");
    }
}