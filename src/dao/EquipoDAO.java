
package dao;

import config.ConexionBD;
import models.EquipoModel;
import models.EquipoFactory;
import repositories.EquipoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipoDAO implements EquipoRepository {

    @Override
    public boolean registrar(EquipoModel equipo) {
        String sql = "INSERT INTO equipos (codigo, descripcion, tarifa_dia, disponible, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, equipo.getCodigo());
                consulta.setString(2, equipo.getDescripcion());
                consulta.setBigDecimal(3, equipo.getTarifaDia());
                consulta.setBoolean(4, equipo.isDisponible());
                consulta.setString(5, equipo.getTipo());

                return consulta.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar equipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<EquipoModel> listar() {
        ArrayList<EquipoModel> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos ORDER BY codigo";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return equipos;

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {
                    equipos.add(convertirEquipo(resultado));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar equipos: " + e.getMessage());
        }

        return equipos;
    }

    @Override
    public EquipoModel buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM equipos WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return null;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, codigo);

                try (ResultSet resultado = consulta.executeQuery()) {
                    if (resultado.next()) {
                        return convertirEquipo(resultado);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar equipo: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean actualizarDisponibilidad(int codigo, boolean disponible) {
        String sql = "UPDATE equipos SET disponible = ? WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setBoolean(1, disponible);
                consulta.setInt(2, codigo);

                return consulta.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM equipos WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, codigo);

                return consulta.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }

    private EquipoModel convertirEquipo(ResultSet resultado) throws SQLException {
        return EquipoFactory.crearEquipo(
                resultado.getString("tipo"),
                resultado.getInt("codigo"),
                resultado.getString("descripcion"),
                resultado.getBigDecimal("tarifa_dia"),
                resultado.getBoolean("disponible")
        );
    }
}
