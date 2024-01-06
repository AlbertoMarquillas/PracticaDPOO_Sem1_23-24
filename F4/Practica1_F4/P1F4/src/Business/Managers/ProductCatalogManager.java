package Business.Managers;


import Business.Entities.Product;
import Business.Entities.ProductCatalog;

import java.util.ArrayList;

/**
 * Clase que gestiona las operaciones relacionadas con el catálogo de productos y tiendas.
 * @author alberto.marquillas i marc.viñas
 */
public class ProductCatalogManager {

    private ShopManager shopManager;
    private ProductManager productManager;

    /**
     * Constructor de la clase ProductCatalogManager.
     *
     * @param optionData la opcion para seleccionar la persistencia de los datos
     */
    public ProductCatalogManager(int optionData) {
        this.shopManager = new ShopManager(optionData);
        this.productManager = new ProductManager(optionData);
    }

    /**
     * Agrega un producto al catálogo de un establecimiento.
     *
     * @param product    Nombre del producto a agregar.
     * @param shop       Nombre del establecimiento.
     * @param productPrice Precio del producto a agregar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean addProduct(String product, String shop, float productPrice) {

        ProductCatalog productCatalog = shopManager.getProductCatalog(shop);

        Product prod = productManager.productFromString(product);

        prod.setPrice(productPrice);

        productCatalog.addProduct(prod);

        return shopManager.addProductToCatalogue(shop, productCatalog);
    }

    /**
     * Verifica si el precio del producto proporcionado es válido.
     *
     * @param productPrice Precio del producto a verificar.
     * @param productName Nombre del producto.
     * @return true si el precio del producto es válido, false en caso contrario.
     */
    public boolean checkProductPrice(float productPrice, String productName) {

        ArrayList<Product> products = productManager.readAll();

        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product.getMaxPrice() >= productPrice;
            }
        }
        return false;
    }

    /**
     * Elimina un producto del catálogo de una tienda.
     *
     * @param product   Nombre del producto a eliminar.
     * @param shopName  Nombre de la tienda.
     * @return true si el producto se elimina correctamente, false en caso contrario.
     */
    public boolean deleteProduct(String product, String shopName) {
            ProductCatalog productCatalog = shopManager.getProductCatalog(shopName);

            Product prod = productManager.productFromString(product);

            int pos = positionFromName(product, shopName);

            if (!productCatalog.removeProduct(prod, pos)) {
                return shopManager.removeProductToCatalogue(shopName, productCatalog);
            } else {
                return false;
            }

    }

    /**
     * Obtiene la posición de un producto en la lista de productos de una tienda.
     *
     * @param product el producto del cual se obtiene la posición.
     * @param shopName la tienda que contiene la lista de productos en la que se busca el producto en cuestión.
     *
     * @return la posición del producto en la lista de productos de la tienda.
     */
    private int positionFromName(String product, String shopName) {
        int i;
        ArrayList<String> prods = listProducts(shopName);
        ArrayList<Product> productArrayList = new ArrayList<>();
        for(i = 0; i < prods.size(); i++) {
            productArrayList.add(productManager.productFromString(prods.get(i)));
        }
        i = 0;
        for (Product prod: productArrayList){
            if (prod.getName().equalsIgnoreCase(product)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    /**
     * Obtiene una lista de productos de una tienda.
     *
     * @param shopName Nombre de la tienda.
     * @return Una lista de nombres de productos en la tienda especificada.
     */
    public ArrayList<String> listProducts(String shopName) {
        ArrayList<String> productNames;

        ProductCatalog productCatalog = shopManager.getProductCatalog(shopName);
        ArrayList<Product> products = productCatalog.listProducts();

        productNames = productManager.getNamesFromProducts(products);

        return productNames;
    }

    /**
     * Obtiene las marcas de los productos de una tienda.
     *
     * @param shopName la tienda de la que se obtienen las marcas de sus productos.
     *
     * @return un ArrayList con las marcas de todos los productos de una tienda.
     */
    public ArrayList<String> listBrands(String shopName) {

        ProductCatalog productCatalog = shopManager.getProductCatalog(shopName);
        ArrayList<Product> products = productCatalog.listProducts();

        return productManager.getBrandsFromProducts(products);
    }

    /**
     * Obtiene el nombre de un producto de una tienda a partir de su posición en la lista.
     *
     * @param deletedProduct la posición del producto del cual se quiere obtener el nombre.
     * @param shopName la tienda la cual contiene la lista con los productos.
     *
     * @return el nombre del producto obtenido a partir de su posición en la lista.
     */
    public String findNameFromInt(int deletedProduct, String shopName) {
        ProductCatalog productCatalog = shopManager.getProductCatalog(shopName);
        ArrayList<Product> products = productCatalog.listProducts();
        return products.get(deletedProduct-1).getName();
    }
}

