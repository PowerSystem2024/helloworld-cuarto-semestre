#!/usr/bin/env python3
"""
Script para inicializar la base de datos del ecommerce.
Este script crea la base de datos si no existe, y luego crea las tablas
necesarias ejecutando el archivo schema.sql.
"""

import os
import psycopg2
from database_config import BASE_DE_DATOS, NOMBRE_USUARIO, CONTRASEÑA, PUERTO_BD, SERVIDOR


def crear_base_datos_si_no_existe():
    """
    Crea la base de datos si no existe.
    Se conecta a la base de datos 'postgres' para crear la nueva base de datos.
    """
    try:
        # Conectar a la base de datos 'postgres' para crear la nueva base de datos
        conexion = psycopg2.connect(
            host=SERVIDOR,
            user=NOMBRE_USUARIO,
            password=CONTRASEÑA,
            port=PUERTO_BD,
            database='postgres'
        )
        conexion.autocommit = True

        with conexion.cursor() as cursor:
            # Verificar si la base de datos ya existe
            cursor.execute("SELECT 1 FROM pg_database WHERE datname = %s", (BASE_DE_DATOS,))
            existe = cursor.fetchone()

            if not existe:
                # Crear la base de datos si no existe
                cursor.execute("CREATE DATABASE %s", (BASE_DE_DATOS,))
                print(f"Base de datos '{BASE_DE_DATOS}' creada exitosamente.")
            else:
                print(f"La base de datos '{BASE_DE_DATOS}' ya existe.")

    except Exception as e:
        print(f"Error al crear/verificar la base de datos: {e}")
        raise
    finally:
        if 'conexion' in locals():
            conexion.close()


def inicializar_base_datos():
    """
    Inicializa la base de datos creando las tablas definidas en schema.sql.

    Primero crea la base de datos si no existe, luego ejecuta el esquema.
    """
    try:
        # Crear la base de datos si no existe
        crear_base_datos_si_no_existe()

        # Conectar a la base de datos del ecommerce
        conexion = psycopg2.connect(
            host=SERVIDOR,
            user=NOMBRE_USUARIO,
            password=CONTRASEÑA,
            port=PUERTO_BD,
            database=BASE_DE_DATOS
        )
        conexion.autocommit = True

        with conexion.cursor() as cursor:
            # Leer y ejecutar schema.sql
            schema_path = os.path.join(os.path.dirname(__file__), 'schema.sql')
            with open(schema_path, 'r', encoding='utf-8') as archivo_esquema:
                sql_esquema = archivo_esquema.read()
                cursor.execute(sql_esquema)
                print("Esquema de la base de datos creado exitosamente.")

    except Exception as e:
        print(f"Error al inicializar la base de datos: {e}")
    finally:
        if 'conexion' in locals():
            conexion.close()


if __name__ == '__main__':
    inicializar_base_datos()