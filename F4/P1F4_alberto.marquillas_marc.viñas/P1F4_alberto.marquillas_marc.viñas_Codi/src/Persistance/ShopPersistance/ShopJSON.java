package Persistance.ShopPersistance;

import Business.Entities.BusinessModel;
import Business.Entities.HerenciasProduct.General;
import Business.Entities.HerenciasProduct.Reduced;
import Business.Entities.HerenciasProduct.SuperReduced;
import Business.Entities.HerenciasShop.Loyalty;
import Business.Entities.HerenciasShop.MaxProfit;
import Business.Entities.HerenciasShop.Sponsored;
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

                BusinessModel businessModel = null;
                if (obj.has("businessModelObject")) {
                    JsonObject businessModelObjectJson = obj.getAsJsonObject("businessModelObject");
                    if (businessModelObjectJson.has("loyaltyThreshold")) {
                        float loyaltyThreshold = businessModelObjectJson.get("loyaltyThreshold").getAsFloat();
                        businessModel = new Loyalty(loyaltyThreshold);
                    } else if (businessModelObjectJson.has("sponsoringBrand")) {
                        String sponsoringBrand = businessModelObjectJson.get("sponsoringBrand").getAsString();
                        businessModel = new Sponsored(sponsoringBrand);
                    } else {
                        businessModel = new MaxProfit();
                    }
                }

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

                        Product product = null;

                        switch (productCategory){
                            case "GENERAL" -> product = new General(productName, productBrand, productCategory, productMaxPrice, reviews);
                            case "REDUCED" -> product = new Reduced(productName, productBrand, productCategory, productMaxPrice, reviews);
                            case "SUPER_REDUCED" -> product = new SuperReduced(productName, productBrand, productCategory, productMaxPrice, reviews);
                        }

                        product.setPrice(productPrice);
                        allProducts.add(product);
                    }
                    productCatalog = new ProductCatalog(allProducts);
                }
                Shop shop = new Shop(name, description, since, earnings, businessModel, productCatalog);
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
     * @param updatedShop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean update(Shop updatedShop) throws IOException {
        ArrayList<Shop> currentShops = readAll();
        boolean updated = false;

        for (int i = 0; i < currentShops.size(); i++) {
            Shop existingShop = currentShops.get(i);
            if (existingShop.getName().equals(updatedShop.getName())) {
                // Preserving the entire business model instance
                updatedShop.setBusinessModel(existingShop.getBusinessModel());

                // Update the shop with new information
                currentShops.set(i, updatedShop);
                updated = true;
                break;
            }
        }

        if (updated) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer fw = Files.newBufferedWriter(Paths.get("files/shops.json"));
            gson.toJson(currentShops, fw);
            fw.close();
        }

        return updated;
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
