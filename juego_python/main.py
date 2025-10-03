import pygame
import sys
import random
import os

from personajes import Personaje, Enemigo, Explosion
from constantes import  SCREEN_WITDH, SCREEN_HEIGHT, ASSETS_PATH

def mostrar_imagen_inicial(screen, imagen_path, duracion):
    imagen = pygame.image.load(imagen_path).convert()
    imagen = pygame.transform.scale(imagen, (SCREEN_WITDH, SCREEN_HEIGHT))
    
    # Bucle para mostrar la imagen inicial con desvanecimiento
    alpha = 255 # Transparencia inicial completa 
    clock = pygame.time.Clock()
    
    tiempo_inicial = pygame.time.get_ticks()
    tiempo_total = duracion # Duración en milisegundos
    
    while pygame.time.get_ticks() - tiempo_inicial < tiempo_total:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.QUIT()
                sys.exit()

        # Calcular el tiempo transcurrido
        tiempo_transcurrido = pygame.time.get_ticks() - tiempo_inicial
        
        # Calcular nuevo valor de alpha basado en el tiempo transcurrido
        alpha = 255 - (255 * (tiempo_transcurrido / tiempo_total))
        if alpha < 0:
            alpha = 0
        
        # Establecer transparencia y dibujar la imagen
        imagen.set_alpha(int(alpha))
        screen.fill((0, 0, 0)) # Pintar la pantalla de negro antes de dibujar la imagen
        screen.blit(imagen, (0, 0))
        pygame.display.flip()
        
        clock.tick(60) # Mantener a 60 fps

def main():
    pygame.init()
    screen = pygame.display.set_mode((SCREEN_WITDH, SCREEN_HEIGHT))
    pygame.display.set_caption('Amenaza Fantasma')
    
    # Definir la ruta a la imagen inicial
    imagen_inicial_path = os.path.join(ASSETS_PATH, 'images', 'inicio', 'star.png')
    
    # Mostrar la imagen inicial durante 5 segundos
    mostrar_imagen_inicial(screen, imagen_inicial_path, 5000)
    
    icon = pygame.image.load(os.path.join(ASSETS_PATH, 'images', '001.jfif'))
    pygame.display.set_icon(icon)
    
    # Inicializar los fondos
    fondo2 = pygame.image.load(os.path.join(ASSETS_PATH, 'images', 'fondo2.jfif'))
    fondo2 = pygame.transform.scale(fondo2, (SCREEN_WITDH, SCREEN_HEIGHT))
    
    fondo3 = pygame.image.load(os.path.join(ASSETS_PATH, 'images', 'fondo3.jpg'))
    fondo3 = pygame.transform.scale(fondo3, (SCREEN_WITDH, SCREEN_HEIGHT))
    
    # Inicializar el fondo actual
    fondo_actual = fondo2
    
    sonido_laser = pygame.mixer.Sound(os.path.join(ASSETS_PATH, 'sounds', 'laserdis.mp3'))
    sonido_explosion = pygame.mixer.sound(os.path.join(ASSETS_PATH, 'sounds', 'explosion.mp3'))
    
    # Reproducir el sonido de fondo
    pygame.mixer.musi.load(os.path.join(ASSETS_PATH, 'sounds', 'efectos.mp3'))
    pygame.mixer.music.play(-1)
    
    personaje = Personaje(SCREEN_WITDH // 2, SCREEN_HEIGHT // 2)
    enemigos = []
    explosiones = []
    puntos = 0
    nivel = 1
    
    #Movimiento del botón de flechas
    clock = pygame.time.Clock()
    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.quit()
                
    keys = pygame.key.get_pressed()
    dx, dy = 0, 0
    
    if keys[pygame.K_LEFT]:
        dx -= 5
    if keys[pygame.K_RIGHT]:
        dx = 5
    if keys[pygame.K_UP]:
        dy -= 5
    if keys[pygame.K_DOWN]:
        dy = 5
    
    personaje.mover(dx, dy)
    
    if keys[pygame.K_SPACE]:
        personaje.lanzar_laser()
        sonido_laser.play()
    
    # Actualizat posición de enemigos y manejar colisiones
    for enemigo in enemigos[:]: # Iterar para eliminar la lista principal
        enemigo.mover()
        if enemigo.rect.top > SCREEN_HEIGHT:
            enemigos.remove(enemigo)
    
    # Verificar colisiones del laser
    for laser in personaje.lasers[:]:
        if enemigo.rect.colliderect(laser.react):
            explosiones.append(Explosion(enemigo.rect.centerx, enemigo.rect.centery))
            enemigos.remove(enemigo)
            personaje.lasers.remove(laser)
            sonido_explosion.play()
            puntos += 10
            break
    
    if enemigo.react.colliderect(personaje.shape):
        if not personaje.recibir_dano():
            running = False
    
    # Generar enemigos de forma aleatoria
    if random.randint(1, 10) < 2:
        x = random.randint(0, SCREEN_WITDH - 50)
        enemigo = Enemigo(x, 0)
        enemigos.append(enemigo)
    
    # Actualizar explosiones
    explosion = [explosion for explosion in explosiones if Explosion.actualizar()]
    
    # Actualizar el fondo cada 250 puntos
    if puntos > 0 and puntos % 250 == 0:
        if fondo_actual == fondo2:
            fondo_actual = fondo3
        else:
            fondo_actual = fondo2
            puntos += 10
    
    # Dibujar el fondo y los objetos en la pantalla
    screen.blit(fondo_actual, (0, 0))
    personaje.dibujar(screen)
    for enemigo in enemigos:
        enemigo.dibujar(screen)
    for explosion in explosiones:
        explosion.dibujar(screen)