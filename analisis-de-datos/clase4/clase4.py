# •Imaginemos un programa que gestiona diferentes tipos de animales utilizando polimorfismo  para representar comportamientos comunes como comer y sonido.

class Animal:
    def hacer_sonido(self):
        pass
    
    def comer(self):
        pass

class Perro(Animal):
    def hacer_sonido(self):
        return 'Guau'
    
    
class Gato(Animal):
    def hacer_sonido(self):
        return 'Miau'
