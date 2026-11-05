
CREATE DATABASE IF NOT EXISTS equipaya;
USE equipaya;

CREATE TABLE IF NOT EXISTS clientes (
                                        dni INT PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS equipos (
                                       codigo INT PRIMARY KEY,
                                       tipo VARCHAR(20) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    tarifa_dia DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CHECK (tarifa_dia > 0),
    CHECK (tipo IN ('CAMARA', 'AUDIO', 'ILUMINACION'))
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS alquileres (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          cliente_dni INT NOT NULL,
                                          equipo_codigo INT NOT NULL,
                                          dias INT NOT NULL,
                                          monto_cobrado DECIMAL(10,2) NOT NULL,
    devuelto BOOLEAN NOT NULL DEFAULT FALSE,
    CHECK (dias > 0),
    CHECK (monto_cobrado >= 0),
    CONSTRAINT fk_alquiler_cliente
    FOREIGN KEY (cliente_dni) REFERENCES clientes(dni),
    CONSTRAINT fk_alquiler_equipo
    FOREIGN KEY (equipo_codigo) REFERENCES equipos(codigo)
    ) ENGINE=InnoDB;

INSERT IGNORE INTO clientes (dni, nombre, telefono)
VALUES
    (12345678, 'Carlos Ramirez', '987654321'),
    (87654321, 'Maria Torres', '912345678'),
    (76543210, 'Luis Mendoza', '923456789');

INSERT IGNORE INTO equipos (codigo, tipo, descripcion, tarifa_dia, disponible)
VALUES
    (101, 'CAMARA', 'Camara profesional Canon', 150.00, TRUE),
    (102, 'AUDIO', 'Microfono inalambrico', 80.00, TRUE),
    (103, 'ILUMINACION', 'Reflector LED profesional', 100.00, TRUE);

INSERT INTO alquileres (cliente_dni, equipo_codigo, dias, monto_cobrado, devuelto)
SELECT 12345678, 101, 3, 517.50, TRUE
    WHERE NOT EXISTS (
    SELECT 1 FROM alquileres
    WHERE cliente_dni = 12345678
      AND equipo_codigo = 101
      AND dias = 3
);

INSERT INTO alquileres (cliente_dni, equipo_codigo, dias, monto_cobrado, devuelto)
SELECT 87654321, 102, 2, 160.00, TRUE
    WHERE NOT EXISTS (
    SELECT 1 FROM alquileres
    WHERE cliente_dni = 87654321
      AND equipo_codigo = 102
      AND dias = 2
);

INSERT INTO alquileres (cliente_dni, equipo_codigo, dias, monto_cobrado, devuelto)
SELECT 76543210, 103, 6, 540.00, FALSE
    WHERE NOT EXISTS (
    SELECT 1 FROM alquileres
    WHERE cliente_dni = 76543210
      AND equipo_codigo = 103
      AND dias = 6
);
