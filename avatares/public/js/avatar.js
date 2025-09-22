// Importación de la clase Personaje
import Personaje from './Personaje.js';

// Variables globales de estado del juego
let jugador = null; // Instancia del Personaje elegido por el Jugador
let enemigo = null; // Instancia Aleatoria del Personaje del Enemigo
let ataqueJugador;
let ataqueEnemigo;
let juegoTerminado = false;

// Variables globales de elementos DOM
let spanVidasJugador;
let spanVidasEnemigo;
let spanPersonajeJugador;
let spanPersonajeEnemigo;
let sectionSeleccionarPersonaje;
let sectionSeleccionarAtaque;
let sectionReglas;
let sectionMensajes;
let botonPersonaje;
let botonCrearPersonaje;
let botonReiniciar;
let botonVerReglas;
let botonCerrarReglas;


let crearPersonajeForm;
let inputNuevoNombre;
let inputNuevasVidas;
let botonGuardarPersonaje;
let botonCancelarCreacion;


// Mapeo entre los tipos de ataque con su emoji correspondiente
const ATAQUES = {
    puño: { texto: "Puño", emoji: "👊" },
    patada: { texto: "Patada", emoji: "🦶" },
    barrida: { texto: "Barrida", emoji: "💨" }
};


let personajes_informacion = {
    1: { nombre: 'Zuko', vidas: 3 },
    2: { nombre: 'Katara', vidas: 3 },
    3: { nombre: 'Aang', vidas: 3 },
    4: { nombre: 'Toph', vidas: 3 }
};

let personajes_lista = []; // aquí se guardan las instancias de Personaje


const MENSAJES = {
    EMPATE: '🤝 ¡EMPATE! Ambos atacaron con ',
    GANASTE_RONDA: '🎉 ¡GANASTE LA RONDA!',
    PERDISTE_RONDA: '💀 ¡PERDISTE LA RONDA!',
    GANASTE_JUEGO: '🏆 ¡HAS GANADO EL JUEGO!',
    PERDISTE_JUEGO: '☠️ ¡HAS PERDIDO EL JUEGO!',
    SELECCIONAR_PERSONAJE: '❗ Por favor selecciona un personaje para jugar.',
    SELECCIONAR_ANTES_ATACAR: '❗❗ Debes seleccionar un personaje antes de atacar.❗❗'
};

function inicializarElementosDOM() {
    // Elementos de interfaz
    sectionSeleccionarPersonaje = document.getElementById("seleccionar-personaje");
    sectionSeleccionarAtaque = document.getElementById("seleccionar-ataque");
    sectionReglas = document.getElementById("reglas-juego");
    sectionMensajes = document.getElementById('mensajes');
    
    // Elementos de información del juego
    spanVidasJugador = document.getElementById('vidas-jugador');
    spanVidasEnemigo = document.getElementById('vidas-enemigo');
    spanPersonajeJugador = document.getElementById("personaje-jugador");
    spanPersonajeEnemigo = document.getElementById("personaje-enemigo");
    
    // Botones
    botonPersonaje = document.getElementById("boton-personaje");
    botonCrearPersonaje = document.getElementById("boton-crear-personaje");
    botonReiniciar = document.getElementById('boton-reiniciar').querySelector('button');
    botonVerReglas = document.getElementById('boton-ver-reglas');
    botonCerrarReglas = document.getElementById('boton-cerrar-reglas');
    


    crearPersonajeForm = document.getElementById('crear-personaje-form');
    inputNuevoNombre = document.getElementById('nuevo-nombre');
    inputNuevasVidas = document.getElementById('nuevas-vidas');
    botonGuardarPersonaje = document.getElementById('boton-guardar-personaje');
    botonCancelarCreacion = document.getElementById('boton-cancelar-creacion');

    



}

function iniciarJuego() {
    inicializarElementosDOM();

    // Renderizar personajes por defecto
    renderPersonajes();
    
    sectionSeleccionarPersonaje.classList.remove("oculto");
    sectionSeleccionarAtaque.classList.add("oculto");
    sectionReglas.classList.add("oculto");

    // Event listeners
    botonPersonaje.addEventListener("click", seleccionarPersonajeJugador);
    botonCrearPersonaje.addEventListener('click', mostrarFormularioCrearPersonaje);
    botonGuardarPersonaje.addEventListener('click', guardarNuevoPersonaje);
    botonCancelarCreacion.addEventListener('click', cancelarCreacionPersonaje);
    botonReiniciar.addEventListener('click', reiniciarJuego);
    botonVerReglas.addEventListener('click', mostrarReglas);
    botonCerrarReglas.addEventListener('click', ocultarReglas);

    deshabilitarBotones();
}


function mostrarReglas() {sectionReglas.classList.remove('oculto');}
function ocultarReglas() {sectionReglas.classList.add('oculto');}


function seleccionarPersonajeJugador() {

    let personajeElegido = obtenerPersonajeSeleccionado();
    
    if (!personajeElegido) {
        alert(MENSAJES.SELECCIONAR_PERSONAJE);
        return;
    }

    // Se crea una instancia del personaje elegido
    jugador = new Personaje(personajeElegido.nombre, personajeElegido.vidas, personajeElegido.ataques);
    spanPersonajeJugador.innerHTML = jugador.nombre;
    spanVidasJugador.innerHTML = jugador.vidas;

    // Se crea instancia de enemigo
    seleccionarPersonajeEnemigo();


    // Se renderizan botones de ataque correspondientes al personaje elegido
    renderBotonesAtaque();


    // Cambiar pantallas
    sectionSeleccionarPersonaje.classList.add("oculto");
    sectionSeleccionarAtaque.classList.remove("oculto");

}

function obtenerPersonajeSeleccionado() {
    const radios = document.querySelectorAll('input[name="personaje"]');
    const radioSeleccionado = Array.from(radios).find(radio => radio.checked);
    if (!radioSeleccionado) return null;

    const id = radioSeleccionado.id.replace('personaje-', '');
    return personajes_informacion[Number(id)];
}



function seleccionarPersonajeEnemigo() {
    const llaves = Object.keys(personajes_informacion);
    const indiceAleatorio = aleatorio(0, llaves.length - 1);
    const p = personajes_informacion[llaves[indiceAleatorio]];
    enemigo = new Personaje(p.nombre, p.vidas, p.ataques);
    spanPersonajeEnemigo.innerHTML = enemigo.nombre;
    spanVidasEnemigo.innerHTML = enemigo.vidas;
}


// Renderizar botones de ataque según personaje elegido por jugador
function renderBotonesAtaque() {
    const contenedor = document.querySelector('.botones-ataque');
    contenedor.innerHTML = '';
    
    jugador.ataques.forEach(ataque => {
        const clave = ataque.toLowerCase(); // convierte 'Puño' en 'puño' para buscar en ATAQUES
        const { texto, emoji } = ATAQUES[clave] || { texto: ataque, emoji: "" };
        
        const btn = document.createElement('button');
        btn.innerHTML = texto + ' ' + emoji; // mostrar el ataque con el emoji
        btn.addEventListener('click', () => manejarAtaque(ataque));
        
        contenedor.appendChild(btn);
    });
}


function manejarAtaque(tipoAtaque) {
    if (!jugador) {
        alert(MENSAJES.SELECCIONAR_ANTES_ATACAR);
        return;
    }
    if (juegoTerminado) return;

    ataqueJugador = tipoAtaque;
    ataqueAleatorioEnemigo();
}

function ataqueAleatorioEnemigo() {
    const listaAtaques = enemigo.ataques;
    ataqueEnemigo = listaAtaques[aleatorio(0, listaAtaques.length - 1)];
    combates();
}

function combates() {
    limpiarMensajes();
    
    crearMensajeCombate("⚔️ Tú atacaste con: " + ataqueJugador + " | Enemigo atacó con: " + ataqueEnemigo);

    let resultado = determinarResultado();
    
    switch(resultado) {
        case 'empate':
            crearMensajeCombate(MENSAJES.EMPATE + ataqueJugador);
            break;
        case 'victoria':
            enemigo.recibirDaño();
            spanVidasEnemigo.innerHTML = enemigo.vidas;
            crearMensajeCombate(MENSAJES.GANASTE_RONDA);
            break;
        case 'derrota':
            jugador.recibirDaño();
            spanVidasJugador.innerHTML = jugador.vidas;
            crearMensajeCombate(MENSAJES.PERDISTE_RONDA);
            break;
    }
    
    crearMensajeCombate("💙 Vidas restantes - Tú: " + jugador.vidas + " | Enemigo: " + enemigo.vidas);
    crearMensajeCombate("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

    revisarVidas();
}

// Para nuevos tipos de ataque hay que determinar lógica aqui
function determinarResultado() {
    if (ataqueJugador === ataqueEnemigo) {
        return 'empate';
    }
    
    const combinacionesGanadoras = [
        [jugador.ataques[0], jugador.ataques[2]], // Puño gana a Barrida
        [jugador.ataques[1], jugador.ataques[0]], // Patada gana a Puño
        [jugador.ataques[2], jugador.ataques[1]]  // Barrida gana a Patada
    ];
    
    for (let [gana, pierde] of combinacionesGanadoras) {
        if (ataqueJugador === gana && ataqueEnemigo === pierde) return 'victoria';
    }
    return 'derrota';
}

function limpiarMensajes() {
    let mensajes = sectionMensajes.querySelectorAll('p')
    if (mensajes.length > 10) {
        for (let i = 0; i < mensajes.length - 5; i++) {
            mensajes[i].remove();
        }
    }
}

function crearMensajeCombate(mensaje) {
    let parrafo = document.createElement('p');
    parrafo.innerHTML = mensaje;
    sectionMensajes.appendChild(parrafo);
}

function revisarVidas() {
    if (!jugador.estaVivo() || !enemigo.estaVivo()) {
        juegoTerminado = true;
        let mensajeFinal = jugador.estaVivo() ? MENSAJES.GANASTE_JUEGO : MENSAJES.PERDISTE_JUEGO;
        crearMensajeCombate(mensajeFinal);
        deshabilitarBotones();
    }
}

function deshabilitarBotones() {
    const botones = document.querySelectorAll('.botones-ataque button');
    botones.forEach(btn => btn.disabled = true);
}

//function habilitarBotones() {
//    const botones = document.querySelectorAll('.botones-ataque button');
//    botones.forEach(btn => btn.disabled = false);
//}


function guardarNuevoPersonaje() {
    let nombre = inputNuevoNombre.value.trim();
    let vidas = parseInt(inputNuevasVidas.value, 10);

    if (!nombre) { 
        alert("Ingresa un nombre válido"); 
        return; 
    }

    if (isNaN(vidas) || vidas <= 0) { 
        vidas = 3; // Por defecto
    }

    // Crear ID nuevo
    const nuevaId = Object.keys(personajes_informacion).length + 1;
    personajes_informacion[nuevaId] = { nombre, vidas };

    // Volver a renderizar la lista completa
    renderPersonajes();

    // Limpiar formulario y regresar a selección
    cancelarCreacionPersonaje();
}


function mostrarFormularioCrearPersonaje() {
    document.getElementById('vista-personajes').classList.add('oculto');
    document.getElementById('vista-crear-personaje').classList.remove('oculto');
}

function cancelarCreacionPersonaje() {
    document.getElementById('vista-crear-personaje').classList.add('oculto');
    document.getElementById('vista-personajes').classList.remove('oculto');
    inputNuevoNombre.value = '';
    inputNuevasVidas.value = '';
}



function renderPersonajes() {
    const contenedor = document.getElementById('personajes-lista');
    contenedor.innerHTML = ''; // limpiar antes de generar

    Object.keys(personajes_informacion).forEach(id => {
        const personaje = personajes_informacion[id];

        const div = document.createElement('div');
        div.classList.add('personaje-option');

        const input = document.createElement('input');
        input.type = 'radio';
        input.name = 'personaje';
        input.id = `personaje-${id}`; // id único

        const label = document.createElement('label');
        label.htmlFor = `personaje-${id}`;
        label.textContent = personaje.nombre;

        div.appendChild(input);
        div.appendChild(label);

        contenedor.appendChild(div);
    });
}





function reiniciarJuego() {
    location.reload();
}

function aleatorio(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

window.addEventListener('load', iniciarJuego);