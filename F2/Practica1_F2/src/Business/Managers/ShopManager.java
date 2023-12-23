package Business.Managers;

import Business.Entities.ProductCatalog;
import Business.Entities.Shop;
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
     * Constructor de la clase ShopManager.
     */
    public ShopManager() {
        try {
            this.shopDAO = new ShopJSON();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea una nueva tienda con la información proporcionada.
     *
     * @param name          Nombre de la tienda.
     * @param description   Descripción de la tienda.
     * @param foundingYear  Año de fundación de la tienda.
     * @param businessModel Modelo de negocio de la tienda.
     * @return true si la tienda se creó correctamente, false en caso contrario.
     */
    public boolean createShop(String name, String description, int foundingYear, String businessModel) {
        Shop shop = new Shop(name, description, foundingYear, businessModel);

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
     * @param shops Lista de tiendas con la que se busca la tienda en cuestión.
     * @param shopName Cadena que representa la tienda que se quiere encontrar.
     *
     * @return La tienda que coincide con la cadena proporcionada encontrada en la lista de tiendas, null si no coincide.
     */
    public Shop shopFromString(ArrayList<Shop> shops, String shopName) {
        for(Shop shop: shops) {
            if(shop.getName().equalsIgnoreCase(shopName)){
                return shop;
            }
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

        ArrayList<Shop> shops = readAll();
        Shop finalShop = shopFromString(shops, shop);
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
        ArrayList<Shop> shops = readAll();
        Shop finalShop = shopFromString(shops, shopName);
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

        Shop shop = shopFromString(readAll(), shopName);
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
        Shop shop = shopFromString(readAll(), shopName);
        ProductCatalog productCatalog = shop.getProductCatalog();

        return productCatalog.searchForProductPrice(prod, productCatalog);//productCatalogManager.searchForProductPrice(prod, productCatalog);

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
    public String getYearFromName(String shopName) {
        ArrayList<Shop> shops = readAll();
        for(Shop shop: shops) {
            if (shop.getName().equalsIgnoreCase(shopName)) {
                return String.valueOf(shop.getFoundationYear());
            }
        }
        return null;
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
}
