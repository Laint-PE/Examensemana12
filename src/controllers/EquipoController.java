package controllers;

import models.EquipoModel;
import models.EquipoFactory;
import repositories.EquipoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class EquipoController {

    private final EquipoRepository equipoRepository;

    public EquipoController(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public boolean registrar(EquipoModel equipo) {
        if (equipo == null || equipo.getCodigo() <= 0 ||
                equipo.getTipo() == null ||
                !EquipoFactory.getTiposRegistrados().contains(
                        equipo.getTipo().trim().toUpperCase(Locale.ROOT)) ||
                equipo.getDescripcion() == null ||
                equipo.getDescripcion().trim().isEmpty() ||
                equipo.getTarifaDia() == null ||
                equipo.getTarifaDia().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        return equipoRepository.registrar(equipo);
    }

    public ArrayList<EquipoModel> listar() {
        return equipoRepository.listar();
    }

    public EquipoModel buscarPorCodigo(int codigo) {
        if (codigo <= 0) return null;

        return equipoRepository.buscarPorCodigo(codigo);
    }

    public boolean eliminar(int codigo) {
        if (codigo <= 0) return false;

        return equipoRepository.eliminar(codigo);
    }
}