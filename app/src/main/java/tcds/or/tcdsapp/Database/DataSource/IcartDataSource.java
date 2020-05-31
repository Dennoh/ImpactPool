package tcds.or.tcdsapp.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tcds.or.tcdsapp.Database.ModelDB.Cart;


public interface IcartDataSource {
    Flowable<List<Cart>> getCartItems();
    Flowable<List<Cart>>getCartItemById(int cartItemid);
    int countCartItems();
    float sumPrice();
    String restaurantNames();
    Flowable<List<Cart>>getRestaurantByName(String restaurantName);
    String isMgahawaExist(String restaurantname);

    void emptyCart();
    void insertToCart(Cart... carts);
    void  updateToCart(Cart... carts);
    void  deleteCartItem(Cart cart);




}
