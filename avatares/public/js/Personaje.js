export default class Personaje {
    constructor(nombre, vidas) {
        this.nombre = nombre;
        this.vidas = vidas;
    }

    recibirDaño() {
        this.vidas--;
        if (this.vidas < 0) this.vidas = 0;
    }

    estaVivo() {
        return this.vidas > 0;
    }
}
