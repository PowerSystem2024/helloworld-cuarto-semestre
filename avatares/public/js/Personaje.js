export default class Personaje {
    constructor(nombre, vidas, ataques) {
        this.nombre = nombre;
        this.vidas = vidas;
        this.ataques = ataques;
    }

    recibirDaño() {
        this.vidas--;
        if (this.vidas < 0) this.vidas = 0;
    }

    estaVivo() {
        return this.vidas > 0;
    }
}
