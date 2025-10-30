// La palabra async no es necesaria para las funciones, por que ya son asincronas
//Igual proyecta una sincronia visual

async function hola(nombre) {
    return new Promise(function (resolve, reject) {
        setTimeout(function () {
            console.log('Hola' + nombre);
            resolve(nombre);
        }, 1000);
    });
}



async function hablar(nombre) {
    return new Promise( (resolve, reject) => {
        setTimeout(function () {
            console.log('Bla bla bla bla....');
            resolve();
         }, 1000);
    });

}


async function adios(nombre) {
    return new Promise((resolve, reject)=> {
        setTimeout(function () {
        console.log('Adios ' + nombre);
        //resolve();
        reject('Hay un error');
    }, 1000);
    });
    
}

// await hola('Juan')
//await solo es valido dentro de funciones async
async function main() {
    let nombre = await hola('Juan');
    await hablar();
    await hablar();
    await hablar();
    await adios(nombre);
    console.log('Terminando proceso...');
 } 
 
 console.log('Iniciando proceso...');
 main();

 console.log('segunda instruccion');


//Codigo en ingles 

async function sayHello(name) {
    return new Promise((resolve, reject)=> {
        setTimeout(() => {
            console.log('Hello ' + name);
            resolve(name);
        }, 1000);
    });
}

async function talk(name) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            console.log('Blah blah blah blah....');
            resolve();
        }, 1000);
    });
}

async function sayBye(name) {
    return new Promise((resolve, reject) => {
        setTimeout(()  => {
            console.log('Goodbye ' + name);
            resolve(name);
        }, 1000);
    });
}

// await hello('John')
// await is only valid inside async functions
async function conversation() {
    console.log('Code in english');
    console.log('Starting process...');
    await sayHello(name);
    await talk();
    await talk();
    await talk();
    await sayBye(name);
    console.log('Ending process...');
}

conversation('Juan');

console.log('Starting process...');
main();

console.log('Second instruction');
