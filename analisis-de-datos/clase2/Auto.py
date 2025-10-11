class Auto:
    def __init__(self, marca, color):
        self.marca = marca
        self.color = color
    
    def __str__(self):
        return f'Auto marca: {self.marca}, Color: {self.color}'

auto1 = Auto('Ford', 'Rojo')
auto2 = Auto('Toyota', 'Azul')

print(auto1)
print(auto2)
