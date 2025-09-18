# La Leyenda de Aang: Avatar - Juego de Elementos

Un juego basado en el universo de Avatar: La Leyenda de Aang donde los jugadores pueden seleccionar personajes y combatir usando los cuatro elementos.

## Descripción

Este proyecto es un juego simple basado en navegador donde:
1. El jugador selecciona un personaje (Zuko, Katara, Aang o Toph)
2. El sistema selecciona aleatoriamente un personaje enemigo
3. El jugador puede atacar usando uno de los cuatro elementos (Fuego, Agua, Tierra, Aire)
4. El juego determina el ganador basado en las selecciones

## Estructura del Proyecto

### HTML (avatar.html)

El archivo HTML contiene la estructura de la interfaz del juego:

- **Sección de selección de personaje**: Permite al jugador elegir entre Zuko, Katara, Aang o Toph.
- **Sección de ataque**: Muestra los personajes seleccionados (jugador y enemigo) con sus vidas y botones para seleccionar el elemento de ataque.
- **Sección de mensajes**: Muestra el resultado de cada ataque.
- **Botón de reinicio**: Permite reiniciar el juego.

### JavaScript (avatar.js)

El archivo JavaScript maneja la lógica del juego:

- **iniciarJuego()**: Inicializa el juego y configura los event listeners.
- **seleccionarPersonajeJugador()**: Captura la selección del personaje del jugador y actualiza la interfaz.
- **seleccionarPersonajeEnemigo()**: Selecciona aleatoriamente un personaje enemigo.
- **aleatorio(min, max)**: Función auxiliar para generar números aleatorios.

## Funcionalidades

- Selección de personajes con radio buttons
- Selección aleatoria del personaje enemigo
- Interfaz para seleccionar ataques basados en los elementos
- Sistema de vidas (3 por personaje)
- Mensajes de resultado de combate
- Capacidad para reiniciar el juego

## Cómo jugar

1. Abre el archivo `avatar.html` en un navegador web
2. Selecciona tu personaje y haz clic en "Seleccionar"
3. El juego seleccionará automáticamente un personaje enemigo
4. Usa los botones de elementos para atacar
5. El resultado de cada ataque se mostrará en la sección de mensajes
6. Cuando el juego termine, puedes reiniciarlo con el botón correspondiente 