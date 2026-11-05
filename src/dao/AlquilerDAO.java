
package dao;

import config.ConexionBD;
import models.AlquilerModel;
import models.ClienteModel;
import models.EquipoModel;
import models.EquipoFactory;
import repositories.AlquilerRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AlquilerDAO implements AlquilerRepository {

    private String consultaBase =
            "SELECT a.id AS alquiler_id, a.fecha_inicio, a.dias, " +
                    "a.monto_cobrado, a.devuelto, " +
                    "c.documento AS cliente_documento, c.nombre, c.telefono, " +
                    "e.codigo AS equipo_codigo, e.descripcion, e.tarifa_dia, " +
                    "e.disponible, e.tipo " +
                    "FROM alquileres a " +
                    "INNER JOIN clientes c ON a.cliente_documento = c.documento " +
                    "INNER JOIN equipos e ON a.equipo_codigo = e.codigo ";

    @Override
    public boolean registrar(Connection conexion, AlquilerModel alquiler) throws SQLException {
        String sql = "INSERT INTO alquileres (cliente_documento, equipo_codigo, fecha_inicio, dias, monto_cobrado, devuelto) VALUES (?, ?, ?, ?, ?, FALSE)";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setString(1, alquiler.getCliente().getDocumento());
            consulta.setInt(2, alquiler.getEquipo().getCodigo());
            consulta.setDate(3, java.sql.Date.valueOf(alquiler.getFechaInicio()));
            consulta.setInt(4, alquiler.getDias());
            consulta.setBigDecimal(5, alquiler.getMontoCobrado());

            return consulta.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizarDevolucion(Connection conexion, int id) throws SQLException {
        String sql = "UPDATE alquileres SET devuelto = TRUE WHERE id = ? AND devuelto = FALSE";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            return consulta.executeUpdate() > 0;
        }
    }

    @Override
    public ArrayList<AlquilerModel> listar() {
        ArrayList<AlquilerModel> alquileres = new ArrayList<>();
        String sql = consultaBase + "ORDER BY a.fecha_inicio DESC, a.id DESC";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return alquileres;

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {
                    alquileres.add(convertirAlquiler(resultado));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar alquileres: " + e.getMessage());
        }

        return alquileres;
    }

    @Override
    public AlquilerModel buscarPorId(int id) {
        String sql = consultaBase + "WHERE a.id = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return null;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, id);

                try (ResultSet resultado = consulta.executeQuery()) {
                    if (resultado.next()) {
                        return convertirAlquiler(resultado);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar alquiler: " + e.getMessage());
        }

        return null;
    }

    @Override
    public ArrayList<AlquilerModel> listarPendientes() {
        ArrayList<AlquilerModel> alquileres = new ArrayList<>();
        String sql = consultaBase +
                "WHERE a.devuelto = FALSE " +
                "ORDER BY a.fecha_inicio ASC, a.id ASC";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return alquileres;

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {
                    alquileres.add(convertirAlquiler(resultado));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar alquileres pendientes: " + e.getMessage());
        }

        return alquileres;
    }

    @Override
    public ArrayList<String> reporteMensual(int mes, int anio) {
        ArrayList<String> reporte = new ArrayList<>();

        String sql =
                "SELECT e.tipo, SUM(a.monto_cobrado) AS total " +
                        "FROM alquileres a " +
                        "INNER JOIN equipos e ON a.equipo_codigo = e.codigo " +
                        "WHERE a.fecha_inicio >= ? AND a.fecha_inicio < ? " +
                        "GROUP BY e.tipo ORDER BY e.tipo";

        try {
            LocalDate inicio = LocalDate.of(anio, mes, 1);
            LocalDate fin = inicio.plusMonths(1);

            try (Connection conexion = ConexionBD.conectar()) {
                if (conexion == null) return reporte;

                try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                    consulta.setDate(1, java.sql.Date.valueOf(inicio));
                    consulta.setDate(2, java.sql.Date.valueOf(fin));

                    try (ResultSet resultado = consulta.executeQuery()) {
                        while (resultado.next()) {
                            reporte.add(
                                    resultado.getString("tipo") + ": S/ " +
                                            resultado.getBigDecimal("total")
                            );
                        }
                    }
                }
            }
        } catch (DateTimeException e) {
            System.out.println("Fecha del reporte no valida.");
        } catch (SQLException e) {
            System.out.println("Error al generar reporte: " + e.getMessage());
        }

        return reporte;
    }

    private AlquilerModel convertirAlquiler(ResultSet resultado) throws SQLException {
        ClienteModel cliente = new ClienteModel(
                resultado.getString("cliente_documento"),
                resultado.getString("nombre"),
                resultado.getString("telefono")
        );

        EquipoModel equipo = EquipoFactory.crearEquipo(
                resultado.getString("tipo"),
                resultado.getInt("equipo_codigo"),
                resultado.getString("descripcion"),
                resultado.getBigDecimal("tarifa_dia"),
                resultado.getBoolean("disponible")
        );

        return new AlquilerModel(
                resultado.getInt("alquiler_id"),
                cliente,
                equipo,
                resultado.getDate("fecha_inicio").toLocalDate(),
                resultado.getInt("dias"),
                resultado.getBigDecimal("monto_cobrado"),
                resultado.getBoolean("devuelto")
        );
    }
}
