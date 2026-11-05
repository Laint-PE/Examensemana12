
package repositories;

import models.EquipoModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface EquipoRepository {

    boolean registrar(EquipoModel equipo);

    ArrayList<EquipoModel> listar();

    EquipoModel buscarPorCodigo(int codigo);

    boolean actualizarDisponibilidad(int codigo, boolean disponible);

    boolean actualizarDisponibilidad(Connection conexion, int codigo, boolean disponible) throws SQLException;

    boolean ocuparSiDisponible(Connection conexion, int codigo) throws SQLException;

    boolean eliminar(int codigo);
}
