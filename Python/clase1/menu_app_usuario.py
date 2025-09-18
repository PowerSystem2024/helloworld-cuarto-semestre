from .logger_base import log
from .usuario import Usuario
from .usuario_dao import UsuarioDAO

opcion = None

while opcion != 5:
    try:
        print('Opciones:')
        print('1. Listar Usuarios')
        print('2. Agregar Usuario')
        print('3. Modificar Usuario')
        print('4. Eliminar Usuario')
        print('5. Salir')

        opcion = int(input('Seleccione la operación que desee realizar (1-5): '))

        if opcion == 1:
            usuarios = UsuarioDAO.seleccionar()
            for usuario in usuarios:
                log.info(usuario)

        elif opcion == 2:
            try:
                id_usuario_var = int(input('Digite el ID del usuario a agregar: '))
                username_var = input('Digite el nombre del usuario a agregar: ')
                password_var = input('Digite la contraseña del usuario a agregar: ')

                usuario = Usuario(id_usuario=id_usuario_var, username=username_var, password=password_var)
                usuario_insertado = UsuarioDAO.insertar(usuario)
                log.info(f'Usuario Insertado: {usuario_insertado}')

            except ValueError:
                print('El ID debe ser un número válido')

        elif opcion == 3:
            try:
                id_usuario_var = int(input('Digite el ID del usuario a modificar: '))
                username_var = input('Digite el nuevo nombre del usuario: ')
                password_var = input('Digite la nueva contraseña del usuario: ')

                usuario = Usuario(id_usuario=id_usuario_var, username=username_var, password=password_var)
                usuario_actualizado = UsuarioDAO.actualizar(usuario)
                log.info(f'Usuario Actualizado: {usuario_actualizado}')

            except ValueError:
                print('El ID debe ser un número válido')

        elif opcion == 4:
            try:
                id_usuario_var = int(input('Digite el ID del usuario a eliminar: '))
                usuario = Usuario(id_usuario=id_usuario_var)
                usuario_eliminado = UsuarioDAO.eliminar(usuario)
                log.info(f'Usuario Eliminado: {usuario_eliminado}')

            except ValueError:
                print('El ID debe ser un número válido')

        elif opcion == 5:
            log.info('Saliendo de la aplicación')

        else:
            log.info('Opción no disponible')

    except ValueError:
        print('Seleccione un número')
