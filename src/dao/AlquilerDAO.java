
package dao;

import config.ConexionBD;
import models.AlquilerModel;
import models.ClienteModel;
import models.EquipoModel;
import models.EquipoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlquilerDAO {

    private String consultaBase =
            "SELECT a.id AS alquiler_id, a.fecha_inicio, a.dias, " +
                    "a.monto_cobrado, a.devuelto, " +
                    "c.documento AS cliente_documento, c.nombre, c.telefono, " +
                    "e.codigo AS equipo_codigo, e.descripcion, e.tarifa_dia, " +
                    "e.disponible, e.tipo " +
                    "FROM alquileres a " +
                    "INNER JOIN clientes c ON a.cliente_documento = c.documento " +
                    "INNER JOIN equipos e ON a.equipo_codigo = e.codigo ";

    public boolean registrar(AlquilerModel alquiler) {
        String actualizarEquipo =
                "UPDATE equipos SET disponible = FALSE " +
                        "WHERE codigo = ? AND disponible = TRUE";

        String insertarAlquiler =
                "INSERT INTO alquileres " +
                        "(cliente_documento, equipo_codigo, fecha_inicio, dias, monto_cobrado, devuelto) " +
                        "VALUES (?, ?, ?, ?, ?, FALSE)";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try {
                conexion.setAutoCommit(false);

                try (PreparedStatement actualizar = conexion.prepareStatement(actualizarEquipo)) {
                    actualizar.setInt(1, alquiler.getEquipo().getCodigo());

                    if (actualizar.executeUpdate() == 0) {
                        conexion.rollback();
                        return false;
                    }
                }

                try (PreparedStatement insertar = conexion.prepareStatement(insertarAlquiler)) {
                    insertar.setString(1, alquiler.getCliente().getDocumento());
                    insertar.setInt(2, alquiler.getEquipo().getCodigo());
                    insertar.setDate(3, java.sql.Date.valueOf(alquiler.getFechaInicio()));
                    insertar.setInt(4, alquiler.getDias());
                    insertar.setDouble(5, alquiler.getMontoCobrado());

                    if (insertar.executeUpdate() == 0) {
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
            System.out.println("Error al registrar alquiler: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<AlquilerModel> listar() {
        ArrayList<AlquilerModel> alquileres = new ArrayList<>();
        String sql = consultaBase + "ORDER BY a.fecha_inicio DESC, a.id DESC";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return alquileres;
            }

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

    public AlquilerModel buscarPorId(int id) {
        String sql = consultaBase + "WHERE a.id = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return null;
            }

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

    public boolean actualizarDevolucion(int id, boolean devuelto) {
        String buscar = "SELECT equipo_codigo, devuelto FROM alquileres WHERE id = ? FOR UPDATE";
        String actualizarAlquiler = "UPDATE alquileres SET devuelto = TRUE WHERE id = ?";
        String actualizarEquipo = "UPDATE equipos SET disponible = TRUE WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try {
                conexion.setAutoCommit(false);

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

                if (!devuelto) {
                    conexion.rollback();
                    return false;
                }

                try (PreparedStatement actualizar = conexion.prepareStatement(actualizarAlquiler)) {
                    actualizar.setInt(1, id);

                    if (actualizar.executeUpdate() == 0) {
                        conexion.rollback();
                        return false;
                    }
                }

                try (PreparedStatement actualizar = conexion.prepareStatement(actualizarEquipo)) {
                    actualizar.setInt(1, codigoEquipo);

                    if (actualizar.executeUpdate() == 0) {
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

    public ArrayList<AlquilerModel> listarPendientes() {
        ArrayList<AlquilerModel> alquileres = new ArrayList<>();
        String sql = consultaBase +
                "WHERE a.devuelto = FALSE " +
                "ORDER BY a.fecha_inicio ASC, a.id ASC";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return alquileres;
            }

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

    public ArrayList<String> reporteMensual(int mes, int anio) {
        ArrayList<String> reporte = new ArrayList<>();

        String sql =
                "SELECT e.tipo, SUM(a.monto_cobrado) AS total " +
                        "FROM alquileres a " +
                        "INNER JOIN equipos e ON a.equipo_codigo = e.codigo " +
                        "WHERE MONTH(a.fecha_inicio) = ? AND YEAR(a.fecha_inicio) = ? " +
                        "GROUP BY e.tipo ORDER BY e.tipo";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return reporte;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, mes);
                consulta.setInt(2, anio);

                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        reporte.add(
                                resultado.getString("tipo") + ": S/ " +
                                        resultado.getBigDecimal("total")
                        );
                    }
                }
            }

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
                resultado.getDouble("tarifa_dia"),
                resultado.getBoolean("disponible")
        );

        return new AlquilerModel(
                resultado.getInt("alquiler_id"),
                cliente,
                equipo,
                resultado.getDate("fecha_inicio").toLocalDate(),
                resultado.getInt("dias"),
                resultado.getDouble("monto_cobrado"),
                resultado.getBoolean("devuelto")
        );
    }
}
