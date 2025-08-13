import logging as log

# configuración básica
log.basicConfig(
    level=log.DEBUG,
    format='%(asctime)s:%(levelname)s [%(filename)s:%(lineno)s] %(message)s',
    datefmt='%I:%M:%S %p',
    handlers=[
        log.FileHandler('/home/joaquin/tecnicatura-utn/segundo-año/programacion-UTN/cuarto-semestre/Python/clase1/app.log'),
        log.StreamHandler()
    ]
)

if __name__ == '__main__':
    log.debug('Mensaje a nivel de debug')
    log.info('Mensaje a nivel de info')
    log.warning('Mensaje a nivel de warning')
    log.error('Mensaje a nivel de error')
    log.critical('Mensaje a nivel crítico')
