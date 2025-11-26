package db;

import agencia_prog_3_data.Aerolinea;
import agencia_prog_3_data.Aeropuertos;
import agencia_prog_3_data.Avion;
import agencia_prog_3_data.VueloData;
import db.GestorBD;

import java.util.ArrayList;
import java.util.List;

public class MainBD {

    public static void main(String[] args) {
        // Inicialización del gestor de BD (lee config y carga el driver)
    	GestorBD gestorBD = new GestorBD();		
        
        System.out.println("--- PRUEBA DE GESTOR DE BASES DE DATOS ---");

        // 1. ESTRUCTURA: Borrar y Crear la BBDD (para empezar desde cero)
        gestorBD.borrarBBDD(); // DROP DATABASE y borrado del fichero
        gestorBD.crearBBDD(); // CREATE TABLES

        // 2. INSERCIÓN DE DATOS INICIALES (Tablas Padre: sin dependencias)
        System.out.println("\n--- 2. INSERCIÓN DE DATOS PADRE ---");
        
        // Carga y guarda entidades
        List<Aerolinea> aerolineas = initAerolineas();
//        gestorBD.insertarAerolinea(aerolineas.toArray(new Aerolinea[0]));
        
        List<Aeropuertos> aeropuertos = initAeropuertos();
        gestorBD.insertarAeropuerto(aeropuertos.toArray(new Aeropuertos[0]));
        
        List<Avion> aviones = initAviones();
//        gestorBD.insertarAvion(aviones.toArray(new Avion[0]));
        
        // 3. INSERCIÓN DE DATOS RELACIONALES (Tabla Hija: Vuelo)
        System.out.println("\n--- 3. INSERCIÓN DE VUELOS ---");
        
        // EJEMPLO DE INSERCIÓN DE VUELO (Requiere IDs de las tablas padre)
        
        // A. Obtener IDs (Claves Foráneas) de las entidades recién insertadas:
//        int idIberia = gestorBD.getAerolineaIdByNombre("Iberia");
//        int idBoing747 = gestorBD.getAvionIdByCodigo("B747");
        int idMadrid = gestorBD.getAeropuertoIdByNombre("Aeropuerto de Madrid");
        int idParis = gestorBD.getAeropuertoIdByNombre("Aeropuerto de Paris");
        
        // B. Insertar el Vuelo usando los IDs:
//        if (idIberia != -1 && idBoing747 != -1 && idMadrid != -1 && idParis != -1) {
//            gestorBD.insertarVuelo(
//                idIberia, 
//                idBoing747, 
//                idMadrid, 
//                idParis, 
//                "IB320",       // Codigo
//                "2025-05-15",  // Fecha
//                150.0,         // Duración en minutos
//                250.50         // Precio
//            );
//        }
        
        // 4. LECTURA Y PRUEBA DE CONSULTA CON JOIN
        System.out.println("\n--- 4. LECTURA DE DATOS COMPLETOS (Vuelos) ---");
        
//        List<VueloCompleto> vuelosCompletos = gestorBD.getVuelosCompletosList();
//        printVuelos(vuelosCompletos);

        // 5. UPDATE y DELETE (Ejemplo de mantenimiento)
        System.out.println("\n--- 5. PRUEBA DE BORRADO ---");
        
        // Borrar todos los datos (manteniendo las tablas)
        gestorBD.borrarDatos();
        System.out.println("Datos borrados. Recargando lista de vuelos...");
        
//        vuelosCompletos = gestorBD.getVuelosCompletosList();
//        printVuelos(vuelosCompletos);
        
    }
    
    private static List<Aerolinea> initAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();
        // Nota: Asume que Aerolinea tiene un constructor Aerolinea(String nombre)
        aerolineas.add(new Aerolinea("Iberia"));
        aerolineas.add(new Aerolinea("Air France"));
        return aerolineas;
    }
    
    private static List<Aeropuertos> initAeropuertos() {
        List<Aeropuertos> aeropuertos = new ArrayList<>();
        // Nota: Asume que Aeropuertos tiene un constructor Aeropuertos(String nombre)
        aeropuertos.add(new Aeropuertos("Aeropuerto de Madrid"));
        aeropuertos.add(new Aeropuertos("Aeropuerto de Paris"));
        aeropuertos.add(new Aeropuertos("Aeropuerto de Nueva York"));
        return aeropuertos;
    }

    private static List<Avion> initAviones() {
        List<Avion> aviones = new ArrayList<>();
        // Nota: Asume que Avion tiene un constructor Avion(codigo, nombre, numAsientos)
        aviones.add(new Avion("A320", "Airbus A320", 150));
        aviones.add(new Avion("B747", "Boeing 747", 400));
        return aviones;
    }
    
    private static void printVuelos(List<VueloData> vuelos) {
        if (vuelos.isEmpty()) {
            System.out.println("-> No hay vuelos cargados en la base de datos.");
        } else {
            vuelos.forEach(v -> {
                System.out.format(
                    "\n -> %s: %s (%s) a %s. Precio: %.2f€. Duración: %.0f min.", 
                    v.getCodigo(), 
                    v.getAerolinea().getNombre(),
                    v.getOrigen(),
                    v.getDestino(),
                    v.getPrecio(),
                    v.getDuracionvuelo()
                );
            });
            System.out.println();
        }
    }
}