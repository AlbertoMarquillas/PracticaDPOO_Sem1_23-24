package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

/**
 * Clase que representa un producto reducido.
 * @author alberto.marquillas i marc.viñas
 */
public class Reduced extends Product {

    /**
     * Constructor de la clase Reduced.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     */
    public Reduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
    }

    /**
     * Constructor de la clase Reduced.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     * @param price    Precio del producto.
     */
    public Reduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
    }

    /**
     * Obtiene el valor del IVA para productos.
     *
     * @return Valor del IVA para productos Reduced (en este caso, 10).
     */
    public int getIva() {
        return 10;
    }

    /**
     * Calcula el precio original del producto, aplicando el IVA adecuado según el precio y la calificación total del producto.
     *
     * @param price        Precio original del producto.
     * @param totalRating  Total de calificaciones del producto.
     * @return Precio original calculado después de aplicar el IVA correspondiente.
     */
    @Override
    public float originalPrice(float price, float totalRating) {

        if (totalRating > 3.5) {
            return (float) ((price) / (1.0 + (getReducedIva()/100.0)));
        } else {
            return (float) ((price) / (1.0 + (getIva()/100.0)));
        }
    }

    /**
     * Obtiene el valor del IVA para productos Reduced.
     *
     * @return Valor del IVA para productos Reduced (en este caso, 5).
     */
    public int getReducedIva() {
        return 5;
    }


}
