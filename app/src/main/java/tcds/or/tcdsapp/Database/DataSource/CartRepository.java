package tcds.or.tcdsapp.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tcds.or.tcdsapp.Database.ModelDB.Cart;


public class CartRepository implements IcartDataSource{
private IcartDataSource icartDataSource;

    public CartRepository(IcartDataSource icartDataSource) {
        this.icartDataSource = icartDataSource;
    }
    private static CartRepository instance;
    public static CartRepository getInstance(IcartDataSource icartDataSource){
        if (instance==null)
            instance=new CartRepository(icartDataSource);
        return instance;

    }


    @Override
    public Flowable<List<Cart>> getCartItems() {
        return icartDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int cartItemid) {
        return icartDataSource.getCartItemById(cartItemid);
    }

    @Override
    public int countCartItems() {
        return icartDataSource.countCartItems();
    }

    @Override
    public float sumPrice() {
        return icartDataSource.sumPrice();
    }

    @Override
    public String restaurantNames() {
        return icartDataSource.restaurantNames();
    }

    @Override
    public Flowable<List<Cart>> getRestaurantByName(String restaurantName) {
        return icartDataSource.getRestaurantByName(restaurantName);
    }

    @Override
    public String isMgahawaExist(String restaurantname) {
        return icartDataSource.isMgahawaExist(restaurantname);
    }


    @Override
    public void emptyCart() {
        icartDataSource.emptyCart();

    }

    @Override
    public void insertToCart(Cart... carts) {
        icartDataSource.insertToCart(carts);
    }

    @Override
    public void updateToCart(Cart... carts) {
icartDataSource.updateToCart(carts);
    }

    @Override
    public void deleteCartItem(Cart cart) {
        icartDataSource.deleteCartItem(cart);

    }
}
