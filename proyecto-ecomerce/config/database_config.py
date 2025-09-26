"""
Configuración de la base de datos para el proyecto de ecommerce.
Parámetros de conexión a PostgreSQL.

Este módulo contiene las constantes necesarias para establecer
una conexión con la base de datos PostgreSQL del proyecto ecommerce.
"""

# Nombre de la base de datos
BASE_DE_DATOS = 'ecommerce_db'

# Nombre de usuario para la conexión
NOMBRE_USUARIO = 'postgres'

# Contraseña del usuario
CONTRASEÑA = 'Joako2004@'

# Puerto de la base de datos
PUERTO_BD = '5432'

# Dirección del servidor/host
SERVIDOR = '127.0.0.1'

# Número mínimo de conexiones en el pool
MIN_CONEXIONES = 1

# Número máximo de conexiones en el pool
MAX_CONEXIONES = 5