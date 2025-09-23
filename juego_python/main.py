import pygame
import sys
import random
import os
from personajes import Personaje, Enemigo, Explosion
from constantes import SCREEN_WIDTH, SCREEN_HEIGHT, ASSETS_PATH

def mostrar_imagen_inicial(screen, imagen_path, duracion):
    imagen = pygame.image.load(imagen_path).convert()
    imagen = pygame.transform.scale(imagen, (SCREEN_WIDTH, SCREEN_HEIGHT))
    
    # Bucle para mostrar imagen inicial con desvanecimiento
    alpha = 255
    clock = pygame.time.Clock()
    
    tiempo_inicial = pygame.time.get_ticks()
    tiempo_total = duracion # Duración en milisegundos
    while pygame.time.get_ticks() - tiempo_inicial < tiempo_total:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
        
        # Calcular el tiempo transcurrido
        tiempo_transcurrido = pygame.time.get_ticks() - tiempo_inicial
        
        # Calcular nuevo valor de alpha basado en el tiempo transcurrido
        alpha = 255 - (255 * (tiempo_transcurrido / tiempo_total))
        if alpha < 0:
            alpha = 0
        
        # Establecer transparencia y dibujar la imagen
        imagen.set_alpha(int(alpha))
        screen.fill((0, 0, 0)) # Llenar la pantalla negra antes de dibujar la imagen
        screen.blit(imagen, (0, 0))
        pygame.display.flip()
        
        clock.tick(60) # Mantener a 60 FPS 

def main():
    pygame.init()
    screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
    pygame.display.set_caption('Amenaza Fantasma')
    
    # Definir la ruta dela imagen inicial
    imagen_inicial_path = os.path.join(ASSETS_PATH, 'images', 'inicio', 'star.png')
    
    # Mostrar la imagen inicial durante cinco segundos
    mostrar_imagen_inicial(screen, imagen_inicial_path, 5000)
    
    # Usa os.path.join para construir la ruta del icono
    icon = pygame.image.load(os.path.join(ASSETS_PATH, 'images', '001.jfif'))
    pygame.display.set_icon(icon)
    
    # Inicializar los fondos
    fondo2 = pygame.image.load(os.path.join(ASSETS_PATH, 'images', 'fondo2.jfif'))
    fondo2 = pygame.transform.scale(fondo2, (SCREEN_WIDTH, SCREEN_HEIGHT))
    
    fondo3 = pygame.image.load(os.path.join(ASSETS_PATH, 'images', 'fondo3.jfif'))
    fondo3 = pygame.transform.scale(fondo3, (SCREEN_WIDTH, SCREEN_HEIGHT))
    
    # Inicializar el fondo actual
    fondo_actual = fondo2
    
    # Usa os.path.join para construir las rutas de los sonidos
    sonido_laser = pygame.mixer.Sound(os.path.join(ASSETS_PATH, 'sounds', 'laserdis.mp3'))
    sonido_explosion = pygame.mixer.Sound(os.path.join(ASSETS_PATH, 'sounds', 'explosion.mp3 '))
    
    # Reproducir el sonido de fondo
    pygame.mixer.music.load(os.path.join(ASSETS_PATH, 'sounds', 'efectos.mp3'))
    pygame.mixer.music.play(-1) # Reproduce el sonido en un bucle
    
    personaje = Personaje(SCREEN_WIDTH // 2, SCREEN_HEIGHT // 2)
    enemigos = []
    explosiones = []
    puntos = 0
    nivel = 1
    
    clock = pygame.time.Clock()
    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
    
        # Movimiento del botón de flechas
        keys = pygame.key.get_pressed()
        dx, dy = 0, 0
        
        if keys[pygame.K_LEFT]:
            dx = -5
        if keys[pygame.K_RIGHT]:
            dx = 5
        if keys[pygame.K_UP]:
            dy = -5
        if keys[pygame.K_DOWN]:
            dy = 5
        
        personaje.mover(dx, dy)
        
        if keys[pygame.K_SPACE]:
            personaje.lanzar_laser()
            sonido_laser.play()
        
        # Actualizar posición de enemigos y manejar colisiones
        for enemigo in enemigos[:]: # Iterar sobre una copia para eliminar de la lista original
            enemigo.mover()
            if enemigo.rect.top > SCREEN_HEIGHT:
                enemigos.remove(enemigo)
        
            # Verificar colisiones con láseres
            for laser in personaje.lasers[:]: # Iterar sobre una copia para eliminar de la lista original
                    if enemigo.rect.colliderect(laser.rect):
                        explosiones.append(Explosion(enemigo.rect.centerx, enemigo.rect.centery))
                        enemigos.remove(enemigo) # Eliminar al enemigo
                        personaje.lasers.remove(laser) # Eliminar el láser
                        sonido_explosion.play()
                        puntos += 10 # Incrementar el puntaje
                        break
                    
            if enemigo.rect.colliderect(personaje.shape):
                if not personaje.recibir_dano():
                    running = False # Terminar el juego si la energía llega a cero
            
        # Generar enemigos aleatoriamente        
        if random.random() < 0.02:
            x = random.randint(0, SCREEN_WIDTH - 50) # Asegurarse de que el personaje esté dentro de la pantalla
            enemigo = Enemigo(x, 0)
            enemigos.append(enemigo)