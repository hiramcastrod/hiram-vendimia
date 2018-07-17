package hiram.vendimia.models;

public class Cart {
    String modelProduct;
    int quantity, icon;
    float total;

    public Cart(String modelProduct, int quantity, float total, int icon){
        this.modelProduct = modelProduct;
        this.quantity = quantity;
        this.total = total;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
