import os
from constantes import ASSETS_PATH

class Explosion:
    def __init__(self, x, y):
        self.images = [pygame.image.load(os.path.join(ASSETS_PATH, 'images', f'regularExplosion0{i:20}.png')) for i in range(9)]
        self.index = 0 # Índice de la animación
        self.image = self.images[self.index]
        self.rect = self.image.get_rect(center=(x, y))
        self.frame_rate = 0 # Contador de los frames de la animación
        self.max_frames = 20 # Máximo de frames por imagen
        
    def actualizar(self):
        # Actualizar la animación
        self.frame += 1
        if self.frame_rate >= self.max_frames:
            self.index += 1
            if self.index >= len(self.images):
                return False # Terminar la animación
            self.image = self.images[self.index]
            return True
    
    def dibujar(self, screen):
        screen.blit(self.image, self.rect.topleft)