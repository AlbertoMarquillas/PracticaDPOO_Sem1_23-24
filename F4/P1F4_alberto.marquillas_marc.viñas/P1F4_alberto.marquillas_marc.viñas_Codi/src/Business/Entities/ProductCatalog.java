package Business.Entities;

import java.util.ArrayList;

/**
 * Clase que representa un catálogo de productos.
 * @author alberto.marquillas i marc.viñas
 */
public class ProductCatalog {

    private ArrayList<Product> products;

    /**
     * Constructor de la clase ProductCatalog que inicializa un catálogo con una lista de productos vacia.
     *
     */
    public ProductCatalog() {
        // Inicializar la lista de productos en el catálogo.
        this.products = new ArrayList<>();
    }
    /**
     * Constructor de la clase ProductCatalog que inicializa un catálogo con una lista de productos dada.
     * @param products ArrayList de productos para inicializar el catálogo.
     */
    public ProductCatalog(ArrayList<Product> products) {
        // Inicializar la lista de productos en el catálogo.
        this.products = products;
    }

    /**
     * Agrega un producto al catálogo.
     *
     * @param product Producto que se agregará al catálogo.
     */
    public void addProduct(Product product) {
        // Agregar el producto a la lista del catálogo.
        this.products.add(product);
    }

    /**
     * Elimina un producto del catálogo.
     *
     * @param product Producto que se eliminará del catálogo.
     * @param i posición del producto en la lista.
     *
     * @return true si el producto se eliminó correctamente, false en caso contrario.
     */
    public boolean removeProduct(Product product, int i) {

        this.products.remove(i);
        return this.products.contains(product);
    }

    /**
     * Lista los productos actualmente en el catálogo.
     *
     * @return ArrayList que contiene los productos en el catálogo.
     */
    public ArrayList<Product> listProducts() {
        return this.products;
    }

    /**
     * Convierte los nombres de los productos en un ArrayList de Strings.
     *
     * @return Un ArrayList de strings con los nombres de los productos en el catálogo.
     */
    public ArrayList<String> toArrayString() {
        ArrayList<String> prods = new ArrayList<>();
        for (Product prod: products) {
            prods.add(prod.getName());
        }
        return prods;
    }

    /**
     * Convierte las marcas de los productos en un ArrayList de Strings.
     *
     * @return Un ArrayList de strings con las marcas de los productos en el catálogo.
     */
    public ArrayList<String> toArrayStringBrand() {
        ArrayList<String> brands = new ArrayList<>();
        for (Product prod: products) {
            brands.add(prod.getBrand());
        }
        return brands;
    }

    /**
     * Busca el precio de un producto en el catálogo.
     *
     * @param prod El nombre del producto del que se busca el precio.
     * @param productCatalog El catálogo de productos en el que se busca el producto y su precio.
     *
     * @return El precio del producto o 0 si no se encuentra.
     */
    public float searchForProductPrice(String prod, ProductCatalog productCatalog) {

        ArrayList<Product> products = productCatalog.listProducts();

        for (Product product: products) {
            if(product.getName().equalsIgnoreCase(prod)) {
                return product.getPrice();
            }
        }
        return 0;
    }

    /**
     * Obtiene un ArrayList de Strings que representa los precios de los productos en un catálogo.
     *
     * @param productCatalog El catálogo de productos del cual se extraen los precios.
     * @return Un ArrayList de Strings que contiene los precios de todos los productos en el catálogo.
     *         Si el catálogo está vacío, el ArrayList resultante también estará vacío.
     */
    public ArrayList<String> searchForProductPriceArray(ProductCatalog productCatalog) {

        ArrayList<Product> products = productCatalog.listProducts();
        ArrayList<String> prices = new ArrayList<>();

        for (Product product: products) {
            prices.add(String.valueOf(product.getPrice()));
        }
        return prices;
    }

    /**
     * Obtiene el rating de un producto por su nombre.
     *
     * @param prodName El nombre del producto del que se busca el rating.
     * @return Un ArrayList de Strings que representa el rating del producto o una lista vacía si no se encuentra.
     */
    public ArrayList<String> getProductRating(String prodName) {
        ArrayList<Product> products = listProducts();
        ArrayList<String> ratings = new ArrayList<>();

        for (Product product: products)  {
            if (product.getName().equalsIgnoreCase(prodName)) {
                return product.getRating();
            }
        }
        return ratings;
    }

    /**
     * Establece el rating de un producto por su nombre.
     *
     * @param prod El nombre del producto al que se le establecerá el rating.
     * @param finalRating El rating que se establecerá.
     */
    public void setProductRating(String prod, String finalRating) {
        ArrayList<Product> products = listProducts();

        for (Product product: products)  {
            if (product.getName().equalsIgnoreCase(prod)) {
                product.setRating(finalRating);
            }
        }
    }
}
