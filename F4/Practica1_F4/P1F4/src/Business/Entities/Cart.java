package Business.Entities;

import java.util.ArrayList;

/**
 * Clase que representa el carrito de compras de un usuario.
 * @author alberto.marquillas i marc.viñas
 */
public class Cart {

    private ArrayList<CartItem> cartItems;
    private float thresholdLoyalty;

    /**
     * Constructor de la clase Cart. Inicializa un carrito vacío sin umbral de lealtad.
     */
    public Cart() {
        cartItems = new ArrayList<>();
        this.thresholdLoyalty = 0;
    }

    /**
     * Agrega un producto al carrito con el elemento del carrito proporcionado.
     *
     * @param cartItem Elemento del carrito que se agregará.
     */
    public void addProduct(CartItem cartItem) {

        cartItems.add(cartItem);
    }

    /**
     * Vacía el carrito, eliminando todos los productos.
     */
    public void emptyCart() {
        cartItems.clear();
    }

    /**
     * Obtiene la lista de elementos del carrito.
     *
     * @return ArrayList de elementos del carrito.
     */
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * Obtiene el umbral de lealtad del carrito.
     *
     * @return Umbral de lealtad del carrito.
     */
    public float getThresholdLoyalty() {
        return thresholdLoyalty;
    }

    /**
     * Establece el umbral de lealtad del carrito.
     *
     * @param thresholdLoyalty Umbral de lealtad que se establecerá en el carrito.
     */
    public void setThresholdLoyalty(float thresholdLoyalty) {
        this.thresholdLoyalty = thresholdLoyalty;
    }
}
