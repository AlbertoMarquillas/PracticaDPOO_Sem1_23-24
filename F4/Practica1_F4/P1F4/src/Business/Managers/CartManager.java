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
     * @return totalprice La suma total de todos los precios.
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
     * @param productName   Nombre del producto.
     * @param brand         Marca del producto.
     * @param shopName      Nombre de la tienda donde se vende el producto.
     * @param price         Precio del producto.
     * @param fundationYear
     * @param descr
     * @param earnings
     * @param reviews
     * @return
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
     * Obtiene los nombres de los productos del carrito.
     *
     * @return Un ArrayList de strings con los nombres de los productos del carrito.
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
     * Obtiene las marcas de los productos del carrito.
     *
     * @return Un ArrayList de strings con las marcas de los productos del carrito.
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
     * Obtiene los precios de los productos del carrito.
     *
     * @return Un ArrayList de floats con los precios de los productos del carrito.
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
     * Obtiene las tiendas de los productos del carrito.
     *
     * @return Un ArrayList de strings con las tiendas de los productos del carrito.
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


    public float calculateTaxes(String shop, int i) {

        float prodPrice = 0;

        CartItem cartItem = cart.getCartItems().get(i);

        if(cartItem.getShop().getName().equalsIgnoreCase(shop)) {
            float price1 = cartItem.getProduct().getPrice();
            prodPrice = originalPrice(price1, cartItem);
        }

        return prodPrice;

    }

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

    public Product getProductInShop(String shop) {
        ArrayList<String> shops = new ArrayList<>();
        ArrayList <CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem: cartItems){
            shops.add(cartItem.getShop().getName());
        }

        for (String shop1: shops){
            if (shop1.equalsIgnoreCase(shop)){

            }
        }
        return null;
    }

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

    public boolean priceThreshold(String shop, float thresholdLoyalty) {


        ArrayList<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            if (cartItem.getShop().getName().equalsIgnoreCase(shop) && cartItem.getShop().getBusinessModel().getModel().equalsIgnoreCase("LOYALTY")) {

                if (thresholdLoyalty >  ((Loyalty)cartItem.getShop().getBusinessModel()).getLoyaltyThreshold()){
                    return true;
                }

            }
        }
        return false;

    }

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
}
