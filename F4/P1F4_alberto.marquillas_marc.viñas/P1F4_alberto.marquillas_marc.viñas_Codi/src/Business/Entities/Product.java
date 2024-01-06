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
     * @param rating   Lista de calificaciones del producto.
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

        setAllRating(rating);
        
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
     * Establece el precio máximo del producto.
     *
     * @param maxPrice Precio máximo del producto.
     */
    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
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
     * Establece todas las calificaciones de un producto.
     *
     * @param rating Lista de calificaciones del producto.
     */
    public void setAllRating(ArrayList<String> rating) {

        if (this.rating == null) {
            this.rating = new ArrayList<>();
        }

        this.rating.clear();
        this.rating.addAll(rating);
    }

    /**
     * Establece una calificación adicional de un producto.
     *
     * @param rating Calificación adicional del producto.
     */
    public void setRating(String rating) {
        this.rating.add(rating);
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return Categoría del producto.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Obtiene el valor del IVA para el producto.
     *
     * @return Valor del IVA del producto.
     */
    public abstract int getIva();

    /**
     * Calcula el precio original del producto considerando una calificación promedio.
     *
     * @param price       Precio del producto antes de aplicar descuentos.
     * @param totalRating Calificación promedio del producto.
     * @return Precio original del producto después de aplicar descuentos.
     */
    public abstract float originalPrice(float price, float totalRating);
}

