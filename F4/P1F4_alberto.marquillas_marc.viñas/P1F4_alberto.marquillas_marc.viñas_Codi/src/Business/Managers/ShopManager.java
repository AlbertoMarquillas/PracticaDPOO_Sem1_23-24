package Business.Managers;

import Business.Entities.HerenciasShop.Loyalty;
import Business.Entities.HerenciasShop.Sponsored;
import Business.Entities.Product;
import Business.Entities.ProductCatalog;
import Business.Entities.Shop;

import Persistance.ShopPersistance.ShopCloud;
import Persistance.ShopPersistance.ShopDAO;
import Persistance.ShopPersistance.ShopJSON;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que gestiona las operaciones relacionadas con las tiendas.
 * @author alberto.marquillas i marc.viñas
 */
public class ShopManager {

    private ShopDAO shopDAO;

    /**
     * Constructor de la clase ShopManager que permite seleccionar la fuente de datos para las tiendas.
     *
     * @param optionData La opción que determina si se utilizará almacenamiento en la nube (0) o almacenamiento local (1).
     */
    public ShopManager(int optionData) {
        try {
            switch (optionData) {
                case 0 -> this.shopDAO = new ShopCloud();
                case 1 -> this.shopDAO = new ShopJSON();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea una nueva tienda con la información proporcionada.
     *
     * @param name             Nombre de la tienda.
     * @param description      Descripción de la tienda.
     * @param foundingYear     Año de fundación de la tienda.
     * @param businessModel    Modelo de negocio de la tienda.
     * @param loyaltyThreshold Umbral de lealtad de la tienda (solo aplicable si el modelo de negocio es "LOYALTY").
     * @param sponsoringBrand  Marca patrocinadora de la tienda (solo aplicable si el modelo de negocio es "SPONSORED").
     * @return true si la tienda se creó correctamente, false en caso contrario.
     */
    public boolean createShop(String name, String description, int foundingYear, String businessModel, float loyaltyThreshold, String sponsoringBrand) {
        Shop shop = new Shop(name, description, foundingYear, businessModel, loyaltyThreshold, sponsoringBrand);

        try {
            if(shopDAO.add(shop)){
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Verifica si el nombre de tienda proporcionado es único en el sistema.
     *
     * @param name Nombre de tienda a verificar.
     *
     * @return true si el nombre es único, false si ya existe una tienda con ese nombre.
     */
    public boolean shopUniqueName(String name) {

        ArrayList<Shop> shops;
        try {
            shops = shopDAO.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        boolean isUnique = true;

        if (shops != null) {
            for(Shop shop: shops) {
                if (shop.getName().equals(name)){
                    isUnique = false;
                    break;
                }
            }
        }
        return isUnique;
    }

    /**
     * Verifica si el número proporcionado es un entero positivo.
     *
     * @param number Número a verificar.
     *
     * @return true si el número es un entero positivo, false en caso contrario.
     */
    public boolean checkPositive(int number) {
        return number > 0;
    }

    /**
     * Lista los nombres de todas las tiendas registradas en el sistema.
     *
     * @return ArrayList que contiene los nombres de todas las tiendas registradas.
     */
    public ArrayList<String> listShops() {
        ArrayList<Shop> shops = readAll();
        ArrayList<String> shopsString = new ArrayList<>();

        for (Shop shop: shops) {
            shopsString.add(shop.getName());
        }

        return shopsString;
    }

    /**
     * Obtiene el catálogo de productos de una tienda según su nombre.
     *
     * @param name El nombre de la tienda de la que se desea obtener el catálogo de productos.
     *
     * @return El catálogo de productos de la tienda correspondiente.
     */
    public ProductCatalog getProductCatalog(String name) {

        int pos = findPosFromName(name);

        try {
            return shopDAO.readAll().get(pos).getProductCatalog();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encuentra la posición de una tienda en la lista de tiendas según su nombre.
     *
     * @param name El nombre de la tienda de la que queremos encontrar su posición.
     *
     * @return La posición de la tienda en la lista.
     */
    public int findPosFromName(String name) {
        ArrayList<Shop> shops;
        int i = 0;
        try {
            shops = shopDAO.readAll();
            for (Shop shop: shops) {
                if (shop.getName().equalsIgnoreCase(name)){
                    return i;
                }
                i++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    /**
     * Identifica y obtiene una tienda según la cadena que se le proporciona.
     *
     * @param shopName Cadena que representa la tienda que se quiere encontrar.
     *
     * @return La tienda que coincide con la cadena proporcionada encontrada en la lista de tiendas, null si no coincide.
     */
    public Shop shopFromString(String shopName) {
        ArrayList<Shop> shopArrayList = readAll();
        for (Shop shop: shopArrayList)
            if(shop.getName().equalsIgnoreCase(shopName)){
                return shop;
            }
        return null;
    }

    /**
     * Agrega un producto en el catálogo de productos de una tienda.
     *
     * @param shop el nombre de la tienda de la que queremos actuializar el catálogo.
     * @param productCatalog el catálogo de productos que se desea actualizar.
     *
     * @return true si se ha podido actualizar el catálogo, false si no se ha podido.
     */
    public boolean addProductToCatalogue(String shop, ProductCatalog productCatalog) {

        Shop finalShop = shopFromString(shop);

        try {
            finalShop.setProductCatalog(productCatalog);
            return shopDAO.update(finalShop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un producto del catálogo de una tienda.
     *
     * @param shopName el nombre de la tienda de la que se modifica su catálogo.
     * @param productCatalog el catálogo de productos que se modifica de la tienda en cuestión.
     *
     * @return true si se ha podido eliminar el catálogo, false si no se ha podido.
     */
    public boolean removeProductToCatalogue(String shopName, ProductCatalog productCatalog) {
        Shop finalShop = shopFromString(shopName);
        try {
            finalShop.setProductCatalog(productCatalog);
            return shopDAO.update(finalShop);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lee todas las tiendas almacenadas en el sistema.
     *
     * @return ArrayList de strings con todas las tiendas almacenadas. Si no hay, esta lista estará vacía.
     */
    public ArrayList<Shop> readAll () {
        try {
            return shopDAO.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica si un producto existe en el catálogo de una tienda.
     *
     * @param product el producto que se verifica si está en la tienda proporcionada.
     * @param shopName el nombre de la tienda de donde es el producto a verificar.
     *
     * @return true si el producto está en el catálogo de la tienda, false si no está.
     */
    public boolean prodInShop(String product, String shopName) {

        Shop shop = shopFromString(shopName);
        ArrayList<String> prodsCat = shop.getProductCatalog().toArrayString();

        for(String prodCat: prodsCat) {
            if (prodCat.equalsIgnoreCase(product)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el precio del producto de una tienda.
     *
     * @param prod el producto del que se obtiene su precio.
     * @param shopName el nombre de la tienda en la que se encuentra el producto.
     *
     * @return el precio del producto en la tienda proporcionada.
     */
    public float getProductPriceShop(String prod, String shopName) {
        Shop shop = shopFromString(shopName);
        ProductCatalog productCatalog = shop.getProductCatalog();

        return productCatalog.searchForProductPrice(prod, productCatalog);

    }

    /**
     * Obtiene los beneficios de una tienda.
     *
     * @param shop la tienda de la que se obtienen los beneficios.
     *
     * @return los beneficios de la tienda proporcionada.
     */
    public float getEarnings(String shop) {
        ArrayList<Shop> shops = readAll();
        for (Shop shopShop: shops) {
            if (shopShop.getName().equalsIgnoreCase(shop)) {
                return shopShop.getEarnings();
            }
        }
        return 0;
    }

    /**
     * Obtiene el año de fundación de una tienda a partir de su nombre.
     *
     * @param shopName el nombre de la tienda de la que se obtiene el año de fundación.
     *
     * @return el año de fundación de la tienda proporcionada, null si no se encuentra la tienda.
     */
    public int getYearFromName(String shopName) {
        ArrayList<Shop> shops = readAll();
        for(Shop shop: shops) {
            if (shop.getName().equalsIgnoreCase(shopName)) {
                return shop.getFoundationYear();
            }
        }
        return 0;
    }

    /**
     * Obtiene la descripción de una tienda a partir de su nombre.
     *
     * @param shopName el nombre de la tienda de la que se obtiene la descripción.
     *
     * @return la descripción de la tienda proporcionada, null si no se encuentra la tienda.
     */
    public String getDescrFromName(String shopName) {
        ArrayList<Shop> shops = readAll();
        for(Shop shop: shops){
            if(shop.getName().equalsIgnoreCase(shopName)){
                return shop.getDescription();
            }
        }
        return null;
    }

    /**
     * Obtiene el nombre de los productos del catálogo de una tienda.
     *
     * @param shopName el nombre de la tienda de la que se obtienen los productos de su catálogo.
     *
     * @return un ArrayList de strings con los nombres de los productos del catálogo de una tienda, si no se encuentra este ArrayList estará vacío.
     */
    public ArrayList<String> getProdFromCat(String shopName) {
        ArrayList<Shop> shops = readAll();

        for(Shop shop: shops){
            if(shop.getName().equalsIgnoreCase(shopName)){
                ProductCatalog productCatalog = getProductCatalog(shopName);

                return productCatalog.toArrayString();
            }
        }

        return null;
    }

    /**
     * Obtiene las marcas de los productos del catálogo de una tienda.
     *
     * @param shopName el nombre de la tienda de la que se obtienen las marcas de los productos de su catálogo.
     *
     * @return un ArrayList de strings  con las marcas de los productos del catálogo de una tienda, si no se encuentra este ArrayList estará vacío.
     */
    public ArrayList<String> getBrandFromCat(String shopName) {
        ArrayList<Shop> shops = readAll();

        for(Shop shop: shops){
            if(shop.getName().equalsIgnoreCase(shopName)){

                ProductCatalog productCatalog = getProductCatalog(shopName);

                return productCatalog.toArrayStringBrand();
            }
        }

        return null;
    }

    /**
     * Obtiene los precios de los productos del catálogo de una tienda.
     *
     * @param shopName el nombre de la tienda de la que se obtienen los precios de los productos de su catálogo.
     *
     * @return un ArrayList de strings con los precios de los productos del catálogo de una tienda, si no se encuentra este ArrayList estará vacío.
     */
    public ArrayList<String> getPriceFromCat(String shopName) {
        ArrayList<Shop> shops = readAll();

        for(Shop shop: shops){
            if(shop.getName().equalsIgnoreCase(shopName)){
                ProductCatalog productCatalog = getProductCatalog(shopName);


                return productCatalog.searchForProductPriceArray(productCatalog);
            }
        }

        return null;
    }

    /**
     * Actualiza los beneficios generados por una tienda.
     *
     * @param earnings los nuevos beneficios a actualizar.
     * @param shop la tienda de la que se actualizan sus beneficios.
     */
    public void updateEarnings(float earnings, String shop) {
        ArrayList<Shop> shops = readAll();

        for(Shop sh: shops) {
            if (sh.getName().equalsIgnoreCase(shop)) {
                sh.setEarnings(earnings);

                try {
                    shopDAO.update(sh);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Obtiene las categorías de productos de una tienda especificada por su nombre.
     *
     * @param shopName Nombre de la tienda de la que se desean obtener las categorías de productos.
     * @return ArrayList de cadenas que contiene las categorías de productos de la tienda.
     */
    public ArrayList<String> getCatFromCat(String shopName) {
        ArrayList<Shop> shops = readAll();
        ArrayList<String> categories = new ArrayList<>();

        for(Shop shop: shops){
            if(shop.getName().equalsIgnoreCase(shopName)){

                ProductCatalog productCatalog = getProductCatalog(shopName);

                for (Product product: productCatalog.listProducts()) {
                    categories.add(product.getCategory());

                }
            }
        }

        return categories;
    }

    /**
     * Obtiene el modelo de negocio de una tienda especificada por su nombre.
     *
     * @param shopName Nombre de la tienda de la que se desea obtener el modelo de negocio.
     * @return Cadena que representa el modelo de negocio de la tienda.
     */
    public String getBusinessModel(String shopName) {
        ArrayList<Shop> shops = readAll();

        for(Shop shop: shops){
            if(shop.getName().equalsIgnoreCase(shopName)){

                 return shop.getBusinessModel().getModel();

            }
        }

        return null;
    }

    /**
     * Obtiene una tienda a partir de su nombre.
     *
     * @param shop Nombre de la tienda que se desea obtener.
     * @return Objeto Shop correspondiente a la tienda encontrada, o null si no se encuentra.
     */
    public Shop getShopFromString(String shop) {
        ArrayList<Shop> shops = readAll();
        for (Shop shop1: shops){
            if (shop1.getName().equalsIgnoreCase(shop)){
                return shop1;
            }
        }
        return null;
    }

    /**
     * Obtiene el umbral de lealtad de una tienda especificada por su nombre.
     *
     * @param shopName Nombre de la tienda de la que se desea obtener el umbral de lealtad.
     * @return Umbral de lealtad de la tienda en forma de valor flotante.
     */
    public float getLoyalty(String shopName) {
        Shop shop = getShopFromString(shopName);
        return ((Loyalty)shop.getBusinessModel()).getLoyaltyThreshold();
    }

    /**
     * Obtiene la marca patrocinadora de una tienda especificada por su nombre.
     *
     * @param shopName Nombre de la tienda de la que se desea obtener la marca patrocinadora.
     * @return Cadena que representa la marca patrocinadora de la tienda.
     */
    public String getSponsor(String shopName) {
        Shop shop = getShopFromString(shopName);
        return ((Sponsored)shop.getBusinessModel()).getSponsoringBrand();
    }

    /**
     * Obtiene las calificaciones de un producto específico de una tienda por su nombre de producto y nombre de tienda.
     *
     * @param shopName Nombre de la tienda en la que se encuentra el producto.
     * @param prodName Nombre del producto del que se desean obtener las calificaciones.
     * @return ArrayList de cadenas que contiene las calificaciones del producto en la tienda.
     */
    public ArrayList<String> getRatingFromCat(String shopName, String prodName) {
        ArrayList<String> ratings = new ArrayList<>();
        ArrayList<Shop> shops = readAll();

        for (Shop shop: shops) {
            if (shop.getName().equalsIgnoreCase(shopName)) {
                for(String prod: shop.getProductCatalog().toArrayString()) {
                    if (prod.equalsIgnoreCase(prodName)) {
                        return shop.getProductCatalog().getProductRating(prodName);
                    }
                }
            }
        }

        return ratings;
    }

    /**
     * Agrega una calificación a un producto específico en el catálogo de una tienda.
     *
     * @param prod Nombre del producto al que se agregará la calificación.
     * @param finalRating Calificación final a agregar en formato de cadena.
     */
    public void addProductToCatalogueRaiting(String prod,String finalRating) {
        ArrayList<Shop> shops = readAll();



        for (Shop shop: shops) {
            ArrayList<String> prods = shop.getProductCatalog().toArrayString();
            for (String product : prods) {

                if (prod.equalsIgnoreCase(product)) {
                    try {
                        shop.getProductCatalog().setProductRating(product, finalRating);
                        shopDAO.update(shop);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }

    }
}

