const shopContent = document.getElementById("shopContent");
const cart = [];

products.forEach((product) => {
  const productCard = document.createElement("div");

  productCard.innerHTML = `
    <img src="${product.image}" />
    <h2>${product.productName}</h2>
    <p>Precio: $${product.price}</p>
    <p>Cantidad: ${product.quantity} U.</p>
  `;
  shopContent.append(productCard);

  const buyButton = document.createElement("button");
  buyButton.innerText = "Comprar";

  productCard.append(buyButton);

  buyButton.addEventListener("click", () => {
    const repeat = cart.some(
      (repeatProduct) => repeatProduct.id === product.id
    );
    if (repeat) {
      cart.map((prod) => {
        if (prod.id === product.id) {
          prod.quantity++;
        }
      });
    } else
      cart.push({
        id: product.id,
        productName: product.productName,
        price: product.price,
        image: product.image,
        quantity: product.quantity,
      });
    console.log(cart);
  });
});
