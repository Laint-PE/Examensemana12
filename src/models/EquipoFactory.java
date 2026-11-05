package models;

import java.math.BigDecimal;
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
        if (tipo == null || tipo.trim().isEmpty() || creador == null) {
            throw new IllegalArgumentException("El tipo y el creador son obligatorios.");
        }

        String tipoNormalizado = tipo.trim().toUpperCase(Locale.ROOT);

        if (CREADORES.containsKey(tipoNormalizado)) {
            throw new IllegalArgumentException("El tipo ya se encuentra registrado.");
        }

        CREADORES.put(tipoNormalizado, creador);
    }

    public static ArrayList<String> getTiposRegistrados() {
        return new ArrayList<>(CREADORES.keySet());
    }

    public static EquipoModel crearEquipo(String tipo, int codigo, String descripcion,
                                          BigDecimal tarifaDia, boolean disponible) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de equipo es obligatorio.");
        }

        String tipoNormalizado = tipo.trim().toUpperCase(Locale.ROOT);
        EquipoCreator creador = CREADORES.get(tipoNormalizado);

        if (creador == null) {
            throw new IllegalArgumentException("Tipo de equipo no válido: " + tipo);
        }

        return creador.crear(codigo, descripcion, tarifaDia, disponible);
    }
}