package Business.Entities;

import java.util.ArrayList;

/**
 * Clase que representa el carrito de compras de un usuario.
 * @author alberto.marquillas i marc.viñas
 */
public class Cart {

    // Lista que contiene los productos en el carrito.
    private ArrayList<String> names;
    private ArrayList<String> shops;
    private ArrayList<Float> prices;
    private ArrayList<String> brands;


    /**
     * Constructor de la clase Cart.
     */
    public Cart() {
        // Inicializar la lista de productos en el carrito.
        this.names = new ArrayList<>();
        this.shops = new ArrayList<>();
        this.prices = new ArrayList<>();
        this.brands = new ArrayList<>();
    }

    /**
     * Agrega un producto a la lista del carrito con la información proporcionada.
     *
     * @param name   Nombre del producto.
     * @param shop   Nombre de la tienda donde se compra el producto.
     * @param price  Precio del producto.
     * @param brand  Marca del producto.
     */
    public void addProduct(String name, String shop, Float price, String brand) {
        // Agregar el producto a la lista del carrito.
        this.names.add(name);
        this.shops.add(shop);
        this.prices.add(price);
        this.brands.add(brand);
    }

    /**
     * Vacía el carrito, eliminando todos los productos.
     */
    public void emptyCart() {
        // Limpiar la lista de productos en el carrito.
        this.names.clear();
        this.shops.clear();
        this.prices.clear();
        this.brands.clear();
    }

    /**
     * Obtiene los nombres de los productos de un carrito.
     *
     * @return names Un ArrayList de strings con los nombres de los productos del carrito.
     */
    public ArrayList<String> getCartProds() {
        return names;
    }

    /**
     * Obtiene las tiendas de los productos del carrito.
     *
     * @return Un ArrayList de strings con las tiendas de los productos del carrito.
     */
    public ArrayList<String> getShopCart() {
        return shops;
    }

    /**
     * Obtiene los precios de los productos del carrito.
     *
     * @return Un ArrayList de floats con los precios de los productos del carrito.
     */
    public ArrayList<Float> getCartPrice() {
        return prices;
    }

    /**
     * Obtiene las marcas de los productos del carrito.
     *
     * @return Un ArrayList de strings con las marcas de los productos del carrito.
     */
    public ArrayList<String> getCartBrands() {
        return brands;
    }
}
