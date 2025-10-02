-- Esquema para la base de datos de ecommerce
-- Crear tablas para clientes y productos

-- Crear tabla de clientes
-- Esta tabla almacena la información de los clientes del ecommerce
CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,  -- Identificador único del cliente
    nombre VARCHAR(50) NOT NULL,  -- Nombre del cliente
    apellido VARCHAR(50) NOT NULL,  -- Apellido del cliente
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,  -- Nombre de usuario único
    contraseña VARCHAR(255) NOT NULL  -- Contraseña del cliente
);

-- Crear tabla de productos
-- Esta tabla almacena la información de los productos disponibles
CREATE TABLE IF NOT EXISTS productos (
    id SERIAL PRIMARY KEY,  -- Identificador único del producto
    nombre VARCHAR(100) NOT NULL,  -- Nombre del producto
    descripcion TEXT,  -- Descripción detallada del producto
    precio NUMERIC(10,2) NOT NULL  -- Precio del producto con 2 decimales
);