
package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class EquipoFactory {

    private static final Map<String, EquipoCreator> CREADORES = new LinkedHashMap<>();

    static {
        registrar("CAMARA", CamaraModel::new);
        registrar("AUDIO", AudioModel::new);
        registrar("ILUMINACION", IluminacionModel::new);
    }

    public static void registrar(String tipo, EquipoCreator creador) {
        if (tipo == null || creador == null) {
            throw new IllegalArgumentException("El tipo y el creador son obligatorios.");
        }

        CREADORES.put(tipo.toUpperCase(Locale.ROOT), creador);
    }

    public static ArrayList<String> getTiposRegistrados() {
        return new ArrayList<>(CREADORES.keySet());
    }

    public static EquipoModel crearEquipo(String tipo, int codigo, String descripcion,
                                          double tarifaDia, boolean disponible) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de equipo es obligatorio.");
        }

        EquipoCreator creador = CREADORES.get(tipo.toUpperCase(Locale.ROOT));

        if (creador == null) {
            throw new IllegalArgumentException("Tipo de equipo no válido: " + tipo);
        }

        return creador.crear(codigo, descripcion, tarifaDia, disponible);
    }
}
