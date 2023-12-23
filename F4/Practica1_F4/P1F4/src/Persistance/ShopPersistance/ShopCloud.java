package Persistance.ShopPersistance;

import Business.Entities.HerenciasProduct.General;
import Business.Entities.HerenciasProduct.Reduced;
import Business.Entities.HerenciasProduct.SuperReduced;
import Business.Entities.Product;
import Business.Entities.ProductCatalog;
import Business.Entities.Shop;
import com.google.gson.*;
import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz ShopDAO para la manipulación de datos de tiendas en la API.
 * @author alberto.marquillas i marc.viñas
 */
public class ShopCloud implements ShopDAO{

    private static Path path;
    ApiHelper api;
    String pathString;
    public ShopCloud () {
        try {
            path = Path.of("https//balandrau.salle.url.edu/dpoo/P1-G109/shops");
            pathString = "https://balandrau.salle.url.edu/dpoo/P1-G109/shops";
            api = new ApiHelper();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Lee todas las tiendas almacenadas en la API.
     *
     * @throws FileNotFoundException - FileNotFoundException
     * @return ArrayList que contiene todas las tiendas almacenadas.
     */
    @Override
    public ArrayList<Shop> readAll() throws FileNotFoundException {
        ArrayList<Shop> shops = new ArrayList<>();
        try{
            JsonArray array;

            String string = api.getFromUrl(pathString);
            JsonElement element = JsonParser.parseString(string);
            array = element.getAsJsonArray();

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String name = obj.get("name").getAsString();
                String description = obj.get("description").getAsString();
                int since = obj.get("since").getAsInt();
                float earnings = obj.get("earnings").getAsFloat();

                JsonObject businessModelObjectJson = obj.getAsJsonObject("businessModelObject");
                String businessModelObject = businessModelObjectJson.get("model").getAsString();

                float loyaltyThreshold = 0;
                String sponsoringBrand = null;

                switch (businessModelObject) {
                    case "LOYALTY":
                        loyaltyThreshold = businessModelObjectJson.get("loyaltyThreshold").getAsFloat();
                        break;
                    case "SPONSOED":
                        sponsoringBrand = businessModelObjectJson.get("sponsoringBrand").getAsString();
                        break;
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
                            case "GENERAL" -> product = new General(productName, productBrand, productCategory, productMaxPrice, reviews, productPrice);
                            case "REDUCED" -> product = new Reduced(productName, productBrand, productCategory, productMaxPrice, reviews, productPrice);
                            case "SUPER_REDUCED" -> product = new SuperReduced(productName, productBrand, productCategory, productMaxPrice, reviews, productPrice);
                        }


                        allProducts.add(product);
                    }
                    productCatalog = new ProductCatalog(allProducts);
                }

                Shop shop = new Shop(name, description, since, earnings, businessModelObject, productCatalog, loyaltyThreshold, sponsoringBrand);
                shops.add(shop);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return shops;
    }

    /**
     * Actualiza la información de las tiendas proporcionadas en la API.
     *
     * @param shop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    @Override
    public boolean update(Shop shop) throws IOException {
        ArrayList<Shop> currentShops = readAll();
        ArrayList<Shop> finalShops = new ArrayList<>();
        int flag = 0;
        api.deleteFromUrl(pathString);
        for (int i = 0; i < currentShops.size(); i++) {
            finalShops.add(currentShops.get(i));
            if (shop.getName().equalsIgnoreCase(currentShops.get(i).getName())){
                finalShops.set(i, shop);
            }
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (api.postToUrl(pathString, gson.toJson(finalShops.get(i))) != null) {
                    flag++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag == currentShops.size()) {
            return true;
        }

        return false;
    }

    /**
     * Agrega nuevas tiendas al sistema en la API.
     *
     * @param shop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la adición fue exitosa, false en caso contrario.
     */
    @Override
    public boolean add(Shop shop) throws IOException {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (api.postToUrl(pathString, gson.toJson(shop)) != null) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }
}
