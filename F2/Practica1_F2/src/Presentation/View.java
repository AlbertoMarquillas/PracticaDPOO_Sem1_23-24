package Presentation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase View que maneja la interacción con el usuario.
 *
 * @author alberto.marquillas i marc.viñas
 */
public class View {

    private final Scanner scanner;

    /**
     * Constructor de la clase View.
     */
    public View() {
        // Inicializa el objeto Scanner para leer la entrada del usuario desde la consola.
        this.scanner = new Scanner(System.in);
    }

    /**
     * Solicita una entrada de tipo String al usuario y muestra un mensaje.
     *
     * @param message: Mensaje a mostrar al usuario.
     *
     * @return String ingresado por el usuario.
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Solicita una entrada de tipo Integer al usuario y muestra un mensaje.
     *
     * @param message: Mensaje a mostrar al usuario.
     *
     * @return Integer ingresado por el usuario.
     */
    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                // Captura la excepción si el usuario no ingresa un entero válido.
                spacing();
                showMessage("That's not a valid integer, try again!");
                spacing();
            } finally {
                // Limpia el búfer del scanner para evitar problemas con la siguiente entrada.
                scanner.nextLine();
            }
        }
    }
    /**
     * Solicita al usuario que ingrese un número decimal (float) mediante un mensaje y espera la entrada del usuario.
     * Si el usuario ingresa un valor no válido, se mostrará un mensaje de error y se le pedirá que lo intente nuevamente.
     *
     * @param message El mensaje que se muestra al usuario para solicitar la entrada.
     *
     * @return Un número decimal (float) ingresado por el usuario.
     */
    public float askForFloat(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                // Captura la excepción si el usuario no ingresa un float válido.
                spacing();
                System.out.println("That's not a valid float, try again!");
                spacing();
            } finally {
                // Limpia el búfer del scanner para evitar problemas con la siguiente entrada.
                scanner.nextLine();
            }
        }
    }

    /**
     * Muestra un mensaje en la consola.
     *
     * @param message: Mensaje a mostrar.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Imprime un espacio en blanco entre líneas en la consola.
     */
    public void spacing() {
        System.out.println();
    }

    /**
     * Muestra el título del programa en la consola.
     */
    public void showTitle() {
        String MENU_TITLE = """
                         ________        ____
                  ___   / / ____/___    / __/_______
                 / _ \\/ / /   /  __ \\/ /   _/ ___/ _ \\
                /  __ / / /___/  /_/  / __/ / / __/
                \\___/_/\\____/\\____/_/ /_/ \\___/\s""";
        showMessage(MENU_TITLE);
        spacing();
    }

    /**
     * Muestra un mensaje de bienvenida en la consola.
     */
    public void showWelcome() {
        String WELCOME_TITLE = "Welcome to elCofre Digital Shopping Experiences.\n";
        showMessage(WELCOME_TITLE);
    }

    /**
     * Muestra el menú principal del programa en la consola.
     * El menú contiene opciones relacionadas con la gestión de empleados, como la promoción,
     * la visualización de sueldos, el acomodo de empleados y el cierre del sistema.
     * Utiliza showMessage para imprimir el menú en la consola.
     */
    public void showMenu() {
        String MENU = """
                \t1) Manage Products
                \t2) Manage Shops
                \t3) Search Products
                \t4) List Shops
                \t5) Your Cart
                
                \t6) Exit
                """;

        showMessage(MENU);
    }

    /**
     * Solicita al usuario que elija una experiencia de compra digital mediante la entrada de un número.
     *
     * @return Un entero que representa la opción seleccionada por el usuario.
     */
    public int askForOption() {

        String OPTION = "Choose a Digital Shopping Experience: ";
        return askForInteger(OPTION);
    }

    /**
     * Muestra la solicitud de nombre para un producto o tienda.
     *
     * @param prodShop Producto o tienda para el cual se solicita el nombre.
     * @return El nombre proporcionado por el usuario.
     */
    public String showAskForName(String prodShop) {

        String A_NAME = "Please enter the " + prodShop +"'s name: ";
        return askForString(A_NAME);
    }

    /**
     * Muestra un mensaje solicitando la marca de un producto y retorna la entrada del usuario.
     *
     * @return La marca del producto ingresada por el usuario.
     */
    public String showAskForBrand() {

        String A_NAME = "Please enter the product's brand: ";
        return askForString(A_NAME);

    }

    /**
     * Muestra un mensaje solicitando la categoría de un producto y retorna la entrada del usuario.
     *
     * @return La categoría del producto ingresada por el usuario.
     */
    public String showAskForCategory() {
        String A_CAT = "The system supports the following product categories:\n\n\tA) General\n\tB) Reduced Taxes\n\tC) Superreduced Taxes\n\nPlease pick the product's category: ";

        return askForString(A_CAT);

    }

    /**
     * Muestra un mensaje solicitando el precio de un producto y retorna la entrada del usuario como un entero.
     *
     * @return El precio del producto ingresado por el usuario.
     */
    public float showAskForPrice() {

        String PRICE = "Please enter the product’s price at this shop: ";

        return askForFloat(PRICE);

    }

    /**
     * Muestra un mensaje solicitando el precio máximo de un producto y retorna la entrada del usuario como un número flotante.
     *
     * @return El precio máximo del producto ingresado por el usuario.
     */
    public float showAskForMaxPrice() {
        String MAX_PRICE = "Please enter the product’s maximum retail price: ";
        return askForFloat(MAX_PRICE);
    }

    /**
     * Muestra un mensaje solicitando una descripción y retorna la entrada del usuario.
     *
     * @return La descripción ingresada por el usuario.
     */
    public String showAskForDescription() {
        String DESCR = "Please enter the shop’s description: ";

        return askForString(DESCR);
    }

    /**
     * Muestra un mensaje solicitando el año de fundación de una tienda y retorna la entrada del usuario como un entero.
     *
     * @return El año de fundación de la tienda ingresado por el usuario.
     */
    public int showAskForFundationYear() {
        String YEAR = "Please enter the shop's founding year: ";
        return askForInteger(YEAR);
    }

    /**
     * Muestra un mensaje solicitando el modelo de negocio de una tienda y retorna la entrada del usuario.
     *
     * @return El modelo de negocio de la tienda ingresado por el usuario.
     */
    public String showAskForBusinessModel() {
        String ASK_BM = "\nThe system supports teh following business models:\n\n\tA) Maximum Benefits\n\tB) Loyalty\n\tC) Sponsored\n\nPlease pick the shop's business model: ";
        return askForString(ASK_BM);
    }

    /**
     * Muestra la información de un producto que se ha agregado al carrito.
     *
     * @param name  Nombre del producto.
     * @param brand Marca del producto.
     */
    public void showAddProduct(String name, String brand) {

        String CONFIRM = "\nThe product \"" + name + "\" by \"" + brand + "\" was added to the system.\n";
        showMessage(CONFIRM);
    }

    /**
     * Muestra un mensaje solicitando el nombre de un producto a eliminar y retorna la entrada del usuario.
     *
     * @return El nombre del producto a eliminar ingresado por el usuario.
     */
    public int showDeleteProduct() {

        String DEL = "Which one would you like to remove? ";
        return askForInteger(DEL);

    }

    /**
     * Muestra los productos junto con sus marcas en la consola.
     *
     * @param products Lista de nombres de productos.
     * @param brands   Lista de marcas de productos.
     * @return La opción seleccionada por el usuario.
     */
    public int showProducts(ArrayList<String> products, ArrayList<String> brands) {
        String MESSAGE = "These are the currently available products:";
        showMessage(MESSAGE);
        spacing();
        int i = 0;
        for(String str: products) {
            showMessage("\t" + (i+1) + ") " + "\"" + str + "\"" + " by \"" + brands.get(i) + "\"");
            i++;
        }
        spacing();
        showMessage("\t" + (i+1) + ") Back");
        spacing();
        return i+1;
    }

    /**
     * Muestra la información para agregar un producto al carrito.
     *
     * @param product Nombre del producto.
     * @param brand   Marca del producto.
     */
    public void showAddToCart(String product, String brand) {
        String ADD_CART = "1x " + product + "\" by \"" + brand + "\" has been added to your cart.";
        showMessage(ADD_CART);
    }

    /**
     * Muestra un mensaje indicando el fin de la compra.
     */
    public void showEndShopping() {
        String CHECKOUT = "Your cart has been cleared.";
        showMessage(CHECKOUT);
    }

    /**
     * Muestra la información del proceso de pago.
     *
     * @param shop     Nombre de la tienda.
     * @param aFloat   Precio total de la compra.
     * @param earnings Ganancias de la tienda.
     */
    public void showCheckout(String shop, Float aFloat, float earnings) {
        String PROD = "\"" + shop + "\" has earned " + aFloat + ", for an historic total of " + earnings + ".";
        showMessage(PROD);
    }


    /**
     * Muestra el menú de operaciones relacionadas con productos en la consola.
     */
    public void showProductMenu() {
        String PROD_MENU = "\t1) Create a Product\n\t2) Remove a Product\n\n\t3) Back\n";
        showMessage(PROD_MENU);
    }

    /**
     * Muestra el menú de operaciones relacionadas con tiendas en la consola.
     */
    public void showShopMenu() {
        String SHOP_MENU = "\t1) Create a Shop\n\t2) Expand a Shop's Catalogue\n\t3) Reduce a Shop's Catalogue\n\n\t4) Back";
        showMessage(SHOP_MENU);
        spacing();
    }

    /**
     * Muestra un mensaje solicitando una opción (un entero)
     *
     * @return un entero que representa la opción escogida.
     */
    public int chooseOption() {
        String OPTION = "Choose an option: ";
        return askForInteger(OPTION);
    }

    /**
     * Muestra el menú del catálogo en la consola.
     *
     * @return La opción seleccionada por el usuario.
     */
    public int showCatalogMenu() {
        String C_MENU = "\t1) Read Reviews\n\t2) Review Product\n\t3) Add to Cart\n\nChoose an option: ";
        return askForInteger(C_MENU);
    }

    /**
     * Muestra un mensaje indicando la verificación del archivo JSON.
     */
    public void showVerififyingJson() {
        String VERIFY = "Verifying local files...";
        showMessage(VERIFY);

    }

    /**
     * Muestra un mensaje indicando que el programa se está iniciando.
     */
    public void showStartingProgram() {
        String START = "Starting program...";
        showMessage(START);
        spacing();
    }

    /**
     * Muestra un mensaje de error indicando que el archivo .json no puede ser accedido
     * y anuncia el cierre del programa.
     */
    public void shuttingDown() {
        String SHUTTING = "Error: The shops.json file can't be accessed.\nShutting down...";
        showMessage(SHUTTING);
    }

    /**
     * Muestra un mensaje indicando la salida del programa.
     */
    public void showExit() {
        String EXIT= "We hope to see you again!";
        showMessage(EXIT);
    }

    /**
     * Muestra un mensaje indicando que se va a agregar una tienda.
     *
     * @param name Nombre de la tienda que se va a agregar.
     */
    public void showAddShop(String name) {
        String ADD_SHOP = "\n\"" + name + "\" is now a part of the elCofre family.\n";
        showMessage(ADD_SHOP);
    }

    /**
     * Muestra un mensaje indicando que se va a buscar un producto.
     *
     * @return La cadena introducida por el usuario para la búsqueda del producto.
     */
    public String showSearchProduct() {
        String S_PROD = "Enter your query: ";
        return askForString(S_PROD);
    }

    /**
     * Muestra el menú de operaciones relacionadas con la búsqueda en la consola.
     *
     * @return La opción seleccionada por el usuario para las operaciones de búsqueda.
     */
    public int showSearchMenu() {
        String S_MENU = "Which one would you like to review? ";
        return askForInteger(S_MENU);

    }

    /**
     * Muestra el menú de operaciones relacionadas con el carrito de compras en la consola.
     */
    public void showCartMenu() {
        String C_MENU = "\t1) Checkout\n\t2) Clear cart\n\n\t3) Back\n";
        showMessage(C_MENU);
    }

    /**
     * Muestra un mensaje de error informando sobre cúales son las opciones posibles.
     *
     * @param option1 primer entero que representa la primera opción posible.
     * @param option2 segundo entero que representa la última opción posible.
     */
    public void showWrongOption(int option1, int option2) {
        String WRONG_OPTION = "Please enter an option between " + option1 + " and " + option2;
        showMessage(WRONG_OPTION);
        spacing();
    }

    /**
     * Muestra un mensaje de error si un nombre introducido ya se ha introducido anteriormente.
     */
    public void showNotUniqueName(){
        String STRING = "Error. Name not unique";
        spacing();
        showMessage(STRING);
        spacing();
    }

    /**
     * Muestra un mensaje de error informando sobre las opciones posibles.
     */
    public void showWrongCategory() {
        String STRING = "Error. Choose a correct option (A, B, C) \n";
        spacing();
        showMessage(STRING);
    }

    /**
     * Muestra un mensaje solicitando la confirmación de eliminar un producto.
     *
     * @param name el nombre del producto a eliminar.
     * @param brand la marca del producto a eliminar.
     *
     * @return una cadena introducida por el usuario para saber si confirma la operación.
     */
    public String confirmRemove(String name, String brand) {
        spacing();
        String CONF_DEL = "Are you sure you want to remove \"" + name + "\" by \"" + brand + "\"? ";
        return askForString(CONF_DEL);
    }

    /**
     * Mensaje de error por si no se añade un producto.
     *
     * @param prodShop el nombre del producto que se quería agregar a la tienda.
     */
    public void notAdded (String prodShop) {
        String NOT_ADDED = "Error. Not added "+ prodShop +".\n";
        showMessage(NOT_ADDED);
    }

    /**
     * Mensaje informativo para informar sobre el producto eliminado.
     *
     * @param name el nombre del producto eliminado.
     * @param brand la marca del producto eliminado.
     */
    public void deletedProduct(String name, String brand) {
        spacing();
        String DEL_PROD = "\"" + name + "\" by \"" + brand + "\" has been withdrawn from sale. \n";
        showMessage(DEL_PROD);
    }

    /**
     * Mensaje de error para informar de que no se ha eliminado un producto.
     *
     * @param name nombre del producto que se iba a eliminar.
     * @param brand marca del producto que se iba a eliminar.
     */
    public void errorDeleting(String name, String brand) {
        String NOT_DEL_PROD = "\"" + name + "\" by \"" + brand + "\" has not been withdrawn from sale. \n\n";
        showMessage(NOT_DEL_PROD);
    }

    /**
     * Mensaje de error informando de las opciones posibles.
     */
    public void wrongBusinessModel() {
        String STRING = "Error. Choose a correct option (A, B, C) \n";
        showMessage(STRING);
    }

    /**
     * Mensaje informativo sobre que hay que introducir un nombre positivo.
     */
    public void negativeNumber() {
        String NEG_NUMBER = "Please, enter a positive number.\n";
        showMessage(NEG_NUMBER);
    }

    /**
     * Muestra un producto junto a su marca.
     *
     * @param prod el nombre del producto.
     * @param brand la marca del producto.
     * @param i el número que lo identifica en este mensaje.
     */
    public void printTile(String prod, String brand, int i) {
        spacing();
        String TITLE = "\t" + (i+1) +") \"" + prod + "\" by \"" + brand + "\"";
        showMessage(TITLE);
    }

    /**
     * Mensaje que encabeza otro mensaje que informa del precio de un producto en una tienda.
     */
    public void soldAt() {
        String SOLD = "\t\tSold at:";
        showMessage(SOLD);
    }

    /**
     * Muestra la tienda y el precio en el que se vende un producto.
     *
     * @param shop el nombre de la tienda donde se vende el producto.
     * @param price el precio del producto en esa tienda.
     */
    public void shopData(String shop, float price) {
        String DATA = "\t\t\t- " + shop + ": " + price;
        showMessage(DATA);
    }

    /**
     * Mensaje que informa sobre que un producto no está a la venta en ninguna tienda.
     */
    public void showNotShops() {
        String NOT_SHOPS = "\t\tThis product is not currently being sold in any shops.";
        showMessage(NOT_SHOPS);
    }

    /**
     * Mensaje informativo sobre que un producto ahora se vende en una tienda.
     *
     * @param prod nombre del producto.
     * @param brand marca del producto.
     * @param shop tienda en la que se vende el producto ahora.
     */
    public void confirmExp(String prod, String brand, String shop) {
        String EXPANSION = "\"" + prod + "\" by \"" + brand + "\" is now being sold at \"" + shop + "\".";
        spacing();
        showMessage(EXPANSION);
        spacing();
    }

    /**
     * Mensaje de opciónde ir atrás para el usuario.
     *
     * @param i número identificativo según la lista en la que se muestre.
     */
    public void back(int i) {
        String BACK = "\t" + i +") Back";
        showMessage(BACK);
        spacing();
    }

    /**
     * Mensaje que muestra un submenú del programa.
     *
     * @return la opción introducida por el usuario.
     */
    public int reviewMenu() {
        String MENU = "\n\t1) Read Reviews\n\t2) Review Product";
        showMessage(MENU);
        spacing();

        return chooseOption();
    }

    /**
     * Mensaje que encabeza el mensaje que muestra las reseñas de un producto.
     *
     * @param product el nombre del producto.
     * @param brand la marca del producto.
     */
    public void readReviews(String product, String brand) {
        spacing();
        String MESSAGE = "These are the reviews for \"" + product + "\" by \"" + brand + "\": ";
        showMessage(MESSAGE);
    }

    /**
     * Mensaje que muestra las reseñas de un producto.
     *
     * @param reviews ArrayList de strings con las reseñas de un producto.
     */
    public void reviews(ArrayList<String> reviews) {
        spacing();
        for (String str: reviews) {
            showMessage("\t" + str);
        }
    }

    /**
     * Mensaje que pide al usuario una puntución del 1 al 5.
     *
     * @return la puntuación introducida por el usuario.
     */
    public String rateProd() {
        String MESSAGE = "\nPlease rate the product (1-5 stars): ";
        return askForString(MESSAGE);
    }

    /**
     * Mensaje que pide al usuario un comentario para una reseña.
     *
     * @return el comentario añadido por el usuario en la reseña.
     */
    public String commentProd() {
        String MESSAGE = "Please add a comment to your review: ";
        return askForString(MESSAGE);
    }

    /**
     * Mensaje de agradecimiento por hacer una reseña a un producto.
     *
     * @param prod el nombre del producto.
     * @param brand la marca del producto.
     */
    public void thankForRev(String prod, String brand) {
        String MESSAGE = "Thank you for your review of \"" + prod + "\" by \"" + brand + "\". ";
        showMessage(MESSAGE);
    }

    /**
     * Mensaje que muestra a media de las puntuaciones de un producto.
     *
     * @param averageReviews la media de puntuación de todas las reseñas.
     */
    public void averageReviews(float averageReviews) {
        spacing();
        String AVERGAE = "\tAverage rating: " + averageReviews + "*";
        showMessage(AVERGAE);
        spacing();
    }

    /**
     * Mensaje que encabeza un mensaje que muestra los productos en el carrito.
     */
    public void showCartTitle() {
        String MESSAGE = "Your cart contains the following items:\n";
        showMessage(MESSAGE);
    }

    /**
     * Mensaje que muestra los productos que hay en un carrito.
     *
     * @param productsCarts ArrayList de strings con los productos de un carrito.
     * @param brands ArrayList de strings con las marcas de los productos de un carrito.
     * @param prices ArrayList de floats con los precios de los productos de un carrito.
     */
    public void totalCart(ArrayList<String> productsCarts, ArrayList<String> brands, ArrayList<Float> prices) {
        int i = 0;
        for (String prod: productsCarts) {
            String MESSAGE = "\t- \"" + prod + "\" by \"" + brands.get(i) + "\"";
            String PRICE = "\t   Price: " + prices.get(i);

            showMessage(MESSAGE);
            showMessage(PRICE);

            i++;
        }
    }

    /**
     * Mensaje que muestra el precio total de una compra.
     *
     * @param totalPrice el precio total de los productos de un carrito.
     */
    public void showTotalPrice(float totalPrice) {
        String MESSAGE = "Total: " + totalPrice;
        spacing();
        showMessage(MESSAGE);
    }

    /**
     * Mensaje que pide confirmación para eliminar el carrito.
     *
     * @return cadena introducida por el usuario.
     */
    public String confirmClear() {
        String MESSAGE = "Are you sure you want to clear your cart?";
        spacing();
        return askForString(MESSAGE);
    }

    /**
     * Mensaje que pide confirmación para acabar la compra.
     *
     * @return cadena introducida por el usuario.
     */
    public String confirmCart() {
        String MESSAGE = "Are you sure you want to checkout? ";
        return askForString(MESSAGE);
    }

    /**
     * Mensaje de encabezado antes de mostrar todas las tiendas.
     */
    public void followingShops() {
        String LIST = "The elCofre family is formed by the following shops:";
        showMessage(LIST);
    }

    /**
     * Mensaje que muestra todas las tiendas disponibles.
     *
     * @param shops ArrayList de strigs con todas las tiendas alamacenadas en el sistema.
     *
     * @return la opción introducida por el usuario.
     */
    public int allShops(ArrayList<String> shops) {
        int i = 1;

        spacing();
        for (String shop: shops) {
            String MESSAGE = "\t" + i + ") " + shop;
            showMessage(MESSAGE);
            i++;
        }
        spacing();
        back(i);
        String OPTION = "Which catalogue do you want to see? ";
        return askForInteger(OPTION);
    }

    /**
     * Mensaje que muestra un producto y su información.
     *
     * @param prodName el nombre del producto.
     * @param brand la marca del producto.
     * @param price el precio del producto.
     * @param i el número del producto en el listado.
     */
    public void product(String prodName, String brand, String price, int i ) {

        String MESSAGE = "\t" + i + ") \"" + prodName + "\" by \"" + brand + "\"";
        String PRICE = "\t   Price: " + price;
        showMessage(MESSAGE);
        showMessage(PRICE);
        spacing();
    }

    /**
     * Mensaje que pide una opción.
     *
     * @return la opción introducida por el usuario.
     */
    public int interestedOption() {
        String OPTION = "Which one are you interested in: ";
        return askForInteger(OPTION);
    }

    /**
     * Encabezado para mostrar la información de una tienda.
     *
     * @param shopName el nombre de la tienda.
     * @param fundationYear el año de fundación de la tienda.
     * @param descr la descripción de la tienda.
     */
    public void shopTitle(String shopName, String fundationYear, String descr) {
        spacing();
        String TITLE = shopName + " - Since " + fundationYear + "\n" + descr;
        showMessage(TITLE);
        spacing();
    }

    /**
     * Mensaje que encabeza un listado con productos encontrados.
     */
    public void printMessage() {
        spacing();
        String MESSAGE = "The following products where found:";
        showMessage(MESSAGE);
    }

    /**
     * Mensaje informativo sobre que una tienda no existe.
     */
    public void noShop() {
        spacing();
        String MESSAGE = "This shop doesn't exist.";
        showMessage(MESSAGE);
        spacing();
    }

    /**
     * Mensaje informativo sobre que un precio excede el máximo de un producto.
     */
    public void priceError() {
        spacing();
        String MESSAGE = "This price exceeds the maximum!";
        showMessage(MESSAGE);
        spacing();
    }

    /**
     * Mensaje informativo sobre que no hay valoraciones para un producto.
     */
    public void noReview() {
        String NO_REV = "There are no reviews for this product.\n";
        showMessage(NO_REV);
    }
    /**
     * Mensaje informativo sobre que no hay productosen una tienda.
     */
    public void noProducts() {
        String NO_PROD = "There are no products";
        showMessage(NO_PROD);
    }
    /**
     * Muestra un mensaje indicando que el producto no existe.
     */
    public void noProduct() {
        String NO_PROD = "The product does not exist.";
        spacing();
        showMessage(NO_PROD);
        spacing();
    }
}

