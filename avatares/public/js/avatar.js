// Variables globales de estado del juego
let ataqueJugador
let ataqueEnemigo
let personajeSeleccionado = false
let juegoTerminado = false
let vidasJugador = 3
let vidasEnemigo = 3

// Variables globales de elementos DOM
let spanVidasJugador
let spanVidasEnemigo
let spanPersonajeJugador
let spanPersonajeEnemigo
let sectionSeleccionarPersonaje
let sectionSeleccionarAtaque
let sectionReglas
let sectionMensajes
let botonPersonaje
let botonPunio
let botonPatada
let botonBarrida
let botonReiniciar
let botonVerReglas
let botonCerrarReglas
let radioZuko
let radioKatara
let radioAang
let radioToph

// Constantes del juego
const ATAQUES = {
    PUNIO: 'Puño',
    PATADA: 'Patada',
    BARRIDA: 'Barrida'
}

const PERSONAJES = {
    1: 'Zuko',
    2: 'Katara',
    3: 'Aang',
    4: 'Toph'
}

const MENSAJES = {
    EMPATE: '🤝 ¡EMPATE! Ambos atacaron con ',
    GANASTE_RONDA: '🎉 ¡GANASTE LA RONDA!',
    PERDISTE_RONDA: '💀 ¡PERDISTE LA RONDA!',
    GANASTE_JUEGO: '🏆 ¡HAS GANADO EL JUEGO!',
    PERDISTE_JUEGO: '☠️ ¡HAS PERDIDO EL JUEGO!',
    SELECCIONAR_PERSONAJE: '❗ Por favor selecciona un personaje para jugar.',
    SELECCIONAR_ANTES_ATACAR: '❗❗ Debes seleccionar un personaje antes de atacar.❗❗'
}

function inicializarElementosDOM() {
    // Elementos de interfaz
    sectionSeleccionarPersonaje = document.getElementById("seleccionar-personaje")
    sectionSeleccionarAtaque = document.getElementById("seleccionar-ataque")
    sectionReglas = document.getElementById("reglas-juego")
    sectionMensajes = document.getElementById('mensajes')
    
    // Elementos de información del juego
    spanVidasJugador = document.querySelector('#seleccionar-ataque .vidas:first-of-type')
    spanVidasEnemigo = document.querySelector('#seleccionar-ataque .vidas:last-of-type')
    spanPersonajeJugador = document.getElementById("personaje-jugador")
    spanPersonajeEnemigo = document.getElementById("personaje-enemigo")
    
    // Botones
    botonPersonaje = document.getElementById("boton-personaje")
    botonPunio = document.getElementById('boton-punio')
    botonPatada = document.getElementById('boton-patada')
    botonBarrida = document.getElementById('boton-barrida')
    botonReiniciar = document.getElementById('boton-reiniciar').querySelector('button')
    botonVerReglas = document.getElementById('boton-ver-reglas')
    botonCerrarReglas = document.getElementById('boton-cerrar-reglas')
    
    // Radio buttons
    radioZuko = document.getElementById("zuko")
    radioKatara = document.getElementById("katara")
    radioAang = document.getElementById("aang")
    radioToph = document.getElementById("toph")
}

function iniciarJuego() {
    inicializarElementosDOM()
    
    sectionSeleccionarPersonaje.classList.remove("oculto")
    sectionSeleccionarAtaque.classList.add("oculto")
    sectionReglas.classList.add("oculto")

    // Event listeners
    botonPersonaje.addEventListener("click", seleccionarPersonajeJugador)
    botonPunio.addEventListener('click', () => manejarAtaque(ATAQUES.PUNIO))
    botonPatada.addEventListener('click', () => manejarAtaque(ATAQUES.PATADA))
    botonBarrida.addEventListener('click', () => manejarAtaque(ATAQUES.BARRIDA))
    botonReiniciar.addEventListener('click', reiniciarJuego)
    botonVerReglas.addEventListener('click', mostrarReglas)
    botonCerrarReglas.addEventListener('click', ocultarReglas)

    deshabilitarBotones()
}

function mostrarReglas() {
    sectionReglas.classList.remove('oculto')
}

function ocultarReglas() {
    sectionReglas.classList.add('oculto')
}

function seleccionarPersonajeJugador() {
    let personajeElegido = obtenerPersonajeSeleccionado()
    
    if (!personajeElegido) {
        alert(MENSAJES.SELECCIONAR_PERSONAJE)
        return
    }

    spanPersonajeJugador.innerHTML = personajeElegido
    personajeSeleccionado = true
    seleccionarPersonajeEnemigo()

    // Cambiar pantallas
    sectionSeleccionarPersonaje.classList.add("oculto")
    sectionSeleccionarAtaque.classList.remove("oculto")

    habilitarBotones()
}

function obtenerPersonajeSeleccionado() {
    if (radioZuko.checked) return PERSONAJES[1]
    if (radioKatara.checked) return PERSONAJES[2]
    if (radioAang.checked) return PERSONAJES[3]
    if (radioToph.checked) return PERSONAJES[4]
    return null
}

function seleccionarPersonajeEnemigo() {
    let personajeAleatorio = aleatorio(1, 4)
    spanPersonajeEnemigo.innerHTML = PERSONAJES[personajeAleatorio]
}

function manejarAtaque(tipoAtaque) {
    if (!personajeSeleccionado) {
        alert(MENSAJES.SELECCIONAR_ANTES_ATACAR)
        return
    }
    if (juegoTerminado) return

    ataqueJugador = tipoAtaque
    ataqueAleatorioEnemigo()
}

function ataqueAleatorioEnemigo() {
    let ataqueAleatorio = aleatorio(1, 3)
    ataqueEnemigo = ataqueAleatorio == 1 ? ATAQUES.PUNIO : 
                    ataqueAleatorio == 2 ? ATAQUES.PATADA : 
                    ATAQUES.BARRIDA
    combates()
}

function combates() {
    limpiarMensajes()
    crearMensajeCombate("⚔️ Tú atacaste con: " + ataqueJugador + " | Enemigo atacó con: " + ataqueEnemigo)

    let resultado = determinarResultado()
    
    switch(resultado) {
        case 'empate':
            crearMensajeCombate(MENSAJES.EMPATE + ataqueJugador)
            break
        case 'victoria':
            vidasEnemigo--
            spanVidasEnemigo.innerHTML = vidasEnemigo
            crearMensajeCombate(MENSAJES.GANASTE_RONDA)
            break
        case 'derrota':
            vidasJugador--
            spanVidasJugador.innerHTML = vidasJugador
            crearMensajeCombate(MENSAJES.PERDISTE_RONDA)
            break
    }
    
    crearMensajeCombate("💙 Vidas restantes - Tú: " + vidasJugador + " | Enemigo: " + vidasEnemigo)
    crearMensajeCombate("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
    revisarVidas()
}

function determinarResultado() {
    if (ataqueJugador == ataqueEnemigo) {
        return 'empate'
    }
    
    const combinacionesGanadoras = [
        [ATAQUES.PUNIO, ATAQUES.BARRIDA],
        [ATAQUES.PATADA, ATAQUES.PUNIO],
        [ATAQUES.BARRIDA, ATAQUES.PATADA]
    ]
    
    for (let combinacion of combinacionesGanadoras) {
        if (ataqueJugador == combinacion[0] && ataqueEnemigo == combinacion[1]) {
            return 'victoria'
        }
    }
    
    return 'derrota'
}

function limpiarMensajes() {
    let mensajes = sectionMensajes.querySelectorAll('p')
    if (mensajes.length > 10) {
        for (let i = 0; i < mensajes.length - 5; i++) {
            mensajes[i].remove()
        }
    }
}

function crearMensajeCombate(mensaje) {
    let parrafo = document.createElement('p')
    parrafo.innerHTML = mensaje
    sectionMensajes.appendChild(parrafo)
}

function revisarVidas() {
    if (vidasJugador <= 0 || vidasEnemigo <= 0) {
        juegoTerminado = true
        let mensajeFinal = vidasJugador > 0 ? MENSAJES.GANASTE_JUEGO : MENSAJES.PERDISTE_JUEGO
        crearMensajeCombate(mensajeFinal)
        deshabilitarBotones()
    }
}

function deshabilitarBotones() {
    botonPunio.disabled = true
    botonPatada.disabled = true
    botonBarrida.disabled = true
}

function habilitarBotones() {
    botonPunio.disabled = false
    botonPatada.disabled = false
    botonBarrida.disabled = false
}

function reiniciarJuego() {
    location.reload()
}

function aleatorio(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min)
}

window.addEventListener('load', iniciarJuego)