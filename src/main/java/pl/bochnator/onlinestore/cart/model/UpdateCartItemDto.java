package pl.bochnator.onlinestore.cart.model;

public class UpdateCartItemDto {

    private Integer quantity;
    private String price;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
