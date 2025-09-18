import math
from decimal import Decimal

# NaN (Not a Number)
a = float(2.0) # no es case sensitive
print(f'a: {a}')

# Módulo math
a = float('nan')
print(f'¿Es de tipo NaN?: {math.isnan(a)}') # verificar si es de tipo NaN usando el módulo nan

# Módulo Decimal
a = Decimal('NaN') # no es case sensitive
print(f'¿Es de tipo NaN?: {math.isnan(a)}') # verificar si es de tipo NaN usando el módulo nan
