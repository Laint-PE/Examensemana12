CREATE DATABASE IF NOT EXISTS equipaya;
USE equipaya;

CREATE TABLE IF NOT EXISTS clientes (
                                        documento VARCHAR(11) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS equipos (
                                       codigo INT PRIMARY KEY,
                                       tipo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    tarifa_dia DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_equipo_tarifa
    CHECK (tarifa_dia > 0)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS alquileres (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          cliente_documento VARCHAR(11) NOT NULL,
    equipo_codigo INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    dias INT NOT NULL,
    monto_cobrado DECIMAL(10,2) NOT NULL,
    devuelto BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT chk_alquiler_dias
    CHECK (dias > 0),

    CONSTRAINT chk_alquiler_monto
    CHECK (monto_cobrado >= 0),

    CONSTRAINT fk_alquiler_cliente
    FOREIGN KEY (cliente_documento)
    REFERENCES clientes(documento)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

    CONSTRAINT fk_alquiler_equipo
    FOREIGN KEY (equipo_codigo)
    REFERENCES equipos(codigo)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
    ) ENGINE=InnoDB;