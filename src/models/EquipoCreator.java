package models;

@FunctionalInterface
public interface EquipoCreator {
    EquipoModel crear(int codigo, String descripcion, double tarifaDia, boolean disponible);
}