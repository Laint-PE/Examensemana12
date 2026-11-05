
CREATE DATABASE IF NOT EXISTS equipaya;
USE equipaya;

CREATE TABLE IF NOT EXISTS tipos_equipo (
                                            tipo VARCHAR(30) PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL
    ) ENGINE=InnoDB;

INSERT IGNORE INTO tipos_equipo (tipo, descripcion) VALUES
('CAMARA', 'Equipos de grabacion'),
('AUDIO', 'Equipos de sonido'),
('ILUMINACION', 'Equipos de iluminacion');

CREATE TABLE IF NOT EXISTS clientes (
                                        documento VARCHAR(11) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    CHECK (documento REGEXP '^[0-9]{8}([0-9]{3})?$')
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS equipos (
                                       codigo INT PRIMARY KEY,
                                       tipo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    tarifa_dia DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CHECK (tarifa_dia > 0),
    CONSTRAINT fk_equipo_tipo
    FOREIGN KEY (tipo)
    REFERENCES tipos_equipo(tipo)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS alquileres (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          cliente_documento VARCHAR(11) NOT NULL,
    equipo_codigo INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    dias INT NOT NULL,
    monto_cobrado DECIMAL(10,2) NOT NULL,
    devuelto BOOLEAN NOT NULL DEFAULT FALSE,
    CHECK (dias > 0),
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

INSERT IGNORE INTO clientes (documento, nombre, telefono) VALUES
('12345678', 'Carlos Ramirez', '987654321'),
('87654321', 'Maria Torres', '912345678'),
('20123456789', 'Empresa Audiovisual SAC', '955444333');

INSERT IGNORE INTO equipos
(codigo, tipo, descripcion, tarifa_dia, disponible) VALUES
(101, 'CAMARA', 'Camara profesional Sony', 150.00, TRUE),
(102, 'AUDIO', 'Sistema de sonido profesional', 100.00, TRUE),
(103, 'ILUMINACION', 'Kit de iluminacion LED', 80.00, TRUE);
