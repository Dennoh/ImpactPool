package tcds.or.tcdsapp.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import tcds.or.tcdsapp.Database.DataSource.IcartDataSource;
import tcds.or.tcdsapp.Database.ModelDB.Cart;


public class CartDataSource implements IcartDataSource {
private CartDAO cartDAO;
private static CartDataSource instance;

    public CartDataSource(CartDAO cartDAO) {

        this.cartDAO = cartDAO;
    }
    public static CartDataSource getInstance(CartDAO cartDAO){
        if (instance==null)
            instance=new CartDataSource(cartDAO);
        return instance;
    }


    @Override
    public Flowable<List<Cart>> getCartItems() {
        return cartDAO.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemById(int cartItemid) {
        return cartDAO.getCartItemById(cartItemid);
    }

    @Override
    public int countCartItems() {
        return cartDAO.countCartItems();
    }

    @Override
    public float sumPrice() {
        return cartDAO.sumPrice();
    }

    @Override
    public String restaurantNames() {
        return cartDAO.restaurantNames();
    }

    @Override
    public Flowable<List<Cart>> getRestaurantByName(String restaurantName) {
        return cartDAO.getRestaurantByName(restaurantName);
    }

    @Override
    public String isMgahawaExist(String restaurantname) {
        return cartDAO.isMgahawaExist(restaurantname);
    }


    @Override
    public void emptyCart() {
        cartDAO.emptyCart();

    }

    @Override
    public void insertToCart(Cart... carts) {
      cartDAO.insertToCart(carts);
    }

    @Override
    public void updateToCart(Cart... carts) {
        cartDAO.updateToCart(carts);

    }

    @Override
    public void deleteCartItem(Cart cart) {
cartDAO.deleteCartItem(cart);
    }
}
