package Business.Entities;

import Persistance.ShopPersistance.ShopDAO;

import java.util.ArrayList;

public class CartItem {
    // Lista que contiene los productos en el carrito.

    private Product product;
    private Shop shop;
    private int cantidad;

    public CartItem(Product product, Shop shop, int cantidad) {
        this.product = product;
        this.shop = shop;
        this.cantidad = cantidad;
    }

    public CartItem() {
        this.product = null;
        this.shop = null;
        this.cantidad = 0;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void addProduct(String name, String shop, Float price, String brand) {



    }

}
