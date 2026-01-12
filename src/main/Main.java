package main;

import java.util.List;

import db.GestorBD;
import domain.Aeropuerto;
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
            
        System.out.println("Agencia de Viajes iniciada correctamente.");
    }
}
