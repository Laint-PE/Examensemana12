package dao;

import config.ConexionBD;
import models.EquipoModel;
import models.EquipoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipoDAO {

    public boolean registrar(EquipoModel equipo, String tipo) {
        String sql = "INSERT INTO equipos (codigo, descripcion, tarifa_dia, disponible, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, equipo.getCodigo());
            consulta.setString(2, equipo.getDescripcion());
            consulta.setDouble(3, equipo.getTarifaDia());
            consulta.setBoolean(4, equipo.isDisponible());
            consulta.setString(5, tipo);

            consulta.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar equipo: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<EquipoModel> listar() {
        ArrayList<EquipoModel> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {
                EquipoModel equipo = EquipoFactory.crearEquipo(
                        resultado.getString("tipo"),
                        resultado.getInt("codigo"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("tarifa_dia"),
                        resultado.getBoolean("disponible")
                );

                equipos.add(equipo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar equipos: " + e.getMessage());
        }

        return equipos;
    }

    public boolean actualizarDisponibilidad(int codigo, boolean disponible) {
        String sql = "UPDATE equipos SET disponible = ? WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setBoolean(1, disponible);
            consulta.setInt(2, codigo);

            return consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar equipo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM equipos WHERE codigo = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, codigo);

            return consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar equipo: " + e.getMessage());
            return false;
        }
    }
}