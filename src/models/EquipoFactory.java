package models;
public class EquipoFactory {

    public static EquipoModel crearEquipo(String tipo, int codigo, String descripcion, double tarifaDia, boolean disponible) {

        switch (tipo.toUpperCase()) {
            case "CAMARA":
                return new CamaraModel(codigo, descripcion, tarifaDia, disponible);

            case "AUDIO":
                return new AudioModel(codigo, descripcion, tarifaDia, disponible);

            case "ILUMINACION":
                return new IluminacionModel(codigo, descripcion, tarifaDia, disponible);

            default:
                throw new IllegalArgumentException("Tipo de equipo no válido.");
        }
    }
}