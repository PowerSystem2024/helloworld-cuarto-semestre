import psycopg2 as bd
from .logger_base import log
import sys

class Conexion:
    _DATABASE = 'test_dos_db'
    _USERNAME = 'postgres'
    _PASSWORD = 'Joako2004@'
    _DB_PORT = '5432'
    _HOST = '127.0.0.1'
    _conexion = None
    
    @classmethod
    def obtenerConexion(cls):
        if cls._conexion is None:
            try:
                cls._conexion = bd.connect(
                    host=cls._HOST,
                    user=cls._USERNAME, 
                    password=cls._PASSWORD, 
                    port=cls._DB_PORT, 
                    database=cls._DATABASE,
                    options="-c client_encoding=utf8"
                )
                log.debug(f'Conexión exitosa: {cls._conexion}')
            except Exception as e:
                log.error(f'Ocurrió un error del tipo: {e}')
                sys.exit()
        return cls._conexion
    
    @classmethod
    def obtenerCursor(cls):
        try:
            # Crear un NUEVO cursor cada vez, no reutilizar
            cursor = cls.obtenerConexion().cursor()
            log.debug(f'Se abrió correctamente el cursor: {cursor}')
            return cursor
        except Exception as e:
            log.error(f'Ocurrió un error: {e}')
            sys.exit()

if __name__ == '__main__':
    Conexion.obtenerConexion()
    cursor = Conexion.obtenerCursor()
    cursor.close()