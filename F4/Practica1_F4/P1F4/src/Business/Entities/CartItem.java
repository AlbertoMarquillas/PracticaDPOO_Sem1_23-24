package Business.Entities;


/**
 * Clase que representa un elemento en el carrito de compras.
 * @author alberto.marquillas i marc.viñas
 */
public class CartItem {

    private Product product;
    private Shop shop;
    private int cantidad;

    /**
     * Constructor de la clase CartItem. Inicializa el elemento del carrito sin ningún producto ni tienda.
     */
    public CartItem() {
        this.product = null;
        this.shop = null;
        this.cantidad = 0;
    }

    /**
     * Obtiene el producto asociado a este elemento del carrito.
     *
     * @return Producto asociado.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Establece el producto asociado a este elemento del carrito.
     *
     * @param product Producto que se asociará al elemento del carrito.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Obtiene la tienda asociada a este elemento del carrito.
     *
     * @return Tienda asociada.
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * Establece la tienda asociada a este elemento del carrito.
     *
     * @param shop Tienda que se asociará al elemento del carrito.
     */
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    /**
     * Obtiene la cantidad de unidades del producto en este elemento del carrito.
     *
     * @return Cantidad de unidades del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades del producto en este elemento del carrito.
     *
     * @param cantidad Cantidad de unidades del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


}
