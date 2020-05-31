package tcds.or.tcdsapp.Database.Local;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;
import tcds.or.tcdsapp.Database.ModelDB.Cart;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
    Flowable<List<Cart>> getCartItems();

    @Query("SELECT * FROM Cart WHERE id=:cartItemId")
    Flowable<List<Cart>> getCartItemById(int cartItemId);

    @Query("SELECT COUNT(*) FROM Cart ")
    int countCartItems();


    @Query("SELECT SUM(Price) FROM Cart ")
    float sumPrice();


    @Query("SELECT restaurantname FROM Cart")
    String restaurantNames();


    @Query("SELECT * FROM Cart WHERE restaurantname=:restaurantName")

            Flowable<List<Cart>> getRestaurantByName(String restaurantName);



    @Query("SELECT restaurantname FROM Cart WHERE restaurantname=:restaurantname")
    String isMgahawaExist(String restaurantname);


    @Query("DELETE FROM Cart")
    void emptyCart();

    @Insert
    void insertToCart(Cart... carts);

    @Update
    void  updateToCart(Cart... carts);

    @Delete
    void  deleteCartItem(Cart cart);





}
