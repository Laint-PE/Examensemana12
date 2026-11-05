
package dao;

import config.ConexionBD;
import models.ClienteModel;
import repositories.ClienteRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements ClienteRepository {

    @Override
    public boolean registrar(ClienteModel cliente) {
        String sql = "INSERT INTO clientes (documento, nombre, telefono) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, cliente.getDocumento());
                consulta.setString(2, cliente.getNombre());
                consulta.setString(3, cliente.getTelefono());

                return consulta.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<ClienteModel> listar() {
        ArrayList<ClienteModel> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombre";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return clientes;

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {
                    clientes.add(convertirCliente(resultado));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    @Override
    public ClienteModel buscarPorDocumento(String documento) {
        String sql = "SELECT * FROM clientes WHERE documento = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return null;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, documento);

                try (ResultSet resultado = consulta.executeQuery()) {
                    if (resultado.next()) {
                        return convertirCliente(resultado);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return null;
    }

    @Override
    public ArrayList<ClienteModel> buscarPorNombre(String nombre) {
        ArrayList<ClienteModel> clientes = new ArrayList<>();

        if (nombre == null || nombre.trim().isEmpty()) return clientes;

        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? ORDER BY nombre";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return clientes;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, "%" + nombre.trim() + "%");

                try (ResultSet resultado = consulta.executeQuery()) {
                    while (resultado.next()) {
                        clientes.add(convertirCliente(resultado));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar clientes: " + e.getMessage());
        }

        return clientes;
    }

    @Override
    public boolean eliminar(String documento) {
        String sql = "DELETE FROM clientes WHERE documento = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, documento);
                return consulta.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    private ClienteModel convertirCliente(ResultSet resultado) throws SQLException {
        return new ClienteModel(
                resultado.getString("documento"),
                resultado.getString("nombre"),
                resultado.getString("telefono")
        );
    }
}
