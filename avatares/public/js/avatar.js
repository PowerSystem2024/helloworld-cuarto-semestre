// Variables globales
let ataqueJugador
let ataqueEnemigo
let personajeSeleccionado = false
let juegoTerminado = false
let vidasJugador = 3
let vidasEnemigo = 3
const ATAQUES = ['Puño', 'Patada', 'Barrida'];


// Elementos del DOM
const btnPunio = document.getElementById('boton-punio');
const btnPatada = document.getElementById('boton-patada');
const btnBarrida = document.getElementById('boton-barrida');
const btnReiniciar = document.getElementById('boton-reiniciar').querySelector('button');
const btnVerReglas = document.getElementById('boton-ver-reglas');
const btnCerrarReglas = document.getElementById('boton-cerrar-reglas');
const btnPersonaje = document.getElementById("boton-personaje");

const seccionSeleccionPersonaje = document.getElementById('seleccionar-personaje');
const seccionSeleccionAtaque = document.getElementById('seleccionar-ataque');
const seccionReglas = document.getElementById('reglas-juego');
const sectionMensajes = document.getElementById('mensajes');

const spanPersonajeJugador = document.getElementById('personaje-jugador');
const spanPersonajeEnemigo = document.getElementById('personaje-enemigo');
let spanVidasJugador = document.querySelector('#seleccionar-ataque .vidas:first-of-type')
let spanVidasEnemigo = document.querySelector('#seleccionar-ataque .vidas:last-of-type')



function iniciarJuego() {
    seccionSeleccionPersonaje.classList.remove("oculto")
    btnPersonaje.addEventListener("click", seleccionarPersonajeJugador)
    btnPunio.addEventListener('click', () => atacar('Puño'));
    btnPatada.addEventListener('click', () => atacar('Patada'));
    btnBarrida.addEventListener('click', () => atacar('Barrida'));
    btnReiniciar.addEventListener('click', reiniciarJuego)
    btnVerReglas.addEventListener('click', mostrarReglas)
    btnCerrarReglas.addEventListener('click', ocultarReglas)
    seccionReglas.classList.add("oculto")
    deshabilitarBotones()
    seccionSeleccionAtaque.classList.add("oculto") // Ocultamos al principio
}



function mostrarReglas() {
    seccionReglas.classList.remove('oculto')
}

function ocultarReglas() {
    seccionReglas.classList.add('oculto')
}

function seleccionarPersonajeJugador() {
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
    seccionSeleccionPersonaje.classList.add("oculto")
    // Y mostramos la selección de ataque
    seccionSeleccionAtaque.classList.remove("oculto")

    habilitarBotones()
}

function seleccionarPersonajeEnemigo() {
    let personajeAleatorio = aleatorio(1, 4)
    spanPersonajeEnemigo.innerHTML = personajeAleatorio == 1 ? "Zuko" : personajeAleatorio == 2 ? "Katara" : personajeAleatorio == 3 ? "Aang" : "Toph"
}

function atacar(tipo) {
    if (!personajeSeleccionado) return alert('❗ Debes seleccionar un personaje antes de atacar.');
    if (juegoTerminado) return;

    ataqueJugador = tipo;
    ataqueAleatorioEnemigo();
}



function ataqueAleatorioEnemigo() {
    ataqueEnemigo = ATAQUES[aleatorio(0, ATAQUES.length - 1)];
    combate();
    combates();
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
    btnPunio.disabled = true
    btnPatada.disabled = true
    btnBarrida.disabled = true
}

function habilitarBotones() {
    btnPunio.disabled = false
    btnPatada.disabled = false
    btnBarrida.disabled = false
}

function reiniciarJuego() {
    location.reload()
}

function aleatorio(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min)
}

window.addEventListener('load', iniciarJuego)
