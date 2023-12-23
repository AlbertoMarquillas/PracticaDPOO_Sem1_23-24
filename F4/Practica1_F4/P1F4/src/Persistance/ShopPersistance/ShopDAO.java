package Persistance.ShopPersistance;

import Business.Entities.Shop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interfaz que define las operaciones de acceso a datos para tiendas.
 * @author alberto.marquillas i marc.viñas
 */
public interface ShopDAO {



    /**
     * Lee todas las tiendas almacenadas.
     *
     * @throws FileNotFoundException - FileNotFoundException
     * @return ArrayList que contiene todas las tiendas almacenadas.
     */
    ArrayList<Shop> readAll() throws FileNotFoundException;

    /**
     * Actualiza la información de las tiendas proporcionadas.
     *
     * @param shop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean update(Shop shop) throws IOException;

    /**
     * Agrega nuevas tiendas al sistema.
     *
     * @param shop Tienda que se agregará.
     * @throws IOException - IOException
     * @return true si la adición fue exitosa, false en caso contrario.
     */
    boolean add(Shop shop) throws IOException;
}
