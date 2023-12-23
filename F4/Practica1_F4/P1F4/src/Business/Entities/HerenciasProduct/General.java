package Business.Entities.HerenciasProduct;

import Business.Entities.Product;

import java.util.ArrayList;

public class General extends Product {
    int iva;

    public General(String name, String brand, String category, float maxPrice, ArrayList<String> rating) {
        super(name, brand, category, maxPrice, rating);
        this.iva = 21;
    }

    public General(String name, String brand, String category, float maxPrice, ArrayList<String> rating, float price) {
        super(name, brand, category, maxPrice, rating, price);
        this.iva = 21;
    }
    public int getIva() {
        return iva;
    }
    @Override
    public float applyIVA(float price) {
        return (float) (price*(getIva()/100.0));
    }
}
