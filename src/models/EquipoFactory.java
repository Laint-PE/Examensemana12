package models;

import java.util.HashMap;
import java.util.Map;

public class EquipoFactory {

    private static final Map<String, EquipoCreator> CREADORES = new HashMap<>();

    static {
        registrar("CAMARA", CamaraModel::new);
        registrar("AUDIO", AudioModel::new);
        registrar("ILUMINACION", IluminacionModel::new);
        // Drones: registrar("DRON", DronModel::new);  <- única línea nueva
    }

    public static void registrar(String tipo, EquipoCreator creador) {
        CREADORES.put(tipo.toUpperCase(), creador);
    }

    public static EquipoModel crearEquipo(String tipo, int codigo, String descripcion,
                                          double tarifaDia, boolean disponible) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de equipo es obligatorio.");
        }

        EquipoCreator creador = CREADORES.get(tipo.toUpperCase());

        if (creador == null) {
            throw new IllegalArgumentException("Tipo de equipo no válido: " + tipo);
        }

        return creador.crear(codigo, descripcion, tarifaDia, disponible);
    }
}