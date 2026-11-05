
package repositories;

import models.AlquilerModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface AlquilerRepository {
    boolean registrar(Connection conexion, AlquilerModel alquiler) throws SQLException;
    boolean actualizarDevolucion(Connection conexion, int id) throws SQLException;
    ArrayList<AlquilerModel> listar();
    AlquilerModel buscarPorId(int id);
    ArrayList<AlquilerModel> listarPendientes();
    ArrayList<String> reporteMensual(int mes, int anio);
}
