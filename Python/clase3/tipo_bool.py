# Bool contiene los valos True y False
# En los tipos numéricos, el 0 es false, y el 1 es true

valor = 0
resultado = bool(valor)
print(f'Valor: {valor}, Resultado: {resultado}') 

valor = 15
resultado = bool(valor)
print(f'Valor: {valor}, Resultado: {resultado}') # cualquier valor diferente a 0 (+,-) resulta True 

# tipo str
# La cadena vacia representa a False, el resto de cadenas representa True

valor = ''
resultado = bool(valor)
print(f'Valor: {valor}, Resultado: {resultado}') 

valor = "HelloWorld"
resultado = bool(valor)
print(f'Valor: {valor}, Resultado: {resultado}') # cualquier valor diferente a 0 (+,-) resulta True 

# tipo colecciones -> false para vacias, true para el resto (cualquier colección)

valor = []
resultado = bool(valor)
print(f'Valor: {valor}, Resultado: {resultado}') 

valor = (1, 2, 3)
resultado = bool(valor)
print(f'Valor: {valor}, Resultado: {resultado}') # cualquier valor diferente a 0 (+,-) resulta True 

# sentencia de control
if '':
    print('Regresa verdadero')
else:
    print('Regresa falso')
    
# ciclos

variable = 0
while variable:
    print('Regresa verdadero')
else:
    print('Regresa falso')