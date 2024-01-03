package Business.Entities;

/**
 * Clase que representa un modelo de negocio.
 * @author alberto.marquillas i marc.viñas
 */
public abstract class BusinessModel {

    // Atributos
    private String model;

    /**
     * Constructor de la clase BusinessModel.
     */
    public BusinessModel() {

    }

    public abstract String getModel();

    public abstract float descompte(Float price, int iva);


}

