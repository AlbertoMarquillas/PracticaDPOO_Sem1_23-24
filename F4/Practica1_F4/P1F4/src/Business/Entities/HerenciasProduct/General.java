package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

/**
 * Clase que representa un producto general.
 * @author alberto.marquillas i marc.viñas
 */
public class General extends Product {

    /**
     * Constructor de la clase General.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     */
    public General(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
    }

    /**
     * Constructor de la clase General.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @param rating   Lista de calificaciones del producto.
     * @param price    Precio del producto.
     */
    public General(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
    }
    /**
     * Obtiene el valor del IVA para productos Generales.
     *
     * @return Valor del IVA para productos Generales (en este caso, 21).
     */
    public int getIva() {
        return 21;
    }

    /**
     * Calcula el precio original del producto, aplicando el IVA correspondiente.
     *
     * @param price        Precio original del producto.
     * @param totalRating  Total de calificaciones del producto (no se utiliza en este cálculo).
     * @return Precio original calculado después de aplicar el IVA correspondiente.
     */
    @Override
    public float originalPrice(float price, float totalRating) {
        return (float) ((price) / (1.0 + (getIva()/100.0)));
    }

}
