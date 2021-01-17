package in.kay.mealzip.Models;

public class CartModel {
    String name,price;
    int imageres;

    public CartModel(String name, String price, int imageres) {
        this.name = name;
        this.price = price;
        this.imageres = imageres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageres() {
        return imageres;
    }

    public void setImageres(int imageres) {
        this.imageres = imageres;
    }
}
