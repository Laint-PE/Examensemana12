
package services;

import config.ConexionBD;
import dao.AlquilerDAO;
import dao.EquipoDAO;
import models.AlquilerModel;
import models.EquipoModel;
import repositories.AlquilerRepository;
import repositories.EquipoRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final EquipoRepository equipoRepository;

    public AlquilerService() {
        this.alquilerRepository = new AlquilerDAO();
        this.equipoRepository = new EquipoDAO();
    }

    public AlquilerService(AlquilerRepository alquilerRepository,
                           EquipoRepository equipoRepository) {
        this.alquilerRepository = alquilerRepository;
        this.equipoRepository = equipoRepository;
    }

    public boolean registrar(AlquilerModel alquiler) {
        if (alquiler == null || alquiler.getCliente() == null ||
                alquiler.getEquipo() == null || alquiler.getDias() <= 0) {
            return false;
        }

        EquipoModel equipo = alquiler.getEquipo();
        double monto = equipo.calcularMonto(alquiler.getDias());

        if (!Double.isFinite(monto) || monto < 0) {
            return false;
        }

        AlquilerModel nuevoAlquiler = new AlquilerModel(
                0,
                alquiler.getCliente(),
                equipo,
                LocalDate.now(),
                alquiler.getDias(),
                monto,
                false
        );

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            conexion.setAutoCommit(false);

            try {
                boolean ocupado = equipoRepository.ocuparSiDisponible(
                        conexion, equipo.getCodigo()
                );

                if (!ocupado) {
                    conexion.rollback();
                    return false;
                }

                boolean registrado = alquilerRepository.registrar(
                        conexion, nuevoAlquiler
                );

                if (!registrado) {
                    conexion.rollback();
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar alquiler: " + e.getMessage());
            return false;
        }
    }

    public boolean devolver(int id) {
        if (id <= 0) return false;

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            conexion.setAutoCommit(false);

            try {
                int codigoEquipo = alquilerRepository.obtenerCodigoEquipoPendiente(
                        conexion, id
                );

                if (codigoEquipo == -1) {
                    conexion.rollback();
                    return false;
                }

                boolean devuelto = alquilerRepository.marcarDevuelto(
                        conexion, id
                );

                if (!devuelto) {
                    conexion.rollback();
                    return false;
                }

                boolean disponible = equipoRepository.actualizarDisponibilidad(
                        conexion, codigoEquipo, true
                );

                if (!disponible) {
                    conexion.rollback();
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar devolucion: " + e.getMessage());
            return false;
        }
    }
}
