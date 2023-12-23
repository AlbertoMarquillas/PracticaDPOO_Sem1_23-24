package Business.Entities;

import java.util.ArrayList;

/**
 * Clase que representa un producto.
 * @author alberto.marquillas i marc.viñas
 */
public abstract class Product {

    // Atributos
    private String name;
    private String brand;
    private String category;
    private float maxPrice;
    private ArrayList<String> rating;
    private float price;

    /**
     * Constructor de la clase Product.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Valoración del producto.
     */
    public Product(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.maxPrice = maxPrice;
        this.rating = rating;
    }

    /**
     * Constructor de la clase Product.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     * @param price    Precio del producto.
     */
    public Product(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.maxPrice = maxPrice;
        this.price = price;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la marca del producto.
     *
     * @return Marca del producto.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Obtiene la lista de calificaciones del producto.
     *
     * @return ArrayList de String que representa las calificaciones del producto.
     */
    public ArrayList<String> getRating() {
        return rating;
    }

    /**
     * Obtiene el precio actual del producto.
     *
     * @return Precio actual del producto.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Obtiene el precio maximo del producto.
     *
     * @return Precio maximo del producto.
     */
    public float getMaxPrice() {
        return maxPrice;
    }

    /**
     * Establece el precio de un producto.
     *
     * @param price Precio del producto.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Establece la puntuación de un producto.
     *
     * @param rating Puntuación del producto.
     */
    public void setRating(String rating) {
        this.rating.add(rating);
    }

    public String getCategory() {
        return category;
    }
    public abstract float applyIVA(float price);
}

