
package repositories;

import models.EquipoModel;
import java.util.ArrayList;

public interface EquipoRepository {
    boolean registrar(EquipoModel equipo);
    ArrayList<EquipoModel> listar();
    EquipoModel buscarPorCodigo(int codigo);
    boolean actualizarDisponibilidad(int codigo, boolean disponible);
    boolean eliminar(int codigo);
}
