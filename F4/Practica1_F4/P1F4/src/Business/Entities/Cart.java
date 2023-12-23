package Business.Entities;

import java.util.ArrayList;

/**
 * Clase que representa el carrito de compras de un usuario.
 * @author alberto.marquillas i marc.viñas
 */
public class Cart {

    private ArrayList<CartItem> cartItems;

    /**
     * Constructor de la clase Cart.
     */
    public Cart() {
        cartItems = new ArrayList<>();
    }

    /**
     * Agrega un producto a la lista del carrito con la información proporcionada.
     *
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

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
