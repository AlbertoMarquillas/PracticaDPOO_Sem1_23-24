package Business.Managers;

import Business.Entities.Cart;
import Business.Entities.CartItem;
import Business.Entities.HerenciasProduct.General;
import Business.Entities.HerenciasProduct.Reduced;
import Business.Entities.HerenciasProduct.SuperReduced;
import Business.Entities.HerenciasShop.Loyalty;
import Business.Entities.HerenciasShop.Sponsored;
import Business.Entities.Product;
import Business.Entities.Shop;


import java.util.ArrayList;

/**
 * Clase que gestiona el carrito de compras de un usuario.
 * @author alberto.marquillas i marc.viñas
 */
public class CartManager {
    private Cart cart;

    /**
     * Constructor de la clase CartManager que crea un carrito vacío.
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
     * Calcula el precio total de los productos en el carrito.
     *
     * @return totalPrice El precio total de todos los productos en el carrito.
     */
    public float sumTotalPrice() {

        float totalPrice = 0;
        ArrayList<CartItem> cartItems = cart.getCartItems();

        for(CartItem cartItem: cartItems){

            totalPrice = totalPrice + cartItem.getProduct().getPrice();;
        }

        return totalPrice;
    }

    /**
     * Confirma si el usuario ingresó "yes" por teclado.
     *
     * @param confirm La cadena introducida por el usuario.
     * @return true si la cadena proporcionada es "yes", false en caso contrario.
     */
    public boolean confirmOK(String confirm) {
        return confirm.equalsIgnoreCase("yes");
    }

    /**
     * Añade un producto con todas sus características descriptivas al carrito.
     *
     * @param productName     Nombre del producto.
     * @param brand           Marca del producto.
     * @param shopName        Nombre de la tienda donde se vende el producto.
     * @param price           Precio del producto.
     * @param category        Categoría del producto.
     * @param fundationYear   Año de fundación de la tienda.
     * @param descr           Descripción de la tienda.
     * @param earnings        Ganancias de la tienda.
     * @param businessModel   Modelo de negocio de la tienda.
     * @param loyaltyThres    Umbral de lealtad (solo si el modelo es "LOYALTY").
     * @param sponsor         Marca patrocinadora (solo si el modelo es "SPONSORED").
     * @param reviews         Lista de revisiones del producto.
     * @param thresholdLoyalty Umbral de lealtad para aplicar descuento (solo si el modelo es "LOYALTY").
     */
    public void addToCart(String productName, String brand, String shopName, Float price, String category, int fundationYear, String descr, float earnings, String businessModel, float loyaltyThres, String sponsor, ArrayList<String> reviews, float thresholdLoyalty) {

        boolean added = false;

        ArrayList <CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem: cartItems) {
            if (cartItem.getProduct().getName().equalsIgnoreCase(productName)){

                cartItem.setCantidad(cartItem.getCantidad()+1);
                cartItem.getProduct().setPrice(cartItem.getProduct().getPrice() + price);
                added = true;
            }
        }
        if (!added) {
            Shop shop = null;
            Product product = null;
            CartItem cartItem = new CartItem();

            switch (category) {
                case "GENERAL" -> product = new General(productName, brand, category, 0, reviews, price);

                case "REDUCED" -> product = new Reduced(productName, brand, category, 0, reviews, price);

                case "SUPER_REDUCED" -> product = new SuperReduced(productName, brand, category, 0, reviews, price);
            }
            cartItem.setProduct(product);
            switch (businessModel) {
                case "MAX_PROFIT" -> shop = new Shop(shopName,descr, fundationYear, businessModel, 0, null);
                case "LOYALTY" -> {
                    shop = new Shop(shopName,descr, fundationYear, businessModel, loyaltyThres, null);

                    if (thresholdLoyalty > loyaltyThres) {

                        //aplicar descompte

                        price = shop.descompte(price, cartItem.getProduct().getIva());
                        product.setPrice(price);

                    }

                }
                case "SPONSORED" -> {
                    shop = new Shop(shopName,descr, fundationYear, businessModel, 0,sponsor);
                    if (product.getBrand().equalsIgnoreCase(sponsor)){
                        price = shop.applyModel(price);
                        product.setPrice(price);
                    }
                }

            }

            cartItem.setProduct(product);
            cartItem.setShop(shop);
            cartItem.setCantidad(cartItem.getCantidad()+1);
            cart.addProduct(cartItem);

        }

    }

    /**
     * Obtiene los nombres de los productos en el carrito.
     *
     * @return Un ArrayList de strings con los nombres de los productos en el carrito.
     */
    public ArrayList<String> getCartProds() {

        ArrayList <String> prodName = new ArrayList<>();
        ArrayList <CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem: cartItems){
            prodName.add(cartItem.getProduct().getName());
        }
        return prodName;
    }

    /**
     * Obtiene las marcas de los productos en el carrito.
     *
     * @return Un ArrayList de strings con las marcas de los productos en el carrito.
     */
    public ArrayList<String> getCartBrands() {

        ArrayList <String> brands = new ArrayList<>();
        ArrayList <CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem: cartItems){
            brands.add(cartItem.getProduct().getBrand());
        }
        return brands;
    }

    /**
     * Obtiene los precios de los productos en el carrito.
     *
     * @return Un ArrayList de floats con los precios de los productos en el carrito.
     */
    public ArrayList<Float> getCartPrice() {

        ArrayList <Float> prices = new ArrayList<>();
        ArrayList <CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem: cartItems){
            prices.add(cartItem.getProduct().getPrice());
        }
        return prices;
    }

    /**
     * Obtiene los nombres de las tiendas de los productos en el carrito.
     *
     * @return Un ArrayList de strings con los nombres de las tiendas de los productos en el carrito.
     */
    public ArrayList<String> getShopCart() {

        ArrayList <String> shops = new ArrayList<>();
        ArrayList <CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()){
        } else {

            for (CartItem cartItem: cartItems){
                shops.add(cartItem.getShop().getName());
            }

        }


        return shops;
    }

    /**
     * Calcula los impuestos para un producto en una tienda específica.
     *
     * @param shop Nombre de la tienda.
     * @param i    Índice del producto en el carrito.
     * @return El precio del producto con impuestos calculados.
     */
    public float calculateTaxes(String shop, int i) {

        float prodPrice = 0;

        CartItem cartItem = cart.getCartItems().get(i);

        if(cartItem.getShop().getName().equalsIgnoreCase(shop)) {
            float price1 = cartItem.getProduct().getPrice();
            prodPrice = originalPrice(price1, cartItem);
        }

        return prodPrice;

    }

    /**
     * Calcula el precio original del producto con descuento si corresponde.
     *
     * @param price     Precio original del producto.
     * @param cartItem  Ítem del carrito que contiene el producto.
     * @return El precio original del producto con descuento si corresponde.
     */
    private float originalPrice(float price, CartItem cartItem) {
        float totalRating = 0.0F;
        if (cartItem.getProduct().getCategory().equalsIgnoreCase("REDUCED")){

            ArrayList<String> reviews = cartItem.getProduct().getRating();

            float cantidad = 0.0F;

            if (reviews != null) {
                if (reviews.size() > 0) {
                    for (String rating: reviews) {
                        totalRating = totalRating + Float.parseFloat(String.valueOf(rating.charAt(0)));
                        cantidad++;

                    }
                    if (cantidad > 0){
                        totalRating = totalRating/cantidad;
                    } else {
                        totalRating = 0;
                    }
                }
            }


        }
        return cartItem.getProduct().originalPrice(price, totalRating);


    }

    /**
     * Obtiene las cantidades de los productos en el carrito.
     *
     * @return Un ArrayList de strings con las cantidades de los productos en el carrito.
     */
    public ArrayList<String> getCantidades() {
        ArrayList <String> cantidades = new ArrayList<>();
        ArrayList <CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()){
        } else {

            for (CartItem cartItem: cartItems){
                cantidades.add(String.valueOf(cartItem.getCantidad()));
            }

        }


        return cantidades;
    }

    /**
     * Comprueba si la tienda seleccionada en el carrito tiene el modelo de negocio "LOYALTY".
     *
     * @param shop Nombre de la tienda.
     * @return true si la tienda tiene el modelo "LOYALTY", false en caso contrario.
     */
    public boolean isLoyality(String shop) {

        ArrayList<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getShop().getName().equalsIgnoreCase(shop)) {

                if (cartItem.getShop().getBusinessModel().getModel().equalsIgnoreCase("LOYALTY")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Obtiene el precio de un producto específico en una tienda del carrito.
     *
     * @param shop    Nombre de la tienda.
     * @param product Nombre del producto.
     * @return El precio del producto en la tienda especificada en el carrito.
     */
    public float getPriceProd(String shop, String product) {
        ArrayList<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getShop().getName().equalsIgnoreCase(shop)){
                if (cartItem.getProduct().getName().equalsIgnoreCase(product)){
                    return cartItem.getProduct().getPrice();
                }
            }
        }
        return 0;
    }

    /**
     * Establece el umbral de lealtad en el carrito.
     *
     * @param loyaltyThres Umbral de lealtad.
     */
    public void setLoyaltyThres(float loyaltyThres) {
        cart.setThresholdLoyalty(loyaltyThres);
    }

    /**
     * Obtiene el umbral de lealtad del carrito.
     *
     * @return El umbral de lealtad del carrito.
     */
    public float getLoyaltyThres() {
        return cart.getThresholdLoyalty();
    }
}
