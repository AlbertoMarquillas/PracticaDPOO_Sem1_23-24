package Business.Managers;

import Business.Entities.HerenciasProduct.General;
import Business.Entities.HerenciasProduct.Reduced;
import Business.Entities.HerenciasProduct.SuperReduced;
import Business.Entities.Product;
import Persistance.ProductPersistance.*;
import edu.salle.url.api.exception.ApiException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que gestiona las operaciones relacionadas con los productos.
 * @author alberto.marquillas i marc.viñas
 */
public class ProductManager {

    private ProductDAO productDAO;

    /**
     * Constructor de ProductManager.
     */
    public ProductManager(int optionData) {
        switch (optionData) {
            case 0 -> {
                try {
                    this.productDAO = new ProductCloud();
                } catch (ApiException e) {
                    // Switch to JSON if API is not reachable
                    try {
                        this.productDAO = new ProductJSON();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            case 1 -> {
                try {
                    this.productDAO = new ProductJSON();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public boolean isUsingCloud() {
        return this.productDAO instanceof ProductCloud;
    }

    /**
     * Crea un nuevo producto con la información proporcionada.
     *
     * @param name     Nombre del producto.
     * @param brand    Marca del producto.
     * @param category Categoría del producto.
     * @param maxPrice Precio máximo del producto.
     * @return true si el producto se crea correctamente, false en caso contrario.
     */
    public boolean createProduct(String name, String brand, String category, float maxPrice) {
        ArrayList<String> string = new ArrayList<>();

        Product product = null;


        switch (category) {
            case "GENERAL" -> product = new General(name, brand, category, maxPrice, string);

            case "REDUCED" -> product = new Reduced(name, brand, category, maxPrice, string);

            case "SUPER_REDUCED" -> product = new SuperReduced(name, brand, category, maxPrice, string);

        }

        try {
            if (productDAO.add(product)) {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Elimina un producto del sistema.
     *
     * @param prodName Producto que se eliminará del sistema.
     * @return true si el producto se eliminó con éxito, false en caso contrario.
     */
    public boolean deleteProduct(String prodName) {

        try {
            ArrayList<Product> prods = productDAO.readAll();
            ArrayList<Product> prodNoDelete = new ArrayList<>();

            for (Product prod : prods) {
                if (!prod.getName().equalsIgnoreCase(prodName)) {
                    prodNoDelete.add(prod);
                }
            }
            return productDAO.delete(prodNoDelete);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Verifica si el nombre del producto proporcionado es único en el sistema.
     *
     * @param name Nombre del producto a verificar.
     * @return true si el nombre del producto es único, false si ya existe un producto con ese nombre.
     */
    public boolean productUniqueName(String name) {

        ArrayList<Product> products;

        try {
            products = productDAO.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (products == null) {
            products = new ArrayList<>();
        }
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }



    /**
     * Formatea la cadena proporcionada según un formato específico.
     *
     * @param string Cadena a formatear.
     *
     * @return Cadena formateada según las reglas específicas.
     */
    public String stringFormat(String string) {

        String[] words = string.split(" ");
        String finalWord = "";
        for (String word: words){
            word = word.toUpperCase().charAt(0) + word.substring(1).toLowerCase();
            finalWord = finalWord.concat(word);
            finalWord = finalWord.concat(" ");
        }
        return finalWord.trim();
    }

    /**
     * Verifica si el número proporcionado es positivo.
     *
     * @param number Número a verificar.
     *
     * @return true si el número es positivo, false en caso contrario.
     */
    public boolean checkPositiveFloat(float number) {
        return number >= 0;
    }

    /**
     * Lista los nombres de todos los productos registrados en el sistema.
     *
     * @return ArrayList que contiene los nombres de todos los productos registrados.
     */
    public ArrayList<String> listProducts() {
        ArrayList<String> productNames;

        try {
            ArrayList<Product> products = productDAO.readAll();
            productNames = getNamesFromProducts(products);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return productNames;
    }

    /**
     * Lista las marcas de los productos registrados en el sistema.
     *
     * @return un ArrayList que contiene las marcas de todos los productos registrados.
     */
    public ArrayList<String> listBrands() {
        ArrayList<String> brandNames;

        try {
            ArrayList<Product> products = productDAO.readAll();
            brandNames = getBrandsFromProducts(products);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return brandNames;
    }

    /**
     * Obtiene una lista de nombres de productos a partir de una de productos.
     *
     * @param products lista de productos de la que se obtienen los nombres de los mismos.
     *
     * @return un ArrayList de strings con los nombres de los productos.
     */
    public ArrayList<String> getNamesFromProducts(ArrayList<Product> products) {

        ArrayList<String> productNames = new ArrayList<>();

        for(Product prod: products){
            productNames.add(prod.getName());
        }
        return productNames;
    }

    /**
     * Obtiene una lista de marcas de productos a partir de una de productos.
     *
     * @param products lista de productos de la que se obtienen las marcas de los mismos.
     *
     * @return un ArrayList de strings con las marcas de los productos.
     */
    public ArrayList<String> getBrandsFromProducts(ArrayList<Product> products) {

        ArrayList<String> brandNames = new ArrayList<>();

        for(Product prod: products){
            brandNames.add(prod.getBrand());
        }
        return brandNames;
    }

    /**
     * Obtiene una marca de un producto a partir del nombre de este.
     *
     * @param product nombre del producto del que se obtiene la marca.
     *
     * @return la marca del producto proporcionado.
     */
    public String getBrandFromProduct(String product) {

        ArrayList<Product> products = readAll();

        for(Product prod: products) {
            if (prod.getName().equalsIgnoreCase(product)) {
                return prod.getBrand();
            }
        }
        return "";
    }

    /**
     * Agrega una calificación a un producto existente.
     *
     * @param rating Calificación a agregar.
     * @param prod   Nombre del producto al que se agregará la calificación.
     * @return true si la calificación se agrega correctamente, false en caso contrario.
     */
    public boolean addRating(String rating, String prod) {
        ArrayList<Product> prods = readAll();

        for (Product product: prods) {
            if (product.getName().equalsIgnoreCase(prod)) {
                product.setRating(rating);
                try {
                    return productDAO.update(product);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }

    /**
     * Busca y devuelve el nombre del producto correspondiente al nombre proporcionado.
     *
     * @param name Nombre del producto a buscar.
     *
     * @return Nombre del producto encontrado, o una cadena vacía si no se encontró.
     */
    public ArrayList<String> searchProductName(String name) {

        ArrayList<Product> products = readAll();
        ArrayList<String> searchProds = new ArrayList<>();
        name = name.toLowerCase();
        for (Product prod: products) {
            if (prod.getName().toLowerCase().contains(name)) {
                searchProds.add(prod.getName());
            } else if (prod.getBrand().toLowerCase().contains(name)) {
                searchProds.add(prod.getName());
            }
        }

        return searchProds;
    }

    /**
     * Verifica la existencia de productos en el archivo JSON.
     *
     * @throws RuntimeException si se produce una FileNotFoundException al intentar leer el archivo JSON.
     *
     * @return true si hay productos en el archivo JSON, false si el archivo está vacío.
     */
    public boolean checkProductJSON() {
        try {
            return productDAO.readAll() != null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Confirma si el usuario quiere eliminar algo según la cadena que introduce.
     *
     * @param str la cadena introducida por el usuario
     *
     * @return true si la cadena es "yes", false si no lo es.
     */
    public boolean confirmDelete(String str) {
        return str.equalsIgnoreCase("Yes");
    }

    /**
     * Obtiene el nombre de un producto según la posición proporcionada.
     *
     * @param pos la posición de la que se obtiene el nombre del producto.
     *
     * @return el nombre del producto que estaba en la posición proporcionada.
     */
    public String findNameFromInt(int pos) {
        ArrayList<Product> products = readAll();

        return products.get(pos-1).getName();
    }

    /**
     * Obtiene el nombre de un producto según la cadena proporcionada.
     *
     * @param product la cadena de la que se obtiene el nombre del producto.
     *
     * @return el nombre del producto si coincide con la cadena, null si no coincide.
     */
    public Product productFromString(String product) {

        ArrayList<Product> products;
        try {
            products = productDAO.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for(Product prod: products) {
            if(prod.getName().equalsIgnoreCase(product)){
                return prod;
            }
        }
        return null;
    }

    /**
     * Lee todos los productos almacenados en el sistema.
     *
     * @return un ArrayList de productos que representa todos los productos almacenados.
     */
    public ArrayList<Product> readAll() {
        try {
            return productDAO.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cuenta cuantas posiciones tiene el string proporcionado.
     *
     * @param rate cadena que representa la calificación de un producto.
     *
     * @return cuantas posiciones tiene la cadena proporcionada.
     */
    public String contRating(String rate) {
        return String.valueOf(rate.length());
    }

    /**
     * Obtiene las reseñas de un producto.
     *
     * @param product el producto del cual se obtienen las reseñas.
     *
     * @return un ArrayList de strings con las calificaciones del producto si se encuentra, null si no se encuentra.
     */
    public ArrayList<String> getReviews(String product) {
        ArrayList<Product> products = readAll();
        for (Product prods: products) {
            if (prods.getName().equalsIgnoreCase(product)) {
                return prods.getRating();
            }
        }
        return null;
    }

    /**
     * Obtiene la media de las calificaciones de las reseñas de un producto.
     *
     * @param reviews ArrayList con las reseñas de un producto.
     *
     * @return un float que representa la media de calificaciones de un producto.
     */
    public float getAverageReviews(ArrayList<String> reviews) {

        float finalGrade = 0;

        for (String str: reviews) {
            float value = Float.parseFloat(String.valueOf(str.charAt(0)));
            finalGrade = finalGrade + value;
        }

        finalGrade = finalGrade / reviews.size();

        return finalGrade;

    }

    public boolean checkProductAPI() {

        try {
            return productDAO.readAll() != null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUsingLocal() {
        return this.productDAO instanceof ProductJSON;
    }
}
