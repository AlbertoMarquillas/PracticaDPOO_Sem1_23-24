package Persistance.ShopPersistance;

import Business.Entities.Product;
import Business.Entities.ProductCatalog;
import Business.Entities.Shop;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz ShopDAO para la manipulación de datos de tiendas en formato JSON.
 * @author alberto.marquillas i marc.viñas
 */
public class ShopJSON implements ShopDAO {

    /**
     * Constructor from the ShopJSON class.
     * @throws FileNotFoundException - FileNotFoundException
     */
    public ShopJSON() throws FileNotFoundException {

    }

    /**
     * Lee todas las tiendas almacenadas en formato JSON.
     *
     * @throws FileNotFoundException - FileNotFoundException
     * @return ArrayList que contiene todas las tiendas almacenadas.
     */
    @Override
    public ArrayList<Shop> readAll() throws FileNotFoundException {
        JsonArray array;
        FileReader fileReader = new FileReader("files/shops.json");
        JsonElement element = JsonParser.parseReader(fileReader);
        if (element.isJsonArray()) {
            array = element.getAsJsonArray();
            ArrayList<Shop> shops = new ArrayList<>();

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String name = obj.get("name").getAsString();
                String description = obj.get("description").getAsString();
                int since = obj.get("since").getAsInt();
                float earnings = obj.get("earnings").getAsFloat();

                JsonObject businessModelObjectJson = obj.getAsJsonObject("businessModelObject");
                String businessModelObject = businessModelObjectJson.get("model").getAsString();

                ProductCatalog productCatalog = null;
                if (obj.has("productCatalog")) {
                    JsonObject productCatalogJson = obj.getAsJsonObject("productCatalog");
                    JsonArray prod = productCatalogJson.getAsJsonArray("products");

                    ArrayList<Product> allProducts = new ArrayList<>();
                    for (JsonElement productElem : prod) {
                        JsonObject productObj = productElem.getAsJsonObject();
                        String productName = productObj.get("name").getAsString();
                        String productBrand = productObj.get("brand").getAsString();
                        String productCategory = productObj.get("category").getAsString();
                        float productMaxPrice = productObj.get("maxPrice").getAsFloat();

                        JsonArray reviewsArray = productObj.getAsJsonArray("rating");
                        ArrayList<String> reviews = new ArrayList<>();
                        if (reviewsArray != null) {
                            for (JsonElement rev : reviewsArray) {
                                reviews.add(rev.getAsString());
                            }
                        }
                        float productPrice = productObj.get("price").getAsFloat();

                        Product product = new Product(productName, productBrand, productCategory, productMaxPrice, reviews, productPrice);
                        allProducts.add(product);
                    }
                    productCatalog = new ProductCatalog(allProducts);
                }

                Shop shop = new Shop(name, description, since, earnings, businessModelObject, productCatalog);
                shops.add(shop);
            }
            return shops;
        } else {
            return null;
        }
    }

    /**
     * Actualiza la información de las tiendas proporcionadas en formato JSON.
     *
     * @param shop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean update(Shop shop) throws IOException {

        ArrayList<Shop> currentShops = readAll();

        for (int i = 0; i < currentShops.size(); i++) {
            if (currentShops.get(i).getName().equals(shop.getName())) {
                currentShops.set(i, shop);
                break;
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer fw = Files.newBufferedWriter(Paths.get("files/shops.json"));
        gson.toJson(currentShops, fw);
        fw.close();

        return true;
    }

    /**
     * Agrega nuevas tiendas al sistema en formato JSON.
     *
     * @param shop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la adición fue exitosa, false en caso contrario.
     */
    @Override
    public boolean add(Shop shop) throws IOException {
        ArrayList<Shop> shops = readAll();
        if (shops == null) {
            shops = new ArrayList<>();
        }
        shops.add(shop);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer fw = Files.newBufferedWriter(Paths.get("files/shops.json"));
        gson.toJson(shops, fw);
        fw.close();
        return true;
    }

}
