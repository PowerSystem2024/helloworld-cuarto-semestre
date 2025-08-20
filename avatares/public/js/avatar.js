// Variables globales
// Variables globales
let ataqueJugador
let ataqueEnemigo
let spanVidasJugador = document.getElementById('vidas-jugador')
let spanVidasEnemigo = document.getElementById('vidas-enemigo')
let personajeSeleccionado = false
let juegoTerminado = false
let vidasJugador = 3
let vidasEnemigo = 3


function iniciarJuego() {
    document.getElementById("seleccionar-personaje").classList.remove("oculto")
    document.getElementById("boton-personaje").addEventListener("click", seleccionarPersonajeJugador)
    document.getElementById('boton-punio').addEventListener('click', ataquePunio)
    document.getElementById('boton-patada').addEventListener('click', ataquePatada)
    document.getElementById('boton-barrida').addEventListener('click', ataqueBarrida)
    document.getElementById('boton-reiniciar').querySelector('button').addEventListener('click', reiniciarJuego)
    document.getElementById('boton-ver-reglas').addEventListener('click', mostrarReglas)
    document.getElementById('boton-cerrar-reglas').addEventListener('click', ocultarReglas)
   document.getElementById("seleccionar-ataque").classList.add("oculto")
   document.getElementById("reglas-juego").classList.add("oculto")

    deshabilitarBotones()
    document.getElementById("seleccionar-ataque").classList.add("oculto") // Ocultamos al principio
}


function mostrarReglas() {
    document.getElementById('reglas-juego').classList.remove('oculto')
}

function ocultarReglas() {
    document.getElementById('reglas-juego').classList.add('oculto')
}

function seleccionarPersonajeJugador() {
    let spanPersonajeJugador = document.getElementById("personaje-jugador")
    if (document.getElementById("zuko").checked) spanPersonajeJugador.innerHTML = "Zuko"
    else if (document.getElementById("katara").checked) spanPersonajeJugador.innerHTML = "Katara"
    else if (document.getElementById("aang").checked) spanPersonajeJugador.innerHTML = "Aang"
    else if (document.getElementById("toph").checked) spanPersonajeJugador.innerHTML = "Toph"
    else {
        alert('❗ Por favor selecciona un personaje para jugar.')
        return
    }

    personajeSeleccionado = true
    seleccionarPersonajeEnemigo()

    // Aquí ocultamos la selección de personaje
    document.getElementById("seleccionar-personaje").classList.add("oculto")
    // Y mostramos la selección de ataque
    document.getElementById("seleccionar-ataque").classList.remove("oculto")

    habilitarBotones()
}

function seleccionarPersonajeEnemigo() {
    let personajeAleatorio = aleatorio(1, 4)
    let spanPersonajeEnemigo = document.getElementById("personaje-enemigo")
    spanPersonajeEnemigo.innerHTML = personajeAleatorio == 1 ? "Zuko" : personajeAleatorio == 2 ? "Katara" : personajeAleatorio == 3 ? "Aang" : "Toph"
}

function ataquePunio() {
    if (!personajeSeleccionado) {
        alert('❗❗ Debes seleccionar un personaje antes de atacar.❗❗')
        return
    }
    if (juegoTerminado) return

    ataqueJugador = 'Puño'
    ataqueAleatorioEnemigo()
}

function ataquePatada() {
    if (!personajeSeleccionado) {
        alert('❗❗ Debes seleccionar un personaje antes de atacar.❗❗')
        return
    }
    if (juegoTerminado) return

    ataqueJugador = 'Patada'
    ataqueAleatorioEnemigo()
}

function ataqueBarrida() {
    if (!personajeSeleccionado) {
        alert('❗ Debes seleccionar un personaje antes de atacar.')
        return
    }
    if (juegoTerminado) return

    ataqueJugador = 'Barrida'
    ataqueAleatorioEnemigo()
}

function ataqueAleatorioEnemigo() {
    let ataqueAleatorio = aleatorio(1, 3)
    ataqueEnemigo = ataqueAleatorio == 1 ? 'Puño' : ataqueAleatorio == 2 ? 'Patada' : 'Barrida'
    combate()
    combates()
}

function combate() {
    if (ataqueEnemigo == ataqueJugador) {
        crearMensaje('Empate')
    } else if ((ataqueJugador == 'Puño' && ataqueEnemigo == 'Barrida') || 
               (ataqueJugador == 'Patada' && ataqueEnemigo == 'Puño') ||
               (ataqueJugador == 'Barrida' && ataqueEnemigo == 'Patada')) {
        crearMensaje('Ganaste')
    } else {
        crearMensaje('Perdiste')
    }
}

function combates() {
    let spanVidasJugador = document.querySelector('#seleccionar-ataque .vidas:first-of-type')
    let spanVidasEnemigo = document.querySelector('#seleccionar-ataque .vidas:last-of-type')
    limpiarMensajes()
    crearMensajeCombate("⚔️ Tú atacaste con: " + ataqueJugador + " | Enemigo atacó con: " + ataqueEnemigo)

    if (ataqueJugador == ataqueEnemigo) {
        crearMensajeCombate("🤝 ¡EMPATE! Ambos atacaron con " + ataqueJugador)
    } else if ((ataqueJugador == 'Puño' && ataqueEnemigo == 'Barrida') ||
               (ataqueJugador == 'Patada' && ataqueEnemigo == 'Puño') ||
               (ataqueJugador == 'Barrida' && ataqueEnemigo == 'Patada')) {
        vidasEnemigo--
        spanVidasEnemigo.innerHTML = vidasEnemigo
        crearMensajeCombate("🎉 ¡GANASTE LA RONDA!")
    } else {
        vidasJugador--
        spanVidasJugador.innerHTML = vidasJugador
        crearMensajeCombate("💀 ¡PERDISTE LA RONDA!")
    }
    crearMensajeCombate("💙 Vidas restantes - Tú: " + vidasJugador + " | Enemigo: " + vidasEnemigo)
    crearMensajeCombate("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
    revisarVidas()
}

function limpiarMensajes() {
    let sectionMensajes = document.getElementById('mensajes')
    let mensajes = sectionMensajes.querySelectorAll('p')
    if (mensajes.length > 10) {
        for (let i = 0; i < mensajes.length - 5; i++) mensajes[i].remove()
    }
}

function crearMensaje(resultado) {
    let sectionMensaje = document.getElementById('mensajes')
    let parrafo = document.createElement('p')
    parrafo.innerHTML = 'Tu personaje atacó con ' + ataqueJugador + ' el personaje del enemigo atacó con ' + ataqueEnemigo + ' ' + resultado
    sectionMensaje.appendChild(parrafo)
}

function crearMensajeCombate(resultado) {
    let sectionMensajes = document.getElementById('mensajes')
    let parrafo = document.createElement('p')
    parrafo.innerHTML = resultado
    sectionMensajes.appendChild(parrafo)
}

function revisarVidas() {
    if (vidasJugador <= 0 || vidasEnemigo <= 0) {
        juegoTerminado = true
        let mensajeFinal = vidasJugador > 0 ? '🏆 ¡HAS GANADO EL JUEGO!' : '☠️ ¡HAS PERDIDO EL JUEGO!'
        crearMensajeCombate(mensajeFinal)
        deshabilitarBotones()
    }
}

function deshabilitarBotones() {
    document.getElementById('boton-punio').disabled = true
    document.getElementById('boton-patada').disabled = true
    document.getElementById('boton-barrida').disabled = true
}

function habilitarBotones() {
    document.getElementById('boton-punio').disabled = false
    document.getElementById('boton-patada').disabled = false
    document.getElementById('boton-barrida').disabled = false
}

function reiniciarJuego() {
    location.reload()
}

function aleatorio(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min)
}

window.addEventListener('load', iniciarJuego)
