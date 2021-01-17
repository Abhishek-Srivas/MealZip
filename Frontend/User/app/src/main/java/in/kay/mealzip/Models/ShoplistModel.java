package in.kay.mealzip.Models;

import java.util.List;

public class ShoplistModel {
       String _id;
       List<ShopItem> shopItem;

    public ShoplistModel(String _id, List<ShopItem> shopItem) {
        this._id = _id;
        this.shopItem = shopItem;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<ShopItem> getShopItem() {
        return shopItem;
    }

    public void setShopItem(List<ShopItem> shopItem) {
        this.shopItem = shopItem;
    }
}
