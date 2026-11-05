
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

    public boolean registrar(AlquilerModel alquiler) {
        String sql = "INSERT INTO alquileres (cliente_dni, equipo_codigo, dias, monto_cobrado, devuelto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, alquiler.getCliente().getDni());
                consulta.setInt(2, alquiler.getEquipo().getCodigo());
                consulta.setInt(3, alquiler.getDias());
                consulta.setDouble(4, alquiler.getMontoCobrado());
                consulta.setBoolean(5, alquiler.isDevuelto());

                return consulta.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar alquiler: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<AlquilerModel> listar() {
        ArrayList<AlquilerModel> alquileres = new ArrayList<>();

        String sql = "SELECT a.*, c.dni, c.nombre, c.telefono, " +
                "e.codigo, e.descripcion, e.tarifa_dia, e.disponible, e.tipo " +
                "FROM alquileres a " +
                "INNER JOIN clientes c ON a.cliente_dni = c.dni " +
                "INNER JOIN equipos e ON a.equipo_codigo = e.codigo";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return alquileres;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {
                    ClienteModel cliente = new ClienteModel(
                            resultado.getInt("dni"),
                            resultado.getString("nombre"),
                            resultado.getString("telefono")
                    );

                    EquipoModel equipo = EquipoFactory.crearEquipo(
                            resultado.getString("tipo"),
                            resultado.getInt("codigo"),
                            resultado.getString("descripcion"),
                            resultado.getDouble("tarifa_dia"),
                            resultado.getBoolean("disponible")
                    );

                    AlquilerModel alquiler = new AlquilerModel(
                            resultado.getInt("id"),
                            cliente,
                            equipo,
                            resultado.getInt("dias"),
                            resultado.getDouble("monto_cobrado"),
                            resultado.getBoolean("devuelto")
                    );

                    alquileres.add(alquiler);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar alquileres: " + e.getMessage());
        }

        return alquileres;
    }

    public AlquilerModel buscarPorId(int id) {
        for (AlquilerModel alquiler : listar()) {
            if (alquiler.getId() == id) {
                return alquiler;
            }
        }

        return null;
    }

    public boolean actualizarDevolucion(int id, boolean devuelto) {
        String sql = "UPDATE alquileres SET devuelto = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setBoolean(1, devuelto);
                consulta.setInt(2, id);

                return consulta.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar alquiler: " + e.getMessage());
            return false;
        }
    }
}
