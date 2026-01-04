package agencia_prog_3_recursividad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agencia_prog_3_GUI.DatosVuelos;

public class GeneradorItinerariosRecursivos {
	
	public static void generarItinerarios(List<DatosVuelos> vuelos, 
            List<List<DatosVuelos>> itinerarios, 
            int numVuelos, 
            double presupuestoMax) {
			// Llamada al método recursivo con listas auxiliares vacías
			generarItinerarios(vuelos, itinerarios, numVuelos, presupuestoMax, new ArrayList<>(), new ArrayList<>());
	}
	
	private static void generarItinerarios(List<DatosVuelos> vuelos,
            List<List<DatosVuelos>> itinerarios,
            int numVuelos,
            double presupuestoMax,
            List<DatosVuelos> itinerarioAux,
            List<Double> preciosAux) {
		
		// CASO BASE: El itinerario tiene el número correcto de vuelos y cumple el presupuesto
		if (itinerarioAux.size() == numVuelos && calcularPrecioTotal(preciosAux) <= presupuestoMax) {
			//Crear nueva lista para evitar que las modificaciones afecten la recursividad
			List<DatosVuelos> nuevoItinerario = new ArrayList<>(itinerarioAux);

			// Ordenar por código de vuelo para evitar duplicados
			Collections.sort(nuevoItinerario, (v1, v2) -> v1.getCodigo().compareTo(v2.getCodigo()));

			// Verificar que no esté repetido
			if (!itinerariosContiene(itinerarios, nuevoItinerario)) {
				itinerarios.add(nuevoItinerario);
			}
			
		} else if (itinerarioAux.size() < numVuelos) {
			
			// CASO RECURSIVO: Seguir construyendo el itinerario
			for (int i = 0; i < vuelos.size(); i++) {
				DatosVuelos vueloActual = vuelos.get(i);

				// Verificar que el vuelo no esté ya en el itinerario
				if (!contieneVuelo(itinerarioAux, vueloActual)) {
					// Poda: Si agregar este vuelo excede el presupuesto, no continuar
					double precioAcumulado = calcularPrecioTotal(preciosAux) + vueloActual.getPrecio();
					if (precioAcumulado <= presupuestoMax) {
						// Agregar vuelo y precio a las listas temporales
						itinerarioAux.add(vueloActual);
						preciosAux.add((double) vueloActual.getPrecio());

						// LLAMADA RECURSIVA
						generarItinerarios(vuelos, itinerarios, numVuelos, presupuestoMax, 
								itinerarioAux, preciosAux);

						// BACKTRACKING: Eliminar el último elemento agregado
						itinerarioAux.remove(itinerarioAux.size() - 1);
						preciosAux.remove(preciosAux.size() - 1);
					}
				}
			}
		}
	}
	
	
	
	/**
     * Calcula el precio total de una lista de precios.
     */
    private static double calcularPrecioTotal(List<Double> precios) {
        double total = 0.0;
        for (Double precio : precios) {
            total += precio;
        }
        return total;
    }
    
    
    /**
     * Verifica si un vuelo ya está en el itinerario (comparando por código).
     */
    private static boolean contieneVuelo(List<DatosVuelos> itinerario, DatosVuelos vuelo) {
        for (DatosVuelos v : itinerario) {
            if (v.getCodigo().equals(vuelo.getCodigo())) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Verifica si un itinerario ya existe en la lista de itinerarios.
     */
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
