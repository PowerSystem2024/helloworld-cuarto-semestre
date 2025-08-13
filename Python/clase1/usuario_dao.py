from .conexion import Conexion
from .usuario import Usuario
from .logger_base import log

class UsuarioDAO:
    '''
    DAO significa DataAccess Object
    CRUD significa Create, Read, Update and Delete
    '''
    _SELECCIONAR = 'SELECT * FROM usuario ORDER BY id_usuario'
    _INSERTAR = 'INSERT INTO usuario(id_usuario, username, password) VALUES (%s, %s, %s)'
    _ACTUALIZAR = 'UPDATE usuario SET username = %s, password = %s WHERE id_usuario = %s'
    _ELIMINAR = 'DELETE FROM usuario WHERE id_usuario = %s'

     # metodos de clase
    @classmethod
    def seleccionar(cls):
        with Conexion.obtenerConexion():
            with Conexion.obtenerCursor() as cursor:
                cursor.execute(cls._SELECCIONAR)
                registros = cursor.fetchall()
                usuarios = []
                for registro in registros:
                    usuario = Usuario(registro[0], registro[1], registro[2])
                    usuarios.append(usuario)
                return usuarios

    @classmethod
    def insertar(cls, usuario):
        with Conexion.obtenerConexion():
            with Conexion.obtenerCursor() as cursor:
                valores = (usuario.id_usuario, usuario.username, usuario.password)
                cursor.execute(cls._INSERTAR, valores)
                log.debug(f'Usuario Insertado: {usuario}')
                return cursor.rowcount
    
    @classmethod
    def actualizar(cls, usuario):
        with Conexion.obtenerConexion():
            with Conexion.obtenerCursor() as cursor:
                valores = (usuario.username, usuario.password, usuario.id_usuario)
                cursor.execute(cls._ACTUALIZAR, valores)
                log.debug(f'Usuario Actualizado {usuario}')
                return cursor.rowcount 
        
    @classmethod
    def eliminar(cls, usuario):
        with Conexion.obtenerConexion():
            with Conexion.obtenerCursor() as cursor:
                valores = (usuario.id_usuario, )
                cursor.execute(cls._ELIMINAR, valores)
                log.debug(f'Los objetos eliminados son {usuario}')
                return cursor.rowcount    

if __name__ == '__main__':
    
    usuarios = UsuarioDAO.seleccionar()
    for usuario in usuarios:
        log.debug(usuario)