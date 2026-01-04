package agencia_prog_3_recursividad;

import java.util.ArrayList;
import java.util.List;

import agencia_prog_3_GUI.DatosVuelos;
import agencia_prog_3_data.Vuelo;

public class GenerarRutasConectadas {
	
	public static void generarRutasConectadas(List<DatosVuelos> vuelos,
            List<List<DatosVuelos>> rutas,
            int numEscalas,
            String ciudadOrigen,
            String ciudadDestinoFinal) {
		generarRutasConectadas(vuelos, rutas, numEscalas, ciudadOrigen, ciudadDestinoFinal, new ArrayList<>(), ciudadOrigen, 0);
	}
	
	
	private static void generarRutasConectadas(List<DatosVuelos> vuelos,
            List<List<DatosVuelos>> rutas,
            int numEscalas,
            String ciudadOrigen,
            String ciudadDestinoFinal,
            List<DatosVuelos> rutaAux,
            String ciudadActual,
            int escalaActual) {
		// CASO BASE: Hemos llegado al destino final con el número correcto de escalas
		if (escalaActual == numEscalas + 1 && ciudadActual.equals(ciudadDestinoFinal)) {
			List<DatosVuelos> nuevaRuta = new ArrayList<>(rutaAux);
			if (!itinerariosContiene(rutas, nuevaRuta)) {
				rutas.add(nuevaRuta);
			}
			return;
		}

		// CASO RECURSIVO: Buscar vuelos que salgan de la ciudad actual
		if (escalaActual <= numEscalas) {
			for (DatosVuelos vuelo : vuelos) {
				//El vuelo debe salir de la ciudad actual
				if (vuelo.getOrigen().equals(ciudadActual) && !contieneVuelo(rutaAux, vuelo)) {
					// Agregar vuelo a la ruta
					rutaAux.add(vuelo);

					// LLAMADA RECURSIVA con el destino del vuelo como nueva ciudad actual
					generarRutasConectadas(vuelos, rutas, numEscalas, ciudadOrigen, ciudadDestinoFinal, rutaAux, vuelo.getDestino().toString(), escalaActual + 1);

					// BACKTRACKING
					rutaAux.remove(rutaAux.size() - 1);
				}
			}
		}
	}

	
	private static boolean contieneVuelo(List<DatosVuelos> itinerario, DatosVuelos vuelo) {
        for (DatosVuelos v : itinerario) {
            if (v.getCodigo().equals(vuelo.getCodigo())) {
                return true;
            }
        }
        return false;
    }
	
	private static boolean itinerariosContiene(List<List<DatosVuelos>> itinerarios, 
            List<DatosVuelos> itinerario) {
		for (List<DatosVuelos> it : itinerarios) {
			if (it.size() == itinerario.size()) {
				boolean iguales = true;
				for (int i = 0; i < it.size(); i++) {
					if (!it.get(i).getCodigo().equals(itinerario.get(i).getCodigo())) {
						iguales = false;
						break;
					}
				}
				if (iguales) return true;
			}
		}
		return false;
	}
	
	
}
