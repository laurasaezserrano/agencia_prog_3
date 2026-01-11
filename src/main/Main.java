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
        
        // Limpieza e inicialización de datos (puedes comentar borrar/crear si ya tienes datos)
        gbd.borrarBBDD();
        gbd.crearBBDD();
        gbd.initilizeFromCSV(); // Asumiendo que mantienes la carga desde CSV
        
        // 2. Carga de datos maestros desde la BD
        // Cargamos los elementos principales que tu agencia necesita mostrar
        List<Vuelo> listaVuelos = gbd.getVuelos();
        List<Hotel> listaHoteles = gbd.getHoteles();
        List<Excursion> listaExcursiones = gbd.getExcursiones();
        List<Aeropuerto> listaAeropuertos = gbd.getAeropuertos();
        
        // 3. Carga de usuarios y sus reservas
        List<User> usuarios = gbd.getUsuarios();
        List<Reserva> reservas = gbd.getReservas();
        
        // 4. Lógica de vinculación (Update)
        // Al igual que con los equipos, aquí podrías necesitar conectar objetos
        // Por ejemplo: asignar los objetos Aeropuerto a los Vuelos si la BD solo trae IDs
        gbd.updateVuelos(listaVuelos, listaAeropuertos);
        gbd.updateReservas(reservas, usuarios, listaVuelos, listaHoteles);

        // 5. Lanzamiento de la Interfaz Gráfica
        // Normalmente a la ventana de inicio le pasas los datos cargados o el gestor
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaComienzoPrev ventana = new VentanaComienzoPrev();
            ventana.setVisible(true);
        });
        
        System.out.println("Proyecto Agencia de Viajes iniciado correctamente.");
    }
}