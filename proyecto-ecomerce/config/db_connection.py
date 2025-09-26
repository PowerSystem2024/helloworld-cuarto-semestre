"""
Módulo para manejar conexiones a la base de datos PostgreSQL.
Utiliza un pool de conexiones para una gestión eficiente de las conexiones.
"""

import psycopg2
from psycopg2 import pool
import sys
from .database_config import BASE_DE_DATOS, NOMBRE_USUARIO, CONTRASEÑA, PUERTO_BD, SERVIDOR, MIN_CONEXIONES, MAX_CONEXIONES


class ConexionBaseDatos:
    """
    Clase para manejar conexiones a la base de datos usando un pool de conexiones.
    Proporciona métodos para obtener, liberar y cerrar conexiones de manera eficiente.
    """
    _pool = None  # Pool de conexiones compartido

    @classmethod
    def obtener_pool(cls):
        """
        Obtiene o crea el pool de conexiones.

        Returns:
            pool.SimpleConnectionPool: El pool de conexiones activo.

        Exits:
            Si ocurre un error al crear el pool, termina el programa.
        """
        if cls._pool is None:
            try:
                cls._pool = pool.SimpleConnectionPool(
                    MIN_CONEXIONES, MAX_CONEXIONES,
                    host=SERVIDOR,
                    user=NOMBRE_USUARIO,
                    password=CONTRASEÑA,
                    port=PUERTO_BD,
                    database=BASE_DE_DATOS
                )
                print(f'Pool de conexiones creado exitosamente: {cls._pool}')
                return cls._pool
            except Exception as e:
                print(f'Error al crear el pool de conexiones: {e}')
                sys.exit()
        else:
            return cls._pool

    @classmethod
    def obtener_conexion(cls):
        """
        Obtiene una conexión del pool.

        Returns:
            psycopg2.connection: Una conexión activa de la base de datos.
        """
        return cls.obtener_pool().getconn()

    @classmethod
    def liberar_conexion(cls, conexion):
        """
        Libera una conexión de vuelta al pool.

        Args:
            conexion: La conexión a liberar.
        """
        cls.obtener_pool().putconn(conexion)

    @classmethod
    def cerrar_todas_conexiones(cls):
        """
        Cierra todas las conexiones del pool.
        """
        if cls._pool:
            cls._pool.closeall()
            print('Todas las conexiones han sido cerradas.')