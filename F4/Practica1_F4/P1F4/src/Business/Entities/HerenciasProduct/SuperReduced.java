package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

public class SuperReduced extends Product {

    int iva;

    public SuperReduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
        this.iva = 5;
    }

    public SuperReduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
        this.iva = 4;
    }
    public int getIva() {
        return iva;
    }

    public float applyIVA(float price) {
        return (float) (price*(getIva()/100.0));
    }

}
