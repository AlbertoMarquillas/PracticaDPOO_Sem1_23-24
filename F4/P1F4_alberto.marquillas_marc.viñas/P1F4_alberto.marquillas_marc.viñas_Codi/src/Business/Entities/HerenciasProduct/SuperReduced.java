package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

/**
 * Clase que representa un producto altamente reducido.
 * @author alberto.marquillas i marc.viñas
 */
public class SuperReduced extends Product {

    /**
     * Constructor de la clase SuperReduced.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     */
    public SuperReduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
    }

    /**
     * Constructor de la clase SuperReduced.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     * @param price    Precio del producto.
     */
    public SuperReduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
    }

    /**
     * Obtiene el valor del IVA para productos.
     *
     * @return Valor del IVA para productos SuperReduced (en este caso, 4).
     */
    public int getIva() {
        return 4;
    }

    /**
     * Calcula el precio original del producto, aplicando el IVA adecuado según el precio y el tipo de producto.
     *
     * @param price        Precio original del producto.
     * @param totalRating  Total de calificaciones del producto (no se utiliza aquí).
     * @return Precio original calculado después de aplicar el IVA correspondiente.
     */
    @Override
    public float originalPrice(float price, float totalRating) {
        if (price > 100) {
            return (float) ((price) / (1.0 + (getSuperReducedIva()/100.0)));
        } else {
            return (float) ((price) / (1.0 + (getIva()/100.0)));
        }
    }

    /**
     * Obtiene el valor del IVA para productos SuperReduced.
     *
     * @return Valor del IVA para productos SuperReduced (en este caso, 0).
     */
    public int getSuperReducedIva() {
        return 0;
    }

}
