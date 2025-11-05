let saludo = process.env.NOMBRE || 'Sin Nombre';
let web = process.env.MI_WEB || 'No tengo web';

console.log('hola ' + saludo);
console.log('Mi web es:  '  +web);
console.log('Ultima ejecucion');