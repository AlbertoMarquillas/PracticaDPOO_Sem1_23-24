package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

public class Reduced extends Product {

    int iva;

    public Reduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
        this.iva = 10;
    }

    public Reduced(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
        this.iva = 10;
    }
    public int getIva() {
        return iva;
    }
    @Override
    public float applyIVA(float price) {
        return (float) (price*(getIva()/100.0));
    }
}
