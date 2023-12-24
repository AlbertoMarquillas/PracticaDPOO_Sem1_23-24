package Persistance.ProductPersistance;

import Business.Entities.HerenciasProduct.General;
import Business.Entities.HerenciasProduct.Reduced;
import Business.Entities.HerenciasProduct.SuperReduced;
import Business.Entities.HerenciasShop.Loyalty;
import Business.Entities.HerenciasShop.MaxProfit;
import Business.Entities.HerenciasShop.Sponsored;
import Business.Entities.Product;
import com.google.gson.*;
import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz ProductDAO para la manipulación de datos de productos en la API.
 * @author alberto.marquillas i marc.viñas
 */
public class ProductCloud implements ProductDAO{

    private static Path path;
    ApiHelper api;
    String pathString;
    public ProductCloud () throws ApiException {
            path = Path.of("https//balandrau.salle.url.edu/dpoo/P1-G109/products");
            pathString = "https://balandrau.salle.url.edu/dpoo/P1-G109/products";
            this.api = new ApiHelper();
    }

    /**
     * Lee todos los productos almacenados en la API.
     *
     * @throws FileNotFoundException - FileNotFoundException
     * @return ArrayList que contiene todos los productos almacenados.
     */
    @Override
    public ArrayList<Product> readAll() throws FileNotFoundException {
        ArrayList<Product> products = new ArrayList<>();
        JsonArray array;
        JsonObject obj;

        String string = null;
        try {
            string = api.getFromUrl(pathString);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        JsonElement element = JsonParser.parseString(string);
        array = element.getAsJsonArray();
        for (JsonElement elem: array) {
            obj = elem.getAsJsonObject();
            String name = obj.get("name").getAsString();
            String brand = obj.get("brand").getAsString();
            float maxPrice = obj.get("maxPrice").getAsFloat();
            String category = obj.get("category").getAsString();

            JsonArray reviewsArray = obj.get("rating").getAsJsonArray();
            ArrayList<String> reviews = new ArrayList<>();
            if (!reviewsArray.isEmpty()) {
                for (JsonElement rev : reviewsArray) {
                    reviews.add(rev.getAsString());
                }
            }

            Product product = null;

            switch (category){
                case "GENERAL" -> product = new General(name, brand, category, maxPrice, reviews);
                case "REDUCED" -> product = new Reduced(name, brand, category, maxPrice, reviews);
                case "SUPER_REDUCED" -> product = new SuperReduced(name, brand, category, maxPrice, reviews);
            }

            products.add(product);
        }

        return products;

    }

    /**
     * Actualiza la información de los productos proporcionados en la API.
     *
     * @param product producto que se actualizará.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean update(Product product) throws IOException {
        int flag = 0;
        ArrayList<Product> products = readAll();



        api.deleteFromUrl(pathString);



        for (Product prod: products) {
            if (prod.getName().equalsIgnoreCase(product.getName())) {
                prod.setAllRating(product.getRating());
            }
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (api.postToUrl(pathString, gson.toJson(prod)) != null) {
                    flag++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag == products.size()){
            return true;
        }
        return false;
    }

    /**
     * Elimina los productos proporcionados en la API.
     *
     * @param products ArrayList de productos que se eliminarán.
     * @throws IOException - IOException
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean delete(ArrayList<Product> products) throws IOException {
        int flag = 0;
        if(products.size() == 0) {
            api.deleteFromUrl(pathString + "/" + 0);
        }
        for (Product product: products) {
            String name = product.getName();
            String s = pathString + "?name=" + name;
            try {
                if (api.deleteFromUrl(s) != null) {
                    flag++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (flag == products.size()){
            return true;
        }
        return false;
    }

    /**
     * Agrega nuevos productos al sistema en la API.
     *
     * @param product Producto que se agregará.
     * @throws IOException - IOException
     * @return true si la adición fue exitosa, false en caso contrario.
     */
    @Override
    public boolean add(Product product) throws IOException {
        int flag = 0;
        ArrayList<Product> products = readAll();
        products.add(product);
        api.deleteFromUrl(pathString);
        for (Product prod: products) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (api.postToUrl(pathString, gson.toJson(prod)) != null) {
                    flag++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag == products.size()){
            return true;
        }
        return false;
    }
}
