# dar formato a un string
nombre = 'Ariel'
edad = 28
mensaje_con_formato = 'Mi nombre %s y tengo %s años'%(nombre, edad)
print(mensaje_con_formato)

# Crear una persona
persona = ('Carla', 'Gomez', 5000.00)
mensaje_con_formato = 'Hola %s %s, tu sueldo es de %.2f'%persona
print(mensaje_con_formato)

nombre = 'Juan'
edad = 19
sueldo = 3000
mensaje_con_formato = 'Nombre {}, Edad {}, Sueldo {:.2f}'.format(nombre, edad, sueldo)
print(mensaje_con_formato)

mensaje_con_formato = 'Nombre {0}, Edad {1}, Sueldo {2:.2f}'.format(nombre, edad, sueldo)
print(mensaje_con_formato)

mensaje_con_formato = 'Nombre {n}, Edad {e}, Sueldo {s:.2f}'.format(n=nombre, e=edad, s=sueldo)
print(mensaje_con_formato)

diccionario = {
    'nombre': 'Ivan',
    'edad': 35,
    'sueldo': 8000.00
}
mensaje_con_formato = 'Nombre {dic[nombre]}, Edad {dic[edad]}, Sueldo {dic[sueldo]:.2f}'.format(dic=diccionario)
print(mensaje_con_formato)