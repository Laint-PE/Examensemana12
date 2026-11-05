package services;

import config.ConexionBD;
import dao.AlquilerDAO;
import dao.EquipoDAO;
import models.AlquilerModel;
import models.ClienteModel;
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
                alquiler.getCliente().getDocumento() == null ||
                alquiler.getCliente().getDocumento().trim().isEmpty() ||
                alquiler.getEquipo() == null ||
                alquiler.getEquipo().getCodigo() <= 0 ||
                alquiler.getDias() <= 0) {
            return false;
        }

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            conexion.setAutoCommit(false);

            try {
                EquipoModel equipo = equipoRepository.buscarPorCodigoBloqueado(
                        conexion, alquiler.getEquipo().getCodigo()
                );

                if (equipo == null || !equipo.isDisponible()) {
                    revertir(conexion);
                    return false;
                }

                double monto = equipo.calcularMonto(alquiler.getDias());

                if (!Double.isFinite(monto) || monto < 0) {
                    revertir(conexion);
                    return false;
                }

                if (!equipoRepository.ocuparSiDisponible(
                        conexion, equipo.getCodigo())) {
                    revertir(conexion);
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

                if (!alquilerRepository.registrar(conexion, nuevoAlquiler)) {
                    revertir(conexion);
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                revertir(conexion);
                System.out.println("Error al registrar alquiler: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error de conexion al registrar alquiler: " + e.getMessage());
            return false;
        }
    }

    public boolean devolver(int id) {
        if (id <= 0) return false;

        try (Connection conexion = ConexionBD.conectar()) {
            if (conexion == null) return false;

            conexion.setAutoCommit(false);

            try {
                int codigoEquipo =
                        alquilerRepository.obtenerCodigoEquipoPendiente(
                                conexion, id
                        );

                if (codigoEquipo == -1) {
                    revertir(conexion);
                    return false;
                }

                if (!alquilerRepository.marcarDevuelto(conexion, id)) {
                    revertir(conexion);
                    return false;
                }

                if (!equipoRepository.actualizarDisponibilidad(
                        conexion, codigoEquipo, true)) {
                    revertir(conexion);
                    return false;
                }

                conexion.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                revertir(conexion);
                System.out.println("Error al registrar devolucion: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error de conexion al registrar devolucion: " + e.getMessage());
            return false;
        }
    }

    private void revertir(Connection conexion) {
        try {
            conexion.rollback();
        } catch (SQLException e) {
            System.out.println("Error al revertir la transaccion: " + e.getMessage());
        }
    }
}