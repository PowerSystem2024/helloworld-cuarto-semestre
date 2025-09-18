# Clase base
class Animal:
    def __init__(self, nombre):
        self.nombre = nombre
    
    def hacer_sonido(self):
        return "Hace un sonido"


class Perro(Animal):
    def hacer_sonido(self):
        return f"{self.nombre} dice: ¡Guau!"
    


class Gato(Animal):
    def hacer_sonido(self):
        return f"{self.nombre} dice: ¡Miau!"


class Vaca(Animal):
    def hacer_sonido(self):
        return f"{self.nombre} dice: ¡Muuu!"


animales = [
    Perro("Max"),
    Gato("Michi"),
    Vaca("Lola")
]

print("=== SONIDOS ===")
for animal in animales:
    print(animal.hacer_sonido())
