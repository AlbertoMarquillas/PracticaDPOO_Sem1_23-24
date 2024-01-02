package Presentation;

import Business.Managers.CartManager;
import Business.Managers.ProductCatalogManager;
import Business.Managers.ProductManager;
import Business.Managers.ShopManager;
import edu.salle.url.api.ApiHelper;
import edu.salle.url.api.exception.ApiException;

import java.io.FileNotFoundException;
import java.util.ArrayList;



/**
 * Clase que representa el controlador del programa.
 * @author alberto.marquillas i marc.viñas
 */
public class Controller {

    private View view;
    private ProductManager productManager;
    private ShopManager shopManager;
    private ProductCatalogManager productCatalogManager;
    private CartManager cartManager;

    static final int CLOUD = 0;
    static final int JSON = 1;
    static final int ERROR_PERSISTANCE = 1;

    /**
     * Constructor de la clase Controller.
     * @throws RuntimeException Si hay un error al instanciar los gestores debido a un FileNotFoundException.
     */
    public Controller() {
        this.view = new View();
    }

    /**
     * Método principal que ejecuta el programa.
     * Inicializa el programa y muestra un menú interactivo para realizar diferentes acciones.
     */
    public void run() {

        int option = 0;

        /*

        SI QUEREMOS BORRAR TODAS LAS TIENDAS




        try {
            ApiHelper apiHelper = new ApiHelper();
            String pathString = "https://balandrau.salle.url.edu/dpoo/P1-G109/shops";
            String pathString2 = "https://balandrau.salle.url.edu/dpoo/P1-G109/products";

            //apiHelper.deleteFromUrl(pathString);
            //apiHelper.deleteFromUrl(pathString2);

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        */

        startProgram();
        int data = checkProgram();
        if (data != -1) {


            this.shopManager = new ShopManager(data);
            this.productCatalogManager = new ProductCatalogManager(data);
            this.cartManager = new CartManager();

            view.showStartingProgram();
            while (option != 6) {
                view.showMenu();

                option = view.askForOption();
                view.spacing();

                switch (option) {
                    case 1 -> manageProducts();
                    case 2 -> manageShops();
                    case 3 -> searchProducts();
                    case 4 -> listShops();
                    case 5 -> yourCart();
                    case 6 -> view.showExit();

                    default -> view.showWrongOption(1, 6);
                }
            }
        } else {
            view.shuttingDown();
        }
    }

    public void startProgram() {
        view.showTitle();
        view.showWelcome();
        view.showVerififyingJson();
    }

    private int checkProgram() {
        this.productManager = new ProductManager(0);
        if (productManager.isUsingCloud()) {
            view.showProductCloudTrue();
            return CLOUD;
        } else {
            view.showProductJSONTrue();
            return JSON;
        }
    }

    /**
     * Gestiona las operaciones relacionadas con los productos
     */
    public void manageProducts() {

        int option = 0;

        while (option != 3) {

            view.showProductMenu();
            option = view.chooseOption();
            view.spacing();

            switch (option) {
                case 1 -> createProduct();
                case 2 -> removeProduct();
                case 3 -> {
                }
                default -> view.showWrongOption(1, 3);
            }

        }

    }

    /**
     * Crea un nuevo producto.
     */
    public void createProduct() {

        String name = view.showAskForName("product");

        if (productManager.productUniqueName(name)) {
            String brand = view.showAskForBrand();
            brand = productManager.stringFormat(brand);
            float maxPrice = view.showAskForMaxPrice();

            while (!productManager.checkPositiveFloat(maxPrice)) {
                maxPrice = view.showAskForMaxPrice();
            }
            String category;
            view.spacing();
            while (true) {
                category = view.showAskForCategory();

                switch (category.toLowerCase()) {
                    case "a" -> category = "GENERAL";

                    case "b" -> category = "REDUCED";

                    case "c" -> category = "SUPER_REDUCED";

                    default -> {
                        view.showWrongCategory();
                        continue;
                    }
                }
                break;
            }


            if (productManager.createProduct(name, brand, category, maxPrice)) {
                view.showAddProduct(name, brand);
            } else {
                view.notAdded("product");
            }

        } else {
            view.showNotUniqueName();
        }


    }

    /**
     * Elimina un producto existente.
     */
    public void removeProduct() {

        ArrayList<String> products = productManager.listProducts();
        ArrayList<String> brands = productManager.listBrands();

        while (true) {
            int ultimoProd = view.showProducts(products, brands);
            int deletedProduct = view.showDeleteProduct();
            view.spacing();

            if (deletedProduct < ultimoProd && deletedProduct > 0) {
                String confirmation = view.confirmRemove(products.get(deletedProduct - 1), brands.get(deletedProduct - 1));
                String delProd = productManager.findNameFromInt(deletedProduct);
                if (productManager.confirmDelete(confirmation)) {
                    if (productManager.deleteProduct(delProd)) {
                        view.deletedProduct(products.get(deletedProduct - 1), brands.get(deletedProduct - 1));
                    } else {
                        view.errorDeleting(products.get(deletedProduct - 1), brands.get(deletedProduct - 1));
                    }
                }
                break;
            } else if (deletedProduct == ultimoProd) {
                view.spacing();
                break;
            } else {
                view.spacing();
                view.showWrongOption(1, ultimoProd);
            }
        }

    }

    /**
     * Gestiona las operaciones relacionadas con las tiendas.
     */
    public void manageShops() {
        int option = 0;

        while (option != 4) {
            view.showShopMenu();
            option = view.chooseOption();
            view.spacing();

            switch (option) {
                case 1 -> createShop();
                case 2 -> expandShopCatalogue();
                case 3 -> reduceShopCatalogue();
                case 4 -> {

                }
                default -> view.showWrongOption(1, 4);
            }

        }
    }

    /**
     * Gestiona la creación de una tienda.
     */
    public void createShop() {

        String name = view.showAskForName("shop");
        if (shopManager.shopUniqueName(name)) {
            String description = view.showAskForDescription();
            int foundingYear;
            while (true) {
                foundingYear = view.showAskForFundationYear();
                if (shopManager.checkPositive(foundingYear)) {
                    break;
                } else {
                    view.negativeNumber();
                }
            }

            String businessModel;
            float loyaltyThreshold = 0;
            String sponsoringBrand = null;

            while (true) {
                businessModel = view.showAskForBusinessModel();

                switch (businessModel.toLowerCase()) {
                    case "a" -> {
                        businessModel = "MAX_PROFIT";
                    }

                    case "b" -> {
                        businessModel = "LOYALTY";
                        loyaltyThreshold = view.askForLoyaltyTreshold();
                    }

                    case "c" -> {
                        businessModel = "SPONSORED";
                        sponsoringBrand = view.askForSponsoringBrand();
                    }
                    default -> {
                        view.wrongBusinessModel();
                        continue;
                    }
                }
                break;
            }

            if (shopManager.createShop(name, description, foundingYear, businessModel, loyaltyThreshold, sponsoringBrand)) {
                view.showAddShop(name);
            } else {
                view.notAdded("shop");
            }

        } else {
            view.showNotUniqueName();
        }
    }

    /**
     * Gestiona la expansión de un catálogo.
     */
    public void expandShopCatalogue() {
        String shopName = view.showAskForName("shop");

        if (!shopManager.shopUniqueName(shopName)) {
            String productName = view.showAskForName("product");
            if (!productManager.productUniqueName(productName)) {
                float productPrice = view.showAskForPrice();
                if (productCatalogManager.checkProductPrice(productPrice, productName)) {

                    if(productCatalogManager.addProduct(productName, shopName, productPrice)) {
                        String brand = productManager.getBrandFromProduct(productName);
                        view.confirmExp(productName, brand ,shopName);
                    }
                } else {
                    view.priceError();
                }
            } else {
                view.noProduct();
            }
        } else {

            view.noShop();
        }

    }

    /**
     * Gestiona la reducción de un catálogo.
     */
    public void reduceShopCatalogue() {

        String shopName = view.showAskForName("shop");

        //this shop sells the following products:

        //primer llista productes
        ArrayList<String> products = productCatalogManager.listProducts(shopName);
        ArrayList<String> brands = productCatalogManager.listBrands(shopName);

        while (true) {
            int ultimoProd = view.showProducts(products, brands);
            int deletedProduct = view.showDeleteProduct();
            view.spacing();

            if (deletedProduct < ultimoProd && deletedProduct > 0) {
                String delProd = productCatalogManager.findNameFromInt(deletedProduct, shopName);
                if (productCatalogManager.deleteProduct(delProd, shopName)) {
                    view.deletedProduct(products.get(deletedProduct - 1), brands.get(deletedProduct - 1));
                    break;
                } else {
                    view.errorDeleting(products.get(deletedProduct - 1), brands.get(deletedProduct - 1));
                }
            } else if (deletedProduct > ultimoProd || deletedProduct <= 0) {
                view.spacing();
                view.showWrongOption(1, ultimoProd);
            } else {
                break;
            }
        }
    }

    /**
     * Realiza búsquedas de productos
     */
    public void searchProducts() {

        int i = 0;
        boolean printed = false;

        String element = view.showSearchProduct();

        ArrayList<String> products = productManager.searchProductName(element);

        ArrayList<String> shops =  shopManager.listShops();

        view.printMessage();
        for (String prod: products) {
            String brand = productManager.getBrandFromProduct(prod);
            view.printTile(prod, brand, i);
            for (String shop: shops) {
                if (shopManager.prodInShop(prod, shop)) {
                    if (!printed) {
                        view.soldAt();
                    }
                    float price = shopManager.getProductPriceShop(prod, shop);
                    view.shopData(shop, price);
                    printed = true;
                }
            }
            if (!printed) {
                view.showNotShops();
            }
            printed = false;
            i++;
        }
        i++;
        view.spacing();
        view.back(i);

        int opcioReview;

        do {

            opcioReview = view.showSearchMenu();

            if (opcioReview == i) {
                view.spacing();
                return;
            }

        } while (opcioReview > i);

        if (opcioReview - 1 > products.size() || products.size() == 0){
            view.spacing();
            return;
        }
        String prod = products.get(opcioReview-1);
        String prodBrand = productManager.getBrandFromProduct(prod);

        int opcioMenu;

        do {

            opcioMenu = view.reviewMenu();

            switch (opcioMenu) {
                case 1 -> readReviews(prod, prodBrand);
                case 2 -> reviewProduct(prod, prodBrand);
                default -> view.showWrongOption(1,2);
            }


        } while (opcioMenu > 2 || opcioMenu < 1);


    }

    /**
     * Enseña las reseñas de un producto.
     *
     * @param product el nombre del producto.
     * @param brand la marca del producto.
     */
    public void readReviews(String product, String brand) {
        ArrayList<String> reviews = productManager.getReviews(product);
        if (reviews.size() > 0) {
            view.readReviews(product, brand);
            float averageReviews = productManager.getAverageReviews(reviews);
            view.reviews(reviews);
            view.averageReviews(averageReviews);
        } else {
            view.spacing();
            view.noReview();
        }

    }

    /**
     * Gestiona la creación de una reseña sobre un producto.
     *
     * @param prod el nombre del producto.
     * @param brand la marca del producto.
     */
    public void reviewProduct(String prod, String brand) {

        String rate = view.rateProd();
        String comment = view.commentProd();

        String rating = productManager.contRating(rate);

        String finalRating = rating + "* " + comment;

        if (productManager.addRating(finalRating, prod)) {
            shopManager.addProductToCatalogueRaiting(prod, finalRating);
            view.spacing();
            view.thankForRev(prod, brand);
            view.spacing();
        }
    }

    /**
     * Lista todas las tiendas
     */
    public void listShops() {
        int option;
        int i = 1;

        view.followingShops();

        ArrayList<String> shops = shopManager.listShops();

        option = view.allShops(shops);

        if (option - 1 < shops.size()) {

            String shopName = shops.get(option-1);
            int fundationYear = shopManager.getYearFromName(shopName);
            String descr = shopManager.getDescrFromName(shopName);
            float earnings = shopManager.getEarnings(shopName);
            String businessModel = shopManager.getBusinessModel(shopName);
            float loyaltyThres = 0;
            String sponsor = null;
            if (businessModel.equalsIgnoreCase("LOYALTY")) {
                loyaltyThres = shopManager.getLoyalty(shopName);
            }
            if (businessModel.equalsIgnoreCase("SPONSORED")){
                sponsor = shopManager.getSponsor(shopName);
            }

            ArrayList<String> prodName = shopManager.getProdFromCat(shopName);
            ArrayList<String> brand = shopManager.getBrandFromCat(shopName);
            ArrayList<String> price = shopManager.getPriceFromCat(shopName);
            ArrayList<String> category = shopManager.getCatFromCat(shopName);

            view.shopTitle(shopName, fundationYear, descr);

            if(prodName.size() > 0) {
                do{

                    view.product(prodName.get(i-1), brand.get(i-1), price.get(i-1), i);
                    i++;

                } while(i < prodName.size()+1);
                view.back(i);
                int interest = view.interestedOption();
                view.spacing();

                if (interest -1 < prodName.size()) {
                    int catOption;

                    do{
                        catOption = view.showCatalogMenu();
                        ArrayList<String> reviews = shopManager.getRatingFromCat(shopName, prodName.get(interest-1));
                        switch (catOption) {
                            case 1 -> readReviews(prodName.get(interest-1), brand.get(interest-1));
                            case 2 -> reviewProduct(prodName.get(interest-1), brand.get(interest-1));
                            case 3 -> addToCart(prodName.get(interest-1), brand.get(interest-1), shopName, Float.parseFloat(price.get(interest-1)), category.get(interest-1), fundationYear, descr, earnings, businessModel, loyaltyThres, sponsor, reviews);
                            default -> view.showWrongOption(1, 3);
                        }

                    }while(catOption > 3 || catOption < 1);
                }

            } else {
                view.noProducts();
                view.spacing();
            }
        } else {
            view.spacing();
        }
    }

    /**
     * Gestiona añadir un producto al carrito.
     *
     * @param productName el nombre del producto.
     * @param brand la marca del producto.
     * @param shopName la tienda donde se vende el producto.
     * @param price el precio del producto.
     */
    public void addToCart(String productName, String brand, String shopName, Float price, String category, int fundationYear, String descr, float earnings, String businessModel, float loyaltyThres, String sponsor, ArrayList<String> reviews) {

        float totalPrice = 0;

        cartManager.addToCart(productName, brand, shopName, price, category, fundationYear, descr, earnings, businessModel, loyaltyThres, sponsor, reviews);

        view.spacing();
        view.showAddToCart(productName, brand);
        view.spacing();
    }

    /**
     * Gestiona las operaciones relacionadas con el carrito
     */
    public void yourCart() {

        view.showCartTitle();

        ArrayList<String> shops = cartManager.getShopCart();

        float totalPrice = 0;

        ArrayList<String> products = cartManager.getCartProds();
        ArrayList<String> brands = cartManager.getCartBrands();
        ArrayList<Float> prices = cartManager.getCartPrice();
        ArrayList<String> cantidades = cartManager.getCantidades();

        view.totalCart(products, brands, prices, cantidades);

        totalPrice = cartManager.sumTotalPrice();

        view.showTotalPrice(totalPrice);
        view.spacing();

        int option;

        do {
            view.showCartMenu();
            option = view.chooseOption();

            switch (option) {
                case 1:
                    checkout(shops);
                    break;
                case 2:
                    clearCart();
                    break;
                case 3:
                    break;
                default:
                    view.showWrongOption(1,3);
                    break;
            }

        } while (option > 3 || option < 1);
    }

    /**
     * Gestiona borrar el carrito.
     */
    public void clearCart() {
        String confirm = view.confirmClear();
        view.spacing();
        if (cartManager.confirmOK(confirm)) {
            if(cartManager.emptyCart()){
                view.showEndShopping();
                view.spacing();
            }
        }
    }

    /**
     * Gestiona terminar una compra.
     *
     * @param shops ArrayList de strings con el nombre de las tiendas de todos los productos del carrito.
     *
     */
    public void checkout(ArrayList<String> shops) {
        view.spacing();
        String confirm = view.confirmCart();
        view.spacing();
        float earnings = 0;
        float price = 0;
        float totalPrice = 0;
        if (cartManager.confirmOK(confirm)) {
            int i = 0;
            for (String shop: shops) {

                ArrayList<String> products = cartManager.getCartProds();
                for (String product: products){
                    if (shopManager.getProdFromCat(shop).contains(product)) {
                        //ingresos nets per a la botiga
                        price = cartManager.calculateTaxes(shop, i);
                        totalPrice = totalPrice + price;
                        earnings = shopManager.getEarnings(shop) + price;
                        shopManager.updateEarnings(earnings, shop);
                        i++;
                    }

                }

                view.showCheckout(shop, totalPrice, shopManager.getEarnings(shop));

                if (i == shops.size()){
                    break;
                }
            }

            view.spacing();
            if (cartManager.emptyCart()) {
                view.showEndShopping();
                view.spacing();
            }
        }
    }

}

