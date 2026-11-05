
CREATE TABLE IF NOT EXISTS clientes (
                                        documento VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS equipos (
                                       codigo INTEGER PRIMARY KEY,
                                       tipo VARCHAR(20) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    tarifa_dia NUMERIC(10,2) NOT NULL CHECK (tarifa_dia > 0),
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_tipo_equipo
    CHECK (tipo IN ('CAMARA', 'AUDIO', 'ILUMINACION'))
    );

CREATE TABLE IF NOT EXISTS alquileres (
                                          id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                          documento_cliente VARCHAR(20) NOT NULL,
    codigo_equipo INTEGER NOT NULL,
    fecha_inicio DATE NOT NULL,
    dias INTEGER NOT NULL CHECK (dias > 0),
    monto_cobrado NUMERIC(10,2) NOT NULL CHECK (monto_cobrado >= 0),
    devuelto BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_alquiler_cliente
    FOREIGN KEY (documento_cliente)
    REFERENCES clientes(documento),

    CONSTRAINT fk_alquiler_equipo
    FOREIGN KEY (codigo_equipo)
    REFERENCES equipos(codigo)
    );

INSERT INTO clientes (documento, nombre, telefono)
VALUES
    ('12345678', 'Carlos Ramirez', '987654321'),
    ('87654321', 'Maria Torres', '912345678'),
    ('76543210', 'Luis Mendoza', '923456789')
    ON CONFLICT (documento) DO NOTHING;

INSERT INTO equipos (codigo, tipo, descripcion, tarifa_dia, disponible)
VALUES
    (101, 'CAMARA', 'Camara profesional Canon', 150.00, TRUE),
    (102, 'AUDIO', 'Microfono inalambrico', 80.00, TRUE),
    (103, 'ILUMINACION', 'Reflector LED profesional', 100.00, TRUE)
    ON CONFLICT (codigo) DO NOTHING;

INSERT INTO alquileres (
    documento_cliente,
    codigo_equipo,
    fecha_inicio,
    dias,
    monto_cobrado,
    devuelto
)
SELECT '12345678', 101, '2026-11-01', 3, 517.50, TRUE
    WHERE NOT EXISTS (
    SELECT 1 FROM alquileres
    WHERE documento_cliente = '12345678'
      AND codigo_equipo = 101
      AND fecha_inicio = '2026-11-01'
);

INSERT INTO alquileres (
    documento_cliente,
    codigo_equipo,
    fecha_inicio,
    dias,
    monto_cobrado,
    devuelto
)
SELECT '87654321', 102, '2026-11-02', 2, 160.00, TRUE
    WHERE NOT EXISTS (
    SELECT 1 FROM alquileres
    WHERE documento_cliente = '87654321'
      AND codigo_equipo = 102
      AND fecha_inicio = '2026-11-02'
);

INSERT INTO alquileres (
    documento_cliente,
    codigo_equipo,
    fecha_inicio,
    dias,
    monto_cobrado,
    devuelto
)
SELECT '76543210', 103, '2026-11-03', 6, 540.00, FALSE
    WHERE NOT EXISTS (
    SELECT 1 FROM alquileres
    WHERE documento_cliente = '76543210'
      AND codigo_equipo = 103
      AND fecha_inicio = '2026-11-03'
);
