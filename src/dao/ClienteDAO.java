package dao;

import config.ConexionBD;
import models.ClienteModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    public boolean registrar(ClienteModel cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, telefono) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, cliente.getDni());
                consulta.setString(2, cliente.getNombre());
                consulta.setString(3, cliente.getTelefono());

                return consulta.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ClienteModel> listar() {
        ArrayList<ClienteModel> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return clientes;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {
                    ClienteModel cliente = new ClienteModel(
                            resultado.getInt("dni"),
                            resultado.getString("nombre"),
                            resultado.getString("telefono")
                    );

                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public ClienteModel buscarPorDni(int dni) {
        String sql = "SELECT * FROM clientes WHERE dni = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return null;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, dni);

                try (ResultSet resultado = consulta.executeQuery()) {
                    if (resultado.next()) {
                        return new ClienteModel(
                                resultado.getInt("dni"),
                                resultado.getString("nombre"),
                                resultado.getString("telefono")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return null;
    }

    public boolean eliminar(int dni) {
        String sql = "DELETE FROM clientes WHERE dni = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, dni);

                return consulta.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}