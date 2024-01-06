package Persistance.ProductPersistance;

import Business.Entities.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interfaz que define las operaciones de acceso a datos para productos.
 * @author alberto.marquillas i marc.viñas
 */
public interface ProductDAO {

    /**
     * Lee todos los productos almacenados.
     *
     * @throws FileNotFoundException - FileNotFoundException
     * @return ArrayList que contiene todos los productos almacenados.
     */
    ArrayList<Product> readAll() throws FileNotFoundException;

    /**
     * Actualiza la información de los productos proporcionados.
     *
     * @param products ArrayList de productos que se actualizarán.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean update(Product products) throws IOException;

    /**
     * Elimina los productos proporcionados.
     *
     * @param products ArrayList de productos que se eliminarán.
     * @throws IOException - IOException
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    boolean delete(ArrayList<Product> products) throws IOException;

    /**
     * Agrega nuevos productos al sistema.
     *
     * @param product ArrayList de productos que se agregarán.
     * @throws IOException - IOException
     * @return true si la adición fue exitosa, false en caso contrario.
     */
    boolean add(Product product) throws IOException;
}

