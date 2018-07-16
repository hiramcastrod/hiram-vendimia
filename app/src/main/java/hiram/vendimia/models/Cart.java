package hiram.vendimia.models;

public class Cart {
    String modelProduct;
    int quantity;
    float total;

    public Cart(String modelProduct, int quantity, float total){
        this.modelProduct = modelProduct;
        this.quantity = quantity;
        this.total = total;
    }

    public String getModelProduct() {
        return modelProduct;
    }

    public void setModelProduct(String modelProduct) {
        this.modelProduct = modelProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
