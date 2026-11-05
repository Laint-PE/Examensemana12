
package models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EquipoFactory {

    private static Map<String, EquipoCreator> registros = new HashMap<>();

    static {
        registrarTipo("CAMARA", CamaraModel::new);
        registrarTipo("AUDIO", AudioModel::new);
        registrarTipo("ILUMINACION", IluminacionModel::new);
    }

    public static void registrarTipo(String tipo, EquipoCreator creador) {
        registros.put(tipo.toUpperCase(), creador);
    }

    public static EquipoModel crearEquipo(String tipo, int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible) {
        EquipoCreator creador = registros.get(tipo.toUpperCase());

        if (creador == null) {
            throw new IllegalArgumentException("Tipo de equipo no valido.");
        }

        return creador.crear(codigo, descripcion, tarifaDia, disponible);
    }
}
