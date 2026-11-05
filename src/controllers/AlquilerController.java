package controllers;

import models.AlquilerModel;
import models.EquipoModel;
import repositories.AlquilerRepository;
import services.AlquilerService;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AlquilerController {

    private final AlquilerRepository alquilerRepository;
    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerRepository alquilerRepository,
                              AlquilerService alquilerService) {
        this.alquilerRepository = alquilerRepository;
        this.alquilerService = alquilerService;
    }

    public BigDecimal calcularMonto(EquipoModel equipo, int dias) {
        if (equipo == null || dias <= 0) {
            return BigDecimal.ZERO;
        }

        return equipo.calcularMonto(dias);
    }

    public boolean registrar(AlquilerModel alquiler) {
        if (alquiler == null || alquiler.getCliente() == null ||
                alquiler.getEquipo() == null || alquiler.getDias() <= 0) {
            return false;
        }

        return alquilerService.registrar(alquiler);
    }

    public boolean devolver(int id) {
        return alquilerService.devolver(id);
    }

    public ArrayList<AlquilerModel> listar() {
        return alquilerRepository.listar();
    }

    public AlquilerModel buscarPorId(int id) {
        if (id <= 0) return null;

        return alquilerRepository.buscarPorId(id);
    }

    public ArrayList<AlquilerModel> listarPendientes() {
        return alquilerRepository.listarPendientes();
    }

    public ArrayList<String> reporteMensual(int mes, int anio) {
        if (mes < 1 || mes > 12 || anio < 1) {
            return new ArrayList<>();
        }

        return alquilerRepository.reporteMensual(mes, anio);
    }
}