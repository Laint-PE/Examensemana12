package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AlquilerModel {

    private final int id;
    private final ClienteModel cliente;
    private final EquipoModel equipo;
    private final LocalDate fechaInicio;
    private final int dias;
    private final BigDecimal montoCobrado;
    private final boolean devuelto;

    public AlquilerModel(int id, ClienteModel cliente, EquipoModel equipo,
                         LocalDate fechaInicio, int dias, BigDecimal montoCobrado, boolean devuelto) {
        if (id < 0 || cliente == null || equipo == null || fechaInicio == null ||
                dias <= 0 || montoCobrado == null ||
                montoCobrado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Datos de alquiler no válidos.");
        }

        this.id = id;
        this.cliente = cliente;
        this.equipo = equipo;
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.montoCobrado = montoCobrado.setScale(2, java.math.RoundingMode.HALF_UP);
        this.devuelto = devuelto;
    }

    public int getId() { return id; }
    public ClienteModel getCliente() { return cliente; }
    public EquipoModel getEquipo() { return equipo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public int getDias() { return dias; }
    public BigDecimal getMontoCobrado() { return montoCobrado; }
    public boolean isDevuelto() { return devuelto; }
}