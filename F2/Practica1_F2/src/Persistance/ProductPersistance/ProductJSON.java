package Persistance.ProductPersistance;

import Business.Entities.Product;
import Business.Entities.Shop;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz ProductDAO para la manipulación de datos de productos en formato JSON.
 * @author alberto.marquillas i marc.viñas
 */
public class ProductJSON implements ProductDAO {

    /**
     * Constructor from the ProductJSON class.
     * @throws FileNotFoundException - FileNotFoundException
     */
    public ProductJSON() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("files/products.json"));
        Gson gson = new Gson();
    }

    /**
     * Lee todos los productos almacenados en formato JSON.
     *
     * @throws FileNotFoundException - FileNotFoundException
     * @return ArrayList que contiene todos los productos almacenados.
     */
    @Override
    public ArrayList<Product> readAll() throws FileNotFoundException {
        ArrayList<Product> products = new ArrayList<>();
        JsonArray array;
        JsonObject obj;

        FileReader file = new FileReader("files/products.json");
        JsonElement element = JsonParser.parseReader(file);
        if(!element.isJsonNull()) {
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

                Product product = new Product(name, brand, category, maxPrice, reviews);
                products.add(product);
            }

            return products;
        } else {
            return null;
        }

    }

    /**
     * Actualiza la información de los productos proporcionados en formato JSON.
     *
     * @param product producto que se actualizará.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean update(Product product) throws IOException {

        ArrayList<Product> products = readAll();

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(product.getName())) {
                products.set(i, product);
                break;
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer fw = Files.newBufferedWriter(Paths.get("files/products.json"));
        gson.toJson(products, fw);
        fw.close();

        return true;
    }


    /**
     * Elimina los productos proporcionados en formato JSON.
     *
     * @param products ArrayList de productos que se eliminarán.
     * @throws IOException - IOException
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean delete(ArrayList<Product> products) throws IOException {
        // Implementación para eliminar productos de un archivo JSON.
        // Devuelve true si la eliminación fue exitosa, false en caso contrario.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer fw = Files.newBufferedWriter(Paths.get("files/products.json"));
        gson.toJson(products, fw);
        fw.close();
        return true;
    }

    /**
     * Agrega nuevos productos al sistema en formato JSON.
     *
     * @param product Producto que se agregará.
     * @throws IOException - IOException
     * @return true si la adición fue exitosa, false en caso contrario.
     */
    @Override
    public boolean add(Product product) throws IOException {
        // Implementación para agregar productos a un archivo JSON.
        // Devuelve true si la adición fue exitosa, false en caso contrario.
        ArrayList<Product> products = readAll();
        products.add(product);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer fw = Files.newBufferedWriter(Paths.get("files/products.json"));
        gson.toJson(products, fw);
        fw.close();
        return true;
    }
}
