package agencia_prog_3_recursividad;

import java.util.ArrayList;
import java.util.List;

import gui.DatosVuelos;

public class GenerarRutasConectadas {
    
    public static void generarRutasConectadas(List<DatosVuelos> vuelos,
            List<List<DatosVuelos>> rutas,
            int maxEscalas,
            String ciudadOrigen,
            String ciudadDestinoFinal) {
        // Iniciamos la recursividad con una lista de ciudades visitadas para evitar ciclos
        List<String> ciudadesVisitadas = new ArrayList<>();
        ciudadesVisitadas.add(ciudadOrigen);
        
        generarRutasRecursivo(vuelos, rutas, maxEscalas, ciudadDestinoFinal, 
                             new ArrayList<>(), ciudadOrigen, 0, ciudadesVisitadas);
    }
    
    private static void generarRutasRecursivo(List<DatosVuelos> vuelos,
            List<List<DatosVuelos>> rutas,
            int maxEscalas,
            String ciudadDestinoFinal,
            List<DatosVuelos> rutaAux,
            String ciudadOrigen,
            int escalaActual,
            List<String> ciudadesVisitadas) {
        
        // CASO BASE: Si la ciudad actual es el destino, hemos encontrado una ruta válida
        if (ciudadOrigen.equals(ciudadDestinoFinal)) {
            rutas.add(new ArrayList<>(rutaAux));
            return;
        }
        if (escalaActual > maxEscalas) {
            return;
        }

        // CASO RECURSIVO: Buscar vuelos que salgan de la ciudad actual
        for (DatosVuelos vuelo : vuelos) {
            if (vuelo.getOrigen().equals(ciudadOrigen)) {
                if (ciudadesVisitadas.contains(vuelo.getDestino().toString())) continue;
                if (!esVueloTemporalmenteValido(rutaAux, vuelo)) continue;
                // BACKTRACKING
                rutaAux.add(vuelo);
                ciudadesVisitadas.add(vuelo.getDestino().toString());
                generarRutasRecursivo(vuelos, rutas, maxEscalas, ciudadDestinoFinal, 
                                     rutaAux, vuelo.getDestino().toString(), escalaActual + 1, ciudadesVisitadas);
                rutaAux.remove(rutaAux.size() - 1);
                ciudadesVisitadas.remove(ciudadesVisitadas.size() - 1);
            }
        }
    }

    /**
     * Comprueba si el nuevo vuelo sale después de que el último vuelo de la ruta haya aterrizado.
     */
    private static boolean esVueloTemporalmenteValido(List<DatosVuelos> rutaActual, DatosVuelos proximoVuelo) {
        if (rutaActual.isEmpty()) return true; // El primer vuelo siempre es válido temporalmente
        DatosVuelos ultimoVuelo = rutaActual.get(rutaActual.size() - 1);
        // Convertir horas "HH:mm" a minutos totales para comparar fácilmente
        int llegadaUltimo = convertirAMinutos(ultimoVuelo.getHora()) + ultimoVuelo.getDuracionvuelo();
        int salidaProximo = convertirAMinutos(proximoVuelo.getHora());
        // Margen de seguridad: al menos 45 minutos para hacer la escala
        return salidaProximo >= (llegadaUltimo + 45);
    }

    private static int convertirAMinutos(String hora) {
        String[] partes = hora.split(":");
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }
}