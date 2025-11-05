

function soyAsincrona(miCallback) {
    setTimeout(function () {
        console.log('Hola' + nombre);
        miCallback(nombre);
    }, 1000);

    console.log('Inicio de la funcion asincrona');

}
function hablar(callbackHablar) {
    setTimeout(function () {
        console.log('Bla bla bla bla....');
        callbackHablar();
    }, 1000);
}


function adios(nombre, otroCallback) {
    setTimeout(function () {
        console.log('Adios ' + nombre);
        otroCallback();
    }, 1000);
}
//----PROGRAMA PRINCIPAL ----//
console.log('Iniciando el proceso ...');
hola('Carlos', function (nombre) {
    hablar(function () {
        hablar(function () {
            hablar(function () {
                hablar(function () {
                    adios(nombre, function () {
                        console.log('Terminando el proceso ...');
                    });
                });
            });
        });
    });
});

//hola('Carlos', function(){});
//adios('Carlos', function(){});
