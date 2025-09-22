const ATAQUES_COMUNES = ['Puño', 'Patada', 'Barrida'];

export default class Personaje {
    constructor(nombre, vidas = 3, ataques = null) {
        this.nombre = nombre;
        this.vidas = vidas;
        // Si no se pasan ataques, se asignan los comunes
        this.ataques = ataques && ataques.length > 0 ? ataques : ATAQUES_COMUNES;
    }

    recibirDaño() {
        this.vidas--;
        if (this.vidas < 0) this.vidas = 0;
    }

    estaVivo() {
        return this.vidas > 0;
    }
}
