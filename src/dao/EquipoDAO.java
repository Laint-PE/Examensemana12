package dao;

import config.ConexionBD;
import models.EquipoModel;
import models.EquipoFactory;
import repositories.EquipoRepository;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class EquipoDAO implements EquipoRepository {

    @Override
    public boolean registrar(EquipoModel equipo) {
        if (equipo == null || equipo.getTipo() == null ||
                !EquipoFactory.getTiposRegistrados().contains(
                        equipo.getTipo().trim().toUpperCase(Locale.ROOT))) {
            return false;
        }

        String sql = "INSERT INTO equipos (codigo, tipo, descripcion, tarifa_dia, disponible) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, equipo.getCodigo());
                consulta.setString(2, equipo.getTipo().trim().toUpperCase(Locale.ROOT));
                consulta.setString(3, equipo.getDescripcion());
                consulta.setBigDecimal(4, equipo.getTarifaDia());
                consulta.setBoolean(5, equipo.isDisponible());

                return consulta.executeUpdate() == 1;
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
                    try {
                        equipos.add(convertirEquipo(resultado));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Equipo omitido por tipo no registrado: " +
                                resultado.getString("tipo"));
                    }
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
                        try {
                            return convertirEquipo(resultado);
                        } catch (IllegalArgumentException e) {
                            System.out.println("El equipo tiene un tipo no registrado.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar equipo: " + e.getMessage());
        }

        return null;
    }

    @Override
    public EquipoModel buscarPorCodigoBloqueado(Connection conexion, int codigo)
            throws SQLException {
        String sql = "SELECT * FROM equipos WHERE codigo = ? FOR UPDATE";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, codigo);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return convertirEquipo(resultado);
                }
            }
        }

        return null;
    }

    @Override
    public boolean actualizarDisponibilidad(Connection conexion, int codigo, boolean disponible)
            throws SQLException {
        String sql = "UPDATE equipos SET disponible = ? WHERE codigo = ? AND disponible <> ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setBoolean(1, disponible);
            consulta.setInt(2, codigo);
            consulta.setBoolean(3, disponible);

            return consulta.executeUpdate() == 1;
        }
    }

    @Override
    public boolean ocuparSiDisponible(Connection conexion, int codigo) throws SQLException {
        String sql = "UPDATE equipos SET disponible = FALSE WHERE codigo = ? AND disponible = TRUE";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, codigo);

            return consulta.executeUpdate() == 1;
        }
    }

    @Override
    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM equipos WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, codigo);

                return consulta.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }

    private EquipoModel convertirEquipo(ResultSet resultado) throws SQLException {
        BigDecimal tarifa = resultado.getBigDecimal("tarifa_dia");

        return EquipoFactory.crearEquipo(
                resultado.getString("tipo"),
                resultado.getInt("codigo"),
                resultado.getString("descripcion"),
                tarifa,
                resultado.getBoolean("disponible")
        );
    }
}