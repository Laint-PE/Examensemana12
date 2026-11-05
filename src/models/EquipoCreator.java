
package models;

import java.math.BigDecimal;

@FunctionalInterface
public interface EquipoCreator {

    EquipoModel crear(int codigo, String descripcion, BigDecimal tarifaDia, boolean disponible);
}
