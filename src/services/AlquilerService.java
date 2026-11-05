
package services;

import config.ConexionBD;
import dao.AlquilerDAO;
import repositories.AlquilerRepository;
import models.AlquilerModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlquilerService {

    private AlquilerRepository alquilerRepository = new AlquilerDAO();

    public boolean registrar(AlquilerModel alquiler) {
        String sql = "UPDATE equipos SET disponible = FALSE WHERE codigo = ? AND disponible = TRUE";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            conexion.setAutoCommit(false);

            try {
                try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                    consulta.setInt(1, alquiler.getEquipo().getCodigo());

                    if (consulta.executeUpdate() != 1) {
                        conexion.rollback();
                        return false;
                    }
                }

                if (!alquilerRepository.registrar(conexion, alquiler)) {
                    conexion.rollback();
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar alquiler: " + e.getMessage());
            return false;
        }
    }

    public boolean devolver(int id) {
        String buscar = "SELECT equipo_codigo, devuelto FROM alquileres WHERE id = ? FOR UPDATE";
        String actualizarEquipo = "UPDATE equipos SET disponible = TRUE WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            conexion.setAutoCommit(false);

            try {
                int codigoEquipo;

                try (PreparedStatement consulta = conexion.prepareStatement(buscar)) {
                    consulta.setInt(1, id);

                    try (ResultSet resultado = consulta.executeQuery()) {
                        if (!resultado.next() || resultado.getBoolean("devuelto")) {
                            conexion.rollback();
                            return false;
                        }

                        codigoEquipo = resultado.getInt("equipo_codigo");
                    }
                }

                if (!alquilerRepository.actualizarDevolucion(conexion, id)) {
                    conexion.rollback();
                    return false;
                }

                try (PreparedStatement consulta = conexion.prepareStatement(actualizarEquipo)) {
                    consulta.setInt(1, codigoEquipo);

                    if (consulta.executeUpdate() != 1) {
                        conexion.rollback();
                        return false;
                    }
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar devolucion: " + e.getMessage());
            return false;
        }
    }
}
