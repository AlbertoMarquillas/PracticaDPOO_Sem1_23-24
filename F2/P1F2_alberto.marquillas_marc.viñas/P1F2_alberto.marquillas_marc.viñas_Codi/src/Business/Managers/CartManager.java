package Business.Managers;

import Business.Entities.Cart;


import java.util.ArrayList;

/**
 * Clase que gestiona el carrito de compras de un usuario.
 * @author alberto.marquillas i marc.viñas
 */
public class CartManager {
    private Cart cart;

    /**
     * Constructor de la clase CartManager.
     */
    public CartManager() {
        this.cart = new Cart();
    }

    /**
     * Vacía el carrito de compras del usuario.
     *
     * @return true si el carrito se vació con éxito, false en caso contrario.
     */
    public boolean emptyCart() {
        cart.emptyCart();
        return true;
    }

    /**
     * Calcula el precio total de la lista proporcionada.
     *
     * @param prices ArrayList de Float que contiene los precios de los proudctos del carrito.
     *
     * @return totalprice La suma total de todos los precios.
     */
    public float sumTotalPrice(ArrayList<Float> prices) {

        float totalPrice = 0;

        for(Float price: prices){
            totalPrice = totalPrice + price;
        }

        return totalPrice;
    }

    /**
     * Confirma que el usuari haya ingresado "yes" por el teclado.
     *
     * @param confirm String con info. introducida por el usuario.
     *
     * @return true o false dependiendo de si la cadena proporcionada es "yes" o no lo es.
     */
    public boolean confirmOK(String confirm) {
        return confirm.equalsIgnoreCase("yes");
    }

    /**
     * Añade un producto con todas sus características descriptivas al carrito.
     *
     * @param productName Nombre del producto.
     *
     * @param brand Marca del producto.
     *
     * @param shopName Nombre de la tienda donde se vende el producto.
     *
     * @param price Precio del producto.
     */
    public void addToCart(String productName, String brand, String shopName, Float price) {

        cart.addProduct(productName,shopName,price, brand);
    }

    /**
     * Obtiene los nombres de los productos del carrito.
     *
     * @return Un ArrayList de strings con los nombres de los productos del carrito.
     */
    public ArrayList<String> getCartProds() {

        return cart.getCartProds();
    }

    /**
     * Obtiene las marcas de los productos del carrito.
     *
     * @return Un ArrayList de strings con las marcas de los productos del carrito.
     */
    public ArrayList<String> getCartBrands() {

        return cart.getCartBrands();
    }

    /**
     * Obtiene los precios de los productos del carrito.
     *
     * @return Un ArrayList de floats con los precios de los productos del carrito.
     */
    public ArrayList<Float> getCartPrice() {

        return cart.getCartPrice();
    }

    /**
     * Obtiene las tiendas de los productos del carrito.
     *
     * @return Un ArrayList de strings con las tiendas de los productos del carrito.
     */
    public ArrayList<String> getShopCart() {

        return cart.getShopCart();
    }


}
