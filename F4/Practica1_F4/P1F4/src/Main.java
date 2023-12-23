import Presentation.Controller;

/**
 * Clase principal que contiene el método main para iniciar la aplicación.
 * @author alberto.marquillas i marc.viñas
 */
public class Main {
    /**
     * Punto de entrada principal del programa.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.run();
    }

}
